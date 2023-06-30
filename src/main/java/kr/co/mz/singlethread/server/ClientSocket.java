package kr.co.mz.singlethread.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import kr.co.mz.singlethread.database.dao.CacheDao;
import kr.co.mz.singlethread.utils.http.ParsedRequest;
import kr.co.mz.singlethread.utils.http.ResponseGenerator;
import kr.co.mz.singlethread.utils.http.ResponseSender;

public class ClientSocket implements AutoCloseable {

  private final Socket socket;
  private final Cache cache;
  private final Connection connection;

  public ClientSocket(
      Socket clientSocket, Cache cache, Connection connection
  ) {
    this.socket = clientSocket;
    this.cache = cache;
    this.connection = connection;
  }

  public void handleRequest() throws IOException, SQLException {
    final var requestLine = new BufferedReader(
        new InputStreamReader(socket.getInputStream())).readLine();

    var parsedRequest = new ParsedRequest(requestLine);

    var cacheDao = new CacheDao(connection);
    var responseGenerator = new ResponseGenerator(
        parsedRequest,
        cache
    );

    var cacheDaoOneByFileName = cacheDao.findOneByFileName(parsedRequest.getPath().substring(6));
    var responseBytes = parsedRequest.isCacheDtoRequest() && cacheDaoOneByFileName.isPresent()
        ? cacheDaoOneByFileName.get().getFileData()
        : new byte[0];

    if (responseBytes.length == 0) {
      responseBytes = responseGenerator.generateResponse();
      cacheDao.insertOne(parsedRequest.getPath(), responseBytes);
    }
    var responseSender = new ResponseSender(socket.getOutputStream(), responseBytes);
    responseSender.send();
  }//handle


  @Override
  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
      System.err.println("Failed to close serversocket: " + e.getMessage());
    }
  }// close
}

package kr.co.mz.tutorial.httpserver.guide.server;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import kr.co.mz.tutorial.httpserver.guide.cache.Cache;
import kr.co.mz.tutorial.httpserver.guide.httpserver.RequestLineParser;
import kr.co.mz.tutorial.httpserver.guide.httpserver.ResponseGenerater;
import kr.co.mz.tutorial.httpserver.guide.httpserver.ResponseSender;
import kr.co.mz.tutorial.httpserver.guide.util.jdbc.DBConnector;

public class ClientRequestHandler implements AutoCloseable {

  private final Socket clientSocket;
  private final Cache cache;

  public ClientRequestHandler(Socket clientSocket, Cache cache) {
    this.clientSocket = clientSocket;
    this.cache = cache;

  }

  public void handle() throws IOException {

    try (DBConnector dbConnector = new DBConnector()) {// todo ??
      dbConnector.getConnection();

      var responseGenerater = new ResponseGenerater(
          new RequestLineParser(clientSocket.getInputStream()), cache, dbConnector);
      var responseSender = new ResponseSender(clientSocket.getOutputStream(), responseGenerater);
      responseSender.send();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }


  }//handle

  @Override
  public void close() {
    if (clientSocket != null && !clientSocket.isClosed()) {
      try {
        clientSocket.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

package kr.co.mz.singlethread.server;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import kr.co.mz.singlethread.repository.DTOHandler;
import kr.co.mz.singlethread.utils.database.DBConnector;
import kr.co.mz.singlethread.utils.http.RequestLineParser;
import kr.co.mz.singlethread.utils.http.ResponseGenerater;
import kr.co.mz.singlethread.utils.http.ResponseSender;

public class ClientSocket implements AutoCloseable {

  private final Socket socket;
  private final Cache cache;
  private final DBConnector dbConnector;

  public ClientSocket(Socket clientSocket, Cache cache, DBConnector dbConnector) {
    this.socket = clientSocket;
    this.cache = cache;
    this.dbConnector = dbConnector;
  }

  public void handleRequest() {

    try {// todo ??
      var dtoHandler = new DTOHandler(dbConnector);
      var responseGenerater = new ResponseGenerater(
          new RequestLineParser(socket.getInputStream()),
          cache,
          dtoHandler
      );
      var responseSender = new ResponseSender(socket.getOutputStream(), responseGenerater);
      responseSender.send();
    } catch (IOException ioe) {
      System.err.println("Failed to response" + ioe.getMessage());
    } catch (SQLException sqle) {
      System.err.println("Faild to connect to database." + sqle.getMessage());
    }
  }//handle

  @Override
  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
      System.err.println("Failed to close serversocket" + e.getMessage());
    }
    dbConnector.close();

  }// close
}

package kr.co.mz.singlethread.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.SQLException;
import kr.co.mz.singlethread.config.ConnectionProperties;
import kr.co.mz.singlethread.utils.database.DbConnectionFactory;
import kr.co.mz.singlethread.utils.http.ResponseSender;

public class STServer implements Closeable {

  private final Cache cache = new Cache();
  private final int port;
  //private String ip;
  private ServerSocket serverSocket;

  private Connection connection;

  private volatile boolean isServerRunning = true; // volatile 항상 주 메모리에서 값을 읽고 쓰겠다. 읽기쓰기만 보장. 가시+순서.

  public STServer(int port) {//매니저말고 소켓으로
    this.port = port;
    System.out.println("Server has been created.");
  }

  public void start() throws IOException, SQLException {
    System.out.println("Server has been started.");
    serverSocket = new ServerSocket(port);
    connection = DbConnectionFactory.createConnection(new ConnectionProperties());

    while (isServerRunning) {
      try (
          var clientSocket = serverSocket.accept();
          var stClientSocket = new ClientSocket(clientSocket, cache, connection);
      ) {
        stClientSocket.handleRequest();
      } catch (IOException | ArrayIndexOutOfBoundsException ioe) {
        System.out.println("Failed to connect: " + ioe.getMessage());
        new ResponseSender(serverSocket.accept().getOutputStream()).sendNotFound();
      } catch (SQLException sqle) {
        System.out.println("Database error occurred: " + sqle.getMessage());
      }
    }//while


  }

  @Override
  public void close() {
    System.out.println("Server is closing");
    isServerRunning = false;
    try {
      serverSocket.close();
    } catch (IOException e) {
      System.out.println("An error occurred when closeing the server: " + e.getMessage());
    }

  }
}

package kr.co.mz.singlethread.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;

public class STServer implements Closeable {

  private Cache cache;

  private final int port;
  //private String ip;
  private ServerSocket serverSocket;

  public STServer(int port) {//매니저말고 소켓으로
    this.port = port;
  }


  public void start() throws IOException {
    serverSocket = new ServerSocket(port);
    cache = new Cache();

//    try (var dbConnector = new DBConnector()) {
//      try {
//        dbConnector.getConnection();
//      } catch (SQLException e) {
//        System.out.println("Failed to connect to MySQL database");
//        e.printStackTrace();
//        return;
//      }

    while (true) {
      try (var clientSocket = serverSocket.accept();
          var stClientSocket = new ClientRequestHandler(clientSocket, cache);
      ) {
        stClientSocket.handle();//name

      } catch (IOException ioe) {
        System.out.println("Failed to connect");
      }
    }


  }

  @Override
  public void close() {
    if (serverSocket != null && !serverSocket.isClosed()) {

      try {
        serverSocket.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

package kr.co.mz.tutorial.httpserver.guide.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import kr.co.mz.tutorial.httpserver.guide.cache.Cache;

public class STServerSocket implements Closeable {

  private Cache cache;

  private final int port;
  //private String ip;
  private ServerSocket serverSocket;

  public STServerSocket(int port) {//매니저말고 소켓으로
    this.port = port;
  }


  public void start() throws IOException {
    serverSocket = new ServerSocket(port);
    cache = new Cache();

    while (true) {

      try (var clientSocket = serverSocket.accept();
          var clientSocketManager = new STClientSocket(clientSocket, cache);
      ) {
        clientSocketManager.handleRequest();//name

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

package kr.co.mz.tutorial.httpserver.guide.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import kr.co.mz.tutorial.httpserver.guide.cache.Cache;

public class ServerSocketManager implements Closeable {

  private Cache cache;

  private final int port;
  //private String ip;
  private ServerSocket serverSocket;

  public ServerSocketManager(int port) {//매니저말고 소켓으로
    this.port = port;
  }


  public void init() throws IOException {
    serverSocket = new ServerSocket(port);
    cache = new Cache();
  }

  public void start() {

    while (true) {

      try (var clientSocket = serverSocket.accept();
          var clientSocketManager = new ClientSocketManager(clientSocket, cache);
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
      } finally {
        System.out.println("Fail to close server socket");
      }
    }
  }
}

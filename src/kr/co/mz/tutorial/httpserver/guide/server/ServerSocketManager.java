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

  public ServerSocketManager(int port) {
    this.port = port;
  }


  public void create() throws IOException {
    serverSocket = new ServerSocket(port);
    cache = new Cache();
    System.out.println("Server has benn created.");
  }

  public void init() {
    if (serverSocket == null) {
      System.out.println("Server is not created. Create first.");
      return;
    }
    System.out.println("Server started.");
    while (true) {

      try (var clientSocket = serverSocket.accept();
          var clientSocketManager = new ClientSocketManager(clientSocket);
      ) {
        System.out.println("Client connected.");
        clientSocketManager.handleRequest(cache);

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

package kr.co.mz.tutorial.httpserver.guide.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import kr.co.mz.tutorial.httpserver.guide.cache.Cache;
import kr.co.mz.tutorial.httpserver.guide.httpserver.RequestLineParser;
import kr.co.mz.tutorial.httpserver.guide.httpserver.ResponseGenerater;
import kr.co.mz.tutorial.httpserver.guide.httpserver.ResponseSender;

public class STClientSocket implements Closeable {

  private final Socket clientSocket;
  private final Cache cache;

  public STClientSocket(Socket clientSocket,
      Cache cache) {// todo 매니저말고 소켓으로 그리고 얘가 하는일이 너무 많다.
    this.clientSocket = clientSocket;
    this.cache = cache;
  }

  public void handleRequest() throws IOException {
    var responseGenerater = new ResponseGenerater(
        new RequestLineParser(clientSocket.getInputStream()), cache);
    // todo 캐싱 작업 적재시키기.
    var responseSender = new ResponseSender(clientSocket.getOutputStream(), responseGenerater);
    responseSender.send();

  }//handle

  @Override
  public void close() {
    if (clientSocket != null && !clientSocket.isClosed()) {
      try {
        clientSocket.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        System.out.println("Fail to close client socket");
      }
    }
  }
}

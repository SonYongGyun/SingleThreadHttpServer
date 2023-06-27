package kr.co.mz.tutorial.httpserver.guide;

import java.io.IOException;
import kr.co.mz.tutorial.httpserver.guide.server.STServerSocket;

public class STHTTPSMain {


  public static void main(String[] args) {
    final int port = 8080;
// todo 공부했던것들을 사용해서 발전시켜나가라.
    try (
        var serverSocketManager = new STServerSocket(port);
    ) {
      serverSocketManager.start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }//main

}//Main

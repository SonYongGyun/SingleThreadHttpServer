package kr.co.mz.tutorial.httpserver.guide;

import java.io.IOException;
import kr.co.mz.tutorial.httpserver.guide.server.ServerSocketManager;

public class STSMain {


  public static void main(String[] args) {
    final int port = 8080;

    try (
        var serverSocketManager = new ServerSocketManager(port);

    ) {
      serverSocketManager.create();

      serverSocketManager.init();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }//main

}//Main

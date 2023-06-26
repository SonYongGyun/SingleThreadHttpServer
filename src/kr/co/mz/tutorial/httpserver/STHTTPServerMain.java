package kr.co.mz.tutorial.httpserver;

import java.io.IOException;

public class STHTTPServerMain {

  // 6/21 클라이언트 요청 시 html 파일 읽어와서 응답하는 기능 추가
  // 6/22 서버소켓 관리하는 객체 분리, 클라이언트 소켓의 첫번쨰 헤더를 분리해서, 메소드 방식과 url 요청을 구분, 파라미터가 있다면 파라미터도 가져 올 수 있게 만듦.
  public static void main(String[] args) throws IOException {
    //1. 서버 구축
    final int PORT = 8080;

    var singleThreadServerSocketManager = new SingleThreadServerSocketManager(PORT);

    singleThreadServerSocketManager.startServer();

  }//main


}// class

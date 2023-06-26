package kr.co.mz.tutorial.httpserver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SingleThreadServerSocketManager { //

  private final int port;
  private ServerSocket serverSocket;

  private final Map<String, String> cache = new HashMap<>();

  public SingleThreadServerSocketManager(int port) {
    this.port = port;
  }

  public void startServer() {
    try {

      serverSocket = new ServerSocket(port);
      System.out.println(port + " Server Started");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected");

        handleClientRequest(clientSocket);
        //모든 요청에 하나의 응답만 한다.
        //게다가 한번에 하나의 응답밖에 못하니까. 응답 다하기전에 걍 연결 끊어버림.

        // 1. 메인 페이지 갈떄는 하나의 요청만 하고싶다.
        // 2. 이미지는 캐싱하던가. 없는 html 로 가보자.
        // 3. request headers 의 첫번재꺼 짤라서 분기 시켜주기.

//        try {
//          Thread.sleep(6000);

        System.out.println("Client closed");
        clientSocket.close();
        //serverSocket.close();
        // solved  뭘 닫는거임..?. 1. 닫히지 않아서 계속 응답대기중,
//          // 2. 여기서 닫아버리면 2번째 요청을 못받아서 소켓 닫혔다고 걍 꺼져버림.
//      (InterruptedException e) {// 얜또 뭘까?

      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (NotHTMLFileException e) {
      throw new RuntimeException(e);
    }
  }

  private void handleClientRequest(Socket clientSocket)
      throws IOException, NotHTMLFileException { // 캐싱과 헤더 슬라이서, 응답제작자 부름.
    String response = ""; // 최종 응답.

//    //1. 헤더 읽기.
    HeaderSlicer headerSlicer = new HeaderSlicer(clientSocket);

    //2. 응답 쓰기
    BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(clientSocket.getOutputStream())
    );

    //3. HTTP 헤더 읽기
    String slicedHttpMethod = headerSlicer.getMethod();
    String slicedURL = headerSlicer.getURL();

    // 시나리오 1.  /home. /home/test 를 여기서 잘라서 보낸다.
    ResponseMaker responseMaker = new ResponseMaker(slicedHttpMethod, slicedURL);
    // 시나리오 2. /home 파일을 읽어온걸 String 으로 반환한다.
    // null 이 아니면 반환값을 캐싱한다.
    String responseBody = "";
    responseBody = responseMaker.makeHtmlBody();


    /*------------------------ url 에 따른 응답 분기. html or favicon or download

      System.out.println(slicedURL);
    String suffix1 = "l";
    String suffix2 = "o";
    if (slicedURL.split("")[slicedURL.length() - 1].equals("l")) {
    } else if (slicedURL.split("")[slicedURL.length() - 1].equals("o")) {
      responseBody = responseMaker.makeFavicon().toString();
    }
    */
//-----------------------

    if (cache.containsKey(slicedURL)) {
      response = cache.get(slicedURL);

      System.out.println("Cache read");
    } else {

      if (responseBody != null) {
        cache.put(slicedURL, responseMaker.makeHtmlBody());
        System.out.println("Cached Body just created");
      }

      response = responseMaker.createHttpResponse(responseBody);
//      System.out.println(response);
      cache.put(slicedURL, response);
    }

    // 4. 응답 만들기. 새로나는 이거때문에 생기는거였나? 왜이런거지?
/*
캐싱할꺼면  if else 로 분기 해준다.
응답 헤더들은 텍스트파일로 관리한다.
 */

    //5. 만든 응답을 클라이언트 소켓에 붙여주기.
    writer.write(response);
    //6. 붙여준 응답을 내보내면서 없애기.
    writer.flush();
  }// handle2


  public void stopServer() {
    try {
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
        System.out.println("서버가 종료되었습니다.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}//class

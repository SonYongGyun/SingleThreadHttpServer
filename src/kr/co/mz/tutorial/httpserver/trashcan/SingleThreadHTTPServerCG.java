package kr.co.mz.tutorial.httpserver.trashcan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import kr.co.mz.tutorial.httpserver.NotHTMLFileException;


public class SingleThreadHTTPServerCG {

  public static final String TEST_HTMLDIRECTORY = "/Users/mz01-omega/Desktop";

  public static void main(String[] args) {
    final int PORT = 8080;

    try {
      ServerSocket serverSocket = new ServerSocket(PORT);// 지정된 포트에서 접속을 기다림.
      System.out.println("서버가 시작되었습니다. 포트: " + PORT);

      while (true) {
        Socket clientSocket = serverSocket.accept(); // 클라이언트가 서버에 접속하면 accept메소드로 클라이언트와의 연결 수락
        System.out.println("클라이언트가 연결되었습니다.");

        // 클라이언트 요청 처리
        handleClientRequest(clientSocket);

        clientSocket.close();
        System.out.println("클라이언트와의 연결이 종료되었습니다.");
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (NotHTMLFileException e) {
      throw new RuntimeException(e);
    }
  }

  private static void handleClientRequest(Socket clientSocket)
      throws IOException, NotHTMLFileException {// 클라이언트의 소캣 객체를 받아서.

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));//클라이언트의 요청

    BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(clientSocket.getOutputStream())
    );

//    // HTTP 요청 읽기
//    String requestLine = reader.readLine();
//    System.out.println("클라이언트 요청: " + requestLine);
//    // HTTP 요청 읽기2
    String requestLine = reader.readLine();
    System.out.println("클라요청" + requestLine);

    String readRequest = "";
    while ((readRequest = reader.readLine()) != null && !readRequest.isEmpty()) {
      System.out.println("헤더필드" + readRequest);
    }
    // 이거 크롬에서 raw 눌러서 보면 왜 이렇게 한줄씩 읽는지 알 수 있다.
    // request headers 에 아래처럼 입력되어있다.
    /*
    GET / HTTP/1.1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*//*;q=0.8,application/signed-exchange;v=b3;q=0.7
//    Accept-Encoding: gzip, deflate, br
//    Accept-Language: ko,ko-KR;q=0.9,en-US;q=0.8,en;q=0.7
//    Cache-Control: max-age=0
//    Connection: keep-alive
//    Host: localhost:8080
//    Sec-Fetch-Dest: document
//    Sec-Fetch-Mode: navigate
//    Sec-Fetch-Site: none
//    Sec-Fetch-User: ?1
//    Upgrade-Insecure-Requests: 1
//    User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36
//    sec-ch-ua: "Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114"
//    sec-ch-ua-mobile: ?0
//    sec-ch-ua-platform: "macOS"
      */

    // HTTP 응답 작성
    //String responseBody = "<html><body><h1>Hello, Client!</h1></body></html>";// html이 포함되는 body 부분만 명시하는 것.

    String response = // 헤더가 아니라. 전체 응답을 말하는 것임.
        "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n" +
            "Cache-Control: max-age = 15\r\n" + // 초단위
            "\r\n";
    // 응답 헤더와 본문 사이에 빈 줄을 나타냄. 이는 응답에서 헤더와 본문을 구분하는 구분자.
    // 이걸 해 줌으로써 html 이 포함된 전체응답이 날라감.
    writer.write(response);//데이터를 출력 스트림에 씀.
    writer.write(getResponseBody(TEST_HTMLDIRECTORY));
    writer.flush();// 출력스트림의 내부 버ㄹ플 출력하고 차단.

  }

  private static String getResponseBody(String htmlDirectory)
      throws NotHTMLFileException, IOException { // 6/21에 할거.
    //1. 데이터검증.
    File htmlFile = new File("/Users/mz01-omega/Desktop/test.txt");
    if (!htmlFile.exists() || htmlFile.isDirectory() || !htmlFile.isFile()) {
      throw new NotHTMLFileException("Maybe, this is not a HTML File" + htmlFile);
    }

//    byte[] buffer = new byt   e[4096];
//    int cnt;
    // 파일을 읽어오는 부분이 잘못되었다 라고 한다.
    BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(new FileInputStream(htmlFile), StandardCharsets.UTF_8));

    StringBuilder stringBuilder = new StringBuilder();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line);
    }

    String htmlContent = stringBuilder.toString();
    System.out.println(htmlContent);
    return htmlContent;
  }// getResponseBody
}
/*
싱글 스레드 서버 코드가 실행되면, ServerSocket을 사용하여 지정된 포트에서 클라이언트의 접속을 기다립니다.
 클라이언트가 서버에 접속하면 serverSocket.accept() 메서드가 호출되어 클라이언트와의 연결을 수락하고, 이후 코드가 실행됩니다.

클라이언트와의 연결이 수락되면, handleClientRequest(Socket clientSocket) 메서드가 호출되어 클라이언트의 요청을 처리합니다.
해당 메서드에서는 클라이언트로부터 Socket 객체를 받아서 소켓의 InputStream을 통해 클라이언트의 요청을 읽고, 소켓의 OutputStream을 통해 응답을 전송합니다.

클라이언트가 웹 브라우저로 localhost:8080에 접속하면, 싱글 스레드 서버는 클라이언트의 요청을 수신합니다.
클라이언트의 요청은 HTTP 프로토콜에 따라 구성되며, 요청 메서드(GET, POST 등)와 URL 등의 정보를 포함합니다.
싱글 스레드 서버는 해당 요청을 읽어서 처리하고, 응답으로 "Hello, Client!"라는 내용을 포함한 HTTP 응답을 생성합니다.

웹 브라우저는 해당 응답을 받으면, 응답 내용을 렌더링하여 사용자에게 보여줍니다.
 따라서 웹 브라우저에서 localhost:8080에 접속하면 "Hello, Client!"라는 응답을 받게 되는 것입니다.

이 과정에서는 HTTP 프로토콜을 기반으로 클라이언트와 서버 간의 요청과 응답이 이루어집니다.
싱글 스레드 서버는 클라이언트의 요청을 순차적으로 처리하며, 한 번에 하나의 요청만 처리할 수 있기 때문에 동시에 여러 클라이언트의 요청을 처리하지는 않습니다.
 */
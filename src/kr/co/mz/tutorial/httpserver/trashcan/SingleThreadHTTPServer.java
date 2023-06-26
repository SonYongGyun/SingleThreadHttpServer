//package kr.co.mz.tutorial.httpserver;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class SingleThreadHTTPServer {
//
//  public static void main(String[] args) {
//    final int PORT = 8080;// 서버의 접속 은 어디로 찾아가야해?
//
//    try {
//      ServerSocket serverSocket = new ServerSocket(
//          PORT);// 서버를 만들기 위해선 서버에 접근 할 수 있는 포트가 필요 할 것 같아. 이 서버 소켓이란건 앱 밖의 물건을 건드리는거 같아.
//      // 예외처리를 해주자.
//      while (true) {// 서버가 클라이언트의 연결을 지속적으로 받기 위함이야. 이렇게 되면 탈출 경로가 필요 할 거같은데... 나는 한번만 받으면 되는데 왜 열어놔야할까?
//        // 연결을 받아야 하니까 클라이언트의 연결을 받을 녀석이 필요할 거 같아. 뭐로받지?
//        Socket clientSocket = serverSocket.accept(); //--> 1.지정된 포트에서 연결을 기다림 2. 클라이언트의 연결 시도는 해당 연결을 수락
//        // 3. 연결 수락 후, 통신에 사용할 소캣 객체를 새로만듦 4. 메소드는 소캣 객체를 반환하고 이를 통해 통신.
//
//        // 소캣으로 받고나서는. 뭐?  통신을 해야겠어. 어떻게? 일단 메소드 만들어.
//        handleClientRequest(clientSocket);// 그러고는 통신을 위해 소켓을 받아야한대.
//      }
//
//    } catch (IOException e) {
//      throw new RuntimeException(e); // 얜또 런타임 익셉션이고 ㅎ
//    }
//
//  }//main
//
//  private static void handleClientRequest(
//      Socket clientSocket) {//뭐.. 소켓받는게 하나밖에없으니깐...? 소캣으 로 받아서 뭘 하겠지. =>
////        //1. 스트림으로 요청을 받는대.
////        InputStream request = null;
////        // 그럼 응답도 해야지.
////        OutputStream response = null;
////
////        try(//File IO랑 같다고 했으니깐. 일단 twr 로 하겠지?
////            request =
////                )  와! 전혀 아니에요!
//    //1. 요청을 가진 객체에서 요청인 인풋스트림을 받아온다. 이걸 읽는 객체를 만들어주고 이걸 bufferedReader에 넣어서 효율적으로 읽게 한다... 뭐이런건가?
//    BufferedReader requestReader = new BufferedReader(
//        new InputStreamReader(
//            clientSocket.getInputStream())); // 보낸 요청을 효율적으로 처리하기 위한놈. 1. 클라이언트와 연결된 소켓의 입력 스트림을 받음.
//    // 그래서 이에 맞는 응답을 해야겠지..
//    // OutputStream responseStreamWriter = new OutputStream() { 와 전혀 아니에요!
//    OutputStream responseStreamWriter = clientSocket.getOutputStream(); // 클라이언트 소캣님 이거 가져가세요~ 가져가는 역할을 하는놈.
//
//    // HTTP 요청을 읽으래. 어떻게..?
//    String requestLine = requestReader.readLine();// 이렇게 그냥 읽는거야..? 그래..
//    System.out.println("어떤 요청인지 한번 보자" + requestLine);
//
//    // HTTP 응답을 하래... 뭐어쩌라고 ㅎ
//    String response =
//        //HttpHeaders.of()// 뭔시발 헤더가 몇개나 필요한데 ㅎ 어케쓰는지 하나도 모르겠다.
//        "HTTP/1.1 200 OK\r\n" +
//            "Content-Type: text/plain\r\n" +
//            "\r\n" +
//            "Hello, Client!";
//    // 응답 작성 했어. 이제뭐 ?
//    //그래.. 작성한 응답을 우리 응애풋 스트림에 넣어줘야지...
//    responseStreamWriter.write(response.getBytes("UTF-8"));
//    responseStreamWriter.flush();//왜쓰는지도 모르고~ 어휴~
//  }//handleCR
//
//}
///*
//클라이언트-서버 관계에서 "single thread"는 한 번에 하나의 요청만 처리하는 방식을 의미합니다.
// 이는 서버가 동시에 여러 클라이언트 요청을 처리하지 않고, 순차적으로 하나의 요청에 대해서만 작업을 수행하는 것을 의미합니다.
//
//일반적으로 클라이언트-서버 시나리오에서는 클라이언트가 서버에 요청을 보내고, 서버는 해당 요청을 받아서 처리하는 역할을 합니다.
// 이때, 서버가 single thread로 동작한다면, 한 번에 하나의 클라이언트 요청에 대해서만 처리를 수행하며, 다른 클라이언트의 요청은 대기 상태에 있게 됩니다.
// 클라이언트의 요청 처리가 완료되면 다음 클라이언트 요청을 처리하는 순서로 진행됩니다.
//
//이러한 single thread 방식은 간단하고 구현이 쉽지만, 여러 클라이언트의 동시 요청을 처리하는 능력이 제한적입니다.
// 따라서 클라이언트 수가 많거나 처리해야 할 작업이 복잡하다면, 서버의 성능이 저하될 수 있습니다.
// 이러한 이유로 대규모 시스템에서는 multi-thread 또는 multi-process 방식을 사용하여 동시에 여러 클라이언트 요청을 처리하는 방식을 선호합니다.
//
//
// */
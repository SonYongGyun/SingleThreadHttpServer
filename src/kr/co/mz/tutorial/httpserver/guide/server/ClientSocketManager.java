package kr.co.mz.tutorial.httpserver.guide.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import kr.co.mz.tutorial.httpserver.guide.cache.Cache;
import kr.co.mz.tutorial.httpserver.guide.httpserver.request.RequestLineParser;
import kr.co.mz.tutorial.httpserver.guide.httpserver.response.ResponseGenerater;

public class ClientSocketManager implements Closeable {

  private final Socket clientSocket;
  private final Cache cache;

  public ClientSocketManager(Socket clientSocket,
      Cache cache) {// todo 매니저말고 소켓으로 그리고 얘가 하는일이 너무 많다.
    this.clientSocket = clientSocket;
    this.cache = cache;
  }

  public void handleRequest() throws IOException {
    // 소캣에서 요청 추출, 요청전달, 응답 생성

    //1.헤더 읽기
    //---------- todo 한줄 한번에 던지기. 아니면 아웃풋스트림 하나 던지던가.
    var requestLineParser = new RequestLineParser(clientSocket.getInputStream());
    requestLineParser.parse();

    var requestMethod = requestLineParser.getRequestMethod();
    var requestURI = requestLineParser.getRequestURI();
    var requestParameters = requestLineParser.getRequestParameters();
//------------------

    byte[] response = cache.get(requestURI);

    if (response == null) {
      response = new ResponseGenerater(requestMethod, requestURI,
          requestParameters).createHttpResponse();
      cache.put(requestURI, response);
    }

    //2. 응답 쓰기.
    OutputStream outputStream = clientSocket.getOutputStream(); //todo divideroll 지금은세세하게.

    outputStream.write(response);

    outputStream.flush();
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

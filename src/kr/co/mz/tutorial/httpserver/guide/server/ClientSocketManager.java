package kr.co.mz.tutorial.httpserver.guide.server;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import kr.co.mz.tutorial.httpserver.guide.cache.Cache;
import kr.co.mz.tutorial.httpserver.guide.httpserver.request.RequestLineParser;
import kr.co.mz.tutorial.httpserver.guide.httpserver.response.ResponseGenerater;

public class ClientSocketManager implements Closeable {

  private Socket clientSocket;

  public ClientSocketManager(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void handleRequest(Cache cache) throws IOException {
    // 소캣에서 요청 추출, 요청전달, 응답 생성

    //1.헤더 읽기
    var reader = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
//    String line;
//    while ((line = reader.readLine()) != null) { // 다 읽으면 리더사망.
//      // 읽은 문자열 처리  이까진 잘 온다.
//      System.out.println(line + "?");
//    }

    var requestLine = reader.readLine();

    var requestLineParser = new RequestLineParser(requestLine);
    requestLineParser.doParse();

    var requsetMethod = requestLineParser.getRequestMethod();
    var requsetUri = requestLineParser.getRequestURI();
    var requsetParameters = requestLineParser.getRequestParameters();

    //받아온 캐시와 비교해서 답해야함.
    var responseGenerater = new ResponseGenerater(requsetMethod, requsetUri, requsetParameters);
    if (!cache.getCache(requsetUri).isEmpty()) {

    }

    //2. 응답 쓰기.
    OutputStream outputStream = clientSocket.getOutputStream();
    outputStream.write(
        responseGenerater.createHttpResponse()
    );
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

package kr.co.mz.tutorial.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HeaderSlicer {

  // 클라소켓 먹어서, 요청을 분할해서 다시 뱉어내는 녀석.
  private String[] requsetHeader;

  private BufferedReader reader;

  //1. 헤더 읽기.
  public HeaderSlicer(Socket clientSocket) throws IOException {
    this.reader = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
    );

    String headerLine = reader.readLine();
    if (headerLine != null) { // null 검증
      this.requsetHeader = headerLine.split(" ");
    }
  }

  private boolean isValidHeader() {
    return requsetHeader != null && requsetHeader.length >= 2;
  }

  public String getMethod() {
    if (isValidHeader()) {
      return requsetHeader[0]; // 요청 메소드 반환
    }
    return null; // 요청 메소드를 찾지 못한 경우
  }

  public String getURL() {
    if (isValidHeader()) {
      String[] uri = requsetHeader[1].split("\\?");

      return uri[0]; // URI 반환
    }
    return null; // URI를 찾지 못한 경우
  }

  public String getParams() {
    if (isValidHeader()) {
      String[] uri = requsetHeader[1].split("\\?");
      if (uri.length >= 2) {
        return uri[1]; // 파라미터 반환
      }
    }
    return null; // 파라미터를 찾지 못한 경우
  }
}

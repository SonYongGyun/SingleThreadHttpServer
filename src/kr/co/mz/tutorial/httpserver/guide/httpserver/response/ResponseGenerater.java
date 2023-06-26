package kr.co.mz.tutorial.httpserver.guide.httpserver.response;

import java.io.FileNotFoundException;
import java.util.Map;
import kr.co.mz.tutorial.httpserver.guide.util.FileReader;

public class ResponseGenerater {


  private String requestMethod;
  private String requestURI;
  private Map<String, String> requestParameters;

  public ResponseGenerater(String requestMethod, String requestURI,
      Map<String, String> requestParameters) {
    this.requestMethod = requestMethod;
    this.requestURI = requestURI;
    this.requestParameters = requestParameters;
  }

  public byte[] createHttpResponse() throws FileNotFoundException {

    // HTML 파일 내용 읽어오기
    FileReader fileReader = new FileReader();
    // 파일이 존재하는 경우 HTTP 200 OK 응답 생성
    byte[] responseBody = fileReader.readHtmlFile(requestURI);

    String responseWithHeaders;

    if (responseBody != null) {
      responseWithHeaders = "HTTP/1.1 200 OK\r\n";
      responseWithHeaders += "Content-Type: text/html; charset=utf-8\r\n";
      responseWithHeaders += "Content-Length: " + responseBody.length + "\r\n";
      responseWithHeaders += "\r\n";
    } else {
      // 파일이 존재하지 않는 경우 404 Not Found 응답 생성
      return createNotFoundResponse();
    }

    return responseWithHeaders.getBytes();

  }//createResponse

  private static byte[] createNotFoundResponse() {
    String response = "HTTP/1.1 404 Not Found\r\n";
    response += "Content-Type: text/plain; charset=utf-8\r\n";
    response += "Content-Length: 9\r\n";
    response += "\r\n";
    response += "Not Found";

    return response.getBytes();
  }
}//class

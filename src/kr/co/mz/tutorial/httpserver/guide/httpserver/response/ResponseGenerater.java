package kr.co.mz.tutorial.httpserver.guide.httpserver.response;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
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
    FileReader fileReader = new FileReader(requestURI);
    // 파일이 존재하는 경우 HTTP 200 OK 응답 생성
    byte[] responseBodyBytes = fileReader.readFile(requestURI);
    if (requestURI.equals("/favicon.ico")) {
      System.out.println(responseBodyBytes);
    }
    
    long resBodyLength = responseBodyBytes.length;
    String basicHeaders = "HTTP/1.1 200 OK\r\n";
    basicHeaders += "Content-Type: text/html; charset=utf-8\r\n";// New
    basicHeaders += "Content-Length: " + resBodyLength + "\r\n"; // New
    basicHeaders += "\r\n";

    byte[] responseHeadersBytes = basicHeaders.getBytes(StandardCharsets.UTF_8);

    if (responseBodyBytes.length != 0) {
//------------------------------------------------------
// 얘네가 여기있으면 안되지......
      // 구분할게 엄청 많은데 어떤식으로 구분해야하는지?
      // 응답 헤더를 바이트로 변환
      if (requestURI.endsWith(".html")) {
        responseHeadersBytes = createHtmlResponseHeaders(requestURI,
            responseBodyBytes.length);
      }
      if (requestURI.endsWith(".ico")) {
        responseHeadersBytes = createIconResponseHeaders(requestURI,
            responseBodyBytes.length);
      }
//------------------------------------------------------

      // New 응답 헤더와 바디를 합쳐서 전송할 바이트 배열 생성
      byte[] responseBytes = new byte[responseHeadersBytes.length + responseBodyBytes.length];
      System.arraycopy(responseHeadersBytes, 0, responseBytes, 0, responseHeadersBytes.length);
      System.arraycopy(responseBodyBytes, 0, responseBytes, responseHeadersBytes.length,
          responseBodyBytes.length);

      // 응답 전송 로직 추가
      // ...

      return responseBytes;

    } else {
      // 파일이 존재하지 않는 경우 404 Not Found 응답 생성
      return createNotFoundResponse();
    }


  }//createResponse

  private byte[] createHtmlResponseHeaders(String requestUrl, long resBodyLength) {
    byte[] responseHeadersBytes = null;
    String responseHeaders;

    if (requestUrl != null) {
      responseHeaders = "HTTP/1.1 200 OK\r\n";
      responseHeaders += "Content-Type: text/html; charset=utf-8\r\n";// New
      responseHeaders += "Content-Length: " + resBodyLength + "\r\n"; // New
      responseHeaders += "\r\n";

      // 응답 헤더를 바이트로 변환
      responseHeadersBytes = responseHeaders.getBytes(StandardCharsets.UTF_8);
    }
    return responseHeadersBytes;
  }

  private byte[] createIconResponseHeaders(String reqestUri, long resBodyLength) {
    String responseHeaders = "HTTP/1.1 200 OK\r\n";
    responseHeaders += "Content-Type: image/x-icon\r\n";
    responseHeaders += "Content-Length: " + resBodyLength + "\r\n";
    responseHeaders += "\r\n";

    return responseHeaders.getBytes(StandardCharsets.UTF_8);

  }

  private byte[] createImgResponseHeaders() {
    return null;
  }

  private static byte[] createNotFoundResponse() {
    String response = "HTTP/1.1 404 Not Found\r\n";
    response += "Content-Type: text/plain; charset=utf-8\r\n";
    response += "Content-Length: 9\r\n";
    response += "\r\n";
    response += "Not Found";

    return response.getBytes();
  }
}//class

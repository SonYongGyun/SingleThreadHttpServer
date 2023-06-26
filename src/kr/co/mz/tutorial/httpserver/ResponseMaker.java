//1. 어떤 요청을 String 으로 받아오는데 구분 할 수 있어야함.
// 1-1. 일단은 home, test, test2, testdown, test2down 총 5개의 html 파일을 읽어와서 입력한 String 에 맞게 http 응답을 생성해서 리턴 해 주려고함.
package kr.co.mz.tutorial.httpserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResponseMaker {

  // HTML 파일들의 경로
  private static final String PROJECT_DIRECTORY = System.getProperty("user.dir"); // 사용 가능 ? 보안?
  private final String requestUrl;
  private String requestMethod;

  public ResponseMaker(String requestMethod, String requestUrl) {
    this.requestMethod = requestMethod;
    this.requestUrl = PROJECT_DIRECTORY + "/src/resources/html" + requestUrl;
  }

  // 요청에 대한 HTTP 응답 생성
  public String createHttpResponse(String responseBody) {
    String responseWithHeaders;

    // HTML 파일 내용 읽어오기

    // 파일이 존재하는 경우 HTTP 200 OK 응답 생성
    if (responseBody != null) {
      responseWithHeaders = "HTTP/1.1 200 OK\r\n";
      responseWithHeaders += "Content-Type: text/html; charset=utf-8\r\n";
//      responseWithHeaders += "Content-Length: " + fileContent.length() + "\r\n";
      responseWithHeaders += "\r\n";
      responseWithHeaders += responseBody;
    } else {
      // 파일이 존재하지 않는 경우 404 Not Found 응답 생성
      responseWithHeaders = createNotFoundResponse();
    }

    return responseWithHeaders;
  }// create repsonse


  // HTML 파일 읽어오기
  public String makeHtmlBody() throws NotHTMLFileException {
    if (!requestUrl.contains(".html")) {
      throw new NotHTMLFileException("Wrong Request." + requestUrl);
    }
    var sourceHtml = new File(requestUrl);
//    System.out.println(sourceHtml);
    if (!sourceHtml.exists() || sourceHtml.isDirectory() || !sourceHtml.isFile()) {
      return createNotFoundResponse();

//      throw new NotHTMLFileException("이거 아냐" + sourceHtml.getAbsolutePath());
    }
    try (
        var bufferedFileReader = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(sourceHtml), StandardCharsets.UTF_8), 4096
        )

    ) {
      StringBuilder stringBuilder = new StringBuilder();
      char[] buffer = new char[4096];
      int readChars;
      while ((readChars = bufferedFileReader.read(buffer)) != -1) {
        stringBuilder.append(buffer, 0, readChars);
      }
//      System.out.println(stringBuilder); 너무 많아서 이제 못띄움. 대신 맞는 charset 잘 설정하기.
      return stringBuilder.toString();
    } catch (IOException e) {
      // Handle any IO exceptions
      e.printStackTrace();
      return null;
    }
  }//get Rbody

  public byte[] makeFaviconBody() throws IOException, NotHTMLFileException {
    File favicon = new File(requestUrl);
    if (!favicon.exists() || favicon.isDirectory() || !favicon.isFile()) {
      throw new NotHTMLFileException("이거 아냐" + favicon.getPath());
    }

    if (!favicon.exists()) {
      // 404 found 처리
      return new byte[]{};
    }

    try (FileInputStream inputStream = new FileInputStream(favicon)) {
      int fileSize = (int) favicon.length();
      byte[] fileData = new byte[fileSize]; // 바이트 배열 받은걸 어떻게 붙여서 큰 배열로 보내고 그걸 처리하는지 공부해오기.
      // 그리고 파일 읽는놈을 전문적으로 두면 이렇게 responseMaker 가 고생하지 않아도 된다.
      int bytesRead = inputStream.read(fileData, 0, fileSize);
      if (bytesRead == fileSize) {
        return fileData;
      } else {
        throw new IOException("Failed to read the complete favicon file.");
      }
    }
  }

  public String downloadRsponse() throws IOException {
    File faviconFile = new File(requestUrl);
    String downloadName = "test.txt";
    byte[] fileData;

    try (
        InputStream inputStream = new FileInputStream(faviconFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
    ) {
      byte[] buffer = new byte[4096];
      int byteRead;

      while ((byteRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, byteRead);
      }
      fileData = outputStream.toByteArray();
    }

    String contentType = "application/octet-stream"; // 다운로드할 파일의 MIME 타입
    long contentLength = fileData.length;

    // HTTP 응답 헤더 작성
    String responseHeader = "HTTP/1.1 200 OK\r\n";
    responseHeader += "Content-Type: " + contentType + "\r\n";
    responseHeader += "Content-Length: " + contentLength + "\r\n";
    responseHeader += "Content-Disposition: attachment; filename=\"" + downloadName + "\"\r\n";
    responseHeader += "\r\n";

    return null;
  }

  // 404 Not Found 응답 생성
  private static String createNotFoundResponse() {
    String response = "HTTP/1.1 404 Not Found\r\n";
    response += "Content-Type: text/plain; charset=utf-8\r\n";
    response += "Content-Length: 9\r\n";
    response += "\r\n";
    response += "Not Found";

    return response;
  }
}

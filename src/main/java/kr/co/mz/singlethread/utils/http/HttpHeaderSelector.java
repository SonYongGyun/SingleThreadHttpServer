package kr.co.mz.singlethread.utils.http;

public class HttpHeaderSelector {

  public String getHeaders(String extension,
      int resBodyLength) {// todo 강화된 switch문
    return switch (extension) {
      case "html" -> getHtmlHeaders(resBodyLength);
      case "ico" -> getFaviconHeaders(resBodyLength);
      default -> notFoundHeaders();
    };
  }


  private String getHtmlHeaders(int resBodyLength) {
    String responseHeaders = "HTTP/1.1 200 OK\r\n";
    responseHeaders += "Content-Type: text/html; charset=utf-8\r\n";// New
    responseHeaders += "Content-Length: " + resBodyLength + "\r\n";
    responseHeaders += "\r\n";
    return responseHeaders;
  }

  private String getFaviconHeaders(int resBodyLength) {
    String responseHeaders = "HTTP/1.1 200 OK\r\n";
    responseHeaders += "Content-Type: image/x-icon\r\n";
    responseHeaders += "Content-Length: " + resBodyLength + "\r\n";
    responseHeaders += "\r\n";
    return responseHeaders;
  }

  private String getImgHeaders() {
    return "building";
  }

  public String notFoundHeaders() {
    String responseHeaders = "HTTP/1.1 404 Not Found\r\n";
    responseHeaders += "Content-Type: text/plain; charset=utf-8\r\n";
    responseHeaders += "Content-Length: 9\r\n";
    responseHeaders += "\r\n";
    return responseHeaders;
  }

}

package kr.co.mz.singlethread.utils.http;

public class HttpHeaderSelector {

  public String getHeaders(String extension,
      int resBodyLength) {// todo 강화된 switch문
    return switch (extension) {
      case "html" -> getHtmlHeaders(resBodyLength);
      case "ico" -> getFaviconHeaders(resBodyLength);
      case "jpg", "jpeg", "png" -> getImgHeaders(resBodyLength);
      default -> notFoundHeaders();
    };
  }


  private String getHtmlHeaders(int resBodyLength) {
    return "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/html; charset=utf-8\r\n" +
        "Content-Length: " + resBodyLength + "\r\n" +
        "\r\n";
  }


  private String getFaviconHeaders(int resBodyLength) {
    return "HTTP/1.1 200 OK\r\n" +
        "Content-Type: image/x-icon\r\n" +
        "Content-Length: " + resBodyLength + "\r\n" +
        "\r\n";
  }

  private String getImgHeaders(int resBodyLength) {
    return "HTTP/1.1 200 OK\r\n" +
        "Content-Type: image/jpeg\r\n" +
        "Content-Length: " + resBodyLength + "\r\n" +
        "\r\n";
  }

  public String notFoundHeaders() {
    return "HTTP/1.1 404 Not Found\r\n" +
        "Content-Type: text/plain; charset=utf-8\r\n" +
        "Content-Length: 9\r\n" +
        "\r\n";
  }

}

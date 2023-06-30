package kr.co.mz.singlethread.utils.http;

import java.net.MalformedURLException;
import java.net.URL;

public class ParsedRequest {

  private final String method;
  private final URL url;
  private final String version;

  private final String uriExtension;

  private final boolean cacheDtoRequest;

  public ParsedRequest(String requestLine) throws MalformedURLException {
    var splitRequestLine = requestLine.split(" ");
    method = splitRequestLine[0];
    url = new URL(splitRequestLine[1]);
    version = splitRequestLine[2];

    if (url.getPath().startsWith("/cache")) {// 요청마다 다를 수도 있어서?
      cacheDtoRequest = true;
    } else {
      cacheDtoRequest = false;
    }
    String[] uriParts = url.getFile().split("\\.");
    uriExtension = uriParts[1];
   
  }


  public String getMethod() {
    return method;
  }

  public URL getUrl() {
    return url;
  }

  public String getVersion() {
    return version;
  }

  public String getProtocol() {
    return url.getProtocol();
  }

  public String getPath() {
    return url.getPath();
  }

  public int getPort() {
    return url.getPort();
  }

  public String getQuery() {
    return url.getQuery();
  }

  public String getUriExtension() {
    return uriExtension;
  }

  public boolean isCacheDtoRequest() {
    return cacheDtoRequest;
  }

}


package kr.co.mz.tutorial.httpserver.guide.httpserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import kr.co.mz.tutorial.httpserver.guide.cache.Cache;
import kr.co.mz.tutorial.httpserver.guide.util.file.FileReader;
import kr.co.mz.tutorial.httpserver.guide.util.jdbc.DBConnector;

public class ResponseGenerater {


  private final String requestMethod;
  private final String requestURI;
  private final Map<String, String> requestParameters;
  private final Cache cache;
  private final HttpHeaderSelector httpHeaderSelector = new HttpHeaderSelector();
  private final String uriExtension;
  private final DBConnector dbConnector;

  public ResponseGenerater(RequestLineParser requestLineParser, Cache cache,
      DBConnector dbConnector) throws IOException {
    requestLineParser.parse();
    this.requestMethod = requestLineParser.getRequestMethod();
    this.requestURI = requestLineParser.getRequestURI();
    this.requestParameters = requestLineParser.getRequestParameters();
    this.uriExtension = requestLineParser.getUriExtension();
    this.cache = cache;
    this.dbConnector = dbConnector;
  }

  public byte[] getOrGenerateResponse() {
    //일단 cache 로 들어오면 jdbc 와 연결되고, 거기있는 데이터를 가져와서 바이트로 돌려주는 코드를 만들어보자.
    if (requestURI.startsWith("/cache")) {

    }

    var responseBodyBytes = cache.get(requestURI);

    if (responseBodyBytes.length == 0) {
      responseBodyBytes = new FileReader(requestURI)
          .readFile(requestURI);
      cache.put(requestURI, responseBodyBytes);
    }
    var responseHeaders = httpHeaderSelector.getHeaders(uriExtension, responseBodyBytes.length);

    var responseHeadersBytes = responseHeaders.getBytes(StandardCharsets.UTF_8);

    var responseBytes = new byte[responseHeadersBytes.length
        + responseBodyBytes.length];// todo singlethread 라 shallow copy?
    System.arraycopy(responseHeadersBytes, 0, responseBytes, 0, responseHeadersBytes.length);
    System.arraycopy(responseBodyBytes, 0, responseBytes, responseHeadersBytes.length,
        responseBodyBytes.length);

    return responseBytes;

  }//createResponse

}//class

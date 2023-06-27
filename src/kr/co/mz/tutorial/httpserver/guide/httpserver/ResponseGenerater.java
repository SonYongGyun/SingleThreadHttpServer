package kr.co.mz.tutorial.httpserver.guide.httpserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import kr.co.mz.tutorial.httpserver.guide.cache.Cache;
import kr.co.mz.tutorial.httpserver.guide.util.FileReader;

public class ResponseGenerater {


  private final String requestMethod;
  private final String requestURI;
  private final Map<String, String> requestParameters;
  private final Cache cache;
  private final HttpHeaderSelector httpHeaderSelector = new HttpHeaderSelector();
  private final String uriExtension;

  public ResponseGenerater(RequestLineParser requestLineParser, Cache cache) throws IOException {
    requestLineParser.parse();
    this.requestMethod = requestLineParser.getRequestMethod();
    this.requestURI = requestLineParser.getRequestURI();
    this.requestParameters = requestLineParser.getRequestParameters();
    this.uriExtension = requestLineParser.getUriExtension();
    this.cache = cache;
  }

  public byte[] getOrGenerateResponse() {

    var fileReader = new FileReader(requestURI);
    var responseBodyBytes = fileReader.readFile(requestURI);
    var basicHeaders = httpHeaderSelector.getHeaders(uriExtension, responseBodyBytes.length);

    var responseHeadersBytes = basicHeaders.getBytes(StandardCharsets.UTF_8);

    var responseBytes = new byte[responseHeadersBytes.length
        + responseBodyBytes.length];// todo singlethread 라 shallow copy?
    System.arraycopy(responseHeadersBytes, 0, responseBytes, 0, responseHeadersBytes.length);
    System.arraycopy(responseBodyBytes, 0, responseBytes, responseHeadersBytes.length,
        responseBodyBytes.length);

    return responseBytes;

  }//createResponse

}//class

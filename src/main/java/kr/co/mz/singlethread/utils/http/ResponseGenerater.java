package kr.co.mz.singlethread.utils.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;
import kr.co.mz.singlethread.model.CacheDTO;
import kr.co.mz.singlethread.repository.DTOHandler;
import kr.co.mz.singlethread.server.Cache;
import kr.co.mz.singlethread.utils.files.ServerFileProcessor;

public class ResponseGenerater {

  private final RequestLineParser requestLineParser;
  private final String requestMethod;
  private final String requestURI;
  private final Map<String, String> requestParameters;
  private final Cache cache;
  private final HttpHeaderSelector httpHeaderSelector = new HttpHeaderSelector();
  private final String uriExtension;
  private final DTOHandler dtoHandler;

  public ResponseGenerater(RequestLineParser requestLineParser, Cache cache,
      DTOHandler dtoHandler) throws IOException {
    requestLineParser.parse();
    this.requestLineParser = requestLineParser;
    this.requestMethod = requestLineParser.getRequestMethod();
    this.requestURI = requestLineParser.getRequestURI();
    this.requestParameters = requestLineParser.getRequestParameters();
    this.uriExtension = requestLineParser.getUriExtension();
    this.cache = cache;
    this.dtoHandler = dtoHandler;
  }

  public byte[] getOrGenerateResponse() throws SQLException {
    //일단 cache 로 들어오면 jdbc 와 연결되고, 거기있는 데이터를 가져와서 바이트로 돌려주는 코드를 만들어보자.
    byte[] responseBodyBytes;
    if (requestURI.startsWith("/cache")) {
      //requestline parse 에서 cache 따로 분기.
      responseBodyBytes = generateCacheResponse();
    } else {
      responseBodyBytes = cache.get(requestURI);
    }

    if (responseBodyBytes.length == 0) {
      responseBodyBytes = new ServerFileProcessor(requestURI, dtoHandler)
          .readAndPut();
      cache.put(requestURI, responseBodyBytes);
    }

    var responseHeaders = httpHeaderSelector.getHeaders(uriExtension, responseBodyBytes.length);
    var responseHeadersBytes = responseHeaders.getBytes(StandardCharsets.UTF_8);

    return spliceBytes(responseHeadersBytes, responseBodyBytes);

  }//createResponse

  private byte[] generateCacheResponse() throws SQLException {

    CacheDTO cacheDTO = dtoHandler.getCache(requestURI.substring(1));
    return cacheDTO.getFileData();//안읽히면터짐 구지 nullable?
  }

  private byte[] spliceBytes(byte[] header, byte[] body) {
    var splicedBytes = new byte[header.length + body.length];
    // todo singlethread 라 shallow copy?
    System.arraycopy(header, 0, splicedBytes, 0, header.length);
    System.arraycopy(body, 0, splicedBytes, header.length, body.length);
    return splicedBytes;
  }
}//class

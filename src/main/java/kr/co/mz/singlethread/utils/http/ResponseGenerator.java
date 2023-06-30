package kr.co.mz.singlethread.utils.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import kr.co.mz.singlethread.server.Cache;
import kr.co.mz.singlethread.utils.files.FileProcessor;

public class ResponseGenerator {

  private final ParsedRequest parsedRequest;
  private final HttpHeaderSelector httpHeaderSelector = new HttpHeaderSelector();

  private final Cache cache;


  public ResponseGenerator(ParsedRequest parsedRequest, Cache cache) {

    this.parsedRequest = parsedRequest;

    this.cache = cache;
  }

  public byte[] generateResponse() throws IOException {
    var responseBodyBytes = cache.get(parsedRequest.getPath());

    if (responseBodyBytes.length == 0) {
      responseBodyBytes = getBodyBytes();
      cache.put(parsedRequest.getPath(), responseBodyBytes);
    }

    var responseHeaders = httpHeaderSelector.getHeaders(parsedRequest.getUriExtension(),
        responseBodyBytes.length);
    var responseHeadersBytes = responseHeaders.getBytes(StandardCharsets.UTF_8);

    return spliceBytes(responseHeadersBytes, responseBodyBytes);

  }//createResponse


  private byte[] spliceBytes(byte[] header, byte[] body) {
    var splicedBytes = new byte[header.length + body.length];
    // todo singlethread 라 shallow copy?
    System.arraycopy(header, 0, splicedBytes, 0, header.length);
    System.arraycopy(body, 0, splicedBytes, header.length, body.length);
    return splicedBytes;
  }

  public byte[] getBodyBytes() throws IOException {
    return new FileProcessor(parsedRequest.getPath()).read();

  }

}

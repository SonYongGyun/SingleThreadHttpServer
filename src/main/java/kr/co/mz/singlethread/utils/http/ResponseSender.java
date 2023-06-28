package kr.co.mz.singlethread.utils.http;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseSender {


  private final OutputStream clientOutputStream;
  private final byte[] response;

  public ResponseSender(OutputStream outputStream, ResponseGenerater responseGenerater) {
    this.clientOutputStream = outputStream;

    this.response = responseGenerater.getOrGenerateResponse();
  }

  public void send() throws IOException {
    var outputStream = clientOutputStream;

    outputStream.write(response);

    outputStream.flush();
  }

}//class

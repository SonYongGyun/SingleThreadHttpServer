package kr.co.mz.singlethread.utils.http;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class ResponseSender {


  private final OutputStream clientOutputStream;
  private final byte[] response;

  public ResponseSender(OutputStream outputStream, ResponseGenerater responseGenerater)
      throws SQLException {
    this.clientOutputStream = outputStream;

    this.response = responseGenerater.getOrGenerateResponse();
  }

  public void send() throws IOException {
    var outputStream = clientOutputStream;

    outputStream.write(response);

    outputStream.flush();
  }

}//class

package kr.co.mz.singlethread.utils.http;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseSender {


  private OutputStream clientOutputStream;
  private byte[] response;

  public ResponseSender(OutputStream outputStream) {
    this.clientOutputStream = outputStream;
  }

  public ResponseSender(OutputStream outputStream, byte[] resposeBytes) {
    this.clientOutputStream = outputStream;
    this.response = resposeBytes;
  }


  public void send() throws IOException {
    clientOutputStream.write(response);

    clientOutputStream.flush();
  }

  public void sendNotFound() throws IOException {
    clientOutputStream.write((new HttpHeaderSelector().notFoundHeaders() + "Not Found").getBytes());
    clientOutputStream.flush();

  }

}//class

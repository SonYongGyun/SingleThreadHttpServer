package kr.co.mz.tutorial.httpserver.guide.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {

  private static final String PROJECT_DIRECTORY =
      System.getProperty("user.dir") + "/src/resources/html"; // 사용 가능 ? 보안?
  private String requsetUri;

  public FileReader() {

  }

  public byte[] readHtmlFile(String requestUri) throws FileNotFoundException {
    File htmlFile = new File(PROJECT_DIRECTORY + requestUri);
    byte[] buffer = new byte[4096];

    try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(htmlFile))) {
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        // 읽은 데이터에 대한 처리를 원하는 대로 수행할 수 있습니다.
        // 여기서는 버퍼에 읽은 데이터를 그대로 유지합니다.
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return buffer;
  }


}//class

package kr.co.mz.tutorial.httpserver.guide.util.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

  private static final String PROJECT_DIRECTORY =
      System.getProperty("user.dir") + "/src/resources/html"; // 사용 가능 ? 보안?
  private String requsetUri;

  public FileReader(String requsetUri) {
    this.requsetUri = requsetUri;
  }

  public byte[] readFile(String requestUri) {
    File htmlFile = new File(PROJECT_DIRECTORY + requestUri);
    byte[] buffer = new byte[4096];

    try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(htmlFile));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      /*
        위의 코드에서 ByteArrayOutputStream 객체인 outputStream을 생성하고,
        읽은 데이터를 outputStream.write(buffer, 0, bytesRead) 메소드를 사용하여 쓰고 있습니다.
        이렇게 하면 버퍼로 읽은 데이터가 outputStream에 계속 추가되며,
        최종적으로 outputStream.toByteArray() 메소드를 호출하여 데이터를 하나의 배열로 변환하여 반환합니다.
        이렇게 수정된 코드를 사용하면 버퍼로 읽은 데이터를 하나의 배열로 합칠 수 있습니다.
       */
      // 밖으로 빼는 방법도 있으나 이러면 이걸 호출한 메소드에서 ioe를 throw 해줘야함
      return outputStream.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}//class

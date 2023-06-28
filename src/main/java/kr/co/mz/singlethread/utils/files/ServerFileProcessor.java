package kr.co.mz.singlethread.utils.files;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import kr.co.mz.singlethread.repository.DTOHandler;

public class ServerFileProcessor {

  private static final String PROJECT_DIRECTORY =
      System.getProperty("user.dir") + "/src/resources/html"; // 사용 가능 ? 보안?
  private String requsetUri;
  private DTOHandler cacheDAO;

  public ServerFileProcessor(String requsetUri, DTOHandler cacheDAO) {
    this.requsetUri = requsetUri;
    this.cacheDAO = cacheDAO;
  }

  public byte[] readAndPut() {
    var resourceFile = new File(PROJECT_DIRECTORY + requsetUri);
    var buffer = new byte[4096];

    try (var inputStream = new BufferedInputStream(new FileInputStream(resourceFile));
        var outputStream = new ByteArrayOutputStream()) {
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      //메타데이터만 저장함.
      var readFileBytes = outputStream.toByteArray();
      cacheDAO.putCache(resourceFile.getName(), readFileBytes);
      return readFileBytes;
    } catch (IOException e) {
      System.out.println("Failed to read file" + e.getMessage());
      return null;
    }
  }


}//class
 /*
        위의 코드에서 ByteArrayOutputStream 객체인 outputStream을 생성하고,
        읽은 데이터를 outputStream.write(buffer, 0, bytesRead) 메소드를 사용하여 쓰고 있습니다.
        이렇게 하면 버퍼로 읽은 데이터가 outputStream에 계속 추가되며,
        최종적으로 outputStream.toByteArray() 메소드를 호출하여 데이터를 하나의 배열로 변환하여 반환합니다.
        이렇게 수정된 코드를 사용하면 버퍼로 읽은 데이터를 하나의 배열로 합칠 수 있습니다.
       */
// 밖으로 빼는 방법도 있으나 이러면 이걸 호출한 메소드에서 ioe를 throw 해줘야함
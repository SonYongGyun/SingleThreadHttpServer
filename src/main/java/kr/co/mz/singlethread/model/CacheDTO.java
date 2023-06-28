package kr.co.mz.singlethread.model;

import java.util.UUID;

public class CacheDTO {

  private UUID fileId;
  private String fileName;
  private String filePath;
  private long fileSize;
  private String fileExtension;

  public CacheDTO(String fileName, String filePath, long fileSize,
      String fileExtension) {
    this.fileId = UUID.randomUUID();
    this.fileName = fileName;
    this.filePath = filePath;
    this.fileSize = fileSize;
    this.fileExtension = fileExtension;
  }

  @Override
  public String toString() {
    return "CacheDTO{" +
        "fileId=" + fileId +
        ", fileName='" + fileName + '\'' +
        ", filePath='" + filePath + '\'' +
        ", fileSize=" + fileSize +
        ", fileExtension='" + fileExtension + '\'' +
        '}';
  }

  public UUID getFileId() {
    return fileId;
  }

  public void setFileId(UUID fileId) {
    this.fileId = fileId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public long getFileSize() {
    return fileSize;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }
}

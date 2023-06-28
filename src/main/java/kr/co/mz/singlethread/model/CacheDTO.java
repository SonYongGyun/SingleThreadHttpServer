package kr.co.mz.singlethread.model;

import java.util.UUID;

public class CacheDTO {

  private UUID fileId;
  private String fileName;
  private byte[] fileData;

  public CacheDTO() {

  }

  public CacheDTO(String fileName, byte[] fileData) {
    this.fileId = UUID.randomUUID();
    this.fileName = fileName;
    this.fileData = fileData;
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

  public byte[] getFileData() {
    return fileData;
  }

  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }
}
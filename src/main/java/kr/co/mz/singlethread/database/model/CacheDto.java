package kr.co.mz.singlethread.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CacheDto {

  private UUID fileId;
  private String fileName;
  private byte[] fileData;

  public CacheDto() {
  }

  public CacheDto(String fileName, byte[] fileData) {
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

  public static CacheDto fromResultSet(ResultSet rs) throws SQLException {
    CacheDto cache = new CacheDto();
    cache.setFileId(UUID.fromString(rs.getString("fileId")));
    cache.setFileName(rs.getString("filename"));
    cache.setFileData(rs.getBytes("filedata"));

    return cache;
  }
}
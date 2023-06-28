package kr.co.mz.singlethread.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kr.co.mz.singlethread.model.CacheDTO;
import kr.co.mz.singlethread.utils.database.DBConnector;

public class DTOHandler {

  private final DBConnector dbConnector;

  public DTOHandler(DBConnector dbConnector) {
    this.dbConnector = dbConnector;
  }

  public List<CacheDTO> getCacheList() throws SQLException {
    var sql = "select * from cache";
    var list = new ArrayList<CacheDTO>();
    var pst = dbConnector.getConnection().prepareStatement(sql);
    var rs = pst.executeQuery();
    if (rs != null) {
      while (rs.next()) {
        CacheDTO cacheDTO = new CacheDTO();
        cacheDTO.setFileId(UUID.fromString(rs.getString("fildId")));
        cacheDTO.setFileName(rs.getString("fileName"));
        cacheDTO.setFileData(rs.getBytes("fileData"));
        list.add(cacheDTO);
      }
    }
    return list;
  }

  public CacheDTO getCache(String fileName) throws SQLException {
    String sql = "select * from cache where fileName=?";
    PreparedStatement pst = dbConnector.getConnection().prepareStatement(sql);
    pst.setString(1, fileName);
    ResultSet rs = pst.executeQuery();
    CacheDTO cacheDTO = new CacheDTO();
    cacheDTO.setFileId(UUID.fromString(rs.getString("fileId")));
    cacheDTO.setFileName(rs.getString("fileName"));
    cacheDTO.setFileData(rs.getBytes("fileData"));
    return cacheDTO;
  }

  public void putCache(String fileName, byte[] fileData) {
    var cacheDto = new CacheDTO(fileName, fileData);
    String sql = "INSERT INTO cache (fileId, fileName, fileData) "
        + "VALUES (?,?,?);";
    try {
      var pst = dbConnector.getConnection().prepareStatement(sql);
      pst.setString(1, cacheDto.getFileId().toString());
      pst.setString(2, cacheDto.getFileName());
      pst.setBytes(3, cacheDto.getFileData());
      pst.executeUpdate();
    } catch (SQLException sqle) {
      System.out.println("Failed to close statment" + sqle.getMessage());
    }

  }

  public int deleteCache() {
    return 0;
  }

  public int updateCache() {
    return 0;
  }


}

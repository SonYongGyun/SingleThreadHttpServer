package kr.co.mz.singlethread.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kr.co.mz.singlethread.model.CacheDto;

public class CacheDao {

  private final Connection connection;

  public CacheDao(Connection connection) {
    this.connection = connection;
  }

  public List<CacheDto> findAll() throws SQLException {
    final var sql = "select * from cache";
    final var list = new ArrayList<CacheDto>();
    try (
        var pst = connection.prepareStatement(sql);
    ) {
      var rs = pst.executeQuery();
      while (rs.next()) {
        list.add(
            CacheDto.fromResultSet(rs)
        );
      }
    }
    return list;
  }

  public Optional<CacheDto> findOneByFileId(String uuid) throws SQLException {
    final var sql = "select * from cache where fileId = ?";
    try (
        PreparedStatement pst = connection.prepareStatement(sql);
    ) {
      pst.setString(1, uuid);
      ResultSet rs = pst.executeQuery();
      CacheDto cacheDTO = null;
      if (rs.next()) {
        cacheDTO = CacheDto.fromResultSet(rs);
      }
      return Optional.ofNullable(cacheDTO);
    }
  }

  public Optional<CacheDto> findOneByFileName(String fileName) throws SQLException {
    String sql = "select * from cache where fileName = ?";
    try (
        PreparedStatement pst = connection.prepareStatement(sql);
    ) {
      pst.setString(1, fileName);
      var rs = pst.executeQuery();
      CacheDto cacheDto = null;
      if (rs.next()) {
        cacheDto = CacheDto.fromResultSet(rs);
      }
      return Optional.ofNullable(cacheDto);
    }
  }

  public Optional<UUID> insertOne(String fileName, byte[] fileData) throws SQLException {
    if (existsByFileName(fileName)) {
      return Optional.empty();
    }
    var cacheDto = new CacheDto(fileName, fileData);
    String sql = """
        INSERT INTO cache (fileId, fileName, fileData) 
        VALUES (?,?,?)
        """;
    try (
        var pst = connection.prepareStatement(sql);
    ) {
      pst.setString(1, cacheDto.getFileId().toString());
      pst.setString(2, cacheDto.getFileName());
      pst.setBytes(3, cacheDto.getFileData());
      pst.executeUpdate();
      return Optional.of(cacheDto.getFileId());
    }
  }

  public int deleteByFileId(UUID fileId) throws SQLException {
    final var sql = "delete from cache where fileid = ?";
    try (
        var pst = connection.prepareStatement(sql);
    ) {
      pst.setString(1, fileId.toString());
      return pst.executeUpdate();
    }
  }

  public int updateBy(CacheDto cacheDto) throws SQLException {
    final var sql = """
        update cache
           set filedata = ?
         where fileid = ?
        """;
    try (
        var pst = connection.prepareStatement(sql);
    ) {
      pst.setString(1, cacheDto.getFileId().toString());
      pst.setBytes(2, cacheDto.getFileData());
      return pst.executeUpdate();
    }
  }

  public boolean existsByFileName(String fileName) throws SQLException {
    final var sql = "select exists (select 1 from cache where filename=?)";
    try (
        PreparedStatement pst = connection.prepareStatement(sql);
    ) {
      pst.setString(1, fileName);
      try (var rs = pst.executeQuery()) {
        if (rs.next()) {
          final int existsResult = rs.getInt(1);
          return existsResult == 1;
        }
        return false;
      }
    }
  }
}

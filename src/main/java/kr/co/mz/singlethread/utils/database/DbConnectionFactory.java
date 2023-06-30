package kr.co.mz.singlethread.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import kr.co.mz.singlethread.config.ConnectionProperties;

public class DbConnectionFactory {

  public static Connection createConnection(
      ConnectionProperties connectionProperties) throws SQLException {
    return DriverManager.getConnection(connectionProperties.getUrl(), connectionProperties.getUserName(),
        connectionProperties.getUserPassword());
  }

}

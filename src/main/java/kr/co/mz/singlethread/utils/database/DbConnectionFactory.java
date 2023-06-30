package kr.co.mz.singlethread.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import kr.co.mz.singlethread.config.ConnectionRequirement;

public class DbConnectionFactory {

  public static Connection createConnection(
      ConnectionRequirement connectionRequirement) throws SQLException {
    return DriverManager.getConnection(connectionRequirement.getUrl(), connectionRequirement.getUserName(),
        connectionRequirement.getUserPassword());
  }

}

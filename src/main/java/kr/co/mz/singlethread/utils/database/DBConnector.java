package kr.co.mz.singlethread.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector implements AutoCloseable {

  private Connection connection;

  public DBConnector() throws SQLException {
    var url = "jdbc:mysql://localhost:3306/webchat?serverTimezone=Asia/Seoul&useSSL=true&requireSSL=false";
    var username = "webchat";
    var password = "webchat!";
    connection = DriverManager.getConnection(url, username, password);
  }

  public Connection getConnection() {
    return connection;
  }

  public void close() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

}//class

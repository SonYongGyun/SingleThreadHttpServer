package kr.co.mz.singlethread.config;

public class ConnectionProperties {

  private final String url = "jdbc:mysql://localhost:3306/webchat?serverTimezone=Asia/Seoul&useSSL=true&requireSSL=false";
  private final String userName = "webchat";
  private final String userPassword = "webchat!";

  public String getUrl() {
    return url;
  }

  public String getUserName() {
    return userName;
  }

  public String getUserPassword() {
    return userPassword;
  }
}

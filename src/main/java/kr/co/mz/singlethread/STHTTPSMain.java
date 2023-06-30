package kr.co.mz.singlethread;

import java.io.IOException;
import java.sql.SQLException;
import kr.co.mz.singlethread.server.STServer;

public class STHTTPSMain {


  public static void main(String[] args) {
    final int port = 8080;

    try (
        var serverSocketManager = new STServer(port);
    ) {
      serverSocketManager.start();
    } catch (IOException e) {
      System.out.println("Failed to start server.");
    } catch (SQLException sqle) {
      System.out.println("Failed to get connection to database.");

    }
  }//main

}//Main

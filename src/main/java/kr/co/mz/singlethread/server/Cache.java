package kr.co.mz.singlethread.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Cache {

  private final Map<String, byte[]> savedCache;

  public Cache() {
    this.savedCache = new HashMap<>();
  }

  public void put(String responseTitle, byte[] response) {
    if (responseTitle.isEmpty()) {
      System.out.println("Nothing to caching");
      return;
    }
    if (response.length == 0) {
      System.out.println("Nothing to caching");
    }

    savedCache.put(responseTitle, response);
  }

  public byte[] get(String key) {
    return Optional.ofNullable(savedCache.get(key))
        .orElse(new byte[0]); //optional 옵셔널 써서 get 의 NPE 처리 하기.
  }

  public void deleteCache(String responseTitle) {
    savedCache.remove(responseTitle);
  }

  public void deleteAllCache() {
    savedCache.clear();
  }

}

package kr.co.mz.tutorial.httpserver.guide.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {

  private Map<String, byte[]> savedCache;

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
    if (key.isEmpty()) {
      System.out.println("Invalid response title.");
      return null;
    }

    return savedCache.get(key); //todo optional 옵셔널 써서 get 의 NPE 처리 하기.
  }

  public void deleteCache(String responseTitle) {
    if (responseTitle.isEmpty()) {
      System.out.println("Invalid response title.");
      return;
    }

    if (!savedCache.containsKey(responseTitle)) {
      System.out.println("Cache not found for the given response title.");
      return;
    }

    savedCache.remove(responseTitle);
  }

  public void deleteAllCache() {
    savedCache.clear();
  }

  public boolean containsKey(String key) {// todo 이게 구지 필요한가? 안쓰고도 로직으로 짧게 처리 가능했다.
    return savedCache.containsKey(key);
  }

}

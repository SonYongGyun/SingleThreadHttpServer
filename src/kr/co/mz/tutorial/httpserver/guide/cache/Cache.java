package kr.co.mz.tutorial.httpserver.guide.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {

  private Map<String, String> savedCache;

  public Cache() {

    this.savedCache = new HashMap<>();
  }

  public void saveCache(String responseTitle, String response) {
    if (responseTitle.isEmpty()) {
      System.out.println("Nothing to caching");
      return;
    }
    if (response.isEmpty()) {
      System.out.println("Nothing to caching");
    }

    savedCache.put(responseTitle, response);
  }

  public String getCache(String responseTitle) {
    if (responseTitle.isEmpty()) {
      System.out.println("Invalid response title.");
      return null;
    }

    if (!savedCache.containsKey(responseTitle)) {
      System.out.println("Cache not found for the given response title.");
      return null;
    }

    return savedCache.get(responseTitle);
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

}

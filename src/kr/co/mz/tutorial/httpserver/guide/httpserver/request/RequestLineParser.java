package kr.co.mz.tutorial.httpserver.guide.httpserver.request;

import java.util.HashMap;
import java.util.Map;

public class RequestLineParser {

  private final String requestLine;
  private String requestMethod;
  private String requestURI;
  private Map<String, String> requestParameters = new HashMap<>();

  public RequestLineParser(String requestLine) {
    this.requestLine = requestLine;
  }

  public void doParse() {
    if (requestLine.isBlank()) {
      System.out.println("No requestLine.");
      return;
    }

    String[] parts = requestLine.split(" ", 3);
    if (parts.length == 3) {
      this.requestMethod = parts[0];

      String[] uriParts = parts[1].split("\\?");
      requestURI = uriParts[0];

      if (uriParts.length > 1) {
        String[] params = uriParts[1].split("&");

        for (String param : params) {
          String[] keyValue = param.split("=");
          if (keyValue.length == 2) {
            String key = keyValue[0];
            String value = keyValue[1];
            requestParameters.put(key, value);
          }
        }
      } else {
        requestParameters = null;
      }
    } else {
      System.out.println("Invalid requestLine format.");
    }
  }//doParse


  public String getRequestMethod() {
    if (requestMethod == null || requestMethod.isBlank()) {
      throw new IllegalStateException("RequestMethod is not set.");
    }
    return requestMethod;
  }

  public String getRequestURI() {
    if (requestURI == null || requestURI.isBlank()) {
      throw new IllegalStateException("RequestURI is not set.");
    }
    return requestURI;
  }

  public Map<String, String> getRequestParameters() {
    if (requestParameters == null) {
      System.out.println("No Params");
    }
    return requestParameters;
  }
}//class



/*
네, Getter 메소드에 대한 값의 유효성 검증은 해당 Getter 메소드 내부에서 직접 처리하는 것이 가장 적합합니다.
이유는 값의 유효성은 해당 필드의 상태에 직접적으로 의존하기 때문입니다. 따라서 값의 유효성 검증은 Getter 메소드 내부에 포함시켜야 합니다.

만약 검증 로직을 별도의 메소드로 추출하면, 해당 메소드에서 어떤 필드에 대한 유효성 검증인지를 파악하기 어려워질 수 있고,
Getter 메소드가 여러 개인 경우에는 어떤 Getter 메소드를 호출했을 때 검증이 이루어져야 하는지 혼란스러워질 수 있습니다.
따라서 가독성과 유지보수성을 고려하여 각 Getter 메소드 내에서 값을 검증하는 것이 좋습니다.
 */
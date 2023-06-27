package kr.co.mz.tutorial.httpserver.guide.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestLineParser {

  private final InputStream inputStream;
  private String requestMethod;
  private String requestURI;
  private String uriExtension;
  private Map<String, String> requestParameters = new HashMap<>();

  public RequestLineParser(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public void parse() throws IOException {
    var reader = new BufferedReader(
        new InputStreamReader(inputStream));

    var requestLine = reader.readLine();

    String[] parts = requestLine.split(" ", 3);
    if (parts.length == 3) {
      this.requestMethod = parts[0];

      String[] uriParts = parts[1].split("\\?");
      requestURI = uriParts[0];

      String[] uri = requestURI.split("\\.");
      System.out.println(uri[0]);
      uriExtension = uri[1];
      if (uriParts.length > 1) {
        String[] params = uriParts[1].split("&");

        for (String param : params) {
          String[] keyValue = param.split("="); // 파라미터 변수많음. url class
          if (keyValue.length == 2) {
            String key = keyValue[0];
            String value = keyValue[1];
            requestParameters.put(key, value);
          }
        }
      }
    } else {
      System.out.println("response 400 error fix .");// todo fix 404 에러 뜨게 만들어보기.

    }
  }//parse


  public String getRequestMethod() {// nullable notice 위에서 null 될수있다고 했으면 구지 또 할필요 없다.
    return requestMethod;
  }

  public String getRequestURI() {
    return requestURI;
  }

  public Map<String, String> getRequestParameters() {
    return requestParameters;
  }

  public String getUriExtension() {
    return uriExtension;
  }

}//class





/*
네, Getter 메소드에 대한 값의 유효성 검증은 해당 Getter 메소드 내부에서 직접 처리하는 것이 가장 적합합니다.
이유는 값의 유효성은 해당 필드의 상태에 직접적으로 의존하기 때문입니다. 따라서 값의 유효성 검증은 Getter 메소드 내부에 포함시켜야 합니다.

만약 검증 로직을 별도의 메소드로 추출하면, 해당 메소드에서 어떤 필드에 대한 유효성 검증인지를 파악하기 어려워질 수 있고,
Getter 메소드가 여러 개인 경우에는 어떤 Getter 메소드를 호출했을 때 검증이 이루어져야 하는지 혼란스러워질 수 있습니다.
따라서 가독성과 유지보수성을 고려하여 각 Getter 메소드 내에서 값을 검증하는 것이 좋습니다.
 */
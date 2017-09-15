
 

package com.axway.mbaas_preprod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.axway.mbaas_preprod.auth.SdkAuthentication;
import com.axway.mbaas_preprod.auth.SdkCookiesHelper;
import com.axway.mbaas_preprod.SdkException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;

public class Result {

  private int statusCode;
  private String body;
  private Exception error;
  private long validUntil;
  private Map<String, Object> headers;

  public Result(HttpResponse response) {
    this.statusCode = response.getStatusCode();
    this.headers = new HashMap<>();
    HttpHeaders responseHeaders = response.getHeaders();
    String cookieValue = response.getHeaders().get("set-cookie") != null ? response.getHeaders().get("set-cookie").toString() : null;
    //Since ArrowDB has [_session=<value>] the [] create a problem of parsing Cookie, hence getCookie() method returns null.
    if(cookieValue != null && cookieValue.length() > 1){
      new SdkCookiesHelper(cookieValue);
      //Log.d("Result ","Cookie ==="+response.getHeaders().get("set-cookie").toString());
    }

    try {
      InputStream content = response.getContent();
      BufferedReader reader = new BufferedReader(new InputStreamReader(content));
      StringBuilder builder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
      this.body = builder.toString();
    } catch (Exception e) {
      this.error = e;
    }

    if (responseHeaders != null && !responseHeaders.isEmpty()) {
      for (Map.Entry<String, Object> h : responseHeaders.entrySet()) {
        this.headers.put(h.getKey(), h.getValue());
      }
    }
  }

  public Result(Exception e, String[] authNames, Map<String, SdkAuthentication> authentications) {
    if (e instanceof HttpResponseException) {
      HttpResponseException exception = (HttpResponseException) e;
      statusCode = exception.getStatusCode();
    } else {
      try {
        statusCode = Integer.parseInt(e.getMessage().replaceAll("\\D+", "").trim());
      } catch (NumberFormatException nfe) {
        statusCode = 0;
      }
    }

    if (statusCode == 401 || statusCode == 500 && !isAuthenticationAvailable(authNames, authentications)) {
      error =
          new SdkException(statusCode, e.getMessage() + ", There is a potential mismatch between the authentication required by the API call and the authentication provided. Before calling the API, confirm you have called one of the accepted authentication type(s): " + Arrays.toString(authNames));
    } else {
      if (e instanceof HttpResponseException) {
        HttpResponseException httpResponseException = (HttpResponseException) e;
        String message = httpResponseException.getStatusMessage() + "-" + httpResponseException.getContent();
        error = new SdkException(httpResponseException.getStatusCode(), message);
      }
    }
  }

  /**
   * Check if the Authentication required parameters are set before making the API Call.
   *
   * @param authNames String[] of Authentication types allowed by the API
   * @throws SdkException Exception is thrown if the parameters are not set.
   */
  private boolean isAuthenticationAvailable(String[] authNames, Map<String, SdkAuthentication> authentications) {
    boolean isAuthenticationAvailable = false;
    for (String authName : authNames) {
      if (authentications.get(authName).isAvailable()) {
        isAuthenticationAvailable = true;
        break;
      }
    }
    if (!isAuthenticationAvailable) {
      return false;
    }
    return true;
  }

  public boolean isSuccessful() {
    return statusCode >= 200 && statusCode <= 299;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getBody() {
    return body;
  }

  public JSONObject asJSON() throws JSONException {
    return new JSONObject(getBody());
  }

  public Exception getError() {
    return error;
  }

  public long getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(long validUntil) {
    this.validUntil = validUntil;
  }

  public Map<String, Object> getHeaders() {
    return this.headers;
  }

  @Override
  public String toString() {
    return "Result{" +
            "statusCode=" + statusCode +
            ", body='" + body + '\'' +
            ", error=" + error +
            ", validUntil=" + validUntil +
            ", headers=" + headers +
            '}';
  }

}

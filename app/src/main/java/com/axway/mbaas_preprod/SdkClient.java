
 

package com.axway.mbaas_preprod;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.axway.mbaas_preprod.auth.SdkAPIKeyAuth;
import com.axway.mbaas_preprod.auth.SdkAuthentication;
import com.axway.mbaas_preprod.auth.SdkCookiesHelper;
import com.axway.mbaas_preprod.auth.SdkHttpBasicAuth;
import com.axway.mbaas_preprod.auth.SdkIdentityProvider;
import com.axway.mbaas_preprod.auth.SdkOAuthHelper;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import net.openid.appauth.AuthorizationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


/**
 * The mbaas_preprod SDK
 */
public class SdkClient implements SdkConstants {

  /**
   * SdkClient singleton object
   */
  private static SdkClient mSdkClientInstance = null;

  /**
   * LOG Tag string
   */
  private final String LOG_TAG = this.getClass().getSimpleName();
  /**
   * Date Format
   */
  private final DateFormat dateFormat;
  /**
   * The mapping of security definitions by name to auth type. Use this map to set the various auth values necessary to use the APIs.
   */
  private Map<String, SdkAuthentication> authentications;
  /**
   * The default HTTP headers to be included for all API calls.
   */
  private Map<String, String> defaultHeaders;
  /**
   * Used if Developer wants to force a certain Auth Type
   */
  private String mForceAuthName;

  /**
   * Constructor
   */
  private SdkClient() {

    // authentications hashmap requires all types of authentications objects. ( SdkOAuthHelper
    // types are
    // all combined into One SdkOAuthHelper Object).
    this.authentications = new HashMap<>();
    HashMap<String, SdkIdentityProvider> providers = SdkIdentityProvider.getAllIdentityProviders();

    this.authentications.put(NAME_API_AUTH, new SdkAPIKeyAuth("query", "key"));

    if (providers.size() > 0) {
      for (Map.Entry<String, SdkIdentityProvider> entry : providers.entrySet()) {
        SdkIdentityProvider idp = entry.getValue();
        this.authentications.put(idp.getIDPName(), SdkOAuthHelper.getInstance());
      }
    }

    // Prevent the authentications from being modified.
    this.authentications = Collections.unmodifiableMap(this.authentications);

    // Use RFC3339 format for date and datetime.
    // See http://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14
    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
    // Use UTC as the default time zone.
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

  }

  /**
   * Returns the singleton instance of the SdkClient class
   *
   * @return SdkClient instance
   */
  public static SdkClient getInstance() {

    if (mSdkClientInstance == null) {
      mSdkClientInstance = new SdkClient();
    }
    return mSdkClientInstance;

  }

  /**
   * Add a default header to be added to all API calls
   *
   * @param key Header key String
   * @param value Header value String
   */
  public void addDefaultHeader(@NonNull String key, @NonNull String value) {
    if (defaultHeaders == null) {
      defaultHeaders = new HashMap<>();
    }
    defaultHeaders.put(key, value);

  }

  /**
   * Set the Auth type if the API supports it. This will force the client to use the authName
   *
   * @param authName @{@link String} Authentication Name as specified in {@link SdkConstants}#NAME_*
   * @throws SdkException Exception if there is an issue
   */
  public void setAuthenticationType(String authName) throws SdkException {

    mForceAuthName = authName;
    // Check if the SdkOAuthHelper object has been initialized
    SdkIdentityProvider idp = SdkIdentityProvider.getAllIdentityProviders().get(authName);
    if (idp != null) {
      if (!SdkOAuthHelper.checkIfInitialized()) {
        throw new SdkException(SdkException.NOT_INITIALIZED, "Please initialize SdkOAuthHelper Objects");
      }
    }
  }

  /**
   * Helper method to set username for the first HTTP basic authentication.
   */
  public void setBasicAuthUsername(String username) throws SdkException {
    for (SdkAuthentication auth : authentications.values()) {
      if (auth instanceof SdkHttpBasicAuth) {
        ((SdkHttpBasicAuth) auth).setUsername(username);
        return;
      }
    }
    throw new SdkException(SdkException.AUTH_CONFIG_NOT_AVAILABLE, "No HTTP basic authentication configured!");
  }

  /**
   * Helper method to set password for the first HTTP basic authentication.
   */
  public void setBasicAuthPassword(String password) throws SdkException {
    for (SdkAuthentication auth : authentications.values()) {
      if (auth instanceof SdkHttpBasicAuth) {
        ((SdkHttpBasicAuth) auth).setPassword(password);
        return;
      }
    }
    throw new SdkException(SdkException.AUTH_CONFIG_NOT_AVAILABLE, "No HTTP basic authentication configured!");
  }

  /**
   * Helper method to set API key value for the first API key authentication.
   */
  public void setApiKey(String apiKey) throws SdkException {
    for (SdkAuthentication auth : authentications.values()) {
      if (auth instanceof SdkAPIKeyAuth) {
        ((SdkAPIKeyAuth) auth).setApiKey(apiKey);
        return;
      }
    }
    throw new SdkException(SdkException.AUTH_CONFIG_NOT_AVAILABLE, "No API key authentication configured!");
  }

  /**
   * Helper method to set API key prefix for the first API key authentication.
   */
  public void setApiKeyPrefix(String apiKeyPrefix) throws SdkException {
    for (SdkAuthentication auth : authentications.values()) {
      if (auth instanceof SdkAPIKeyAuth) {
        ((SdkAPIKeyAuth) auth).setApiKeyPrefix(apiKeyPrefix);
        return;
      }
    }
    throw new SdkException(SdkException.AUTH_CONFIG_NOT_AVAILABLE, "No API key authentication configured!");
  }

  /**
   * Calls the respective Auth method's logout.
   */
  public void logoutUser() {
    // Log.d(LOG_TAG, "Logging Out");
    for (SdkAuthentication auth : authentications.values()) {
      auth.logoutUser();
    }
  }

  /**
   * Format the given parameter object into string.
   */
  public String parameterToString(Object param) {
    if (param == null) {
      return "";
    }

    if (param instanceof Date) {
      return SdkUtils.formatDate(dateFormat, (Date) param);
    }

    if (param instanceof Collection) {
      StringBuilder b = new StringBuilder();
      for (Object o : (Collection<?>) param) {
        if (b.length() > 0) {
          b.append(",");
        }
        b.append(String.valueOf(o));
      }
      return b.toString();
    }

    return String.valueOf(param);
  }

  /**
   * Format to {@code Pair} objects.
   *
   * @param collectionFormat String
   * @param name String
   * @param value Object
   * @return List of Pair Objects
   */
  public List<Pair> parameterToPairs(@NonNull String collectionFormat, String name, Object value) {
    List<Pair> params = new ArrayList<>();

    // preconditions
    if (name == null || name.isEmpty() || value == null) {
      return params;
    }

    Collection<?> valueCollection;
    if (value instanceof Collection<?>) {
      valueCollection = (Collection<?>) value;
    } else {
      params.add(new Pair(name, parameterToString(value)));
      return params;
    }

    if (valueCollection.isEmpty()) {
      return params;
    }

    // get the collection format
    String finalCollectionFormat = (collectionFormat == null || collectionFormat.isEmpty() ? "csv" : collectionFormat); // default: csv

    // create the params based on the collection format
    if ("multi".equals(collectionFormat)) {
      for (Object item : valueCollection) {
        params.add(new Pair(name, parameterToString(item)));
      }

      return params;
    }

    String delimiter = ",";

    switch (finalCollectionFormat) {
      case "csv":
        delimiter = ",";
        break;
      case "ssv":
        delimiter = " ";
        break;
      case "tsv":
        delimiter = "\t";
        break;
      case "pipes":
        delimiter = "|";
        break;
    }

    StringBuilder sb = new StringBuilder();
    for (Object item : valueCollection) {
      sb.append(delimiter);
      sb.append(parameterToString(item));
    }

    params.add(new Pair(name, sb.substring(1)));

    return params;
  }

  /**
   * Check if the given MIME is a JSON MIME. JSON MIME examples: application/json application/json; charset=UTF8 APPLICATION/JSON
   */
  private boolean isJsonMime(String mime) {
    return mime != null && mime.matches("(?i)application/json(;.*)?");
  }

  /**
   * Select the Accept header's value from the given accepts array: if JSON exists in the given array, use it; otherwise use all of them (joining into a string)
   *
   * @param accepts The accepts array to select from
   * @return The Accept header to use. If the given array is empty, null will be returned (not to set the Accept header explicitly).
   */
  public String selectHeaderAccept(String[] accepts) {
    if (accepts.length == 0) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    for (String accept : accepts) {
      if (isJsonMime(accept)) {
        return accept;
      }
      sb.append(accept).append(",");
    }
    if (sb.length() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  /**
   * Select the Content-Type header's value from the given array: if JSON exists in the given array, use it; otherwise use the first one of the array.
   *
   * @param contentTypes The Content-Type array to select from
   * @return The Content-Type header to use. If the given array is empty, JSON will be used.
   */
  public String selectHeaderContentType(String[] contentTypes) {
    if (contentTypes.length == 0) {
      return "application/json";
    }

    for (String contentType : contentTypes) {
      if (isJsonMime(contentType)) {
        return contentType;
      }
    }
    return contentTypes[0];
  }

  /**
   * Escape the given string to be used as URL query value.
   */
  public String escapeString(String str) {
    try {
      return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
    } catch (UnsupportedEncodingException e) {
      // Log.e(LOG_TAG, "escapeString error ", e);
      return str;
    }
  }

  /**
   * Serialize the given Java object into string according the given Content-Type (only JSON is supported for now).
   *
   * @param obj @{@link Object} to be serialized
   * @param contentType {@link String} containing the content-type
   * @param formParams {@link Map} of Form Parameters
   * @return {@link HttpContent}
   * @throws SdkException if serialize fails
   */
  private HttpContent serialize(Object obj, @NonNull String contentType, @NonNull Map<String, Object> formParams) throws SdkException {
    if (contentType.toLowerCase().startsWith("multipart/form-data")) {
      return new SdkMultipartFormDataContent(formParams);
    } else if (contentType.toLowerCase().startsWith("application/x-www-form-urlencoded")) {
      return new UrlEncodedContent(formParams);
    } else {
      try {
        HttpContent content = new EmptyContent();;
        if (obj != null) {
        //JsonHttpContent requires a Map. If you pass an Object then the content will be empty when added to the Request.
          Type type = new TypeToken<Map<String, String>>(){}.getType();
          Map<String, String> objMap = JSONUtil.getGson().fromJson(obj.toString(), type);
          content = new JsonHttpContent(new GsonFactory(), objMap);
        }
        return content;
      } catch (Exception e) {
        // Log.e(LOG_TAG, "serialize" + SdkException.INTERNAL_SERVER_ERROR + e.getMessage());
        throw new SdkException(SdkException.INTERNAL_SERVER_ERROR, e.getMessage());
      }
    }
  }

  /**
   * Deserialize the given JSON Result object into Object ( Only JSON is supported for now).
   *
   * @param result @{@link Result} object to be de-serialized
   * @param t @{@link java.lang.reflect.Type}
   * @return @{@link Object}
   * @throws SdkException In case there is an error
   */
  public Object deserialize(Result result, java.lang.reflect.Type t) throws SdkException {
    String json = result.getBody();
    try {
      if (String.class.equals(t)) {
        if (json != null && json.startsWith("\"") && json.endsWith("\"") && json.length() > 1)
          return json.substring(1, json.length() - 1);
        else
          return json;
      } else if (JSONUtil.isJSONValid(json) && JSONObject.class.equals(t)) {
        return new JSONObject(json);
      } else {
        return JSONUtil.deserialize(json, t);
      }
    } catch (JsonParseException e) {
      // Log.e(LOG_TAG, "deserialize ==" + SdkException.INTERNAL_SERVER_ERROR + e.getMessage(),e);
      throw new SdkException(SdkException.INTERNAL_SERVER_ERROR, e.getMessage());
    } catch (JSONException e) {
      // Log.e(LOG_TAG, "deserialize ==" + SdkException.INTERNAL_SERVER_ERROR + e.getMessage(),e);
       throw new SdkException(SdkException.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }


  /**
   * Build full URL by concatenating base path, the given sub path and query parameters.
   *
   * @param path The sub path
   * @param queryParams The query parameters
   * @return The full URL
   */
  private GenericUrl buildUrl(String path, List<Pair> queryParams) {
    /*
     * The base path to put in front of every API call's (relative) path.
     */
    GenericUrl url = new GenericUrl(BASE_PATH_URL);
    url.appendRawPath(path);

    if (queryParams != null && !queryParams.isEmpty()) {
      for (Pair param : queryParams) {
        if (param.getValue() != null) {
          url.put(param.getName(), param.getValue());
        }
      }
    }

    return url;
  }

  /**
   * Calls the required HTTP API's to initiate the call for the REST API along with the Authentication
   *
   * @param path String containing the API Path
   * @param method String containing the method ( GET,POST,DELETE etc)
   * @param queryParams List of Query Parameters
   * @param body Object containing the body
   * @param headerParams Map of header Parameters
   * @param formParams Map of Form Parameters
   * @param accept String containing the accept header Parameter
   * @param contentType String containing the content-type
   * @param authNames List of Auth names allowed as per API.
   * @return {@see Result} object
   * @throws SdkException on Error/Exception
   */
  private Result getAPIResponse(@Nullable String path, @NonNull String method, @Nullable List<Pair> queryParams, @Nullable Object body, @NonNull Map<String, String> headerParams, @NonNull Map<String, Object> formParams, @Nullable String accept, @Nullable String contentType,
      final @Nullable String[] authNames) throws SdkException {

    if (body != null && !formParams.isEmpty()) {
      throw new SdkException(SdkException.INTERNAL_SERVER_ERROR, "Cannot have body and form params");
    }

    String finalMethod = method.toUpperCase();

    HttpHeaders headers = new HttpHeaders();

    final GenericUrl url = buildUrl(path, queryParams);

    // Headers
    // "Accept", "Accept-Encoding", "Authorization", "Cache-Control", "Content-Encoding",
    // "Content-Length", "Content-MD5",
    // "Content-Range", "Content-Type", "Cookie", "Date", "ETag", "Expires",
    // "If-Modified-Since", "If-Match", "If-None-Match",
    // "If-Unmodified-Since", "If-Range", "Last-Modified", "Location", "MIME-Version", "Range",
    // "Retry-After", "User-Agent",
    // "WWW-Authenticate", "Age"
    if (defaultHeaders != null && defaultHeaders.size() > 0) {
      headers.putAll(defaultHeaders); // first put defaults...
    }
    headers.putAll(headerParams); // then do request specific overrides
    // then do Accept
    if (accept == null) {
      headers.setAccept("application/json");
    } else {
      headers.setAccept(accept);
    }

    HttpTransport transport;

    if (!AuthorizationRequest.isCertificateValidated()) {
      try {
        // Log.d(LOG_TAG, "getAPIResponse:: doNotValidateCertificate Mode");
        transport = new NetHttpTransport.Builder().doNotValidateCertificate().build();
      } catch (GeneralSecurityException e) {
        // Log.e(LOG_TAG, "getAPIResponse:: GeneralSecurityException == " + e.getMessage(),e);
        throw new SdkException(SdkException.TRANSPORT_OBJECT_CREATION_FAILED, "Transport Object Creation Failed");
      }
    } else {
      transport = new NetHttpTransport();
    }
    HttpContent content = null;
    // Method
    switch (finalMethod) {
      case "GET":
      case "DELETE":
        break;
      case "PATCH":
        transport = new ApacheHttpTransport();
        // fall-through intentionally
        headers.setContentType(contentType);
        content = serialize(body, contentType, formParams);
        break;
      case "POST":
      case "PUT":
        headers.setContentType(contentType);
        if (contentType.toLowerCase().startsWith("multipart/form-data")) {
          headers.setContentType("multipart/form-data; boundary=" + SdkMultipartFormDataContent.BOUNDARY);
        }
        content = serialize(body, contentType, formParams);
        break;
      default:
        throw new SdkException(SdkException.INTERNAL_SERVER_ERROR, "unknown method type " + finalMethod);
    }

    HttpRequestFactory requestFactory = transport.createRequestFactory(new HttpRequestInitializer() {
      @Override
      public void initialize(HttpRequest request) throws IOException {
        try {
          request.setUrl(url);
          applyRequestInitializer(authNames, request);
        } catch (SdkException sdkE) {
          // Log.e("SdkClient", "SdkException sdk Code -- " + sdkE.getCode() + "-- Message --" + sdkE.getMessage(), sdkE);
          // Throw an IOException to stop the next steps
          throw new IOException(sdkE.getMessage(), sdkE);
        }
      }
    });

    try {
      HttpRequest req = requestFactory.buildRequest(finalMethod, url, content);
      // Get the current set of headers added for authentication and add to headers object.
      // setHeaders method resets all the headers
      headers.fromHttpHeaders(req.getHeaders());
      req.setHeaders(headers);
      //Use the below method if you want Logging.
      enableLogging(req);
      return new Result(req.execute());
    } catch (Exception e) {
      Log.e ("SdkClient",e.getMessage ());
      return new Result(e,authNames,authentications);
    }
  }

  /**
   * Enables logging for the HTTP Request including the CUrl and Request Content printing to Console Log.
   * 
   * @param request - HTTP Request
   */
  public static void enableLogging(HttpRequest request) {
    request.setCurlLoggingEnabled(true);
    request.setLoggingEnabled(true);
    Logger logger = Logger.getLogger(HttpTransport.class.getName());
    logger.setLevel(Level.ALL);
    if (request.getContent() != null) {
      try {
        Log.i(SdkClient.class.getSimpleName() + "#HttpRequest", "------------- Content -----------\n Length: " + request.getContent().getLength() + ", \n Type: " + request.getContent().getType() + "\n Content: " + JSONUtil.serialize(request.getContent())+ "\n ---------------");
        Log.i(SdkClient.class.getSimpleName() + "#HttpRequest", "------------- Headers -----------\n " + request.getHeaders().toString()+"\n ---------------");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    logger.addHandler(new Handler() {

      @Override
      public void close() throws SecurityException {}

      @Override
      public void flush() {}

      @Override
      public void publish(LogRecord record) {
        // default ConsoleHandler will print &gt;= INFO to System.err
        Log.i(SdkClient.class.getSimpleName() + "#HttpRequest", record.getMessage());
      }
    });
  }

  /**
   * Invoke API by sending HTTP request with the given options.
   *
   * @param path The sub-path of the HTTP URL
   * @param method The request method, one of "GET", "POST", "PUT", and "DELETE"
   * @param queryParams The query parameters
   * @param body The request body object - if it is not binary, otherwise null
   * @param headerParams The header parameters
   * @param formParams The form parameters
   * @param accept The request's Accept header
   * @param contentType The request's Content-Type header
   * @param authNames The authentications to apply
   * @return The response body in type of string
   */
  public Result invokeAPI(@Nullable String path, @NonNull String method, @Nullable List<Pair> queryParams, @Nullable Object body, @NonNull Map<String, String> headerParams, @NonNull Map<String, Object> formParams, @Nullable String accept, @Nullable String contentType, @Nullable String[] authNames)
      throws SdkException {

    // checkAuthentication(authNames);
    Result response = getAPIResponse(path, method, queryParams, body, headerParams, formParams, accept, contentType, authNames);
    if (response != null) {

      if (response.isSuccessful()) {
        return response;
      }
      // If we get a 4XX or 5XX, throw APIException, don't return response...
      throw new SdkException(response.getError ());
    } else {
      return null;
    }
  }


  

  /**
   * Applies headers/query parameters to request.
   *
   * @param request @{@link HttpRequest} Object
   */
  private void applyRequestInitializer(String[] authNames, HttpRequest request) throws SdkException, IOException {

    if (authNames.length > 0) {
      if (TextUtils.isEmpty(mForceAuthName)) {
        // If developer has not forced the Authentication then authenticate the API with the first item in the authNames array
        if (authentications.get(authNames[0]) != null) {
          authentications.get(authNames[0]).initialize(request);
        }
      } else {
        // If the developer has forced a particular Authentication then check if that API allows it .
        if (Arrays.asList(authNames).contains(mForceAuthName)) {
          authentications.get(mForceAuthName).initialize(request);
        } else {
          throw new SdkException(SdkException.AUTH_CONFIG_NOT_AVAILABLE, "API doesn't allow this type of Authentication");
        }
      }
    } else if (authNames == null || authNames.length == 0) {
      if (SdkIdentityProvider.getAllIDPNames().size() == 2 && SdkIdentityProvider.getAllIDPNames().contains(SdkConstants.NAME_NO_AUTH)) {
        String idpName1 = SdkIdentityProvider.getAllIDPNames().get(0);
        String idpName2 = SdkIdentityProvider.getAllIDPNames().get(1);

        if (idpName1 == SdkConstants.NAME_NO_AUTH) {
          authentications.get(idpName2).initialize(request);
        } else {
          authentications.get(idpName1).initialize(request);
        }
      } else if (SdkIdentityProvider.getAllIDPNames().size() == 1 && !SdkIdentityProvider.getAllIDPNames().get(0).equalsIgnoreCase(SdkConstants.NAME_NO_AUTH)) {
        authentications.get(SdkIdentityProvider.getAllIDPNames().get(0)).initialize(request);
      }
    }

    try {
      if (SdkCookiesHelper.getInstance().isAvailable()) {
        SdkCookiesHelper.getInstance().initialize(request);
      }
    } catch (SdkException e) {
      Log.d(LOG_TAG, "Cookie not initialized");
    }
  }
}

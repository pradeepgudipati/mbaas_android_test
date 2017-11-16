
 

package com.axway.mbaas_preprod;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.axway.mbaas_preprod.auth.SdkAPIKeyAuth;
import com.axway.mbaas_preprod.auth.SdkAuthentication;
import com.axway.mbaas_preprod.auth.SdkHttpBasicAuth;
import com.axway.mbaas_preprod.auth.SdkIdentityProvider;
import com.axway.mbaas_preprod.auth.SdkOAuthHelper;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import net.openid.appauth.AuthorizationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * The petstore SDK
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
    private OkHttpClient mOkHttpClient = null;


    /**
     * Constructor
     */
    private SdkClient() {

        // authentications hashmap requires all types of authentications objects.
        // ( SdkOAuthHelper  types are
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

        mOkHttpClient = createOkHttpClient();
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }

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
     * @param key   Header key String
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
        // Creating a new object of Cookie Handler clears the cookies.
        cookieJar = new CookieHandler();
    }

    /**
     * Clears the cookies in the store.
     */
    public void clearCookies() {
        // Creating a new object of Cookie Handler clears the cookies.
        cookieJar = new CookieHandler();
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
     * @param name             String
     * @param value            Object
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
     * Deserialize the given JSON Result object into Object ( Only JSON is supported for now).
     *
     * @param result @{@link Result} object to be de-serialized
     * @param t      @{@link java.lang.reflect.Type}
     * @return @{@link Object}
     * @throws SdkException In case there is an error
     */
    public Object deserialize(Result result, java.lang.reflect.Type t) throws SdkException {
        if(result != null) {
            String json = result.getBody();
            try {
                if (String.class.equals(t)) {
                    if (json != null && json.startsWith("\"") && json.endsWith("\"") && json.length() > 1) {
                        return json.substring(1, json.length() - 1);
                    } else {
                        return json;
                    }
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
        }else{
            throw new SdkException(SdkException.MISSING_PARAMETER,"Empty Result");
        }
    }


    /**
     * Build full URL by concatenating base path, the given sub path and query parameters.
     *
     * @param path        The sub path
     * @param queryParams The query parameters
     * @return The full URL
     */
    private HttpUrl.Builder buildUrl(String path, List<Pair> queryParams) {
    /*
     * The base path to put in front of every API call's (relative) path.
     */
        HttpUrl.Builder url = new HttpUrl.Builder();
        HttpUrl basePathUrl = HttpUrl.parse(BASE_PATH_URL);

        url.host(basePathUrl.host());
        url.port(basePathUrl.port());
        url.scheme(basePathUrl.scheme());
        for (String pathSegment : basePathUrl.pathSegments()) {
            url.addEncodedPathSegment(pathSegment);
        }
        if (path.startsWith("/")) {
            path = path.substring(1, path.length());
        }
        url.addEncodedPathSegments(path);
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Pair param : queryParams) {
                if (param.getValue() != null) {
                    url.addQueryParameter(param.getName(), param.getValue());
                }
            }
        }

        return url;
    }


    /**
     * Invoke API by sending HTTP request with the given options.
     *
     * @param path         The sub-path of the HTTP URL
     * @param method       The request method, one of "GET", "POST", "PUT", and "DELETE"
     * @param queryParams  The query parameters
     * @param body         The request body object - if it is not binary, otherwise null
     * @param headerParams The header parameters
     * @param formParams   The form parameters
     * @param accept       The request's Accept header
     * @param contentType  The request's Content-Type header
     * @param authNames    The authentications to apply
     * @return The response body in type of string
     */

    public Result invokeAPI(@Nullable String path, @NonNull String method, @Nullable List<Pair> queryParams, @Nullable Object body, @NonNull Map<String, String> headerParams, @NonNull Map<String,
            Object> formParams, @Nullable String accept, @Nullable String contentType, @Nullable String[] authNames)
            throws SdkException {

        Response response = getAPIResponse(path, method, queryParams, body, headerParams, formParams, accept, contentType, authNames);
        if (response != null) {
            Log.d(LOG_TAG, response.toString());
            if (response.isSuccessful()) {
                //return response.body().string();
                return new Result(response);
            }
            // If we get a 4XX or 5XX, throw APIException, don't return response...
            try {
                throw new SdkException(response.code(), response.message(),response.headers(),response.peekBody(1024).string());
            } catch (IOException e) {
                throw new SdkException(response.code(), response.message());
            }
        } else {
            throw new SdkException(SdkException.INTERNAL_SERVER_ERROR,"No Response received.");
        }
    }


   /**
     * Calls the required HTTP API's to initiate the call for the REST API along with the Authentication
     *
     * @param path         String containing the API Path
     * @param method       String containing the method ( GET,POST,DELETE etc)
     * @param queryParams  List of Query Parameters
     * @param body         Object containing the body
     * @param headerParams Map of header Parameters
     * @param formParams   Map of Form Parameters
     * @param accept       String containing the accept header Parameter
     * @param contentType  String containing the content-type
     * @param authNames    List of Auth names allowed as per API.
     * @return {@see Result} object
     * @throws SdkException on Error/Exception
     */
    private Response getAPIResponse(@Nullable String path, @NonNull String method, @Nullable List<Pair> queryParams, @Nullable Object body, @NonNull Map<String, String> headerParams, @NonNull
            Map<String, Object> formParams, @Nullable String accept, @Nullable String contentType,
            final @Nullable String[] authNames) throws SdkException {

        if (body != null && !formParams.isEmpty()) {
            throw new SdkException(SdkException.INTERNAL_SERVER_ERROR, "Cannot have body and form params");
        }

        String finalMethod = method.toUpperCase();

        Headers.Builder headerBuilder = new Headers.Builder();
        final HttpUrl.Builder urlBuilder = buildUrl(path, queryParams);

        // add default headers
        addHeader(defaultHeaders, headerBuilder);

        // adding header params specified by the API
        addHeader(headerParams, headerBuilder);

        // then add Accept header
        if (accept == null) {
            headerBuilder.add("Accept", "application/json");
        } else {
            headerBuilder.add("Accept", accept);
        }

        Request request = null;
        RequestBody content = null;
        Request.Builder requestBuilder = new Request.Builder();
        // add the Authentication headers
        addAuthHeaders(authNames, headerBuilder, urlBuilder);
        // Set the URL to the request builder
        requestBuilder.url(urlBuilder.build());
        //set the headers for the request builder
        requestBuilder.headers(headerBuilder.build());


        switch (finalMethod) {
            case "GET":
                request = requestBuilder.build();
                break;
            case "DELETE":
                FormBody.Builder deleteBody = new FormBody.Builder();
                for (Map.Entry<String, Object> entry : formParams.entrySet()) {
                    deleteBody.add(entry.getKey(), entry.getValue().toString());
                }
                content = deleteBody.build();
                request = requestBuilder.delete(content).build();
                break;
            case "PATCH":
                headerBuilder.add("Content-type", contentType);
                content = RequestBody.create(JSON, formParams.toString());
                request = requestBuilder.patch(content).build();
                break;
            case "POST":
            case "PUT":
                headerBuilder.add("Content-type", contentType);
                /**
                 * Please note that file upload will need the content type to be multipart and it should be the only or first type in the contentType string
                 */
                if (contentType.toLowerCase().startsWith("multipart/form-data")) {
                    MultipartBody.Builder multiPartFormData = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    for (Map.Entry<String, Object> entry : formParams.entrySet()) {
                        if (entry.getValue() instanceof File) {
                            File file = (File) entry.getValue();
                            MediaType mediaType = MediaType.parse(SdkUtils.getMimeType(file.getAbsolutePath()));
                            RequestBody fileBody = RequestBody.create(mediaType, file);
                            multiPartFormData.addFormDataPart(entry.getKey(), file.getName(), fileBody);
                        } else {
                            multiPartFormData.addFormDataPart(entry.getKey(), entry.getValue().toString());
                        }
                    }
                    request = requestBuilder.post(multiPartFormData.build()).build();
                } else {
                    if (formParams != null && formParams.size() > 0 && body == null) {
                        FormBody.Builder requestBodyForm = new FormBody.Builder();
                        for (Map.Entry<String, Object> entry : formParams.entrySet()) {
                            requestBodyForm.add(entry.getKey(), entry.getValue().toString());
                        }

                        content = requestBodyForm.build();
                    } else if (body != null) {
                        // handle body as JSON
                        Gson gson = new Gson();
                        String postJsonBody = gson.toJson(body);
                        content = RequestBody.create(JSON, postJsonBody);
                    }
                    if ("PUT".equalsIgnoreCase(finalMethod)) {
                        request = requestBuilder.put(content).build();
                    } else {
                        request = requestBuilder.post(content).build();
                    }

                }
                break;
            default:
                throw new SdkException(SdkException.INTERNAL_SERVER_ERROR, "unknown method type " + finalMethod);
        }

        try {
            return mOkHttpClient.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void addAuthHeaders(String[] authNames, Headers.Builder headerBuilder, HttpUrl.Builder urlBuilder) {

        //If no authentication method is defined
        if (authNames != null && authNames.length > 0) {
            //If a authentication is not set by the developer use the first one in the list
            if (TextUtils.isEmpty(mForceAuthName)) {
                // If developer has not forced the Authentication then authenticate the API with the first item in the authNames array
                if (authentications.get(authNames[0]) != null) {
                    Map headers = authentications.get(authNames[0]).initializeHeader();
                    addHeader(headers, headerBuilder);
                }
            } else {
                //if an authentication is set by developer
                Map<String, String> header;
                //Check if the authentication type is available
                if (Arrays.asList(authNames).contains(mForceAuthName)) {
                    header = authentications.get(mForceAuthName).initializeHeader();
                    if (authentications.get(mForceAuthName) instanceof SdkAPIKeyAuth) {
                        // if authentication is of type = Api Key,  check if it is a http header or URL query type
                        SdkAPIKeyAuth apiKeyAuth = (SdkAPIKeyAuth) authentications.get(mForceAuthName);
                        if ("query".equalsIgnoreCase(apiKeyAuth.getLocation())) {
                            // Add to URL and exit from the method
                            for (Map.Entry<String, String> param : header.entrySet()) {
                                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
                            }
                            //reset the header since it is not a http header
                            header = new HashMap<>();
                        }
                    }
                    // add header if it is not empty
                    if (!header.isEmpty()) {
                        addHeader(header, headerBuilder);
                    }
                }
            }
        } else {
            if (SdkIdentityProvider.getAllIDPNames().size() == 2 && SdkIdentityProvider.getAllIDPNames().contains(SdkConstants.NAME_NO_AUTH)) {
                String idpName1 = SdkIdentityProvider.getAllIDPNames().get(0);
                String idpName2 = SdkIdentityProvider.getAllIDPNames().get(1);

                if (idpName1 == SdkConstants.NAME_NO_AUTH) {
                    Map headers = authentications.get(idpName2).initializeHeader();
                    addHeader(headers, headerBuilder);
                } else {
                    Map headers = authentications.get(idpName1).initializeHeader();
                    addHeader(headers, headerBuilder);
                }
            } else if (SdkIdentityProvider.getAllIDPNames().size() == 1 && !SdkIdentityProvider.getAllIDPNames().get(0).equalsIgnoreCase(SdkConstants.NAME_NO_AUTH)) {
                Map headers = authentications.get(SdkIdentityProvider.getAllIDPNames().get(0)).initializeHeader();
                addHeader(headers, headerBuilder);
            }
        }

    }

    /**
     * Adds the header to the Request
    */
    private void addHeader(Map<String, String> map, Headers.Builder builder) {
        if (map != null && map.size() > 0) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                builder.add(pair.getKey().toString(), pair.getValue().toString());
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
    }

    /**
     * Creates the OkHttpClient
     *
     * @return OkHttpClient Object
     */
    private OkHttpClient createOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (!AuthorizationRequest.isCertificateValidated()) {
                // Bypass certificate validation. (ignore SSL or HTTPS check )
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };
                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            }

            builder.cookieJar(cookieJar);
            builder.connectTimeout(CONNECT_TIMEOUT_IN_SECS, TimeUnit.SECONDS);
            builder.writeTimeout(WRITE_TIMEOUT_IN_SECS, TimeUnit.SECONDS);
            builder.readTimeout(READ_TIMEOUT_IN_SECS, TimeUnit.SECONDS);
            builder.addInterceptor(interceptor);
            builder.followRedirects(true);
            builder.followSslRedirects(true);
            return builder.build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            // If the method returns null because of an error this is fail safe.
            if (mOkHttpClient == null) {
                mOkHttpClient = new OkHttpClient();
            }
        }
        return mOkHttpClient;
    }


    private CookieJar cookieJar = new CookieHandler();

    public class CookieHandler implements CookieJar {

        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        /**
         * Saves {@code cookies} from an HTTP response to this store according to this jar's policy.
         * <p>
         * <p>Note that this method may be called a second time for a single HTTP response if the response
         * includes a trailer. For this obscure HTTP feature, {@code cookies} contains only the trailer's
         * cookies.
         */
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        /**
         * Load cookies from the jar for an HTTP request to {@code url}. This method returns a possibly
         * empty list of cookies for the network request.
         * <p>
         * <p>Simple implementations will return the accepted cookies that have not yet expired and that
         * {@linkplain Cookie#matches match} {@code url}.
         */
        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }
}

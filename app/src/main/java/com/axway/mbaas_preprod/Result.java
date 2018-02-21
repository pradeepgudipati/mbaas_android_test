 

package com.axway.mbaas_preprod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Response;

public class Result {

    private int statusCode;
    private String body;
    private Exception error;
    private long validUntil;
    private Map<String, Object> headers;

    public Result(Response response) {
        this.statusCode = response.code();
        this.headers = new HashMap<>();
        Headers responseHeaders = response.headers();
        try {
            this.body = response.body().string();
        } catch (Exception e) {
            this.error = e;
        }

        if (responseHeaders != null && (responseHeaders.size() > 0)) {
            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                this.headers.put(responseHeaders.name(i), responseHeaders.value(i));
            }
        }
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

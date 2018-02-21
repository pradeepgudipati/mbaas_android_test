 

package com.axway.mbaas_preprod;

import android.util.Log;

import java.util.Map;

import okhttp3.Headers;

public class SdkException extends Exception {

    public static final byte NOT_INITIALIZED = 1;
    public static final int AUTH_STATE_READ_FAILED = 2;
    public static final int SECURE_PREFERENCES_NOT_INITIALIZED = 3;
    public static final int MISSING_PARAMETER = 4;
    public static final int AUTH_CONFIG_NOT_AVAILABLE = 5;
    public static final int INTERNAL_SERVER_ERROR = 501;
    public static final byte OAUTHPASSWORD_CLIENT_CREDENTIALS_MISSING = 6;
    public static final byte ERROR_RUNNING_ON_UI_THREAD = 7;
    public static final byte EXCEPTION = 8;

    private int code = 0;
    private String message = null;
    private Headers responseHeaders;
    private String responseBody;

    /**
     * Default Constructor
     *
     * @param throwable @{@link Throwable} object
     */
    public SdkException(Throwable throwable) {
        super(throwable);
        Log.e("SdkException", "Error -- Code = " + ((SdkException) throwable).getCode() + ", Message = " + throwable.getMessage());
        if (throwable instanceof SdkException && ((SdkException) throwable).code > 0) {
            this.code = ((SdkException) throwable).getCode();
        } else {
            this.code = EXCEPTION;
        }

        this.message = throwable.getMessage();
    }

    /**
     * Plain old Exception with a Code and Message
     *
     * @param code    Exception Code
     * @param message @{@link String} Exception Message
     */
    public SdkException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * SDKException for HTTP Response errors
     *
     * @param code            Exception code
     * @param message         Exception Message
     * @param responseHeaders @{@link Map} of response headers
     * @param responseBody    @{@link String} Response Body
     */
    @SuppressWarnings("SameParameterValue")
    public SdkException(int code, String message, Headers responseHeaders, String responseBody) {
        this.code = code;
        this.message = message;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Get the HTTP response headers.
     */
    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Get the HTTP response body.
     */
    public String getResponseBody() {
        return responseBody;
    }
}

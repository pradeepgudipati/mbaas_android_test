
 

package com.axway.mbaas_preprod.auth;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the API Key based Authentication
 */
public class SdkAPIKeyAuth implements SdkAuthentication {
    private final String location;
    private final String paramName;

    private String apiKey;
    private String apiKeyPrefix;

    /**
     * API Key Auth constructor
     *
     * @param location  - location of the key e.g. Header or Query Parameter
     * @param paramName - Key Name e.g. apiKey
     */
    @SuppressWarnings("SameParameterValue")
    public SdkAPIKeyAuth(String location, String paramName) {
        this.location = location;
        this.paramName = paramName;
    }

    /**
     * Gets the location of the API Key
     *
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the API Key Parameter name
     *
     * @return String
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * Gets the API Key set to the Authentication Object
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the API Key set to the Authentication Object
     *
     * @param apiKey {@link String}
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Gets the API Key Prefix set to the Authentication Object
     *
     * @return String
     */
    public String getApiKeyPrefix() {
        return apiKeyPrefix;
    }

    /**
     * Sets the API Key set to the Authentication Object
     *
     * @param apiKeyPrefix {@link String}
     */
    public void setApiKeyPrefix(String apiKeyPrefix) {
        this.apiKeyPrefix = apiKeyPrefix;
    }

    @Override
    public Map<String, String> initializeHeader() {
        Map<String, String> header = new HashMap<>();
        if (apiKey == null) {
            return header;
        }
        String value;
        if (apiKeyPrefix != null) {
            value = apiKeyPrefix + " " + apiKey;
        } else {
            value = apiKey;
        }
        if ("query".equals(location)) {
            header.put(paramName, value);
        } else if ("header".equals(location)) {
            header.put(paramName, value);
        }
        return header;
    }

    /**
     * Resets the API Key details to logout
     */
    @Override
    public void logoutUser() {
        setApiKey("");
        setApiKeyPrefix("");
    }

    /**
     * Checks if the parameters for the Authentication are available
     *
     * @return true if available, false if NOT available
     */
    @Override
    public boolean isAvailable() {
        if (TextUtils.isEmpty(this.getApiKey()) && TextUtils.isEmpty(this.getApiKeyPrefix())) {
            return false;
        }
        return true;
    }
}


 

package com.axway.mbaas_preprod.auth;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles HTTP Basic based Authentication
 */
public class SdkHttpBasicAuth implements SdkAuthentication {
    private String username;
    private String password;

    /**
     * Returns the Username from the Authentication Object
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the User name to the Authentication Object
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the Password from the Authentication Object
     *
     * @return String Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the Password to the Authentication Object
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the Credentials to the Authentication Object
     *
     * @param username {@link String}
     * @param password {@link String}
     */
    public void setBasicAuthCredentials(final @NonNull String username, final @NonNull String password) {
        setUsername(username);
        setPassword(password);
    }

    @Override
    public Map<String, String> initializeHeader() {
        Map<String, String> header = new HashMap<>();
        if (username == null && password == null) {
            return header;
        }
        String userPass = username + ":" + password;
        String encoded = Base64.encodeBase64String(StringUtils.getBytesUtf8(userPass));
        header.put("Authorization", "Basic " + encoded);
        return header;
    }

    @Override
    public void logoutUser() {
        username = null;
        password = null;
    }

    /**
     * Checks if the parameters for the Authentication are available
     *
     * @return true if available, false if NOT available
     */
    @Override
    public boolean isAvailable() {
        if (TextUtils.isEmpty(this.getUsername()) && TextUtils.isEmpty(this.getPassword())) {
            return false;
        }
        return true;
    }
}

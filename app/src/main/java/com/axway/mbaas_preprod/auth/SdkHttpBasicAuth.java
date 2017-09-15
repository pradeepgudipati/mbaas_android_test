
 

package com.axway.mbaas_preprod.auth;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Handles HTTP Basic based Authentication
 */
public class SdkHttpBasicAuth implements SdkAuthentication {
  private String username;
  private String password;

  /**
   * Returns the Username from the Authentication Object
   * 
   * @return
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the User name to the Authentication Object
   * 
   * @param username
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
   * 
   * @param password
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
  public void initialize(HttpRequest request) throws IOException {
    if (username == null && password == null) {
      return;
    }
    request.getHeaders().setBasicAuthentication((username == null ? "" : username), (password == null ? "" : password));
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

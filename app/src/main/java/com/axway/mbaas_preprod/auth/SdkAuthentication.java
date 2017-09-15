
 

package com.axway.mbaas_preprod.auth;

import com.google.api.client.http.HttpRequestInitializer;

public interface SdkAuthentication extends HttpRequestInitializer {
  /**
   * Log out the user
   */
  void logoutUser();

  /**
   * Checks if the parameters for the Authentication are available
   * 
   * @return true if available, false if NOT available
   */
  boolean isAvailable();
}

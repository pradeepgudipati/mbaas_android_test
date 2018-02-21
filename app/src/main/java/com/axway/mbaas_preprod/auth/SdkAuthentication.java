 

package com.axway.mbaas_preprod.auth;


import java.util.Map;

public interface SdkAuthentication {
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

    /**
     * returns the header for auth type
     *
     * @return the Map of the Header Key value pair
     */
    Map<String, String> initializeHeader();
}



 

package com.axway.mbaas_preprod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains all the required Constants for the SdkOAuthHelper This file is
 */
public interface SdkConstants {

    // Auto generated DO NOT CHANGE THE BELOW VALUES ------------------------------
    String BASE_PATH_URL = "https://preprod-api.cloud.appctest.com/v1";



    String NAME_API_AUTH = "api_key";

  String NAME_NO_AUTH = "";


  // Auto generated END ------------------------------

  // Auto generated but can be changed by user
  String REDIRECT_SCHEME = "https";
  String REDIRECT_HOST = "www.axwayapp.com";
  String REDIRECT_PATH = "/auth/callback";

  // Required by the application DO NOT CHANGE ------------------
  /**
   * Shared Prefs store name
   */
  String SHARED_PREFS_STORE_NAME = "axway_auth_store";

  /**
   * Shared Prefs Key Name
   */
  String SHARED_PREFS_KEY_NAME = "state_json";

}
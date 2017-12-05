
 

package com.axway.mbaas_preprod;

import okhttp3.MediaType;

/**
 * Contains all the required Constants for the SdkOAuthHelper This file is
 */
public interface SdkConstants {

  // Auto generated DO NOT CHANGE THE BELOW VALUES ------------------------------
  String BASE_PATH_URL = "https://preprod-api.cloud.appctest.com/v1";



  String NAME_API_AUTH = "api_key";

  String NAME_NO_AUTH = "";

  // Auto generated END ------------------------------

  /**
  * Media Type JSON constant
  */
  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
  /**
  * Connection timeout in seconds
  */
  int CONNECT_TIMEOUT_IN_SECS = 30;
  /**
  * Read timeout in seconds
  */
  int READ_TIMEOUT_IN_SECS = 30;
  /**
  * Write timeout in seconds
  */
  int WRITE_TIMEOUT_IN_SECS = 30;

    // Auto generated but can be changed by user

  /**
  * Oauth redirect Scheme
  */
  String REDIRECT_SCHEME = "https";
  /**
  * Oauth redirect host
  */
  String REDIRECT_HOST = "www.axwayapp.com";
  /**
  * Oauth redirect path
  */
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
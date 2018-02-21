 

package com.axway.mbaas_preprod.auth;

import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.SdkConstants;
import static net.openid.appauth.AuthorizationException.TYPE_GENERAL_ERROR;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

/**
 * Token Helper class to handle the flow after the authorize method has been received
 */
public class SdkOAuthTokenHelper implements SdkConstants {
  /**
   * used for passing info between intents
   */
  private static final String KEY_AUTH_STATE = "authState";
  /**
   * used for passing info between intents
   */
  private static final String EXTRA_AUTH_STATE = "authState";

  /**
   * This error code represents that the Refresh Token is still valid.
   */
  private static final int ERROR_CODE_TOKEN_STILL_VALID = 21;
  /**
   * String Tag for Logs
   */
  private static final String TAG = SdkOAuthTokenHelper.class.getSimpleName();
  /**
   * {@link TokenResponseListener} object
   */
  private static TokenResponseListener tokenResponseListenerInstance;
  /**
   * SdkTokenHelper instance
   */
  private static SdkOAuthTokenHelper tokenHelperInstance;

  /**
   * Constructor for SdkOAuthTokenHelper
   */
  private SdkOAuthTokenHelper() {

  }

  public static SdkOAuthTokenHelper getInstance() {

    if (tokenHelperInstance == null) {
      tokenHelperInstance = new SdkOAuthTokenHelper();
    }
    return tokenHelperInstance;
  }

  /**
   * Creates a Pending intent for Authorization callback. Accessible within package
   *
   * @param context {@link Context} object
   * @param callBackActivity The call back class
   * @param request {@link AuthorizationRequest} object
   * @param authState {@link AuthState} object
   * @return {@link PendingIntent} object
   */
  static PendingIntent createPostAuthorizationIntent(@NonNull Context context, @NonNull Class callBackActivity, @NonNull AuthorizationRequest request, @NonNull AuthState authState) {
    Intent intent = new Intent(context, callBackActivity);
    intent.putExtra(SdkOAuthTokenHelper.EXTRA_AUTH_STATE, authState.jsonSerializeString());

    return PendingIntent.getActivity(context, request.hashCode(), intent, 0);
  }

  /**
   * Receives the Auth State from the Intent
   *
   * @param intent {@link Intent}
   * @return {@link AuthState} object
   */
  private static AuthState getAuthStateFromIntent(Intent intent) {
    if (!intent.hasExtra(EXTRA_AUTH_STATE)) {
      throw new IllegalArgumentException("The AuthState instance is missing in the intent.");
    }
    try {
      return AuthState.jsonDeserialize(intent.getStringExtra(EXTRA_AUTH_STATE));
    } catch (JSONException ex) {
      // Log.e(TAG, "Malformed AuthState JSON saved" + ex);
      throw new IllegalArgumentException("The AuthState instance is missing in the intent.");
    }
  }

  /**
   * Gets the {@link TokenResponseListener} object
   */
  private TokenResponseListener getTokenResponseListener() {
    return tokenResponseListenerInstance;
  }

  /**
   * Sets the {@link TokenResponseListener} object
   *
   * @param tokenResponseListener {@link TokenResponseListener} object
   */
  private void setTokenResponseListener(@NonNull TokenResponseListener tokenResponseListener) {
    tokenResponseListenerInstance = tokenResponseListener;
  }

  /**
   * Used in case of Refresh Token
   *
   * @param tokenResponseListener {@link TokenResponseListener} Object
   * @throws SdkException Throws this exception if the performRefreshToken fails
   */
  public void performRefreshToken(@NonNull TokenResponseListener tokenResponseListener) throws SdkException {
    setTokenResponseListener(tokenResponseListener);

    AuthState authState = SdkOAuthHelper.getAuthState();
    if (authState.getNeedsTokenRefresh()) {
      performTokenRequest(authState.createTokenRefreshRequest(), tokenResponseListener);
    } else {
      AuthorizationException ex = new AuthorizationException(TYPE_GENERAL_ERROR, ERROR_CODE_TOKEN_STILL_VALID, null, "Token is still valid", null, null);
      getTokenResponseListener().onReceivedTokenResponse(ex);
    }
  }

  /**
   * Performs the Token Request Call.
   *
   * @param request {@link TokenRequest} object
   */
  private void performTokenRequest(final TokenRequest request, TokenResponseListener activity) throws SdkException {

    setTokenResponseListener(activity);
    ClientAuthentication clientAuthentication;
    try {
      clientAuthentication = SdkOAuthHelper.getAuthState().getClientAuthentication();
    } catch (ClientAuthentication.UnsupportedAuthenticationMethod ex) {
      // Log.e(TAG, "Token request cannot be made, client authentication for the token endpoint could not be constructed (%s)");
      return;
    }

    SdkOAuthHelper.getInstance().getAuthService().performTokenRequest(request, clientAuthentication, new AuthorizationService.TokenResponseCallback() {
      @Override
      public void onTokenRequestCompleted(@Nullable TokenResponse tokenResponse, @Nullable AuthorizationException ex) {
        // Update the AuthState
        try {
          AuthState authState = SdkOAuthHelper.getAuthState();

          authState.update(tokenResponse, ex);
          // Save the Auth State
          SdkOAuthHelper.getInstance().writeAuthStateToSharedPrefs(authState);
          // Call back
          getTokenResponseListener().onReceivedTokenResponse(ex);

        } catch (SdkException e) {
          // Should never happen
          // Log.d("SdkOAuthTokenHelper", "Exception -- onTokenRequestCompleted :: " + e.getMessage());
        }

        if (tokenResponse != null) {
          // Log.d("SdkOAuthTokenHelper", "Token Response == " + tokenResponse.jsonSerializeString());
        }

      }
    });
  }

  /**
   * Processes the Token Response Call back. The Authorization details are in the intent that has called the activity
   *
   * @param context {@link Context} object
   * @param intent {@link Intent} object
   * @throws SdkException Custom Exception
   */
  public void processTokenCallback(@NonNull Context context, Intent intent) throws SdkException {
    setTokenResponseListener((TokenResponseListener) context);
    processToken(intent);
  }

  /**
   * Processes the Token Response Call back. The Authorization details are in the intent that has called the activity
   *
   * @param activity {@link Activity} object
   */
  public void processTokenCallback(@NonNull Activity activity) throws SdkException {

    setTokenResponseListener((TokenResponseListener) activity);
    if (activity.getIntent() != null) {
      processToken(activity.getIntent());
    }
  }

  /**
   * Extracts the Token from the intent and stores it locally.
   *
   * @param intent The {@link Intent} containing the Token response
   * @throws SdkException
   */
  private void processToken(@NonNull Intent intent) throws SdkException {

    AuthState authState = SdkOAuthHelper.getAuthState();
    // If Access token is null it is in the Intent.
    AuthorizationResponse response = AuthorizationResponse.fromIntent(intent);
    AuthorizationException ex = AuthorizationException.fromIntent(intent);

    if (response != null) {
      // Log.d("SdkOAuthTokenHelper", "ProcessToken - response = " + response.toString());
      authState = new AuthState(response, ex);
      SdkOAuthHelper.getInstance().writeAuthStateToSharedPrefs(authState);

      if (response.request.responseType.equals(ResponseTypeValues.CODE)) {
        // Since Response type is a Code, the SdkOAuthHelper type is either
        // Explicit or Password, We now need to exchange the code for an Auth/Access token
        // Log.d(TAG, "Received AuthorizationResponse.");
        exchangeAuthorizationCode(response, SdkOAuthHelper.getInstance().getSelectedIDP().getClientSecret());

      } else {
        // If Response type is access token then the SdkOAuthHelper type is Implicit and
        // there needs
        // no further processing
        getInstance().getTokenResponseListener().onReceivedTokenResponse(ex);
      }
    } else if (authState.getAccessToken() != null) {
      getTokenResponseListener().onReceivedTokenResponse(ex);
    }

  }

  /**
   * Exchanges the Authorization Code for a Token. Added the Grant Type to make this method configurable
   *
   * @param authorizationResponse {@link AuthorizationResponse} Object
   * @param clientSecret Secret String value
   */
  private void exchangeAuthorizationCode(AuthorizationResponse authorizationResponse, String clientSecret) throws SdkException {

    Map<String, String> additionalParams = new HashMap<>();
    additionalParams.put("client_secret", clientSecret);
    TokenRequest request = authorizationResponse.createTokenExchangeRequest(additionalParams);
    TokenRequest formattedRequest = (new TokenRequest.Builder(request.configuration, request.clientId)).setGrantType(authorizationResponse.request.grant_type).setRedirectUri(request.redirectUri).setClientId(request.clientId).setScope(request.scope).setAuthorizationCode(request.authorizationCode)
        .setAdditionalParameters(request.additionalParameters).build();

    // Log.d(TAG, " TokenRequest ---" + formattedRequest.jsonSerializeString());
    performTokenRequest(formattedRequest, getTokenResponseListener());
  }

  /**
   * Saves Auth state to Bundle
   *
   * @param appBundle {@link Bundle} object
   */
  public void saveAuthStateToBundle(Bundle appBundle) throws SdkException {
    if (SdkOAuthHelper.getAuthState() != null) {
      appBundle.putString(SdkOAuthTokenHelper.KEY_AUTH_STATE, SdkOAuthHelper.getAuthState().jsonSerializeString());
    }
  }

  /**
   * Token Response Listener Interface
   */
  public interface TokenResponseListener {

    /**
     * Callback for the TokenResponse for Token callbacks and non Implicit Grant type
     *
     * @param ex {@link AuthorizationException} object
     */
    void onReceivedTokenResponse(AuthorizationException ex);

  }
}

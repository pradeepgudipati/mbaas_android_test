
 

package com.axway.mbaas_preprod.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.axway.mbaas_preprod.SdkUtils;
import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.SdkConstants;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;
import android.util.Log;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.GrantTypeValues;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;
import net.openid.appauth.browser.BrowserWhitelist;
import net.openid.appauth.encryption.AesCbcWithIntegrity;
import net.openid.appauth.encryption.SecurePreferences;

/**
 * SdkOAuthHelper Helper Class
 */
public class SdkOAuthHelper implements SdkAuthentication, SdkConstants {

/**
 * Selected Identity provider
 */
private static SdkIdentityProvider mSelectedIDP;

/**
 * SdkOAuthHelper class singleton instance
 */
private static SdkOAuthHelper mSdkOAuthHelperInstance;

/**
 * SdkOAuthHelper class singleton instance
 */
private static boolean isSdkOAuthHelperInitialized = false;
/**
 * AuthState Object
 */
private static AuthState mAuthState;
/**
 * Secure Preferences instance
 */
private static SharedPreferences mSecurePreferences;
/**
 * Tag name for Log.d
 */
private final String logTag = SdkOAuthHelper.this.getClass ().getSimpleName ();
/**
 * Authorization service object
 */
private AuthorizationService mAuthService;

/**
 * Constructor for SdkOAuthHelper Class
 */
private SdkOAuthHelper() {
    mSdkOAuthHelperInstance = this;
}

/**
 * Initializes the SdkOAuthHelper Class with default values for BRowser
 *
 * @param context @{@link Context} object
 * @throws SdkException Custom Exception class
 */
public static void initializeOAuth(@NonNull Context context, Activity callBackActivity) throws SdkException, GeneralSecurityException {
    if (mSdkOAuthHelperInstance == null) {
        init (context, null);
        SdkOAuthTokenHelper.getInstance ().processTokenCallback (callBackActivity);
    }
}

/**
 * Initializes the SdkOAuthHelper Class with default values for BRowser
 *
 * @param context @{@link Context} object
 * @throws SdkException Custom Exception class
 */
public static void initializeOAuth(@NonNull Context context) throws SdkException {
    if (mSdkOAuthHelperInstance == null) {
        init (context, null);
    }
}

/**
 * Initializes the SdkOAuthHelper Class with a BrowserWhitelist
 *
 * @param context @{@link Context} object
 * @throws SdkException Custom Exception class
 */
public static void initializeOAuth(@NonNull Context context, @NonNull BrowserWhitelist browsers) throws SdkException, GeneralSecurityException {
    if (mSdkOAuthHelperInstance == null) {
        init (context, browsers);
    }
}

private static void init(@NonNull Context context, BrowserWhitelist browsers) throws SdkException {

    if (mSdkOAuthHelperInstance == null) {
        mSdkOAuthHelperInstance = new SdkOAuthHelper ();
        initSecurePrefs (context.getApplicationContext ());

        if (browsers != null) {
            mSdkOAuthHelperInstance.initAuthService (context, browsers);
        } else {
            mSdkOAuthHelperInstance.initAuthService (context);
        }

        isSdkOAuthHelperInitialized = true;

    }

}


/**
 * returns the SdkOAuthHelper instance
 *
 * @return mSdkOAuthHelperInstance object of {@link SdkOAuthHelper} class
 */
public static SdkOAuthHelper getInstance() {
    return mSdkOAuthHelperInstance;
}

/**
 * @return true if {@link SdkOAuthTokenHelper} is initialized false otherwise
 */
public static boolean checkIfInitialized() {
    return isSdkOAuthHelperInitialized;
}

/**
 * Initializes the Secure Preferences
 *
 * @throws GeneralSecurityException if SecurePreferences initialization fails
 */
private static void initSecurePrefs(@NonNull Context context) throws SdkException {
    try {
        String pass = AesCbcWithIntegrity.generateKey ().toString ();
        mSecurePreferences = new SecurePreferences (context, pass, SHARED_PREFS_STORE_NAME);
    } catch (GeneralSecurityException gse) {
        throw new SdkException (SdkException.SECURE_PREFERENCES_NOT_INITIALIZED, "Secure Store initialization error");
    }
}

/**
 * Reads the AuthState Json data from the Shared Prefs Should be moved soon
 *
 * @return {@see AuthState} object
 */
private static AuthState readAuthStateFromSharedPrefs() throws SdkException {
    if (mSecurePreferences != null) {
        String decryptedString = mSecurePreferences.getString (SHARED_PREFS_KEY_NAME, "");
        AuthState state = null;
        if (!TextUtils.isEmpty (decryptedString)) {
            try {
                state = AuthState.jsonDeserialize (decryptedString);
            } catch (JSONException e) {
                Log.e ("", "readAuthStateFromSharedPrefs :: Error reading JSON file " + e.getMessage ());
                throw new SdkException (SdkException.AUTH_STATE_READ_FAILED, "Reading Auth state from Storage failed" + e.getMessage ());

            }
        }
        return state;
    } else {
        throw new SdkException (SdkException.SECURE_PREFERENCES_NOT_INITIALIZED, "Initialize Secure Preferences first :: #initSecurePrefs");
    }

}

static AuthState getAuthState() throws SdkException {
    if (mAuthState == null || mAuthState.getAccessToken () == null) {
        mAuthState = readAuthStateFromSharedPrefs ();

        if (mAuthState == null) {
            mAuthState = new AuthState ();
        }
    }
    return mAuthState;
}

/**
 * Initiates the Auth Service with no Browser Whitelist
 *
 * @param context {@link Context} Object
 */
private void initAuthService(@NonNull Context context) {
    AppAuthConfiguration appAuthConfig;
    appAuthConfig = new AppAuthConfiguration.Builder ().build ();
    initAuthService (appAuthConfig, context);
}

/**
 * Initiates the Auth Service with user defined browser whitelist
 *
 * @param context  {@link Context} Object
 * @param browsers {@link BrowserWhitelist} Object
 */
private void initAuthService(@NonNull Context context, @NonNull BrowserWhitelist browsers) {
    AppAuthConfiguration appAuthConfig;

    if (browsers != null) {
        appAuthConfig = new AppAuthConfiguration.Builder ().setBrowserMatcher (browsers).build ();
        initAuthService (appAuthConfig, context);
    }
}

/**
 * Initializes the AuthService with custom AppAuthConfiguration
 *
 * @param appAuthConfig {@link AuthorizationService} Object
 * @param context       {@link Context} Object
 */
private void initAuthService(@NonNull AppAuthConfiguration appAuthConfig, @NonNull Context context) {
    mAuthService = new AuthorizationService (context, appAuthConfig);
}

/**
 * Persists the Auth to memory, Accessible only within generated code
 *
 * @param state {@link AuthState} object
 */
void writeAuthStateToSharedPrefs(@NonNull AuthState state) {

    mSecurePreferences.edit ().putString (SHARED_PREFS_KEY_NAME, state.jsonSerializeString ()).apply ();

}

/**
 * Resets the Auth state
 */
private void resetAuthStateInSharedPrefs() {
    mSecurePreferences.edit ().putString (SHARED_PREFS_KEY_NAME, "").apply ();
}

/**
 * Generates the {@link AuthorizationRequest} object
 *
 * @param serviceConfig {@link AuthorizationServiceConfiguration} object
 * @return AuthorizationRequest Object
 */
private AuthorizationRequest getAuthorizationRequest(final @NonNull Context context, @NonNull AuthorizationServiceConfiguration serviceConfig) throws SdkException {

    checkClientCredentials ();

    AuthorizationRequest authRequest = null;


    // Log.d(logTag, authRequest != null ? authRequest.toUri().toString() : null);
    return authRequest;
  }

private void checkClientCredentials() throws SdkException {

    if (TextUtils.isEmpty (getSelectedIDP ().getClientId ()) && TextUtils.isEmpty (getSelectedIDP ().getClientSecret ())) {
        throw new SdkException (SdkException.OAUTHPASSWORD_CLIENT_CREDENTIALS_MISSING, "Client ID /Client Secret not set");
    }
    if (getSelectedIDP ().getClientId ().equalsIgnoreCase ("SOME_CLIENT_ID") && getSelectedIDP ().getClientSecret ().equalsIgnoreCase ("SOME_CLIENT_SECRET")) {
        throw new SdkException (SdkException.OAUTHPASSWORD_CLIENT_CREDENTIALS_MISSING, "Client ID /Client Secret not set");

    }
}

/**
 * Performs authorization for OAuth Password or OAuth Application
 *
 * @param idp      {@link SdkIdentityProvider}
 * @param activity {@link Activity}
 * @throws SdkException Custom Exception
 */
public void doOAuth2Legged(@NonNull SdkIdentityProvider idp, final @NonNull Activity activity) throws SdkException {
    setSelectedIDP (idp);
    SdkClient.getInstance().setAuthenticationType(idp.getIDPName());
    if (SdkUtils.isCurrentlyOnMainUIThread ()) {
        throw new SdkException (SdkException.ERROR_RUNNING_ON_UI_THREAD, "Authentication is running on UI thread. Please call it inside an Async task or a thread");
    }

    final AuthorizationServiceConfiguration.RetrieveConfigurationCallback retrieveCallback = new AuthorizationServiceConfiguration.RetrieveConfigurationCallback () {

        @Override
        public void onFetchConfigurationCompleted(@Nullable AuthorizationServiceConfiguration serviceConfiguration, @Nullable AuthorizationException ex) {

            if (ex != null) {
                Log.w (logTag, "Failed to retrieve configuration for " + getSelectedIDP ().getIDPName (), ex);
            } else {
                final String headerUserName = "username";
                final String headerPass = "password";
                final String headerClientSecret = "client_secret";
                try {
                    Map<String, String> additionalParameters = new HashMap<> ();
                    String grant_type = "";
                    String scopes = "";

                    TokenRequest formattedRequest = (new TokenRequest.Builder (serviceConfiguration, getSelectedIDP ().getClientId ())).setGrantType (grant_type).setScope (scopes).setClientId (getSelectedIDP ().getClientId ()).setAdditionalParameters (additionalParameters).build ();


                    try {
                        final ClientAuthentication clientAuthentication = SdkOAuthHelper.getAuthState ().getClientAuthentication ();

                        getAuthService ().performTokenRequest (formattedRequest, clientAuthentication, new AuthorizationService.TokenResponseCallback () {

                            @Override
                            public void onTokenRequestCompleted(@Nullable TokenResponse tokenResponse, @Nullable AuthorizationException ex) {
                                try {

                                    // Update the AuthState
                                    getAuthState ().update (tokenResponse, ex);

                                    // Save the Auth State
                                    SdkOAuthHelper.getInstance ().writeAuthStateToSharedPrefs (getAuthState ());

                                    // Call back
                                    SdkOAuthTokenHelper.getInstance ().processTokenCallback (activity);

                                } catch (SdkException e) {
                                    Log.e (logTag, "OAuth Exception ----" + e.getMessage ());
                                }

                            }
                        });
                    } catch (SdkException e) {
                        Log.e (logTag, "getClientAuthentication Exception -- " + e.getMessage ());
                    }
                } catch (ClientAuthentication.UnsupportedAuthenticationMethod cex) {
                    Log.e (logTag, "Token request cannot be made, client authentication for the token " + "endpoint could not be constructed (%s)" + cex.getMessage ());
                }

            }
        }
    };
    idp.retrieveConfig (retrieveCallback);

}

public void doOAuth3Legged(@NonNull SdkIdentityProvider idp, final @NonNull Context context) throws SdkException {
    doOAuth3Legged (idp, context, context.getClass ());
}

/**
 * Performs the actual SdkOAuthHelper calls.
 *
 * @param idp              {@link SdkIdentityProvider} Object
 * @param callBackActivity {@link Activity} The callback Activity class
 * @throws SdkException Exception if something goes wrong
 */
public void doOAuth3Legged(@NonNull SdkIdentityProvider idp, final @NonNull Context context, final @Nullable Class callBackActivity) throws SdkException {
    setSelectedIDP (idp);
    SdkClient.getInstance().setAuthenticationType(idp.getIDPName());
    if (SdkUtils.isCurrentlyOnMainUIThread ()) {
        throw new SdkException (SdkException.ERROR_RUNNING_ON_UI_THREAD, "Authentication is running on UI thread. Please call it inside an Async task or a thread");
    }

    if (TextUtils.isEmpty (getSelectedIDP ().getClientSecret ()) && TextUtils.isEmpty (getSelectedIDP ().getClientId ())) {
        throw new SdkException (SdkException.OAUTHPASSWORD_CLIENT_CREDENTIALS_MISSING, "OAuth Client Credentials missing. Set them to the Identity Provider");
    }

    final AuthorizationServiceConfiguration.RetrieveConfigurationCallback retrieveCallback = new AuthorizationServiceConfiguration.RetrieveConfigurationCallback () {

        @Override
        public void onFetchConfigurationCompleted(@Nullable AuthorizationServiceConfiguration serviceConfiguration, @Nullable AuthorizationException ex) {

            if (ex != null) {
                Log.e (logTag, "Failed to retrieve configuration for " + getSelectedIDP ().getIDPName (), ex);
            } else {
                Log.d (logTag, "configuration retrieved for " + getSelectedIDP ().getIDPName () + ", proceeding");

                if (serviceConfiguration != null) {
                    AuthorizationRequest authRequest = null;
                    try {
                        authRequest = getAuthorizationRequest (context, serviceConfiguration);
                    } catch (SdkException e) {
                        Log.e (logTag, "retrieveCallback , Auth Request Creation failed");
                        throw new RuntimeException ("Auth Request Creation failed - " + e.getMessage ());
                    }
                    if (authRequest != null) {
                        try {
                            PendingIntent postAuthIntent = SdkOAuthTokenHelper.createPostAuthorizationIntent (context, callBackActivity, authRequest, SdkOAuthHelper.getAuthState ());
                            CustomTabsIntent tabsIntent = SdkOAuthHelper.getInstance ().getAuthService ().createCustomTabsIntentBuilder ().setToolbarColor (Integer.parseInt ("FF4081", 16)).build ();
                            SdkOAuthHelper.getInstance ().getAuthService ().performAuthorizationRequest (authRequest, postAuthIntent, tabsIntent);

                        } catch (SdkException e) {
                            Log.e (logTag, "performAuthorizationRequest request failed as AuthState get failed." + e.getMessage ());
                        }
                    }
                }

            }
        }
    };
    idp.retrieveConfig (retrieveCallback);
}

/**
 * Disposes the SdkAuthentication State
 */
public void disposeAuthenticationState() {
    getAuthService ().dispose ();
    if (mAuthState != null) {
        mAuthState.resetAuthState ();
    }
    this.resetAuthStateInSharedPrefs ();
    isSdkOAuthHelperInitialized = false;
}

/**
 * Initializes a request, by adding the Authorization header to the request.
 *
 * @param request @see HTTPRequest
 */
@Override
public void initialize(HttpRequest request) throws IOException {
    try {
        HttpHeaders authHeaders = new HttpHeaders ();
         if (getAuthState() != null && getAuthState().getAccessToken() != null) {
            String token = mAuthState.getAccessToken();
            authHeaders.setAuthorization("Bearer " + token);
            authHeaders.fromHttpHeaders(request.getHeaders());
            request.setHeaders(authHeaders);
        }
    } catch (Exception e) {
        Log.e (logTag, " HttpRequestInitializer initialize exception e =" + e.getMessage ());
        throw new IOException ("Initialize SdkOAuthHelper Failed");
    }
}

/**
 * Log out the user
 */
public void logoutUser() {
    disposeAuthenticationState ();
    resetAuthStateInSharedPrefs ();
    if (mAuthState != null) {
        mAuthState.resetAuthState ();
        resetAuthStateInSharedPrefs ();
    }
}

/**
 * Checks if the parameters for the Authentication are available
 *
 * @return true if available, false if NOT available
 */
@Override
public boolean isAvailable() {
    try {
        if (TextUtils.isEmpty (getAuthState ().getAccessToken ())) {
            return false;
        }
    } catch (SdkException e) {
        return false;
    }
    return true;
}

/**
 * Gets the Selected IDP
 *
 * @return selectedIDP {@link SdkIdentityProvider} object
 */
public SdkIdentityProvider getSelectedIDP() {
    return mSelectedIDP;
}

/**
 * Sets the Selected IDP
 *
 * @param selectedIDP {@link SdkIdentityProvider} object
 */
private void setSelectedIDP(@NonNull SdkIdentityProvider selectedIDP) {
    mSelectedIDP = selectedIDP;
}

/**
 * Returns the AuthService object Accessible within generated code
 *
 * @return @see AuthorizationService
 */
AuthorizationService getAuthService() {
    return mAuthService;
}

}
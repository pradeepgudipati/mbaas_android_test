package com.axway.mbaas_preprod.auth;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.axway.mbaas_preprod.SdkConstants;

import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.AuthorizationServiceConfiguration.RetrieveConfigurationCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An abstraction of identity providers, containing all necessary info for OAuth Authentications
 */
@SuppressWarnings("SameParameterValue")
public class SdkIdentityProvider implements SdkConstants{


@NonNull
private final String mAuthName;
@NonNull
private final Uri mAuthEndpoint;
@NonNull
private final Uri mTokenEndpoint;
@NonNull
private final Uri mRedirectUri;
@NonNull
private final String mScope;
@NonNull
private String mClientId;
@NonNull
private String mClientSecret;
@NonNull
private String mOAuthPassUserName;
@NonNull
private String mOAuthPassPassword;

private SdkIdentityProvider(@NonNull String name, @NonNull String authEndpoint,
                            @Nullable String tokenEndpoint, @Nullable String clientId,
                            @Nullable String clientSecret, @NonNull String redirectScheme,
                            @NonNull String redirectHost, @NonNull String redirectPath,
                            @Nullable String scope){

    this.mAuthName = name;
    this.mAuthEndpoint = Uri.parse(authEndpoint);
    this.mTokenEndpoint = Uri.parse(tokenEndpoint);
    this.mClientId = clientId;
    this.mClientSecret = clientSecret;
    this.mRedirectUri = getUriResource(redirectScheme, redirectHost, redirectPath);
    this.mScope = scope;
}

/**
 * Gets the Providers List
 *
 * @return @{@link List} of {@link SdkIdentityProvider} objects
 */
public static HashMap<String, SdkIdentityProvider> getAllIdentityProviders(){
    HashMap<String, SdkIdentityProvider> mAllIdentityProviders = new HashMap<>();

    return mAllIdentityProviders;
}

/**
 * Gets the Providers List
 *
 * @return @{@link List} of {@link SdkIdentityProvider} objects
 */
public static List<String> getAllIDPNames(){
    List<String> allIDPNames = new ArrayList<>();
    allIDPNames.add(NAME_API_AUTH);
    allIDPNames.add(NAME_NO_AUTH);
    return allIDPNames;
}

private static Uri getUriResource(@NonNull String scheme, @NonNull String host, @NonNull String path){
    StringBuilder url = new StringBuilder();
    if(!TextUtils.isEmpty(scheme)){
        url.append(scheme).append("://");
    }
    if(!TextUtils.isEmpty(host)){
        url.append(host);
    }
    if(!TextUtils.isEmpty(host)){
        url.append(path);
    }
    return Uri.parse(url.toString());
}

public String getIDPName(){
    return mAuthName;
}

@Nullable
public Uri getAuthEndpoint(){
    return mAuthEndpoint;
}

@Nullable
public Uri getTokenEndpoint(){
    return mTokenEndpoint;
}

public String getClientId(){
    return mClientId;
}

public void setClientId(@NonNull String clientId){
    mClientId = clientId;
}

public String getClientSecret(){
    return mClientSecret;
}

public void setClientSecret(@NonNull String clientSecret){
    mClientSecret = clientSecret;
}

@NonNull
public Uri getRedirectUri(){
    return mRedirectUri;
}

@NonNull
public String getScope(){
    return mScope;
}

public void retrieveConfig(RetrieveConfigurationCallback callback){
    AuthorizationServiceConfiguration config = new AuthorizationServiceConfiguration(mAuthEndpoint, mTokenEndpoint, null);
    callback.onFetchConfigurationCompleted(config, null);
}

@NonNull
public String getOAuthPassUserName(){
    return mOAuthPassUserName;
}

public void setOAuthPassUserName(@NonNull String mOAuthPassUserName){
    this.mOAuthPassUserName = mOAuthPassUserName;
}

@NonNull
public String getOAuthPassPassword(){
    return mOAuthPassPassword;
}

public void setOAuthPassPassword(@NonNull String mOAuthPassPassword){
    this.mOAuthPassPassword = mOAuthPassPassword;
}

@Override
public String toString(){
    return "SdkIdentityProvider{" + "mAuthName='" + mAuthName + '\'' + ", mAuthEndpoint=" + mAuthEndpoint + ", mTokenEndpoint=" + mTokenEndpoint + ", mRedirectUri=" + mRedirectUri + ", mScope='" + mScope + '\'' + ", mClientId='" + mClientId + '\'' + ", mClientSecret='" + mClientSecret + '\''
                   + ", mOAuthPassUserName='" + mOAuthPassUserName + '\'' + ", mOAuthPassPassword='" + mOAuthPassPassword + '\'' + '}';
}
}

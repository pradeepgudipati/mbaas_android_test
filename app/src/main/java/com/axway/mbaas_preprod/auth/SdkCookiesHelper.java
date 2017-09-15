
 

package com.axway.mbaas_preprod.auth;


import android.util.Log;

import com.axway.mbaas_preprod.SdkException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;

/**
 * Cookie Handler & Parser Class
 */

public class SdkCookiesHelper implements SdkAuthentication{

private static List<HttpCookie> cookiesList;

private static SdkCookiesHelper instance;

public static SdkCookiesHelper getInstance() throws SdkException{
    if(instance != null){
        return instance;
    }else{
        throw new SdkException(SdkException.COOKIE_NOT_INITIALIZED, "Cookie not initialized");
    }
}


public SdkCookiesHelper(String cookieValue){
    List<HttpCookie> cookiesList = null;

    if(cookieValue.startsWith("[") || cookieValue.startsWith("{") || cookieValue.startsWith("(")){
        cookiesList = HttpCookie.parse(cookieValue.substring(1, cookieValue.length() - 2));
    }
    
    if(cookiesList.get(0).getValue() != null && cookiesList.get(0).getValue().length() > 0){
        setCookieValue(cookiesList);
    }
    instance = this;
}

/**
 * Log out the user
 */
@Override
public void logoutUser(){
    cookiesList.clear();
    instance = null;
}

/**
 * Checks if the parameters for the Authentication are available
 *
 * @return true if available, false if NOT available
 */
@Override
public boolean isAvailable(){
    if(getCookieValue() != null && getCookieValue().size() > 0){
        return true;
    }else{
        return false;
    }
}

/**
 * Initializes a request.
 *
 * @param request HTTP request
 */
@Override
public void initialize(HttpRequest request) throws IOException{
    if(isAvailable()){
        HttpHeaders headers = request.getHeaders();
        StringBuffer cookieToAdd = new StringBuffer();
        for(HttpCookie cookie : cookiesList){
            cookieToAdd.append(cookie.getName() + "=" + cookie.getValue()).append(";");
        }
        headers.setCookie(cookieToAdd.toString());
        request.setHeaders(headers);
    }

}


public List<HttpCookie> getCookieValue(){
    return cookiesList;
}

public void setCookieValue(List<HttpCookie> cookieValues){
    cookiesList = cookieValues;
}

}

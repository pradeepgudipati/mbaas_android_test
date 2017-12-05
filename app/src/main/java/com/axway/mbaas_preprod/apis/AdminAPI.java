
 

package com.axway.mbaas_preprod.apis;

import android.os.Looper;

import com.google.gson.reflect.TypeToken;

import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.Pair;
import com.axway.mbaas_preprod.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.axway.mbaas_preprod.models.*;
import com.google.gson.reflect.TypeToken;

public class AdminAPI {

	private SdkClient client;

	public AdminAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Login as admin using dashboard authentication or dashboard secret token(x-auth-token). x-auth-token has high priority than connect.sid if they are existing at the same time.
	 * 
	 * 
	 * @param connectSid App name
	 * @param groupId 
	 * @param ct If the app is enterprise.
	 * @param xAuthToken 
	 * @param email 
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse adminLogin360(String connectSid, String groupId, String ct, String xAuthToken, String email) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'connectSid' is set
		if (connectSid == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'connect.sid' when calling adminLogin360");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/admins/login360.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "connect.sid", connectSid));
		queryParams.addAll(client.parameterToPairs("", "group_id", groupId));
		queryParams.addAll(client.parameterToPairs("", "ct", ct));
		queryParams.addAll(client.parameterToPairs("", "x-auth-token", xAuthToken));
		queryParams.addAll(client.parameterToPairs("", "email", email));


		
		//If a file is being uploaded in this API then the contentType should have "multipart/form-data" only or as the first entry in the contentTypes array.
		// Please set the order to have multipart/form-data as the first entry 
		// e.g.   final String[] contentTypes = {
        //             "multipart/form-data", "application/x-www-form-urlencoded"
        //       };
		final String[] accepts = {
			"application/json"
		};

		final String accept = client.selectHeaderAccept(accepts);

		final String[] contentTypes = {
			"application/x-www-form-urlencoded"
		};
		final String contentType = client.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] { "api_key" };

		Result result = client.invokeAPI(localVarPath, "get", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

}

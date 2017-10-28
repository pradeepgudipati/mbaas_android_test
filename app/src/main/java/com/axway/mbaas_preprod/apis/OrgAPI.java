
 

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

public class OrgAPI {

	private SdkClient client;

	public OrgAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Set production apps as the package info in the global app db.
	 * Set production apps as the package info in the global app db.
	 * 
	 * @param orgId Set production apps as the package info in the global app db.
	 * @param packageInfo A hash string which must include 'apiRateMinute' and 'allowProduction', the 'type' of 'packageInfo' can be 'free', 'starter', 'trial', 'professional', or 'enterprise'.
	 * @param xAuthToken The dashboard access token.
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject orgOrgUpdate(String orgId, String xAuthToken, String packageInfo) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'orgId' is set
		if (orgId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'org_id' when calling orgOrgUpdate");
		}
		// verify the required parameter 'xAuthToken' is set
		if (xAuthToken == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'x-auth-token' when calling orgOrgUpdate");
		}
		// verify the required parameter 'packageInfo' is set
		if (packageInfo == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'packageInfo' when calling orgOrgUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/org/{org_id}".replaceAll("\\{format\\}","json")
			.replaceAll("\\{org_id\\}", client.escapeString(orgId.toString()));
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();


		if (xAuthToken != null) headerParams.put("x-auth-token", client.parameterToString(xAuthToken));

		if (packageInfo != null) formParams.put("packageInfo", packageInfo);
		
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

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Used to disable the production apps according to setting the &#39;status&#39; to 1 in the global_apps collection.
	 * Used to disable the production apps according to setting the 'status' to 1 in the global_apps collection.
	 * 
	 * @param orgId Used to disable the production apps according to setting the 'status' to 1 in the global_apps collection.
	 * @param xAuthToken The dashboard access token.
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject orgOrgDelete(String orgId, String xAuthToken) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'orgId' is set
		if (orgId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'org_id' when calling orgOrgDelete");
		}
		// verify the required parameter 'xAuthToken' is set
		if (xAuthToken == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'x-auth-token' when calling orgOrgDelete");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/org/{org_id}".replaceAll("\\{format\\}","json")
			.replaceAll("\\{org_id\\}", client.escapeString(orgId.toString()));
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();


		if (xAuthToken != null) headerParams.put("x-auth-token", client.parameterToString(xAuthToken));

		
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

		Result result = client.invokeAPI(localVarPath, "delete", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

}

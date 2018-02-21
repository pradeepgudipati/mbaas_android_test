 

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

public class SocialIntegrationsAPI {

	private SdkClient client;

	public SocialIntegrationsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Find Facebook Friends
	 * Find the current user's Facebook Friends who also registered in the same App.

	 * 
	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject socialIntegrationsFacebookSearchFriends(Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/social/facebook/search_friends.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "pretty_json", prettyJson));


		
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
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Link an external Account
	 * Associates an external account with an existing Appcelerator Cloud Services
user account. Registered and logged in Appcelerator Cloud Services users can
link one or more external accounts to their existing account. Once linked, the
user can login using either Appcelerator Cloud Services account or any of the
linked external accounts.

	 * 
	 * @param iD External account's user ID. Optional for Facebook; if ID is missing and `type`
is `facebook`, Appcelerator Cloud Services uses the Facebook token to obtain
the user ID.

	 * @param type Type of the external account, for example, "facebook", "linkedin", or
"twitter".

	 * @param token Token provided by the external account. Currently only Facebook tokens are
validated  by the Appcelerator Cloud Services server.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject socialIntegrationsExternalAccountLink(String iD, String type, String token, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'iD' is set
		if (iD == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'id' when calling socialIntegrationsExternalAccountLink");
		}
		// verify the required parameter 'type' is set
		if (type == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'type' when calling socialIntegrationsExternalAccountLink");
		}
		// verify the required parameter 'token' is set
		if (token == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'token' when calling socialIntegrationsExternalAccountLink");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/external_account_link.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (iD != null) formParams.put("id", iD);
		if (type != null) formParams.put("type", type);
		if (token != null) formParams.put("token", token);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		
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

		Result result = client.invokeAPI(localVarPath, "post", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Login with external account
	 * Users can login using an external account such as Facebook, Twitter,
Linkedin, etc without creating an account with Appcelerator Cloud Services
ahead of time. The external account login creates a Appcelerator Cloud
Services account if it hasn't been created, otherwise, it will login using the
user who has the matching external account info.

	 * 
	 * @param iD External account's user ID. Optional for Facebook; if ID is missing and `type`
is `facebook`, Appcelerator Cloud Services uses the Facebook token to obtain
the user ID.

	 * @param type Type of the external account, for example, "facebook", "linkedin", or
"twitter".

	 * @param token Token provided by the external account. Currently only Facebook tokens are
validated  by the Appcelerator Cloud Services server.

	 * @param aclName Name of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject socialIntegrationsExternalAccountLogin(String iD, String type, String token, String aclName, String aclId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'iD' is set
		if (iD == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'id' when calling socialIntegrationsExternalAccountLogin");
		}
		// verify the required parameter 'type' is set
		if (type == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'type' when calling socialIntegrationsExternalAccountLogin");
		}
		// verify the required parameter 'token' is set
		if (token == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'token' when calling socialIntegrationsExternalAccountLogin");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/external_account_login.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (iD != null) formParams.put("id", iD);
		if (type != null) formParams.put("type", type);
		if (token != null) formParams.put("token", token);
		if (aclName != null) formParams.put("acl_name", aclName);
		if (aclId != null) formParams.put("acl_id", aclId);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		
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

		Result result = client.invokeAPI(localVarPath, "post", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Unlink an external account
	 * Disassociate an external account from a Appcelerator Cloud Services user
account.

	 * 
	 * @param iD External account's user ID.

	 * @param type Type of the external account, for example, "facebook", "linkedin", or
"twitter".

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject socialIntegrationsExternalAccountUnlink(String iD, String type, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'iD' is set
		if (iD == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'id' when calling socialIntegrationsExternalAccountUnlink");
		}
		// verify the required parameter 'type' is set
		if (type == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'type' when calling socialIntegrationsExternalAccountUnlink");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/external_account_unlink.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (iD != null) formParams.put("id", iD);
		if (type != null) formParams.put("type", type);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		
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

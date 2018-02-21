 

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

public class LogsAPI {

	private SdkClient client;

	public LogsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Query details about a specific push notification log item.
	 * Performs a custom query for details about a specific Arrow push notification log item specified
in the query's `where` clause. Returns a {@link PushLogDetails} object for the specified log item.

	 * 
	 * @param where Where clause whose value is JSON object consisting of the field name "push_id"
and the ID of the push notification log item to query for, for example:

    where={"push_id":"<PUSHLOG_ID>"}

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject logsQuerypushlogdetails(String where) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'where' is set
		if (where == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'where' when calling logsQuerypushlogdetails");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/logs/querypushlogdetails.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "where", where));


		
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
	 * Query Arrow push notification logs.
	 * Performs a custom query of Arrow push notification logs with sorting and pagination. Returns
a list of {@link PushLogs} objects that matched the query parameters.

To get additional details about a specific PushLogs item, pass the value of PushLogs#_id to
a {@link Logs#querypushlogdetails} query.

	 * 
	 * @param where Constraint values for fields. `where` should be JSON encoded.

If `where` is not specified, query returns all objects.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject logsQuerypushlogs(String where) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/logs/querypushlogs.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "where", where));


		
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

}

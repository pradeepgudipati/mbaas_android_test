
 

package com.axway.mbaas_preprod.apis;

import android.os.Looper;

import com.axway.mbaas_preprod.Pair;
import com.axway.mbaas_preprod.Result;
import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ACLsAPI {

	private SdkClient client;

	public ACLsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Add user(s) to an ACL.
	 * Adds one or more user(s) to an existing ACL object, identified by its `id` or `name`.

	 * 
	 * @param name Name of the ACL object.

Either `name` or `id` must be specified.

	 * @param iD ID of the ACL oject.

Either `name` or `id` must be specified.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param readerIds Comma separated list of IDs identifying users who can read objects
controlled by this ACL.

	 * @param writerIds Comma separated list of IDs identifying users who can update an object.
controlled by this ACL.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsAdd(String name, String iD, Boolean prettyJson, String readerIds, String writerIds) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'readerIds' is set
		if (readerIds == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'reader_ids' when calling aCLsAdd");
		}
		// verify the required parameter 'writerIds' is set
		if (writerIds == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'writer_ids' when calling aCLsAdd");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/add.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (name != null) formParams.put("name", name);
		if (iD != null) formParams.put("id", iD);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		if (readerIds != null) formParams.put("reader_ids", readerIds);
		if (writerIds != null) formParams.put("writer_ids", writerIds);
		
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
	 * Checks a user&#39;s permission in an ACL.
	 * Checks the permissions a specified user is granted by a specified ACL.
In the response, "read_permission": "yes" means the user has read permission; if
this property is omitted or the value is not "yes", the user does not have read
permission.

Similarly, "write_permission": "yes" means the user has write permission. If the
property is omitted or the value is not "yes", the user does not have write
permission.

	 * 
	 * @param name Name of the ACL object.

Either `name` or `id` must be specified.

	 * @param iD ID of the ACL oject.

Either `name` or `id` must be specified.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param userId User ID of the user to check.
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsCheck(String name, String iD, Boolean prettyJson, String userId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/check.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "name", name));
		queryParams.addAll(client.parameterToPairs("", "id", iD));
		queryParams.addAll(client.parameterToPairs("", "pretty_json", prettyJson));
		queryParams.addAll(client.parameterToPairs("", "user_id", userId));


		
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
	 * Retrieves the total number of ACL objects.
	 * Retrieves the total number of ACL objects.
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsCount() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/count.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		
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
	 * Create an access control list
	 * Creates an ACL object, which can be used to control access to ArrowDB objects.

	 * 
	 * @param name Name of the ACL object.

	 * @param readerIds Comma separated list of IDs identifying users who can read objects
controlled by this ACL.

	 * @param writerIds Comma separated list of IDs identifying users who can update an object.
controlled by this ACL.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param publicRead Determines whether objects controlled by this ArrowDB are publically readable.

Default is false.

	 * @param publicWrite Determines whether objects controlled by this ArrowDB are publically writable.

Default is false.

	 * @param suId Specifies the owner of the new URL.

Only allowed if the current login user is an application admin. Otherwise, the
new ACL is always owned by the current login user.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsCreate(String name, String readerIds, String writerIds, Boolean prettyJson, String publicRead, String publicWrite, String suId) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'name' is set
		if (name == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'name' when calling aCLsCreate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (name != null) formParams.put("name", name);
		if (readerIds != null) formParams.put("reader_ids", readerIds);
		if (writerIds != null) formParams.put("writer_ids", writerIds);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		if (publicRead != null) formParams.put("public_read", publicRead);
		if (publicWrite != null) formParams.put("public_write", publicWrite);
		if (suId != null) formParams.put("su_id", suId);
		
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
	 * Delete an ACL
	 * Deletes an ACL object with the given `id` or `name`.

An application admin can delete any ACL object.

	 * 
	 * @param iD ID of the ACL oject to delete.

Either `name` or `id` must be specified.

	 * @param name Name of the ACL object to delete.

Either `name` or `id` must be specified.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param suId User to delete the ACL object on behalf of. The user must be the creator of the object.

The current user must be an application admin to remove an ACL on
behalf of another user.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsDelete(String iD, String name, Boolean prettyJson, String suId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (iD != null) formParams.put("id", iD);
		if (name != null) formParams.put("name", name);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		if (suId != null) formParams.put("su_id", suId);
		
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

	/**
	 * Performs a custom query of ACLs.
	 * Performs a custom query of ACLs. Regular application users can only query ACLs that they have created. 
Application admins can query ACLs for an arbitrary user by specifying the `user_id` method parameter.
(In applications created with ArrowDB 1.1.7 and earlier, any user can query another user's 
ACLs, regardless of whether they are an admin or not.)

<p class="note">The <code>name</code> field is not queryable.</p>

* Applications created with ArrowDB 1.1.5 and later can paginate query results using `skip` 
and `limit` parameters. For details, see [Query Pagination](#!/guide/search_query-section-query-pagination).
* Currently you can not query or sort data stored inside an array or hash in custom fields.

For general information on queries, see [Search and Query guide](#!/guide/search_query).

	 * 
	 * @param count Used for paginating queries. If set to `true`, the response's `meta` object contains a 
`count` field that indicates the number of objects that matched the query constraints.

	 * @param page <p class="note">
Starting in ArrowDB 1.1.5, page and per_page are no longer supported in query operations. 
Applications should instead use <strong>skip</strong> and <strong>limit</strong> 
query parameters.
</p>

	 * @param perPage <p class="note">
Starting in ArrowDB 1.1.5, page and per_page are no longer supported in query operations. 
Applications should instead use <strong>skip</strong> and <strong>limit</strong> 
query parameters.
</p>

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param limit The number of records to fetch. The value must be greater than 0, and no greater than 
1000, or an HTTP 400 (Bad Request) error will be returned. Default value of `limit` is 10.

	 * @param skip The number of records to skip. The value must be greater than or equal to 0, and no greater 
than 4999, or an HTTP 400 error will be returned. To skip 5000 records or more 
you need to perform a range-based query. See 
<a href="#!/guide/search_query-section-query-pagination">Query Pagination</a> for more information.</p>

	 * @param order Sort results by one or more fields.

	 * @param sel Selects the object fields to display. Do not use this parameter with `unsel`.

	 * @param unsel Selects the object fields NOT to display. Do not use this parameter with `sel`.

	 * @param responseJsonDepth Nested object depth level counts in response json.
In order to reduce server API calls from an application, the response json may
include not just the objects that are being queried/searched, but also with
some important data related to the returning objects such as object's owner or
referencing objects.

Default is 1, valid range is 1 to 8.

	 * @param userId ID of the user whose ACLs should be returned. You must be an application admin to use this 
parameter.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsQuery(Boolean count, Integer page, Integer perPage, Boolean prettyJson, Integer limit, Integer skip, String order, String sel, String unsel, Integer responseJsonDepth, String userId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/query.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "count", count));
		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "pretty_json", prettyJson));
		queryParams.addAll(client.parameterToPairs("", "limit", limit));
		queryParams.addAll(client.parameterToPairs("", "skip", skip));
		queryParams.addAll(client.parameterToPairs("", "order", order));
		queryParams.addAll(client.parameterToPairs("", "sel", sel));
		queryParams.addAll(client.parameterToPairs("", "unsel", unsel));
		queryParams.addAll(client.parameterToPairs("", "response_json_depth", responseJsonDepth));
		queryParams.addAll(client.parameterToPairs("", "user_id", userId));


		
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
	 * Remove user(s) from an ACL
	 * Removes one or more user(s) from an ACL object with the given `id` or `name`.

You can remove users from the `readers` list, which grants read permission, the
`writers` list, which grants update/delete permission, or both.

	 * 
	 * @param name Name of the ACL object.

Either `name` or `id` must be specified.

	 * @param iD ID of the ACL oject.

Either `name` or `id` must be specified.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param readerIds Comma separated list of IDs to remove from the `readers` list.

	 * @param writerIds Comma separated list of IDs to remove from the `writers` list.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsRemove(String name, String iD, Boolean prettyJson, String readerIds, String writerIds) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'readerIds' is set
		if (readerIds == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'reader_ids' when calling aCLsRemove");
		}
		// verify the required parameter 'writerIds' is set
		if (writerIds == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'writer_ids' when calling aCLsRemove");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/remove.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (name != null) formParams.put("name", name);
		if (iD != null) formParams.put("id", iD);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		if (readerIds != null) formParams.put("reader_ids", readerIds);
		if (writerIds != null) formParams.put("writer_ids", writerIds);
		
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

	/**
	 * Show an ACL
	 * Shows the ACL object with the given `id` or `name`.

	 * 
	 * @param iD ID of the ACL oject.

Either `name` or `id` must be specified.

	 * @param name Name of the ACL object.

Either `name` or `id` must be specified.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsShow(String iD, String name, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "id", iD));
		queryParams.addAll(client.parameterToPairs("", "name", name));
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
	 * Update an ACL
	 * Updates an ACL object to change its access control list. When updating an ACL,
you can change the members of the `readers` list and the `writers` list, or change the value
of the `public_read` and `public_write` flags.

An application admin can update any ACL object.

	 * 
	 * @param iD ID of the ACL oject.

Either `name` or `id` must be specified.

	 * @param name Name of the ACL object.

Either `name` or `id` must be specified.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param readerIds Comma separated list of IDs identifying users who can read objects
controlled by this ACL.

To remove all users from the `readers` list, simply set `reader_ids=""`.
This removes all users except for the owner from the list.

	 * @param writerIds Comma separated list of IDs identifying users who can update an object.
controlled by this ACL.

To remove all users from the `writers` list, simply set `writer_ids=""`.
This removes all users except for the owner from the list.

	 * @param publicRead Determines whether objects controlled by this ArrowDB are publically readable.

Default is false.

	 * @param publicWrite Determines whether objects controlled by this ArrowDB are publically writable.

Default is false.

	 * @param suId User to update the ACL object on behalf of. The user must be the creator of the object.

The current user must be an application admin to update an ACL object on
behalf of another user.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject aCLsUpdate(String iD, String name, Boolean prettyJson, String readerIds, String writerIds, String publicRead, String publicWrite, String suId) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'readerIds' is set
		if (readerIds == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'reader_ids' when calling aCLsUpdate");
		}
		// verify the required parameter 'writerIds' is set
		if (writerIds == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'writer_ids' when calling aCLsUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/acls/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (iD != null) formParams.put("id", iD);
		if (name != null) formParams.put("name", name);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		if (readerIds != null) formParams.put("reader_ids", readerIds);
		if (writerIds != null) formParams.put("writer_ids", writerIds);
		if (publicRead != null) formParams.put("public_read", publicRead);
		if (publicWrite != null) formParams.put("public_write", publicWrite);
		if (suId != null) formParams.put("su_id", suId);
		
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

}

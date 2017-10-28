
 

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

public class CheckinsAPI {

	private SdkClient client;

	public CheckinsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Deletes multiple Checkin objects.
	 * Deletes Checkin objects that match the query constraints provided in the `where` parameter.
If no `where` parameter is provided, all Checkin objects are deleted. Note that an HTTP 200 code (success)
is returned if the call completed successfully but the query matched no objects.

For performance reasons, the number of objects that can be deleted in a single batch delete 
operation is limited to 100,000.

The matched objects are deleted asynchronously in a separate process. The {@link #place Place}, 
{@link #event Event}, or {@link #photo Photo} associated with any of the matched objects is 
not deleted.

You must be an application admin to run this command.

	 * 
	 * @param where Encoded JSON object that specifies constraint values for Checkins objects to delete.
If not specified, all Checkins objects are deleted.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject checkinsBatchDelete(String where) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/checkins/batch_delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (where != null) formParams.put("where", where);
		
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
	 * Retrieves the total number of Checkin objects.
	 * Retrieves the total number of Checkin objects.
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject checkinsCount() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/checkins/count.json".replaceAll("\\{format\\}","json");
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
	 * Checkin to a Place or Event
	 * Creates a checkin associated with either a {@link Places} or {@link Events} object. 

You should specify either a Places or Events object, but not both. If both are
provided, the Places object will be used.

A checkin message is optional. The optional photo parameter contains the
binary data stream representing the photo included with the checkin. If a
photo is included, the response includes a "processed" flag which indicates if
the photo has been resized and stored reliably in the Appcelerator Cloud
Services storage engine. This will be false initially be false.

	 * 
	 * @param placeId ID of the {@link Places} to check in to.

You can associate a checkin with either a {@link Places} or {@link Events} object but not both.

	 * @param eventId ID of the {@link Events} to check in to.

You can associate a checkin with either a {@link Places} or {@link Events} object but not both.

	 * @param message Message to attach to the checkin.
	 * @param photo New photo to attach as the primary photo for the checkin.

When you use the `photo` parameter to attach a new photo, you can use the
[custom resize and sync options](#!/guide/photosizes).

	 * @param photoId ID of an existing photo to attach as the primary photo for the checkin.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param responseJsonDepth Nested object depth level counts in response json.

In order to reduce server API calls from an application, the JSON response may
include not just the objects that are being queried/searched, but also
important data related to the queried objects, such as the object's owner or
referencing objects.

Default depth is 1. Valid values are 1-8.

	 * @param tags Comma separated list of tags for this checkin.

	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
	 * @param aclName Name of an {@link ACLs} to associate with this checkin object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this checkin object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId User ID to create the checkin on behalf of.

The current login user must be an application admin to create a checkin on
behalf of another user.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject checkinsCreate(String placeId, String eventId, String message, File photo, String photoId, Boolean prettyJson, Integer responseJsonDepth, String tags, String customFields, String aclName, String aclId, String suId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/checkins/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (placeId != null) formParams.put("place_id", placeId);
		if (eventId != null) formParams.put("event_id", eventId);
		if (message != null) formParams.put("message", message);
		if (photo != null) formParams.put("photo", photo);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		if (responseJsonDepth != null) formParams.put("response_json_depth", responseJsonDepth);
		if (tags != null) formParams.put("tags", tags);
		if (customFields != null) formParams.put("custom_fields", customFields);
		if (aclName != null) formParams.put("acl_name", aclName);
		if (aclId != null) formParams.put("acl_id", aclId);
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
			"application/x-www-form-urlencoded", "multipart/form-data"
		};
		final String contentType = client.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] { "api_key" };

		Result result = client.invokeAPI(localVarPath, "post", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Delete a Checkin
	 * Deletes a checkin. 

The {@link #place Place}, {@link #event Event}, or {@link #photo Photo} associated with the checkin
is not deleted.

An application admin can delete any Checkin object.

	 * 
	 * @param checkinId ID of the checkin to delete.
	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param suId User to delete the Checkin object on behalf of. The user must be the creator of the object.

The current user must be an application admin to delete a Checkin object on
behalf of another user.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject checkinsDelete(String checkinId, Boolean prettyJson, String suId) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'checkinId' is set
		if (checkinId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'checkin_id' when calling checkinsDelete");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/checkins/delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (checkinId != null) formParams.put("checkin_id", checkinId);
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
	 * Custom Query Checkins
	 * Performs a custom query of checkins with sorting and pagination. Currently you
can not query or sort data stored inside array or hash in custom fields.

In ArrowDB 1.1.5 and later, you can paginate query results using `skip` and `limit` parameters, or by including
a `where` clause to limit the results to objects whose IDs fall within a specified range.
For details, see [Query Pagination](#!/guide/search_query-section-query-pagination).

For details about using the query parameters,
see the [Search and Query guide](#!/guide/search_query).

	 * 
	 * @param page Request page number, default is 1.

<p class="note">This parameter is only available to ArrowDB applications created before ArrowDB 1.1.5. 
Applications created with ArrowDB 1.1.5 and later must use <a href="#!/guide/search_query-section-query-pagination">ranged-based queries</a> queries
to paginate their queries.</p>

	 * @param perPage Number of results per page, default is 10.

<p class="note">This parameter is only available to ArrowDB applications created before ArrowDB 1.1.5. 
Applications created with ArrowDB 1.1.5 and later must use <a href="#!/guide/search_query-section-query-pagination">ranged-based queries</a> queries
to paginate their queries.</p>

	 * @param limit The number of records to fetch. The value must be greater than 0, and no greater than 
1000, or an HTTP 400 (Bad Request) error will be returned. Default value of `limit` is 10.

	 * @param skip The number of records to skip. The value must be greater than or equal to 0, and no greater 
than 4999, or an HTTP 400 error will be returned. To skip 5000 records or more 
you need to perform a range-based query. See 
<a href="#!/guide/search_query-section-query-pagination">Query Pagination</a> for more information.</p>

	 * @param where Constraint values for fields. `where` should be encoded JSON.

If `where` is not specified, `query` returns all objects.

	 * @param order Sort results by one or more fields.

	 * @param sel Selects the object fields to display. Do not use this parameter with `unsel`.

	 * @param showUserLike If set to **true**, each Checkin object in the response includes "current_user_liked: true"`
if the current user has liked the object. If the user has not liked the object, the
`current_user_liked` field is not included in the response.

	 * @param unsel Selects the object fields NOT to display. Do not use this parameter with `sel`.

	 * @param responseJsonDepth Nested object depth level counts in response json.
In order to reduce server API calls from an application, the response json may
include not just the objects that are being queried/searched, but also with
some important data related to the returning objects such as object's owner or
referencing objects.

Default is 1, valid range is 1 to 8.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject checkinsQuery(Integer page, Integer perPage, Integer limit, Integer skip, String where, String order, String sel, Boolean showUserLike, String unsel, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/checkins/query.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "limit", limit));
		queryParams.addAll(client.parameterToPairs("", "skip", skip));
		queryParams.addAll(client.parameterToPairs("", "where", where));
		queryParams.addAll(client.parameterToPairs("", "order", order));
		queryParams.addAll(client.parameterToPairs("", "sel", sel));
		queryParams.addAll(client.parameterToPairs("", "show_user_like", showUserLike));
		queryParams.addAll(client.parameterToPairs("", "unsel", unsel));
		queryParams.addAll(client.parameterToPairs("", "response_json_depth", responseJsonDepth));
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
	 * Show a Checkin
	 * Returns the contents of the identified checkin.
	 * 
	 * @param checkinId ID of the checkin to show.
	 * @param responseJsonDepth Nested object depth level counts in response json.
In order to reduce server API calls from an application, the response json may
include not just the objects that are being queried/searched, but also with
some important data related to the returning objects such as object's owner or
referencing objects.

	 * @param showUserLike If set to **true** the Checkin object in the response will include `"current_user_liked: true"`
if the current user has liked the object. If the user has not liked the object, the
`current_user_liked` field is not included in the response.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject checkinsShow(String checkinId, Integer responseJsonDepth, Boolean showUserLike, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'checkinId' is set
		if (checkinId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'checkin_id' when calling checkinsShow");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/checkins/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "checkin_id", checkinId));
		queryParams.addAll(client.parameterToPairs("", "response_json_depth", responseJsonDepth));
		queryParams.addAll(client.parameterToPairs("", "show_user_like", showUserLike));
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
	 * 
	 * Updates a checkin for the currenty logged in user.

Application admins can update another user's checkin on their behalf by including the
`su_id` field in the request.

	 * 
	 * @param checkinId ID of the {@link Checkins} to update.

	 * @param placeId ID of the {@link Places} to check in to.

You can associate a checkin with either a {@link Places} or {@link Events} object but not both.

	 * @param eventId ID of the {@link Events} to check in to.

You can associate a checkin with either a {@link Places} or {@link Events} object but not both.

	 * @param message Message to attach to the checkin.
	 * @param photo New {@link Photos} object to attach as the primary photo for the checkin.

When you use the `photo` parameter to attach a new photo, you can use the
[custom resize and sync options](#!/guide/photosizes).

	 * @param photoId ID of an existing {@link Photos} object to attach as the primary photo for the checkin.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param tags Comma-separated list of tags for this checkin.

	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
	 * @param aclName Name of an {@link ACLs} to associate with this checkin object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this checkin object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId ID of {@link Users} to update the checkin on behalf of.

The current login user must be an application admin to create a checkin on
behalf of another user.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject checkinsUpdate(String checkinId, String placeId, String eventId, String message, File photo, String photoId, Boolean prettyJson, String tags, String customFields, String aclName, String aclId, String suId) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'checkinId' is set
		if (checkinId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'checkin_id' when calling checkinsUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/checkins/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (checkinId != null) formParams.put("checkin_id", checkinId);
		if (placeId != null) formParams.put("place_id", placeId);
		if (eventId != null) formParams.put("event_id", eventId);
		if (message != null) formParams.put("message", message);
		if (photo != null) formParams.put("photo", photo);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		if (tags != null) formParams.put("tags", tags);
		if (customFields != null) formParams.put("custom_fields", customFields);
		if (aclName != null) formParams.put("acl_name", aclName);
		if (aclId != null) formParams.put("acl_id", aclId);
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
			"application/x-www-form-urlencoded", "multipart/form-data"
		};
		final String contentType = client.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] { "api_key" };

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

}

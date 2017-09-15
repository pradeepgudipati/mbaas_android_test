
 

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

public class LikesAPI {

	private SdkClient client;

	public LikesAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Create Like
	 * Adds a "like" to an object. Currently, likes can only be associated with one of
the following object types, and a user can only like an object once:

*   {@link Posts}
*   {@link Photos}
*   {@link Users}
*   {@link Events}
*   {@link Checkins}
*   {@link Places}
*   {@link CustomObjects}
*   {@link Statuses}
*   {@link Reviews}

Once an object has one or more likes attached to it, it will return a
total like count with the object:

    "likes_count": 2

You should specify one, and only one, ArrowDB object ID parameter to identify the target object.

	 * 
	 * @param postId Post object to like.
	 * @param photoId Photo object to like.
	 * @param userId User object to like.
	 * @param eventId Event object to like.
	 * @param placeId Place object to like.
	 * @param checkinId Checkin object to like.
	 * @param statusId Status object to like.
	 * @param reviewId Review object to like.
	 * @param customObjectId Custom object to like.
	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject likesCreate(String postId, String photoId, String userId, String eventId, String placeId, String checkinId, String statusId, String reviewId, String customObjectId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/likes/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (postId != null) formParams.put("post_id", postId);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (userId != null) formParams.put("user_id", userId);
		if (eventId != null) formParams.put("event_id", eventId);
		if (placeId != null) formParams.put("place_id", placeId);
		if (checkinId != null) formParams.put("checkin_id", checkinId);
		if (statusId != null) formParams.put("status_id", statusId);
		if (reviewId != null) formParams.put("review_id", reviewId);
		if (customObjectId != null) formParams.put("custom_object_id", customObjectId);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		
		//If a file is being uploaded in this API then the contentType should have "multipart/form-data" only or as the first entry in the accepts array.
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
	 * Delete a Like
	 * Delete the like from the target object. Only the original submitter can delete
the like.

Specify one and only one of the ID parameters to identify  the target object.

	 * 
	 * @param postId Post object to delete "like" from.
	 * @param photoId Photo object to delete "like" from.
	 * @param userId User object to delete "like" from.
	 * @param eventId Event object to delete "like" from.
	 * @param placeId Place object to delete "like" from.
	 * @param checkinId Checkin object to delete "like" from.
	 * @param statusId Status object to delete "like" from.
	 * @param reviewId Review object to delete "like" from.
	 * @param customObjectId Custom object to delete "like" from.
	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject likesDelete(String postId, String photoId, String userId, String eventId, String placeId, String checkinId, String statusId, String reviewId, String customObjectId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/likes/delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (postId != null) formParams.put("post_id", postId);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (userId != null) formParams.put("user_id", userId);
		if (eventId != null) formParams.put("event_id", eventId);
		if (placeId != null) formParams.put("place_id", placeId);
		if (checkinId != null) formParams.put("checkin_id", checkinId);
		if (statusId != null) formParams.put("status_id", statusId);
		if (reviewId != null) formParams.put("review_id", reviewId);
		if (customObjectId != null) formParams.put("custom_object_id", customObjectId);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		
		//If a file is being uploaded in this API then the contentType should have "multipart/form-data" only or as the first entry in the accepts array.
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
	 * Custom Query of Likes
	 * Performs custom query of likes with sorting and paginating.

You can either query the likes of an object using the object's ID,
such as the `post_id`, `photo_id`, etc. parameter, or the likes generated by a user,
by specifying the `user_id` parameter.

A non-administrator user can only retrieve results on the likes they generated.

Application administrators can retrieve results on the likes of all users and
query likes generated by other users by specifying the `user_id` parameter.

In addition to custom fields, the following pre-defined fields can be used to
query and sort likes:

*   `su_id` : `String`. User ID of the User that generated the likes.
    Only an application admininstrator can query likes of other users.
*   `likeable_type` : `String`. Object type of the like object, which is the name of the object,
    such as `Post`, `Photo`, etc.
*   `likeable_id` : `String`. Object ID of the like object.
*   `created_at` : `Date`. Timestamp when the like was created.
*   `updated_at` : `Date`. Timestamp when the like was last updated.

In ArrowDB 1.1.5 and later, you can paginate query results using `skip` and `limit` parameters, or by including
a `where` clause to limit the results to objects whose IDs fall within a specified range.
For details, see [Query Pagination](#!/guide/search_query-section-query-pagination).

For details about using the query parameters,
see the [Search and Query guide](#!/guide/search_query).

	 * 
	 * @param postId Limit query to likes on the identified Post object.
	 * @param photoId Limit query to likes on the identified Photo object.
	 * @param eventId Limit query to likes on the identified Event object.
	 * @param placeId Limit query to likes on the identified Place object.
	 * @param checkinId Limit query to likes on the identified Checkin object.
	 * @param reviewId Limit query to likes on the identified Review object.
	 * @param customObjectId Limit query to likes on the identified Custom object.
	 * @param userObjectId Limit query to likes on the identified User object.
	 * @param suId Limit query to likes generated by the identified User.
Only an application administrator can query likes generated by other users.

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

	 * @param unsel Selects the object fields NOT to display. Do not use this parameter with `sel`.

	 * @param responseJsonDepth Nested object depth level counts in the response JSON.

In order to reduce server API calls from an application, the response JSON may
include not just the objects that are being queried/searched, but also
some important data related to the returned objects, such as owners and
referenced objects.

Default is 1, valid range is 1 to 8.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject likesQuery(String postId, String photoId, String eventId, String placeId, String checkinId, String reviewId, String customObjectId, String userObjectId, String suId, Integer page, Integer perPage, Integer limit, Integer skip, String where, String order, String sel, String unsel, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/likes/query.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "post_id", postId));
		queryParams.addAll(client.parameterToPairs("", "photo_id", photoId));
		queryParams.addAll(client.parameterToPairs("", "event_id", eventId));
		queryParams.addAll(client.parameterToPairs("", "place_id", placeId));
		queryParams.addAll(client.parameterToPairs("", "checkin_id", checkinId));
		queryParams.addAll(client.parameterToPairs("", "review_id", reviewId));
		queryParams.addAll(client.parameterToPairs("", "custom_object_id", customObjectId));
		queryParams.addAll(client.parameterToPairs("", "user_object_id", userObjectId));
		queryParams.addAll(client.parameterToPairs("", "su_id", suId));
		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "limit", limit));
		queryParams.addAll(client.parameterToPairs("", "skip", skip));
		queryParams.addAll(client.parameterToPairs("", "where", where));
		queryParams.addAll(client.parameterToPairs("", "order", order));
		queryParams.addAll(client.parameterToPairs("", "sel", sel));
		queryParams.addAll(client.parameterToPairs("", "unsel", unsel));
		queryParams.addAll(client.parameterToPairs("", "response_json_depth", responseJsonDepth));
		queryParams.addAll(client.parameterToPairs("", "pretty_json", prettyJson));


		
		//If a file is being uploaded in this API then the contentType should have "multipart/form-data" only or as the first entry in the accepts array.
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

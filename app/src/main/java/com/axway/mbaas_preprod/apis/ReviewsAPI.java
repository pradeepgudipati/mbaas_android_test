 

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

public class ReviewsAPI {

	private SdkClient client;

	public ReviewsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Deletes multiple Reviews objects.
	 * Deletes Reviews objects that match the query constraints provided in the `where` parameter.
If no `where` parameter is provided, all Reviews objects are deleted. 
Note that an HTTP 200 code (success)
is returned if the call completed successfully but the query matched no objects.

For performance reasons, the number of objects that can be deleted in a single batch delete 
operation is limited to 100,000.

The matched objects are deleted asynchronously in a separate process.              

The reviewed object ({@link #post Post}, {@link #photo Photo}, {@link #user User}, {@link #event Event}, 
{@link #checkin Checkin}, {@link #place Place}, {@link #custom_object CustomObject}, 
{@link #status Status}, or {@link #review Review}) of each matched object is not deleted.

You must be an application admin to run this command.

	 * 
	 * @param where Encoded JSON object that specifies constraint values for Reviews objects to delete.
If not specified, all Reviews objects are deleted.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject reviewsBatchDelete(String where) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/reviews/batch_delete.json".replaceAll("\\{format\\}","json");
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
	 * Retrieves the total number of Review objects.
	 * Retrieves the total number of Review objects.
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject reviewsCount() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/reviews/count.json".replaceAll("\\{format\\}","json");
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
	 * Create Review&#x2F;Comment&#x2F;Rating&#x2F;Like
	 * Adds a review with an optional integer rating. You can also use this API to add
comments or likes.

Once an object has one or more reviews (comments) attached to it, it will
return a total review count, rating_count, average rating and a breakdown of
each rating value:

    "reviews_count": 2,
    "ratings_count": 2,
    "ratings_average": 150.0,
    "ratings_summary": {
      "100": 1,
      "200": 1
    },

To create a review, you must specify a target object using one of the `<object>_id` parameters, 
such as `photo_id` or `post_id`. Only one `<object>_id` parameter may be specified in a request.
To specify a {@link Users User} to review, use the the `user_object_id` parameter.

An application admin can create a review on behalf of another user by 
specifying that user's ID in the `user_id` method parameter. 

A review must include either `content` or `rating`. It can also include both.

	 * 
	 * @param postId ID of the {@link Posts} object to review.

	 * @param photoId ID of the {@link Photos} object to review.

	 * @param userObjectId ID of the {@link Users} object to review.

	 * @param eventId ID of the {@link Events} object to review.

	 * @param placeId ID of the {@link Places} object to review.

	 * @param checkinId ID of the {@link Checkins} object to review.

	 * @param reviewId ID of the {@link Reviews} object to review.

	 * @param customObjectId ID of the {@link CustomObjects} object to review.

	 * @param statusId ID of the {@link Statuses} object to review.

	 * @param content Review or comment text.

	 * @param rating Rating to be associated with review. You can use "1" to represent one {@link Likes Like}.
	 * @param allowDuplicate By default, the same user can only submit one review/comment per object.
Set this flag to `true` to allow the user to add multiple  reviews or comments to
the same object.

	 * @param tags Comma separated list of tags for this review.

	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
	 * @param aclName Name of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId ID of the {@link Users} object to create the review on behalf of.

The currently logged-in user must be an application admin to create a review on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject reviewsCreate(String postId, String photoId, String userObjectId, String eventId, String placeId, String checkinId, String reviewId, String customObjectId, String statusId, String content, String rating, Boolean allowDuplicate, String tags, String customFields, String aclName, String aclId, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/reviews/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (postId != null) formParams.put("post_id", postId);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (userObjectId != null) formParams.put("user_object_id", userObjectId);
		if (eventId != null) formParams.put("event_id", eventId);
		if (placeId != null) formParams.put("place_id", placeId);
		if (checkinId != null) formParams.put("checkin_id", checkinId);
		if (reviewId != null) formParams.put("review_id", reviewId);
		if (customObjectId != null) formParams.put("custom_object_id", customObjectId);
		if (statusId != null) formParams.put("status_id", statusId);
		if (content != null) formParams.put("content", content);
		if (rating != null) formParams.put("rating", rating);
		if (allowDuplicate != null) formParams.put("allow_duplicate", allowDuplicate);
		if (tags != null) formParams.put("tags", tags);
		if (customFields != null) formParams.put("custom_fields", customFields);
		if (aclName != null) formParams.put("acl_name", aclName);
		if (aclId != null) formParams.put("acl_id", aclId);
		if (suId != null) formParams.put("su_id", suId);
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
	 * 
	 * Delete the review (comment) with the given `id`. Only the original submitter
can delete the review. If the review has a rating attached to
it, deleting the review will update the average rating and rating summary.

To delete a review, you must specify **both** the ID of the review and the ID of
the reviewed object ({@link #post Post}, {@link #photo Photo}, {@link #user User}, {@link #event Event}, 
{@link #checkin Checkin}, {@link #place Place}, {@link #custom_object CustomObject}, 
{@link #status Status}, or {@link #review Review}. The reviewed object is not deleted, however.

An application admin can delete any Review object.

	 * 
	 * @param reviewId Review object to delete.
	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject reviewsDelete(String reviewId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'reviewId' is set
		if (reviewId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'review_id' when calling reviewsDelete");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/reviews/delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (reviewId != null) formParams.put("review_id", reviewId);
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

	/**
	 * Custom Query Reviews&#x2F;Comments&#x2F;Ratings&#x2F;Likes
	 * Perform custom query of reviews with sorting and paginating. Currently you can
not query or sort data stored inside array or hash in custom fields.

The query must be limited to reviews of a given object (by specifying one of `post_id`,
`photo_id`, etc.) or limited to reviews generated by a given user (by specifying
`owner_id`.

In addition to custom fields, the following pre-defined fields can be used to
query and sort reviews:

*   `user_id` : `String`. Review owner's user ID.
*   `rating` : `Integer`. Rating assigned to this review.
*   `tags_array` : `String`. Tags associated with the review.
*   `created_at` : `Date`. Timestamp when the review was created.
*   `updated_at` : `Date`. Timestamp when the review was last updated.

In ArrowDB 1.1.5 and later, you can paginate query results using `skip` and `limit` parameters, or by including
a `where` clause to limit the results to objects whose IDs fall within a specified range.
For details, see [Query Pagination](#!/guide/search_query-section-query-pagination).        

For details about using the query parameters,
see the [Search and Query guide](#!/guide/search_query).

	 * 
	 * @param postId Limit query to reviews on the identified Post object.
	 * @param photoId Limit query to reviews on the identified Photo object.
	 * @param userId Limit query to reviews on the identified User object.
	 * @param eventId Limit query to reviews on the identified Event object.
	 * @param placeId Limit query to reviews on the identified Place object.
	 * @param checkinId Limit query to reviews on the identified Checkin object.
	 * @param reviewId Limit query to reviews on the identified Review object.
	 * @param customObjectId Limit query to reviews on the identified Custom object.
	 * @param statusId Limit query to reviews on the identified Status object.
	 * @param ownerId Limit query results to reviews submitted by the identified  user.
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

	 * @param showUserLike If set to **true**, each Review object in the response includes `"current_user_liked: true"`
 if the current user has liked the object. If the user has not liked the object, the 
`current_user_liked` field is not included in the response.

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
	public JSONObject reviewsQuery(String postId, String photoId, String userId, String eventId, String placeId, String checkinId, String reviewId, String customObjectId, String statusId, String ownerId, Integer page, Integer perPage, Integer limit, Integer skip, String where, String order, String sel, Boolean showUserLike, String unsel, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/reviews/query.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "post_id", postId));
		queryParams.addAll(client.parameterToPairs("", "photo_id", photoId));
		queryParams.addAll(client.parameterToPairs("", "user_id", userId));
		queryParams.addAll(client.parameterToPairs("", "event_id", eventId));
		queryParams.addAll(client.parameterToPairs("", "place_id", placeId));
		queryParams.addAll(client.parameterToPairs("", "checkin_id", checkinId));
		queryParams.addAll(client.parameterToPairs("", "review_id", reviewId));
		queryParams.addAll(client.parameterToPairs("", "custom_object_id", customObjectId));
		queryParams.addAll(client.parameterToPairs("", "status_id", statusId));
		queryParams.addAll(client.parameterToPairs("", "owner_id", ownerId));
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
	 * Show a review
	 * Shows the review with the given `id`.

	 * 
	 * @param reviewId Review object to show.
	 * @param showUserLike If set to **true** the Review object in the response will include `"current_user_liked: true"`
if the current user has liked the object. If the user has not liked the object, the 
`current_user_liked` field is not included in the response.

	 * @param responseJsonDepth Nested object depth level counts in response JSON.

In order to reduce server API calls from an application, the response JSON may
include not just the objects that are being queried/searched, but also
some important data related to the returned objects such as object's owner or
referenced objects.

Default is 1, valid range is 1 to 8.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject reviewsShow(String reviewId, Boolean showUserLike, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'reviewId' is set
		if (reviewId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'review_id' when calling reviewsShow");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/reviews/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "review_id", reviewId));
		queryParams.addAll(client.parameterToPairs("", "show_user_like", showUserLike));
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
	 * Update a Review&#x2F;Comment&#x2F;Rating&#x2F;Like
	 * Updates the review with the given `id`.

Ordinary users can update reviews they own or have update access to.

An application admin can update a Review on behalf of another user by 
specifying that user's ID in the `user_id` method parameter.

	 * 
	 * @param postId ID of the {@link Posts} object to review.

	 * @param photoId ID of the {@link Photos} object to review.

	 * @param userObjectId ID of the {@link Users} object to review.

	 * @param eventId ID of the {@link Events} object to review.

	 * @param placeId ID of the {@link Places} object to review.

	 * @param checkinId ID of the {@link Checkins} object to review.

	 * @param reviewObjectId ID of the {@link Reviews} object to review.

	 * @param customObjectId ID of the {@link CustomObjects} object to review.

	 * @param statusId ID of the {@link Statuses} object to review.

	 * @param reviewId ID of the Review object to update.
	 * @param content Review or comment text.
	 * @param rating Rating to be associated with review. You can use "1" to represent one {@link Likes Like}.
	 * @param suId ID of the {@link Users} object to update the review on behalf of. The currently 
logged-in user must be an application admin to create a review on
behalf of another user.

	 * @param allowDuplicate By default, the same user can only submit one review/comment per object.
Set this flag to `true` to allow the user to add multiple  reviews or comments to
the same object.

	 * @param tags Comma separated list of tags for this review.

	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
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
	public JSONObject reviewsUpdate(String postId, String photoId, String userObjectId, String eventId, String placeId, String checkinId, String reviewObjectId, String customObjectId, String statusId, String reviewId, String content, String rating, String suId, Boolean allowDuplicate, String tags, String customFields, String aclName, String aclId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'reviewId' is set
		if (reviewId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'review_id' when calling reviewsUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/reviews/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (postId != null) formParams.put("post_id", postId);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (userObjectId != null) formParams.put("user_object_id", userObjectId);
		if (eventId != null) formParams.put("event_id", eventId);
		if (placeId != null) formParams.put("place_id", placeId);
		if (checkinId != null) formParams.put("checkin_id", checkinId);
		if (reviewObjectId != null) formParams.put("review_object_id", reviewObjectId);
		if (customObjectId != null) formParams.put("custom_object_id", customObjectId);
		if (statusId != null) formParams.put("status_id", statusId);
		if (reviewId != null) formParams.put("review_id", reviewId);
		if (content != null) formParams.put("content", content);
		if (rating != null) formParams.put("rating", rating);
		if (suId != null) formParams.put("su_id", suId);
		if (allowDuplicate != null) formParams.put("allow_duplicate", allowDuplicate);
		if (tags != null) formParams.put("tags", tags);
		if (customFields != null) formParams.put("custom_fields", customFields);
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

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

}

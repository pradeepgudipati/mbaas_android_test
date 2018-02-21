 

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

public class PlacesAPI {

	private SdkClient client;

	public PlacesAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Deletes multiple Places objects.
	 * Deletes Places objects that match the query constraints provided in the `where` parameter.
If no `where` parameter is provided, all Places objects are deleted. 
Note that an HTTP 200 code (success)
is returned if the call completed successfully but the query matched no objects.

For performance reasons, the number of objects that can be deleted in a single batch delete 
operation is limited to 100,000.

The matched objects are deleted asynchronously in a separate process.     

Any {@link #photo primary photos} associated with the matched objects are not deleted.

You must be an application admin to run this command.

	 * 
	 * @param where Encoded JSON object that specifies constraint values for Places objects to delete.
If not specified, all Places objects are deleted.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject placesBatchDelete(String where) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/places/batch_delete.json".replaceAll("\\{format\\}","json");
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
	 * Retrieves the total number of Place objects.
	 * Retrieves the total number of Place objects.
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject placesCount() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/places/count.json".replaceAll("\\{format\\}","json");
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
	 * Create a Place
	 * Creates a new place object.

To create a place, you must specify at least one of the following:
address, city, state, postal_code, country, or geographical coordinates (longitude and latitude).

	 * 
	 * @param name Place name.
	 * @param address Address.
	 * @param city City.
	 * @param state State.
	 * @param postalCode Postal or ZIP code.
	 * @param country Country.
	 * @param latitude Latitude.
	 * @param longitude Longitude.
	 * @param website Website URL.
	 * @param twitter Twitter ID.
	 * @param phoneNumber Phone number.
	 * @param photo New photo to attach as the primary photo for this place.

When you use the `photo` parameter to attach a new photo, you can use the
[custom resize and sync options](#!/guide/photosizes).

	 * @param photoId ID of an existing photo to attach as the primary photo for this place.

	 * @param tags Comma separated list of tags for this place.

	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
	 * @param aclName Name of an {@link ACLs} to associate with this place object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this place object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId User ID to create this place on behalf of.

The current login user must be an application admin to create a place on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject placesCreate(String name, String address, String city, String state, String postalCode, String country, Double latitude, Double longitude, String website, String twitter, String phoneNumber, File photo, String photoId, String tags, String customFields, String aclName, String aclId, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'name' is set
		if (name == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'name' when calling placesCreate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/places/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (name != null) formParams.put("name", name);
		if (address != null) formParams.put("address", address);
		if (city != null) formParams.put("city", city);
		if (state != null) formParams.put("state", state);
		if (postalCode != null) formParams.put("postal_code", postalCode);
		if (country != null) formParams.put("country", country);
		if (latitude != null) formParams.put("latitude", latitude);
		if (longitude != null) formParams.put("longitude", longitude);
		if (website != null) formParams.put("website", website);
		if (twitter != null) formParams.put("twitter", twitter);
		if (phoneNumber != null) formParams.put("phone_number", phoneNumber);
		if (photo != null) formParams.put("photo", photo);
		if (photoId != null) formParams.put("photo_id", photoId);
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
			"application/x-www-form-urlencoded", "multipart/form-data"
		};
		final String contentType = client.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] { "api_key" };

		Result result = client.invokeAPI(localVarPath, "post", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Delete a Place
	 * Deletes a place.

Only the user who created the place can delete it.

The {@link #photo primary photo} associated with the object is not deleted.

An application admin can delete any Place object.

	 * 
	 * @param placeId ID of the place to delete.
	 * @param suId User ID to delete the Place object on behalf of. The user must be the creator of the object.

The current login user must be an application admin to delete a Place object on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject placesDelete(String placeId, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'placeId' is set
		if (placeId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'place_id' when calling placesDelete");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/places/delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (placeId != null) formParams.put("place_id", placeId);
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

		Result result = client.invokeAPI(localVarPath, "delete", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Custom Query Places
	 * Performs custom query of places with sorting and paginating. Currently you can
not query or sort data stored inside array or hash in custom fields.

The following fields can be used for querying and sorting places:

*   `address` : String.  Place address.
*   `city` : String.  Place city.
*   `state` : String. Place state.
*   `country` : String.  Country.
*   `user_id` : String. ID of the user who created this place.
*   `google_cid` : Google Customer ID (CID) associated with this place.
*   `tags_array` : String. Search tags.
*   `lnglat` : `[longitude, latitude]`. The Place's default coordinates. You can also store
     custom coordinates in a custom field and query for those coordinates separately 
     (see [Geographic Coordinates in Custom Fields](#!/guide/customfields-section-geographic-coordinates-in-custom-fields)).
*   `ratings_average:  Number`.  Place's average rating (see {@link Reviews}).
*   `ratings_count: Number`. Place's total number of ratings (see {@link Reviews}).
*   `reviews_count: Number`. Place's total number of reviews (see {@link Reviews}).
*   `created_at: Date`. Timestamp when the photo was created.
*   `updated_at: Date`. Timestamp when the photo was updated.

In ArrowDB 1.1.5 and later, you can paginate query results using `skip` and `limit` parameters, or by including
a `where` clause to limit the results to objects whose IDs fall within a specified range.
For details, see [Query Pagination](#!/guide/search_query-section-query-pagination).

For details about using the query parameters,
see the [Search and Query guide](#!/guide/search_query).

	 * 
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

	 * @param showUserLike If set to **true**, each Place object in the response includes `"current_user_liked: true"`
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
	public JSONObject placesQuery(Integer page, Integer perPage, Integer limit, Integer skip, String where, String order, String sel, Boolean showUserLike, String unsel, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/places/query.json".replaceAll("\\{format\\}","json");
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
	 * Search Places
	 * Returns the list of places that have been added to the app, sorted by search
relevancy.

Optionally, `latitude` and `longitude` can be given to return the list of
places starting from a particular location. To bound the results within a
certain radius (in km) from the starting coordinates, add the `distance`
parameter. `q` can be given to search by place name.

If you have provided a starting latitude and longitude for place search, each
result will return a distance to the starting point in km.

	 * 
	 * @param page Request page number, default is 1.
	 * @param perPage Number of results per page, default is 10.
	 * @param responseJsonDepth Nested object depth level counts in the response JSON.

In order to reduce server API calls from an application, the response JSON may
include not just the objects that are being queried/searched, but also
some important data related to the returned objects, such as owners and
referenced objects.

Default is 1, valid range is 1 to 8.

	 * @param latitude Latitude to center search on.
	 * @param longitude Longitude to center search on.
	 * @param distance Distance in km to search from the identified center point.
	 * @param q Space-separated list of keywords used to perform full text search on place name and tags.
	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject placesSearch(Integer page, Integer perPage, Integer responseJsonDepth, Double latitude, Double longitude, Double distance, String q, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/places/search.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "response_json_depth", responseJsonDepth));
		queryParams.addAll(client.parameterToPairs("", "latitude", latitude));
		queryParams.addAll(client.parameterToPairs("", "longitude", longitude));
		queryParams.addAll(client.parameterToPairs("", "distance", distance));
		queryParams.addAll(client.parameterToPairs("", "q", q));
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
	 * Show a Place
	 * Returns information for the identified place.
	 * 
	 * @param placeId ID of the place to show.
	 * @param responseJsonDepth Nested object depth level counts in the response JSON.

In order to reduce server API calls from an application, the response JSON may
include not just the identified object, but also
some important data related to the returned objects, such as owners and
referenced objects.

Default is 1, valid range is 1 to 8.

	 * @param showUserLike If set to **true** the Place object in the response will include `"current_user_liked: true"`
if the current user has liked the object. If the user has not liked the object, the 
`current_user_liked` field is not included in the response.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject placesShow(String placeId, Integer responseJsonDepth, Boolean showUserLike, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'placeId' is set
		if (placeId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'place_id' when calling placesShow");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/places/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "place_id", placeId));
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
	 * Update a Place
	 * Any of the parameters used to {@link Places#create Create a Place} can
be used to update it as well. Only the user that created the place can update
it.

An application admin can update any place object.

	 * 
	 * @param placeId ID of the place to delete.
	 * @param name Place name.
	 * @param address Address.
	 * @param city City.
	 * @param state State.
	 * @param postalCode Postal or ZIP code.
	 * @param country Country.
	 * @param latitude Latitude.
	 * @param longitude Longitude.
	 * @param website Website URL.
	 * @param twitter Twitter ID.
	 * @param phoneNumber Phone number.
	 * @param photo New photo to attach as the primary photo for this place.

When you use the `photo` parameter to attach a new photo, you can use the
[custom resize and sync options](#!/guide/photosizes).

	 * @param photoId ID of an existing photo to attach as the primary photo for this place.

	 * @param tags Comma separated list of tags for this place.

	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
	 * @param aclName Name of an {@link ACLs} to associate with this place object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this place object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param userId User ID to assign as the owner of the place object. The current user must have write
access to the object in order to update the owner.

	 * @param suId User ID to update the Place object on behalf of. The user must be the creator of the object.

The current login user must be an application admin to update a Place object on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject placesUpdate(String placeId, String name, String address, String city, String state, String postalCode, String country, Double latitude, Double longitude, String website, String twitter, String phoneNumber, File photo, String photoId, String tags, String customFields, String aclName, String aclId, String userId, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'placeId' is set
		if (placeId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'place_id' when calling placesUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/places/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (placeId != null) formParams.put("place_id", placeId);
		if (name != null) formParams.put("name", name);
		if (address != null) formParams.put("address", address);
		if (city != null) formParams.put("city", city);
		if (state != null) formParams.put("state", state);
		if (postalCode != null) formParams.put("postal_code", postalCode);
		if (country != null) formParams.put("country", country);
		if (latitude != null) formParams.put("latitude", latitude);
		if (longitude != null) formParams.put("longitude", longitude);
		if (website != null) formParams.put("website", website);
		if (twitter != null) formParams.put("twitter", twitter);
		if (phoneNumber != null) formParams.put("phone_number", phoneNumber);
		if (photo != null) formParams.put("photo", photo);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (tags != null) formParams.put("tags", tags);
		if (customFields != null) formParams.put("custom_fields", customFields);
		if (aclName != null) formParams.put("acl_name", aclName);
		if (aclId != null) formParams.put("acl_id", aclId);
		if (userId != null) formParams.put("user_id", userId);
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
			"application/x-www-form-urlencoded", "multipart/form-data"
		};
		final String contentType = client.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] { "api_key" };

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

}

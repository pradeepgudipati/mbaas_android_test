
 

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

public class EventsAPI {

	private SdkClient client;

	public EventsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Deletes multiple Events objects.
	 * Deletes Events objects that match the query constraints provided in the `where` parameter.
If no `where` parameter is provided, all Events objects are deleted. 
Note that an HTTP 200 code (success)
is returned if the call completed successfully but the query matched no objects.

For performance reasons, the number of objects that can be deleted in a single batch delete 
operation is limited to 100,000.

The matched objects are deleted asynchronously in a separate process.

Any {@link #place Place} associated with the matched objects are not deleted.

You must be an application admin to run this command.

	 * 
	 * @param where Encoded JSON object that specifies constraint values for Events objects to delete.
If not specified, all Events objects are deleted.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject eventsBatchDelete(String where) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/batch_delete.json".replaceAll("\\{format\\}","json");
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
	 * Retrieves the total number of Event objects.
	 * Retrieves the total number of Event objects.
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject eventsCount() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/count.json".replaceAll("\\{format\\}","json");
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
	 * 
	 * Create an event. Times given with time zones other than UTC (for example PST
during daylight savings is -0700) will be converted to UTC. An ical string
will be returned to represent the occurrences of the event.

For the event that is set as a recurring event, once created, there will be
several "event occurrences" created associating with the event object on
server side, one "event occurrence" represents a single occurrence of the
recurring event. An "event occurrence" contains start and end time of the
event's occurrence which are calculated according to the "recurring" settings
of the event object.

Instead of computing actual individual occurrences of a recurring event on the
client side, you can use event occurrences query api to get a list of
occurrences associated of a repeating event.

To get all occurrences for a recurring event object, you can call {@link Events#show_occurrences}
event occurrence](/docs/api/v1/events/show_occurrences) and pass in the
event's `id`.

	 * 
	 * @param name Event name.
	 * @param startTime Event start time.
	 * @param details Description of the event.
	 * @param duration Event duration, in seconds.
	 * @param recurring Recurrance schedule. Can take the following values: "daily", "weekly", "monthly", or "yearly".

Must be used together with `recurring_count` or `recurring_until` to limit the
number of occurances. The total number of occurrences of an event in either
case is limited to 1000.

	 * @param recurringCount Number of occurrences for the event.
	 * @param recurringUntil Date of last recurrance.
	 * @param placeId ID for the {@link Places} where the event takes place.
	 * @param photo New photo to attach as the primary photo for the event.

When you use the `photo` parameter to attach a new photo, you can use the
[custom resize and sync options](#!/guide/photosizes).

	 * @param photoId ID of an existing photo to attach as the primary photo for the event.

	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
	 * @param aclName Name of an {@link ACLs} to associate with this event.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this event.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param tags Comma-separated list of tags associated with this event.
	 * @param suId User ID to create the event on behalf of.

The current login user must be an application admin to create an event on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject eventsCreate(String name, java.util.Date startTime, String details, Integer duration, String recurring, Integer recurringCount, String recurringUntil, String placeId, File photo, String photoId, String customFields, String aclName, String aclId, String tags, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'name' is set
		if (name == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'name' when calling eventsCreate");
		}
		// verify the required parameter 'startTime' is set
		if (startTime == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'start_time' when calling eventsCreate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (name != null) formParams.put("name", name);
		if (startTime != null) formParams.put("start_time", startTime);
		if (details != null) formParams.put("details", details);
		if (duration != null) formParams.put("duration", duration);
		if (recurring != null) formParams.put("recurring", recurring);
		if (recurringCount != null) formParams.put("recurring_count", recurringCount);
		if (recurringUntil != null) formParams.put("recurring_until", recurringUntil);
		if (placeId != null) formParams.put("place_id", placeId);
		if (photo != null) formParams.put("photo", photo);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (customFields != null) formParams.put("custom_fields", customFields);
		if (aclName != null) formParams.put("acl_name", aclName);
		if (aclId != null) formParams.put("acl_id", aclId);
		if (tags != null) formParams.put("tags", tags);
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
	 * Delete an Event
	 * Delete the event with the given `id`. Only the original submitter can delete
the event.

The {@link #place Place} associated with the object is not deleted.

Application Admin can delete any Event object.

	 * 
	 * @param eventId ID of the event to delete.
	 * @param suId User to delete the Event object on behalf of. The user must be the creator of the object.

The current user must be an application admin to delete an Event object on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject eventsDelete(String eventId, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'eventId' is set
		if (eventId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'event_id' when calling eventsDelete");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (eventId != null) formParams.put("event_id", eventId);
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
	 * Custom Query Events
	 * Perform custom query of events with sorting and paginating. Currently you can
not query or sort data stored inside array or hash in custom fields.

In ArrowDB 1.1.5 and later, you can paginate query results using `skip` and `limit` parameters, or by including
a `where` clause to limit the results to objects whose IDs fall within a specified range.
For details, see [Query Pagination](#!/guide/search_query-section-query-pagination).

In addition to the custom fields, you can query the following event fields:

<table>
    <tr>
        <th>Name</th>
    <th>Type</th>
        <th>Summary</th>
    </tr>
  <tr>
    <td><code>name</code></td>
    <td>String</td>
    <td>Event's name</td>
  </tr>
  <tr>
    <td><code>user_id</code></td>
    <td>String</td>
    <td>Event owner's user ID</td>
  </tr>
  <tr>
    <td><code>place_id</code></td>
    <td>String</td>
    <td>If an event belongs to a place, the associated place_id</td>
  </tr>
  <tr>
    <td><code>tags_array</code></td>
    <td>Array</td>
    <td>Array of tags assigned to the Event.</td>
  </tr>
  <tr>
    <td><code>start_time</code></td>
    <td>Time</td>
    <td>Start time of an event</td>
  </tr>
  <tr>
    <td><code>num_occurences</code></td>
    <td>Integer</td>
    <td>Number of time the event repeats</td>
  </tr>
  <tr>
    <td><code>lnglat</code></td>
    <td>Geo location array - [longitude, latitude]</td>
    <td>If an event belongs to a place, you can use <code>lnglat</code> to query events by place location</td>
  </tr>
  <tr>
    <td><code>created_at</code></td>
    <td>Date</td>
    <td>Timestamp when the event was created</td>
  </tr>
  <tr>
    <td><code>updated_at</code></td>
    <td>Date</td>
    <td>Timestamp when the event was last updated</td>
  </tr>
</table>

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

	 * @param showUserLike If set to **true**, each Event in the response includes `"current_user_liked: true"`
 if the current user has liked the object. If the current user has not liked the object, the
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
	public JSONObject eventsQuery(Integer page, Integer perPage, Integer limit, Integer skip, String where, String order, String sel, Boolean showUserLike, String unsel, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/query.json".replaceAll("\\{format\\}","json");
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
	 * Custom Query Event Occurrences
	 * Perform custom query of event occurrences with sorting and paginating.

Currently, you can not query or sort data stored inside array or hash in custom
fields.

In addition to custom fields, you can query the following fields:

<table class="doc_content_table">
    <tr>
        <th>Name</th>
    <th>Type</th>
        <th>Summary</th>
    </tr>
  <tr>
    <td><code>name</code></td>
    <td>String</td>
    <td>Event's name</td>
  </tr>
  <tr>
    <td><code>user_id</code></td>
    <td>String</td>
    <td>Event owner's user id</td>
  </tr>
  <tr>
    <td><code>place_id</code></td>
    <td>String</td>
    <td>If an event belongs to a place, the associated place_id</td>
  </tr>
  <tr>
    <td><code>start_time</code></td>
    <td>Time</td>
    <td>Start time of an event occurrence</td>
  </tr>
  <tr>
    <td><code>end_time</code></td>
    <td>Time</td>
    <td>End time of an event occurrence</td>
  </tr>
  <tr>
    <td><code>lnglat</code></td>
    <td>Geo location array - [longitude, latitude]</td>
    <td>If an event belongs to a place, you can use <code>lnglat</code> to query events by place location</td>
  </tr>
</table>

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
1000, or an HTTP 400 (Bad Request) error will be returned.

	 * @param skip Number of records to skip. Must be used together with `limit`.
The specified value must not be less than 0 or an HTTP 400 error will be returned.

	 * @param where Constraint values for fields. `where` should be encoded JSON.

If `where` is not specified, `query` returns all objects.

	 * @param order Sort results by one or more fields.

	 * @param sel Selects the object fields to display. Do not use this parameter with `unsel`.

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
	public JSONObject eventsQueryOccurrences(Integer page, Integer perPage, Integer limit, Integer skip, String where, String order, String sel, String unsel, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/query/occurrences.json".replaceAll("\\{format\\}","json");
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
	 * Search for Events
	 * Full text search of events.

Optionally, `latitude` and `longitude` can be given to return the list of
events starting from a particular location (location is retrieved from place
if the event is associated with a place). To bound the results within a
certain radius (in km) from the starting coordinates, add the `distance`
parameter. `q` can be given to search by event name.

	 * 
	 * @param page Request page number, default is 1.
	 * @param perPage Number of results per page, default is 10.
	 * @param placeId Restrict search results to events located in the identified {@link Places}.
	 * @param userId Restrict search results to events owned by the identified {@link Users}.
	 * @param latitude Latitude of the search starting point.
	 * @param longitude Longitude of the search starting point.
	 * @param distance Maximum distance in km from the starting point identified by
`longitude`, latitude`.

	 * @param startTime Only return events that start on or after `start_time`.
	 * @param q Space-separated list of keywords, used to perform full text search on event
name and tags.

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
	public JSONObject eventsSearch(Integer page, Integer perPage, String placeId, String userId, Double latitude, Double longitude, Double distance, java.util.Date startTime, String q, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/search.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "place_id", placeId));
		queryParams.addAll(client.parameterToPairs("", "user_id", userId));
		queryParams.addAll(client.parameterToPairs("", "latitude", latitude));
		queryParams.addAll(client.parameterToPairs("", "longitude", longitude));
		queryParams.addAll(client.parameterToPairs("", "distance", distance));
		queryParams.addAll(client.parameterToPairs("", "start_time", startTime));
		queryParams.addAll(client.parameterToPairs("", "q", q));
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
	 * Seach for Event Occurrences
	 * Full text search of event occurrences.

Optionally, `latitude` and `longitude` can be given to return the list of
event occurrences starting from a particular location (location is retrieved
from place if the event is associated with a place). To bound the results
within a certain radius (in km) from the starting coordinates, add the
`distance` parameter. `q` can be given to search by event name.

	 * 
	 * @param page Request page number, default is 1.
	 * @param perPage Number of results per page, default is 10.
	 * @param placeId Restrict search results to events located in the identified {@link Places}.
	 * @param userId Restrict search results to events owned by the identified {@link Users}.
	 * @param latitude Latitude of the search starting point.
	 * @param longitude Longitude of the search starting point.
	 * @param distance Maximum distance in km from the starting point identified by
`longitude`, latitude`.

	 * @param startTime Only return events that start on or after `start_time`.
	 * @param endTime Only return events that end on or before `end_time`.
	 * @param q Space-separated list of keywords, used to perform full text search on event
name and tags.

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
	public JSONObject eventsSearchOccurrences(Integer page, Integer perPage, String placeId, String userId, Double latitude, Double longitude, Double distance, java.util.Date startTime, java.util.Date endTime, String q, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/search/occurrences.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "place_id", placeId));
		queryParams.addAll(client.parameterToPairs("", "user_id", userId));
		queryParams.addAll(client.parameterToPairs("", "latitude", latitude));
		queryParams.addAll(client.parameterToPairs("", "longitude", longitude));
		queryParams.addAll(client.parameterToPairs("", "distance", distance));
		queryParams.addAll(client.parameterToPairs("", "start_time", startTime));
		queryParams.addAll(client.parameterToPairs("", "end_time", endTime));
		queryParams.addAll(client.parameterToPairs("", "q", q));
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
	 * Show Event
	 * Show event(s) with the given IDs.

	 * 
	 * @param eventId ID of the event to delete.

Either `event_id` or `event_ids` must be specified.

	 * @param eventIds Comma-separated list of event IDs to show.
	 * @param responseJsonDepth Nested object depth level counts in response JSON.

In order to reduce server API calls from an application, the response JSON may
include not just the identified objects, but also some important data related
to the returning objects such as object's owner or referenced objects.

Default is 1, valid range is 1 to 8.

	 * @param showUserLike If set to **true** the Event object in the response will include `"current_user_liked: true"`
if the current user has liked the object. If the user has not liked the object, the 
`current_user_liked` field is not included in the response.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject eventsShow(String eventId, String eventIds, Integer responseJsonDepth, Boolean showUserLike, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "event_id", eventId));
		queryParams.addAll(client.parameterToPairs("", "event_ids", eventIds));
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
	 * Show Event Occurrences
	 * Show the event occurrences of an event with the given `event_id`.

	 * 
	 * @param eventId ID of the event to show occurrences of.
	 * @param page Request page number, default is 1.
	 * @param perPage Number of results per page, default is 10.
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
	public JSONObject eventsShowOccurrences(String eventId, Integer page, Integer perPage, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'eventId' is set
		if (eventId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'event_id' when calling eventsShowOccurrences");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/show/occurrences.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "event_id", eventId));
		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
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
	 * 
	 * Update the event with the given `id`. Only the original submitter can update
the event.

For the event that is set as a recurring event, once created, there will be
several "event occurrences" created associating with the event object on
server side, one "event occurrence" represents a single occurrence of the
recurring event. An "event occurrence" contains start and end time of the
event's occurrence which are calulated according to the "recurring" settings
of the event object.

Instead of computing actual individual occurrences of a recurring event on the
client side, you can use event occurrences query API to get a list of
occurrences associated with a repeating event.

To get all "event occurrence" of an recurring event object, you can use
{@link Events#show_occurrences} with the event's `id`.

All the event occurrences will be recomputed if there is any change to the
start_time, duration and/or recurring.

An application admin can update any Event object.

	 * 
	 * @param eventId ID of the event to update.
	 * @param name Updated event name.
	 * @param startTime Updated event start time.
	 * @param duration Updated event duration, in seconds.
	 * @param recurring New recurrance schedule. Can take the following values: "daily", "weekly", "monthly", or "yearly".

	 * @param recurringCount Updated number of occurrences for the event.
	 * @param recurringUntil Updated date of last recurrance.
	 * @param details Updated description of the event.
	 * @param placeId ID of the place where this event takes place.
	 * @param photo New photo to assign as the event's primary photo.

When you use `photo` parameter to attach a new photo, you can use it with
[custom resize and sync options](/docs/photosizes)

To remove primary photo, simply set "photo=" or "photo_id=". If the original
photo was created by using `photo` parameter, the photo will be deleted.

	 * @param photoId ID of an existing photo to use as the event's primary photo.

To remove primary photo, simply set "photo=" or "photo_id=". If the original
photo was created by using `photo` parameter, the photo will be deleted.

	 * @param tags Comma-separated list of tags associated with this event.
	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
	 * @param aclName Name of an {@link ACLs} to associate with this checkin object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this checkin object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId User to update the Event object on behalf of. The user must be the creator of the object.

The current user must be an application admin to update the Event object on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject eventsUpdate(String eventId, String name, java.util.Date startTime, Integer duration, String recurring, Integer recurringCount, String recurringUntil, String details, String placeId, File photo, String photoId, String tags, String customFields, String aclName, String aclId, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'eventId' is set
		if (eventId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'event_id' when calling eventsUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/events/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (eventId != null) formParams.put("event_id", eventId);
		if (name != null) formParams.put("name", name);
		if (startTime != null) formParams.put("start_time", startTime);
		if (duration != null) formParams.put("duration", duration);
		if (recurring != null) formParams.put("recurring", recurring);
		if (recurringCount != null) formParams.put("recurring_count", recurringCount);
		if (recurringUntil != null) formParams.put("recurring_until", recurringUntil);
		if (details != null) formParams.put("details", details);
		if (placeId != null) formParams.put("place_id", placeId);
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

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

}

 

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

public class PushNotificationsAPI {

	private SdkClient client;

	public PushNotificationsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Channels Query
	 * Returns a list of push notification channels the user is subscribed to.

For application admins, if the `user_id` parameter is not specified, returns all channels
with subscribed users.

	 * 
	 * @param userId User to retrieve subscribed channels for.

Only application admins can query subscribed channels of a user.

	 * @param page Request page number, default is 1.
	 * @param perPage Number of results per page, default is 10.
	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param count Set to `true` to return the total number of push channels in the `count` field of the
`meta` header.  Default is `false`.

Only valid for applications created with ArrowDB 1.1.8 or greater.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsChannelsQuery(String userId, Integer page, Integer perPage, Boolean prettyJson, Boolean count) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/channels/query.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "user_id", userId));
		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "pretty_json", prettyJson));
		queryParams.addAll(client.parameterToPairs("", "count", count));


		
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
	 * Channels Show
	 * Returns the number of devices subscribed to the specified channel.

	 * 
	 * @param name Name of the push channel.

The name of the push channel cannot start with a hash symbol ('#') or contain a comma (',').

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsChannelsShow(String name, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'name' is set
		if (name == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'name' when calling pushNotificationsChannelsShow");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/channels/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

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
	 * Retrieves the total number of devices subscribed to push notifications.
	 * Retrieves the total number of devices subscribed to push notifications.
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsCount() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/count.json".replaceAll("\\{format\\}","json");
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
	 * notify
	 * Sends push notifications to one or more users who are subscribed to a channel.

Specify either `friends`, `to_ids` or `where`.
One of these parameters must be used. These parameters cannot be used simultaneously.

Application admins can set the `to_ids` parameter to `everyone` to send to all devices
subscribed to the identified channel.

The REST version of `notify` includes the push notification ID in its response.
See the REST example below.

	 * 
	 * @param channel Name of the channel. For multiple channels, either comma-separate the list of channels
or use an array of strings.

The name of the push channel cannot start with a hash symbol ('#') or contain a comma (',').

	 * @param friends If this parameter is specified (regardless of the parameter's value),
push notifications are sent to any of the user's {@link Friends} who are
subscribed to the identified channel.

	 * @param toIds Comma-separated list of user IDs to send the notification to users who are subscribed
to the specified channel. Up to 1000 users can be specified.

You **cannot** use this parameter when using a location query with the `where` parameter.

Application admins can set this parameter to `everyone` to send to all devices
subscribed to the channel.

If you are using the web interface, you do not need to specify this parameter.

	 * @param payload Payload to send with the push notification.

For a string, it will be sent as an alert (notification message).

	 * @param options Additional push options.

* *expire_after_seconds* (Number): Expiration time in seconds of when to stop sending the push notification.
  For example, if the push notification expiration time is for a day and the user's device
  is off for over a day, the user does not receive the push notification since it has expired.

For example, to specify a one day expiration period, use `options={'expire_after_seconds':86400}`.

	 * @param where A JSON-encoded object that defines either the user or location query used to select the device
that will receive the notification. Up to 1000 users can be returned by the query.

If you are using a location query, you **cannot** use the `to_ids` parameter.

To specify a user query, set the `user` field to a custom query, for example, the
following query searches for all users with the first name of Joe:

    where={"user": {"first_name":"Joe">

To specify a location query, set the `loc` field to a
[MongoDB Geospatial Query](http://docs.mongodb.org/manual/reference/operator/query-geospatial/).
The following query searches for all users within 2 km of Oakland, CA, USA:

    where={"loc": { "$nearSphere" : { "$geometry" : { "type" : "Point" , "coordinates" : [-122.2708,37.8044] } , "$maxDistance" : 2000 >}

For details about using the `where` parameter, see the [Search and Query guide](#!/guide/search_query).

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsNotify(String channel, String friends, String toIds, String payload, String options, String where, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'channel' is set
		if (channel == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'channel' when calling pushNotificationsNotify");
		}
		// verify the required parameter 'payload' is set
		if (payload == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'payload' when calling pushNotificationsNotify");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/notify.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (channel != null) formParams.put("channel", channel);
		if (friends != null) formParams.put("friends", friends);
		if (toIds != null) formParams.put("to_ids", toIds);
		if (payload != null) formParams.put("payload", payload);
		if (options != null) formParams.put("options", options);
		if (where != null) formParams.put("where", where);
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
	 * notify_tokens
	 * Sends push notifications to one or more users who are subscribed to a channel.

Application admins can set the `to_tokens` parameter to `everyone` to send to all devices
subscribed to the identified channel.

If you use the `to_tokens` parameter, you **cannot** specify a location query using the `where`
parameter in the same API call.

	 * 
	 * @param channel Name of the channel. For multiple channels, either comma-separate the list of channels
or use an array of strings.

The name of the push channel cannot start with a hash symbol ('#') or contain a comma (',').

	 * @param toTokens Comma-separated list of device tokens. Sends push notification to the specified
tokens who are subscribed to the specified channel.

You **cannot** use this parameter with a location query using the `where` parameter.

Application admins can set this parameter to `everyone` to send to all devices
subscribed to the channel.

If you are using the web interface, you do not need to specify this parameter.

	 * @param payload Payload to send with the push notification.

For a string, it will be sent as an alert (message notification).

	 * @param options Additional push options.

* *expire_after_seconds* (Number): Expiration time in seconds of when to stop sending the push notification.
  For example, if the push notification expiration time is for a day and the user's device
  is off for over a day, the user does not receive the push notification since it has expired.

For example, to specify a one day expiration period, use `options={'expire_after_seconds':86400}`.

	 * @param where A JSON-encoded object that defines either the user or location query used to select the device
that will receive the notification. Up to 1000 users can be returned by the query.

If you are using the `to_tokens` parameter, you **cannot** specify a location query.

To specify a user query, set the `user` field to a custom query, for example, the
following query searches for all users with the first name of Joe:

    where={"user": {"first_name":"Joe">

To specify a location query, set the `loc` field to a
[MongoDB Geospatial Query](http://docs.mongodb.org/manual/reference/operator/query-geospatial/).
The following query searches for all users within 2 km of Oakland, CA, USA:

    where={"loc": { "$nearSphere" : { "$geometry" : { "type" : "Point" , "coordinates" : [-122.2708,37.8044] } , "$maxDistance" : 2000 >}

For details about using the `where` parameter,
see the [Search and Query guide](#!/guide/search_query).

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsNotifyTokens(String channel, String toTokens, String payload, String options, String where, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'channel' is set
		if (channel == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'channel' when calling pushNotificationsNotifyTokens");
		}
		// verify the required parameter 'toTokens' is set
		if (toTokens == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'to_tokens' when calling pushNotificationsNotifyTokens");
		}
		// verify the required parameter 'payload' is set
		if (payload == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'payload' when calling pushNotificationsNotifyTokens");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/notify_tokens.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (channel != null) formParams.put("channel", channel);
		if (toTokens != null) formParams.put("to_tokens", toTokens);
		if (payload != null) formParams.put("payload", payload);
		if (options != null) formParams.put("options", options);
		if (where != null) formParams.put("where", where);
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
	 * Query
	 * **Note:** this API is only available for applications created with ArrowDB 1.1.7 or greater.

Custom query of push notification subscriptions with pagination. For regular (non-admin) application
users, this method returns the currently logged-in user's list of push notification subscriptions.
For app admins, the method returns a list of subscriptions for all users, or just those
for the user specified by as the method's `su_id` parameter.

You can paginate query results using `skip` and `limit` parameters, or `page` and `per_page`, 
but not both in the same query.

	 * 
	 * @param suId User ID of the user who has subscribed devices for push notification.
You must be an application admin to query another user's subscriptions.

If this parameter is not included, a list of subscriptions for all users is returned.

	 * @param channel Name of the push notification channel.

The name of the push channel cannot start with a hash symbol ('#') or contain a comma (',').

	 * @param deviceToken Apple or Android Device Token.
	 * @param type Selects the push type.

Set to `android` for Android devices usingGoogle Cloud Messaging
or `ios` for iOS devices using Apple Push Notification Service.

	 * @param page Request page number, default is 1.
	 * @param perPage Number of results per page, default is 10.
	 * @param limit Instead of using `page` and `per_page` for pagination, you can use `limit` and
`skip` to do your own pagination. `limit` is the maximum number of records to `skip`. 
The specified value must be greater than 0 and no greater than 1000, or an HTTP 400 
(Bad Request) error will be returned.

	 * @param skip Number of records to skip. Must be used together with `limit`. The specified value must not
be less than 0 or an HTTP 400 error will be returned.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsQuery(String suId, String channel, String deviceToken, String type, Integer page, Integer perPage, Integer limit, Integer skip, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/query.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "su_id", suId));
		queryParams.addAll(client.parameterToPairs("", "channel", channel));
		queryParams.addAll(client.parameterToPairs("", "device_token", deviceToken));
		queryParams.addAll(client.parameterToPairs("", "type", type));
		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "limit", limit));
		queryParams.addAll(client.parameterToPairs("", "skip", skip));
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
	 * reset_badge
	 * Sets the internally stored value of the badge to zero of a specific device.

This method only updates the internally stored value of the badge.  To update the badge value
on the iOS icon or Android notification center, send a push notification with the `badge` field defined.

If you are an application admin, you may omit the device token.

	 * 
	 * @param deviceToken Device token. If you are an application admin, you may omit the device token.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsResetBadge(String deviceToken) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'deviceToken' is set
		if (deviceToken == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'device_token' when calling pushNotificationsResetBadge");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/reset_badge.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (deviceToken != null) formParams.put("device_token", deviceToken);
		
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
	 * set_badge
	 * Sets the value of the internally stored value of the badge.

This method only updates the internally stored value of the badge.  To update the badge value
on the iOS icon or Android notification center, send a push notification with the `badge` field defined.

	 * 
	 * @param deviceToken Device token. Required if you are not an application admin.

	 * @param badgeNumber Number to set as the badge on the application's icon.
Specify postive and negative values with the `+` and `-`
symbols to increment or decrement the current badge number, respectively.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsSetBadge(String deviceToken, String badgeNumber, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/set_badge.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (deviceToken != null) formParams.put("device_token", deviceToken);
		if (badgeNumber != null) formParams.put("badge_number", badgeNumber);
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

	/**
	 * subscribe
	 * Subscribes a mobile device to a push notifications channel. Developers can
create different channels for different types of push notifications. For
instance, a channel for friend request, a channel for chat, etc. Push
notifications currently only work on iOS and Andriod.

	 * 
	 * @param channel Push notification channel to subscribe to. For multiple channels, comma separate the
list of channel names.

The name of the push channel cannot start with a hash symbol ('#') or contain a comma (',').

	 * @param deviceToken Apple or Android Device Token.
	 * @param type Selects the push type.

Set to `android` for Android devices using Google Cloud Messaging
or `ios` for iOS devices using Apple Push Notification Service.

	 * @param suId User ID to subscribe on behalf of.

Only application admins can subscribe to push notifications on behalf of other
users.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsSubscribe(String channel, String deviceToken, String type, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'channel' is set
		if (channel == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'channel' when calling pushNotificationsSubscribe");
		}
		// verify the required parameter 'deviceToken' is set
		if (deviceToken == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'device_token' when calling pushNotificationsSubscribe");
		}
		// verify the required parameter 'type' is set
		if (type == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'type' when calling pushNotificationsSubscribe");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/subscribe.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (channel != null) formParams.put("channel", channel);
		if (deviceToken != null) formParams.put("device_token", deviceToken);
		if (type != null) formParams.put("type", type);
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
	 * Subscribes a mobile device to a push notifications channel.

Developers can create different channels for different types of push notifications, such as
a friend request, chat, etc.

	 * 
	 * @param deviceToken Android or iOS device token.

For Android, the length is dynamic and is less than 4096 characters.

For iOS, the length is 64 characters.

	 * @param channel Name of the channel. For multiple channels, comma separate the list of channel names.

The name of the push channel cannot start with a hash symbol ('#') or contain a comma (',').

	 * @param type Selects the push type.

Set to `android` for Android devices using Google Cloud Messaging
or `ios` for iOS devices using Apple Push Notification Service.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsSubscribeToken(String deviceToken, String channel, String type, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'deviceToken' is set
		if (deviceToken == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'device_token' when calling pushNotificationsSubscribeToken");
		}
		// verify the required parameter 'channel' is set
		if (channel == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'channel' when calling pushNotificationsSubscribeToken");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/subscribe_token.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (deviceToken != null) formParams.put("device_token", deviceToken);
		if (channel != null) formParams.put("channel", channel);
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

		Result result = client.invokeAPI(localVarPath, "post", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Update the Subscription
	 * Updates the device's push channel subscription.

	 * 
	 * @param deviceToken Apple or Android Device Token.
	 * @param suId User ID to update the subscription on behalf of. You must be logged in as an application administrator
to update another user's notification subscription.

	 * @param loc The device's current location specified as an array with longitude as the first element, and latitude
as the second element (`[longitude,latitude]`).

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsSubscriptionUpdate(String deviceToken, String suId, String loc, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'deviceToken' is set
		if (deviceToken == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'device_token' when calling pushNotificationsSubscriptionUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/subscription/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (deviceToken != null) formParams.put("device_token", deviceToken);
		if (suId != null) formParams.put("su_id", suId);
		if (loc != null) formParams.put("loc", loc);
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

	/**
	 * unsubscribe
	 * Unsubscribes one of the current user's devices from a push notification channel. If channel name is not
provided, unsubscribe the device from all channels.

When a user logs out from a device, you can cancel all subscriptions for the
device by passing the device's token to the {@link Users#logout} method.

	 * 
	 * @param channel Name of the push notification channel. For multiple channels, comma separate the list of
channel names.

The name of the push channel cannot start with a hash symbol ('#') or contains a comma (',').

	 * @param deviceToken Apple or Android Device Token.
	 * @param userId User ID to unsubscribe from push notifications.

Only application admins can unsubscribe another user from push notifications.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsUnsubscribe(String channel, String deviceToken, String userId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'deviceToken' is set
		if (deviceToken == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'device_token' when calling pushNotificationsUnsubscribe");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/unsubscribe.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (channel != null) formParams.put("channel", channel);
		if (deviceToken != null) formParams.put("device_token", deviceToken);
		if (userId != null) formParams.put("user_id", userId);
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
	 * unsubscribe_token
	 * Unsubscribes the specified device from a push notification channel.
If `channel` is not defined, unsubscribes the device from all channels.

	 * 
	 * @param channel Name of the push notification channel. For multiple channels, comma separate the list of
channel names.

The name of the push channel cannot start with a hash symbol ('#') or contain a comma (',').

	 * @param deviceToken Android or iOS device token.
	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject pushNotificationsUnsubscribeToken(String channel, String deviceToken, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'deviceToken' is set
		if (deviceToken == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'device_token' when calling pushNotificationsUnsubscribeToken");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/push_notification/unsubscribe_token.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (channel != null) formParams.put("channel", channel);
		if (deviceToken != null) formParams.put("device_token", deviceToken);
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

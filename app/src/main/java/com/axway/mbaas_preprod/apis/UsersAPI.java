
 

package com.axway.mbaas_preprod.apis;

import android.os.Looper;

import com.axway.mbaas_preprod.Pair;
import com.axway.mbaas_preprod.Result;
import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersAPI {

	private SdkClient client;

	public UsersAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Creates a user.
	 * Creates a new user.

When creating a user, you must specify either:

*   username
*   email address, first name, and last name

A user can have both an email address and username specified. However, if username
is omitted, email address, first name and last name are required.

For regular users (non-admin users), after successully executing this command, you will be logged
in as the new user and the session ID will be associated with the newly created user.  For
example, when user A creates user B, user A is now logged in as user B and user A's session
ID belongs to user B.

For admin users, you will still be logged in as the admin user.

	 * 
	 * @param email User's email address. Required if `username` is not specified.
	 * @param username User's login name. Required if `email` is not specified.

If `username` is not specified, `email`, `first_name`, and `last_name` must be included.

	 * @param password User's password.
	 * @param passwordConfirmation Copy of user's password for confirmation.
	 * @param firstName User's first name. Required when `username` is not provided.
	 * @param lastName User's last name. Required when `username` is not provided.
	 * @param photo New photo to attach as the primary photo for the user.

When you use the `photo` parameter to attach a new photo, you can use the
[custom resize and sync options](#!/guide/photosizes).

	 * @param photoId ID of an existing photo to attach as the primary photo for the user.

	 * @param tags Comma separated list of tags for this user.

	 * @param customFields User defined fields. See [Custom Data Fields](#!/guide/customfields).
	 * @param aclName Name of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId User ID to create this user on behalf of.

The current login user must be an application admin to create a user on
behalf of another user.

	 * @param role String representation of user role, for example, "teacher".
	 * @param template Send a congratulation email to notify that the user has been created successfully.

You need to create an email template and pass the template name.

	 * @param confirmationTemplate If "New User Email Verification" is enabled for the application, ArrowDB sends a confirmation email to
the user.

If you don't pass the `confirmation_template` parameter, then ArrowDB sends a default
confirmation email.

If you wish to use your custom email body, you can create a email template and pass
the template name. Your email template must contain the following URL to allow users
to be redirected to Appcelerator Cloud Services email verification page:

     https://cloud.appcelerator.com/users/confirmation?key=<key>&confirmation_token=<confirmation_token>

If you have your own website that talks to Appcelerator Cloud Services backend and
wish to use custom URL, you must provide a URL with the following format:

     https://<your_url.com>/<your_method>?key=<key>&confirmation_token=<confirmation_token>

When a user visits the above URL, you call the ArrowDB `users/confirm_user.json` method to 
confirm the user internally from your web server and you must append the query parameter `&ct=enterprise`
to the end of the URL:

     GET https://api.cloud.appcelerator.com/v1/users/confirm_user.json?confirmation_token=<confirmation_token>&key=<Your App Key>&ct=enterprise

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersCreate(String email, String username, String password, String passwordConfirmation, String firstName, String lastName, File photo, String photoId, String tags, String customFields, String aclName, String aclId, String suId, String role, String template, String confirmationTemplate, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'password' is set
		if (password == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'password' when calling usersCreate");
		}
		// verify the required parameter 'passwordConfirmation' is set
		if (passwordConfirmation == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'password_confirmation' when calling usersCreate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (email != null) formParams.put("email", email);
		if (username != null) formParams.put("username", username);
		if (password != null) formParams.put("password", password);
		if (passwordConfirmation != null) formParams.put("password_confirmation", passwordConfirmation);
		if (firstName != null) formParams.put("first_name", firstName);
		if (lastName != null) formParams.put("last_name", lastName);
		if (photo != null) formParams.put("photo", photo);
		if (photoId != null) formParams.put("photo_id", photoId);
		if (tags != null) formParams.put("tags", tags);
		if (customFields != null) formParams.put("custom_fields", customFields);
		if (aclName != null) formParams.put("acl_name", aclName);
		if (aclId != null) formParams.put("acl_id", aclId);
		if (suId != null) formParams.put("su_id", suId);
		if (role != null) formParams.put("role", role);
		if (template != null) formParams.put("template", template);
		if (confirmationTemplate != null) formParams.put("confirmation_template", confirmationTemplate);
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
	 * Logs user into the system
	 * 
	 * 
	 * @param login The user name for login
	 * @param password The password for login in clear text
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersLoginUser(String login, String password) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'login' is set
		if (login == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'login' when calling usersLoginUser");
		}
		// verify the required parameter 'password' is set
		if (password == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'password' when calling usersLoginUser");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/login.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (login != null) formParams.put("login", login);
		if (password != null) formParams.put("password", password);
		
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
	 * Log out a user.
	 * 
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersLogoutUser() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/logout.json".replaceAll("\\{format\\}","json");
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
	 * Show Current User Profile
	 * Shows both public and private user information about the user who is
currently logged in.

	 * 
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
	public JSONObject usersShowMe(Number responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/show/me.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

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
	 * Any of the same parameters as {@link Users#create create} can be
used to update the current user. If `password` is updated then
`password_confirmation` must be sent as well.

	 * 
	 * @param email Email address.
	 * @param username User name.
	 * @param password Password.
	 * @param passwordConfirmation Password.
	 * @param firstName First name.
	 * @param lastName First name.
	 * @param photo New photo to assign as the user's primary photo.

When you use `photo` parameter to attach a new photo, you can use it with
[custom resize and sync options](/docs/photosizes)

To remove primary photo, simply set "photo=" or "photo_id=". If the original
photo was created by using `photo` parameter, the photo will be deleted.

	 * @param photoId ID of an existing photo to use as the user's primary photo.

To remove primary photo, simply set "photo=" or "photo_id=". If the original
photo was created by using `photo` parameter, the photo will be deleted.

	 * @param tags Comma separated tags, overwrites the existing tags. For example, "hiking,swimming".

	 * @param customFields User-defined data. See [Custom Data Fields](/docs/customfields).

	 * @param aclName Name of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId User ID to update this user on behalf of.

The current login user must be an application admin to update a user on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersUpdate(String email, String username, String password, String passwordConfirmation, String firstName, String lastName, File photo, String photoId, String tags, String customFields, String aclName, String aclId, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (email != null) formParams.put("email", email);
		if (username != null) formParams.put("username", username);
		if (password != null) formParams.put("password", password);
		if (passwordConfirmation != null) formParams.put("password_confirmation", passwordConfirmation);
		if (firstName != null) formParams.put("first_name", firstName);
		if (lastName != null) formParams.put("last_name", lastName);
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

	/**
	 * Show User Profile
	 * Shows public user information. For private information about the currently
logged in user, see {@link Users#show_me Show Logged In User Info}.

	 * 
	 * @param userId User ID of the user to show. Either `user_id` or `user_ids` must be specified.

	 * @param userIds Comma-separated list of user IDs to show. Either `user_id` or `user_ids` must be specified.

	 * @param responseJsonDepth Nested object depth level counts in response json.

In order to reduce server API calls from an application, the response json may
include not just the objects that are being queried/searched, but also with
some important data related to the returning objects such as object's owner or
referencing objects.

Default is 1, valid range is 1 to 8.

	 * @param showUserLike If set to **true** the User object in the response will include `"current_user_liked: true"`
if the current user has liked the object. If the user has not liked the object, the 
`current_user_liked` field is not included in the response.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersShow(String userId, String userIds, Number responseJsonDepth, Boolean showUserLike, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "user_id", userId));
		queryParams.addAll(client.parameterToPairs("", "user_ids", userIds));
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
	 * Search Users
	 * Returns the list of users that have been added to the app, sorted by search
relevancy.

Optionally, `q` can be given to perform full text search on user's first name,
last name, email address, username and tags. For security reasons, when searching for {@link Users}
the {@link Users#email email} field is not returned in each Users object unless you have 
[admin access](#!/guide/admin_access).

For advanced custom search, see {@link Users#query query}.

	 * 
	 * @param page Request page number, default is 1.
	 * @param perPage Number of results per page, default is 10.
	 * @param q Space-separated list of keywords, used to perform full text search on first name, last name, email address,
username and tags fields.

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
	public JSONObject usersSearch(Number page, Number perPage, String q, Number responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/search.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
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
	 * Custom Query of Users
	 * Custom query of Users objects with sorting and paginating. You can query on sort
based on the data in any of the standard User fields. You can also query and
sort data based on the values of any custom fields, if the values are simple JSON
values.

For security reasons, when querying for {@link Users} the {@link Users#email email} field is not returned
in each Users object unless you have [admin access](#!/guide/admin_access).

Currently you **cannot** sort or query based on data stored inside array or hash
objects in custom fields.

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

	 * @param showUserLike If set to **true**, each User object in the response includes `"current_user_liked: true"`
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
	public JSONObject usersQuery(Integer page, Integer perPage, Integer limit, Integer skip, String where, String order, String sel, Boolean showUserLike, String unsel, Integer responseJsonDepth, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/query.json".replaceAll("\\{format\\}","json");
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
	 * Send a reset password email to User
	 * Sends an email to a user containing a link to recover a lost password. You can use the default
email template provided by ACS, or specify a [custom email template](http://docs.appcelerator.com/platform/latest/#!/guide/Managing_Email_Templates-section-37548619_ManagingEmailTemplates-Creatinganemailtemplate) 
that you have created. When using a custom email template, the email must contain a properly
formatted URL, as explained in the `template` method parameter documentation below.

You must also have configured your application's [email settings](http://docs.appcelerator.com/platform/latest/#!/guide/Configuring_Cloud_Services-section-39683049_ConfiguringCloudServices-Email) 
in Appcelerator Dashboard.

	 * 
	 * @param email Email address. Must match the user's registered email address.
	 * @param subject The default subject of the password reset email is "Password reset request for
<your app name>". If you wish to have your custom email subject, you can
provide it by setting the subject parameter

	 * @param template If you wish to use your custom email body. You can create a email template and
pass the template name. The email must contain a properly formatted link to the password
reset URL on appcelerator.com, or a link on your own site, as follows:
<p>

To link directly to the password page, your email template must contain a link to the following
URL:

    https://dashboard.appcelerator.com/#/users/confirmation/<key>/<confirmation_token>

If you prefer the user to reset their password on your own website, rather than on appcelerator.com,
you must provide a URL with the following format:

    https://[your_url.com]/[your_method]?reset_password_token=<reset_password_token>

This page must cache the value of the `reset_password_token` parameter and present a form 
that allows the user to enter a new password and password confirmation.
When the user submits the form, the app server must make the following call to Appcelerator Cloud Services API to reset the
password, passing the original password reset token, the user's new password, and the password confirmation:

    GET https://api.cloud.appcelerator.com/v1/users/reset_password.json?key=<Your App Key>&reset_password_token=<reset_password_token>&password=<password> &password_confirmation=<password_confirmation>

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersRequestResetPassword(String email, String subject, String template, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'email' is set
		if (email == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'email' when calling usersRequestResetPassword");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/request_reset_password.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "email", email));
		queryParams.addAll(client.parameterToPairs("", "subject", subject));
		queryParams.addAll(client.parameterToPairs("", "template", template));
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
	 * Re-send user email verification.
	 * If you enabled new user account email verification in your App settings, all
new users will receive an email containing instructions to activate their
account. You can use this API to re-send user verification email.

	 * 
	 * @param email Email must match user's registered email.
	 * @param confirmationSubject The default subject of the email verification is "Email Verification
Instructions for <your app name>". If you wish to have your custom email
subject, you can provide it by setting the `confirmation_subject` parameter.

	 * @param confirmationTemplate If you wish to use your custom email body. You can create a email template and
pass the template name. Your email template must contain the following URL to
allow users to be redirected to Appcelerator Cloud Services email verification page:

    https://cloud.appcelerator.com/users/confirmation?key=<key>&confirmation_token=<confirmation_token>

If you have your own website that talks to Appcelerator Cloud Services backend
and wish to use custom URL, you must provide a URL with the following format:

    https://[your_url.com]/[your_method]?key=<key>&confirmation_token=<confirmation_token>

When a user visits the above URL, you make a call to Appcelerator Cloud
Services to confirm the user internally from your web server:

    GET https://api.cloud.appcelerator.com/v1/users/confirm_user.json?confirmation_token=<confirmation_token>&key=<Your App Key>

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersResendConfirmation(String email, String confirmationSubject, String confirmationTemplate, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'email' is set
		if (email == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'email' when calling usersResendConfirmation");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/resend_confirmation.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "email", email));
		queryParams.addAll(client.parameterToPairs("", "confirmation_subject", confirmationSubject));
		queryParams.addAll(client.parameterToPairs("", "confirmation_template", confirmationTemplate));
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
	 * Retrieves the total number of User objects.
	 * Retrieves the total number of User objects.
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersCount() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/count.json".replaceAll("\\{format\\}","json");
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
	 * Deletes multiple Users objects.
	 * Deletes Users objects that match the query constraints provided in the `where` parameter.
If no `where` parameter is provided, all Users objects are deleted. 
Note that an HTTP 200 code (success)
is returned if the call completed successfully but the query matched no objects.

For performance reasons, the number of objects that can be deleted in a single batch delete 
operation is limited to 100,000.

The matched objects are deleted asynchronously in a separate process.      

The {@link #photo primary photos} associated with the matched objects are 
not deleted. 

You must be an application admin to run this command.

	 * 
	 * @param where Encoded JSON object that specifies constraint values for Users objects to delete.
If not specified, all Users objects are deleted.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject usersBatchDelete(String where) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/users/batch_delete.json".replaceAll("\\{format\\}","json");
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

}

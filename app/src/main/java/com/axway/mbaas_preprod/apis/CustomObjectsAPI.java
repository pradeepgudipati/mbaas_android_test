
 

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

public class CustomObjectsAPI {

	private SdkClient client;

	public CustomObjectsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Drops a CustomObjects collection.
	 * Drops a CustomObjects collection of a specified type. The collection is dropped
asynchronously in a separate process.

You must be an application admin to run this command.

	 * 
	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsAdminDropCollection(String classname) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsAdminDropCollection");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/admin_drop_collection.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
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

		Result result = client.invokeAPI(localVarPath, "delete", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (JSONObject) client.deserialize(result, new TypeToken<JSONObject>() {}.getType());
	}

	/**
	 * Create multiple custom objects
	 * Creates up to 100 custom objects.  The current user must be an application admin to use this API.

If there is an error inserting one of the objects, the response payload will return a 200 code, the
number of objects created, and the error for creating the first object that failed. The HTTP
response will be 202.

The `classname` of the object is part of the URL. You do not have to define
`classname` ahead of time. It will be created on the fly.

	 * 
	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

	 * @param jsonArray Array of JSON-encoded objects to create. You may specify up to 100 objects.
You do not have to define fields ahead of time,
simply set key-value pairs of the fields. Since the data must be encoded as
JSON, keys must not contain the dot character.

You may pass the following predefined fields: `acl_id`, `photo_id` and `user_id`, to
attach an ACL, Photo, or User object, respectively. Note that ArrowDB will not check if
the ID exists.

    json_array = [
        {
            custom_field: 'red',
            acl_id: '1234567890abcdef',
            photo_id: '0987654321fedcba',
            user_id: 'ab12cd34ef098765'
        }
    ]

The `tags` field is not supported for the batch create operations.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsBatchCreate(String classname, String jsonArray, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsBatchCreate");
		}
		// verify the required parameter 'jsonArray' is set
		if (jsonArray == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'json_array' when calling customObjectsBatchCreate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/batch_create.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (jsonArray != null) formParams.put("json_array", jsonArray);
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
	 * Deletes multiple CustomObject objects.
	 * Deletes CustomObjects objects that match the query constraints provided in the `where` parameter.
If no `where` parameter is provided, all CustomObject objects are deleted. 
Note that an HTTP 200 code (success)
is returned if the call completed successfully but the query matched no objects.

For performance reasons, the number of objects that can be deleted in a single batch delete 
operation is limited to 100,000.  

The matched objects are deleted asynchronously in a separate process. 

The {@link #photo primary photos} associated with the matched objects are not deleted. 

You must be an application admin to run this command.

	 * 
	 * @param where Encoded JSON object that specifies constraint values for CustomObjects objects to delete.
If not specified, all CustomObjects objects are deleted.

	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsBatchDelete(String classname, String where) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsBatchDelete");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/batch_delete.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
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
	 * Retrieves the total number of objects of the specified class.
	 * Retrieves the total number of objects of the specified class.
	 * 
	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsCount(String classname) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsCount");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/count.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
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
	 * Create Custom Object
	 * Create a custom object of type `classname`.

The `classname` of the object is part of the URL. You do not have to define
`classname` ahead of time. It will be created on the fly

	 * 
	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

	 * @param fields JSON encoding of object fields. You don't have to define fields ahead of time,
simply set key-value pairs of the fields. Since the data must be encoded as
JSON, keys must not contain the dot character.

For instance, if you want to define a car object:

    {
       "make": "nissan",
       "color": "blue",
       "year": 2005,
       "purchased_at": "2011-11-02 17:07:37 -0700",
       "used": false
    }

See the main description for {@link CustomObjects} for more information on
fields.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param tags Comma-separated list of tags associated with this object.

If the `tags` parameter is omitted and a `tags` key is included in the
`fields` dictionary, `fields.tags` will be used instead.

	 * @param photo New photo to attach as the primary photo for the object.

When you use the `photo` parameter to attach a new photo, you can use the
[custom resize and sync options](#!/guide/photosizes).

	 * @param photoId ID of an existing photo to attach as the primary photo for the object.

	 * @param aclName Name of an {@link ACLs} to associate with this checkin object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this checkin object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId User ID to create the object on behalf of.

The current login user must be an application admin to create an object on
behalf of another user.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsCreate(String classname, String fields, Boolean prettyJson, String tags, File photo, String photoId, String aclName, String aclId, String suId) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsCreate");
		}
		// verify the required parameter 'fields' is set
		if (fields == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'fields' when calling customObjectsCreate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/create.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (fields != null) formParams.put("fields", fields);
		if (prettyJson != null) formParams.put("pretty_json", prettyJson);
		if (tags != null) formParams.put("tags", tags);
		if (photo != null) formParams.put("photo", photo);
		if (photoId != null) formParams.put("photo_id", photoId);
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
	 * Query Custom Objects
	 * Query custom object by specified fields with sorting and paginating. 

**Notes**:

* If a custom object contains fields or values that are greater than 1KB in length, you will
not be able to query on that field. For more information, see [Indexing Size Limit for Custom Objects and Fields](#!/guide/customfields-section-indexing-size-limit-for-custom-objects-and-fields).
* Currently, you cannot query or sort data stored inside an array or hash.
* You can paginate query results using `skip` and `limit` parameters, or by including
a `where` clause to limit the results to objects whose IDs fall within a specified range.
For details, see [Query Pagination](#!/guide/search_query-section-query-pagination).

For details about using the query parameters,
see the [Search and Query guide](#!/guide/search_query).

	 * 
	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

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

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param showUserLike If set to **true**, each CustomObject in the response includes `"current_user_liked: true"`
 if the current user has liked the object. If the current user has not liked the object, the 
`current_user_liked` field is not included in the response.

	 * @param where Constraint values for fields. `where` should be encoded JSON.

Each value in the search query needs to be less that 1024 bytes.
If the value is larger than 1024 bytes, the query does not return any results.

In addition to developer created fields, custom objects include
four predefined fields that can be queried as well:

*   `user_id: String`.  Object owner's user ID.

*   `tags_array: String`. List of tags.

*   `created_at: Date`. Timestamp when the object was created.

*   `updated_at: Date`. Timestamp when the object was updated.

If `where` is not specified, `query` returns all objects.

	 * @param order Sort results by one or more fields, specified as a comma-separated list of
field names.
See the [Search and Query guide](#!/guide/search_query) for more information.

Fields with a Hash data type cannot be sorted. Custom objects include
two predefined sortable fields:

    `created_at: Date` - Timestamp when the object was created.
    `updated_at: Date` - Timestamp when the object was last updated.

For example, if you want to query cars and sort them by `make` and
`created_at`:

    "order": "make,created_at"

To reverse the sorting order, simply add `-` in front of a field. For example,
to sort results by `make` in ascending order then by `created_at` in descending
order:

    "order": "make,-created_at"

	 * @param sel Selects the object fields to display. Do not use this parameter with `unsel`.

	 * @param unsel Selects the object fields NOT to display. Do not use this parameter with `sel`.

	 * @param responseJsonDepth Nested object depth level counts in response json.
In order to reduce server API calls from an application, the response json may
include not just the objects that are being queried/searched, but also with
some important data related to the returning objects such as object's owner or
referencing objects.

Default is 1, valid range is 1 to 8.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsQuery(String classname, Integer page, Integer perPage, Integer limit, Integer skip, Boolean prettyJson, Boolean showUserLike, String where, String order, String sel, String unsel, Integer responseJsonDepth) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsQuery");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/query.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "page", page));
		queryParams.addAll(client.parameterToPairs("", "per_page", perPage));
		queryParams.addAll(client.parameterToPairs("", "limit", limit));
		queryParams.addAll(client.parameterToPairs("", "skip", skip));
		queryParams.addAll(client.parameterToPairs("", "pretty_json", prettyJson));
		queryParams.addAll(client.parameterToPairs("", "show_user_like", showUserLike));
		queryParams.addAll(client.parameterToPairs("", "where", where));
		queryParams.addAll(client.parameterToPairs("", "order", order));
		queryParams.addAll(client.parameterToPairs("", "sel", sel));
		queryParams.addAll(client.parameterToPairs("", "unsel", unsel));
		queryParams.addAll(client.parameterToPairs("", "response_json_depth", responseJsonDepth));


		
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
	 * Update Custom Object
	 * Any of the same parameters as [Create Custom
Object](/docs/api/v1/custom_objects/create) can be used to update a custom
object. Only the owner of the object or user who is entitled write
permission(ACL) to the object can update the object.

Application Admin can update any Custom Object.

	 * 
	 * @param iD The object ID of the custom object to update.

	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

	 * @param fields JSON encoding of object fields to update.

If any of the fields do not exist in the current object, they will be added.
To delete an existing field, use {"field_name" : null}. For example, the car object created in
{@link CustomObjects#create CustomObject.create} is:

    { "make": "nissan",
      "color": "blue",
      "year": 2005,
      "purchased_at": "2011-11-02 17:07:37 -0700",
      "used": false,
      "coordinates": [-122.1, 37.1]
    }

To change color, remove the `purchased_at` field and add a new field `mileage`,
you could pass the following JSON object to fields:

    { "color": "purple",
      "purchased_at": null,
      "mileage": 10000
    }

	 * @param tags Comma-separated list of tags to associate with this object, for example, "hiking,swiming."
Replaces any existing tags.

If the `tags` parameter is omitted and a `tags` key is included in the
`fields` dictionary, `fields.tags` will be used instead.

	 * @param photo New photo to attach as the primary photo for the object.

When you use the `photo` parameter to attach a new photo, you can use the
[custom resize and sync options](#!/guide/photosizes).

	 * @param photoId ID of an existing photo to attach as the primary photo for the object.

	 * @param aclName Name of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param aclId ID of an {@link ACLs} to associate with this object.

An ACL can be specified using `acl_name` or `acl_id`. The two parameters are
mutually exclusive.

	 * @param suId User to update the Custom object on behalf of. The user must be the creator of the object.

The current user must be an application admin to update a Custom object on
behalf of another user.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsUpdate(String classname, String iD, String fields, String tags, File photo, String photoId, String aclName, String aclId, String suId, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsUpdate");
		}
		// verify the required parameter 'iD' is set
		if (iD == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'id' when calling customObjectsUpdate");
		}
		// verify the required parameter 'fields' is set
		if (fields == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'fields' when calling customObjectsUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/update.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (iD != null) formParams.put("id", iD);
		if (fields != null) formParams.put("fields", fields);
		if (tags != null) formParams.put("tags", tags);
		if (photo != null) formParams.put("photo", photo);
		if (photoId != null) formParams.put("photo_id", photoId);
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
	 * Delete Custom Object
	 * Only the owner of the object or user who is granted write permission by the
object's ACL can delete the object.

The {@link #photo primary photo} associated with the CustomObject is not deleted.

An application admin can delete any custom object.

	 * 
	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

	 * @param iD The object ID of the custom object to delete.

You must specify either `id` or `ids`.

	 * @param ids A comma-separated list of object IDs of the custom objects to delete.

You must specify either `id` or `ids`.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param suId User to delete the Custom object on behalf of. The user must be the creator of the object.

The current user must be an application admin to delete a Custom object on
behalf of another user.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsDelete(String classname, String iD, String ids, Boolean prettyJson, String suId) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsDelete");
		}
		// verify the required parameter 'iD' is set
		if (iD == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'id' when calling customObjectsDelete");
		}
		// verify the required parameter 'ids' is set
		if (ids == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'ids' when calling customObjectsDelete");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/delete.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (iD != null) formParams.put("id", iD);
		if (ids != null) formParams.put("ids", ids);
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
	 * Show Custom Object(s)
	 * Show a custom object's info.

	 * 
	 * @param classname Type of custom object. Specified as part of the URL path, not in the
parameters.

	 * @param iD The object ID of the custom object to show.

You must specify either `id` or `ids`.

	 * @param ids A comma-separated list of object IDs of the custom objects to show.

You must specify either `id` or `ids`.

	 * @param responseJsonDepth Nested object depth level counts in response json.
In order to reduce server API calls from an application, the response json may
include not just the objects that are being queried/searched, but also with
some important data related to the returning objects such as object's owner or
referencing objects.

Default is 1, valid range is 1 to 8.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @param showUserLike If set to **true** the CustomObject in the response will include `"current_user_liked: true"`
if the current user has liked the object. If the user has not liked the object, the
`current_user_liked` field is not included in the response.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject customObjectsShow(String classname, String iD, String ids, Integer responseJsonDepth, Boolean prettyJson, Boolean showUserLike) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'classname' is set
		if (classname == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'classname' when calling customObjectsShow");
		}
		// verify the required parameter 'iD' is set
		if (iD == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'id' when calling customObjectsShow");
		}
		// verify the required parameter 'ids' is set
		if (ids == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'ids' when calling customObjectsShow");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/objects/{classname}/show.json".replaceAll("\\{format\\}","json")
			.replaceAll("\\{classname\\}", client.escapeString(classname.toString()));
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "id", iD));
		queryParams.addAll(client.parameterToPairs("", "ids", ids));
		queryParams.addAll(client.parameterToPairs("", "response_json_depth", responseJsonDepth));
		queryParams.addAll(client.parameterToPairs("", "pretty_json", prettyJson));
		queryParams.addAll(client.parameterToPairs("", "show_user_like", showUserLike));


		
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

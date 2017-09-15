
 

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

public class AppsAPI {

	private SdkClient client;

	public AppsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Create an ArrowDB app
	 * 
	 * 
	 * @param name App name
	 * @param description App's description
	 * @param ct App's description
	 * @param orgId Particular organization id.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsAppsCreate(String ct, String name, String description, String orgId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "ct", ct));


		if (name != null) formParams.put("name", name);
		if (description != null) formParams.put("description", description);
		if (orgId != null) formParams.put("org_id", orgId);
		
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
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Show an ArrowDB app&#39;s details
	 * 
	 * 
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsAppsShow() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		
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
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Update an ArrowDB app
	 * 
	 * 
	 * @param name App name.
	 * @param description App description.
	 * @param friendsTwoWay Friend Request Type.
	 * @param allowUserCreation Allow user creation in the app.
	 * @param newUserVerification New User Email Verification
	 * @param secureIdentity false : api, true : auth secure identity server.
	 * @param orgId Particular organization id.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsAppsUpdate(String name, String description, Boolean friendsTwoWay, Boolean allowUserCreation, Boolean newUserVerification, Boolean secureIdentity, String orgId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (name != null) formParams.put("name", name);
		if (description != null) formParams.put("description", description);
		if (friendsTwoWay != null) formParams.put("friends_two_way", friendsTwoWay);
		if (allowUserCreation != null) formParams.put("allow_user_creation", allowUserCreation);
		if (newUserVerification != null) formParams.put("new_user_verification", newUserVerification);
		if (secureIdentity != null) formParams.put("secure_identity", secureIdentity);
		if (orgId != null) formParams.put("org_id", orgId);
		
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

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Count app&#39;s objects.
	 * 
	 * 
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsAppsCountObjects() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/app_object_counts.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		
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
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Remove an apple certificate on an app
	 * 
	 * 
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsRemoveAppleCertificates() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/remove_apple_certificates.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		
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
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Set an apple certificate on an app.
	 * 
	 * 
	 * @param certificateDev Dev certificate file.
	 * @param devCertPassword Password for dev certificate file.
	 * @param certificateProd Prod certificate file.
	 * @param productionCertPassword Password for prod certificate file.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsSetAppleCertificates(File certificateDev, String devCertPassword, File certificateProd, String productionCertPassword) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/set_apple_certificates.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (certificateDev != null) formParams.put("certificate_dev", certificateDev);
		if (devCertPassword != null) formParams.put("dev_cert_password", devCertPassword);
		if (certificateProd != null) formParams.put("certificate_prod", certificateProd);
		if (productionCertPassword != null) formParams.put("production_cert_password", productionCertPassword);
		
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
			"application/x-www-form-urlencoded", "multipart/form-data"
		};
		final String contentType = client.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] { "api_key" };

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Set an android settings on an app.
	 * 
	 * 
	 * @param androidAppPackage 
	 * @param androidGcmApikey 
	 * @param androidGcmSenderId 
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsSetAndroidCertificate(String androidAppPackage, String androidGcmApikey, String androidGcmSenderId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/set_android_certificate.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (androidAppPackage != null) formParams.put("android_app_package", androidAppPackage);
		if (androidGcmApikey != null) formParams.put("android_gcm_apikey", androidGcmApikey);
		if (androidGcmSenderId != null) formParams.put("android_gcm_sender_id", androidGcmSenderId);
		
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

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Set a windows push settings on an app.
	 * 
	 * 
	 * @param wnsSid 
	 * @param wnsClientSecret 
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsSetWindowsCertificate(String wnsSid, String wnsClientSecret) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/set_windows_certificate.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (wnsSid != null) formParams.put("wns_sid", wnsSid);
		if (wnsClientSecret != null) formParams.put("wns_client_secret", wnsClientSecret);
		
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

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Delete an app or apps.
	 * 
	 * 
	 * @param groupId Note that it should take either group_id or app_id, not both.
	 * @param appId Note that it should take either group_id or app_id, not both.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsAppsDelete(String groupId, String appId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (groupId != null) formParams.put("group_id", groupId);
		if (appId != null) formParams.put("app_id", appId);
		
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
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Delete an app data.
	 * 
	 * 
	 * @param appId App id.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsAppsDeleteData(String appId) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/delete/data.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (appId != null) formParams.put("app_id", appId);
		
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
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

	/**
	 * Set SMTP or MessageGears settings for an app, app email will be delivered through SMTP or MessageGears.
	 * 
	 * 
	 * @param address SMTP server Address.
	 * @param username SMTP username.
	 * @param password SMTP password.
	 * @param tls SMTP TLS support.
	 * @param port SMTP server port.
	 * @param authentication SMTP: Valid valud is Plain, Login or MD5.
	 * @param domain SMTP: Domain.
	 * @param provider Email provider, it 's "smtp" by default if not provided.
	 * @param accountid App key.
	 * @param apikey App key.
	 * @param endpoint App key.
	 * @param senderEmail MessageGears: It's required by MessageGears, should be a valid email address, such as "acs_support@appcelerator.com".
	 * @param senderName MessageGears: Such as "Appcelerator Cloud Service Support Team".
	 * @param devMode 
	 * @param replacementEmail Default Sender Email.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse appsAppsEmailsSetting(String accountid, String apikey, String endpoint, String senderEmail, String senderName, String address, String username, String password, Boolean tls, String port, String authentication, String domain, String provider, Boolean devMode, String replacementEmail) throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/apps/emails/setting.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "accountid", accountid));
		queryParams.addAll(client.parameterToPairs("", "apikey", apikey));
		queryParams.addAll(client.parameterToPairs("", "endpoint", endpoint));
		queryParams.addAll(client.parameterToPairs("", "sender_email", senderEmail));
		queryParams.addAll(client.parameterToPairs("", "sender_name", senderName));


		if (address != null) formParams.put("address", address);
		if (username != null) formParams.put("username", username);
		if (password != null) formParams.put("password", password);
		if (tls != null) formParams.put("tls", tls);
		if (port != null) formParams.put("port", port);
		if (authentication != null) formParams.put("authentication", authentication);
		if (domain != null) formParams.put("domain", domain);
		if (provider != null) formParams.put("provider", provider);
		if (devMode != null) formParams.put("dev_mode", devMode);
		if (replacementEmail != null) formParams.put("replacement_email", replacementEmail);
		
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

		Result result = client.invokeAPI(localVarPath, "put", queryParams, bodyParameter, headerParams, formParams, accept, contentType, authNames);
		return (SuccessResponse) client.deserialize(result, new TypeToken<SuccessResponse>() {}.getType());
	}

}

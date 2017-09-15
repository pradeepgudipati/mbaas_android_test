
 

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

public class EmailTemplateAPI {

	private SdkClient client;

	public EmailTemplateAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 *  Create an email template
	 * 
	 * 
	 * @param name Email template name.
	 * @param subject Email template subject.
	 * @param body Email template html body.
	 * @param plainBody Email template plain text body. At least one of body and plain_body is required for one template. And body is to store html format email content, and plain_body is to store plain text email content. 
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse emailTemplateEmailTemplatesCreate(String name, String subject, String body, String plainBody) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'name' is set
		if (name == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'name' when calling emailTemplateEmailTemplatesCreate");
		}
		// verify the required parameter 'subject' is set
		if (subject == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'subject' when calling emailTemplateEmailTemplatesCreate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/email_templates/create.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (name != null) formParams.put("name", name);
		if (subject != null) formParams.put("subject", subject);
		if (body != null) formParams.put("body", body);
		if (plainBody != null) formParams.put("plain_body", plainBody);
		
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
	 *  Update an email template
	 * 
	 * 
	 * @param emailTemplateId Email template id.
	 * @param name Email template name.
	 * @param subject Email template subject.
	 * @param body Email template html body.
	 * @param plainBody Email template plain text body.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse emailTemplateEmailTemplatesUpdate(String emailTemplateId, String name, String subject, String body, String plainBody) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'emailTemplateId' is set
		if (emailTemplateId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'email_template_id' when calling emailTemplateEmailTemplatesUpdate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/email_templates/update.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (emailTemplateId != null) formParams.put("email_template_id", emailTemplateId);
		if (name != null) formParams.put("name", name);
		if (subject != null) formParams.put("subject", subject);
		if (body != null) formParams.put("body", body);
		if (plainBody != null) formParams.put("plain_body", plainBody);
		
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
	 *  Show an email template
	 * 
	 * 
	 * @param emailTemplateId Email template id.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse emailTemplateEmailTemplatesShow(String emailTemplateId) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'emailTemplateId' is set
		if (emailTemplateId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'email_template_id' when calling emailTemplateEmailTemplatesShow");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/email_templates/show.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();

		queryParams.addAll(client.parameterToPairs("", "email_template_id", emailTemplateId));


		
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
	 *  Delete an email template
	 * 
	 * 
	 * @param emailTemplateId Email template id.
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse emailTemplateEmailTemplatesDelete(String emailTemplateId) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'emailTemplateId' is set
		if (emailTemplateId == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'email_template_id' when calling emailTemplateEmailTemplatesDelete");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/email_templates/delete.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (emailTemplateId != null) formParams.put("email_template_id", emailTemplateId);
		
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
	 * Query email templates
	 * 
	 * 
	 * @return SuccessResponse
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public SuccessResponse emailTemplateEmailTemplatesQuery() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/email_templates/query.json".replaceAll("\\{format\\}","json");
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

}

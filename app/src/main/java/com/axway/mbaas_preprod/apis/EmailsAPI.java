 

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

public class EmailsAPI {

	private SdkClient client;

	public EmailsAPI(SdkClient client) {
		this.client = client;
	}

	/**
	 * Retrieves the total number of email templates.
	 * Retrieves the total number of email templates.
	 * 
	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject emailsCount() throws SdkException {
		Object bodyParameter = null;
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/email_templates/count.json".replaceAll("\\{format\\}","json");
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
	 * Send Emails
	 * Sends an email to a list of email addresses you specify. 

When sending an email, you specify the name of an email template created in Dashboard 
(see [Managing Email Templates](http://docs.appcelerator.com/platform/latest/#!/guide/Managing_Email_Templates)),
and one or more email recipients. You can optionally specify the email content type (
HTML, plain-text, or multipart), as well as values for any placeholder fields
defined by the template. The below examples demonstrate these concepts.

An email template's body can contain HTML-formatted or plain-text content 
, or both. When you send an email, you can specify whether the email should be sent 
as HTML, plain text, or multipart using the `content_type` parameter. 
A multipart email contains both the plain text and HTML versions; which 
version is displayed is determined by the recipient's email client.

If the `content_type` parameter is **not** provided in the request, the format
is automatically chosen based on the following rules:

* If the email template contains both HTML and plain-text bodies, it will be sent in a multipart format. 
* If the email template contains only an HTML body, it will be sent in an HTML format. 
* If the email template contains only a plain-text body. it will be sent in plain-text format.      

If the `content_type` parameter is provided in the request, then the following
must be true:

* If `content_type` is "html", then the email template must define an HTML body.
* If `content_type` is "plain", then the email template must define a plain text body.
* If `content_type` is "multipart", then the email template must define both HTML and plain text body.

	 * 
	 * @param template Name of the email template you have created.

	 * @param recipients Comma separated list of email addresses.
	 * @param contentType_ Specifies the email's content-type. The following values are valid:

* "plain" &mdash; If specified, the email template must define a plain text body.
* "html" &mdash; If specified, the email template must define an HTML body.
* "multipart" &mdash; If specified, the email template must define both a plain text
   and HTML body.
   
See [Managing Email Templates](http://docs.appcelerator.com/platform/latest/#!/guide/Managing_Email_Templates)
for details on creating email templates.

	 * @param from The sender's email address. 

**Notes**:
  
  * If you use Google as your SMTP server, the email will always be sent using the email account 
  you used to configure the SMTP service. 
  * Some SMTP service provider require the sender's email address to be present.

	 * @param prettyJson Determines if the JSON response is formatted for readability (`true`), or displayed on a
single line (`false`). Default is `false`.

	 * @return JSONObject
	 * @throws SdkException if fails to make API call
	 */
	 @SuppressWarnings("unchecked")
	public JSONObject emailsEmailFromTemplate(String template, String recipients, String contentType_, String from, Boolean prettyJson) throws SdkException {
		Object bodyParameter = null;
		// verify the required parameter 'template' is set
		if (template == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'template' when calling emailsEmailFromTemplate");
		}
		// verify the required parameter 'recipients' is set
		if (recipients == null) {
			throw new SdkException(SdkException.MISSING_PARAMETER, "Missing the required parameter 'recipients' when calling emailsEmailFromTemplate");
		}
		if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
  		 // On UI thread.
		    throw new SdkException(SdkException.ERROR_RUNNING_ON_UI_THREAD, "API Call is running on UI thread. Please call it inside an Async task or a thread");
		}
		// create path and map variables
		String localVarPath = "/custom_mailer/email_from_template.json".replaceAll("\\{format\\}","json");
		// query params
		List<Pair> queryParams = new ArrayList<>();
		Map<String, String> headerParams = new HashMap<>();
		Map<String, Object> formParams = new HashMap<>();



		if (template != null) formParams.put("template", template);
		if (recipients != null) formParams.put("recipients", recipients);
		if (contentType_ != null) formParams.put("content_type", contentType_);
		if (from != null) formParams.put("from", from);
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

}

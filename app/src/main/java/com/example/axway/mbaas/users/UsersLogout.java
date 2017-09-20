/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */
package com.example.axway.mbaas.users;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.axway.mbaas_preprod.auth.SdkCookiesHelper;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;


public class UsersLogout extends Activity {
	private static UsersLogout currentActivity;
	JSONObject response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_logout);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		new apiTask().execute();

	}
	
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}

	private class apiTask extends AsyncTask<Void, Void, JSONObject> {
		private SdkException exceptionThrown =null;

		JSONObject successResponse;

		@Override
		protected void onPreExecute() {
			try {
				if(SdkCookiesHelper.getInstance().isAvailable())
                    SdkCookiesHelper.getInstance().logoutUser();
			} catch (SdkException e) {
				e.printStackTrace();
			}
		}
		@Override
		protected JSONObject doInBackground(Void... voids) {
			try {
				successResponse = new UsersAPI(SdkClient.getInstance()).usersLogoutUser();
			} catch (SdkException e) {
				exceptionThrown = e;
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject xml) {
			try {
				if(exceptionThrown == null && xml.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok"))
                {
                    ((TextView) findViewById(R.id.users_logout_text_view1)).setText("Logged out!");
                }
                else
                    handleSDKException(exceptionThrown,currentActivity);
			} catch (JSONException e) {
				handleException(e,currentActivity);
			}

		}
	}
}

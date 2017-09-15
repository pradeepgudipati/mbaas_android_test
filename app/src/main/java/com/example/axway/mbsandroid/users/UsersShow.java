/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbsandroid.users;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UsersShow extends Activity {
	private static UsersShow currentActivity;
	 HashMap<String, Object> data = new HashMap<String, Object>();
JSONObject successResponse;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_show);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		textView = (TextView) findViewById(R.id.users_show_text_view);
		textView.setMovementMethod(new ScrollingMovementMethod());

		Intent intent = getIntent();
		String userId = intent.getStringExtra("userId");
		
		// Create dictionary of parameters to be passed with the request
		data.put("user_id", userId);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					successResponse = new UsersAPI(SdkClient.getInstance()).usersShow(data.get("user_id").toString(), null, null, null, null);
				} catch (SdkException e) {
					e.printStackTrace();
				}
			}
		}).start();
		try {
			textView.setText(successResponse.toString(4));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	/*	try {
			APSUsers.show(data, new APSResponseHandler() {
				
				@Override
				public void onResponse(final APSResponse e) {
					if (e.getSuccess()) {
						try {
							textView.setText(new JSONObject(e.getResponseString()).toString(4));
						} catch (APSCloudException e1) {
							Utils.handleException(e1, currentActivity);
						} catch (JSONException e1) {
							Utils.handleException(e1, currentActivity);
						}
					} else {
						Utils.handleErrorInResponse(e, currentActivity);
					}
				}

				@Override
				public void onException(APSCloudException e) {
					Utils.handleException(e, currentActivity);
				}

			});
		} catch (APSCloudException e) {
			Utils.handleException(e, currentActivity);
		}*/
		
	}
	
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
	
}

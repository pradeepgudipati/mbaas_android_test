/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbsandroid.users;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.axway.mbaas.R;


public class UsersShowMe extends Activity {
	private static UsersShowMe currentActivity;
	
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_show_me);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		textView = (TextView) findViewById(R.id.users_show_me_text_view);
		textView.setMovementMethod(new ScrollingMovementMethod());
	/*
		try {
			APSUsers.showMe(null, new APSResponseHandler() {
				
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

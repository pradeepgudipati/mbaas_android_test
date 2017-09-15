/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbsandroid.users;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class UsersLogin extends Activity {
	private static UsersLogin currentActivity;	
	private ArrayList<EditText> fields = new ArrayList<EditText>();
	HashMap<String, Object> data = new HashMap<String, Object>();
	JSONObject successResponse;
	private EditText usernameField;
	private EditText passwordField;
	private Button button1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_login);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		usernameField = (EditText) findViewById(R.id.users_login_username_field);
		passwordField = (EditText) findViewById(R.id.users_login_password_field);

		
		button1 = (Button) findViewById(R.id.users_login_button1);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submitForm();
			}
		});
		
		fields.add(usernameField);
		fields.add(passwordField);
	}
	
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
	
	private void submitForm() {
		for (EditText field : fields) {
			if (field.getText().toString().length() <= 0) {
				field.requestFocus();
				return;
			}
		}
		
		button1.setVisibility(View.GONE);
		
		// Create dictionary of parameters to be passed with the request
		data.put("login", usernameField.getText().toString());
		data.put("password", passwordField.getText().toString());


		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
				 successResponse = new UsersAPI(SdkClient.getInstance()).usersLoginUser(
							data.get("login").toString(),
							data.get("password").toString());
				} catch (SdkException e) {
					e.printStackTrace();
				}
			}
		}).start();
		Log.d("usersLoginUser", successResponse.toString());

		/*try {
			APSUsers.login(data, new APSResponseHandler() {
				
				@Override
				public void onResponse(final APSResponse e) {
					if (e.getSuccess()) {
						try {
							String userId = e.getResponse().getJSONArray("users").getJSONObject(0).getString("id");
							new AlertDialog.Builder(currentActivity)
							.setTitle("Success!").setMessage("Logged in! You are now logged in as " + userId)
							.setPositiveButton(android.R.string.ok, null)
							.setIcon(android.R.drawable.ic_dialog_info)
							.show();
						} catch (JSONException e1) {
							Utils.handleException(e1, currentActivity);
						}
					} else {
						Utils.handleErrorInResponse(e, currentActivity);
					}
					button1.setVisibility(View.VISIBLE);
					
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
}

/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class UsersCreate extends Activity {
	private static UsersCreate currentActivity;
	private ArrayList<EditText> fields = new ArrayList<EditText>();
	JSONObject successResponse;
	private EditText usernameField;
	private EditText passwordField;
	private EditText passwordConfField;
	private EditText firstNameField;
	private EditText lastNameField;
	private EditText emailField;
	private Button button1;
	HashMap<String, Object> data = new HashMap<String, Object>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_create);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		usernameField = (EditText) findViewById(R.id.users_create_username_field);
		passwordField = (EditText) findViewById(R.id.users_create_password_field);
		passwordConfField = (EditText) findViewById(R.id.users_create_password_conf_field);
		firstNameField = (EditText) findViewById(R.id.users_create_first_name_field);
		lastNameField = (EditText) findViewById(R.id.users_create_last_name_field);
		emailField = (EditText) findViewById(R.id.users_create_email_field);

		button1 = (Button) findViewById(R.id.users_create_button1);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (EditText field : fields) {
					if (field.getText().toString().length() <= 0) {
						field.requestFocus();
						return;
					}
				}
				if (!passwordField.getText().toString().equals(passwordConfField.getText().toString())) {
					new AlertDialog.Builder(currentActivity)
							.setTitle("Alert").setMessage("Passwords do not match!")
							.setPositiveButton(android.R.string.ok, null)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.show();
					return;
				}
				else
				new apiTask().execute();
			}
		});
		
		// Required fields
		fields.add(usernameField);
		fields.add(passwordField);
		fields.add(passwordConfField);
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
			button1.setVisibility(View.GONE);

			// Create dictionary of parameters to be passed with the request
			//final HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("username", usernameField.getText().toString());
			data.put("password", passwordField.getText().toString());
			data.put("password_confirmation", passwordConfField.getText().toString());
			data.put("first_name", firstNameField.getText().toString());
			data.put("last_name", lastNameField.getText().toString());
			data.put("email", emailField.getText().toString());
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {
			try {
				successResponse = new UsersAPI(SdkClient.getInstance()).usersCreate(
						data.get("email").toString(),
						data.get("username").toString(),
						data.get("password").toString(),
						data.get("password_confirmation").toString(),
						data.get("first_name").toString(),
						data.get("last_name").toString(),
						null, null, null, null, null, null, null, null, null, null, null);


			} catch (final SdkException e) {
				exceptionThrown = e;
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject xml) {
			try {
				if (exceptionThrown == null && xml.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
					new AlertDialog.Builder(currentActivity)
							.setTitle("Success!").setMessage("User " + data.get("username").toString()+ "Created!!")
							.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									finish();
								}
							})
							.setIcon(android.R.drawable.ic_dialog_info)
							.show();
				} else
					handleSDKException(exceptionThrown, currentActivity);
			} catch (JSONException e) {
				handleException(e, currentActivity);
			}

		}
	}
}

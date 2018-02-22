/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.customobjects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.CustomObjectsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.example.axway.mbaas.Utils.handleException;

public class CustomObjectsUpdate extends Activity {
	private static CustomObjectsUpdate currentActivity;
	
	private TextView statusTextView;
	private Button removeButton;
	private Button updateButton;
	
	private LinearLayout linearLayout;
	private String className;
	private OnKeyListener keyListener;
	
	private String customObjectId;
	private JSONObject customObject;
	private ArrayList<EditText> newPropertyValueFieldList = new ArrayList<EditText>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customobjects_update);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		linearLayout = (LinearLayout) findViewById(R.id.customobjects_update_linear_layout);
		statusTextView = (TextView) findViewById(R.id.customobjects_update_status_textView);
		
		keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					new updateCustomObject().execute();
				}
				return false;
			}
		};
				
		Intent intent = getIntent();
		customObjectId = intent.getStringExtra("custom_object_id");
		className = intent.getStringExtra("classname");

		updateButton = (Button) findViewById(R.id.customobjects_update_update_button);
		updateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new updateCustomObject().execute();
			}
		});
		
		removeButton = (Button) findViewById(R.id.customobjects_update_remove_button);
		removeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity, CustomObjectsRemove.class);
				intent.putExtra("custom_object_id", customObjectId);
				intent.putExtra("classname", className);
				startActivity(intent);
			}
		});
		
		new showCustomObject().execute();
	}


	private class updateCustomObject extends AsyncTask<Void, Void, JSONObject> {

		private SdkException exceptionThrown = null;
		JSONObject successResponse = new JSONObject();
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject fieldsToSend = new JSONObject();

		@Override
		protected void onPreExecute() {
			updateButton.setVisibility(View.GONE);

			// Create dictionary of parameters to be passed with the request


			int propertyCount = newPropertyValueFieldList.size();
			for(int i = 1; i < propertyCount; i++) {
				EditText property = null;
				property = newPropertyValueFieldList.get(i);
				try {
					fieldsToSend.put(property.getHint().toString(), property.getText().toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			data.put("classname", className);
			data.put("id", customObjectId);
			data.put("fields", fieldsToSend);
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new CustomObjectsAPI(SdkClient.getInstance()).customObjectsUpdate(data.get("classname").toString(),data.get("id").toString(),
						data.get("fields").toString(),null,null,null,null,null,null,null);


			} catch (SdkException e) {
				exceptionThrown = e;
				//handleSDKException(e, currentActivity);
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				if (exceptionThrown == null && json.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
					new AlertDialog.Builder(currentActivity)
							.setTitle("Success!").setMessage(json.getJSONObject("meta").toString())
							.setPositiveButton(android.R.string.ok, null)
							.setIcon(android.R.drawable.ic_dialog_info)
							.show();
					updateButton.setVisibility(View.VISIBLE);
				} else
					Utils.handleSDKException(exceptionThrown, currentActivity);
			} catch (JSONException e) {
				handleException(e, currentActivity);
			}
		}
	}

	private class showCustomObject extends AsyncTask<Void, Void, JSONObject> {

		private SdkException exceptionThrown = null;
		JSONObject successResponse;
		HashMap<String, Object> data = new HashMap<String, Object>();

		@Override
		protected void onPreExecute() {
			data.put("id", customObjectId);
			data.put("classname", className);
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new CustomObjectsAPI(SdkClient.getInstance()).customObjectsShow(
						data.get("classname").toString(),
						data.get("id").toString(),
						data.get("id").toString(),null,null,null
						);


			} catch (SdkException e) {
				exceptionThrown = e;
				//handleSDKException(e, currentActivity);
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				statusTextView.setVisibility(View.GONE);
				updateButton.setVisibility(View.VISIBLE);
				removeButton.setVisibility(View.VISIBLE);

				try {
					customObject = json.getJSONObject("response").getJSONArray(className).getJSONObject(0);
					Iterator<?> keys = customObject.keys();

					while(keys.hasNext()){
						String newPropertyKey = (String) keys.next();
						if(newPropertyKey.equals("id") || newPropertyKey.equals("created_at") || newPropertyKey.equals("updated_at") || newPropertyKey.equals("user")) {
							continue;
						}

						String val= customObject.getString(newPropertyKey);
						EditText newPropertyValueField = new EditText(currentActivity);

						newPropertyValueField.setHint(newPropertyKey);
						newPropertyValueField.setText(val);
						newPropertyValueField.setOnKeyListener(keyListener);
						newPropertyValueFieldList.add(newPropertyValueField);

						linearLayout.removeView(updateButton);
						linearLayout.addView(newPropertyValueField);
						linearLayout.addView(updateButton);
					}

				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}

			} else {
				Utils.handleSDKException(exceptionThrown, currentActivity);
			}
		}
	}
}

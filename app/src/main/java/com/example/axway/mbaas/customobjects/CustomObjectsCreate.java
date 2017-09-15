/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.customobjects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.CustomObjectsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomObjectsCreate extends Activity {
	private static CustomObjectsCreate currentActivity;
	
	private EditText classNameField;
	private EditText newPropertyKeyField;
	private Button createButton;
	private Button addNewPropertyButton;
	private LinearLayout linearLayout;
	
	private ArrayList<EditText> newPropertyValueFieldList = new ArrayList<EditText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customobjects_create);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		linearLayout = (LinearLayout) findViewById(R.id.customobjects_create_linear_layout);
		classNameField = (EditText) findViewById(R.id.customobjects_create_class_name_field);
		newPropertyKeyField = (EditText) findViewById(R.id.customobjects_create_new_property_key_field);
		
		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					new createCustomObject().execute();
				} else if (keyCode == KeyEvent.KEYCODE_BACK) {
					currentActivity.finish();
				}
				return false;
			}
		};
		
		classNameField.setOnKeyListener(keyListener);
		newPropertyKeyField.setOnKeyListener(keyListener);
		
		addNewPropertyButton = (Button) findViewById(R.id.customobjects_create_add_new_property_button);
		addNewPropertyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createNewPropertyKey();
			}
		});
		
		createButton = (Button) findViewById(R.id.customobjects_create_create_button);
		createButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new createCustomObject().execute();
			}
		});
	}
	
	private void createNewPropertyKey() {
		String newPropertyKey = newPropertyKeyField.getText().toString();
		if(newPropertyKey.equals(null) || newPropertyKey.equals("")) {
			new AlertDialog.Builder(currentActivity)
			.setTitle("Alert").setMessage("Please fill in the \"New Property Key\" text field!")
			.setPositiveButton(android.R.string.ok, null)
			.setIcon(android.R.drawable.ic_dialog_info)
			.show();
			return;

		} else {
			EditText newPropertyValueField = new EditText(currentActivity);
			newPropertyValueField.setHint(newPropertyKey);
			newPropertyKeyField.setText("");
			
			linearLayout.removeView(createButton);
			linearLayout.addView(newPropertyValueField);
			linearLayout.addView(createButton );
			
			newPropertyValueFieldList.add(newPropertyValueField);
		}
	}


	private class createCustomObject extends AsyncTask<Void, Void, JSONObject> {
		HashMap<String, Object> data = new HashMap<String, Object>();
		//HashMap<String, Object> fieldsToSend = new HashMap<String, Object>();
		JSONObject fieldsToSend = new JSONObject();

		private SdkException exceptionThrown = null;
		JSONObject successResponse;
		String fields;

		@Override
		protected void onPreExecute() {
			String className = classNameField.getText().toString();
			if(className.equals(null) || className.equals("")) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Alert").setMessage("Please enter a class name!")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_info)
						.show();
				classNameField.requestFocus();
				return;

			}

			if(newPropertyValueFieldList.size() <= 0) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Alert").setMessage("Please create at least one field!")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_info)
						.show();
				newPropertyKeyField.requestFocus();
				return;

			}

			// Create dictionary of parameters to be passed with the request


			for(int i = 0; i < newPropertyValueFieldList.size(); i++) {
				EditText property = null;
				property = newPropertyValueFieldList.get(i);
				//fieldsToSend.put(property.getHint().toString(), property.getText().toString());
				try {
					fieldsToSend.put(property.getHint().toString(), property.getText().toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			data.put("classname", className);
			data.put("fields", fieldsToSend);

			createButton.setVisibility(View.GONE);

		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new CustomObjectsAPI(SdkClient.getInstance()).customObjectsCreate(data.get("classname").toString(),
						data.get("fields").toString(),null,null,null,null,null,null,null);


			} catch (SdkException e) {
				exceptionThrown = e;
				//handleSDKExcpetion(e, currentActivity);
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Success").setMessage("Created!")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_info)
						.show();
				createButton.setVisibility(View.VISIBLE);

			} else {
				Utils.handleSDKExcpetion(exceptionThrown, currentActivity);
			}
		}
	}
}

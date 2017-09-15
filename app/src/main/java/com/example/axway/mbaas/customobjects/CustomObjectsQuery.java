/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.customobjects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.CustomObjectsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomObjectsQuery extends Activity {
	private static CustomObjectsQuery currentActivity;
	
	private TextView statusTextView;
	private EditText classNameField;
	private Button queryButton;
	private ListView listView;
	
	private ListViewAdapter listViewAdapter;
	private String className;
	private JSONArray customObjects;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customobjects_query);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		classNameField = (EditText) findViewById(R.id.customobjects_query_class_name_field);
		statusTextView = (TextView) findViewById(R.id.customobjects_query_status_textView);	
		statusTextView.setVisibility(View.GONE);
		
		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					new queryCustomObject().execute();
				} else if (keyCode == KeyEvent.KEYCODE_BACK) {
					currentActivity.finish();
				}
				return false;
			}
			
		};
		
		classNameField.setOnKeyListener(keyListener);
	
		queryButton = (Button) findViewById(R.id.customobjects_query_query_button);
		queryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new queryCustomObject().execute();
			}
		});
		
		listView = (ListView) findViewById(R.id.customobjects_query_listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				
				try {
					if (customObjects.length() > 0) {
						String customObjectId = customObjects.getJSONObject(position).getString("id");
						Intent intent = new Intent(currentActivity, CustomObjectsUpdate.class);
						intent.putExtra("custom_object_id", customObjectId);
						intent.putExtra("classname", className);
						
						startActivity(intent);
					}
				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				} 
			}
		});
	}
	HashMap<String, Object> data = new HashMap<String, Object>();
	private class queryCustomObject extends AsyncTask<Void, Void, JSONObject> {

		private SdkException exceptionThrown = null;
		JSONObject successResponse;
		final ArrayList<String> objectsList = new ArrayList<String>();
		@Override
		protected void onPreExecute() {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(classNameField.getWindowToken(), 0);

			statusTextView.setVisibility(View.VISIBLE);
			queryButton.setVisibility(View.INVISIBLE);
			listView.setVisibility(View.GONE);

			className = classNameField.getText().toString();
			if(className.equals(null) || className.equals("")) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Alert").setMessage("Please enter a class name!")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_info)
						.show();
				queryButton.setVisibility(View.VISIBLE);
				statusTextView.setVisibility(View.GONE);
				return;
			}

			// Create dictionary of parameters to be passed with the request

			data.put("classname", className);
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new CustomObjectsAPI(SdkClient.getInstance()).customObjectsQuery(
						data.get("classname").toString(),null,null,null,null,null,null,null,null,null,null,null);

			} catch (SdkException e) {
				exceptionThrown = e;
				//handleSDKExcpetion(e, currentActivity);
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				try {
					customObjects = json.getJSONObject("response").getJSONArray(className);

					for (int i = 0; i < customObjects.length(); i++) {
						JSONObject customObject = customObjects.getJSONObject(i);
						String content = "id: ";
						try {
							content = content + customObject.get("id").toString();
						}catch (JSONException e2) {
							content = null;
						}
						objectsList.add(content);
					}
					statusTextView.setVisibility(View.GONE);

					if (objectsList.size() <= 0) {
						new AlertDialog.Builder(currentActivity)
								.setTitle("Alert").setMessage("No objects found!")
								.setPositiveButton(android.R.string.ok, null)
								.setIcon(android.R.drawable.ic_dialog_info)
								.show();
					}

					listViewAdapter = new ListViewAdapter(currentActivity, objectsList);
					listView.setAdapter(listViewAdapter);
					listView.setVisibility(View.VISIBLE);
					queryButton.setVisibility(View.VISIBLE);

				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}
				queryButton.setVisibility(View.VISIBLE);
			} else {
				Utils.handleSDKExcpetion(exceptionThrown, currentActivity);
			}
		}
	}
}

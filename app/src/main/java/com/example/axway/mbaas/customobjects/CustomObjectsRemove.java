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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.CustomObjectsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class CustomObjectsRemove extends Activity {
	private static CustomObjectsRemove currentActivity;
	
	private TextView statusTextView;
	private Button removeButton;
	
	private String className;
	private String customObjectId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customobjects_remove);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		statusTextView = (TextView) findViewById(R.id.customobjects_remove_status_textView);
		
		Intent intent = getIntent();
		customObjectId = intent.getStringExtra("custom_object_id");
		className = intent.getStringExtra("classname");
		
		removeButton = (Button) findViewById(R.id.customobjects_remove_remove_button);
		removeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new removeCustomObject().execute();
			}
		});
	}

	private class removeCustomObject extends AsyncTask<Void, Void, JSONObject> {

		private SdkException exceptionThrown = null;
		JSONObject successResponse;
		HashMap<String, Object> data = new HashMap<String, Object>();

		@Override
		protected void onPreExecute() {
			removeButton.setVisibility(View.GONE);
			statusTextView.setText("Removing, please wait...");

			// Create dictionary of parameters to be passed with the request
			data.put("classname", className);
			data.put("id", customObjectId);
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new CustomObjectsAPI(SdkClient.getInstance()).customObjectsDelete(data.get("classname").toString(),"",
						data.get("id").toString(),null,null);


			} catch (SdkException e) {
				exceptionThrown = e;
				//handleSDKException(e, currentActivity);
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Success").setMessage("Removed!")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_info)
						.show();


			} else {
				Utils.handleSDKException(exceptionThrown, currentActivity);
			}
		}
	}
}

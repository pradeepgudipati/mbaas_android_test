/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.photos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotosAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class PhotosRemove extends Activity {
	private static PhotosRemove currentActivity;
	private String photoId;
	HashMap<String, Object> data = new HashMap<String, Object>();

	private Button button1;
	private TextView statusLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_remove);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		button1 = (Button) findViewById(R.id.photos_remove_button1);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submitForm();
			}
		});
		
		statusLabel = (TextView) findViewById(R.id.photos_remove_status_label);

		Intent intent = getIntent();
		photoId = intent.getStringExtra("photoId");
	}
	
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
	
	private void submitForm() {		

		new apiTask().execute();
	}

	private class apiTask extends AsyncTask<Void, Void, JSONObject> {
		private SdkException exceptionThrown =null;

		JSONObject successResponse;

		@Override
		protected void onPreExecute() {
			button1.setVisibility(View.GONE);
			statusLabel.setText("Removing, please wait...");

			// Create dictionary of parameters to be passed with the request
			data.put("photo_id", photoId);
		}
		@Override
		protected JSONObject doInBackground(Void... voids) {
			try {
				successResponse = new PhotosAPI(SdkClient.getInstance()).photosDelete(data.get("photo_id").toString(),null,null);
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
					statusLabel.setText("Removed!");
				}
				else
					handleSDKException(exceptionThrown,currentActivity);
			} catch (JSONException e) {
				handleException(e,currentActivity);
			}
			button1.setVisibility(View.VISIBLE);


		}
	}
}

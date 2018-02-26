/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.geoFences;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.GeoFencesAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class GeoFencesRemove extends Activity {
	private static GeoFencesRemove currentActivity;
	private String geoFenceId;
	
	private Button removeButton1;
	private TextView statusLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geofences_remove);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		removeButton1 = (Button) findViewById(R.id.geofences_remove_button1);
		removeButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new removeGeoFence().execute();
			}
		});
		
		statusLabel = (TextView) findViewById(R.id.geofences_remove_status_label);

		Intent intent = getIntent();
		geoFenceId = intent.getStringExtra("geo_fence_id");
	}
	
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}

	HashMap<String, Object> data = new HashMap<String, Object>();
	private class removeGeoFence extends AsyncTask<Void, Void, JSONObject> {

		private SdkException exceptionThrown = null;
		JSONObject successResponse;

		@Override
		protected void onPreExecute() {
			removeButton1.setVisibility(View.GONE);
			statusLabel.setText("Removing, please wait...");

			// Create dictionary of parameters to be passed with the request

			data.put("id", geoFenceId);
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new GeoFencesAPI(SdkClient.getInstance()).geoFencesDelete(data.get("id").toString(),null);
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
				} else
					handleSDKException(exceptionThrown, currentActivity);
			} catch (JSONException e) {
				handleException(e, currentActivity);
			}
		}
	}
}

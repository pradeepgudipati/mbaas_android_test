/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.geoFences;

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

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.GeoFencesAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GeoFencesUpdate extends Activity {
	private static GeoFencesUpdate currentActivity;
	private ArrayList<EditText> fields = new ArrayList<EditText>();
	
	private String geoFenceId;
	
	private EditText geoFenceNameField;
	private EditText geoFenceLatitudeField;
	private EditText geoFenceLongitudeField;
	private EditText geoFenceRadiusField;
	private Button updateButton1;
	private Button removeButton2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geofences_update);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		geoFenceNameField = (EditText) findViewById(R.id.geofences_update_name_field);
		geoFenceLatitudeField = (EditText) findViewById(R.id.geofences_update_latitude_field);
		geoFenceLongitudeField = (EditText) findViewById(R.id.geofences_update_longitude_field);
		geoFenceRadiusField = (EditText) findViewById(R.id.geofences_update_radius_field);
		
		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					new updateGeoFences().execute();
				} else if (keyCode == KeyEvent.KEYCODE_BACK) {
					currentActivity.finish();
				}
				return false;
			}
		};
		
		geoFenceNameField.setOnKeyListener(keyListener);
		geoFenceLatitudeField.setOnKeyListener(keyListener);
		geoFenceLongitudeField.setOnKeyListener(keyListener);
		geoFenceRadiusField.setOnKeyListener(keyListener);
		
		updateButton1 = (Button) findViewById(R.id.geofences_update_update_button1);
		updateButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new updateGeoFences().execute();
			}
		});
		
		removeButton2 = (Button) findViewById(R.id.geofences_update_remove_button2);
		removeButton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity, GeoFencesRemove.class);
				intent.putExtra("geo_fence_id", geoFenceId);
				startActivity(intent);
			}
		});
		
		// Required fields
		fields.add(geoFenceNameField);
		fields.add(geoFenceLatitudeField);
		fields.add(geoFenceLongitudeField);
		fields.add(geoFenceRadiusField);
		
		Intent intent = getIntent();
		geoFenceId = intent.getStringExtra("geo_fence_id");
		geoFenceNameField.setText(intent.getStringExtra("name"));
		geoFenceLatitudeField.setText(intent.getStringExtra("latitude"));
		geoFenceLongitudeField.setText(intent.getStringExtra("longitude"));
		geoFenceRadiusField.setText(intent.getStringExtra("radius"));
	}
	
	
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}

	private class updateGeoFences extends AsyncTask<Void, Void, JSONObject> {

		private SdkException exceptionThrown = null;
		JSONObject successResponse;
		HashMap<String, Object> data = new HashMap<String, Object>();

		@Override
		protected void onPreExecute() {
			for (EditText field : fields) {
				if (field.getText().toString().length() <= 0) {
					field.requestFocus();
					return;
				}
			}

			try {
				JSONArray coordinatesArray = new JSONArray();
				// [longitude, latitude]
				coordinatesArray.put(Double.parseDouble(geoFenceLongitudeField.getText().toString()));
				coordinatesArray.put(Double.parseDouble(geoFenceLatitudeField.getText().toString()));

				JSONObject loc = new JSONObject();
				loc.put("coordinates", coordinatesArray);
				loc.put("radius", geoFenceRadiusField.getText().toString());

				JSONObject name = new JSONObject();
				name.put("name", geoFenceNameField.getText().toString());

				JSONObject geoFence = new JSONObject();
				geoFence.put("payload", name);
				geoFence.put("loc", loc);

				updateButton1.setVisibility(View.INVISIBLE);

				// Create dictionary of parameters to be passed with the request

				data.put("id", geoFenceId);
				data.put("geo_fence", geoFence);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}


		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new GeoFencesAPI(SdkClient.getInstance()).geoFencesUpdate(data.get("id").toString(),data.get("geo_fence").toString(),null);
			} catch (SdkException e) {
				exceptionThrown = e;
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Success!").setMessage("Updated!")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_info)
						.show();
				updateButton1.setVisibility(View.VISIBLE);
			} else {
				Utils.handleSDKExcpetion(exceptionThrown, currentActivity);
			}
		}
	}
}

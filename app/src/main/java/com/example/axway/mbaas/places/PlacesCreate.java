/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.places;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.axway.mbaas_preprod.apis.PlacesAPI;
import com.example.axway.mbaas.AxwayApplication;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class PlacesCreate extends Activity {
	private static PlacesCreate currentActivity;
	private ArrayList<EditText> fields = new ArrayList<EditText>();

	private EditText nameField;
	private EditText addressField;
	private EditText cityField;
	private EditText stateField;
	private EditText postalCodeField;
	private EditText latitudeField;
	private EditText longitudeField;
	private Button createButton1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_create);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		nameField = (EditText) findViewById(R.id.places_create_name_field);
		addressField = (EditText) findViewById(R.id.places_create_address_field);
		cityField = (EditText) findViewById(R.id.places_create_city_field);
		stateField = (EditText) findViewById(R.id.places_create_state_field);
		postalCodeField = (EditText) findViewById(R.id.places_create_postal_code_field);
		latitudeField = (EditText) findViewById(R.id.places_create_latitude_field);
		longitudeField = (EditText) findViewById(R.id.places_create_longitude_field);

		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					new createPlaces().execute();
				} else if (keyCode == KeyEvent.KEYCODE_BACK) {
					currentActivity.finish();
				}
				return false;
			}
		};

		nameField.setOnKeyListener(keyListener);
		addressField.setOnKeyListener(keyListener);
		cityField.setOnKeyListener(keyListener);
		stateField.setOnKeyListener(keyListener);
		postalCodeField.setOnKeyListener(keyListener);
		latitudeField.setOnKeyListener(keyListener);
		longitudeField.setOnKeyListener(keyListener);

		createButton1 = (Button) findViewById(R.id.places_create_button1);
		createButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new createPlaces().execute();
			}
		});

		// Required fields
		fields.add(nameField);
		fields.add(latitudeField);
		fields.add(longitudeField);
	}

	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}

	private class createPlaces extends AsyncTask<Void, Void, JSONObject> {
		HashMap<String, Object> data = new HashMap<String, Object>();
		Double latitudeValue = Double.parseDouble(latitudeField.getText().toString());
		Double longitudeValue = Double.parseDouble(longitudeField.getText().toString());

		private SdkException exceptionThrown = null;
		JSONObject successResponse;

		@Override
		protected void onPreExecute() {
			for (EditText field : fields) {
				if (field.getText().toString().length() <= 0) {
					field.requestFocus();
					return;
				}
			}

			createButton1.setVisibility(View.GONE);



			// Create dictionary of parameters to be passed with the request
			data.put("name", nameField.getText().toString());
			data.put("address", addressField.getText().toString());
			data.put("city", cityField.getText().toString());
			data.put("state", stateField.getText().toString());
			data.put("postal_code", postalCodeField.getText().toString());
			data.put("latitude", latitudeValue);
			data.put("longitude", longitudeValue);

		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new PlacesAPI(SdkClient.getInstance()).placesCreate(data.get("name").toString(),
						data.get("address").toString(),
						data.get("city").toString(),
						data.get("state").toString(),
						data.get("postal_code").toString(),
						null,
						latitudeValue,
						(Double) data.get("longitude"),
						null, null, null, null, null, null, null, null, null, null, null
				);
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
					nameField.setText("");
					addressField.setText("");
					cityField.setText("");
					stateField.setText("");
					postalCodeField.setText("");
					latitudeField.setText("");
					longitudeField.setText("");

					createButton1.setVisibility(View.VISIBLE);

				} else {
					Utils.handleSDKException(exceptionThrown, currentActivity);
				}
			} catch (JSONException e) {
				handleException(e, currentActivity);
			}

		}
	}
}




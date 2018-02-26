/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.places;

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
import com.axway.mbaas_preprod.apis.PlacesAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;

public class PlacesUpdate extends Activity {
	private static PlacesUpdate currentActivity;
	private String placeId;
	private ArrayList<EditText> fields = new ArrayList<EditText>();

	private EditText nameField;
	private EditText addressField;
	private EditText cityField;
	private EditText stateField;
	private EditText postalCodeField;
	private EditText latitudeField;
	private EditText longitudeField;
	private Button updateButton1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_update);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		nameField = (EditText) findViewById(R.id.places_update_name_field);
		addressField = (EditText) findViewById(R.id.places_update_address_field);
		cityField = (EditText) findViewById(R.id.places_update_city_field);
		stateField = (EditText) findViewById(R.id.places_update_state_field);
		postalCodeField = (EditText) findViewById(R.id.places_update_postal_code_field);
		latitudeField = (EditText) findViewById(R.id.places_update_latitude_field);
		longitudeField = (EditText) findViewById(R.id.places_update_longitude_field);

		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					new updatePlaces().execute();
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


		updateButton1 = (Button) findViewById(R.id.places_update_button1);
		updateButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new updatePlaces().execute();
			}
		});

		// Required fields
		fields.add(nameField);
		fields.add(latitudeField);
		fields.add(longitudeField);

		Intent intent = getIntent();
		placeId = intent.getStringExtra("place_id");

        new showPlaces().execute();
	}

    private class showPlaces extends AsyncTask<Void, Void, JSONObject> {
        HashMap<String, Object> data = new HashMap<String, Object>();

        private SdkException exceptionThrown = null;
        JSONObject successResponse;

        @Override
        protected JSONObject doInBackground(Void... voids) {

            try {
                successResponse = new PlacesAPI(SdkClient.getInstance()).placesShow(placeId, null, null, null);
            } catch (SdkException e) {
                exceptionThrown = e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (exceptionThrown == null) {
                try {
                    if (exceptionThrown == null && successResponse.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
                        try {
							JSONObject place = json.getJSONObject("response").getJSONArray("places").getJSONObject(0);
							if (place.has("name")) {
								String name = place.getString("name");
								nameField.setText(name);
							}
							if (place.has("address")) {
								String address = place.getString("address");
								addressField.setText(address);
							}
							if (place.has("city")) {
								String city = place.getString("city");
								cityField.setText(city);
							}
							if (place.has("state")) {
								String state = place.getString("state");
								stateField.setText(state);
							}
							if (place.has("postal_code")) {
								String postal_code = place.getString("postal_code");
								postalCodeField.setText(postal_code);
							}
							if (place.has("latitude")) {
								String postal_code = place.getString("latitude");
								latitudeField.setText(postal_code);
							}
							if (place.has("longitude")) {
								String postal_code = place.getString("longitude");
								longitudeField.setText(postal_code);
							}

						} catch (JSONException e1) {
							Utils.handleException(e1, currentActivity);
						}

                    } else {
                        Utils.handleSDKException(exceptionThrown, currentActivity);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}


    private class updatePlaces extends AsyncTask<Void, Void, JSONObject> {
        HashMap<String, Object> data = new HashMap<String, Object>();

        private SdkException exceptionThrown = null;
        JSONObject successResponse;
        Double latitudeValue = Double.parseDouble(latitudeField.getText().toString());
        Double longitudeValue = Double.parseDouble(longitudeField.getText().toString());

        @Override
        protected void onPreExecute() {
            for (EditText field : fields) {
                if (field.getText().toString().length() <= 0) {
                    field.requestFocus();
                    return;
                }
            }

            updateButton1.setVisibility(View.GONE);

            // Create dictionary of parameters to be passed with the request

            data.put("place_id", placeId);
            data.put("name", nameField.getText().toString());
            data.put("address", addressField.getText().toString());
            data.put("city", cityField.getText().toString());
            data.put("state", stateField.getText().toString());
            data.put("postal_code", postalCodeField.getText().toString());
            data.put("latitude", latitudeField.getText().toString());
            data.put("longitude", longitudeField.getText().toString());
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            try {
                successResponse = new PlacesAPI(SdkClient.getInstance()).placesUpdate(data.get("place_id").toString(),
                        data.get("name").toString(),
                        data.get("address").toString(),
                        data.get("city").toString(),
                        data.get("state").toString(),
                        data.get("postal_code").toString(),
                        null,
                        latitudeValue,
                        longitudeValue,
                        null,null,null,null,null,null,null,null,null,null,null,null);
            } catch (SdkException e) {
                exceptionThrown = e;
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

					updateButton1.setVisibility(View.VISIBLE);

				} else {
					Utils.handleSDKException(exceptionThrown, currentActivity);
				}
			} catch (JSONException e) {
				handleException(e, currentActivity);
			}


        }
    }

}



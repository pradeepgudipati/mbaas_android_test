/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.places;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PlacesAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlacesQuery extends Activity {
	private static PlacesQuery currentActivity;
	private JSONArray places;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_query);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		listView = (ListView) findViewById(R.id.places_query_list_view);
		
		final ArrayList<String> loadingList = new ArrayList<String>();
		loadingList.add("Loading, please wait...");
		new queryPlaces().execute();
		final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, loadingList);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				try {
					if (places.length() > 0) {
						String placeId = places.getJSONObject(position).getString("id");
						Intent intent = new Intent(currentActivity, PlacesShow.class);
						intent.putExtra("place_id", placeId);
						startActivity(intent);
					}
				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}
			}

		});
	}

	private class queryPlaces extends AsyncTask<Void, Void, JSONObject> {
		private SdkException exceptionThrown = null;
		JSONObject successResponse;


		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new PlacesAPI(SdkClient.getInstance()).placesQuery(null,null,null,null,
						null,null,null,null,null,null,null);
			} catch (SdkException e) {
				exceptionThrown = e;
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				try {
					places = json.getJSONObject("response").getJSONArray("places");
					final ArrayList<String> objectsList = new ArrayList<String>();
					for (int i = 0; i < places.length(); i++) {
						JSONObject user = places.getJSONObject(i);
						objectsList.add((String) user.get("name"));
					}
					if (objectsList.size() <= 0) {
						objectsList.add("No Results!");
					}
					// Load listView rows
					final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList);
					listView.setAdapter(adapter);
				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}

			} else {
				Utils.handleSDKException(exceptionThrown, currentActivity);
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
}



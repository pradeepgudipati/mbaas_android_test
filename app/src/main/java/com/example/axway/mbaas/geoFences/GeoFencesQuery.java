/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.geoFences;

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
import com.axway.mbaas_preprod.apis.GeoFencesAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GeoFencesQuery extends Activity {
	private static GeoFencesQuery currentActivity;
	private JSONArray geoFences;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geofences_query);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		 listView = (ListView) findViewById(R.id.geofences_query_list_view);

		final ArrayList<String> loadingList = new ArrayList<String>();
		loadingList.add("Loading, please wait...");
		final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, loadingList);
		listView.setAdapter(adapter);
		new queryGeoFence().execute();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

				try {
					if (geoFences.length() > 0) {
						JSONObject geoFence = geoFences.getJSONObject(position);
						JSONObject loc = geoFence.getJSONObject("loc");
						JSONObject payload = geoFence.getJSONObject("payload");
						Intent intent = new Intent(currentActivity, GeoFencesUpdate.class);
						intent.putExtra("geo_fence_id", geoFence.getString("id"));
						intent.putExtra("name", payload.has("name") ? payload.getString("name") : "");
						// [longitude, latitude]
						intent.putExtra("longitude", loc.getJSONArray("coordinates").getString(0));
						intent.putExtra("latitude", loc.getJSONArray("coordinates").getString(1));
						intent.putExtra("radius", loc.getString("radius"));
						startActivity(intent);
					}
				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}
			}

		});

	}
		
		HashMap<String, Object> data = new HashMap<String, Object>();

		private class queryGeoFence extends AsyncTask<Void, Void, JSONObject> {

			private SdkException exceptionThrown = null;
			JSONObject successResponse;

			@Override
			protected JSONObject doInBackground(Void... voids) {

				try {
					successResponse = new GeoFencesAPI(SdkClient.getInstance()).geoFencesQuery(null,null,50,null,null,null,null);
				} catch (SdkException e) {
					exceptionThrown = e;
				}
				return successResponse;
			}

			@Override
			protected void onPostExecute(JSONObject json) {
				final ArrayList<String> objectsList = new ArrayList<String>();
				if (exceptionThrown == null) {
					try {
						geoFences = json.getJSONObject("response").getJSONArray("geo_fences");
						for (int i = 0; i < geoFences.length(); i++) {
							JSONObject geoFence = geoFences.getJSONObject(i);
							JSONObject payload = geoFence.getJSONObject("payload");
							String name = payload.has("name") ? payload.getString("name") : "(null)";
							objectsList.add(name + " " + geoFence.getJSONObject("loc").getString("coordinates"));
						}
						if (objectsList.size() <= 0) {
							objectsList.add("No Results!");
						}

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

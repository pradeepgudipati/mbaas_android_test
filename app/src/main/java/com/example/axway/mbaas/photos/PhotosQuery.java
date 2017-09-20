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
import android.widget.AdapterView;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotosAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class PhotosQuery extends Activity {
	private static PhotosQuery currentActivity;
	 ListView listView;
	private JSONArray photos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_query);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		listView = (ListView) findViewById(R.id.photos_query_list_view);
		
		final ArrayList<String> loadingList = new ArrayList<String>();
		loadingList.add("Loading...");
		final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, loadingList);
		listView.setAdapter(adapter);	
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				try {
					if (photos != null && photos.length() > 0) {
						String photoId = photos.getJSONObject(position).getString("id");
						Intent intent = new Intent(currentActivity, PhotosShow.class);
						intent.putExtra("photoId", photoId);
						startActivity(intent);
					}
				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}
			}

		});
		new apiTask().execute();
	}
	private class apiTask extends AsyncTask<Void, Void, JSONObject> {
		private SdkException exceptionThrown =null;

		JSONObject successResponse;

		@Override
		protected void onPreExecute() {

		}
		@Override
		protected JSONObject doInBackground(Void... voids) {
			try {
				successResponse = new PhotosAPI(SdkClient.getInstance()).photosQuery(null,null,null,null,null,null,null,null,null,null,null);
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
					photos = xml.getJSONObject("response").getJSONArray("photos");
					final ArrayList<String> objectsList = new ArrayList<String>();
					for (int i = 0; i < photos.length(); i++) {
						JSONObject photo = photos.getJSONObject(i);
						objectsList.add(photo.getString("filename"));
					}
					if (objectsList.size() <= 0) {
						objectsList.add("No Results!");
					}
					// Load listView rows
					final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList);
					listView.setAdapter(adapter);				}
				else
					handleSDKException(exceptionThrown,currentActivity);
			} catch (JSONException e) {
				handleException(e,currentActivity);
			}

		}
	}
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
}

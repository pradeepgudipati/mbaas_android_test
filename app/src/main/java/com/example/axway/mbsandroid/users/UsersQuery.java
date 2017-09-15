/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbsandroid.users;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbsandroid.StableArrayAdapter;
import com.example.axway.mbsandroid.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersQuery extends Activity {
	private static UsersQuery currentActivity;
	private JSONArray users;
	JSONObject successResponse;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_query);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		final ListView listView = (ListView) findViewById(R.id.users_query_list_view);
		
		final ArrayList<String> loadingList = new ArrayList<String>();
		loadingList.add("Loading...");
		StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, loadingList);
		listView.setAdapter(adapter);	
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				try {
					if (users != null && users.length() > 0) {
						String userId = users.getJSONObject(position).getString("id");
						Intent intent = new Intent(currentActivity, UsersShow.class);
						intent.putExtra("userId", userId);
						startActivity(intent);
					}
				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}
			}

		});



		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					successResponse = new UsersAPI(SdkClient.getInstance()).usersQuery(null,null,null,null,null,null ,null,null, null, null, null);
				} catch (SdkException e) {
					e.printStackTrace();
				}
			}
		}).start();
		try {
			users = successResponse.getJSONArray("users");

		final ArrayList<String> objectsList = new ArrayList<String>();
		for (int i = 0; i < users.length(); i++) {
			JSONObject user = users.getJSONObject(i);
			objectsList.add(user.get("first_name") + " " + user.get("last_name"));
		}
		if (objectsList.size() <= 0) {
			objectsList.add("No Results!");
		}
		// Load listView rows
			adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList);
		listView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
/*
		try {
			APSUsers.query(null, new APSResponseHandler() {
				
				@Override
				public void onResponse(final APSResponse e) {
					if (e.getSuccess()) {
						try {
							users = e.getResponse().getJSONArray("users");
							final ArrayList<String> objectsList = new ArrayList<String>();
							for (int i = 0; i < users.length(); i++) {
								JSONObject user = users.getJSONObject(i);
								objectsList.add(user.get("first_name") + " " + user.get("last_name"));
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
						Utils.handleErrorInResponse(e, currentActivity);
					}
				}

				@Override
				public void onException(APSCloudException e) {
					Utils.handleException(e, currentActivity);
				}

			});
		} catch (APSCloudException e1) {
			Utils.handleException(e1, currentActivity);
		}	*/
	}
	
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
}

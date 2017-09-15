/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MethodsViewActivity extends Activity {
	private static String TAG = "MethodsViewActivity";
	private static MethodsViewActivity currentActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_methods_view);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		Intent intent = getIntent();
		final ArrayList<String> rows = intent.getStringArrayListExtra("rows");
		Log.i(TAG, "rows: " + rows.toString());
		
		@SuppressWarnings("unchecked") // Suppressing unchecked cast from Serializable warning
		final Map<String, String> methodsDict = (HashMap<String, String>)intent.getSerializableExtra("methodsDict");
		Log.i(TAG, "methodsDict: " + methodsDict.toString());
		
		final String packageName = intent.getStringExtra("packageName");
		Log.i(TAG, "packageName: " + packageName);
		
		final ListView listView = (ListView) findViewById(R.id.listView1);
		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, rows);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				String rowName = rows.get(position);
				String className = methodsDict.get(rowName);
				Intent intent;
				try {
					intent = new Intent(currentActivity, Class.forName(MethodsViewActivity.class.getPackage().getName() + packageName + "." + className));
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					Utils.handleException(e, currentActivity);
				}
			}

		});
	}
}

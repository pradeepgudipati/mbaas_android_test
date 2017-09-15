/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.checkins;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.CheckinsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CheckinsShow extends Activity {
	private static CheckinsShow currentActivity;
	private String checkinId;

	private TextView textView;
	private Button removeButton1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkins_show);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		textView = (TextView) findViewById(R.id.checkins_show_text_view);
		textView.setMovementMethod(new ScrollingMovementMethod());

		Intent intent = getIntent();
		checkinId = intent.getStringExtra("checkin_id");

		removeButton1 = (Button) findViewById(R.id.checkins_show_remove_button1);
		removeButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity, CheckinsRemove.class);
				intent.putExtra("checkin_id", checkinId);
				startActivity(intent);
			}
		});

		new showCheckins().execute();

	}

	private class showCheckins extends AsyncTask<Void, Void, JSONObject> {
		HashMap<String, Object> data = new HashMap<String, Object>();

		private SdkException exceptionThrown = null;
		JSONObject successResponse;

		protected void onPreExecute() {

			// Create dictionary of parameters to be passed with the request
			data.put("checkin_id", checkinId);

		}
		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new CheckinsAPI(SdkClient.getInstance()).checkinsShow(data.get("checkin_id").toString(), null, null, null);
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
						textView.setText(successResponse.toString(4));

					} else {
						Utils.handleSDKExcpetion(exceptionThrown, currentActivity);
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
}



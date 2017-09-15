/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.checkins;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.CheckinsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class CheckinsRemove extends Activity {
	private static CheckinsRemove currentActivity;
	private String checkinId;

	private Button removeButton1;
	private TextView statusLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkins_remove);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		removeButton1 = (Button) findViewById(R.id.checkins_remove_button1);
		removeButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new removeCheckins().execute();
			}
		});

		statusLabel = (TextView) findViewById(R.id.checkins_remove_status_label);

		Intent intent = getIntent();
		checkinId = intent.getStringExtra("checkin_id");
	}

	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}

	private class removeCheckins extends AsyncTask<Void, Void, JSONObject> {
		HashMap<String, Object> data = new HashMap<String, Object>();

		private SdkException exceptionThrown = null;
		JSONObject successResponse;

		@Override
		protected void onPreExecute() {
			removeButton1.setVisibility(View.GONE);
			statusLabel.setText("Removing, please wait...");

			// Create dictionary of parameters to be passed with the request
			data.put("checkin_id", checkinId);

		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new CheckinsAPI(SdkClient.getInstance()).checkinsDelete(data.get("checkin_id").toString(),null,null);
			} catch (SdkException e) {
				exceptionThrown = e;
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Success!").setMessage("Removed! ")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_info)
						.show();

				removeButton1.setVisibility(View.VISIBLE);

			} else {
				Utils.handleSDKExcpetion(exceptionThrown, currentActivity);
			}
		}
	}

}



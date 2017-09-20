/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.accessControlLists;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.ACLsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleSDKException;

public class AccessControlListsUpdateUsers extends Activity {
	private static final int UPDATE_READER = 3;
	private static final int UPDATE_WRITER = 4;
	private static AccessControlListsUpdateUsers currentActivity;

	private ArrayList<String> selectedReadersIdList = new ArrayList<String>();
	private ArrayList<String> selectedWritersIdList = new ArrayList<String>();

	private EditText accessControlListsNameField;
	private Button selectReadersButton1;
	private Button selectWritersButton2;
	private Button addUsersButton3;
	private Button removeUsersButton4;

    HashMap<String, Object> data = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.access_control_lists_update_users);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		accessControlListsNameField = (EditText) findViewById(R.id.access_control_lists_update_users_name_field);

		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					currentActivity.finish();
				}
				return false;
			}

		};

		accessControlListsNameField.setOnKeyListener(keyListener);

		selectReadersButton1 = (Button) findViewById(R.id.access_control_lists_update_users_select_readers_button1);
		selectReadersButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity , AccessControlListsSelectUsers.class);
				intent.putExtra("selected_reader_ids", selectedReadersIdList);
				intent.putExtra("selected_writer_ids", selectedWritersIdList);
				intent.putExtra("access_type", UPDATE_READER);
				startActivityForResult(intent, UPDATE_READER);
			}
		});

		selectWritersButton2 = (Button) findViewById(R.id.access_control_lists_update_users_select_writers_button2);
		selectWritersButton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity , AccessControlListsSelectUsers.class);
				intent.putExtra("selected_writer_ids", selectedWritersIdList);
				intent.putExtra("selected_reader_ids", selectedReadersIdList);
				intent.putExtra("access_type", UPDATE_WRITER);
				startActivityForResult(intent, UPDATE_WRITER);
			}
		});

		addUsersButton3 = (Button) findViewById(R.id.access_control_lists_update_users_add_users_button3);
		addUsersButton3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//addUsers();
                new addUsers().execute();
			}
		});

		removeUsersButton4 = (Button) findViewById(R.id.access_control_lists_update_users_remove_users_button4);
		removeUsersButton4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//removeUsers();
                new removeUsers().execute();
			}
		});
	}

	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && (requestCode == UPDATE_READER || requestCode == UPDATE_WRITER)) {
            selectedReadersIdList = data.getStringArrayListExtra("selected_reader_ids");
            selectedWritersIdList = data.getStringArrayListExtra("selected_writer_ids");
        }
    }

    private class addUsers extends AsyncTask<Void, Void, JSONObject> {

		private SdkException exceptionThrown = null;
		JSONObject successResponse;

		@Override
		protected void onPreExecute() {
			if (accessControlListsNameField.getText().toString().length() <= 0) {
				accessControlListsNameField.requestFocus();
				return;
			}

			addUsersButton3.setVisibility(View.INVISIBLE);

			// Create dictionary of parameters to be passed with the request

			data.put("name", accessControlListsNameField.getText().toString());
			data.put("reader_ids", TextUtils.join(",", selectedReadersIdList));
			data.put("writer_ids", TextUtils.join(",", selectedWritersIdList));
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new ACLsAPI(SdkClient.getInstance()).aCLsAdd(data.get("name").toString(),
						null, null,
						data.get("reader_ids").toString(),
						data.get("writer_ids").toString());

			} catch (SdkException e) {
				exceptionThrown = e;
				//handleSDKException(e, currentActivity);
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Success!").setMessage("Added!")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_info)
						.show();

				addUsersButton3.setVisibility(View.VISIBLE);
			} else {
				Utils.handleSDKException(exceptionThrown, currentActivity);
			}
		}
	}

    private class removeUsers extends AsyncTask<Void, Void, JSONObject> {

        private SdkException exceptionThrown = null;
        JSONObject successResponse;
        @Override
        protected void onPreExecute(){
            if (accessControlListsNameField.getText().toString().length() <= 0) {
                accessControlListsNameField.requestFocus();
                return;
            }

            removeUsersButton4.setVisibility(View.INVISIBLE);

            // Create dictionary of parameters to be passed with the request
            //HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("name", accessControlListsNameField.getText().toString());
            data.put("reader_ids", TextUtils.join(",", selectedReadersIdList));
            data.put("writer_ids", TextUtils.join(",", selectedWritersIdList));
        }
        @Override
        protected JSONObject doInBackground(Void... voids){

            try {
                successResponse = new ACLsAPI(SdkClient.getInstance()).aCLsRemove(data.get("name").toString(),
                        null,null,
                        data.get("reader_ids").toString(),
                        data.get("writer_ids").toString());

            }catch(SdkException e){
                exceptionThrown = e;

            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json){
            if (exceptionThrown ==null){
			new AlertDialog.Builder(currentActivity)
                    .setTitle("Success!").setMessage("Removed!")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();

					removeUsersButton4.setVisibility(View.VISIBLE);

            }else
				handleSDKException(exceptionThrown, currentActivity);
   		 }
    }
}


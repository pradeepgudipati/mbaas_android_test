/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.accessControlLists;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class AccessControlListsCreate extends Activity {
	private static final int READER = 1;
	private static final int WRITER = 2;
	private static AccessControlListsCreate currentActivity;
	
	private boolean readerPublicAccess = false;
	private boolean writerPublicAccess = false;
	
	private ArrayList<String> selectedReadersIdList = new ArrayList<String>();
	private ArrayList<String> selectedWritersIdList = new ArrayList<String>();
	
	private EditText accessControlListsNameField;
	private Button selectReadersButton1;
	private Button selectWritersButton2;
	private Button createButton3;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.access_control_lists_create);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		accessControlListsNameField = (EditText) findViewById(R.id.access_control_lists_create_name_field);
		
		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					//createACL();
					new aclCreateTask().execute();
				} else if (keyCode == KeyEvent.KEYCODE_BACK) {
					currentActivity.finish();
				}
				return false;
			}
			
		};
		
		accessControlListsNameField.setOnKeyListener(keyListener);
		
		selectReadersButton1 = (Button) findViewById(R.id.access_control_lists_create_select_readers_button1);
		selectReadersButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity , AccessControlListsSelectUsers.class);
				intent.putExtra("selected_reader_ids", selectedReadersIdList);
				intent.putExtra("selected_writer_ids", selectedWritersIdList);
				intent.putExtra("reader_public_access", readerPublicAccess);
				intent.putExtra("writer_public_access", writerPublicAccess);
				intent.putExtra("access_type", READER);
				startActivityForResult(intent, READER);
			}
		});
		
		selectWritersButton2 = (Button) findViewById(R.id.access_control_lists_create_select_writers_button2);
		selectWritersButton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity , AccessControlListsSelectUsers.class);
				intent.putExtra("selected_writer_ids", selectedWritersIdList);
				intent.putExtra("selected_reader_ids", selectedReadersIdList);
				intent.putExtra("writer_public_access", writerPublicAccess);
				intent.putExtra("reader_public_access", readerPublicAccess);
				intent.putExtra("access_type", WRITER);
				startActivityForResult(intent, WRITER);
			}
		});
		
		createButton3 = (Button) findViewById(R.id.access_control_lists_create_create_button3);
		createButton3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(accessControlListsNameField.getText().toString())) {
					new aclCreateTask().execute();
				}
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
		if (resultCode == RESULT_OK && (requestCode == READER || requestCode == WRITER)) {
			readerPublicAccess = data.getBooleanExtra("reader_public_access", false);
			selectedReadersIdList = data.getStringArrayListExtra("selected_reader_ids");
			writerPublicAccess = data.getBooleanExtra("writer_public_access", false);
			selectedWritersIdList = data.getStringArrayListExtra("selected_writer_ids");
		}
	}

	private class aclCreateTask extends AsyncTask<Void,Void, JSONObject>{

		private SdkException exceptionThrown =null;
		JSONObject successResponse;
		HashMap<String, Object> data = new HashMap<String, Object>();



		@Override
		protected void onPreExecute(){
			if (accessControlListsNameField.getText().toString().length() <= 0) {
				accessControlListsNameField.requestFocus();
				return;
			}
			createButton3.setVisibility(View.GONE);

			// Create dictionary of parameters to be passed with the request

			data.put("name", accessControlListsNameField.getText().toString());
			data.put("reader_ids", TextUtils.join(",", selectedReadersIdList));
			data.put("writer_ids", TextUtils.join(",", selectedWritersIdList));
			data.put("public_read", readerPublicAccess);
			data.put("public_write", writerPublicAccess);
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {
			try {
				successResponse = new ACLsAPI(SdkClient.getInstance()).aCLsCreate(data.get("name").toString(),
						data.get("reader_ids").toString(),
						data.get("writer_ids").toString(),
						null,
						data.get("public_read").toString(),
						data.get("public_write").toString(),
						null
						);
			}catch(SdkException e){
				exceptionThrown = e;
				e.printStackTrace();
			}

			return successResponse;
		}
		@Override
		protected void onPostExecute(JSONObject json){
		//	Log.d("Response:", json.toString());
			try {
				if (exceptionThrown == null && json.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")){
					new AlertDialog.Builder(currentActivity)
                            .setTitle("Success!").setMessage(json.getJSONObject("meta").toString())
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                    accessControlListsNameField.setText("");
                    selectedReadersIdList.clear();
                    selectedWritersIdList.clear();
                    readerPublicAccess = false;
                    writerPublicAccess = false;
                }else
				{
					handleSDKException(exceptionThrown, currentActivity);
				}

			} catch (JSONException e) {
				handleException(e, currentActivity);
			}

		}

	}
}

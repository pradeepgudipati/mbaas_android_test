/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.accessControlLists;

import static com.example.axway.mbaas.Utils.handleSDKException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.ACLsAPI;
import com.example.axway.mbaas.AxwayApplication;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;
import com.example.axway.mbaas.users.UsersLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AccessControlListsShow extends Activity {
	private static final int READER = 1;
	private static final int WRITER = 2;
	private static AccessControlListsShow currentActivity;

	private boolean readerPublicAccess = false;
	private boolean writerPublicAccess = false;

	private JSONObject acl;
	private ArrayList<String> selectedReadersIdList = new ArrayList<String>();
	private ArrayList<String> selectedWritersIdList = new ArrayList<String>();

	private EditText accessControlListsNameField;
	private Button selectReadersButton1;
	private Button selectWritersButton2;
	private Button showButton3;
	private Button updateButton4;
	private Button removeButton5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.access_control_lists_show);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		accessControlListsNameField = (EditText) findViewById(R.id.access_control_lists_show_name_field);

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

		selectReadersButton1 = (Button) findViewById(R.id.access_control_lists_show_select_readers_button1);
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

		selectWritersButton2 = (Button) findViewById(R.id.access_control_lists_show_select_writers_button2);
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

		showButton3 = (Button) findViewById(R.id.access_control_lists_show_show_button3);
		showButton3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//showACL();
                new showACLTask().execute();
			}
		});

		updateButton4 = (Button) findViewById(R.id.access_control_lists_show_update_button4);
		updateButton4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//updateACL();
                new updateACLTask().execute();
			}
		});

		removeButton5 = (Button) findViewById(R.id.access_control_lists_show_remove_button5);
		removeButton5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//removeACL();
                new removeACLTask().execute();
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

    private class showACLTask extends AsyncTask<Void, Void, JSONObject>{

        private SdkException exceptionThrown = null;
        JSONObject successResponse;
        HashMap<String, Object> data = new HashMap<String, Object>();
        @Override
        protected void onPreExecute(){
            if (accessControlListsNameField == null || accessControlListsNameField.getText().toString().length() <= 0) {
                accessControlListsNameField.requestFocus();
                this.cancel(true);
                return;
            }

            showButton3.setVisibility(View.INVISIBLE);

            // Create dictionary of parameters to be passed with the request

            data.put("name", accessControlListsNameField.getText().toString());
        }
        @Override
        protected JSONObject doInBackground(Void... voids){

            try {
                successResponse = new ACLsAPI(SdkClient.getInstance()).aCLsShow(null,data.get("name").toString(),null);
            }catch(SdkException e){
                exceptionThrown = e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json){
            try {
                if (exceptionThrown == null && json.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")){


                acl = json.getJSONObject("response").getJSONArray("acls").getJSONObject(0);

                if(acl.getBoolean("public_read")) {
                    readerPublicAccess = acl.getBoolean("public_read");
                } else {
                    readerPublicAccess = false;
                }

                if (acl.has("readers")) {
                    JSONArray readers = acl.getJSONArray("readers");
                    for (int i = 0; i < readers.length(); i++) {
                        selectedReadersIdList.add(readers.getString(i));
                    }
                } else {
                    selectedReadersIdList.clear();
                }

                if(acl.getBoolean("public_write")) {
                    writerPublicAccess = acl.getBoolean("public_write");
                } else {
                    writerPublicAccess = false;
                }

                if (acl.has("writers")) {
                    JSONArray writers = acl.getJSONArray("writers");
                    for (int i = 0; i < writers.length(); i++) {
                        selectedWritersIdList.add(writers.getString(i));
                    }
                } else {
                    selectedWritersIdList.clear();
                }
                    new AlertDialog.Builder(currentActivity)

                            .setTitle("Success!").setMessage(json.getJSONObject("meta").toString())
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                    showButton3.setVisibility(View.VISIBLE);

            } else
                handleSDKException(exceptionThrown,currentActivity);
            }catch (JSONException e1) {
                Utils.handleException(e1, currentActivity);
            }

        }
    }




    private class updateACLTask extends AsyncTask<Void, Void, JSONObject> {

	    private SdkException exceptionThrown = null;
        JSONObject successResponse;
        HashMap<String, Object> data = new HashMap<String, Object>();

        @Override
        protected void onPreExecute() {
            if (accessControlListsNameField.getText()!=null && accessControlListsNameField.getText().toString().length() <= 0) {
                accessControlListsNameField.requestFocus();
                return;
            }

            updateButton4.setVisibility(View.INVISIBLE);

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
                successResponse = new ACLsAPI(SdkClient.getInstance()).aCLsUpdate(null, data.get("name").toString(), null,
                        data.get("reader_ids").toString(),
                        data.get("writer_ids").toString(),
                        data.get("public_read").toString(),
                        data.get("public_write").toString(), null);
            } catch (Exception e) {
                e.printStackTrace();

            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (exceptionThrown == null) {
                try {
                    //Get globally store value
                    String strduserId = ((AxwayApplication)getApplication()).getUserId();
                    if(json != null) {
                        new AlertDialog.Builder(currentActivity)
                                .setTitle("Success!").setMessage(json.getJSONObject("meta").toString())
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }
                    else
                    {

                        String messageStr = (strduserId != null && strduserId.length()  == 0) ?  "Please Login to get data" : "No Data Found";

                        new AlertDialog.Builder(currentActivity)
                                .setTitle("Success!").setMessage(messageStr)
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                updateButton4.setVisibility(View.VISIBLE);
            } else
                handleSDKException(exceptionThrown, currentActivity);
        }
    }

    private class removeACLTask extends AsyncTask<Void, Void, JSONObject> {

        private SdkException exceptionThrown = null;
        JSONObject successResponse;
        HashMap<String, Object> data = new HashMap<String, Object>();

        @Override
        protected void onPreExecute() {
            if (accessControlListsNameField.getText().toString().length() <= 0) {
                accessControlListsNameField.requestFocus();
                return;
            }

            removeButton5.setVisibility(View.INVISIBLE);

            // Create dictionary of parameters to be passed with the request
            data.put("name", accessControlListsNameField.getText().toString());
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            try {
                successResponse = new ACLsAPI(SdkClient.getInstance()).aCLsDelete(null, data.get("name").toString(), null, null);
            } catch (Exception e) {
                e.printStackTrace();

            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (exceptionThrown == null) {
                String strduserId = ((AxwayApplication)getApplication()).getUserId();

                if(json != null) {
                    try {
                        new AlertDialog.Builder(currentActivity)
                                .setTitle("Success!").setMessage(json.getJSONObject("meta").toString())
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    accessControlListsNameField.setText("");
                    selectedReadersIdList.clear();
                    selectedWritersIdList.clear();
                    readerPublicAccess = false;
                    writerPublicAccess = false;
                }
                else
                {
                    String messageStr = (strduserId != null && strduserId.length()  == 0) ?  "Please Login to get data" : "No Data Found";

                    new AlertDialog.Builder(currentActivity)
                            .setTitle("Success!").setMessage(messageStr)
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }

                removeButton5.setVisibility(View.VISIBLE);
            }
            else
            {
                handleSDKException(exceptionThrown, currentActivity);
            }
        }
    }

}

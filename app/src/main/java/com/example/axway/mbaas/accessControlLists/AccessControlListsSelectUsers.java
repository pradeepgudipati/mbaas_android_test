/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.accessControlLists;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKExcpetion;

public class AccessControlListsSelectUsers extends Activity {
	private static final int READER = 1;
	private static final int WRITER = 2;
	private static final int UPDATE_READER = 3;
	private static final int UPDATE_WRITER = 4;
	private static AccessControlListsSelectUsers currentActivity;

	private boolean readerPublicAccess;
	private boolean writerPublicAccess;

	private ArrayList<String> selectedReadersIdList = new ArrayList<String>();
	private ArrayList<String> selectedWritersIdList = new ArrayList<String>();

	private JSONArray users;
	private int selectedIdListLength;
	private int accessType;

	private ArrayList<String> userIdList = new ArrayList<String>();
	private ArrayList<String> selectedIdList = new ArrayList<String>();

	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.access_control_lists_select_users);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		listView = (ListView) findViewById(R.id.access_control_lists_select_users_list_view);

		final ArrayList<String> loadingList = new ArrayList<String>();
		loadingList.add("Loading, please wait...");
		final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, loadingList);
		listView.setAdapter(adapter);

		Intent intent = getIntent();
		accessType = intent.getIntExtra("access_type", 0);
		if (accessType == READER || accessType == WRITER) {
			readerPublicAccess = intent.getBooleanExtra("reader_public_access", false);
			writerPublicAccess = intent.getBooleanExtra("writer_public_access", false);
		}
		selectedReadersIdList = intent.getStringArrayListExtra("selected_reader_ids");
		selectedWritersIdList = intent.getStringArrayListExtra("selected_writer_ids");

		if (accessType == READER || accessType == UPDATE_READER) {
			selectedIdList = selectedReadersIdList;
		} else if (accessType == WRITER || accessType == UPDATE_WRITER) {
			selectedIdList = selectedWritersIdList;
		}
		selectedIdListLength = selectedIdList.size();
        new aclUsersTask().execute();
	}

	private void markSelection() {
		if (accessType == READER && readerPublicAccess) {
			listView.setItemChecked(0, true);
		} else if (accessType == WRITER && writerPublicAccess) {
			listView.setItemChecked(0, true);
		}

		if (selectedIdListLength > 0) {
			int userCount = userIdList.size();
			for (int i = 0; i < selectedIdListLength; i++) {
				if (accessType == READER || accessType == WRITER) {
					for (int j = 1; j < userCount + 1; j++) {
						if (selectedIdList.get(i).equals(userIdList.get(j - 1))) {
							listView.setItemChecked(j, true);
						}
					}
				} else if (accessType == UPDATE_READER || accessType == UPDATE_WRITER) {
					for (int j = 0; j < userCount; j++) {
						if (selectedIdList.get(i).equals(userIdList.get(j))) {
							listView.setItemChecked(j, true);
						}
					}
				}
			}
		}
	}


	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void finish() {
		selectedIdList.clear();
		int listLength = listView.getCount();
		SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();

		for (int i = 0; i < listLength; i++) {
			if (i == 0 && listView.isItemChecked(i) && accessType == READER) {
				readerPublicAccess = true;
			} else if (i == 0 && listView.isItemChecked(i) && accessType == WRITER) {
				writerPublicAccess = true;
			} else if (i == 0 && !(listView.isItemChecked(i)) && accessType == READER) {
				readerPublicAccess = false;
			} else if (i == 0 && !(listView.isItemChecked(i)) && accessType == WRITER) {
				writerPublicAccess = false;
			} else if (i != 0 && checkedItemPositions.get(i) && (accessType == READER || accessType == WRITER)) {
				selectedIdList.add(userIdList.get(i - 1));
			} else if (checkedItemPositions.get(i) && (accessType == UPDATE_READER || accessType == UPDATE_WRITER)) {
				selectedIdList.add(userIdList.get(i));
			}
		}

		if (accessType == READER || accessType == UPDATE_READER) {
			selectedReadersIdList = selectedIdList;
		} else if (accessType == WRITER || accessType == UPDATE_WRITER) {
			selectedWritersIdList = selectedIdList;
		}

		// Prepare data intent
		Intent data = new Intent();
		if (accessType == READER || accessType == WRITER) {
			data.putExtra("reader_public_access", readerPublicAccess);
			data.putExtra("writer_public_access", writerPublicAccess);
		}
		data.putExtra("selected_reader_ids", selectedReadersIdList);
		data.putExtra("selected_writer_ids", selectedWritersIdList);

		// Activity finished successfully, return the data
		setResult(RESULT_OK, data);
		super.finish();
	}

    private class aclUsersTask extends AsyncTask<Void,Void, JSONObject> {

        private SdkException exceptionThrown =null;
        JSONObject successResponse;

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new UsersAPI(SdkClient.getInstance()).usersQuery(null,null,null,null,null,null,null,null,null,3,null);

            }catch(SdkException e){
                exceptionThrown = e;
				handleSDKExcpetion(exceptionThrown, currentActivity);
            }

            return successResponse;
        }
        @Override
        protected void onPostExecute(JSONObject json){
            try {
                if (exceptionThrown == null && json.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")){
                    try {
                        users = json.getJSONObject("response").getJSONArray("users");
                        final ArrayList<String> objectsList = new ArrayList<String>();
                        if (accessType == WRITER || accessType == READER) {
                            objectsList.add("<Public Access>");
                        }

                        for (int i = 0; i < users.length(); i++) {
                            JSONObject user = users.getJSONObject(i);
                            objectsList.add(user.get("first_name") + " " + user.get("last_name"));
                            userIdList.add(user.get("id").toString());
                        }
                        if (objectsList.size() <= 0) {
                            objectsList.add("No Results!");
                        }
                        // Load listView rows
                        final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_checked, objectsList);
                        listView.setAdapter(adapter);
                        listView.setChoiceMode(2);
                    } catch (JSONException e1) {
                        Utils.handleException(e1, currentActivity);
                    }
                    markSelection();
                }else
                {
                    handleSDKExcpetion(exceptionThrown, currentActivity);
                }

            } catch (JSONException e) {
                handleException(e, currentActivity);
            }

        }

    }
}

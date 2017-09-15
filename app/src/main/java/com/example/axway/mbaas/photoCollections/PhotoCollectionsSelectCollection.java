/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.photoCollections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotoCollectionsAPI;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleErrorInResponse;
import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKExcpetion;

public class PhotoCollectionsSelectCollection extends Activity {
    private static PhotoCollectionsSelectCollection currentActivity;
    private String collectionId;
    private int checkedRow;
    private JSONArray tableData;
    HashMap<String, Object> data = new HashMap<String, Object>();

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_collections_select_collection);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        listView = (ListView) findViewById(R.id.photo_collections_select_collection_list_view);

        final ArrayList<String> loadingList = new ArrayList<String>();
        loadingList.add("Loading...");
        final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity,
                android.R.layout.simple_list_item_1, loadingList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (tableData == null || tableData.length() <= 0) {
                    return;
                }

                if (position != checkedRow) {
                    listView.setItemChecked(checkedRow, false);

                    listView.setItemChecked(position, true);
                    checkedRow = position;
                    try {
                        if (position == 0) {
                            collectionId = null;
                        } else {
                            collectionId = tableData.getJSONObject(position - 1).getString("id");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        collectionId = getIntent().getStringExtra("id");


       /* Intent showmeintent = new Intent(currentActivity, UsersShowMe.class);
        startActivityForResult(showmeintent,100);*/
        new apiTask().execute();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resuldata)
    {
        super.onActivityResult(requestCode, resultCode, resuldata);
        if(requestCode==resultCode)
        {
            data.put("user_id", resuldata.getStringExtra("id"));
            new apiTask().execute();
        }
        else
            handleErrorInResponse("Error getting User Details, Please Check login!",currentActivity);
    }

    private void markSelection() {
        if (collectionId == null) {
            listView.setItemChecked(0, true);
            checkedRow = 0;
        } else {
            for (int j = 0; j < tableData.length(); j++) {
                try {
                    if (collectionId.equals(tableData.getJSONObject(j).getString("id"))) {
                        listView.setItemChecked(j + 1, true);
                        checkedRow = j + 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
        // Prepare data intent
        Intent data = new Intent();
        data.putExtra("id", collectionId);

        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        super.finish();
    }

    private class apiTask extends AsyncTask<Void, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(currentActivity);

        JSONObject successResponse;
        private SdkException exceptionThrown = null;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                JSONObject userResponse = new UsersAPI(SdkClient.getInstance()).usersShowMe(null, null);
                data.put("user_id",userResponse.getJSONObject("response").getJSONArray("users").getJSONObject(0).get("id").toString());
                successResponse = new PhotoCollectionsAPI(SdkClient.getInstance()).photoCollectionsSearch(data.get("user_id").toString(),null,null,null,null);
            } catch (SdkException e) {
                exceptionThrown =e;
            }
            catch (JSONException e1) {
                handleException(e1,currentActivity);
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject xml) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            } try {
                if (exceptionThrown == null && successResponse.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
                        tableData = successResponse.getJSONObject("response").getJSONArray("collections");
                        final ArrayList<String> objectsList = new ArrayList<String>();

                        // Add option to pic no collection
                        objectsList.add("No Collection");
                        for (int i = 0; i < tableData.length(); i++) {
                            JSONObject coll = tableData.getJSONObject(i);
                            objectsList.add(coll.getString("name"));
                        }
                        if (objectsList.size() <= 0) {
                            objectsList.add("No Results!");
                        }
                        // Load listView rows
                        final StableArrayAdapter adapter = new StableArrayAdapter(
                                currentActivity, android.R.layout.simple_list_item_checked,
                                objectsList);
                        listView.setAdapter(adapter);
                        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    markSelection();
                } else
                    handleSDKExcpetion(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }

        }

    }
}

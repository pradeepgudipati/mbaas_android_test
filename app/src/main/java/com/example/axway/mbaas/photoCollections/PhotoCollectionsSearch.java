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
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleErrorInResponse;
import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class PhotoCollectionsSearch extends Activity {
    private static PhotoCollectionsSearch currentActivity;
    private JSONArray tableData;
    HashMap<String, Object> data = new HashMap<String, Object>();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_collections_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        listView = (ListView) findViewById(R.id.photo_collections_search_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                try {
                    if (tableData != null && tableData.length() > 0) {
                        String collectionId = tableData.getJSONObject(position).getString("id");
                        Intent intent = new Intent(currentActivity, PhotoCollectionsShow.class);
                        intent.putExtra("id", collectionId);
                        startActivity(intent);
                    }
                } catch (JSONException e1) {
                    Utils.handleException(e1, currentActivity);
                }

            }

        });



       /* Intent showmeintent = new Intent(currentActivity, UsersShowMe.class);
        startActivityForResult(showmeintent,100);*/
        new apiTask().execute();

       }

    @Override
    protected void onDestroy() {
        currentActivity = null;
        super.onDestroy();
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
            } catch (JSONException e1) {
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
                    for (int i = 0; i < tableData.length(); i++) {
                        JSONObject collection = tableData.getJSONObject(i);
                        objectsList.add(collection.getString("name"));
                    }
                    if (objectsList.size() <= 0) {
                        objectsList.add("No Results!");
                    }

                    // Load listView rows
                    final StableArrayAdapter adapter = new StableArrayAdapter(
                            currentActivity, android.R.layout.simple_list_item_1,
                            objectsList);
                    listView.setAdapter(adapter);

                } else
                    handleSDKException(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }

        }

    }
}

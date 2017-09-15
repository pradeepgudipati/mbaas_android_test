/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.users;

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
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersQuery extends Activity {
    private static UsersQuery currentActivity;
    private JSONArray users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_query);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;
        new apiTask().execute();
    }

    @Override
    protected void onDestroy() {
        currentActivity = null;
        super.onDestroy();
    }

    private class apiTask extends AsyncTask<Void, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(currentActivity);

        JSONObject successResponse;

        @Override
        protected void onPreExecute() {
            {
                this.dialog.setMessage("Please wait");
                this.dialog.show();

            }

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new UsersAPI(SdkClient.getInstance()).usersQuery(null, null, null, null, null, null, null, null, null, null, null);
            } catch (SdkException e) {
                Utils.handleSDKExcpetion(e, currentActivity);
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject xml) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
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
            try {
                users = successResponse.getJSONObject("response").getJSONArray("users");

                final ArrayList<String> objectsList = new ArrayList<String>();
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    if (!user.get("username").toString().isEmpty())
                        objectsList.add(user.get("username").toString() + " ");
                }
                if (objectsList.size() <= 0) {
                    objectsList.add("No Results!");
                }
                // Load listView rows
                adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                Utils.handleException(e, currentActivity);
            }
        }
    }
}

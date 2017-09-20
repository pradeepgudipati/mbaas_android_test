/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.chats;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.ChatsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatsQuery extends Activity {
    private static ChatsQuery currentActivity;
    private JSONArray tableData;
    JSONObject successResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_query);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        final ListView listView = (ListView) findViewById(R.id.chats_query_list_view);

        final ArrayList<String> loadingList = new ArrayList<String>();
        loadingList.add("Loading...");
        final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity,
                android.R.layout.simple_list_item_1, loadingList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                try {
                    if (tableData != null && tableData.length() > 0) {
                        String groupId = tableData.getJSONObject(position).getString("id");
                        Intent intent = new Intent(currentActivity, ChatsShowChatGroup.class);
                        intent.putExtra("id", groupId);
                        startActivity(intent);
                    }
                } catch (JSONException e1) {
                    Utils.handleException(e1, currentActivity);
                }
            }

        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    successResponse = new ChatsAPI(SdkClient.getInstance()).chatsGetChatGroups(null,null,null,null,3, null);
                           // chatsQuery(null,null,null,null,null,null,null,null,null,null,null,null);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tableData = successResponse.getJSONObject("response").getJSONArray("chat_groups");
                                final ArrayList<String> objectsList = new ArrayList<String>();
                                for (int i = 0; i < tableData.length(); i++) {
                                    JSONObject group = tableData.getJSONObject(i);
                                    JSONArray users = group.getJSONArray("participate_users");
                                    ArrayList<String> userNames = new ArrayList<String>();
                                    for (int j = 0; j < users.length(); j++) {
                                        JSONObject user = users.getJSONObject(j);
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(user.get("first_name"));
                                        sb.append(" ");
                                        sb.append(user.get("last_name"));
                                        userNames.add(sb.toString());
                                    }
                                    objectsList.add(TextUtils.join(", ", userNames));
                                }
                                if (objectsList.size() <= 0) {
                                    objectsList.add("No Results!");
                                }
                                // Load listView rows
                                final StableArrayAdapter adapter = new StableArrayAdapter(
                                        currentActivity, android.R.layout.simple_list_item_1,
                                        objectsList);
                                listView.setAdapter(adapter);
                            } catch (JSONException e1) {
                                Utils.handleException(e1, currentActivity);
                            }
                        }
                    });

                } catch (final SdkException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.handleSDKException(e, currentActivity);
                        }
                    });
                }

            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        currentActivity = null;
        super.onDestroy();
    }
}

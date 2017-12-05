/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.chats;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
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

public class ChatsSelectUsersForGroup extends Activity{
private static ChatsSelectUsersForGroup currentActivity;
private JSONArray tableData;
JSONObject successResponse;

private Button button;


@Override
public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chats_select_users_for_group);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    currentActivity = this;
    final ListView listView = (ListView) findViewById(R.id.chats_select_users_list_view);
    button = (Button) findViewById(R.id.chats_select_users_button1);
    button.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v){
            // Query Chat Groups
            ArrayList<String> ids = new ArrayList<String>();
            SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
            if(tableData != null && tableData.length() > 0){
                for(int i = 0; i < tableData.length(); i++){
                    if(checkedItemPositions.get(i)){
                        try{
                            ids.add(tableData.getJSONObject(i).getString("id"));
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            if(ids.size() > 0){
                Intent intent = new Intent(currentActivity, ChatsShowChatGroup.class);
                intent.putExtra("ids", ids);
                startActivity(intent);
            }else{
                new AlertDialog.Builder(currentActivity)
                        .setTitle("").setMessage("Please check at least one user!")
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        }

    });

    //listView = (ListView) findViewById(R.id.chats_select_users_list_view);

    final ArrayList<String> loadingList = new ArrayList<String>();
    loadingList.add("Loading, please wait...");
    final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity,
                                                                     android.R.layout.simple_list_item_1, loadingList);
    listView.setAdapter(adapter);

    new Thread(new Runnable(){

        @Override
        public void run(){
            try{
                successResponse = new UsersAPI(SdkClient.getInstance()).usersQuery(null, null, null, null, null, null, null, null, null, null, null);
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        try{

                            tableData = successResponse.getJSONObject("response").getJSONArray("users");
                            final ArrayList<String> objectsList = new ArrayList<String>();

                            for(int i = 0; i < tableData.length(); i++){
                                JSONObject user = tableData.getJSONObject(i);
                                StringBuilder sb = new StringBuilder();
                                sb.append(user.get("first_name"));
                                sb.append(" ");
                                sb.append(user.get("last_name"));
                                objectsList.add(sb.toString());
                            }
                            if(objectsList.size() <= 0){
                                objectsList.add("No Results!");
                            }
                            // Load listView rows
                            final StableArrayAdapter adapter2 = new StableArrayAdapter(
                                                                                              currentActivity, android.R.layout.simple_list_item_checked,
                                                                                              objectsList);
                            listView.setAdapter(adapter2);
                            listView.setChoiceMode(2);
                        }catch(JSONException e1){
                            Utils.handleException(e1, currentActivity);
                        }
                    }
                });
            }catch(SdkException e){
                e.printStackTrace();
            }
        }
    }).start();
}

@Override
public void onBackPressed(){
    finish();
}
}

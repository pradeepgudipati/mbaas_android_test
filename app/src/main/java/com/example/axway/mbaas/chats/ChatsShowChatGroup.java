/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.chats;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ChatsShowChatGroup extends Activity {
    private static ChatsShowChatGroup currentActivity;
    private HashMap<String, Object> pingData = new HashMap<String, Object>();
    private HashMap<String, Object> createData = new HashMap<String, Object>();
    private ListView listView;
    private ArrayList<JSONObject> tableData;
    private Timer timer;
    JSONObject responseHandler;

    String chatGroupId = null;
    String participateIds = null;
    String queryWhere = null;
    String chatId = null;
     public void onResponse() {
         if (responseHandler != null) {
             try {
                 tableData = new ArrayList<JSONObject>();
                    JSONArray chats = responseHandler.getJSONObject("response").getJSONArray("chats");
                 for (int i = 0; i < chats.length(); i++) {
                     tableData.add(chats.getJSONObject(i));

                     if (i == 0) {
                         // Only get new messages on next ping
                         // NOTE: the `where` parameter of queries must be
                         // constructed using a JSONObject
                         JSONObject where = new JSONObject();
                         JSONObject updatedAt = new JSONObject();
                         updatedAt.put("$gt", chats.getJSONObject(i).getString("updated_at"));
                         where.put("updated_at", updatedAt);
                         pingData.put("where", where.toString());
                         queryWhere = pingData.get("where").toString();
                     }
                 }

                 if (currentActivity == null) {
                     return;
                 }

                 final ChatsRowAdapter adapter = new ChatsRowAdapter(currentActivity, tableData);
                 adapter.setOnDeleteClickListener(new ChatsRowAdapter.ChatsOnDeleteClickListener() {

                     @Override
                     public void onClick(final int position, View v) {
                         final JSONObject tempData = tableData.get(position);

                         HashMap<String, Object> data = new HashMap<String, Object>();
                         try {
                             data.put("chat_id", tempData.getString("id"));
                             chatId = data.get("chat_id").toString();
                         } catch (JSONException e1) {
                             e1.printStackTrace();
                         }

                         new Thread(new Runnable() {
                             @Override
                             public void run() {
                                 try {
                                     JSONObject Res = new ChatsAPI(SdkClient.getInstance()).chatsDelete(chatId, null, null);
                                     Log.d("Response of Delete", Res.toString());
                                     try {
                                         responseHandler = (JSONObject) responseHandler.get("response");
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }
                                 } catch (SdkException e) {
                                     Utils.handleSDKException(e, currentActivity);
                                 }
                             }
                         }).start();

                         tableData.remove(position);
                         adapter.notifyDataSetChanged();
                     }

                 });
                 listView.setAdapter(adapter);

             } catch (JSONException e1) {
                 Utils.handleException(e1, currentActivity);
             }
         } else {
             Utils.handleErrorInResponse("SomeError", currentActivity);
         }

     }


    private EditText messageField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_show_chat_group);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        Intent intent = getIntent();

        if (intent.hasExtra("id")) {
            String id = intent.getStringExtra("id");
            pingData.put("chat_group_id", id);
            createData.put("chat_group_id", id);
            chatGroupId = pingData.get("chat_group_id").toString();
        } else if (intent.hasExtra("ids")) {
            ArrayList<String> ids = intent.getStringArrayListExtra("ids");
            String idsString = TextUtils.join(",", ids);
            pingData.put("to_ids", idsString);
            createData.put("to_ids", idsString);
            participateIds = pingData.get("to_ids").toString();
        } else {
            new AlertDialog.Builder(currentActivity).setTitle("Error")
                    .setMessage("Invalid use of Show Chat Groups! Must pass in id or ids!")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }

        messageField = (EditText) findViewById(R.id.chats_show_chat_group_messageField1);

        OnKeyListener keyListener = new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    final String message = messageField.getText().toString();
                    if (message.length() > 0) {
                        createData.put("message", message);
                        messageField.setText("");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    responseHandler = new ChatsAPI(SdkClient.getInstance()).chatsCreate(participateIds,chatGroupId,message,null,null,null,null,null,null,null,null);
                                    Log.d("Response of Create Chat", responseHandler.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            onResponse();
                                        }
                                    });
                                }catch (SdkException e) {
                                    Utils.handleSDKException(e, currentActivity);
                                }
                            }
                            }).start();
                    } else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager
                                .hideSoftInputFromWindow(messageField.getWindowToken(), 0);
                    }
                } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    currentActivity.finish();
                }
                return true;
            }
        };

        messageField.setOnKeyListener(keyListener);

        listView = (ListView) findViewById(R.id.chats_show_chat_group_listView1);

        final ArrayList<String> loadingList = new ArrayList<String>();
        loadingList.add("Loading...");
        final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity,
                android.R.layout.simple_list_item_1, loadingList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

            }

        });

        // Start a timer to poll the server or new messages every 5 seconds
        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run()
//            {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ping();
//                    }
//
//                });
//
//            }
//        }, 500, 500000000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ping();
            }
        },500,10000);
        ping();
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        currentActivity = null;
        super.onDestroy();
    }

    private void ping() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  responseHandler =  new ChatsAPI(SdkClient.getInstance()).chatsQuery(participateIds,chatGroupId,null,null,null,null,null,queryWhere,null,null,null,3);
                    Log.d("Response of Query Chat", responseHandler.toString());
                    runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          onResponse();
                      }
                  });

                } catch (SdkException e) {
                    Utils.handleSDKException(e, currentActivity);
                }
            }
        }).start();
    }

}

/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.pushNotifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PushNotificationsAPI;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class PushNotify extends Activity {
    private static String TAG = "PushSubscribe";
    static PushNotify currentActivity;

    private Button button1;

    private EditText channelName;
    private EditText payload;
    private EditText users;
    private LinearLayout linearLayout;

    String to_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_notify);
        currentActivity = this;

        linearLayout = (LinearLayout) findViewById(R.id.pushnotify_linear_layout);
        channelName = (EditText) findViewById(R.id.pushnotify_channel_name_field);
        payload = (EditText) findViewById(R.id.pushnotify_payload_field);
        users = (EditText) findViewById(R.id.pushnotify_user_field);

        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new notifyPush().execute();
                } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    currentActivity.finish();
                }
                return false;
            }
        };

        channelName.setOnKeyListener(keyListener);
        payload.setOnKeyListener(keyListener);
        users.setOnKeyListener(keyListener);

        button1 = (Button) findViewById(R.id.pushsubscribe_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new notifyPush().execute();
            }
        });
    }



    private class notifyPush extends AsyncTask<Void, Void, JSONObject> {
        HashMap<String, Object> data = new HashMap<String, Object>();

        private SdkException exceptionThrown = null;
        JSONObject successResponse;
        String getusers;

        @Override
        protected void onPreExecute() {



            String Channel = channelName.getText().toString();
            String Payload = payload.getText().toString();
            getusers = users.getText().toString();

            data.put("channel", Channel);
            data.put("payload", Payload);
            data.put("to_ids", to_ids);

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            JSONArray queryusers;
            String[] usernames = new String[0];

            usernames = getusers.split(",");


            try {
                JSONObject usersResponse  = new UsersAPI(SdkClient.getInstance()).usersQuery(null, null, null, null, null, null, null, null, null, null, null);
                queryusers = usersResponse.getJSONObject("response").getJSONArray("users");
                to_ids = ",";

                for (int i = 0; i < queryusers.length(); i++) {
                    JSONObject user = queryusers.getJSONObject(i);
                    for (int j = 0; j < usernames.length; j++) {
                        if (user.get("username").toString().equals(usernames[j])) {
                            if (to_ids.equals(",")) {
                                to_ids = user.get("id").toString();
                            } else
                                to_ids = to_ids + "," + user.get("id").toString();
                        }
                    }
                }

            } catch (SdkException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                successResponse = new PushNotificationsAPI(SdkClient.getInstance()).pushNotificationsNotify(data.get("channel").toString(),null,
                        to_ids,data.get("payload").toString(),null,null,null
                );
            } catch (SdkException e) {
                exceptionThrown = e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (exceptionThrown == null) {
                Log.i(TAG, "Notification Sent SUCCESS");

                new AlertDialog.Builder(currentActivity).setTitle("Success")
                        .setMessage("Notified to Users").setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    finish();
                                }
                            })
                        .setIcon(android.R.drawable.ic_dialog_info).show();

            } else {
                Utils.handleSDKException(exceptionThrown, currentActivity);
            }
        }
    }

}

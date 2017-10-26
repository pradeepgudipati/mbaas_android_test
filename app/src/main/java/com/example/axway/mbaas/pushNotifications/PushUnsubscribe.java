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
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by skchkadiyala on 8/29/2017.
 */

public class PushUnsubscribe extends Activity {
    private static String TAG = "PushUnsubscribe";
    static PushUnsubscribe currentActivity;
    String deviceToken;

    private Button button1;

    private EditText channelName;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_unsubscribe);
        currentActivity = this;

        linearLayout = (LinearLayout) findViewById(R.id.pushunsubscribe_linear_layout);
        channelName = (EditText) findViewById(R.id.pushunsubscribe_channel_name_field);

        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new unsubscribePush().execute();
                } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    currentActivity.finish();
                }
                return false;
            }
        };

        channelName.setOnKeyListener(keyListener);

        button1 = (Button) findViewById(R.id.pushunsubscribe_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deviceToken = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Refreshed token: " + deviceToken);
                new unsubscribePush().execute();
            }
        });
    }


    private class unsubscribePush extends AsyncTask<Void, Void, JSONObject> {
        HashMap<String, Object> data = new HashMap<String, Object>();

        private SdkException exceptionThrown = null;
        JSONObject successResponse;

        @Override
        protected void onPreExecute() {
            String Channel = channelName.getText().toString();

            data.put("channel", Channel);
            data.put("device_token", deviceToken);

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            try {
                successResponse = new PushNotificationsAPI(SdkClient.getInstance()).pushNotificationsUnsubscribe(
                        data.get("channel").toString(),data.get("device_token").toString()
                        ,null,null);
            } catch (SdkException e) {
                exceptionThrown = e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (exceptionThrown == null) {
                Log.i(TAG, "subscribeToken SUCCESS");

                new AlertDialog.Builder(currentActivity).setTitle("Success")
                        .setMessage("Device Unsubscribed!").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
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

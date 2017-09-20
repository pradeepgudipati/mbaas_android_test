/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.pushNotifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PushNotificationsAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by skchkadiyala on 8/30/2017.
 */

public class QueryChannels extends Activity {

    private static QueryChannels currentActivity;

    private TextView statusTextView;
    private Button queryButton;
    private ListView listView;

    private JSONArray subscribeObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_querychannel);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        statusTextView = (TextView) findViewById(R.id.push_querychannel_status_textView);
        statusTextView.setVisibility(View.GONE);


        queryButton = (Button) findViewById(R.id.push_querychannel_button);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new queryChannels().execute();
            }
        });

        listView = (ListView) findViewById(R.id.push_querychannel_listView);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//
//            }
//        });
    }

    private class queryChannels extends AsyncTask<Void, Void, JSONObject> {

        private SdkException exceptionThrown = null;
        JSONObject successResponse;
        final ArrayList<String> objectsList = new ArrayList<String>();


        @Override
        protected JSONObject doInBackground(Void... voids) {

            try {
                successResponse = new PushNotificationsAPI(SdkClient.getInstance()).pushNotificationsChannelsQuery(null,null,null,null,null);
            } catch (SdkException e) {
                exceptionThrown = e;
                //handleSDKException(e, currentActivity);
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (exceptionThrown == null) {
                try {
                    subscribeObjects = json.getJSONObject("response").getJSONArray("push_channels");
                    String content = null;
                    for (int i = 0; i < subscribeObjects.length(); i++) {
                        content = (String) subscribeObjects.get(i);
                        objectsList.add(content);
                    }
                    statusTextView.setVisibility(View.GONE);

                    if (objectsList.size() <= 0) {
                        new AlertDialog.Builder(currentActivity)
                                .setTitle("Alert").setMessage("No objects found!")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }
                    StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList);
                    listView.setAdapter(adapter);
                    listView.setVisibility(View.VISIBLE);
                    queryButton.setVisibility(View.VISIBLE);

                } catch (JSONException e1) {
                    Utils.handleException(e1, currentActivity);
                }
                queryButton.setVisibility(View.VISIBLE);
            } else {
                Utils.handleSDKException(exceptionThrown, currentActivity);
            }
        }
    }
}

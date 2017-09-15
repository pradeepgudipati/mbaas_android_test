/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.photoCollections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotoCollectionsAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKExcpetion;

public class PhotoCollectionsShowSubcollections extends Activity {
    private static PhotoCollectionsShowSubcollections currentActivity;

    private TextView textView;
    HashMap<String, Object> data = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_collections_show_subcollections);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        textView = (TextView) findViewById(R.id.photo_collections_show_subcollections_text_view);
        // Make links clickable
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        // Create dictionary of parameters to be passed with the request

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
        private SdkException exceptionThrown = null;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
            data.put("collection_id", getIntent().getStringExtra("id"));

        }
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new PhotoCollectionsAPI(SdkClient.getInstance()).photoCollectionsShowSubcollections(data.get("collection_id").toString(),null,null,3,null);
            } catch (SdkException e) {
                exceptionThrown =e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject xml) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            } try {
                if (exceptionThrown == null && successResponse.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
                    textView.setText(successResponse.getJSONObject("response").toString(4));


                } else
                    handleSDKExcpetion(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }

        }

    }
}

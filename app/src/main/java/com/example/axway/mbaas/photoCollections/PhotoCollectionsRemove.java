/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.photoCollections;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotoCollectionsAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class PhotoCollectionsRemove extends Activity {
    private static PhotoCollectionsRemove currentActivity;

    private Button button1;
    HashMap<String, Object> data = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_collections_remove);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        button1 = (Button) findViewById(R.id.photo_collections_remove_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new apiTask().execute();
            }
        });
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
            button1.setVisibility(View.GONE);
            data.put("collection_id", getIntent().getStringExtra("id"));

        }
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new PhotoCollectionsAPI(SdkClient.getInstance()).photoCollectionsDelete(data.get("collection_id").toString(),null,null);
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
                    new AlertDialog.Builder(currentActivity)
                            .setTitle("").setMessage("Removed!")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                } else
                    handleSDKException(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }

            button1.setVisibility(View.VISIBLE);


        }

    }
}

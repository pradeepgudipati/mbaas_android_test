/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.photoCollections;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotoCollectionsAPI;
import com.example.axway.mbaas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKExcpetion;

public class PhotoCollectionsShow extends Activity {
    private static PhotoCollectionsShow currentActivity;

    private String collectionId;
    private JSONObject collectionData;

    private Button showPhotosButton;
    private Button showSubcollectionsButton;
    private Button updateButton;
    private Button removeButton;
    private TextView textView;
    HashMap<String, Object> data = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_collections_show);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        showPhotosButton = (Button) findViewById(R.id.photo_collections_show_show_photos_button1);
        showPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (collectionData.getJSONObject("counts").getInt("photos") <= 0) {
                /*        new AlertDialog.Builder(currentActivity)
                                .setTitle("Alert").setMessage("There are no photos to show.")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return;*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(currentActivity, PhotoCollectionsShowPhotos.class);
                intent.putExtra("id", collectionId);
                startActivity(intent);
            }
        });

        showSubcollectionsButton = (Button) findViewById(R.id.photo_collections_show_show_subcollections_button2);
        showSubcollectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray subCollections = collectionData.getJSONArray("subcollections");
                    if (subCollections.length() <= 0) {
                        new AlertDialog.Builder(currentActivity)
                                .setTitle("Alert")
                                .setMessage("There are no subcollections to show.")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(currentActivity,
                        PhotoCollectionsShowSubcollections.class);
                intent.putExtra("id", collectionId);
                startActivity(intent);
            }
        });

        updateButton = (Button) findViewById(R.id.photo_collections_show_update_button3);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentActivity, PhotoCollectionsUpdate.class);
                intent.putExtra("id", collectionId);
                startActivity(intent);
            }
        });

        removeButton = (Button) findViewById(R.id.photo_collections_show_remove_button4);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentActivity, PhotoCollectionsRemove.class);
                intent.putExtra("id", collectionId);
                startActivity(intent);
            }
        });

        textView = (TextView) findViewById(R.id.photo_collections_show_text_view);
        // Make links clickable
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        Intent intent = getIntent();
        collectionId = intent.getStringExtra("id");

        // Create dictionary of parameters to be passed with the request
        data.put("collection_id", collectionId);

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
        }
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new PhotoCollectionsAPI(SdkClient.getInstance()).photoCollectionsShow(data.get("collection_id").toString(),3,null);
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
                    collectionData = successResponse.getJSONObject("response").getJSONArray("collections")
                            .getJSONObject(0);

                    textView.setText(successResponse.getJSONObject("response").toString(4)
                            .replace("\\/", "/"));

                    Intent collectionName = new Intent();
                    collectionName.putExtra("name",successResponse.getJSONObject("response").getJSONArray("collections")
                            .getJSONObject(0).getString("name"));
                    setResult(300,collectionName);
                } else
                    handleSDKExcpetion(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }

        }

    }
}

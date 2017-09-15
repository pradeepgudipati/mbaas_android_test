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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotoCollectionsAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKExcpetion;

public class PhotoCollectionsCreate extends Activity {
    private static final int REQUEST_CODE = 1;
    private static PhotoCollectionsCreate currentActivity;
    private ArrayList<EditText> fields = new ArrayList<EditText>();
    private String parentCollectionId = null;
    HashMap<String, Object> data = new HashMap<String, Object>();

    private EditText nameField;
    private Button chooseParentButton;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_collections_create);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        nameField = (EditText) findViewById(R.id.photo_collections_create_name_field);

        OnKeyListener keyListener = new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submitForm();
                } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    currentActivity.finish();
                }
                return true;
            }
        };

        nameField.setOnKeyListener(keyListener);

        chooseParentButton = (Button) findViewById(R.id.photo_collections_create_choose_parent_button1);
        chooseParentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentActivity, PhotoCollectionsSelectCollection.class);
                intent.putExtra("id", parentCollectionId);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        createButton = (Button) findViewById(R.id.photo_collections_create_create_button2);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        fields.add(nameField);
    }

    @Override
    protected void onDestroy() {
        currentActivity = null;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            parentCollectionId = data.getStringExtra("id");
        }
    }

    private void submitForm() {
        for (EditText field : fields) {
            if (field.getText().toString().length() <= 0) {
                field.requestFocus();
                return;
            }
        }

        createButton.setVisibility(View.GONE);

        // Create dictionary of parameters to be passed with the request
        data.put("name", nameField.getText().toString());
        if (parentCollectionId != null) {
            data.put("parent_collection_id", parentCollectionId);
        }
            new apiTask().execute();
    }

    private class apiTask extends AsyncTask<Void, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(currentActivity);

        JSONObject successResponse;
        private SdkException exceptionThrown = null;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
            createButton.setVisibility(View.GONE);
        }
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new PhotoCollectionsAPI(SdkClient.getInstance()).photoCollectionsCreate(data.get("name").toString(), parentCollectionId,null,null,null,null,null,null);
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
                            .setTitle("Success").setMessage("Created!")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                } else
                    handleSDKExcpetion(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }
            createButton.setVisibility(View.VISIBLE);


        }

    }
}

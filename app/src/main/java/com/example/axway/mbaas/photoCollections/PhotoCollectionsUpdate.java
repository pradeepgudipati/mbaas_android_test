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
import static com.example.axway.mbaas.Utils.handleSDKException;

public class PhotoCollectionsUpdate extends Activity{
private static PhotoCollectionsUpdate currentActivity;
private ArrayList<EditText> fields = new ArrayList<EditText>();
private String id;
HashMap<String, Object> data = new HashMap<String, Object>();

private EditText nameField;
private Button createButton;

@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.photo_collections_update);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    currentActivity = this;

    nameField = (EditText) findViewById(R.id.photo_collections_update_name_field);

    OnKeyListener keyListener = new OnKeyListener(){
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            if((event.getAction() == KeyEvent.ACTION_DOWN)
                       && (keyCode == KeyEvent.KEYCODE_ENTER)){
                submitForm();
            }else if(keyCode == KeyEvent.KEYCODE_BACK){
                currentActivity.finish();
            }
            return true;
        }
    };

    nameField.setOnKeyListener(keyListener);

    createButton = (Button) findViewById(R.id.photo_collections_update_update_button1);
    createButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            submitForm();
        }
    });

    fields.add(nameField);

    id = getIntent().getStringExtra("id");

    // Create dictionary of parameters to be passed with the request
    data.put("collection_id", id);
    new callApiTask().execute();


}

@Override
protected void onDestroy(){
    currentActivity = null;
    super.onDestroy();
}

private void submitForm(){
    for(EditText field : fields){
        if(field.getText().toString().length() <= 0){
            field.requestFocus();
            return;
        }
    }

    createButton.setVisibility(View.GONE);

    // Create dictionary of parameters to be passed with the request
    data.put("collection_id", id);
    data.put("name", nameField.getText().toString());
    new apiTask().execute();

}

private class apiTask extends AsyncTask<Void, Void, JSONObject>{
    private ProgressDialog dialog = new ProgressDialog(currentActivity);

    JSONObject successResponse;
    private SdkException exceptionThrown = null;

    @Override
    protected void onPreExecute(){
        this.dialog.setMessage("Please wait");
        this.dialog.show();
        createButton.setVisibility(View.GONE);
    }

    @Override
    protected JSONObject doInBackground(Void... voids){
        try{
            successResponse = new PhotoCollectionsAPI(SdkClient.getInstance()).photoCollectionsUpdate(data.get("collection_id").toString(), data.get("name").toString(), null, null, null, null, null, null);
        }catch(SdkException e){
            exceptionThrown = e;
        }
        return successResponse;
    }

    @Override
    protected void onPostExecute(JSONObject xml){
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        try{
            if(exceptionThrown == null && successResponse.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")){
                new AlertDialog.Builder(currentActivity)
                        .setTitle("Success").setMessage("Updated!")
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                nameField.setText("");

            }else
                handleSDKException(exceptionThrown, currentActivity);
        }catch(JSONException e){
            handleException(e, currentActivity);
        }
        createButton.setVisibility(View.VISIBLE);


    }

}

private class callApiTask extends AsyncTask<Void, Void, JSONObject>{
    private ProgressDialog dialog = new ProgressDialog(currentActivity);

    JSONObject successResponse;
    private SdkException exceptionThrown = null;

    @Override
    protected void onPreExecute(){
        this.dialog.setMessage("Please wait");
        this.dialog.show();
    }

    @Override
    protected JSONObject doInBackground(Void... voids){
        try{
            successResponse = new PhotoCollectionsAPI(SdkClient.getInstance()).photoCollectionsShow(data.get("collection_id").toString(), 3, null);
        }catch(SdkException e){
            exceptionThrown = e;
        }
        return successResponse;
    }

    @Override
    protected void onPostExecute(JSONObject xml){
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        try{
            if(exceptionThrown == null && successResponse.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")){
                nameField.setText(successResponse.getJSONObject("response").getJSONArray("collections")
                                          .getJSONObject(0).getString("name"));
                createButton.setVisibility(View.VISIBLE);

            }else
                handleSDKException(exceptionThrown, currentActivity);
        }catch(JSONException e){
            handleException(e, currentActivity);
        }

    }

}
}

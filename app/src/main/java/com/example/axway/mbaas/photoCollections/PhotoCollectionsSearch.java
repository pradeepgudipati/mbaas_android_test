/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.photoCollections;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotoCollectionsAPI;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleErrorInResponse;
import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class PhotoCollectionsSearch extends Activity {
    private static PhotoCollectionsSearch currentActivity;
    private JSONArray tableData;
    HashMap<String, Object> data = new HashMap<String, Object>();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_collections_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        checkWritePermission();
        checkReadPermission();

        listView = (ListView) findViewById(R.id.photo_collections_search_list_view);
        final ArrayList<String> loadingList = new ArrayList<String>();
        loadingList.add("Loading, please wait...");
        final StableArrayAdapter adapter = new StableArrayAdapter(
                currentActivity, android.R.layout.simple_list_item_1,
                loadingList);
        listView.setAdapter(adapter);
        appendLog("Before api call");
        new apiTask().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                appendLog("Item clicked...");

                try {
                    if (tableData != null && tableData.length() > 0) {
                        appendLog("Table data...." + tableData.length());
                        String collectionId = tableData.getJSONObject(position).getString("id");
                        Intent intent = new Intent(currentActivity, PhotoCollectionsShow.class);
                        intent.putExtra("id", collectionId);
                        startActivity(intent);
                    }
                } catch (JSONException e1) {
                    Utils.handleException(e1, currentActivity);
                }

            }

        });

       /* Intent showmeintent = new Intent(currentActivity, UsersShowMe.class);
        startActivityForResult(showmeintent,100);*/

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
                JSONObject userResponse = new UsersAPI(SdkClient.getInstance()).usersShowMe(null, null);
                data.put("user_id",userResponse.getJSONObject("response").getJSONArray("users").getJSONObject(0).get("id").toString());
                successResponse = new PhotoCollectionsAPI(SdkClient.getInstance()).photoCollectionsSearch(data.get("user_id").toString(),null,null,null,null);
            } catch (SdkException e) {
                exceptionThrown =e;
            } catch (JSONException e1) {
                handleException(e1,currentActivity);
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject xml) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            } try {
                if (exceptionThrown == null && successResponse.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
                    tableData = successResponse.getJSONObject("response").getJSONArray("collections");
                    final ArrayList<String> objectsList = new ArrayList<String>();
                    for (int i = 0; i < tableData.length(); i++) {
                        JSONObject collection = tableData.getJSONObject(i);
                        objectsList.add(collection.getString("name"));
                    }
                    if (objectsList.size() <= 0) {
                        objectsList.add("No Results!");
                    }

                    // Load listView rows
                    final StableArrayAdapter adapter = new StableArrayAdapter(
                            currentActivity, android.R.layout.simple_list_item_1,
                            objectsList);
                    listView.setAdapter(adapter);
                    appendLog("After api call");

                } else
                    handleSDKException(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }

        }

    }

    /**
     * Used to check the sms permission granted/not
     */
    private void checkWritePermission() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 100);

            }
        }
    }

    /**
     * Used to check the sms permission granted/not
     */
    private void checkReadPermission() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);

            }
        }
    }



    public void appendLog(String text)
    {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/axway");
        myDir.mkdirs();

        String fname = String.format("%s%s","axwaydebug", ".log");
        File logFile = new File(myDir, fname);


        if (!logFile.exists())
        {
            try
            {

                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

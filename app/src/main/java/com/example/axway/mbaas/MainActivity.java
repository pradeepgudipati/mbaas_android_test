
 /**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

 package com.example.axway.mbaas;

 import android.*;
 import android.content.Intent;
 import android.content.pm.ActivityInfo;
 import android.content.pm.PackageManager;
 import android.content.res.AssetManager;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.ListView;

 import com.axway.mbaas_preprod.SdkException;
 import com.axway.mbaas_preprod.auth.SdkOAuthTokenHelper;

 import net.openid.appauth.AuthorizationException;

 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;


public class MainActivity extends LoginActivity  implements SdkOAuthTokenHelper.TokenResponseListener{
    private static String TAG = "MainActivity";
    private static MainActivity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;
        try {
            SdkOAuthTokenHelper.getInstance().processTokenCallback(this);
        } catch (SdkException e) {
            e.printStackTrace();
        }



        final ListView listView = (ListView) findViewById(R.id.listView1);
        String[] values = new String[] {
                "Users",
                "Access Control Lists",
                "Chats",
                "Checkins",
                "Geo Fences",
                "Custom Objects",
                "Photo Collections",
                "Photos",
                "Places",
                "Push Notifications",
//                "Push Schedules",
//                "Emails",
//                "Events",
//                "Files",
//                "Friends",
//                "Key Values",
//                "Likes",
//                "Messages",
//                "Posts",
//                "Reviews",
//                "Social",
//                "Status"
        };

        final ArrayList<String> objectsList = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            objectsList.add(values[i]);
        }

        // Load listView rows
        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, objectsList);
        listView.setAdapter(adapter);

        // Read JSON file with method dictionary
        AssetManager am = this.getAssets();
        String methodsDictJson = "";
        try {
            InputStream is = am.open("methodsDict.json");
            methodsDictJson = convertStreamToString(is);
            is.close();

            // Log JSON read from file from assets
            Log.d(TAG, "methodsDict.json");
            Log.d(TAG, new JSONObject(methodsDictJson).toString(4));
        } catch (IOException e1) {
            Utils.handleException(e1, currentActivity);
        } catch (JSONException e) {
            Utils.handleException(e, currentActivity);
        }

        JSONObject tempDict;
        try {
            tempDict = new JSONObject(methodsDictJson);
        } catch (JSONException e) {
            tempDict = null;
            Utils.handleException(e, currentActivity);
        }
        final JSONObject methodsDict = tempDict;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (methodsDict != null) {
                    try {
                        Intent intent;
                        JSONObject jsonObj = methodsDict.getJSONObject((objectsList.get(position)));
                        String packageName = jsonObj.getString("packageName");
                        if (jsonObj.has("className")) {
                            intent = new Intent(currentActivity, Class.forName(MainActivity.class.getPackage().getName()
                                    + packageName + "." + jsonObj.getString("className")));
                        }
                        else {
                            intent = new Intent(currentActivity, MethodsViewActivity.class);

                            JSONArray jsonRows = jsonObj.getJSONArray("rows");
                            JSONObject jsonMethodsDict = jsonObj.getJSONObject("methodsDict");

                            intent.putExtra("packageName", packageName);
                            intent.putExtra("rows", arrayListOfStringsFromJSONArray(jsonRows));
                            intent.putExtra("methodsDict", mapOfStringsFromJSONObject(jsonMethodsDict));
                        }
                        startActivity(intent);
                    } catch (JSONException e) {
                        Utils.handleException(e, currentActivity);
                    } catch (ClassNotFoundException e) {
                        Utils.handleException(e, currentActivity);
                    }
                }
            }

        });



        checkWritePermission();
        checkReadPermission();


    }

    /**
     * Used to check the sms permission granted/not
     */
    private void checkWritePermission() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 100);

            }
        }
    }

    /**
     * Used to check the sms permission granted/not
     */
    private void checkReadPermission() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);

            }
        }
    }

    private HashMap<String, String> mapOfStringsFromJSONObject(JSONObject jsonObj) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<?> keys = jsonObj.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, (String) jsonObj.get(key));
        }
        return map;
    }

    private ArrayList<String> arrayListOfStringsFromJSONArray(JSONArray jsonArray) {

        ArrayList<String> arrList = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); ++i) {
            try {
                arrList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                Utils.handleException(e, currentActivity);
            }
        }
        return arrList;
    }

    static String convertStreamToString(InputStream is) throws IOException {
        if (is == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        finally {
            is.close();
        }
        return sb.toString();
    }

    /**
     * Callback for the TokenResponse for Token callbacks and non Implicit Grant type
     *
     * @param ex {@link AuthorizationException} object
     */
    @Override
    public void onReceivedTokenResponse(AuthorizationException ex) {


        Log.d("MainActivity", "On Token Response Received. ignore ex if null, ex = " + ex);
        // Recommended to use AsyncTask
        new Thread(new Runnable() {
            @Override
            public void run() {

                // final SuccessResponse successResponse = new DefaultAPI(SdkClient.getInstance()).usersCount();
                Log.d("MainActivity", "successResponse ----- ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d( "MainActivity","Success- Authentication" );
                    }
                });


            }
        }).start();

    }

}

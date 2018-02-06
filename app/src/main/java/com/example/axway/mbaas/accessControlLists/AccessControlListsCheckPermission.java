/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.accessControlLists;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.ACLsAPI;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.AxwayApplication;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class AccessControlListsCheckPermission extends Activity{
private static AccessControlListsCheckPermission currentActivity;
private JSONArray users;

private ListView listView;
private String userId;

private EditText accessControlListsNameField;
private Button button1;


@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.access_control_lists_check_permission);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    currentActivity = this;

    accessControlListsNameField = (EditText) findViewById(R.id.access_control_lists_check_permission_name_field);

    OnKeyListener keyListener = new OnKeyListener(){

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            if(keyCode == KeyEvent.KEYCODE_BACK){
                currentActivity.finish();
            }
            return false;
        }

    };

    accessControlListsNameField.setOnKeyListener(keyListener);


    button1 = (Button) findViewById(R.id.access_control_lists_check_permission_button1);
    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new checkPermission().execute();
        }
    });


//    listView = (ListView) findViewById(R.id.access_control_lists_check_permission_listView);
//
//    final ArrayList<String> loadingList = new ArrayList<String>();
//    loadingList.add("Loading, please wait...");
//    final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, loadingList);
//    listView.setAdapter(adapter);
//    new aclUsersTask().execute();
//    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
//            try{
//                if(users.length() > 0){
//                    userId = users.getJSONObject(position).getString("id");
//                    //checkPermission(userId);
//                    new checkPermission().execute();
//                }
//            }catch(JSONException e1){
//                Utils.handleException(e1, currentActivity);
//            }
//        }
//
//    });
}

private class aclUsersTask extends AsyncTask<Void, Void, JSONObject>{

    private SdkException exceptionThrown = null;
    JSONObject successResponse;
    HashMap<String, Object> data = new HashMap<String, Object>();
    ArrayList<String> objectsList = new ArrayList<String>();

    @Override
    protected JSONObject doInBackground(Void... voids){
        try{
            successResponse = new UsersAPI(SdkClient.getInstance()).usersQuery(null, null, null, null, null, null, null, null, null, 3, null);

        }catch(SdkException e){
            exceptionThrown = e;
            //handleSDKException(exceptionThrown, currentActivity);
        }

        return successResponse;
    }

    @Override
    protected void onPostExecute(JSONObject json){
        try{
            if(exceptionThrown == null && json.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")){
                try{
                    users = json.getJSONObject("response").getJSONArray("users");
                    for(int i = 0; i < users.length(); i++){
                        JSONObject user = users.getJSONObject(i);
                        objectsList.add(user.get("first_name") + " " + user.get("last_name"));
                    }
                    if(objectsList.size() <= 0){
                        objectsList.add("No Users!");
                    }
                }catch(JSONException e1){
                    Utils.handleException(e1, currentActivity);
                }
                final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList);
                listView.setAdapter(adapter);
            }else{
                handleSDKException(exceptionThrown, currentActivity);
            }

        }catch(JSONException e){
            handleException(e, currentActivity);
        }

    }

}

private class checkPermission extends AsyncTask<Void, Void, JSONObject>{

    private SdkException exceptionThrown = null;
    JSONObject successResponse;
    HashMap<String, Object> data = new HashMap<String, Object>();
    ArrayList<String> objectsList = new ArrayList<String>();

    @Override
    protected void onPreExecute(){
        if(accessControlListsNameField.getText().toString().length() <= 0){
            accessControlListsNameField.requestFocus();
            return;
        }

        // Create dictionary of parameters to be passed with the request
        //HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("name", accessControlListsNameField.getText().toString());
        String strduserId =  new Utils().getSharedPreferenceData(AccessControlListsCheckPermission.this);
        data.put("user_id", strduserId);

    }

    @Override
    protected JSONObject doInBackground(Void... voids){
        try{
            successResponse = new ACLsAPI(SdkClient.getInstance()).aCLsCheck(data.get("name").toString(), null, null,
                    data.get("user_id").toString());


        }catch(Exception e){
            e.printStackTrace();
            // handleSDKException(e, currentActivity);
        }
        return successResponse;
    }

    @Override
    protected void onPostExecute(JSONObject json) {


        if (exceptionThrown == null) {
            try {

                String strduserId = ((AxwayApplication)getApplication()).getUserId();

                if (json != null) {
                    new AlertDialog.Builder(currentActivity)
                            .setTitle("Success!").setMessage(json.getJSONObject("meta").toString())
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                } else {

                    String messageStr = (strduserId != null && strduserId.length()  == 0) ?  "Please Login to get data" : "No Data Found";

                    new AlertDialog.Builder(currentActivity)
                            .setTitle("Success!").setMessage(messageStr)
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            handleSDKException(exceptionThrown, currentActivity);
        }

    }
}


@Override
protected void onDestroy(){
    currentActivity = null;
    super.onDestroy();
}

}

/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.users;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersLogin extends Activity {
    private static UsersLogin currentActivity;
    private ArrayList<EditText> fields = new ArrayList<EditText>();
    HashMap<String, Object> data = new HashMap<String, Object>();
    private EditText usernameField;
    private EditText passwordField;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;

        usernameField = (EditText) findViewById(R.id.users_login_username_field);
        passwordField = (EditText) findViewById(R.id.users_login_password_field);


        button1 = (Button) findViewById(R.id.users_login_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        fields.add(usernameField);
        fields.add(passwordField);

        usernameField.setText("bahubhali");
        passwordField.setText("password");
    }

    @Override
    protected void onDestroy() {
        currentActivity = null;
        super.onDestroy();
    }

    private void submitForm() {
        for (EditText field : fields) {
            if (field.getText().toString().length() <= 0) {
                field.requestFocus();
                return;
            }
        }
        data.put("login", usernameField.getText().toString());
        data.put("password", passwordField.getText().toString());
        new apiTask(data).execute();

    }

    private class apiTask extends AsyncTask<Void, Void, JSONObject> {

        private HashMap<String, Object> map;

        private SdkException exceptionThrown =null;
        public apiTask(HashMap<String, Object> m) {
            this.map = m;
        }

        JSONObject successResponse;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new UsersAPI(SdkClient.getInstance()).usersLoginUser(
                        map.get("login").toString(),
                        map.get("password").toString());


            } catch (final SdkException e) {
                exceptionThrown = e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject xml) {
            try {
                if (exceptionThrown == null && xml.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
                    String userId = successResponse.getJSONObject("response").getJSONArray("users").getJSONObject(0).getString("id");
                    setResult(200);

                    new AlertDialog.Builder(currentActivity)
                            .setTitle("Success!").setMessage("Logged in! You are now logged in as " + userId)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();



                } else
                    handleSDKException(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }

        }
    }
}



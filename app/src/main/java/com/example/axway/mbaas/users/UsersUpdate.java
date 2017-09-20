/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class UsersUpdate extends Activity {
    private static UsersUpdate currentActivity;
    private ArrayList<EditText> fields = new ArrayList<EditText>();
    final HashMap<String, String> data = new HashMap<String, String>();

    private EditText usernameField;
    private EditText passwordField;
    private EditText passwordConfField;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private EditText tagsField;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_update);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;
        new AlertDialog.Builder(currentActivity)
                .setTitle("Did you Login?").setMessage("Please Login as a User before this Operation is performed.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        new callapi().execute();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent myintent = new Intent(getApplicationContext(), UsersLogin.class);
                        startActivity(myintent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
        usernameField = (EditText) findViewById(R.id.users_update_username_field);
        passwordField = (EditText) findViewById(R.id.users_update_password_field);
        passwordConfField = (EditText) findViewById(R.id.users_update_password_conf_field);
        firstNameField = (EditText) findViewById(R.id.users_update_first_name_field);
        lastNameField = (EditText) findViewById(R.id.users_update_last_name_field);
        emailField = (EditText) findViewById(R.id.users_update_email_field);
        tagsField = (EditText) findViewById(R.id.users_update_tags_field);


        button1 = (Button) findViewById(R.id.users_update_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (EditText field : fields) {
                    if (field.getText().toString().length() <= 0) {
                        field.requestFocus();
                        return;
                    }
                }
                if (!passwordField.getText().toString().equals(passwordConfField.getText().toString())) {
                    new AlertDialog.Builder(currentActivity)
                            .setTitle("Alert").setMessage("Passwords do not match!")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                } else
                    new apiTask().execute();
            }
        });

        // Required fields
        fields.add(usernameField);
        fields.add(firstNameField);
        fields.add(lastNameField);

    }


    @Override
    protected void onDestroy() {
        currentActivity = null;
        super.onDestroy();
    }

    private class callapi extends AsyncTask<Void, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(currentActivity);

        JSONObject successResponse;
        private SdkException exceptionThrown;

        @Override
        protected void onPreExecute() {

            this.dialog.setMessage("Please wait");
            this.dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new UsersAPI(SdkClient.getInstance()).usersShowMe(null, null);
            } catch (SdkException e) {
                exceptionThrown = e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject xml) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            JSONObject user;
            try {
                if (exceptionThrown == null && xml.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
                    user = successResponse.getJSONObject("response").getJSONArray("users").getJSONObject(0);

                    if (user.has("username")) {
                        usernameField.setText(user.getString("username"));
                    } else {
                        usernameField.setText("");
                    }
                    if (user.has("first_name")) {
                        firstNameField.setText(user.getString("first_name"));
                    }
                    if (user.has("last_name")) {
                        lastNameField.setText(user.getString("last_name"));
                    }
                    if (user.has("email")) {
                        emailField.setText(user.getString("email"));
                    }

                    if (user.has("tags")) {
                        JSONArray tags = user.getJSONArray("tags");
                        tagsField.setText(tags.join(",").replaceAll("\"", ""));
                    }
                } else
                    handleSDKException(exceptionThrown, currentActivity);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            button1.setVisibility(View.VISIBLE);
        }
    }

    private class apiTask extends AsyncTask<Void, Void, JSONObject> {


        private SdkException exceptionThrown = null;


        JSONObject successResponse;

        @Override
        protected void onPreExecute() {
            button1.setVisibility(View.GONE);

            // Create dictionary of parameters to be passed with the request
            //final HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("username", usernameField.getText().toString());
            data.put("password", null);
            data.put("password_confirmation", null);
            if (passwordField.getText().toString().length() > 0) {
                data.put("password", passwordField.getText().toString() == null ? "" : passwordField.getText().toString());
                data.put("password_confirmation", passwordConfField.getText().toString() == null ? "" : passwordConfField.getText().toString());
            }
            data.put("first_name", firstNameField.getText().toString() == null ? "" : firstNameField.getText().toString());
            data.put("last_name", lastNameField.getText().toString() == null ? "" : lastNameField.getText().toString());
            data.put("email", emailField.getText().toString() == null ? "" : emailField.getText().toString());
            data.put("tags", tagsField.getText().toString() == null ? "" : tagsField.getText().toString());

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new UsersAPI(SdkClient.getInstance()).usersUpdate(data.get("email"), data.get("username"), data.get("password"), data.get("password_confirmation"),
                        data.get("first_name"), data.get("last_name"), null, null, data.get("tags"), null, null, null, null, null);


            } catch (final SdkException e) {
                exceptionThrown = e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject xml) {
            try {
                if (exceptionThrown == null && xml.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
                    new AlertDialog.Builder(currentActivity)
                            .setTitle("Success").setMessage("Updated!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
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

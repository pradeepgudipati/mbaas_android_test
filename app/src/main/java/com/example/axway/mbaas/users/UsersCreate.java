/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.users;

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

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKExcpetion;

public class UsersCreate extends Activity{
private static UsersCreate currentActivity;
private ArrayList<EditText> fields = new ArrayList<EditText>();
JSONObject successResponse;
private EditText usernameField;
private EditText passwordField;
private EditText passwordConfField;
private EditText firstNameField;
private EditText lastNameField;
private EditText emailField;
private Button createUserButton;
private Button createDefaultUsersButton;
HashMap<String, Object> data = new HashMap<String, Object>();

@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.users_create);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    currentActivity = this;

    usernameField = (EditText) findViewById(R.id.users_create_username_field);
    passwordField = (EditText) findViewById(R.id.users_create_password_field);
    passwordConfField = (EditText) findViewById(R.id.users_create_password_conf_field);
    firstNameField = (EditText) findViewById(R.id.users_create_first_name_field);
    lastNameField = (EditText) findViewById(R.id.users_create_last_name_field);
    emailField = (EditText) findViewById(R.id.users_create_email_field);

    createUserButton = (Button) findViewById(R.id.users_create_button1);


    createUserButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            for(EditText field : fields){
                if(field.getText().toString().length() <= 0){
                    field.requestFocus();
                    return;
                }
            }
            if(!passwordField.getText().toString().equals(passwordConfField.getText().toString())){
                new AlertDialog.Builder(currentActivity)
                        .setTitle("Alert").setMessage("Passwords do not match!")
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return;
            }else
                new ApiTask().execute();
        }
    });

    createDefaultUsersButton = (Button) findViewById(R.id.users_create_default_users);
    createDefaultUsersButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            new ApiTask(true).execute();
        }
    });

    // Required fields
    fields.add(usernameField);
    fields.add(passwordField);
    fields.add(passwordConfField);
}

@Override
protected void onDestroy(){
    currentActivity = null;
    super.onDestroy();
}

private class ApiTask extends AsyncTask<Void, Void, JSONObject>{
    private boolean isMultUsers = false;

    public ApiTask(boolean isMultipleUsers){
        isMultUsers = isMultipleUsers;
    }

    public ApiTask(){
        this(false);
    }

    private SdkException exceptionThrown = null;


    JSONObject successResponse;

    @Override
    protected void onPreExecute(){

        if(!isMultUsers){


            createUserButton.setVisibility(View.GONE);

            // Create dictionary of parameters to be passed with the request
            //final HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("username", usernameField.getText().toString());
            data.put("password", passwordField.getText().toString());
            data.put("password_confirmation", passwordConfField.getText().toString());
            data.put("first_name", firstNameField.getText().toString());
            data.put("last_name", lastNameField.getText().toString());
            data.put("email", emailField.getText().toString());
        }else{
            createDefaultUsersButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected JSONObject doInBackground(Void... voids){
        try{
            if(!isMultUsers){
                successResponse = new UsersAPI(SdkClient.getInstance()).usersCreate(
                        data.get("email").toString(),
                        data.get("username").toString(),
                        data.get("password").toString(),
                        data.get("password_confirmation").toString(),
                        data.get("first_name").toString(),
                        data.get("last_name").toString(),
                        null, null, null, null, null, null, null, null, null, null, null);
            }else{
                UsersAPI usersApi = new UsersAPI(SdkClient.getInstance());
                final JSONObject result1 = usersApi.usersCreate("bahubhali@gmail.com", "bahubhali", "password", "password", "Bahubhali", "Innominds", null, null, null, null, null, null, null, null, null, null, true);
                final JSONObject result2 = usersApi.usersCreate("katappa@gmail.com", "katappa", "password", "password", "Katappa", "Innominds", null, null, null, null, null, null, null, null, null, null, true);
                final JSONObject result3 = usersApi.usersCreate("devasena@gmail.com", "devasena", "password", "password", "Devasena", "Innominds", null, null, null, null, null, null, null, null, null, null, true);
                final JSONObject result4 = usersApi.usersCreate("bhalladeva@gmail.com", "bhalladeva", "password", "password", "Bhalladeva", "Innominds", null, null, null, null, null, null, null, null, null, null, true);
                final JSONObject result5 = usersApi.usersCreate("avantika@gmail.com", "avantika", "password", "password", "Avantika", "Innominds", null, null, null, null, null, null, null, null, null, null, true);

                successResponse = new JSONObject();
                try{
                    successResponse.put("Bahubhali", result1);
                    successResponse.put("Katappa", result2);
                    successResponse.put("Devasena", result3);
                    successResponse.put("Bhalladeva", result4);
                    successResponse.put("Avantika", result5);
                }catch(JSONException e){
                    throw new SdkException(SdkException.EXCEPTION, e.getMessage());
                }
            }

        }catch(final SdkException e){
            exceptionThrown = e;
        }
        return successResponse;
    }

    @Override
    protected void onPostExecute(JSONObject xml){
        try{
            if(exceptionThrown == null && xml.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")){

                if(!isMultUsers){
                    new AlertDialog.Builder(currentActivity)
                            .setTitle("Success!").setMessage("User " + data.get("username").toString() + "Created!!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }else{
                    new AlertDialog.Builder(currentActivity)
                            .setTitle("Success!").setMessage("Users Bahubhali,Katappa, Devasena, Bhalladeva, Avantika Created!!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
            }else
                handleSDKExcpetion(exceptionThrown, currentActivity);
        }catch(JSONException e){
            handleException(e, currentActivity);
        }

    }
}
}

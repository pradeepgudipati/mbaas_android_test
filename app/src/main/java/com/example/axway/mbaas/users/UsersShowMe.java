/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.users;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.UsersAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class UsersShowMe extends Activity {
    private static UsersShowMe currentActivity;
    JSONObject successResponse;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_show_me);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentActivity = this;
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

            textView = (TextView) findViewById(R.id.users_show_me_text_view);
            textView.setMovementMethod(new ScrollingMovementMethod());
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
            try {
                if (exceptionThrown == null && successResponse.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
                    textView.setText(successResponse.toString(4));
                    Intent data = new Intent();
                    data.putExtra("userId",successResponse.getJSONObject("response").getJSONArray("users").getJSONObject(0).get("id").toString());
                    setResult(100,data);

                } else
                    handleSDKException(exceptionThrown, currentActivity);
            } catch (JSONException e) {
                handleException(e, currentActivity);
            }
        }
    }
}

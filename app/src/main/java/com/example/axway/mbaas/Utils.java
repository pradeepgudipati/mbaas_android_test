/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.axway.mbaas_preprod.JSONUtil;
import com.axway.mbaas_preprod.SdkException;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;


public class Utils {

    public static void handleErrorInResponse(final String response, final Activity activity) {
        if (activity == null) {
            return;
        }
        new AlertDialog.Builder(activity)
                .setTitle("Alert").setMessage(response)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void handleException(final Throwable e, final Activity activity) {
        e.printStackTrace();

        if (activity == null) {
            return;
        }
        new AlertDialog.Builder(activity)
                .setTitle("Exception").setMessage(e.getLocalizedMessage())
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void handleSDKException(final SdkException e, final Activity activity) {
        e.printStackTrace();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);
        String errMsg = e.getMessage();
        Log.d("SDK Error Message: ", errMsg);
        String eMessage = "";
        JSONObject responseJSON = null;
        try {
            if (!TextUtils.isEmpty(e.getResponseBody())) {
                eMessage = e.getResponseBody();
                eMessage = eMessage.replace("\n","");
                if(JSONUtil.isJSONValid(eMessage)){
                    responseJSON = JSONUtil.stringToJSONObject(eMessage);
                }
                eMessage = responseJSON.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        alertDialogBuilder.setTitle("Error!").setMessage(errMsg + "\n" + eMessage)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.finish();
                            }
                        })
                .show();

    }


    public void setData(Activity activity, String userId){

        SharedPreferences sharedPref = activity.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putString(getResources().getString(R.string.LoggedInUserId),userId);
        editor.putString("test",userId);
        editor.commit();

    }

    public String getSharedPreferenceData(Activity activity){
        SharedPreferences sharedPref = activity.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
//                    String lgduserId = getResources().getString(R.string.LoggedInUserId);
//                    String strduserId = sharedPref.getString(getResources().getString(R.string.LoggedInUserId),"");
        String strduserId = sharedPref.getString("test","");
        Log.i("UserId", strduserId);
        return strduserId;

    }


}

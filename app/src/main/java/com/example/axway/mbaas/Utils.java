/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.axway.mbaas_preprod.SdkException;


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

    public static void handleSDKExcpetion(final SdkException e, final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);
        String errMsg;
        if (e.getCause().getMessage() != null)
         errMsg = e.getCause().getMessage().toString();
        else
            errMsg =e.getCause().getCause().getMessage().toString();

        Log.d("SDK Error Message: ", errMsg);
        String eMessage = errMsg.substring(0, errMsg.indexOf("{"));
					alertDialogBuilder.setTitle("Error!").setMessage(eMessage)
							.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											activity.finish();
										}
									})
							.show();

    }


}

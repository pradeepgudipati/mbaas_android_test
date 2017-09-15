/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbsandroid;

import android.app.Activity;
import android.app.AlertDialog;


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
}

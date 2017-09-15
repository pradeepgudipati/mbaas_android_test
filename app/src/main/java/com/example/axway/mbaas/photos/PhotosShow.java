/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.photos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PhotosAPI;
import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKExcpetion;

public class PhotosShow extends Activity {
	private static PhotosShow currentActivity;
    HashMap<String, Object> data = new HashMap<String, Object>();

    private String photoId;
	
	private Button updateButton;
	private Button removeButton;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_show);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		updateButton = (Button) findViewById(R.id.photos_show_update_button1);
		updateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity, PhotosUpdate.class);
				intent.putExtra("photoId", photoId);
				startActivity(intent);
			}
		});
		
		removeButton = (Button) findViewById(R.id.photos_show_remove_button1);
		removeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity, PhotosRemove.class);
				intent.putExtra("photoId", photoId);
				startActivity(intent);
			}
		});

		textView = (TextView) findViewById(R.id.photos_show_text_view);
		// Make links clickable
		textView.setMovementMethod(LinkMovementMethod.getInstance());

		Intent intent = getIntent();
		photoId = intent.getStringExtra("photoId");
		
		// Create dictionary of parameters to be passed with the request

new apiTask().execute();
		
	}
    private class apiTask extends AsyncTask<Void, Void, JSONObject> {
        private SdkException exceptionThrown =null;

        JSONObject successResponse;

        @Override
        protected void onPreExecute() {
            data.put("photo_id", photoId);

        }
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                successResponse = new PhotosAPI(SdkClient.getInstance()).photosShow(data.get("photo_id").toString(),null,null,null);
            } catch (SdkException e) {
                exceptionThrown = e;
            }
            return successResponse;
        }

        @Override
        protected void onPostExecute(JSONObject xml) {
            try {
                if(exceptionThrown == null && xml.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok"))
                {
                    textView.setText(xml.getJSONObject("response").toString(4).replace("\\/", "/"));
                }
                else
                    handleSDKExcpetion(exceptionThrown,currentActivity);
            } catch (JSONException e) {
                handleException(e,currentActivity);
            }

        }
    }
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
	
}

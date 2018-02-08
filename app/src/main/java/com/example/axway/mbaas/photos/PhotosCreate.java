/**
 * Appcelerator Platform SDK
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.photos;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.SdkUtils;
import com.axway.mbaas_preprod.apis.PhotosAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.Utils;
import com.example.axway.mbaas.photoCollections.PhotoCollectionsSelectCollection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.example.axway.mbaas.Utils.handleException;
import static com.example.axway.mbaas.Utils.handleSDKException;

public class PhotosCreate extends Activity {
	private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 4;
	private static String TAG = "PhotosCreate";
	private static PhotosCreate currentActivity;
	
	private File photoFile;
	private String parentCollectionId = null;
	
	private ProgressBar progressBar;
	private Button galleryButton;
	private Button cameraButton;
	private Button chooseCollectionButton;
	private Button createButton;
	HashMap<String, Object> data = new HashMap<String, Object>();

	private static final int REQUEST_IMAGE_LOAD = 1; // Gallery
	private static final int REQUEST_IMAGE_CAPTURE = 2; // Camera
	private static final int REQUEST_PARENT_COLLECTION = 3; // Collection

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SdkUtils.verifyStoragePermissions(this);
		setContentView(R.layout.photos_create);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;
		
		progressBar = (ProgressBar) findViewById(R.id.photos_create_progressBar1);
		progressBar.setMax(100);
		progressBar.setProgress(0);

		/*ActivityCompat.requestPermissions(currentActivity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
				MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
*/
		galleryButton = (Button) findViewById(R.id.photos_create_gallery_button1);
		galleryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, REQUEST_IMAGE_LOAD);
			}
		});
		

		
		chooseCollectionButton = (Button) findViewById(R.id.photos_create_choose_collection_button3);
		chooseCollectionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(currentActivity, PhotoCollectionsSelectCollection.class);
				intent.putExtra("id", parentCollectionId);
				startActivityForResult(intent, REQUEST_PARENT_COLLECTION);
			}
		});
		
		createButton = (Button) findViewById(R.id.photos_create_create_button4);
		createButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submitForm();
			}
		});


	}

    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_IMAGE_LOAD && resultCode == RESULT_OK && null != data) {
			// Loaded image from Gallery
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			photoFile = new File(picturePath);
		}  else if (resultCode == RESULT_OK && requestCode == REQUEST_PARENT_COLLECTION) {
			// Parent collection selected
			parentCollectionId = data.getStringExtra("id");
		}
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
			imageFileName,	/* prefix */
			".jpg",			/* suffix */
			storageDir		/* directory */
		);


		return image;
	}
	
	private void submitForm() {

		new apiTask().execute();
	}

	private class apiTask extends AsyncTask<Void, Void, JSONObject> {
		private ProgressDialog dialog = new ProgressDialog(currentActivity);

		JSONObject successResponse;
		private SdkException exceptionThrown = null;

		@Override
		protected void onPreExecute() {
			if (photoFile == null) {
				new AlertDialog.Builder(currentActivity)
						.setTitle("Alert").setMessage("Please provide a photo!")
						.setPositiveButton(android.R.string.ok, null)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.show();
				return;
			}
			this.dialog.setMessage("Please wait");
			this.dialog.show();

			createButton.setVisibility(View.GONE);

			// Create dictionary of parameters to be passed with the request
			data.put("photo", photoFile);
			if (parentCollectionId != null) {
				data.put("collection_id", parentCollectionId);
			}
			data.put("photo_sync_sizes[]", "small_240");
		}
		@Override
		protected JSONObject doInBackground(Void... voids) {
			try {
				successResponse = new PhotosAPI(SdkClient.getInstance()).photosCreate((File) data.get("photo"), "pretty",null,parentCollectionId,null,null,null,null,null,null,null,null);
			} catch (SdkException e) {
				exceptionThrown =e;
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject xml) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			} try {
				if (exceptionThrown == null && successResponse.getJSONObject("meta").get("status").toString().equalsIgnoreCase("ok")) {
					new AlertDialog.Builder(currentActivity)
							.setTitle("Success").setMessage("Uploaded!")
							.setPositiveButton(android.R.string.ok, null)
							.setIcon(android.R.drawable.ic_dialog_info)
							.show();
				} else
					handleSDKException(exceptionThrown, currentActivity);
			} catch (JSONException e) {
				handleException(e, currentActivity);
			}
			createButton.setVisibility(View.VISIBLE);


		}

	}
}

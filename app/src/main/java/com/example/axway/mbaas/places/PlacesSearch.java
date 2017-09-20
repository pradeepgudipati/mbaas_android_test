/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.places;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkException;
import com.axway.mbaas_preprod.apis.PlacesAPI;
import com.example.axway.mbaas.R;
import com.example.axway.mbaas.StableArrayAdapter;
import com.example.axway.mbaas.Utils;
import com.example.axway.mbaas.getDeviceAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PlacesSearch extends Activity implements  LocationListener{
	private static PlacesSearch currentActivity;
	private JSONArray places;

	private ListView listView;
	Double latitude;
	Double longitude;
	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	boolean locationStatus = true;
	private Location location;

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_search);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		currentActivity = this;

		listView = (ListView) findViewById(R.id.places_search_list_view);

		final ArrayList<String> loadingList = new ArrayList<String>();
		loadingList.add("Loading...");
		Location getLoc = getLocation();

		if (getLoc != null) {
			latitude = getLoc.getLatitude();
			longitude = getLoc.getLongitude();
		}

		new findPlaces().execute();
		final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, loadingList);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				try {
					if (places.length() > 0) {
						String placeId = places.getJSONObject(position).getString("id");
						Intent intent = new Intent(currentActivity, PlacesShow.class);
						intent.putExtra("place_id", placeId);
						startActivity(intent);
					}
				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}
			}

		});
	}

	public Location getLocation() {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!isGPSEnabled && !isNetworkEnabled) {
			locationStatus = false;
		} else {
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			String locationProvider = locationManager.getBestProvider(criteria, true);
			if (locationProvider !=null) {
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                       int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
					//return TODO;
				}
				location = locationManager.getLastKnownLocation(locationProvider);
				if (location == null)
				{
					locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES,
							(LocationListener) this);
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
			} else
			{
				String DeviceIP = getDeviceAddress.getIPAddress(true);
				try {
					URL url = new URL("http://freegeoip.net/json/?q=" + DeviceIP);
					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
					JSONObject response = new JSONObject(urlConnection.getResponseMessage().toString());
					Log.d("IP Response ", response.toString());

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}


		 }
		return location;
		}
	@Override
	protected void onDestroy() {
		currentActivity = null;
		super.onDestroy();
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}


//		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//				ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//			// TODO: Consider calling
//			//    ActivityCompat#requestPermissions
//			// here to request the missing permissions, and then overriding
//			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//			//                                          int[] grantResults)
//			// to handle the case where the user grants the permission. See the documentation
//			// for ActivityCompat#requestPermissions for more details.
//			ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, PackageManager.PERMISSION_GRANTED );
//			return;
//		}
//
//		if (locationProvider != null) {
//
//
//			Location locationObject = locationManager.getLastKnownLocation(locationProvider);
//			if (locationObject == null) {
//				latitude = null;
//				longitude =null;
//				new findPlaces().execute();
//				final ArrayList<String> objectsList1 = new ArrayList<String>();
//				objectsList1.add("GPS lost, looking nearby...");
//
//				// Load listView rows
//				final StableArrayAdapter adapter1 = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList1);
//				listView.setAdapter(adapter1);
//			} else {
//				//findPlaces(String.valueOf(locationObject.getLatitude()), String.valueOf(locationObject.getLongitude()));
//				latitude = locationObject.getLatitude();
//				longitude = locationObject.getLongitude();
//				new findPlaces().execute();
//				final ArrayList<String> objectsList1 = new ArrayList<String>();
//				objectsList1.add("Located, looking nearby...");
//
//				// Load listView rows
//				final StableArrayAdapter adapter1 = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList1);
//				listView.setAdapter(adapter1);
//			}
//		} else
//			{
//		String DeviceIP = getDeviceAddress.getIPAddress(true);
//		try {
//			URL url = new URL("http://freegeoip.net/json/?q=" + DeviceIP);
//			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//			JSONObject response = new JSONObject(urlConnection.getResponseMessage().toString());
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	//}
//
//				Geocoder geocoder;
//				List<Address> addresses;
//				geocoder = new Geocoder(this, Locale.getDefault());
//
//				D latitude;
//				Object longitude;
//				addresses = geocoder.getFromLocation(latitude, longitude, 1);
//
//
//				APSClients.geolocate(null, new APSResponseHandler() {
//
//					@Override
//					public void onResponse(final APSResponse e) {
//						if (e.getSuccess()) {
//							try {
//								JSONObject location = e.getResponse().getJSONObject("location");
//								final ArrayList<String> objectsList2 = new ArrayList<String>();
//								objectsList2.add("Located, looking nearby...");
//
//								// Load listView rows
//								final StableArrayAdapter adapter2 = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList2);
//								listView.setAdapter(adapter2);
//								findPlaces(location.getString("latitude"), location.getString("longitude"));
//							} catch (JSONException e1) {
//								Utils.handleException(e1, currentActivity);
//							}
//						} else {
//							findPlaces(null, null);
//							final ArrayList<String> objectsList2 = new ArrayList<String>();
//							objectsList2.add("GPS lost, looking nearby...");
//
//							// Load listView rows
//							final StableArrayAdapter adapter2 = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList2);
//							listView.setAdapter(adapter2);
//						}
//					}
//
//					@Override
//					public void onException(APSCloudException e) {
//						Utils.handleException(e, currentActivity);
//					}
//
//				});
//			} catch (APSCloudException e1) {
//				Utils.handleException(e1, currentActivity);
////			}
//		}
//	}


	private class findPlaces extends AsyncTask<Void, Void, JSONObject> {
		HashMap<String, Object> data = new HashMap<String, Object>();

		private SdkException exceptionThrown = null;
		JSONObject successResponse;

		@Override
		protected void onPreExecute() {

			data.put("latitude", latitude);
			data.put("longitude", longitude);
		}

		@Override
		protected JSONObject doInBackground(Void... voids) {

			try {
				successResponse = new PlacesAPI(SdkClient.getInstance()).placesSearch(null,null,null,(Double) data.get("latitude"),
						(Double) data.get("longitude"),null,null,null);
			} catch (SdkException e) {
				exceptionThrown = e;
			}
			return successResponse;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (exceptionThrown == null) {
				try {
					places = json.getJSONObject("response").getJSONArray("places");
					final ArrayList<String> objectsList = new ArrayList<String>();
					for (int i = 0; i < places.length(); i++) {
						JSONObject place = places.getJSONObject(i);
						objectsList.add(place.getString("name"));
					}
					if (objectsList.size() <= 0) {
						objectsList.add("No Results!");
					}
					// Load listView rows
					final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList);
					listView.setAdapter(adapter);
				} catch (JSONException e1) {
					Utils.handleException(e1, currentActivity);
				}

			} else {
				Utils.handleSDKException(exceptionThrown, currentActivity);
			}
		}
	}
}
//	private void findPlaces(String latitude, String longitude) {
//
//		// Create dictionary of parameters to be passed with the request
//		HashMap<String, Object> data = new HashMap<String, Object>();
//		data.put("latitude", latitude);
//		data.put("longitude", longitude);
//
//		try {
//			APSPlaces.search(data, new APSResponseHandler() {
//
//				@Override
//				public void onResponse(final APSResponse e) {
//					if (e.getSuccess()) {
//						try {
//							places = e.getResponse().getJSONArray("places");
//							final ArrayList<String> objectsList = new ArrayList<String>();
//							for (int i = 0; i < places.length(); i++) {
//								JSONObject place = places.getJSONObject(i);
//								objectsList.add(place.getString("name"));
//							}
//							if (objectsList.size() <= 0) {
//								objectsList.add("No Results!");
//							}
//							// Load listView rows
//							final StableArrayAdapter adapter = new StableArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, objectsList);
//							listView.setAdapter(adapter);
//						} catch (JSONException e1) {
//							Utils.handleException(e1, currentActivity);
//						}
//					} else {
//						Utils.handleErrorInResponse(e, currentActivity);
//					}
//				}
//
//				@Override
//				public void onException(APSCloudException e) {
//					Utils.handleException(e, currentActivity);
//				}
//
//			});
//		} catch (APSCloudException e1) {
//			Utils.handleException(e1, currentActivity);
//		}
//	}



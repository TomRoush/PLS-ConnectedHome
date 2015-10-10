package com.birdbrain2.plus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentHome extends Fragment {
	View rootView;
	Activity activity;
	Context context;
	boolean warning;

	ListView listView;
	ArrayList<SensorListItem> listItems = new ArrayList<SensorListItem>();

	ListAdapter adapter;
	String sensorId;
	String groupId = "40fc636b-292f-400f-82d8-621bd307cb63";
	int counter = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);
		activity = getActivity();
		context = activity.getBaseContext();
		// TODO: dangerous work around
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// create a new notification
		/*
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(activity)
			    .setSmallIcon(android.R.drawable.stat_notify_error)
			    .setContentTitle("My notification")
			    .setContentText("Hello World!");

		int mNotificationId = 001;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = 
		        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		 */
		
		Bundle args = getArguments();
		warning = args.getBoolean("warning", false);

		listView = (ListView) rootView.findViewById(R.id.listView);
		adapter = new ListAdapter(activity, listItems);
		listView.setAdapter(adapter);
		refresh();
		return rootView;
	}

	SensorListItem useData(String groupIdd, String sensorIdd) {
		HttpClient httpClient = new DefaultHttpClient();
		String retval = null;
		try {

			// TEMPORARY
			//  Group ID
			/*
			HttpPost getGroupId6 = new HttpPost("https://a6.cfapps.io/groups");
			getGroupId6.addHeader("content-type", "application/json");
			getGroupId6.addHeader("Accept","application/json");
			HttpResponse response6 = httpClient.execute(getGroupId6);
			HttpEntity entity6 = response6.getEntity();
			groupId = parseGroupId(entity6.getContent());
			*/

			createNewSensor();


			// TEMPORARY
			/*
			// Send data to sensor
			String sendDataURL = "https://a6.cfapps.io/groups/" + groupId + "/sensors/" + sensorId + "/data";
			HttpPost sendData = new HttpPost(sendDataURL);
			StringEntity params2 =new StringEntity("{\"homeWindowOpen\" : \"" + counter +  "\"}");
			sendData.addHeader("content-type", "application/json");
			sendData.addHeader("Accept", "application/json");
			sendData.setEntity(params2);
			HttpResponse response13 = httpClient.execute(sendData);
			HttpEntity entity13 = response13.getEntity();
			*/


			// Request data from sensor 
			String requestDataURL = "http://as-hack-app-listener.cfapps.io/data";
			HttpGet requestsensordata = new HttpGet(requestDataURL);
			requestsensordata.addHeader("Accept", "application/json");
			HttpResponse response2 = httpClient.execute(requestsensordata);
			HttpEntity entity2 = response2.getEntity();
			//SensorListItem item = parseSensorData(entity2.getContent());
			parseSensorData(entity2.getContent());

			//SensorListItem listitem = new SensorListItem(sensorId, "timegg", s2.substring(0, s2.length()-1), s2.substring(s2.length()-1, s2.length()));
			counter++;
			if (counter == 2) counter = 0;
			return null;
		}catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	void createNewSensor() {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			// Sensor 1
			String requestSensorURL = "http://a6.cfapps.io/groups/" + groupId + "/sensors";
			HttpPost requestsensor = new HttpPost(requestSensorURL);
			StringEntity params12 = new StringEntity("{  \"ageRange\": \"20-30\",  \"gender\": \"m\",  \"sensorType\": \"car\", \"zipCode\": \"string\"}");
			requestsensor.addHeader("content-type", "application/json");
			requestsensor.addHeader("Accept", "application/json");
			requestsensor.setEntity(params12);
			HttpResponse response12 = httpClient.execute(requestsensor);
			HttpEntity entity8 = response12.getEntity();
			sensorId = parseSensorId(entity8.getContent());
		}catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private void parseSensorData(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

		String id = "";
		String time = "";
		String location = "";
		String triggered = "1";

		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			String name1 = reader.nextName();
			if (name1.equals("data")) {
				reader.beginObject();
				while (reader.hasNext()) {
					if (reader.nextName().equals("data")) {
						reader.beginObject();
						while (reader.hasNext()) {
							String name2 = reader.nextName();
							reader.beginObject();
							while (reader.hasNext()) {
								String type = reader.nextName();
								String value = reader.nextString();
								if (type.equals("type")) {
									id += value;
								} else if (type.equals("status")) {
									triggered = value;
								} else if (type.equals("location")) {
									location += value;
								} else if (type.equals("time")) {
									time += value;
								}
							}
						}
					} else {
						reader.skipValue();
					}
				}
			} else {
				reader.skipValue();
			}


			SensorListItem item = new SensorListItem(id, time, location, triggered);
			if(!warning || item.triggered()) {
				listItems.add(item);
				adapter.notifyDataSetChanged();
			}
		}
		reader.close();
	}


	private String parseSensorId(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("sensorId")) {
				sb.append(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.close();
		return sb.toString();
	}

	private String parseGroupId(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			String Id = reader.nextString();
			sb.append(Id);
		}
		reader.close();
		return sb.toString();
	}


	public void refresh() {
		if(adapter == null) return;
		listItems.clear();
		
		//for(int i = 0; i < 5; i++) {
			SensorListItem item = useData("", "");
			/*
			if(!warning || item.triggered()) {
				listItems.add(item);
				adapter.notifyDataSetChanged();
			}
			*/
		//}
	}
}

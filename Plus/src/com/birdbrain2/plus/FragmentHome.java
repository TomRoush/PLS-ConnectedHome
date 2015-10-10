package com.birdbrain2.plus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentHome extends Fragment {
	View rootView;
	Activity activity;
	Context context;

	ListView listView;
	ArrayList<SensorListItem> listItems = new ArrayList<SensorListItem>();

	ListAdapter adapter;

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

		listView = (ListView) rootView.findViewById(R.id.listView);
		adapter = new ListAdapter(activity, listItems);
		listView.setAdapter(adapter);
		refresh();
		return rootView;
	}

	String useData() {
		HttpClient httpClient = new DefaultHttpClient();
		String retval = null;
		try {
			HttpPost request = new HttpPost("http://a6.cfapps.io/groups/5cffd08c-08b2-4a5d-8c13-89ea6c11ee50/sensors");
			StringEntity params =new StringEntity("{  \"ageRange\": \"20-30\",  \"gender\": \"m\",  \"sensorType\": \"car\", \"zipCode\": \"string\"}");
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept","application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			retval = convertStreamToString(entity.getContent());
			// handle response here...
		}catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return retval;
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
	
	public void refresh() {
		listItems.clear();
		for(int i = 0; i < 5; i++) {
			String one = useData();
			String two = one.substring(73+18, 73+18*2);
			one = one.substring(73, 73+18);
			SensorListItem item = new SensorListItem(one, two);
			listItems.add(item);
			adapter.notifyDataSetChanged();

		}
	}
}

package com.birdbrain2.plus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHome extends Fragment {
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		// TODO: dangerous work around
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 

		useData();
		return rootView;
	}

	void useData() {
		TextView textView = (TextView) rootView.findViewById(R.id.text);
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost request = new HttpPost("http://a6.cfapps.io/groups/5cffd08c-08b2-4a5d-8c13-89ea6c11ee50/sensors");
			StringEntity params =new StringEntity("{  \"ageRange\": \"20-30\",  \"gender\": \"m\",  \"sensorType\": \"car\", \"zipCode\": \"string\"}");
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept","application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String s = convertStreamToString(entity.getContent());
			textView.setText(s);
			Log.e("Plus", s);
			// handle response here...
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
}

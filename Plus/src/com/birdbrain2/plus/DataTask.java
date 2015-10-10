package com.birdbrain2.plus;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;

public class DataTask extends AsyncTask<DataTaskParams, Void, HttpResponse> {	
	@Override
	protected HttpResponse doInBackground(DataTaskParams... params) {
		try {
			return params[0].client.execute(params[0].post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

package com.birdbrain2.plus;

import android.util.Log;

public class SensorListItem {
	public String id;
	public String time;
	public String status;
	public String triggered;
	public SensorListItem(String i, String t, String s, String trig) {
		id = i;
		time = t;
		status = s;
		triggered = trig;
	}
	
	public boolean triggered() {
		// returns whether this sensor is triggered
		Log.e("", triggered);
		return triggered.equals("1");
	}
}

package com.birdbrain2.plus;

public class SensorListItem {
	public String id;
	public String time;
	public String status;
	public SensorListItem(String i, String t, String s) {
		id = i;
		time = t;
		status = s;
	}
	
	public boolean triggered() {
		// returns whether this sensor is triggered
		return true;
	}
}

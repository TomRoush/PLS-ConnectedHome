package com.birdbrain2.plus;

public class SensorListItem {
	public String id;
	public String time;
	public String location;
	public String triggered;
	public SensorListItem(String i, String t, String s, String trig) {
		id = i;
		time = t;
		location = s;
		triggered = trig;
	}
	
	public boolean triggered() {
		// returns whether this sensor is triggered
		return triggered.equals("on");
	}
}

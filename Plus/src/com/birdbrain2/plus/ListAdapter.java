package com.birdbrain2.plus;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	ArrayList<SensorListItem> items;
	Context context;
	LayoutInflater inflater;
	
	public ListAdapter(Activity activity, ArrayList<SensorListItem> is) {
		items = is;
		context = activity.getBaseContext();
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = inflater.inflate(R.layout.list_item, null);
		
		TextView one = (TextView)itemView.findViewById(R.id.part1);
		TextView two = (TextView)itemView.findViewById(R.id.part2);
		
		one.setText(items.get(position).part1);
		two.setText(items.get(position).part2);
		
		itemView.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
    	    	SensorListItem item = new SensorListItem("Hello", "There");
    	    	items.add(item);
    	    	notifyDataSetChanged();
            }
        });
		
		return itemView;
	}
}



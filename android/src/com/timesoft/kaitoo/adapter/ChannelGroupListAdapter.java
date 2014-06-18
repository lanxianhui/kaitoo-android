package com.timesoft.kaitoo.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.timesoft.kaitoo.R;
import com.timesoft.kaitoo.activity.listchannel.ChannelGroupListActivity;
import com.timesoft.kaitoo.customized.listview.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelGroupListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public ChannelGroupListAdapter(Activity a,
			ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_column, null);

		TextView groupName = (TextView) vi.findViewById(R.id.groupName); // title
		ImageView groupImage = (ImageView) vi.findViewById(R.id.groupImage); // thumb
																				// image
		HashMap<String, String> group = new HashMap<String, String>();
		group = data.get(position);

		// Setting all values in listview
		groupName.setText(group.get(ChannelGroupListActivity.KEY_TITLE));
		// duration.setText(song.get(CustomizedListViewActivity.KEY_DURATION));
		imageLoader
				.DisplayImage(
						group.get(ChannelGroupListActivity.KEY_THUMB_URL),
						groupImage);
		return vi;
	}

}

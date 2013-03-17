package com.htwk.masterprojekt.hoerbuch;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htwk.masterprojekt.hoerbuch.media.ThumpImageLoader;

// generates the listrow for views with icons
public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
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
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row, null);
		TextView title = (TextView) vi.findViewById(R.id.title); // title
		TextView artist = (TextView) vi.findViewById(R.id.artist); // artistname
		TextView duration = (TextView) vi.findViewById(R.id.duration); // duration
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumbimage
		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position);
		// Setting all values in listview
		title.setText(song.get(FileBrowserActivity.KEY_TITLE));
		artist.setText(song.get(FileBrowserActivity.KEY_ARTIST));
		duration.setText(song.get(FileBrowserActivity.KEY_DURATION));
		thumb_image.setImageBitmap(new ThumpImageLoader(activity, song
				.get(FileBrowserActivity.KEY_THUMB_URL)).getImage());
		return vi;
	}
}
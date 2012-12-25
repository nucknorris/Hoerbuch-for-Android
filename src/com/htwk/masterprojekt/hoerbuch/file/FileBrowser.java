package com.htwk.masterprojekt.hoerbuch.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.htwk.masterprojekt.hoerbuch.PlayerActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class FileBrowser {
	private static File ROOTDIR = new File("/mnt/sdcard/Music");
	File[] files;
	private List<String> fileNames;
	private List<String> paths;
	public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
	public static final String EXTRA_FILE = "EXTRA_FILE";

	public static File getROOTDIR() {
		return ROOTDIR;
	}

	public static void setROOTDIR(File dir) {
		ROOTDIR = dir;
	}

	PopupWindow popUp;
	LinearLayout layout;
	LayoutParams params;
	Button but;
	boolean click = true;
	Context context;
	int width;
	int height;

	public FileBrowser(Context c) {
		this.context = c;

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		popUp = new PopupWindow(context);
		layout = new LinearLayout(context);

		listAll();

		params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		layout.setOrientation(LinearLayout.VERTICAL);

		ListView lv = new ListView(context);
		ArrayAdapter<String> fileList = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, fileNames);

		lv.setAdapter(fileList);
		// lv.setActivated(true);
		// lv.setClickable(true);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("SAY", "leClick");

				File file = new File(paths.get(arg2));
				Intent intent = new Intent(context, PlayerActivity.class);
				intent.putExtra(EXTRA_FILE_PATH, file.getPath());
				intent.putExtra(EXTRA_FILE, file.getName());
				context.startActivity(intent);

			}

		});

		layout.addView(lv);
		popUp.setContentView(layout);

	}

	private void listAll() {

		files = ROOTDIR.listFiles();
		fileNames = new ArrayList<String>();
		paths = new ArrayList<String>();

		for (File file : files) {
			Log.d(file.getAbsolutePath(), file.getName());
			fileNames.add(getMP3Meta(file.getAbsolutePath()));
			paths.add(file.getAbsolutePath());
		}
	}

	public void show(LinearLayout layout) {

		popUp.showAtLocation(layout, Gravity.TOP, 30, 30);
		popUp.update(width - 30, height - 30);
		popUp.setTouchable(true);

	}

	public void hide() {
		popUp.dismiss();
	}

	private String getMP3Meta(String filePath) {
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(filePath);

		String titleName = mmr
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

		StringBuilder sb = new StringBuilder();
		sb.append(titleName);

		return sb.toString();
	}
}

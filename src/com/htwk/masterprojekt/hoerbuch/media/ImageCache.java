package com.htwk.masterprojekt.hoerbuch.media;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.R;
import com.htwk.masterprojekt.hoerbuch.Utils;

public class ImageCache {

	Context c;
	HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();
	private static final String TAG = "ImageCache";

	public ImageCache(Context context) {
		this.c = context;
		cache.put("dir", BitmapFactory.decodeResource(context.getResources(),
				R.drawable.folder));
		cache.put("def", BitmapFactory.decodeResource(context.getResources(),
				R.drawable.def));
		Log.d(TAG, "init done");
	}

	public Bitmap getImage(String path) {
		Bitmap bmp = null;
		Log.d(TAG, Utils.lsplit("/", path));
		bmp = cache.get(Utils.lsplit("/", path));
		if (bmp == null) {
			bmp = createImage(path);
		}
		return bmp;
	}

	public Bitmap createImage(String path) {
		Bitmap bmp = null;
		try {
			bmp = new ThumpImageLoader(c, path).getImage();
		} catch (Exception ex) {
			bmp = BitmapFactory
					.decodeResource(c.getResources(), R.drawable.def);
		}
		cache.put(Utils.lsplit("/", path), bmp);
		return bmp;
	}
}

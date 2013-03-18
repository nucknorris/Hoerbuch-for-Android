package com.htwk.masterprojekt.hoerbuch.media;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.R;
import com.htwk.masterprojekt.hoerbuch.Utils;

// image cache to speed the thinks up a lil bit ;)
public class ImageCache {

	Context c;
	// create the cache but is is actually a hashmap :D
	HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();
	private static final String TAG = "ImageCache";
	Bitmap defaultBmp;

	public ImageCache(Context context) {
		this.c = context;
		// some default items
		cache.put("dir", BitmapFactory.decodeResource(context.getResources(),
				R.drawable.folder));
		cache.put("def", defaultBmp);
		defaultBmp = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.def);
	}

	// get a image from the hashmap
	public Bitmap getImage(String path) {
		Bitmap bmp = null;
		// just for the fans
		bmp = cache.get(Utils.lsplit("/", path));
		if (bmp == null) {
			Log.d(TAG, path);
			// is there no iamge in cache create one
			bmp = createImage(path);
		}
		return bmp;
	}

	// create a image and add it to the hashmap
	public Bitmap createImage(String path) {
		Bitmap bmp = null;
		try {
			bmp = new ThumpImageLoader(c, path).getImage();
		} catch (Exception ex) {
			// if no image in mp3 then take the default bitmap
			bmp = defaultBmp;
		}
		// add to hash
		cache.put(Utils.lsplit("/", path), bmp);
		return bmp;
	}
}

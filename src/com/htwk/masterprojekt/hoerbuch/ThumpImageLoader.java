package com.htwk.masterprojekt.hoerbuch;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;

public class ThumpImageLoader {

	Bitmap bmp = null;
	Context context;

	// default constructor to load a image from a media file
	public ThumpImageLoader(Context context, String keyThumbUrl) {
		this.context = context;
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		Log.d("ThumpImageLoader", "FILE: " + keyThumbUrl);

		if (new File(keyThumbUrl).isFile()) {
			Log.d("ThumpImageLoader", "FILE");
			mmr.setDataSource(keyThumbUrl);
			byte[] cover_array = mmr.getEmbeddedPicture();
			// bmp = BitmapFactory.decodeByteArray(cover_array, 0,
			// cover_array.length);
			bmp = decode(cover_array);
		} else {
			Log.d("ThumpImageLoader", "DIR");
			bmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_launcher);
		}

	}

	// return the bitmap
	public Bitmap getImage() {
		return bmp;
	}

	// decode and resize the image from a mediafile
	private Bitmap decode(byte[] cover_array) {
		// decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(cover_array, 0, cover_array.length);

		// Find the correct scale value. It should be the power of 2.
		final int REQUIRED_SIZE = 70;
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory
				.decodeByteArray(cover_array, 0, cover_array.length);
	}
}

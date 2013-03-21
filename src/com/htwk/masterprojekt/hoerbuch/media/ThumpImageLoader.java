package com.htwk.masterprojekt.hoerbuch.media;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

import com.htwk.masterprojekt.hoerbuch.R;

public class ThumpImageLoader {

	Bitmap bmp = null;
	Context context;

	// default constructor to load a image from a media file
	public ThumpImageLoader(Context context, String keyThumbUrl) {
		this.context = context;
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		if (new File(keyThumbUrl).isFile()) {
			mmr.setDataSource(keyThumbUrl);
			byte[] cover_array = mmr.getEmbeddedPicture();
			bmp = decode(cover_array);
		} else {
			bmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.folder);
		}

	}

	// return the bitmap
	public Bitmap getImage() {
		return bmp;
	}

	// decode and resize the image from a mediafile
	private Bitmap decode(byte[] cover_array) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		Bitmap originalBitmap = BitmapFactory.decodeByteArray(cover_array, 0,
				cover_array.length);
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 70,
				70, false);
		return resizedBitmap;
	}
}

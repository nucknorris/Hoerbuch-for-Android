package com.htwk.masterprojekt.hoerbuch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.htwk.masterprojekt.hoerbuch.db.test.DatabaseTest;
import com.htwk.masterprojekt.hoerbuch.file.FileBrowser;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	LinearLayout mainLayout;
	boolean click = true;
	FileBrowser fb;
	LayoutParams params;
	Button but;
	Button but2;
	Context c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c = this;
		fb = new FileBrowser(c);
		mainLayout = new LinearLayout(this);

		but = new Button(this);
		but.setText("FileBrowser");
		but.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d("SAY", "leClick");
				if (click) {
					Log.d("SAY", "SHOW");
					fb.show(mainLayout);

					click = false;
				} else {
					Log.d("SAY", "HIDE");
					fb.hide();
					click = true;
				}
			}
		});
		but2 = new Button(this);
		but2.setText("DataBase");
		but2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d("SAY", "leClick");
				// sqlite test
				new DatabaseTest(c);
			}
		});

		params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mainLayout.addView(but, params);
		mainLayout.addView(but2, params);
		setContentView(mainLayout);
	}
}

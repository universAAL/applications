package org.universAAL.AALapplication.hwo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class StartupActivity extends Activity implements OnTouchListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		Button buttonStart = (Button) findViewById(R.id.start);
		buttonStart.setOnTouchListener(this);
		Button buttonStop = (Button) findViewById(R.id.stop);
		buttonStop.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.startup, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			switch (v.getId()) {
			case R.id.start:
				Intent startServiceIntent = new Intent(this, BackgroundService.class);
				this.startService(startServiceIntent);
				Button buttonStart = (Button) findViewById(R.id.start);
				buttonStart.setVisibility(View.GONE);
				Button buttonStop = (Button) findViewById(R.id.stop);
				buttonStop.setVisibility(View.VISIBLE);
				break;
			case R.id.stop:
				Intent stopServiceIntent = new Intent(this, BackgroundService.class);
				this.stopService(stopServiceIntent);
				Button buttonStop2 = (Button) findViewById(R.id.stop);
				buttonStop2.setVisibility(View.GONE);
				Button buttonStart1 = (Button) findViewById(R.id.start);
				buttonStart1.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
		return false;
	}

}

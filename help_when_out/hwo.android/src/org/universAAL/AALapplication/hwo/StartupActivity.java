package org.universAAL.AALapplication.hwo;

import java.util.List;

import org.universAAL.AALapplication.hwo.engine.POI;
import org.universAAL.AALapplication.hwo.engine.SCallee;
import org.universAAL.AALapplication.hwo.engine.contacts.ContactScreen;

import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class StartupActivity extends ListActivity implements OnTouchListener{

	private ArrayAdapter<String> m_adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		Button buttonStart = (Button) findViewById(R.id.start);
		buttonStart.setOnTouchListener(this);
		Button buttonStop = (Button) findViewById(R.id.stop);
		buttonStop.setOnTouchListener(this);
		Button buttonPanic = (Button) findViewById(R.id.panic);
		buttonPanic.setOnTouchListener(this);
		m_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,SCallee.GetPOIStrings());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(m_adapter!=null){
			setListAdapter(m_adapter);
		}
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
				Button buttonPanic = (Button) findViewById(R.id.panic);
				buttonPanic.setEnabled(true);
				break;
			case R.id.stop:
				Intent stopServiceIntent = new Intent(this, BackgroundService.class);
				this.stopService(stopServiceIntent);
				Button buttonStop2 = (Button) findViewById(R.id.stop);
				buttonStop2.setVisibility(View.GONE);
				Button buttonStart1 = (Button) findViewById(R.id.start);
				buttonStart1.setVisibility(View.VISIBLE);
				Button buttonPanic1 = (Button) findViewById(R.id.panic);
				buttonPanic1.setEnabled(false);
				break;
			case R.id.panic:
				//SCallee.Panic(null);
				//TODO: In fact, we are usint this button to know the distance between our position and home.
				Intent panicServiceIntent = new Intent(this, BackgroundService.class);
				this.startService(panicServiceIntent);
				break;
			default:
				break;
			}
		}
		return false;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(getApplicationContext(), ContactScreen.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String poiname=(String) getListView().getItemAtPosition(position);
		List<POI> pois = SCallee.GetPOIs();
		for(POI poi:pois){
			if(poi.getName().equals(poiname)){
//				SCallee.GuideTo(poi.getCoordinate());
				Intent i = new Intent(Intent.ACTION_VIEW,
						Uri.parse("google.navigation:q=" + poi.getCoordinate().replace(" ", "+")
								+ "&mode=w"));
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try{
					startActivity(i);
				}catch(ActivityNotFoundException ex){
					Toast.makeText(this, "Google Maps is not installed!", Toast.LENGTH_LONG).show();
				}
				
			}
		}
	}
}

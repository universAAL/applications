package org.universAAL.AALapplication.hwo.engine.contacts;

import org.universAAL.AALapplication.hwo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ContactScreen extends Activity{

	private Button mAddContactButton;
	private ListView mContactList;
	private DBManager mDBmgmt;
	private Cursor mCursor;
	private Long mAuxRemoveId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		
		mAddContactButton = (Button) findViewById(R.id.addContactButton);
        mContactList = (ListView) findViewById(R.id.contactList);
        
        mDBmgmt = new DBManager(this);
        mDBmgmt.open();
        
		mContactList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long id) {
				//Save ID to remove (cannot access otherwise from dialog)
				mAuxRemoveId=id;
				showDialog(R.string.ref_confirmdialog);
			}	
		});
        
        mAddContactButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Start system activity to pick phone contacts
				Intent intent = new Intent(Intent.ACTION_PICK); 
				intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); 
				startActivityForResult(intent, R.string.ref_pickintent);
			}
		});
        
        //Fill the list at first
        updateList();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//Safe close the DB. (needed at all?)
		mDBmgmt.close();
	}

	public void updateList(){
		//re-fill the list (updates the cursor as well)
		mCursor = mDBmgmt.getAllSMSRecipients();
        startManagingCursor(mCursor);
        mContactList.setAdapter(new CustomContactAdapter(this));
        Cursor c=mCursor;
        //Change hint depending on list empty
        TextView tv = (TextView) findViewById(R.id.contactHint);
        if(c.getCount()<1){
        	tv.setText(R.string.layout_contactHintEmpty);
        }else{
        	tv.setText(R.string.layout_contactHint);
        }
	}
	
	//the confirm dialog is generated here
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		//Confirm that you want to delete the item
		case R.string.ref_confirmdialog:
			builder.setMessage(R.string.layout_confirmdialog);
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.layout_yes,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if(mAuxRemoveId!=null){
						mDBmgmt.deleteSMSRecipient(mAuxRemoveId);
						//refill the list when a contact is removed
						updateList();
						mAuxRemoveId=null;
					}
				}
			});
			builder.setNegativeButton(R.string.layout_no, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int buttonid) {
							dialog.cancel();
						}
					});
			dialog = builder.create();

			break;
		//The selected contact was already there. Confirm if you want to pick another
		case R.string.ref_presentdialog:
			builder.setMessage(R.string.layout_presentdialog);
			builder.setCancelable(true);
			builder.setPositiveButton(R.string.layout_yes,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//pick another because it was already present
					Intent intent = new Intent(Intent.ACTION_PICK); 
					intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); 
					startActivityForResult(intent, R.string.ref_pickintent);
				}
			});
			builder.setNegativeButton(R.string.layout_no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int buttonid) {
					dialog.cancel();
				}
			});
			dialog = builder.create();
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		//Get the contact from the system pick contact activity
		case (R.string.ref_pickintent):
			if (resultCode == Activity.RESULT_OK) {
				Uri uri = data.getData();
				if(mDBmgmt.getSMSRecipientByURI(uri.toString()).getCount()<1){
					//add if it wasn´t added
					mDBmgmt.addSMSRecipient(uri.toString());
				}else{
					//warn because it was added before
					showDialog(R.string.ref_presentdialog);//TODO filter from selection those already added
				}
			}
			Toast.makeText(this, "Could not add that user", Toast.LENGTH_LONG);//TODO: if RESULTNOOK? Nothing? i18n
			break;
		}
		// refill the list once a contact is added
		updateList();
	}
	
	//Custom list adpater for the list view,
	//since I couldn´t find a way to show the two_line_list_item list from system
	private class CustomContactAdapter extends BaseAdapter {

		private Context mContext;

		public CustomContactAdapter(Context context){
			this.mContext=context;
		}
		
		@Override
		public int getCount() {
			Cursor c=mCursor;
			return c.getCount();
		}

		@Override
		public Object getItem(int position) {
			Cursor c=mCursor;
			if(c.moveToPosition(position)){
				return (c.getString(c.getColumnIndexOrThrow(DBManager.KEY_URI)));
			}else
				return "";
		}

		@Override
		public long getItemId(int position) {
			Cursor c=mCursor;
			if(c.moveToPosition(position)){
				return (c.getLong(c.getColumnIndexOrThrow(DBManager.KEY_ROWID)));
			}else
			return -1;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//each item has the system view of big line up and below a small line
			LinearLayout item=(LinearLayout) LayoutInflater.from(mContext).inflate(android.R.layout.two_line_list_item, parent,false);
			//we know the text position on advance, because i can´t use TwoLineLayout (or whatever), which provides getText1 & 2
			TextView text1=(TextView)item.getChildAt(0);
			TextView text2=(TextView)item.getChildAt(1);
			//the system uri representing the contact at this position
			String uri = (String)getItem(position);
			Uri contactData = Uri.parse(uri); 
			//query the system DB for that contact info
		    Cursor c =  managedQuery(contactData, null, null, null, null); 
		    if (c.moveToFirst()) { 
			    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)); 
			    String number = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
			    text1.setText(name);
			    text2.setText(number);
		    } 
			return item;
		}
		
	}

}

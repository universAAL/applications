package org.universAAL.AALapplication.hwo.engine.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {

	    public static final String KEY_URI = "recipienturi";
	    public static final String KEY_ROWID = "_id";
	    private static final String DB_NAME = "data";
	    private static final String DB_TABLE = "sms";
	    private static final int DB_VERSION = 2;

	    private LocalDatabaseOpenHelper mOpenHelper;
	    private SQLiteDatabase mDB;
	    private final Context mContext;

	    public DBManager(Context ctx) {
	        this.mContext = ctx;
	    }

	    public DBManager open() throws SQLException {
	        mOpenHelper = new LocalDatabaseOpenHelper(mContext);
	        mDB = mOpenHelper.getWritableDatabase();
	        return this;
	    }

	    public void close() {
	        mOpenHelper.close();
	    }

	    public long addSMSRecipient(String uri) {
	        ContentValues val = new ContentValues();
	        val.put(KEY_URI, uri);
	        return mDB.insert(DB_TABLE, null, val);
	    }

	    public boolean deleteSMSRecipient(long id) {
	        return mDB.delete(DB_TABLE, KEY_ROWID + "=" + id, null) > 0;
	    }

	    public Cursor getAllSMSRecipients() {
	        return mDB.query(DB_TABLE, new String[] {KEY_ROWID, KEY_URI}, null, null, null, null, null);
	    }

	    public Cursor getSMSRecipient(long id) throws SQLException {
	        Cursor mCursor = mDB.query(true, DB_TABLE, new String[] {KEY_ROWID,
	                    KEY_URI}, KEY_ROWID + "=" + id, null,
	                    null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }
	    
	    public Cursor getSMSRecipientByURI(String uri) throws SQLException {
	        Cursor mCursor = mDB.query(true, DB_TABLE, new String[] {KEY_ROWID,
	                    KEY_URI}, KEY_URI + " LIKE '" + uri + "'", null,
	                    null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }


	    private static class LocalDatabaseOpenHelper extends SQLiteOpenHelper {

	        LocalDatabaseOpenHelper(Context context) {
	            super(context, DB_NAME, null, DB_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) {
	            db.execSQL("create table "+DB_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement, "
	        	        + ""+KEY_URI+" text not null);");
	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            //Upgrading database from version oldVersion to newVersion, which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
	            onCreate(db);
	        }
	    }

}

package com.example.juicekaaa.fireserver.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.juicekaaa.fireserver.MyApplication;

import java.io.File;


public class DBHelpers extends SQLiteOpenHelper {
	private static DBHelpers instance;
	public final static int DATABASEVERSION = 1;

	private static Context mContext;

	public static final String mDbName = SDBHelper.DB_DIRS + File.separator + "config.db";

	public DBHelpers(Context context) {
		super(context, mDbName, null, DATABASEVERSION);
		mContext = context;
	}

	public static DBHelpers getInstance(Context context) {
		mContext = context;
		if (instance == null) {
			instance = new DBHelpers(MyApplication.instance);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);
		return cursor;
	}

	public int update(String epc, String staus) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues updatedValues = new ContentValues();
		updatedValues.put("Staus", staus);
		String where = "Epc=" +"'" + epc + "'";
		return db.update("materialtable", updatedValues, where, null);
	}
}

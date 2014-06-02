package com.mickhearne.irishbirds.birds.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BirdsDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "BirdsFragment";
	
	// set db name and version
	private static final String DATABASE_NAME = "BirdNote.db";
	private static final int DATABASE_VERSION = 5;
	
	// set birds table name and columns
	public static final String TABLE_BIRDS = "birds";
	public static final String BIRDS_COLUMN_ID = "bird_id";
	public static final String BIRDS_COLUMN_NAME = "name";
	public static final String BIRDS_COLUMN_LATIN_NAME = "latin_name";
	public static final String BIRDS_COLUMN_STATUS = "status";
	public static final String BIRDS_COLUMN_IDENTIFICATION = "identification";
	public static final String BIRDS_COLUMN_DIET = "diet";
	public static final String BIRDS_COLUMN_BREEDING = "breeding";
	public static final String BIRDS_COLUMN_WINTERING_HABITS = "wintering_habits";
	public static final String BIRDS_COLUMN_WHERE_TO_SEE = "where_to_see";
	public static final String BIRDS_COLUMN_CONSERVATION = "conservation_status";
	public static final String BIRDS_COLUMN_IMAGE_THUMB = "image_thumb";
	public static final String BIRDS_COLUMN_IMAGE_LARGE = "image_large";
	
	// create birds table
	private static final String TABLE_CREATE_BIRDS = 
			"CREATE TABLE " + TABLE_BIRDS + " (" +
					BIRDS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					BIRDS_COLUMN_NAME + " TEXT, " +
					BIRDS_COLUMN_LATIN_NAME + " TEXT, " +
					BIRDS_COLUMN_STATUS + " TEXT, " +
					BIRDS_COLUMN_IDENTIFICATION + " TEXT, " +
					BIRDS_COLUMN_DIET + " TEXT, " +
					BIRDS_COLUMN_BREEDING + " TEXT, " +
					BIRDS_COLUMN_WINTERING_HABITS + " TEXT, " +
					BIRDS_COLUMN_WHERE_TO_SEE + " TEXT, " +
					BIRDS_COLUMN_CONSERVATION + " TEXT, " +
					BIRDS_COLUMN_IMAGE_THUMB + " TEXT, " +
					BIRDS_COLUMN_IMAGE_LARGE + " TEXT " +
					")";


	// birds seen table name and columns
	public static final String TABLE_BIRDS_SEEN = "birdsSeen";
	public static final String BIRDS_COLUMN_LATITUDE = "latitude";
	public static final String BIRDS_COLUMN_LONGITUDE = "longitude";
	private static final String TABLE_CREATE_BIRDS_SEEN =
			"CREATE TABLE " + TABLE_BIRDS_SEEN + " (" +
					BIRDS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
					BIRDS_COLUMN_LATITUDE + " REAL, " +
					BIRDS_COLUMN_LONGITUDE + " REAL " +
					")";


	// wishlist table name and columns
	public static final String TABLE_BIRDS_WISHLIST = "wishList";
    private static final String TABLE_CREATE_BIRDS_WISHLIST =
			"CREATE TABLE " + TABLE_BIRDS_WISHLIST + " (" +
					BIRDS_COLUMN_ID + " INTEGER PRIMARY KEY)";


	public BirdsDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	

	// create tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE_BIRDS);
		db.execSQL(TABLE_CREATE_BIRDS_SEEN);
		db.execSQL(TABLE_CREATE_BIRDS_WISHLIST);
		Log.i(LOGTAG, "BirdsFragment Table created!!");
		Log.i(LOGTAG, "BirdsFragment Seen Table created!!");
		Log.i(LOGTAG, "Wishlist Table created!!");
	}


	// never to be called explicitly. Only on version updates
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRDS_SEEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRDS_WISHLIST);
		onCreate(db);
	}
}

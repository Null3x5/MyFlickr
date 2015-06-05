package com.detroitteatime.myflickr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by mark on 5/7/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    private String[] projection = {
            Contract.PhotoEntry._ID,
            Contract.PhotoEntry.DAY,
            Contract.PhotoEntry.MIN,
            Contract.PhotoEntry.MAX,
            Contract.PhotoEntry.MORN,
            Contract.PhotoEntry.NIGHT,
            Contract.PhotoEntry.EVE,
            Contract.PhotoEntry.WTF,
            Contract.PhotoEntry.DESCRIPTION,};

    private static final String DATABASE_CREATE =
            "CREATE TABLE " +
                    Contract.PhotoEntry.TABLE_NAME + " (" +
                    Contract.PhotoEntry._ID + " INTEGER PRIMARY KEY, " +
                    Contract.PhotoEntry.DAY + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.MIN + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.MAX + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.MORN + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.NIGHT + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.EVE + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.WTF + " TEXT NOT NULL, " +
                    Contract.PhotoEntry.DESCRIPTION + " TEXT NOT NULL " + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.PhotoEntry.TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(Constants.TAG, "Create table command: " + DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void insertPhotoEntry(FlickrPhoto photo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
//    private String id,day,min,max,night,eve,morn,wtf,description;

        cv.put(Contract.PhotoEntry._ID, photo.getId());
        cv.put(Contract.PhotoEntry.DAY, photo.getDay());
        cv.put(Contract.PhotoEntry.MIN, photo.getMin());
        cv.put(Contract.PhotoEntry.MAX, photo.getMax());
        cv.put(Contract.PhotoEntry.MORN, photo.getMorn());
        cv.put(Contract.PhotoEntry.NIGHT, photo.getNight());
        cv.put(Contract.PhotoEntry.EVE, photo.getEve());
        cv.put(Contract.PhotoEntry.WTF, photo.getWtf());
        cv.put(Contract.PhotoEntry.DESCRIPTION, photo.getDescription());

        db.insert(Contract.PhotoEntry.TABLE_NAME, null, cv);
    }

    public Cursor getAllRows() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Contract.PhotoEntry.TABLE_NAME, projection, null, null, null, null, null);

//        Here's the method with arguments:
//        public Cursor query (String table, String[] columns, String selection, String[]
//        selectionArgs, String groupBy, String orderBy, String limit)

    }

    public Cursor getRowByID(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] ids = {String.valueOf(id)};
        return db.query(Contract.PhotoEntry.TABLE_NAME, projection, Contract.PhotoEntry._ID + "==?", ids, null, null, null);
    }

    public void deleteRow(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] ids = {String.valueOf(id)};
        db.delete(Contract.PhotoEntry.TABLE_NAME, Contract.PhotoEntry._ID + "==?", ids);
    }

    public void addRows(List<FlickrPhoto> photos) {
        for (FlickrPhoto photo : photos) {
            insertPhotoEntry(photo);
        }
    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + Contract.PhotoEntry.TABLE_NAME);
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
    }


}

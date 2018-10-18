package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Database.model.Tracker;

/**
 * This Class Holds the database related methods to perform the crud operations.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "expense_db";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate() will be called only once when the app is installed.
     * In this method, we execute the create table sql statements to create necessary tables.
     */
    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(Tracker.CREATE_TABLE);
    }

    /**
     * onUpgrade() called when an update is released.
     */
    //Upgrading Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Tracker.TABLE_NAME);

        // Create tables again
        onCreate(db);

    }

    public long insertTracker(int amount, String purpose, String date, String description, String method) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Tracker.COLUMN_AMOUNT, amount);
        values.put(Tracker.COLUMN_PURPOSE, purpose);
        values.put(Tracker.COLUMN_DATE, date);
        values.put(Tracker.COLUMN_DESCRIPTION, description);
        values.put(Tracker.COLUMN_METHOD, method);

        // insert row
        long id = db.insert(Tracker.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Tracker getTracker(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection={
                Tracker.COLUMN_ID,
                Tracker.COLUMN_AMOUNT,
                Tracker.COLUMN_PURPOSE,
                Tracker.COLUMN_DATE,
                Tracker.COLUMN_DESCRIPTION,
                Tracker.COLUMN_METHOD,
                Tracker.COLUMN_TIMESTAMP
        };

        String selection = Tracker.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                Tracker.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if (cursor != null)
            cursor.moveToFirst();

        // prepare record object
        Tracker record = new Tracker(
                cursor.getInt(cursor.getColumnIndex(Tracker.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Tracker.COLUMN_AMOUNT)),
                cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_PURPOSE)),
                cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_METHOD)),
                cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return record;
    }

    public List<Tracker> getAllRecords() {
        List<Tracker> records = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Tracker.TABLE_NAME + " ORDER BY " + Tracker.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tracker record = new Tracker();
                record.sId(cursor.getInt(cursor.getColumnIndex(Tracker.COLUMN_ID)));
                record.sAmount(cursor.getInt(cursor.getColumnIndex(Tracker.COLUMN_AMOUNT)));
                record.sPurpose(cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_PURPOSE)));
                record.sDate(cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_DATE)));
                record.sDescription(cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_DESCRIPTION)));
                record.sMethod(cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_METHOD)));
                record.sTimestamp(cursor.getString(cursor.getColumnIndex(Tracker.COLUMN_TIMESTAMP)));

                records.add(record);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return records;
    }

    public int getRecordsCount() {
        String countQuery = "SELECT  * FROM " + Tracker.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateRecord(Tracker record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Tracker.COLUMN_AMOUNT, record.gAmount());
        values.put(Tracker.COLUMN_PURPOSE, record.gPurpose());
        values.put(Tracker.COLUMN_DATE, record.gDate());
        values.put(Tracker.COLUMN_DESCRIPTION, record.gDescription());
        values.put(Tracker.COLUMN_METHOD, record.gMethod());

        String selection = Tracker.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(record.gId())};

        // updating row
        int count = db.update(
                Tracker.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
        //return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
        // new String[]{String.valueOf(note.getId())});
    }

    public void deleteRecord(Tracker record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Tracker.TABLE_NAME, Tracker.COLUMN_ID + " = ?",
                new String[]{String.valueOf(record.gId())});
        db.close();
    }


}

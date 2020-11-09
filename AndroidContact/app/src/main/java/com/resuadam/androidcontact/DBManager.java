package com.resuadam.androidcontact;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** Eases opening the database */
public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NAME = "contactDB";
    public static final int DB_VERSION = 2;

    public static final String TABLE_CONTACTS = "contacts";
    public static final String CONTACTS_COL_NAME = "_id";
    public static final String CONTACTS_COL_TLF = "tlf";

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i( "DBManager", DB_NAME + " creating: " + TABLE_CONTACTS);

        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE IF NOT EXISTS "
                    + TABLE_CONTACTS + "("
                    + CONTACTS_COL_NAME + " string(255) PRIMARY KEY NOT NULL,"
                    + CONTACTS_COL_TLF + " string(20) NOT NULL)"
            );
            db.setTransactionSuccessful();
        } catch(SQLException exc) {
            Log.e( "DBManager.onCreate", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
        Log.i( "DBManager", DB_NAME + " " + v1 + " -> " + v2 );

        try {
            db.beginTransaction();
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            db.setTransactionSuccessful();
        } catch(SQLException exc) {
            Log.e( "DBManager.onUpgrade", exc.getMessage() );
        } finally {
            db.endTransaction();
        }

        this.onCreate( db );
    }

    /** Adds a new row.
     * @param name The name of the contact.
     * @param tlf The tlf. of the contact.
     */
    public void add(String name, String tlf)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put( CONTACTS_COL_NAME, name );
        values.put( CONTACTS_COL_TLF, tlf );

        try {
            db.beginTransaction();
            cursor = db.query( TABLE_CONTACTS,
                    new String[]{ CONTACTS_COL_NAME },
                    CONTACTS_COL_NAME + " = ?", new String[]{ name },
                    null, null, null, "1" );

            if ( cursor.getCount() > 0 ) {
                db.update( TABLE_CONTACTS, values,
                        CONTACTS_COL_NAME + " = ?", new String[]{ name }  );
            } else {
                db.insert( TABLE_CONTACTS, null, values );
            }

            db.setTransactionSuccessful();
        } catch(SQLException exc) {
            Log.e( "dbAdd", exc.getMessage() );
        }
        finally {
            if ( cursor != null ) {
                cursor.close();
            }

            db.endTransaction();
        }
    }

    /** Removes a record given the name of the contact.
     * @param name The name of the contact, as a String.
     */
    public void remove(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete( TABLE_CONTACTS, CONTACTS_COL_NAME + " = ?", new String[]{ name } );
            db.setTransactionSuccessful();
        } catch(SQLException exc) {
            Log.e( "dbRemove", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }
    }

    /** Returns a Cursor for the records matching the text criteria.
     * @param text The name to look for, as a String.
     * @return A Cursor for the found records.
     */
    public Cursor searchFor(String text)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor toret = null;

        try {
            toret = db.query( TABLE_CONTACTS, null,
                    CONTACTS_COL_NAME + " LIKE ?",
                    new String[] { text }, null, null, null );
        }
        catch(SQLException exc) {
            Log.e( "DBManager.searchFor", exc.getMessage() );
        }

        return toret;
    }

    /** Returns a Cursor for all the contacts in the database */
    public Cursor getAllContacts()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query( DBManager.TABLE_CONTACTS,
                null, null, null, null, null, null );
    }
}
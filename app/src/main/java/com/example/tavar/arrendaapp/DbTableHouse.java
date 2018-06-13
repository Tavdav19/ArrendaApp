package com.example.tavar.arrendaapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableHouse implements BaseColumns {
    private SQLiteDatabase db;
    public static final String TABLE_HOUSE = "house";
    private static final String FIELD_DESC = "description";
    private static final String FIELD_LOC = "loc";
    private static final String FIELD_PEOPLE = "people";
    private static final String FIELD_BEDROOM = "bedroom";
    private static final String FIELD_BATHROOM = "bathroom";
    private static final String FIELD_ID_SELLER= "idSeller";

    public DbTableHouse (SQLiteDatabase db){
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + TABLE_HOUSE + "(" +
                        _ID + "LONG PRIMARY KEY AUTOINCREMENT," +
                        FIELD_DESC + "TEXT NOT NULL," +
                        FIELD_LOC + "TEXT NOT NULL," +
                        FIELD_PEOPLE + "INTEGER," +
                        FIELD_BEDROOM + "INTEGER," +
                        FIELD_BATHROOM + "INTEGER," +
                        FIELD_ID_SELLER + "LONG," +

                        "FOREIGN KEY (" + FIELD_ID_SELLER + ") REFERENCES " +
                            DbTableSeller.TABLE_SELLER +
                                "(" + DbTableSeller._ID +")" +
                        ")"
        );
    }
    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_HOUSE, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}

package com.example.tavar.arrendaapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableSeller implements BaseColumns {
    private SQLiteDatabase db;
    private static final String TABLE_HOUSE = "house";
    private static final String FIELD_USER = "userName";

    public DbTableSeller (SQLiteDatabase db){
        this.db = db;
    }


    public void create(){

        db.execSQL(
                "CREATE TABLE " + TABLE_HOUSE + "(" +
                    _ID + "LONG PRIMARY KEY AUTOINCREMENT," +
                    FIELD_USER + "TEXT NOT NULL" +
                    ")"
        );
    }
    public static ContentValues getContentValues(Seller seller) {
        ContentValues values = new ContentValues();

        values.put(_ID, seller.getId());
        values.put(FIELD_USER, seller.getUserName());

        return values;
    }
    public long insert(ContentValues values) {
        return db.insert(TABLE_HOUSE, null, values);
    }
}

package com.example.tavar.arrendaapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableSeller implements BaseColumns {
    private SQLiteDatabase db;
    public static final String TABLE_SELLER = "seller";
    private static final String FIELD_USER = "userName";

    public DbTableSeller (SQLiteDatabase db){
        this.db = db;
    }


    public void create(){

        db.execSQL(
                "CREATE TABLE " + TABLE_SELLER + "(" +
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
        return db.insert(TABLE_SELLER, null, values);
    }

    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_SELLER, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}

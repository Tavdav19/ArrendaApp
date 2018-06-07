package com.example.tavar.arrendaapp;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableHouse implements BaseColumns {
    private SQLiteDatabase db;

    public DbTableHouse (SQLiteDatabase db){
        this.db = db;
    }

    public void create(){
        String FIELD_DESC = "description";
        db.execSQL(
                "CREATE TABLE house("+
                        _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_DESC + "TEXT NOT NULL," +
                        "Loc" + "TEXT NOT NULL,"

        );
    }
}

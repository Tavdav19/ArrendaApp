package com.example.tavar.arrendaapp;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableHouse implements BaseColumns {
    private SQLiteDatabase db;

    public DbTableHouse (SQLiteDatabase db){
        this.db = db;
    }

    public void create(){

    }
}

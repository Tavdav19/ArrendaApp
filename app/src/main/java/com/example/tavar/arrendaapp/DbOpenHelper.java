package com.example.tavar.arrendaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jei on 01/06/2018.
 */

public class DbOpenHelper extends SQLiteOpenHelper {


    public static final String NAME_DB = "arrenda.db";
    public static final int VERSION_DB = 1;

    public DbOpenHelper(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbTableHouse dbTableHouse = new DbTableHouse(db);
        dbTableHouse.create();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

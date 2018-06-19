package com.example.tavar.arrendaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jei on 01/06/2018.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final boolean PRODUCTION = false;
    public static final String NAME_DB = "arrenda.db";
    public static final int VERSION_DB = 1;

    public DbOpenHelper(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbTableHouse dbTableHouse = new DbTableHouse(db);
        dbTableHouse.create();

        if(!PRODUCTION){
            seed(db);
        }
    }

    private void seed(SQLiteDatabase db){
        DbTableSeller dbTableSeller = new DbTableSeller(db);

        Seller seller = new Seller();
        seller.setUserName("David Tavares");
        seller.setPassword("1234567890");
        int idSeller = (int) dbTableSeller.insert(DbTableSeller.getContentValues(seller));

        DbTableHouse dbTableHouse = new DbTableHouse(db);

        House house = new House();
            house.setIdSeller(idSeller);
            house.setDescription("Casa pequena de campo");
            house.setLoc("Gafanha");
            house.setPeople(2);
            house.setBedroom(1);
            house.setBathroom(1);
            house.setWeekPrice(300);
            dbTableHouse.insert(DbTableHouse.getContentValues(house));

        house = new House();
        house.setIdSeller(idSeller);
        house.setDescription("Quarto com vista para a Ria");
        house.setLoc("Aveiro");
        house.setPeople(2);
        house.setBedroom(1);
        house.setBathroom(1);
        house.setWeekPrice(300);
        dbTableHouse.insert(DbTableHouse.getContentValues(house));

        house = new House();
        house.setIdSeller(idSeller);
        house.setDescription("Apartamnento junto a Se da Guarda");
        house.setLoc("Guarda");
        house.setPeople(5);
        house.setBedroom(3);
        house.setBathroom(1);
        house.setWeekPrice(400);
        dbTableHouse.insert(DbTableHouse.getContentValues(house));

        house = new House();
        house.setIdSeller(idSeller);
        house.setDescription("Bonita vivenda perto do castelo do Sabugal");
        house.setLoc("Sabugal");
        house.setPeople(10);
        house.setBedroom(5);
        house.setBathroom(2);
        house.setWeekPrice(900);
        dbTableHouse.insert(DbTableHouse.getContentValues(house));
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

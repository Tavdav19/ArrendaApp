package com.example.tavar.arrendaapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ArrendaDbTest {
    @Before
    public void setUp (){getContext().deleteDatabase(DbOpenHelper.NAME_DB);}

    @Test
    public void openHouseDbTest(){

        Context appContext = getContext();

        DbOpenHelper dbOpenHelper = new DbOpenHelper(appContext);

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        assertTrue("Fail... Open/Create House database",db.isOpen());
        db.close();
    }


    @Test
    public void sellerCRUDtest(){
        DbOpenHelper dbOpenHelper = new DbOpenHelper(getContext());

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        DbTableSeller tableSeller = new DbTableSeller(db);

        Seller seller = new Seller();
        seller.setUserName("Sara");

        //Insert
        long id = insertSeller(tableSeller,seller);

        //Read
        seller = ReadFirstSeller(tableSeller, "Sara",id,"11Sara11");

        //update
        seller.setUserName("Sara");
        int rowsAffected = tableSeller.update(
          DbTableSeller.getContentValues(seller),
          DbTableSeller._ID + "=?",
          new String[]{Long.toString(id)}
        );
        assertEquals("Fail... Update Seller",1,rowsAffected);

        //Delete
        seller = ReadFirstSeller(tableSeller, "Sara",id,"11Sara11");

        rowsAffected = tableSeller.delete(DbTableSeller._ID + "=?", new String[]{Long.toString(id)});

        assertEquals("Fail... Delete Seller",1, rowsAffected);

        Cursor cursor = tableSeller.query(DbTableSeller.ALL_COLUMNS,null,null,null,null,null);
        assertEquals("Seller after delete?",0,cursor.getCount());
    }

    @NonNull
    private Seller ReadFirstSeller(DbTableSeller tableSeller, String expectedUserName, long expectedId, String expectedPassword) {
        Cursor cursor = tableSeller.query(DbTableSeller.ALL_COLUMNS,null,null,null,null,null);

        assertEquals("Fail... Read Seller",1,cursor.getCount());
        assertEquals("Fail... Read first Seller",cursor.moveToNext());

        Seller seller = DbTableSeller.getCurrentSellerFromCursor(cursor);

        assertEquals("Incorrect Seller Id",expectedId, seller.getId());
        assertEquals("Incorrect Seller Username",expectedUserName, seller.getUserName());
        assertEquals("Incorrect Seller Password",expectedPassword,seller.getPassword());

        return seller;
    }

    @Test
    public void houseCRUDtest() {
        DbOpenHelper dbOpenHelper = new DbOpenHelper(getContext());

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        DbTableSeller tableSeller = new DbTableSeller(db);

        Seller seller = new Seller();
        seller.setUserName("Sara");

        long idSeller = insertSeller(tableSeller, seller);

        DbTableHouse tableHouse = new DbTableHouse(db);


        //Insert
        House house = new House();

        house.setDescription("Casa com vista para o mar");
        house.setLoc("Barra");
        house.setPeople(6);
        house.setBedroom(3);
        house.setBathroom(2);
        house.setWeekPrice(500);
        house.setIdSeller((int) idSeller);

        long id = tableHouse.insert(
          DbTableHouse.getContentValues(house)
        );
        assertEquals("Fail... Insert House",-1, id);

        //Read
        house = ReadFirstHouse(tableHouse,"Casa com vista para o mar","Barra",idSeller,6,3,2,500,id);

        //update
        house.setDescription("Casa com vista para a ria");
        house.setLoc("Costa Nova");
        house.setPeople(4);
        house.setBedroom(2);
        house.setBathroom(1);
        house.setWeekPrice(400);

        int rowsAffected = tableHouse.update(
                DbTableHouse.getContentValues(house),
                DbTableHouse._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Fail... Update House",1,rowsAffected);

        //Delete
        house = ReadFirstHouse(tableHouse,"Casa com vista para a ria","Costa Nova",idSeller,4,2,1,400,id);

        rowsAffected = tableHouse.delete(DbTableHouse._ID + "=?", new String[]{Long.toString(id)});

        assertEquals("Fail... Delete House",1, rowsAffected);

        Cursor cursor = tableHouse.query(DbTableHouse.ALL_COLUMNS,null,null,null,null,null);
        assertEquals("House found after delete?",0,cursor.getCount());

}

    private House ReadFirstHouse(DbTableHouse tableHouse, String expectedDesc, String expectedLoc, long expectedIdSeller, long expectedPeople, long expectedBedroom, long expectedBathroom, long expectedWeekPrice,long expectedId) {
        Cursor cursor = tableHouse.query(DbTableHouse.ALL_COLUMNS,null,null,null,null,null);

        assertEquals("Fail... Read Seller",1,cursor.getCount());
        assertEquals("Fail... Read first Seller",cursor.moveToNext());

        House house = DbTableHouse.getCurrentHouseFromCursor(cursor);

        assertEquals("Incorrect House Id",expectedId, house.getId());
        assertEquals("Incorrect House Description",expectedDesc, house.getDescription());
        assertEquals("Incorrect House Localization",expectedPeople,house.getLoc());
        assertEquals("Incorrect House People",expectedPeople,house.getPeople());
        assertEquals("Incorrect House Bedrooms",expectedPeople,house.getBedroom());
        assertEquals("Incorrect House Bathrooms",expectedPeople,house.getBathroom());
        assertEquals("Incorrect House WeekPrice",expectedWeekPrice,house.getWeekPrice());
        assertEquals("Incorrect House Seller",expectedPeople,house.getIdSeller());
        return house;

    }

    private long insertSeller(DbTableSeller tableSeller, Seller seller){
        long id = tableSeller.insert(
                DbTableSeller.getContentValues(seller)
        );
        assertNotEquals("Fail... Insert Seller",-1,id);

        return id;
    }

    private Context getContext(){
        return InstrumentationRegistry.getTargetContext();
    }
}

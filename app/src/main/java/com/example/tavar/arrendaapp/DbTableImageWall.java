package com.example.tavar.arrendaapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableImageWall implements BaseColumns {
    private SQLiteDatabase db;
    public static final String TABLE_IMAGEWALL = "imagewall";
    public static final String FIELD_ID_HOUSE = "idhouse"

    public static final String FIELD_00 = "img00";
    public static final String FIELD_01 = "img01";
    public static final String FIELD_02 = "img02";

    public static final String FIELD_10 = "img10";
    public static final String FIELD_11 = "img11";
    public static final String FIELD_12 = "img12";

    public static final String FIELD_20 = "img20";
    public static final String FIELD_21 = "img21";
    public static final String FIELD_22 = "img22";

    public static final String [] ALL_COLUMNS = new String[] {_ID, FIELD_ID_HOUSE, FIELD_00, FIELD_01, FIELD_02, FIELD_10, FIELD_11, FIELD_12, FIELD_20, FIELD_21, FIELD_22};


    public DbTableImageWall(SQLiteDatabase db){
        this.db = db;
    }


    public void create(){

        db.execSQL(
                "CREATE TABLE " + TABLE_IMAGEWALL + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_00 + " TEXT," +
                        FIELD_01 + " TEXT," +
                        FIELD_02 + " TEXT," +

                        FIELD_10 + " TEXT," +
                        FIELD_11 + " TEXT," +
                        FIELD_12 + " TEXT," +

                        FIELD_20 + " TEXT," +
                        FIELD_21 + " TEXT," +
                        FIELD_22 + " TEXT," +

                        "FOREIGN KEY (" + FIELD_ID_HOUSE + ") REFERENCES " +
                        DbTableHouse.TABLE_HOUSE +
                        "(" + DbTableHouse._ID+")" +
                        ")"
        );
    }
    public static ContentValues getContentValues(Seller seller) {
        ContentValues values = new ContentValues();

        //values.put(_ID, seller.getId());
        values.put(FIELD_USER, seller.getUserName());

        return values;
    }

    public static Seller getCurrentSellerFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posName = cursor.getColumnIndex(FIELD_USER);

        Seller seller = new Seller();

        seller.setId(cursor.getInt(posId));
        seller.setUserName(cursor.getString(posName));

        return seller;
    }

    /**
     * Convenience method for inserting a row into the categories table.
     *
     * @param values this map contains the initial column values for the
     *            row. The keys should be the column names and the values the
     *            column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(ContentValues values) {
        return db.insert(TABLE_SELLER, null, values);
    }

    /**
     * Convenience method for updating rows in the categories table.
     *
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLE_SELLER, values, whereClause, whereArgs);
    }

    /**
     * Convenience method for deleting rows in the categories table.
     *
     * @param whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */
    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_SELLER, whereClause, whereArgs);
    }

    /**
     * Query the categories table, returning a {@link Cursor} over the result set.
     *
     * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
     */
    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_SELLER, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}

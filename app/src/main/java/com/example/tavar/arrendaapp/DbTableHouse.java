package com.example.tavar.arrendaapp;

import android.content.ContentValues;
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
    private static final String FIELD_WEEKPRICE= "weekPrice";
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
                        FIELD_WEEKPRICE + "INTEGER," +
                        FIELD_ID_SELLER + "LONG," +

                        "FOREIGN KEY (" + FIELD_ID_SELLER + ") REFERENCES " +
                            DbTableSeller.TABLE_SELLER +
                                "(" + DbTableSeller._ID +")" +
                        ")"
        );
    }
    public static ContentValues getContentValues(House house) {
        ContentValues values = new ContentValues();

        values.put(FIELD_DESC, house.getDescription());
        values.put(FIELD_LOC, house.getLoc());
        values.put(FIELD_PEOPLE, house.getPeople());
        values.put(FIELD_BEDROOM, house.getBedroom());
        values.put(FIELD_BATHROOM, house.getBathroom());
        values.put(FIELD_WEEKPRICE, house.getWeekPrice());
        values.put(FIELD_ID_SELLER, house.getIdSeller());

        return values;
    }

    public static House getCurrentHouseFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posDesc = cursor.getColumnIndex(FIELD_DESC);
        final int posLoc = cursor.getColumnIndex(FIELD_LOC);
        final int posPeople = cursor.getColumnIndex(FIELD_PEOPLE);
        final int posBedroom = cursor.getColumnIndex(FIELD_BEDROOM);
        final int posBathroom = cursor.getColumnIndex(FIELD_BATHROOM);
        final int posWeekPrice = cursor.getColumnIndex(FIELD_WEEKPRICE);
        final int posIdSeller = cursor.getColumnIndex(FIELD_ID_SELLER);

        House house = new House();

        house.setId(cursor.getInt(posId));
        house.setDescription(cursor.getString(posDesc));
        house.setLoc(cursor.getString(posLoc));
        house.setPeople(cursor.getInt(posPeople));
        house.setBedroom(cursor.getInt(posBedroom));
        house.setBathroom(cursor.getInt(posBathroom));
        house.setWeekPrice(cursor.getInt(posWeekPrice));
        house.setIdSeller(cursor.getInt(posIdSeller));

        return house;
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
        return db.insert(TABLE_HOUSE, null, values);
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
        return db.update(TABLE_HOUSE, values, whereClause, whereArgs);
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
        return db.delete(TABLE_HOUSE, whereClause, whereArgs);
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
        Cursor cursor = db.query(TABLE_HOUSE, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
    }
}

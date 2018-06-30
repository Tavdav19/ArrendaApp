package com.example.tavar.arrendaapp;


import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityEdit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private House house;
    private static final int SELLER_CURSOR_LOADER_ID = 0;
    public EditText editTextDesc;
    public EditText editTextLoc;
    public TextView textViewUserName;
    public Spinner spinnerPeople;
    public Spinner spinnerBedroom;
    public Spinner spinnerBathroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();

        int houseId = intent.getIntExtra(HousesActivity.HOUSE_ID, -1);

        if (houseId == -1) {
            finish();
            return;
        }
        Cursor cursorHouse = getContentResolver().query(
                Uri.withAppendedPath(ArrendaContentProvider.HOUSE_URI, Integer.toString(houseId)),
                DbTableHouse.ALL_COLUMNS,
                null,
                null,
                null
        );
        if (!cursorHouse.moveToNext()) {
            Toast.makeText(this, "Not found", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        editTextLoc = (EditText) findViewById(R.id.editTextLoc);
        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        spinnerPeople= (Spinner) findViewById(R.id.spinnerPeople);
        spinnerBedroom =(Spinner) findViewById(R.id.spinnerBedroom);
        spinnerBathroom = (Spinner) findViewById(R.id.spinnerBathroom);

        house = DbTableHouse.getCurrentHouseFromCursor(cursorHouse);

        editTextDesc.setText(String.valueOf(house.getDescription()));
        editTextLoc.setText(String.valueOf(house.getLoc()));
        textViewUserName.setText(String.valueOf(house.getIdSeller()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(SELLER_CURSOR_LOADER_ID,null,this);
    }

    public void save (View view){
        //todo: Validations

        house.setDescription(editTextDesc.getText().toString());
        house.setLoc(editTextLoc.getText().toString());
        house.setPeople((int) spinnerPeople.getSelectedItemId());
        house.setBedroom((int) spinnerBedroom.getSelectedItemId());
        house.setBathroom((int) spinnerBathroom.getSelectedItemId());

        int recordsAffected = getContentResolver().update(
                Uri.withAppendedPath(ArrendaContentProvider.HOUSE_URI, Integer.toString(house.getId())),
                DbTableHouse.getContentValues(house),
                null,
                null
        );

        if(recordsAffected > 0){
            Toast.makeText(this, "Saved",Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "Could not save", Toast.LENGTH_LONG).show();
    }
    /**
     * Instantiate and return a new Loader for the given ID.
     * <p>
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == SELLER_CURSOR_LOADER_ID){
            return new android.support.v4.content.CursorLoader(this, ArrendaContentProvider.HOUSE_URI, DbTableHouse.ALL_COLUMNS, null, null, null);
        }
        return null;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the  constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     * <p>
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        SimpleCursorAdapter cursorAdapterPeople = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{DbTableHouse.FIELD_PEOPLE},
                new int[]{android.R.id.text1}
        );

        spinnerPeople.setAdapter(cursorAdapterPeople);
        int people = house.getPeople();
        for (int i= 0; i<spinnerPeople.getCount(); i++){
            Cursor cursor = (Cursor) spinnerPeople.getItemAtPosition(i);
            final int posId = cursor.getColumnIndex(DbTableHouse.FIELD_PEOPLE);
            if (people == cursor.getInt(posId)){
                spinnerPeople.setSelection(i);
                break;

            }
        }

        SimpleCursorAdapter cursorAdapterBedroom = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{DbTableHouse.FIELD_BEDROOM},
                new int[]{android.R.id.text1}
        );

        spinnerBedroom.setAdapter(cursorAdapterBedroom);
        int bedroom = house.getBedroom();
        for (int i= 0; i<spinnerBedroom.getCount(); i++){
            Cursor cursor = (Cursor) spinnerBedroom.getItemAtPosition(i);
            final int posId = cursor.getColumnIndex(DbTableHouse.FIELD_BEDROOM);
            if (bedroom == cursor.getInt(posId)){
                spinnerBedroom.setSelection(i);
                break;

            }
        }
        SimpleCursorAdapter cursorAdapterBathrooms = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{DbTableHouse.FIELD_BATHROOM},
                new int[]{android.R.id.text1}
        );

        spinnerBathroom.setAdapter(cursorAdapterBathrooms);
        int bathroom = house.getBathroom();
        for (int i= 0; i<spinnerBathroom.getCount(); i++){
            Cursor cursor = (Cursor) spinnerBathroom.getItemAtPosition(i);
            final int posId = cursor.getColumnIndex(DbTableHouse.FIELD_BATHROOM);
            if (bathroom == cursor.getInt(posId)){
                spinnerBathroom.setSelection(i);
                break;

            }
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     * <p>
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
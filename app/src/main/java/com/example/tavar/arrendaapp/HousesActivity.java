package com.example.tavar.arrendaapp;

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
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HousesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private House house;
    public static final String HOUSE_ID = "HOUSE_ID";
    private static final int SELLER_CURSOR_LOADER_ID = 0;
    public FeedCursorAdapter feedCursorAdapter;

    public TextView textViewDesc;
    public TextView textViewLoc;
    public TextView textViewUserName;
    public TextView textViewPrice;
    public TextView textViewPeople;
    public TextView textViewBedroom;
    public TextView textViewBathroom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChat();
            }
        });
        Intent intent = getIntent();

        int houseId = intent.getIntExtra(MainActivity.HOUSE_ID, -1);

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




        textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        textViewLoc = (TextView) findViewById(R.id.textViewLoc);
        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewPeople = (TextView) findViewById(R.id.textViewPeople);
        textViewBedroom = (TextView) findViewById(R.id.textViewBedroom);
        textViewBathroom = (TextView) findViewById(R.id.textViewBathroom);

        house = DbTableHouse.getCurrentHouseFromCursor(cursorHouse);

        textViewDesc.setText(String.valueOf(house.getDescription()));
        textViewLoc.setText(String.valueOf(house.getLoc()));
        textViewUserName.setText(String.valueOf(house.getIdSeller()));
        textViewPrice.setText(String.valueOf(house.getWeekPrice())+("€"));
        textViewPeople.setText(String.valueOf(house.getPeople()));
        textViewBedroom.setText(String.valueOf(house.getBedroom()));
        textViewBathroom.setText(String.valueOf(house.getBathroom()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(SELLER_CURSOR_LOADER_ID, null, this);
    }
    private void startChat() {
        int id = house.getId();
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(HOUSE_ID, id);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_houses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent (this,SettingsActivity.class);
            startActivity(intent);
        }else if(id == R.id.action_edit){
            int ids = house.getId();

            Intent intent = new Intent (this,ActivityEdit.class);

            intent.putExtra(HOUSE_ID, ids);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    @Override
    public void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(SELLER_CURSOR_LOADER_ID, null, this);
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
        if (id == SELLER_CURSOR_LOADER_ID) {
            return new CursorLoader(this, ArrendaContentProvider.SELLER_URI, DbTableSeller.ALL_COLUMNS, null, null, null);
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
     * a {@link Cursor} from a {@link android.content.CursorLoader},
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

    /**
     * A placeholder fragment containing a simple view.
     */
}

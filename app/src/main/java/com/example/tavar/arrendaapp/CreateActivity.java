package com.example.tavar.arrendaapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String HOUSE_ID = "HOUSE_ID";
    private static final int HOUSE_CURSOR_LOARDER_ID = 0;
    public EditText editTextDescCreate;
    public EditText editTextLocCreate;
    public Spinner spinnerPeople;
    public Spinner spinnerBedroom;
    public Spinner spinnerBathroom;
    public EditText editTextPriceCreate;
    public Uri newUri;
    public House house;
    public ImageView imageViewPerfil;
    public ImageView imageButton00;
    public ImageView imageButton01;
    public ImageView imageButton02;

    public ImageView imageButton10;
    public ImageView imageButton11;
    public ImageView imageButton12;

    public ImageView imageButton20;
    public ImageView imageButton21;
    public ImageView imageButton22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createAction();
            }
        });
        intiButtons();
        spinnerAction();
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
        editTextPriceCreate= (EditText) findViewById(R.id.editTextPriceCreate);
        editTextDescCreate = (EditText) findViewById(R.id.editTextDescCreate);
        editTextLocCreate = (EditText) findViewById(R.id.editTextLocCreate);
        spinnerPeople = (Spinner) findViewById(R.id.spinnerPeople);
        spinnerBedroom = (Spinner) findViewById(R.id.spinnerBedroom);
        spinnerBathroom = (Spinner) findViewById(R.id.spinnerBathroom);

        imageViewPerfil = (ImageView) findViewById(R.id.imageViewPerfil);
        imageButton00 = (ImageView) findViewById(R.id.imageButton00);
        imageButton01 = (ImageView) findViewById(R.id.imageButton01);
        imageButton02 = (ImageView) findViewById(R.id.imageButton02);

        imageButton10 = (ImageView) findViewById(R.id.imageButton10);
        imageButton11 = (ImageView) findViewById(R.id.imageButton11);
        imageButton12 = (ImageView) findViewById(R.id.imageButton12);

        imageButton20 = (ImageView) findViewById(R.id.imageButton20);
        imageButton21 = (ImageView) findViewById(R.id.imageButton21);
        imageButton22 = (ImageView) findViewById(R.id.imageButton22);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(HOUSE_CURSOR_LOARDER_ID, null, this);
    }
    public void spinnerAction(){
        String ppl[] = {"1","2","3","4","5","6","7","8","9","10"};

        Spinner people = (Spinner) findViewById(R.id.spinnerPeople);

        ArrayAdapter<String> peopleArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, ppl);
        peopleArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        people.setAdapter(peopleArrayAdapter);

        String bdr [] = {"1","2","3","4","5","6"};

        Spinner bedroom = (Spinner) findViewById(R.id.spinnerBedroom);

        ArrayAdapter<String> bedroomArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, bdr);
        bedroomArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        bedroom.setAdapter(bedroomArrayAdapter);

        String bth[] = {"1","2","3","4"};

        Spinner bathroom = (Spinner) findViewById(R.id.spinnerBathroom);

        ArrayAdapter<String> bathroomArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, bth);
        bathroomArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        bathroom.setAdapter(bathroomArrayAdapter);
    }

    public void createAction(){
        if(editTextDescCreate!=null&&editTextLocCreate!=null){
        create();
        finish();
    }else{
        Toast.makeText(this, "Complete all parameters please", Toast.LENGTH_LONG).show();
    }

}
    private void intiButtons(){
        Button uploadProfile = (Button) findViewById(R.id.uploadProfile) ;
        uploadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
            }
        });

        Button upload00 = (Button) findViewById(R.id.upload00) ;
        upload00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        Button upload01 = (Button) findViewById(R.id.upload01) ;
        upload01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 2);
            }
        });

        Button upload02 = (Button) findViewById(R.id.upload02) ;
        upload02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 3);
            }
        });

        Button upload10 = (Button) findViewById(R.id.upload10) ;
        upload10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 4);
            }
        });

        Button upload11 = (Button) findViewById(R.id.upload11) ;
        upload11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 5);
            }
        });

        Button upload12 = (Button) findViewById(R.id.upload12) ;
        upload12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 6);
            }
        });

        Button upload20 = (Button) findViewById(R.id.upload20) ;
        upload20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 7);
            }
        });

        Button upload21 = (Button) findViewById(R.id.upload21) ;
        upload21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 8);
            }
        });

        Button upload22 = (Button) findViewById(R.id.upload22) ;
        upload22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 9);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
            switch(requestCode) {
                case 0:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageViewPerfil.setImageURI(selectedImage);
                    }
                    break;
                case 1:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton00.setImageURI(selectedImage);
                    }
                    break;
                case 2:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton01.setImageURI(selectedImage);
                    }
                    break;
                case 3:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton02.setImageURI(selectedImage);
                    }
                    break;
                case 4:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton10.setImageURI(selectedImage);
                    }
                    break;
                case 5:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton11.setImageURI(selectedImage);
                    }
                    break;
                case 6:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton12.setImageURI(selectedImage);
                    }
                    break;
                case 7:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton20.setImageURI(selectedImage);
                    }
                    break;
                case 8:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton21.setImageURI(selectedImage);
                    }
                    break;
                case 9:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = imageReturnedIntent.getData();
                        imageButton22.setImageURI(selectedImage);
                    }
                    break;


            }
        }


    private void create(){
        ContentValues values = new ContentValues();
        values.put(DbTableHouse.FIELD_DESC, editTextDescCreate.getText().toString());
        values.put(DbTableHouse.FIELD_WEEKPRICE, editTextPriceCreate.getText().toString());
        values.put(DbTableHouse.FIELD_LOC, editTextLocCreate.getText().toString());
        values.put(DbTableHouse.FIELD_PEOPLE, spinnerPeople.getSelectedItem().toString());
        values.put(DbTableHouse.FIELD_BEDROOM, spinnerBedroom.getSelectedItem().toString());
        values.put(DbTableHouse.FIELD_BATHROOM, spinnerBathroom.getSelectedItem().toString());
        ContentResolver cr = getContentResolver();
        Uri newUri = cr.insert(ArrendaContentProvider.HOUSE_URI, values);
        Toast.makeText(this, "House Created", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(HOUSE_CURSOR_LOARDER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        if (id == HOUSE_CURSOR_LOARDER_ID) {
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
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("MyString", "Welcome back to Android");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String myString = savedInstanceState.getString("MyString");
    }
}

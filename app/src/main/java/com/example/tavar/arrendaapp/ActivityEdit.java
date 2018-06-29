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
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityEdit extends HousesActivity {
    private House house;

    public EditText editTextDesc;
    public EditText editTextLoc;
    public TextView textViewUserName;

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

        editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        editTextLoc = (EditText) findViewById(R.id.editTextLoc);
        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        // spinnerPeople= (Spinner) findViewById(R.id.spinnerPeople);
        // spinnerBedroom =(Spinner) findViewById(R.id.spinnerBedroom);
        //  spinnerBathroom = (Spinner) findViewById(R.id.spinnerBathroom);

        editTextDesc.setText(String.valueOf(HousesActivity.class.getField());


        editTextLoc.setText(String.valueOf(house.getLoc()));
        textViewUserName.setText(String.valueOf(house.getIdSeller()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
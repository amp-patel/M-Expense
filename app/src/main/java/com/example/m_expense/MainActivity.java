//Reference https://www.freakyjolly.com/android-sqlite-example-application-insert-update-delete-truncate-operations/
//https://guides.codepath.com/android/using-the-recyclerview
//Used university materials
package com.example.m_expense;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.m_expense.Model.TripModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDatabaseHelper myDB;
    ArrayList<String> trip_id, trip_name,trip_destination, trip_date, trip_risk, trip_description;
    CustomAdapter customAdapter;
    ArrayList<TripModel> tripModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tripModels = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);

        //add button for enter new trip
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        //create instance of databasehelper class
        myDB = new MyDatabaseHelper(MainActivity.this);
//        trip_id = new ArrayList<>();
//        trip_name = new ArrayList<>();
//        trip_destination = new ArrayList<>();
//        trip_date = new ArrayList<>();
//        trip_risk = new ArrayList<>();
//        trip_description = new ArrayList<>();
        storeDataInArray();

        //set all data to recycler view
        customAdapter = new CustomAdapter(MainActivity.this,this, tripModels);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                //search trip by string
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //open JSON activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all)
        {
            Log.d("msg:","delete selected");
            confirmDialog();
        }
        if(item.getItemId() == R.id.jsonActivity)
        {
            Intent intent = new Intent(MainActivity.this,JsonActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //extract all data from database
    void storeDataInArray()
    {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0)
        {
            Log.d("msg: ","no data");
        }
        else
        {
            while (cursor.moveToNext())
            {
//                trip_id.add(cursor.getString(0));
//                trip_name.add(cursor.getString(1));
//                trip_destination.add(cursor.getString(2));
//                trip_date.add(cursor.getString(3));
//                trip_risk.add(cursor.getString(4));
//                trip_description.add(cursor.getString(5));
//                Log.d("name "," "+trip_name);
                tripModels.add(new TripModel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            }
        }
    }

    //conformation of delete all details
    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure, Delete All trips ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}


























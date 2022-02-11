//Reference https://www.freakyjolly.com/android-sqlite-example-application-insert-update-delete-truncate-operations/
//Used university materials
package com.example.m_expense;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripViewActivity extends AppCompatActivity {

    TextView name_input;
    TextView destination_input;
    TextView date_input;
    TextView risk_input;
    TextView description_input;
    Button update_button, add_expenses_button, view_expenses_button;
    FloatingActionButton deleteFloat_button;
    String id, name,destination,date,risk,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_view);
        name_input = findViewById(R.id.trip_name2);
        destination_input = findViewById(R.id.trip_destination2);
        date_input = findViewById(R.id.trip_date2);
        risk_input = findViewById(R.id.trip_risk2);
        description_input = findViewById(R.id.trip_description2);
        update_button = findViewById(R.id.update_button);
        add_expenses_button = findViewById(R.id.add_expenses);
        view_expenses_button = findViewById(R.id.view_expenses);
        deleteFloat_button = findViewById(R.id.deleteFloatingButton);
        getIntentData();

        //set name of the activity based of trip name
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Details of "+name+" Trip");
        }

        //open all expenses of perticular trip

        view_expenses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(TripViewActivity.this, ExpensesViewActivity.class);
                    intent.putExtra("Id", id);
                    intent.putExtra("Name", name);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(TripViewActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });

        //open update activity for update any details of trip
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripViewActivity.this, UpdateActivity.class);
                intent.putExtra("id",  id);
                intent.putExtra("name", name);
                intent.putExtra("destination", destination);
                intent.putExtra("date", date);
                intent.putExtra("risk", risk);
                intent.putExtra("description", description);
                startActivity(intent);
            }
        });

        //add new expenses of perticular trip
        add_expenses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripViewActivity.this, AddExpensesActivity.class);
                intent.putExtra("Id",id);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });

        //delete current trip
        deleteFloat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    //get all the data from previous activity
    void getIntentData()
    {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("destination") &&getIntent().hasExtra("date")
                &&getIntent().hasExtra("risk")&&getIntent().hasExtra("description"))
        {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            date = getIntent().getStringExtra("date");
            risk = getIntent().getStringExtra("risk");
            description = getIntent().getStringExtra("description");
            name_input.setText(name);
            destination_input.setText(destination);
            date_input.setText(date);
            risk_input.setText(risk);
            description_input.setText(description);
        }
        else
        {
            Log.d("msg: ","error");
        }
    }

    //conformation of delete trip
    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " trip ?");
        builder.setMessage("Are you sure, Delete " + name + " trip ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(TripViewActivity.this);
                myDB.deleteOneRow(id);
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
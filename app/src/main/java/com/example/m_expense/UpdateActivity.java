//Reference https://www.freakyjolly.com/android-sqlite-example-application-insert-update-delete-truncate-operations/
//https://www.tutorialspoint.com/android/android_datepicker_control.htm
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

     EditText name_input;
     EditText destination_input;
     TextView date_input;
     EditText risk_input;
     EditText description_input;
     Button update_button;
     DatePickerDialog.OnDateSetListener setListener;
     String id, name,destination,date,risk,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name_input = findViewById(R.id.trip_name2);
        destination_input = findViewById(R.id.trip_destination2);
        date_input = findViewById(R.id.trip_date2);
        risk_input = findViewById(R.id.trip_risk2);
        description_input = findViewById(R.id.trip_description2);
        update_button = findViewById(R.id.update_button);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //date picker for year, month and day formate
        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        date_input.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        getIntentData();

        //set name of the activity based of trip name
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Details of "+name+" Trip");
        }

        //update button to update new details
                update_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                        name = name_input.getText().toString().trim();
                        destination = destination_input.getText().toString().trim();
                        date = date_input.getText().toString().trim();
                        risk = risk_input.getText().toString().trim();
                        description = description_input.getText().toString().trim();
                        myDB.updateData(id, name, destination, date, risk, description);
                        if (name_input.length() == 0) {
                            name_input.setError("Please enter the name of the trip");
                        } else if (destination_input.length() == 0) {
                            destination_input.setError("Please enter the destination of the trip");
                        } else if (date_input.length() == 0) {
                            date_input.setError("Please enter the date of the trip");
                        } else if (risk_input.length() == 0) {
                            risk_input.setError("Please enter the risk of the trip");
                        } else {
                            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    // get all data from previous activity which is trip view activity
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

}
































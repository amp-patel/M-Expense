//Reference https://www.tutorialspoint.com/android/android_datepicker_control.htm
//https://learnandroidtoday.wordpress.com/2013/08/20/make-edittext-field-to-be-required/comment-page-1/
//Used university materials
package com.example.m_expense;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity {

    static AddActivity instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static AddActivity getInstance() {
        return instance;
    }
    private EditText name;
    private EditText destination;
    private TextView dateEdittext;
    private EditText risk;
    DatePickerDialog.OnDateSetListener setListener;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name = findViewById(R.id.trip_name);
        destination = (EditText)findViewById(R.id.trip_destination);
        dateEdittext = findViewById(R.id.trip_date);
        description = findViewById(R.id.trip_description);
        risk = findViewById(R.id.trip_risk);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //date picker by day, month and year formate.
        dateEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        dateEdittext.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // get all string from conformation activity
        Intent intentConfim = getIntent();
        String Name = intentConfim.getStringExtra("Name");
        String Destination = intentConfim.getStringExtra("Destination");
        String Date = intentConfim.getStringExtra("Date");
        String Risk = intentConfim.getStringExtra("Risk");
        String Description = intentConfim.getStringExtra("Description");

        //set all got strings from conformation activity to each and every components of trip activity
        try{

        name.setText(Name);
        destination.setText(Destination);
        dateEdittext.setText(Date);
        description.setText(Description);
        risk.setText(Risk);

        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        Button buttonOpenActivityConformation = findViewById(R.id.add_button);

        //submit details to conformation activity
        buttonOpenActivityConformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripName = name.getText().toString();
                String tripDestination = destination.getText().toString();
                String tripDate = dateEdittext.getText().toString();
                String tripRisk = risk.getText().toString();
                String tripDescription = description.getText().toString();

                //check if textfield is empty or not
                if (name.length() == 0) {
                    name.setError("Please enter the name of the trip");
                } else if (destination.length() == 0) {
                    destination.setError("Please enter the destination of the trip");
                } else if (dateEdittext.length() == 0) {
                    dateEdittext.setError("Please enter the date of the trip");
                } else if (risk.length() == 0) {
                    risk.setError("Please enter the risk: Yes / No");
                } else if (!tripRisk.equalsIgnoreCase("YES") && (!tripRisk.equalsIgnoreCase("NO"))) {
                    risk.setError("Only accept Yes / No");
                    risk.setText("");
                } else {

                    //transfer all details to conformation activity
                    Intent intent = new Intent(AddActivity.this, AddConfirmationActivity.class);
                    intent.putExtra("Name", tripName);
                    intent.putExtra("Destination", tripDestination);
                    intent.putExtra("Date", tripDate);
                    intent.putExtra("Risk", tripRisk);
                    intent.putExtra("Description", tripDescription);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }
}




























//Reference https://www.freakyjolly.com/android-sqlite-example-application-insert-update-delete-truncate-operations/
//Used university materials
package com.example.m_expense;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_confirmation);

        //get all details from trip addActivity activity

        String Name = null;
        String Destination = null;
        String Date = null;
        String Risk = null;
        String Description = null;
        try {
            Intent intent = getIntent();
            Name = intent.getStringExtra("Name");
            Destination = intent.getStringExtra("Destination");
            Date = intent.getStringExtra("Date");
            Risk = intent.getStringExtra("Risk");
            Description = intent.getStringExtra("Description");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //set all details to textfield for view
        TextView txtName = findViewById(R.id.trip_name);
        TextView txtDestination = findViewById(R.id.trip_destination);
        TextView txtDate = findViewById(R.id.trip_date);
        TextView txtRisk = findViewById(R.id.trip_risk);
        TextView txtDescription = findViewById(R.id.trip_description);
        txtName.setText(Name);
        txtDestination.setText(Destination);
        txtDate.setText(Date);
        txtRisk.setText(Risk);
        txtDescription.setText(Description);

        //rename of the activity by trip name
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Trip of " + Name + " Conformation");
        }
        if (Description.isEmpty()) {
            txtDescription.setText("");
        }
        Button buttonEdit = findViewById(R.id.edit_button);
        Button buttonConfirm = findViewById(R.id.confirm_button);

        //edit button for transfer all deatils to previous activity which is trip addActivity
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tripName = null;
                String tripDestination = null;
                String tripDate = null;
                String tripRisk = null;
                String tripDescription = null;
                try {
                    tripName = txtName.getText().toString();
                    tripDestination = txtDestination.getText().toString();
                    tripDate = txtDate.getText().toString();
                    tripRisk = txtRisk.getText().toString();
                    tripDescription = txtDescription.getText().toString();
                } catch (Exception e) {
                    Toast.makeText(AddConfirmationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
                Intent intent = new Intent(AddConfirmationActivity.this, AddActivity.class);
                intent.putExtra("Name", tripName);
                intent.putExtra("Destination", tripDestination);
                intent.putExtra("Date", tripDate);
                intent.putExtra("Risk", tripRisk);
                intent.putExtra("Description", tripDescription);
                startActivityForResult(intent, 1);
            }
        });

        //conform button for enter all details to database
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddConfirmationActivity.this);
                myDB.addTrip(txtName.getText().toString().trim(),
                        txtDestination.getText().toString().trim(),
                        txtDate.getText().toString().trim(),
                        txtRisk.getText().toString().trim(),
                        txtDescription.getText().toString().trim());
                Intent intent = new Intent(AddConfirmationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}



































//Reference https://programmerworld.co/android/how-to-store-images-in-sqlite-database-insert-update-delete-and-fetch-in-your-android-app/
//https://www.tutorialspoint.com/get-current-time-and-date-on-android
//https://www.tutlane.com/tutorial/android/android-timepicker-with-examples
//Used university materials
package com.example.m_expense;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.m_expense.Util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddExpensesActivity extends AppCompatActivity {

    public String id, name,destination,date,risk,description;
    int hour, minutes;
    MyDatabaseHelper myDB;
    private EditText typeExpense;
    private EditText amountExpense;
    private TextView timeExpense, imageRequireFieldTXT;
    private EditText commentExpense;
    ArrayList<String> id2forimage;
    public Bitmap captureImage;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);
        imageView = findViewById(R.id.image_view);

        //permission for accessing the physical camera of mobile
        if(ContextCompat.checkSelfPermission(AddExpensesActivity.this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(AddExpensesActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        //instance of mydatabasehelper class for accessing database features
        myDB = new MyDatabaseHelper(AddExpensesActivity.this);
        id2forimage = new ArrayList<>();

        //open camera view to capture images
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });
        try {
            id = getIntent().getStringExtra("Id");
            name = getIntent().getStringExtra("Name");
        }
        catch (Exception e) {
            Toast.makeText(AddExpensesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
        storeDataInArrays();
        typeExpense = findViewById(R.id.expenses_type);
        amountExpense = findViewById(R.id.expenses_amount);
        timeExpense = findViewById(R.id.expenses_time);
        commentExpense = findViewById(R.id.expenses_comments);
        imageRequireFieldTXT = findViewById(R.id.imageRequireField);

        //set name of the activity based of trip name
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Add Expenses for a trip of "+name);
        }

        //get current time automatically
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        timeExpense.setText(currentTime);
        Button buttonOpenActivityConformation = findViewById(R.id.add_Expense_button);

        //add new expenses
        buttonOpenActivityConformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sType= typeExpense.getText().toString().trim();
                String sAmount = amountExpense.getText().toString().trim();
                String sTime = timeExpense.getText().toString().trim();
                String sComment = commentExpense.getText().toString().trim();

                //validation
                if (typeExpense.length()==0)
                {
                    typeExpense.setError("Please enter the type of the expense");
                }
                else if(amountExpense.length()==0)
                {
                    amountExpense.setError("Please enter the amount of the expense");
                }
                else if(timeExpense.length()==0)
                {
                    timeExpense.setError("Please enter the time of the expense");
                }
                else
                {
                    Drawable d = imageView.getDrawable();
                    captureImage = ((BitmapDrawable)d).getBitmap();
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddExpensesActivity.this);
                    myDB.addExpenses(typeExpense.getText().toString().trim(),amountExpense.getText().toString().trim(), timeExpense.getText().toString().trim(), commentExpense.getText().toString().trim(), id,typeExpense.getText().toString().trim()+"/"+amountExpense.getText().toString().trim()+"/"+timeExpense.getText().toString().trim(), Utils.getBytes(captureImage));
                    Intent intent = new Intent(AddExpensesActivity.this, ExpensesViewActivity.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("Name",name);
                    startActivity(intent);
                }
            }
        });
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readTripData2(id);
        if(cursor.getCount() == 0)
        {
            Log.d("msg: ","no data");
        }
        else
        {
            while (cursor.moveToNext())
            {
                id2forimage.add(cursor.getString(0));
                Log.d("msg: ","no data"+id);
            }
        }
    }

    //show captured image on screen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100)
        {
            Log.d("Second","Second");
            captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //time picker to change time
    public void TimePicker(View view) {
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            minutes = minute;
            timeExpense.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minutes));
        }
    };
    TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minutes,true);
    timePickerDialog.setTitle("Select Time");
    timePickerDialog.show();
}
}



























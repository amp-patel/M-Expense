//Reference https://guides.codepath.com/android/using-the-recyclerview
//https://www.freakyjolly.com/android-sqlite-example-application-insert-update-delete-truncate-operations/
//Used university materials
package com.example.m_expense;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpensesViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addExpencefloatButton;
    CustomAdapter2 customAdapter2;
    MyDatabaseHelper myDB;
    String Id,Name;
    ArrayList<String> id2,typeExpense,amountExpense,timeExpense,commentExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_view);
        recyclerView = findViewById(R.id.recycleView2);
        addExpencefloatButton = findViewById(R.id.addFloatExpenseButton);

        //add new expense
        addExpencefloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpensesViewActivity.this, AddExpensesActivity.class);
                intent.putExtra("Id", Id);
                intent.putExtra("Name", Name);
                startActivityForResult(intent, 1);
            }
        });
        try {
            Id = getIntent().getStringExtra("Id");
            Name = getIntent().getStringExtra("Name");
        }
        catch (Exception e) {
            Toast.makeText(ExpensesViewActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        //set name of the activity based of trip name
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Expenses for trip of "+Name);
        }

        //database instace for manipulate database features
        myDB = new MyDatabaseHelper(ExpensesViewActivity.this);
        id2 = new ArrayList<>();
        typeExpense = new ArrayList<>();
        amountExpense = new ArrayList<>();
        timeExpense = new ArrayList<>();
        commentExpense = new ArrayList<>();
        storeDataInArrays();
        customAdapter2 = new CustomAdapter2(ExpensesViewActivity.this, id2,typeExpense,amountExpense,timeExpense,commentExpense);
        recyclerView.setAdapter(customAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpensesViewActivity.this));
    }

    //ghet all data from database
    void storeDataInArrays()
    {
        Cursor cursor = myDB.readTripData2(Id);
        if(cursor.getCount() == 0)
        {
            Log.d("msg: ","no data");
        }
        else
        {
            while (cursor.moveToNext())
            {
                id2.add(cursor.getString(0));
                typeExpense.add(cursor.getString(1));
                amountExpense.add(cursor.getString(2));
                timeExpense.add(cursor.getString(3));
                commentExpense.add(cursor.getString(4));
                Log.d("msg: "," "+typeExpense);
                Log.d("id:"," "+Id);
            }
        }
    }
}






















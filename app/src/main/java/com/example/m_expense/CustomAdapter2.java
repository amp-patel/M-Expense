//Reference https://guides.codepath.com/android/using-the-recyclerview
//Used university materials
package com.example.m_expense;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder> {

    private Context context;
    private ArrayList expenses_id,expenses_type,expenses_amount,expenses_time,expenses_comment;
    CustomAdapter2(Context context, ArrayList expenses_id, ArrayList expenses_type, ArrayList expenses_amount, ArrayList expenses_time, ArrayList expenses_comment)
    {
        this.context = context;
        this.expenses_type = expenses_type;
        this.expenses_amount = expenses_amount;
        this.expenses_time = expenses_time;
        this.expenses_comment = expenses_comment;
        this.expenses_id = expenses_id;
    }

    //show each row for expenses
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row2,parent,false);
        return new MyViewHolder(view);
    }

    //print all details on textview
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.expense_type_txt.setText(String.valueOf(expenses_type.get(position)));
        Log.d("CustomerAdapter2","pass");
        holder.expense_amount_txt.setText("Amount: "+String.valueOf(expenses_amount.get(position)));
        holder.expense_time_txt.setText("Time: "+String.valueOf(expenses_time.get(position)));
        holder.expense_comment_txt.setText(String.valueOf(expenses_comment.get(position)));
    }

    @Override
    public int getItemCount()
    {
        return expenses_id.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView expense_id_txt, expense_type_txt,expense_amount_txt,expense_time_txt,expense_comment_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_id_txt = itemView.findViewById(R.id.expenses_id_txt2);
            expense_type_txt = itemView.findViewById(R.id.expenses_type_txt2);
            expense_amount_txt = itemView.findViewById(R.id.expense_amount_txt2);
            expense_time_txt = itemView.findViewById(R.id.expense_time_txt2);
            expense_comment_txt = itemView.findViewById(R.id.expense_comment_txt2);
        }
    }
}
















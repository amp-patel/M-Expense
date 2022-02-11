//Reference for search trips: https://github.com/trulymittal/RecyclerView/tree/search_filter_recycler
//https://guides.codepath.com/android/using-the-recyclerview
//Used university materials
package com.example.m_expense;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_expense.Model.TripModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private Context context;
    // private ArrayList trip_id, trip_name, trip_destination, trip_date, trip_risk, trip_description;

    //  private ArrayList search_trip_name;

    Animation transalate_anim;


    Activity activity;

    /*   List<String> tripName;
       List<String> tripDestination;
       List<String> tripDate;
       List<String> tripAll;*/
    ArrayList<TripModel>tripModels;
    ArrayList<TripModel>tripModelsFiltered;

    CustomAdapter(Activity activity, Context context, ArrayList<TripModel>tripModels)
    {
        this.tripModels=tripModels;
        this.tripModelsFiltered=tripModels;
        this.activity = activity;
        this.context = context;
       /* this.trip_id = trip_id;
        this.trip_name = trip_name;
        this.trip_destination = trip_destination;
        this.trip_date = trip_date;
        this.trip_risk = trip_risk;
        this.trip_description = trip_description;

        search_trip_name = trip_name;*/


     /*   tripName = new ArrayList<>();
        tripDestination = new ArrayList<>();
        tripDate = new ArrayList<>();*/


       /* for(int i=0; i < trip_id.size(); i++){
            this.tripName.add(this.trip_name.get(i).toString());
        }
        for(int i=0; i < trip_id.size(); i++){
            this.tripDestination.add(this.trip_destination.get(i).toString());
        }
        for(int i=0; i < trip_id.size(); i++){
            this.tripDate.add(this.trip_date.get(i).toString());
        }


        tripAll = new ArrayList<>();
        tripAll.addAll(tripName);*/
//        tripAll.addAll(tripDestination);
//        tripAll.addAll(tripDate);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_row,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {



//        holder.trip_id_txt.setText(String.valueOf(tripId.get(position)));
//        holder.trip_name_txt.setText(String.valueOf(trip_name.get(position)));

        ///imp
//        holder.trip_destination_txt.setText(String.valueOf(trip_destination.get(position)));
//        holder.trip_date_txt.setText(String.valueOf(trip_date.get(position)));
//        holder.trip_id_txt.setText(movieList.get(position));



        holder.trip_name_txt.setText(tripModelsFiltered.get(position).getTripName());
//        holder.trip_destination_txt.setText(tripDestination.get(position));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TripViewActivity.class);
                intent.putExtra("id",  String.valueOf(tripModelsFiltered.get(position).getTripId()));
                intent.putExtra("name", tripModelsFiltered.get(position).getTripName());
                intent.putExtra("destination",tripModelsFiltered.get(position).getTripDest());
                intent.putExtra("date", tripModelsFiltered.get(position).getTripDate());
                intent.putExtra("risk", tripModelsFiltered.get(position).getTripRisk());
                intent.putExtra("description", tripModelsFiltered.get(position).getTripDesc());
                activity.startActivityForResult(intent,1);
            }
        });


//        holder.trip_id_txt.setText(tripId.get(position));
//        holder.trip_destination_txt.setText(movieList.get(position));
//        holder.trip_date_txt.setText(movieList.get(position));
//        holder.trip_id_txt.setText(movieList.get(position));
//        holder.trip_name_txt.setText(movieList.get(position));
//        holder.trip_destination_txt.setText(movieList.get(position));
//        holder.trip_date_txt.setText(movieList.get(position));


    }

    @Override
    public int getItemCount() {
        return tripModelsFiltered.size();
    }

   /* @Override
    public Filter getFilter() {
        return filter;
    }*/

    /*   Filter filter = new Filter() {
           @Override
           protected FilterResults performFiltering(CharSequence charSequence) {

   //            List<String> filteredList = new ArrayList<>();
   //
   //            if (charSequence == null || charSequence.length() == 0) {
   //                filteredList.addAll(tripAll);
   //            } else {
   //                for (String movie: tripAll) {
   //                    if (movie.toLowerCase().contains(charSequence.toString().toLowerCase())) {
   //                        filteredList.add(movie);
   //                    }
   //                }
   //            }
   //
   //            FilterResults filterResults = new FilterResults();
   //            filterResults.values = filteredList;
   //            return filterResults;

               ArrayList filteredList = new ArrayList();
               if(charSequence.toString().isEmpty())
               {
                   filteredList.addAll(search_trip_name);
               }
               else
               {
                   for(Object name: search_trip_name)
                   {
                       if(name.toString().toLowerCase().contains(charSequence.toString().toLowerCase()))
                       {
                           filteredList.add(name);
                       }
                   }
               }
               FilterResults filterResults = new FilterResults();
               filterResults.values=filteredList;
               return filterResults;
           }

           @Override
           protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

               tripName.clear();
               tripName.addAll((Collection<? extends String>) filterResults.values);
               notifyDataSetChanged();
           }
       };*/
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = tripModels.size();
                    filterResults.values = tripModels;

                }else{
                    List<TripModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(TripModel itemsModel:tripModels){
                        if(itemsModel.getTripName().contains(searchStr) || itemsModel.getTripDest().contains(searchStr)|| itemsModel.getTripDate().contains(searchStr)|| itemsModel.getTripDesc().contains(searchStr)){
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                tripModelsFiltered = (ArrayList<TripModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trip_id_txt, trip_name_txt, trip_destination_txt, trip_date_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_name_txt = itemView.findViewById(R.id.trip_name_txt);
            trip_destination_txt = itemView.findViewById(R.id.trip_destination_txt);
            trip_date_txt = itemView.findViewById(R.id.trip_date_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            transalate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(transalate_anim);
        }
    }
}



















































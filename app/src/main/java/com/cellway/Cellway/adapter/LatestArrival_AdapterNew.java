package com.cellway.Cellway.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.fragment.Book;
import com.cellway.Cellway.fragment.HomePage;
import com.cellway.Cellway.model.Product_Description;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LatestArrival_AdapterNew extends RecyclerView.Adapter<LatestArrival_AdapterNew.LatestViewHolder> {

    Context context;
    ArrayList<String> lstArvl;

    public LatestArrival_AdapterNew(Context context, ArrayList<String> lstArvl) {
        this.context = context;
        this.lstArvl = lstArvl;
    }


    @NonNull
    @Override
    public LatestArrival_AdapterNew.LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_latestarrival_new,parent,false);
        return new LatestArrival_AdapterNew.LatestViewHolder(view);
    }


    @Override
    public void onBindViewHolder(LatestArrival_AdapterNew.LatestViewHolder holder, int position) {

        holder.mobileName.setText(lstArvl.get(position));

    }


    @Override
    public int getItemCount() {

        return lstArvl.size();

    }

    public class LatestViewHolder extends RecyclerView.ViewHolder{

        TextView mobileName;
        CardView cardLatest;
        public LatestViewHolder(@NonNull View itemView) {
            super(itemView);

            mobileName = itemView.findViewById(R.id.mobileName);
            cardLatest = itemView.findViewById(R.id.cardLatest);

        }
    }
} 

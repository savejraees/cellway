package com.cellway.Cellway.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.activity.LoginActivityNew;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CategoryLatestModel;
import com.cellway.Cellway.util.SessonManager;

import java.util.ArrayList;

public class LatestAccesorisAdapter extends RecyclerView.Adapter<LatestAccesorisAdapter.LatestViewHolder> {

    Context context;
    ArrayList<CategoryLatestModel> lstArvl;
    SessonManager sessonManager;

    public LatestAccesorisAdapter(Context context, ArrayList<CategoryLatestModel> lstArvl) {
        this.context = context;
        this.lstArvl = lstArvl;
        sessonManager = new SessonManager(context);
    }


    @NonNull
    @Override
    public LatestAccesorisAdapter.LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_latest_access,parent,false);
        return new LatestAccesorisAdapter.LatestViewHolder(view);
    }


    @Override
    public void onBindViewHolder(LatestAccesorisAdapter.LatestViewHolder holder, int position) {
        CategoryLatestModel model = lstArvl.get(position);
        holder.accessName.setText(model.getName());
        holder.access_rate.setText(model.getPrice()+"/-");
        Glide.with(context).load(model.getProductImg()).into(holder.img_latst_access);

//        if(sessonManager.getToken().isEmpty()||sessonManager.getToken().equals("")){
//            context.startActivity(new Intent(context, LoginActivityNew.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        }else {
//
//        }

    }


    @Override
    public int getItemCount() {
        return lstArvl.size();
    }

    public class LatestViewHolder extends RecyclerView.ViewHolder{

        TextView accessName,access_rate;
        CardView cardLatestAccess;
        ImageView img_latst_access;
        public LatestViewHolder(@NonNull View itemView) {
            super(itemView);

            accessName = itemView.findViewById(R.id.accessName);
            access_rate = itemView.findViewById(R.id.access_rate);
            cardLatestAccess = itemView.findViewById(R.id.cardLatestAccess);
            img_latst_access = itemView.findViewById(R.id.img_latst_access);

        }
    }
} 


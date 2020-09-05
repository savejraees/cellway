package com.cellway.Cellway.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.activity.GenuineKnowMoreActivity;
import com.cellway.Cellway.activity.LoginActivityNew;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestGenuine;
import com.cellway.Cellway.util.SessonManager;

import java.util.ArrayList;

public class GenuineAdapter extends RecyclerView.Adapter<GenuineAdapter.ProductViewHolder>{

    Context context;
    ArrayList<MobDestGenuine> arrProduct;
    SessonManager sessonManager;

    public GenuineAdapter(Context context, ArrayList<MobDestGenuine> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
        sessonManager = new SessonManager(context);
    }


    @NonNull
    @Override
    public GenuineAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_genuine,parent,false);
        return new GenuineAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull GenuineAdapter.ProductViewHolder holder, int position) {
        final MobDestGenuine model = arrProduct.get(position);
        Glide.with(context).load(model.getImage()).into(holder.imgGen1);
         holder.cardGen.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(!sessonManager.getToken().isEmpty()){
                     context.startActivity(new Intent(context, GenuineKnowMoreActivity.class)
                             .putExtra("id",""+model.getId())
                             .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                 }else {
                     context.startActivity(new Intent(context, LoginActivityNew.class));
                 }

             }
         });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgGen1;
        CardView cardGen;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGen1 = itemView.findViewById(R.id.imgGen1);
            cardGen = itemView.findViewById(R.id.cardGen);

        }
    }
}


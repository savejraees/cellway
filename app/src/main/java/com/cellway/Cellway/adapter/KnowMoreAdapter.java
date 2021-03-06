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
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestKnowMore;
import com.cellway.Cellway.util.SessonManager;

import java.util.ArrayList;

public class KnowMoreAdapter extends RecyclerView.Adapter<KnowMoreAdapter.ProductViewHolder>{

    Context context;
    ArrayList<MobDestKnowMore> arrProduct;
    SessonManager sessonManager;

    public KnowMoreAdapter(Context context, ArrayList<MobDestKnowMore> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
        sessonManager = new SessonManager(context);
    }


    @NonNull
    @Override
    public KnowMoreAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_know_more,parent,false);
        return new KnowMoreAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull KnowMoreAdapter.ProductViewHolder holder, int position) {
        final MobDestKnowMore model = arrProduct.get(position);
        Glide.with(context).load(model.getImage()).into(holder.imgKnow);
        holder.cardKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sessonManager.getToken().isEmpty()){
//                    context.startActivity(new Intent(context, GenuineKnowMoreActivity.class)
//                            .putExtra("id",""+model.getId())
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }else {
                    context.startActivity(new Intent(context, LoginActivityNew.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgKnow;
        CardView cardKnow;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKnow = itemView.findViewById(R.id.imgKnow);
            cardKnow = itemView.findViewById(R.id.cardKnow);
        }
    }
}




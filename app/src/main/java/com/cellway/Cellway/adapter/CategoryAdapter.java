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
import com.cellway.Cellway.activity.BrandListActivity;
import com.cellway.Cellway.activity.GenuineKnowMoreActivity;
import com.cellway.Cellway.activity.LoginActivityNew;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestGenuine;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestShopCategory;
import com.cellway.Cellway.util.SessonManager;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder>{

    Context context;
    ArrayList<MobDestShopCategory> arrProduct;
    SessonManager sessonManager;

    public CategoryAdapter(Context context, ArrayList<MobDestShopCategory> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
        sessonManager = new SessonManager(context);
    }


    @NonNull
    @Override
    public CategoryAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_category,parent,false);
        return new CategoryAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ProductViewHolder holder, int position) {
        final MobDestShopCategory model = arrProduct.get(position);
        Glide.with(context).load(model.getImage()).into(holder.imgCateg);
        holder.txtCateg.setText(model.getTitle());
        holder.imgCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!sessonManager.getToken().isEmpty()){
                    context.startActivity(new Intent(context, BrandListActivity.class)
                            .putExtra("type",""+model.getTitle())
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                else {
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
        ImageView imgCateg,imgCatNote;
        CardView cardCateg;
        TextView txtCateg;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCateg = itemView.findViewById(R.id.imgCateg);
            cardCateg = itemView.findViewById(R.id.cardCateg);
            txtCateg = itemView.findViewById(R.id.txtCateg);
            imgCatNote = itemView.findViewById(R.id.imgCatNote);

        }
    }
}



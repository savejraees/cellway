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
import com.cellway.Cellway.activity.AccesoriesSubCategoryActivity;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CategoryDatumModel;

import java.util.ArrayList;

public class AccessoriesColorAdapter extends RecyclerView.Adapter<AccessoriesColorAdapter.ProductViewHolder>{

    Context context;
    ArrayList<String> arrProduct;

    public AccessoriesColorAdapter(Context context, ArrayList<String> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public AccessoriesColorAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_color_accessories,parent,false);
        return new AccessoriesColorAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AccessoriesColorAdapter.ProductViewHolder holder, final int position) {
        // final MobDestShopCategory model = arrProduct.get(position);
      //  Glide.with(context).load(arrProduct.get(position)).into(holder.imgCategAccess);
        holder.txtColorAccessories.setText(arrProduct.get(position));

    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgColorAccess;
        TextView txtColorAccessories;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgColorAccess = itemView.findViewById(R.id.imgColorAccess);
            txtColorAccessories = itemView.findViewById(R.id.txtColorAccessories);


        }
    }
}


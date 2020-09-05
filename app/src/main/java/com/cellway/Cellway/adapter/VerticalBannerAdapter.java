package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VerticalBannerAdapter extends RecyclerView.Adapter<VerticalBannerAdapter.ProductViewHolder>{

    Context context;
    ArrayList<Integer> arrProduct;

    public VerticalBannerAdapter(Context context, ArrayList<Integer> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public VerticalBannerAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_vertical_banner,parent,false);
        return new VerticalBannerAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VerticalBannerAdapter.ProductViewHolder holder, int position) {
        Picasso.get().load(arrProduct.get(position)).into(holder.imgThree);

    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgThree;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThree = itemView.findViewById(R.id.imgVertical);


        }
    }
}


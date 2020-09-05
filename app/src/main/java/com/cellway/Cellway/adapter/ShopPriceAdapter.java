package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestShopPrice;

import java.util.ArrayList;

public class ShopPriceAdapter extends RecyclerView.Adapter<ShopPriceAdapter.ProductViewHolder>{

    Context context;
    ArrayList<MobDestShopPrice> arrProduct;

    public ShopPriceAdapter(Context context, ArrayList<MobDestShopPrice> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public ShopPriceAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_shop_price,parent,false);
        return new ShopPriceAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ShopPriceAdapter.ProductViewHolder holder, int position) {
        MobDestShopPrice model = arrProduct.get(position);
        Glide.with(context).load(model.getImage()).into(holder.imgPriceShop);

    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPriceShop;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPriceShop = itemView.findViewById(R.id.imgPriceShop);


        }
    }
}



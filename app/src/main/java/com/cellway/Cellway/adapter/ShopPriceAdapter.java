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
import com.cellway.Cellway.activity.ProductPriceActivity;
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
    public void onBindViewHolder(@NonNull ShopPriceAdapter.ProductViewHolder holder, final int position) {
        MobDestShopPrice model = arrProduct.get(position);
        Glide.with(context).load(model.getImage()).into(holder.imgPriceShop);
              holder.cardPrice.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(position==0){
                         context.startActivity(new Intent(context, ProductPriceActivity.class)
                                 .putExtra("p1","Below")
                                 .putExtra("p2","7000")
                         .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                      }
                      else if(position==1){
                         context.startActivity(new Intent(context, ProductPriceActivity.class)
                                 .putExtra("p1","7000")
                                 .putExtra("p2","15000")
                         .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                      }
                      else if(position==2){
                         context.startActivity(new Intent(context, ProductPriceActivity.class)
                                 .putExtra("p1","15000")
                                 .putExtra("p2","25000")
                         .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                      }
                      else if(position==3){
                         context.startActivity(new Intent(context, ProductPriceActivity.class)
                                 .putExtra("p1","25000")
                                 .putExtra("p2","35000")
                         .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                      }
                      else if(position==4){
                         context.startActivity(new Intent(context, ProductPriceActivity.class)
                                 .putExtra("p1","35000")
                                 .putExtra("p2","1500000")
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
        ImageView imgPriceShop;
        CardView cardPrice;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPriceShop = itemView.findViewById(R.id.imgPriceShop);
            cardPrice = itemView.findViewById(R.id.cardPrice);
        }
    }
}



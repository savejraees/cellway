package com.cellway.Cellway.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestBestSeller;

import java.util.ArrayList;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.ProductViewHolder>{

    Context context;
    ArrayList<MobDestBestSeller> arrProduct;

    public BestSellerAdapter(Context context, ArrayList<MobDestBestSeller> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public BestSellerAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_best_seller,parent,false);
        return new BestSellerAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BestSellerAdapter.ProductViewHolder holder, int position) {
        MobDestBestSeller model = arrProduct.get(position);
        Glide.with(context).load(model.getImage()).into(holder.imgBestSeller);
        holder.txtBrandModelBest.setText(model.getBrand()+"\n"+model.getModelName());
        holder.txtGBBest.setText("Up to "+model.getGb()+"GB");
        holder.txtPriceBestSel.setText("FROM\n"+model.getSaleAmount()+"/-");
        holder.txtBatteryBest.setText("* "+model.getBattery()+" Battery");
        holder.txtCameraBest.setText("* "+model.getFrontCamera()+" Front Camera\n"+
                                        "* "+model.getRearCamera()+" Rear Camera");
    }

    @Override
    public int getItemCount() {
        Log.d("lk;;;;;;", String.valueOf(arrProduct.size()));
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBestSeller;
        TextView txtBrandModelBest,txtGBBest,txtPriceBestSel,txtCameraBest,txtBatteryBest,txtProcessorBest;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBestSeller = itemView.findViewById(R.id.imgBestSeller);
            txtBrandModelBest = itemView.findViewById(R.id.txtBrandModelBest);
            txtGBBest = itemView.findViewById(R.id.txtGBBest);
            txtPriceBestSel = itemView.findViewById(R.id.txtPriceBestSel);
            txtCameraBest = itemView.findViewById(R.id.txtCameraBest);
            txtBatteryBest = itemView.findViewById(R.id.txtBatteryBest);
            txtProcessorBest = itemView.findViewById(R.id.txtProcessorBest);

        }
    }
}


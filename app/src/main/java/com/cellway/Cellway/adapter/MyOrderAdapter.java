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

import com.cellway.Cellway.activity.MyOrderDetailActivity;
import com.cellway.Cellway.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ProductViewHolder>{

    Context context;
    ArrayList<Integer> arrProduct;

    public MyOrderAdapter(Context context, ArrayList<Integer> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_my_order,parent,false);
        return new MyOrderAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ProductViewHolder holder, int position) {
        Picasso.get().load(arrProduct.get(position)).into(holder.imgOrder);
        holder.cardMyAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MyOrderDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgOrder;
        CardView cardMyAdapter;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOrder = itemView.findViewById(R.id.imgOrder);
            cardMyAdapter = itemView.findViewById(R.id.cardMyAdapter);

        }
    }
}




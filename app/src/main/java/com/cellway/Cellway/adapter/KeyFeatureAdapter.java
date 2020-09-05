package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KeyFeatureAdapter extends RecyclerView.Adapter<KeyFeatureAdapter.ProductViewHolder>{

    Context context;
    ArrayList<String> arrProduct;

    public KeyFeatureAdapter(Context context, ArrayList<String> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public KeyFeatureAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_available_offer,parent,false);
        return new KeyFeatureAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull KeyFeatureAdapter.ProductViewHolder holder, int position) {
        holder.txtofferKey.setText(arrProduct.get(position));

    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgGo;
        TextView txtofferKey;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGo = itemView.findViewById(R.id.imgGo);
            txtofferKey = itemView.findViewById(R.id.txtofferKey);
            imgGo.setVisibility(View.GONE);

        }
    }
}


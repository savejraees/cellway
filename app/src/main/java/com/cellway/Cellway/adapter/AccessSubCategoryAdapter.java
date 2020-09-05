package com.cellway.Cellway.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.activity.AccesoiesProductActivity;
import com.cellway.Cellway.retrofitModel.AccessoriesSubCateegoryModel.SubCategoryDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccessSubCategoryAdapter extends RecyclerView.Adapter<AccessSubCategoryAdapter.ProductViewHolder>{

    Context context;
    ArrayList<SubCategoryDataModel> arrProduct;

    public AccessSubCategoryAdapter(Context context, ArrayList<SubCategoryDataModel> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;

    }


    @NonNull
    @Override
    public AccessSubCategoryAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_access_subcat,parent,false);
        return new AccessSubCategoryAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AccessSubCategoryAdapter.ProductViewHolder holder, final int position) {
        Picasso.get().load(arrProduct.get(position).getSubcatImg()).into(holder.imgSubCategAccess);
            holder.txtSubCategAccess.setText(arrProduct.get(position).getSubcatName());
        holder.cardSubCategAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AccesoiesProductActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("catId",arrProduct.get(position).getCatId())
                .putExtra("subCatId",arrProduct.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSubCategAccess;
        LinearLayout cardSubCategAccess;
        TextView txtSubCategAccess;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSubCategAccess = itemView.findViewById(R.id.imgSubCategAccess);
            cardSubCategAccess = itemView.findViewById(R.id.cardSubCategAccess);
            txtSubCategAccess = itemView.findViewById(R.id.txtSubCategAccess);


        }
    }
}


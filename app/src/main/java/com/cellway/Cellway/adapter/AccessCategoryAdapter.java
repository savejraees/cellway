package com.cellway.Cellway.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.cellway.Cellway.activity.BrandListActivity;
import com.cellway.Cellway.activity.LoginActivityNew;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CategoryDatumModel;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestShopCategory;
import com.cellway.Cellway.util.SessonManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccessCategoryAdapter extends RecyclerView.Adapter<AccessCategoryAdapter.ProductViewHolder>{

    Context context;
    ArrayList<CategoryDatumModel> arrProduct;
    SessonManager sessonManager;

    public AccessCategoryAdapter(Context context, ArrayList<CategoryDatumModel> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
        sessonManager = new SessonManager(context);
    }


    @NonNull
    @Override
    public AccessCategoryAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_access_category,parent,false);
        return new AccessCategoryAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AccessCategoryAdapter.ProductViewHolder holder, final int position) {
       // final MobDestShopCategory model = arrProduct.get(position);
//        Glide.with(context).load(arrProduct.get(position)).into(holder.imgCategAccess);
        Picasso.get().load(arrProduct.get(position).getCategoryImg()).placeholder(R.drawable.blue3).into(holder.imgCategAccess);

      //  holder.txtCategAccess.setText(arrProduct.get(position).getTitle());
        holder.cardCategAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessonManager.setCatIdAccess(arrProduct.get(position).getId());

                if(sessonManager.getToken().isEmpty()||sessonManager.getToken().equals("")){
                    context.startActivity(new Intent(context, LoginActivityNew.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }else {
                    context.startActivity(new Intent(context, AccesoriesSubCategoryActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("id",arrProduct.get(position).getId()));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCategAccess;
        CardView cardCategAccess;
      //  TextView txtCategAccess;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategAccess = itemView.findViewById(R.id.imgCategAccess);
            cardCategAccess = itemView.findViewById(R.id.cardCategAccess);
           // txtCategAccess = itemView.findViewById(R.id.txtCategAccess);


        }
    }
}




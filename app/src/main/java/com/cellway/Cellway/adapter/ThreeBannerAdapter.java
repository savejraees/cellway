package com.cellway.Cellway.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.fragment.Book;
import com.cellway.Cellway.model.Product_Description;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThreeBannerAdapter extends RecyclerView.Adapter<ThreeBannerAdapter.ProductViewHolder>{

    Context context;
    ArrayList<Integer> arrProduct;

    public ThreeBannerAdapter(Context context, ArrayList<Integer> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public ThreeBannerAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_three_banner,parent,false);
        return new ThreeBannerAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ThreeBannerAdapter.ProductViewHolder holder, int position) {
        Picasso.get().load(arrProduct.get(position)).into(holder.imgThree);

    }

    @Override
    public int getItemCount() {
        Log.d("lk;;;;;;", String.valueOf(arrProduct.size()));
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgThree;
       CardView cardthree;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThree = itemView.findViewById(R.id.imgThree);
            cardthree = itemView.findViewById(R.id.cardthree);
            if(arrProduct.size()==3){
                cardthree.getLayoutParams().height = 200;
                cardthree.requestLayout();
            }
            else if(arrProduct.size()==2){
                cardthree.getLayoutParams().height = 300;
                cardthree.requestLayout();
            }else {
                cardthree.getLayoutParams().height = 400;
                cardthree.requestLayout();
            }

        }
    }
}


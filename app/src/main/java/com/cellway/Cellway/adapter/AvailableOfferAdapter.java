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

import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingAvaiableModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AvailableOfferAdapter extends RecyclerView.Adapter<AvailableOfferAdapter.ProductViewHolder>{

    Context context;
    ArrayList<BookingAvaiableModel> arrProduct;

    public AvailableOfferAdapter(Context context, ArrayList<BookingAvaiableModel> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public AvailableOfferAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_available_offer,parent,false);
        return new AvailableOfferAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AvailableOfferAdapter.ProductViewHolder holder, int position) {
        holder.txtofferKey.setText(arrProduct.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPen;
        TextView txtofferKey;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPen = itemView.findViewById(R.id.imgPen);
            txtofferKey = itemView.findViewById(R.id.txtofferKey);

        }
    }
}


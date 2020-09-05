package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.BookingAccessoriesModel.BookingAccessoriesPaymentPolicy;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingPaymentPolicy;

import java.util.ArrayList;

public class PaymentAccessoriesAdapter extends RecyclerView.Adapter<PaymentAccessoriesAdapter.ProductViewHolder>{

    Context context;
    ArrayList<BookingAccessoriesPaymentPolicy> arrProduct;

    public PaymentAccessoriesAdapter(Context context, ArrayList<BookingAccessoriesPaymentPolicy> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public PaymentAccessoriesAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.rv_payment_option,parent,false);
        return new PaymentAccessoriesAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PaymentAccessoriesAdapter.ProductViewHolder holder, int position) {
        holder.txtPaymentOp.setText("* "+arrProduct.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txtPaymentOp;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPaymentOp = itemView.findViewById(R.id.txtPaymentOp);

        }
    }
}


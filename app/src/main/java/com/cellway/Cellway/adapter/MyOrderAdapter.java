package com.cellway.Cellway.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.activity.MyOrderDetailActivity;
import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.OrderHistoryModel.OrderHistoryStatusModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ProductViewHolder>{

    Context context;
    ArrayList<OrderHistoryStatusModel.Datum> arrProduct;

    public MyOrderAdapter(Context context, ArrayList<OrderHistoryStatusModel.Datum> arrProduct) {
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
        final OrderHistoryStatusModel.Datum model = arrProduct.get(position);
        holder.txtRefId.setText("RefID - "+model.getRefId());
       // holder.txtAddress.setText(model.getAddress());
        holder.txtDate.setText("Date - "+model.getCdate());
        holder.txtStatusOrder.setText("Status - "+model.getStatus());
        holder.txtPriceTotal.setText("₹ " +model.getTotalAmount());
        holder.txtDeliveryOrder.setText("₹ " +model.getDeliveryCharge());
        holder.txtShippingCharges.setText("₹ " +model.getShippingCharge());
        holder.txtbookAmountOrder.setText("- ₹ " +model.getBookingAmount());
        if(model.getBookingAmount()!=0){
            holder.txtAmountToPaid.setText("₹ " +String.valueOf(model.getTotalAmount()-model.getBookingAmount()));
        }else {
            holder.txtAmountToPaid.setText("Paid");
        }

//////////////////
        OrderHistoryStatusModel.Datum.Item itemModel = model.getItem();
        Picasso.get().load(itemModel.getImage()).into(holder.imgOrderMob);
        holder.txtBrandMod.setText(itemModel.getBrandName()+" "+itemModel.getModelName());
        holder.cardMyAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MyOrderDetailActivity.class)
                .putExtra("id",model.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
       // ImageView imgOrder;
        ImageView imgOrderMob;
        CardView cardMyAdapter;
        TextView txtRefId,txtPriceTotal,txtDeliveryOrder,txtShippingCharges,
                txtbookAmountOrder,txtAmountToPaid,txtDate,txtStatusOrder,txtBrandMod;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
           // imgOrder = itemView.findViewById(R.id.imgOrder);
            cardMyAdapter = itemView.findViewById(R.id.cardMyAdapter);
            txtRefId = itemView.findViewById(R.id.txtRefId);
          //  txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPriceTotal = itemView.findViewById(R.id.txtPriceTotal);
            txtDeliveryOrder = itemView.findViewById(R.id.txtDeliveryOrder);
            txtShippingCharges = itemView.findViewById(R.id.txtShippingCharges);
            txtbookAmountOrder = itemView.findViewById(R.id.txtbookAmountOrder);
            txtAmountToPaid = itemView.findViewById(R.id.txtAmountToPaid);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatusOrder = itemView.findViewById(R.id.txtStatusOrder);
            txtBrandMod = itemView.findViewById(R.id.txtBrandMod);
            imgOrderMob = itemView.findViewById(R.id.imgOrderMob);
        }
    }
}




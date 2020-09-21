package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.PaymentFormModel.PaymentFormStatusModel;

import java.util.ArrayList;

public class PaymentFormAdapter extends RecyclerView.Adapter<PaymentFormAdapter.ProductViewHolder> {

    Context context;
    ArrayList<PaymentFormStatusModel.Datum> arrProduct;
    private PaymentClick paymentClick;
    int index = -1;

    public PaymentFormAdapter(Context context, ArrayList<PaymentFormStatusModel.Datum> arrProduct,PaymentClick paymentClick) {
        this.context = context;
        this.arrProduct = arrProduct;
        this.paymentClick = paymentClick;
    }


    @NonNull
    @Override
    public PaymentFormAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_payment_form, parent, false);
        return new PaymentFormAdapter.ProductViewHolder(view,paymentClick);
    }


    @Override
    public void onBindViewHolder(@NonNull PaymentFormAdapter.ProductViewHolder holder, int position) {
        final PaymentFormStatusModel.Datum model = arrProduct.get(position);
        Glide.with(context).load(model.getImage()).into(holder.imgPayment);

         if(index == position){
             holder.chechPayment.setChecked(true);
         }else {
             holder.chechPayment.setChecked(false);
         }
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPayment;
        CheckBox chechPayment;
        private PaymentClick onClick;

        public ProductViewHolder(@NonNull View itemView, final PaymentClick onClick) {
            super(itemView);
            this.onClick = onClick;
            imgPayment = itemView.findViewById(R.id.imgPayment);
            chechPayment = itemView.findViewById(R.id.chechPayment);

            chechPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = getAdapterPosition();
                    if(chechPayment.isChecked()){
                        onClick.ClickPayment(getAdapterPosition());
                    }
//                    else {
//                        onClick.ClickPayment(-1);
//                    }

                }
            });
        }
    }

    public interface PaymentClick {
        void ClickPayment(int position);
    }
}
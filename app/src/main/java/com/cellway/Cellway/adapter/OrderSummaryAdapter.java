package com.cellway.Cellway.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.fragment.DeliveryFragment;
import com.cellway.Cellway.fragment.PickupFragment;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailDatumModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderSummaryAdapter  extends RecyclerView.Adapter<OrderSummaryAdapter.ProductViewHolder>{

    Context context;
    ArrayList<CartDetailDatumModel> arrProduct;
    int amount = 0;

    public OrderSummaryAdapter(Context context, ArrayList<CartDetailDatumModel> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public OrderSummaryAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_order_summary,parent,false);
        return new OrderSummaryAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.ProductViewHolder holder, int position) {
        Picasso.get().load(arrProduct.get(position).getProductImage()).into(holder.imgcart);
        final CartDetailDatumModel model = arrProduct.get(position);
        Picasso.get().load(model.getProductImage()).into(holder.imgcart);
        holder.txtBrandCart.setText(model.getBrandName() + " " + model.getModelName());
        int off = 0;
        if (arrProduct.get(position).getMrp() != 0) {
            off = 100 - (arrProduct.get(position).getPrice() * 100) / arrProduct.get(position).getMrp();
            holder.txtOffCart.setText(off + "%off");
        }
        amount = amount + arrProduct.get(position).getPrice();

        holder.original_price_Cart.setText("" + arrProduct.get(position).getMrp());
        if(arrProduct.get(position).getMrp()==0){
            holder.original_price_Cart.setVisibility(View.GONE);
        }
        holder.deduct_price_Cart.setText("" + arrProduct.get(position).getPrice());

        DeliveryFragment.PriceBookWithCOD( amount);
        PickupFragment.PriceBookPickup( amount);

    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgcart;
        TextView txtBrandCart, deduct_price_Cart, original_price_Cart, txtOffCart;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgcart = itemView.findViewById(R.id.imgOrder);
            txtBrandCart = itemView.findViewById(R.id.txtBrandOrder);
            //  txtGbCart = itemView.findViewById(R.id.txtGbCart);
            deduct_price_Cart = itemView.findViewById(R.id.deduct_price_order);
            original_price_Cart = itemView.findViewById(R.id.original_price_order);
            txtOffCart = itemView.findViewById(R.id.txtOffOrder);
            original_price_Cart.setPaintFlags(original_price_Cart.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}



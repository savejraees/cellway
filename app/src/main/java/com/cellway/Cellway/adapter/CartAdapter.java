package com.cellway.Cellway.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.util.Config;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.activity.CartActivity;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailDatumModel;
import com.cellway.Cellway.retrofitModel.CartModel.DeleteCartModel;
import com.cellway.Cellway.util.SessonManager;
import com.cellway.Cellway.util.Url;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProductViewHolder> {

    Context context;
    ArrayList<CartDetailDatumModel> arrProduct;
    SessonManager sessonManager;
    int amount = 0;

    public CartAdapter(Context context, ArrayList<CartDetailDatumModel> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public CartAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_cart, parent, false);
        return new CartAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ProductViewHolder holder, int position) {
        sessonManager = new SessonManager(context);
        final CartDetailDatumModel model = arrProduct.get(position);
        Picasso.get().load(model.getProductImage()).into(holder.imgcart);
        holder.txtBrandCart.setText(model.getBrandName() + " " + model.getModelName());
        int off = 0;
        if (arrProduct.get(position).getMrp() != 0) {
            off = 100 - (arrProduct.get(position).getPrice() * 100) / arrProduct.get(position).getMrp();
            holder.txtOffCart.setText(off + "%off");
        }
        if (arrProduct.get(position).getMrp() != 0) {
            holder.original_price_Cart.setText("" + arrProduct.get(position).getMrp() * arrProduct.get(position).getQty());
        }
        if (!arrProduct.get(position).getGb().equals("0")) {
            holder.txtGbCart.setText("("+arrProduct.get(position).getGb()+"GB)");
        }else {
            holder.txtGbCart.setVisibility(View.GONE);
        }

        holder.deduct_price_Cart.setText("" + arrProduct.get(position).getPrice() * arrProduct.get(position).getQty());
        if (arrProduct.get(position).getQty() == 1) {
            holder.txtQnty.setVisibility(View.GONE);
        } else {
            holder.txtQnty.setText("(" + arrProduct.get(position).getQty() + " items)");
        }


        amount = amount + (arrProduct.get(position).getPrice() * arrProduct.get(position).getQty());

        holder.relRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int phoneId = model.getId();
                int pos = holder.getAdapterPosition();
                int price = model.getPrice() * model.getQty();

                Log.d("hjaskjkjop", "" + phoneId + "  " + sessonManager.getToken());
                hitApiDelete(phoneId, pos, price);
            }
        });
        CartActivity.finalAmount("" + amount);
    }

    private void hitApiDelete(int phoneId, final int pos, final int mrp) {
        sessonManager.showProgress(context);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                        .build())
                .build();
        ;

        IApiServices api = retrofit.create(IApiServices.class);
        Call<DeleteCartModel> call = api.postDelete(Api.key, String.valueOf(phoneId), sessonManager.getToken());
        call.enqueue(new Callback<DeleteCartModel>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<DeleteCartModel> call, Response<DeleteCartModel> response) {
                sessonManager.hideProgress();
                if (response.isSuccessful()) {
                    DeleteCartModel model = response.body();

                    if (model.getCode().equals("200")) {
                        if (!model.getTotalqty().equals("0")) {
                            sessonManager.setQty(model.getTotalqty());
                            amount = amount - mrp;
                            CartActivity.finalAmount("" + amount);
                        } else {
                            amount = 0;
                            CartActivity.finalAmount("" + amount);
                            sessonManager.setQty("");
                        }

                        //   Toast.makeText(context, "" + model.getTotalqty(), Toast.LENGTH_SHORT).show();

                        removeItem(pos);
                    } else {
                        Toast.makeText(context, "" + model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<DeleteCartModel> call, Throwable t) {
                sessonManager.hideProgress();
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeItem(int position) {
        arrProduct.remove(position);
        notifyItemRemoved(position);
        if(arrProduct.size()==0){
            CartActivity.cardMissinig.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgcart;
        TextView txtBrandCart, deduct_price_Cart, original_price_Cart, txtOffCart, txtQnty, txtGbCart;
        RelativeLayout relRemove;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgcart = itemView.findViewById(R.id.imgcart);
            txtBrandCart = itemView.findViewById(R.id.txtBrandCart);
            //  txtGbCart = itemView.findViewById(R.id.txtGbCart);
            deduct_price_Cart = itemView.findViewById(R.id.deduct_price_Cart);
            original_price_Cart = itemView.findViewById(R.id.original_price_Cart);
            txtOffCart = itemView.findViewById(R.id.txtOffCart);
            relRemove = itemView.findViewById(R.id.relRemove);
            txtQnty = itemView.findViewById(R.id.txtQnty);
            txtGbCart = itemView.findViewById(R.id.txtGbCart);
            original_price_Cart.setPaintFlags(original_price_Cart.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}



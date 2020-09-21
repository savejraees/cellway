package com.cellway.Cellway.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.activity.MyOrderDetailActivity;
import com.cellway.Cellway.retrofitModel.CancelOrderModel;
import com.cellway.Cellway.retrofitModel.NeedHelpModel;
import com.cellway.Cellway.retrofitModel.OrderHistoryModel.OrderDetailDatum;
import com.cellway.Cellway.retrofitModel.OrderHistoryModel.OrderDetailStatusModel;
import com.cellway.Cellway.retrofitModel.OrderHistoryModel.OrderHistoryStatusModel;
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

public class MyOrderDetailAdapter extends RecyclerView.Adapter<MyOrderDetailAdapter.ProductViewHolder>{

    Context context;
    ArrayList<OrderDetailDatum> arrProduct;
    SessonManager sessonManager;
    Dialog dialog;
    EditText edtNeedHelp;
    Button btnSubmitHelp;

    public MyOrderDetailAdapter(Context context, ArrayList<OrderDetailDatum> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
        sessonManager = new SessonManager(context);
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_need_help);
        edtNeedHelp = dialog.findViewById(R.id.edtNeedHelp);
        btnSubmitHelp = dialog.findViewById(R.id.btnSubmitHelp);

    }

    @NonNull
    @Override
    public MyOrderDetailAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_order_details,parent,false);
        return new MyOrderDetailAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyOrderDetailAdapter.ProductViewHolder holder, int position) {
        final OrderDetailDatum model = arrProduct.get(position);
        if(model.getStatus()!=null){
            holder.txtStatus.setText("Status - "+model.getStatus());
        }

        holder.txtBrandModelGB.setText(model.getBrandName()+" "+model.getModelName()+"("+model.getGb()+" GB)");
        holder.txtPriceOrder.setText(""+model.getPrice());
      if(model.getIsCancel()==0){
          holder.txtCancel.setVisibility(View.VISIBLE);
      }else {
          holder.txtCancel.setText("Cancelled");
      }
      if(model.getIsReturn()==1){
          holder.txtReturn.setVisibility(View.VISIBLE);
      }else {
          holder.txtReturn.setText("No Return");
      }

        if(arrProduct.get(position).getProtectionPrice()!=0){
            holder.txtProtectionOrder.setVisibility(View.VISIBLE);
            holder.txtProtectionOrder.setText("Mobile Protection Price - ₹ "+arrProduct.get(position).getProtectionPrice());
        }
        if(arrProduct.get(position).getBackcoverPrice()!=0){
            holder.txtBackCoverOrder.setVisibility(View.VISIBLE);
            holder.txtBackCoverOrder.setText("Back Cover Price - ₹ "+arrProduct.get(position).getBackcoverPrice());
        }

        if(arrProduct.get(position).getTemperedPrice()!=0){
            holder.txtTempredOrder.setVisibility(View.VISIBLE);
            holder.txtTempredOrder.setText("Tempered Glass Price - ₹ "+arrProduct.get(position).getTemperedPrice());
        }
        holder.txtTotal.setText("₹ "+String.valueOf(model.getPrice()+model.getProtectionPrice()+model.getBackcoverPrice()+model.getTemperedPrice()));
        Picasso.get().load(model.getProductImage()).into(holder.imgDetail);

         holder.txtCancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Log.d("askwzs",""+model.getId());
                 if(holder.txtCancel.getText().toString().equals("Cancelled")){
                     Toast.makeText(context, "Order Already Cancelled", Toast.LENGTH_SHORT).show();
                 }else {
                     hitApiCancel(model.getId());
                 }

             }
         });
         holder.txtReturn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Log.d("askwzs",""+model.getId());
                 if(holder.txtReturn.getText().toString().equals("No Return")){
                     Toast.makeText(context, "Order Can not be Return", Toast.LENGTH_SHORT).show();
                 }else {
                     hitApiReturn(model.getId());
                 }

             }
         });
    }

    private void hitApiReturn(Integer id) {
        sessonManager.showProgress(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                        .build())
                .build();
        IApiServices api = retrofit.create(IApiServices.class);


        Call<CancelOrderModel> call = api.postReturnOrder(Api.key, id);
        call.enqueue(new Callback<CancelOrderModel>() {
            @Override
            public void onResponse(Call<CancelOrderModel> call, Response<CancelOrderModel> response) {

                if (response.isSuccessful()) {
                    sessonManager.hideProgress();
                    CancelOrderModel model = response.body();

                    if(model.getCode().equals("200")){
                        Toast.makeText(context, "Order Cancel Successfully", Toast.LENGTH_SHORT).show();
                        ((MyOrderDetailActivity)context).onBackPressed();
                    }
                    else{
                        Toast.makeText(context, "You have Already Cancelled Order", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<CancelOrderModel> call, Throwable t) {
                sessonManager.hideProgress();
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitApiCancel(int id) {
        sessonManager.showProgress(context);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                        .build())
                .build();
        IApiServices api = retrofit.create(IApiServices.class);


            Call<CancelOrderModel> call = api.postCancelOrder(Api.key, id);
            call.enqueue(new Callback<CancelOrderModel>() {
                @Override
                public void onResponse(Call<CancelOrderModel> call, Response<CancelOrderModel> response) {

                    if (response.isSuccessful()) {
                       sessonManager.hideProgress();
                        CancelOrderModel model = response.body();

                        if(model.getCode().equals("200")){
                            Toast.makeText(context, "Order Cancel Successfully", Toast.LENGTH_SHORT).show();
                            ((MyOrderDetailActivity)context).onBackPressed();
                        }
                        else{
                            Toast.makeText(context, "You have Already Cancelled Order", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<CancelOrderModel> call, Throwable t) {
                    sessonManager.hideProgress();
                    Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDetail;
        TextView txtStatus,txtBrandModelGB,txtPriceOrder,txtCancel,txtReturn,
                txtNeedHelp,txtProtectionOrder,txtBackCoverOrder,txtTempredOrder,txtTotal;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDetail = itemView.findViewById(R.id.imgDetail);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtBrandModelGB = itemView.findViewById(R.id.txtBrandModelGB);
            txtPriceOrder = itemView.findViewById(R.id.txtPriceOrder);
            txtCancel = itemView.findViewById(R.id.txtCancel);
            txtReturn = itemView.findViewById(R.id.txtReturn);
            txtNeedHelp = itemView.findViewById(R.id.txtNeedHelp);
            txtProtectionOrder = itemView.findViewById(R.id.txtProtectionOrder);
            txtBackCoverOrder = itemView.findViewById(R.id.txtBackCoverOrder);
            txtTempredOrder = itemView.findViewById(R.id.txtTempredOrder);
            txtTotal = itemView.findViewById(R.id.txtTotal);



            txtNeedHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.show();
                }
            });

            btnSubmitHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtNeedHelp.getText().toString().isEmpty()){
                        edtNeedHelp.setError("Can't be Blank");
                        edtNeedHelp.requestFocus();
                    }else {
                        dialog.dismiss();
                        hitNeedHelpApi();
                    }

                }
            });
        }

        private void hitNeedHelpApi() {
                sessonManager.showProgress(context);
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Url.BASE_URL)
                        .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY
                        ))
                                .build())
                        .build();
                IApiServices api = retrofit.create(IApiServices.class);
                Call<NeedHelpModel> call = api.postNeedHelp(Api.key, sessonManager.getToken(),edtNeedHelp.getText().toString());
                call.enqueue(new Callback<NeedHelpModel>() {
                    @Override
                    public void onResponse(Call<NeedHelpModel> call, Response<NeedHelpModel> response) {

                        if (response.isSuccessful()) {
                            sessonManager.hideProgress();
                            NeedHelpModel model = response.body();

                            if(model.getCode().equals("200")){
                                Toast.makeText(context, "Send Successfully", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(context, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<NeedHelpModel> call, Throwable t) {
                        sessonManager.hideProgress();
                        Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

    }
}

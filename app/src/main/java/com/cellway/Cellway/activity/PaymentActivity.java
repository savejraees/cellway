package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.PaymentFormAdapter;
import com.cellway.Cellway.retrofitModel.PaymentFormModel.PaymentFormStatusModel;
import com.cellway.Cellway.util.SessonManager;
import com.cellway.Cellway.util.Url;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends BaseActivity implements PaymentFormAdapter.PaymentClick{

    RecyclerView rvPayment;
    SessonManager sessonManager;
    Button btnPay;
    int id=-1;
    ArrayList<PaymentFormStatusModel.Datum> list = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        rvPayment = findViewById(R.id.rvPayment);
        btnPay = findViewById(R.id.btnPay);
        sessonManager = new SessonManager(PaymentActivity.this);
        hitApi();

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id==-1){
                    Toast.makeText(PaymentActivity.this, "Please Select Payment Method", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PaymentActivity.this, "Done", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hitApi() {
        sessonManager.showProgress(PaymentActivity.this);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                        .build())
                .build();
        IApiServices api = retrofit.create(IApiServices.class);
        Call<PaymentFormStatusModel> call = api.getPaymentFormDetail(Api.key);
        call.enqueue(new Callback<PaymentFormStatusModel>() {
            @Override
            public void onResponse(Call<PaymentFormStatusModel> call, Response<PaymentFormStatusModel> response) {

                if (response.isSuccessful()) {
                    sessonManager.hideProgress();
                    PaymentFormStatusModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {

                        list = StatusModel.getData();
                        setRVCart();
                    } else {
                        Toast.makeText(PaymentActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentFormStatusModel> call, Throwable t) {
                sessonManager.hideProgress();
                Toast.makeText(PaymentActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("slkklsaksax", "" + t.getMessage());
            }
        });
    }

    private void setRVCart() {
        rvPayment.setLayoutManager(new GridLayoutManager(PaymentActivity.this,2));
        rvPayment.setAdapter(new PaymentFormAdapter(PaymentActivity.this,list,this));
    }

    @Override
    public void ClickPayment(int position) {
        if(position==-1){
            id= -1;
        }else {
            id = list.get(position).getId();
        }
        Log.d("sajkfytr",""+position);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Order Cancel", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PaymentActivity.this, Home.class));
        finishAffinity();
    }
}
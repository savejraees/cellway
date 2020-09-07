package com.cellway.Cellway.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class CheckoutActivity extends BaseActivity implements PaymentFormAdapter.PaymentClick{

    RecyclerView rvPayment;
    SessonManager sessonManager;
    Button btnPay;
    int id=-1;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
     String OrderId;
    ArrayList<PaymentFormStatusModel.Datum> list = new ArrayList<>();
    int amount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        rvPayment = findViewById(R.id.rvPayment);
        btnPay = findViewById(R.id.btnPay);
        sessonManager = new SessonManager(CheckoutActivity.this);
        if(getIntent().hasExtra("amount")){
            amount = getIntent().getIntExtra("amount",0);
            OrderId =  getIntent().getStringExtra("orderId");
        }
        hitApi();

//////////// for genrate random string ///////////
       // randomAlphaNumeric(10);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id==-1){
                    Toast.makeText(CheckoutActivity.this, "Please Select Payment Method", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(PaymentActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CheckoutActivity.this,PaymentModeActivity.class)
                  .putExtra("id",OrderId)
                  .putExtra("amount",amount));
                }
            }
        });
    }

    // Java program generate a random AlphaNumeric String
   // using Math.random() method
//    public static String randomAlphaNumeric(int count) {
//        StringBuilder builder = new StringBuilder();
//        while (count-- != 0) {
//            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
//            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
//        }
//        OrderId = builder.toString();
//        return builder.toString();
//    }

    private void hitApi() {
        sessonManager.showProgress(CheckoutActivity.this);
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
                        Toast.makeText(CheckoutActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentFormStatusModel> call, Throwable t) {
                sessonManager.hideProgress();
                Toast.makeText(CheckoutActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("slkklsaksax", "" + t.getMessage());
            }
        });
    }

    private void setRVCart() {
        rvPayment.setLayoutManager(new GridLayoutManager(CheckoutActivity.this,2));
        rvPayment.setAdapter(new PaymentFormAdapter(CheckoutActivity.this,list,this));
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
       super.onBackPressed();
    }
}
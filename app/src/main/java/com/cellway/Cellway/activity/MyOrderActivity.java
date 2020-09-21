package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.MyOrderAdapter;
import com.cellway.Cellway.retrofitModel.OrderHistoryModel.OrderHistoryStatusModel;
import com.cellway.Cellway.util.SessonManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends BaseActivity {

    RecyclerView rvMyOrder;
    ArrayList<OrderHistoryStatusModel.Datum> historyList = new ArrayList<>();
    SessonManager sessonManager;
    ImageView imgBackMyOrder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        rvMyOrder = findViewById(R.id.rvMyOrder);
        imgBackMyOrder = findViewById(R.id.imgBackMyOrder);
        sessonManager = new SessonManager(MyOrderActivity.this);
        imgBackMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyOrderActivity.this, Home.class));
                finishAffinity();
            }
        });
        hitApi();
    }

    private void hitApi() {
        showProgress(MyOrderActivity.this);
        Call<OrderHistoryStatusModel> call = api.getOrderHistory(Api.key,sessonManager.getToken());
        call.enqueue(new Callback<OrderHistoryStatusModel>() {
            @Override
            public void onResponse(Call<OrderHistoryStatusModel> call, Response<OrderHistoryStatusModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    OrderHistoryStatusModel model = response.body();
                    historyList = model.getData();
                    if(model.getCode().equals("200")){
                        setRVOrder();
                    }

                    else{
                        Toast.makeText(MyOrderActivity.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderHistoryStatusModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(MyOrderActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRVOrder() {


        rvMyOrder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this, 1));
        rvMyOrder.setAdapter(new MyOrderAdapter(MyOrderActivity.this, historyList));
    }
}
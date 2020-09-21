package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.MyOrderAdapter;
import com.cellway.Cellway.adapter.MyOrderDetailAdapter;
import com.cellway.Cellway.retrofitModel.OrderHistoryModel.OrderDetailDatum;
import com.cellway.Cellway.retrofitModel.OrderHistoryModel.OrderDetailStatusModel;
import com.cellway.Cellway.retrofitModel.OrderHistoryModel.OrderDetailStatusModel;
import com.cellway.Cellway.util.SessonManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderDetailActivity extends BaseActivity {

    RecyclerView rvOrderDetails;
    SessonManager sessonManager;
    ArrayList<OrderDetailDatum> list = new ArrayList<>();
    ImageView imgBackOrderDetails;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);
        rvOrderDetails = findViewById(R.id.rvOrderDetails);
        imgBackOrderDetails = findViewById(R.id.imgBackOrderDetails);
        sessonManager = new SessonManager(MyOrderDetailActivity.this);
        if(getIntent().hasExtra("id")){
            int id = getIntent().getIntExtra("id",0);
            Log.d("asjkas",""+id);
            hitApi(id);
        }

        imgBackOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void hitApi(int id) {
        showProgress(MyOrderDetailActivity.this);
        Call<OrderDetailStatusModel> call = api.getOrderDetail(Api.key, String.valueOf(id));
        call.enqueue(new Callback<OrderDetailStatusModel>() {
            @Override
            public void onResponse(Call<OrderDetailStatusModel> call, Response<OrderDetailStatusModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    OrderDetailStatusModel model = response.body();
                    list = model.getData();
                    if(model.getCode().equals("200")){
                        setRVOrder();
                    }

                    else{
                        Toast.makeText(MyOrderDetailActivity.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderDetailStatusModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(MyOrderDetailActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRVOrder() {
        rvOrderDetails.setLayoutManager(new GridLayoutManager(MyOrderDetailActivity.this, 1));
        rvOrderDetails.setAdapter(new MyOrderDetailAdapter(MyOrderDetailActivity.this, list));
    }
}
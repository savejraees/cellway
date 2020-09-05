package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cellway.Cellway.R;
import com.cellway.Cellway.adapter.MyOrderAdapter;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {

    RecyclerView rvMyOrder;
    int[] img = {R.drawable.samsug_demo};
    ArrayList<Integer> cartList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        rvMyOrder = findViewById(R.id.rvMyOrder);
        setRVOrder();
    }

    private void setRVOrder() {
        for (int i = 0; i < img.length; i++) {
            cartList.add(img[i]);
        }

        rvMyOrder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this, 1));
        rvMyOrder.setAdapter(new MyOrderAdapter(MyOrderActivity.this, cartList));
    }
}
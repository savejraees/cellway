package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;

public class PaymentSuccessActivity extends AppCompatActivity {

    CardView cardShopMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        cardShopMore = findViewById(R.id.cardShopMore);

        cardShopMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentSuccessActivity.this, Home.class));
                finishAffinity();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
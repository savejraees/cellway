package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;

public class PaymentFailActivity extends AppCompatActivity {

    Button btnTryAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_fail);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentFailActivity.this, Home.class));
                finishAffinity();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
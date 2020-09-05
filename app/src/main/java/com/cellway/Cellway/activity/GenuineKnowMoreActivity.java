package com.cellway.Cellway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.retrofitModel.GenuineModel.GenuineKnowMoreModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenuineKnowMoreActivity extends BaseActivity {

    TextView txtGen;
    ImageView genImage,imgBackGen;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genuine_know_more);
        genImage = findViewById(R.id.genImage);
        txtGen = findViewById(R.id.txtGen);
        imgBackGen = findViewById(R.id.imgBackGen);

        if(getIntent().hasExtra("id")){
            String id = getIntent().getStringExtra("id");
            hitApi(id);
        }

        imgBackGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GenuineKnowMoreActivity.this,Mobile_DestinationNewActivity.class));
                finish();
            }
        });
    }

    private void hitApi(String id) {
        showProgress(GenuineKnowMoreActivity.this);
        Call<GenuineKnowMoreModel> call = api.getGenKnow(Api.key,id);
        call.enqueue(new Callback<GenuineKnowMoreModel>() {
            @Override
            public void onResponse(Call<GenuineKnowMoreModel> call, Response<GenuineKnowMoreModel> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    GenuineKnowMoreModel GenuineKnowMoreModel = response.body();
                    Glide.with(GenuineKnowMoreActivity.this).load(GenuineKnowMoreModel.getImage()).into(genImage);
                    txtGen.setText(GenuineKnowMoreModel.getDescription());
                }
            }

            @Override
            public void onFailure(Call<GenuineKnowMoreModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(GenuineKnowMoreActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
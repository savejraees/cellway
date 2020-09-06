package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.AddressAdapter;
import com.cellway.Cellway.retrofitModel.AddressModel.AddressDatumModel;
import com.cellway.Cellway.retrofitModel.AddressModel.AddressStatusModel;
import com.cellway.Cellway.retrofitModel.AddressModel.SetAddressModel;
import com.cellway.Cellway.util.SessonManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends BaseActivity implements AddressAdapter.OnNumberClik {

    ImageView imgBackAddress;
    RecyclerView rvAddress;
    CardView cardContinue, cardAddNew;
    ArrayList<AddressDatumModel> listDatum = new ArrayList<>();
    int positionAdapter = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        imgBackAddress = findViewById(R.id.imgBackAddress);
        rvAddress = findViewById(R.id.rvAddress);
        cardAddNew = findViewById(R.id.cardAddNew);
        cardContinue = findViewById(R.id.cardContinue);
        sessonManager = new SessonManager(AddressActivity.this);

        hitApi();
        cardAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this, FillAddressActivity.class));
            }
        });
        imgBackAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        cardContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(AddressActivity.this,FillAddressActivity.class));
                if (positionAdapter == -1) {
                    Toast.makeText(AddressActivity.this, "Please Select Address", Toast.LENGTH_SHORT).show();
                } else {
                    int id = listDatum.get(positionAdapter).getId();
                    hitSetAddress(id);
                }
            }
        });
    }

    private void hitSetAddress(int id) {
        showProgress(AddressActivity.this);
        Call<SetAddressModel> call = api.postSetDeliveryAddress(Api.key, id, sessonManager.getToken());
        call.enqueue(new Callback<SetAddressModel>() {
            @Override
            public void onResponse(Call<SetAddressModel> call, Response<SetAddressModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    SetAddressModel AddressStatusModel = response.body();
                    if (AddressStatusModel.getCode().equals("200")) {
                        Toast.makeText(AddressActivity.this, "" + AddressStatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddressActivity.this, CartActivity.class));

                    } else {
                        Toast.makeText(AddressActivity.this, "" + AddressStatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SetAddressModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(AddressActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitApi() {
        showProgress(AddressActivity.this);
        Call<AddressStatusModel> call = api.getAddress(Api.key, sessonManager.getToken());
        call.enqueue(new Callback<AddressStatusModel>() {
            @Override
            public void onResponse(Call<AddressStatusModel> call, Response<AddressStatusModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    AddressStatusModel AddressStatusModel = response.body();
                    listDatum = AddressStatusModel.getData();
                    Log.d("kjgfgf", String.valueOf(listDatum.size()));
                    setRv();
                }
            }

            @Override
            public void onFailure(Call<AddressStatusModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(AddressActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddressActivity.this);
        rvAddress.setLayoutManager(layoutManager);
        rvAddress.setAdapter(new AddressAdapter(AddressActivity.this, listDatum, this));
    }

    @Override
    public void onNumberClicked(int position) {
        positionAdapter = position;
        Log.d("lkasdjksdrd", "" + positionAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
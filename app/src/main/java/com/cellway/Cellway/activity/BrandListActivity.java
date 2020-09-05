package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.BrandAdapter;
import com.cellway.Cellway.fragment.Mobile_Destination;
import com.cellway.Cellway.retrofitModel.BrandListModel.BrandDatum;
import com.cellway.Cellway.retrofitModel.BrandListModel.BrandStatus;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestData;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandListActivity extends BaseActivity {

    ArrayList<BrandDatum> listBrand = new ArrayList<>();
    RecyclerView rvMobile;
    ImageView imgBackBrand;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView txtBrandHeader;
    String type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);
        rvMobile = findViewById(R.id.rvMobile);
        imgBackBrand = findViewById(R.id.imgBackBrand);
        txtBrandHeader = findViewById(R.id.txtBrandHeader);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        type = getIntent().getStringExtra("type");
        txtBrandHeader.setText(type);
        hitApi();
        imgBackBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void hitApi() {
       // showProgress(BrandListActivity.this);
        Call<BrandStatus> call = api.getBrand(Api.key,"Mobile");
        call.enqueue(new Callback<BrandStatus>() {
            @Override
            public void onResponse(Call<BrandStatus> call, Response<BrandStatus> response) {
               // hideProgress();
                if (response.isSuccessful()) {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);

                    BrandStatus BrandStatus = response.body();
                    listBrand = BrandStatus.getData();

                    setRvBrands();
                }
            }

            @Override
            public void onFailure(Call<BrandStatus> call, Throwable t) {
              //  hideProgress();
                Toast.makeText(BrandListActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRvBrands() {
        rvMobile.setVisibility(View.VISIBLE);
        rvMobile.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(BrandListActivity.this, 2);
        rvMobile.setLayoutManager(layoutManager);
        rvMobile.setAdapter(new BrandAdapter(BrandListActivity.this, listBrand,type));
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
       startActivity(new Intent(BrandListActivity.this,Mobile_DestinationNewActivity.class));
       finishAffinity();
    }
}
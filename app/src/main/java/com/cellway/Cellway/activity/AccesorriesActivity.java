package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.AccessCategoryAdapter;
import com.cellway.Cellway.adapter.LatestAccesorisAdapter;
import com.cellway.Cellway.adapter.LatestArrival_AdapterNew;
import com.cellway.Cellway.adapter.SliderAdapterAccessories;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CategoryDatumModel;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CategoryLatestModel;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CategoryStatusModel;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CatgoryBannerModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingBannerModel;
import com.cellway.Cellway.util.SessonManager;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccesorriesActivity extends BaseActivity {

    RecyclerView rvAccessCategory,rvLatestAccess;
    ViewPager mViewPager;
    DotsIndicator dots_indicator;
    ArrayList<CategoryDatumModel> listCategory = new ArrayList<>();
    ArrayList<CategoryLatestModel> listLatest = new ArrayList<>();
    ArrayList<CatgoryBannerModel> listBanner = new ArrayList<>();
    TextView txtCartAccess;
    SessonManager sessonManager;
    ImageView imgBackAccess;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesorries);
        mViewPager = findViewById(R.id.accesories_viewpagers);
        dots_indicator = findViewById(R.id.dots_indicator_access);
        rvAccessCategory = findViewById(R.id.rvAccessCategory);
        rvLatestAccess = findViewById(R.id.rvLatestAccess);
        txtCartAccess = findViewById(R.id.txtCartAccess);
        imgBackAccess = findViewById(R.id.imgBackAccess);
        sessonManager = new SessonManager(AccesorriesActivity.this);

        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            txtCartAccess.setVisibility(View.VISIBLE);
            txtCartAccess.setText(sessonManager.getQty());
        }

        txtCartAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        imgBackAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
        hitApi();

    }

    private void hitApi() {
        showProgress(AccesorriesActivity.this);
        Call call = api.postCategory(Api.key);
        call.enqueue(new Callback<CategoryStatusModel>() {
            @Override
            public void onResponse(Call<CategoryStatusModel> call, Response<CategoryStatusModel> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    CategoryStatusModel model = response.body();
                    listCategory = model.getData();
                    listLatest = model.getLatest();
                    listBanner = model.getBanner();
                    setRV();
                    setRvLatest();
                    viewPager();

                } else {
                    showToast(String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<CategoryStatusModel> call, Throwable t) {
                hideProgress();
                showToast(t.getMessage());
            }
        });
    }

    private void setRV() {
        LinearLayoutManager staggeredGridLayoutManager = new GridLayoutManager(AccesorriesActivity.this,1);
        rvAccessCategory.setLayoutManager(staggeredGridLayoutManager);
        rvAccessCategory.setAdapter(new AccessCategoryAdapter(AccesorriesActivity.this,listCategory));
    }

    private void setRvLatest() {
        rvLatestAccess.setLayoutManager(new LinearLayoutManager(AccesorriesActivity.this,LinearLayoutManager.HORIZONTAL,false));
        rvLatestAccess.setAdapter(new LatestAccesorisAdapter(AccesorriesActivity.this,listLatest));
    }
    public void viewPager() {

        SliderAdapterAccessories adapterView = new SliderAdapterAccessories(AccesorriesActivity.this, listBanner);
        mViewPager.setAdapter(adapterView);
        dots_indicator.setViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AccesorriesActivity.this, Home.class));
        finishAffinity();
    }
}
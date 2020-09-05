package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.AccessCategoryAdapter;
import com.cellway.Cellway.adapter.AccessSubCategoryAdapter;
import com.cellway.Cellway.adapter.SliderAdapterAccessories;
import com.cellway.Cellway.adapter.SliderAdapterAccessoriesSubCategory;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CatgoryBannerModel;
import com.cellway.Cellway.retrofitModel.AccessoriesSubCateegoryModel.SubCategoryBannerModel;
import com.cellway.Cellway.retrofitModel.AccessoriesSubCateegoryModel.SubCategoryDataModel;
import com.cellway.Cellway.retrofitModel.AccessoriesSubCateegoryModel.SubCategoryStatusModel;
import com.cellway.Cellway.util.SessonManager;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccesoriesSubCategoryActivity extends BaseActivity {

    RecyclerView rvAccessCategory;
    ViewPager mViewPager;
    DotsIndicator dots_indicator;
    ArrayList<SubCategoryBannerModel> listBanner = new ArrayList<>();
    ArrayList<SubCategoryDataModel> listCategory = new ArrayList<>();
    TextView txtCartAccessSubCat;
    SessonManager sessonManager;
    ImageView imgBackAccessSubCat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesories_sub_category);

        mViewPager = findViewById(R.id.accesoriesSubCat_viewpagers);
        dots_indicator = findViewById(R.id.dots_indicator_accessSubCat);
        rvAccessCategory = findViewById(R.id.rvAccessSubCategory);
        txtCartAccessSubCat = findViewById(R.id.txtCartAccessSubCat);
        imgBackAccessSubCat = findViewById(R.id.imgBackAccessSubCat);
        sessonManager = new SessonManager(AccesoriesSubCategoryActivity.this);

        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            txtCartAccessSubCat.setVisibility(View.VISIBLE);
            txtCartAccessSubCat.setText(sessonManager.getQty());
        }

        txtCartAccessSubCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        imgBackAccessSubCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        if(getIntent().hasExtra("id")){
            int id = getIntent().getIntExtra("id",0);
            Log.d("aklsddww", String.valueOf(id));
            hitApi(id);
        }




    }

    private void hitApi(int id) {
        showProgress(AccesoriesSubCategoryActivity.this);
        Call call = api.postSubCategory(Api.key,id);
        call.enqueue(new Callback<SubCategoryStatusModel>() {
            @Override
            public void onResponse(Call<SubCategoryStatusModel> call, Response<SubCategoryStatusModel> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    SubCategoryStatusModel model = response.body();
                    listCategory = model.getData();
                    listBanner = model.getBanner();
                    setRV();
                    viewPager();

                } else {
                    showToast(String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<SubCategoryStatusModel> call, Throwable t) {
                hideProgress();
                showToast(t.getMessage());
            }
        });
    }

    public void viewPager() {

        SliderAdapterAccessoriesSubCategory adapterView = new SliderAdapterAccessoriesSubCategory(AccesoriesSubCategoryActivity.this, listBanner);
        mViewPager.setAdapter(adapterView);
        dots_indicator.setViewPager(mViewPager);
    }

    private void setRV() {
        LinearLayoutManager staggeredGridLayoutManager = new GridLayoutManager(AccesoriesSubCategoryActivity.this,1);
        rvAccessCategory.setLayoutManager(staggeredGridLayoutManager);
        rvAccessCategory.setAdapter(new AccessSubCategoryAdapter(AccesoriesSubCategoryActivity.this,listCategory));
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(AccesoriesSubCategoryActivity.this,AccesorriesActivity.class));
        finishAffinity();
    }
}
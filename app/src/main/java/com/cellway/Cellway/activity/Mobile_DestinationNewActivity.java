package com.cellway.Cellway.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.BestSellerAdapter;
import com.cellway.Cellway.adapter.CategoryAdapter;
import com.cellway.Cellway.adapter.GenuineAdapter;
import com.cellway.Cellway.adapter.KnowMoreAdapter;
import com.cellway.Cellway.adapter.ShopPriceAdapter;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestAllProd;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestBestSeller;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestBrand;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestData;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestGenuine;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestKnowMore;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestOffer;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestShopCategory;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestShopPrice;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestStatus;
import com.cellway.Cellway.util.SessonManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Mobile_DestinationNewActivity extends BaseActivity {


    ImageView imgOffer;
    RecyclerView rv_brands, rvBestSellers, rvGen, rvPrice, rvKnowMore, rvCategory;
    TextView txtCartMobDest;


    ArrayList<MobDestAllProd> listAllProd = new ArrayList<>();
    ArrayList<MobDestShopCategory> listShopCat = new ArrayList<>();
    ArrayList<MobDestGenuine> listGen = new ArrayList<>();
    ArrayList<MobDestBrand> listBrands = new ArrayList<>();
    ArrayList<MobDestBestSeller> listBestSeller = new ArrayList<>();
    ArrayList<MobDestShopPrice> listShopPrice = new ArrayList<>();
    ArrayList<MobDestKnowMore> listKnowMore = new ArrayList<>();
    ArrayList<MobDestOffer> listOffers = new ArrayList<>();

    ImageView mainBanner, imgBackMobDest;
    private ShimmerFrameLayout mShimmerViewContainer;
    NestedScrollView nestedMobDest;
    SessonManager sessonManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__destination_new);
        init();

        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            txtCartMobDest.setVisibility(View.VISIBLE);
            txtCartMobDest.setText(sessonManager.getQty());
        }

        hitApi();

        imgBackMobDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Mobile_DestinationNewActivity.this, Home.class));
                finishAffinity();
            }
        });
        mainBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sessonManager.getToken().isEmpty()){
                    startActivity(new Intent(Mobile_DestinationNewActivity.this, ProductActivityBanner.class).
                            putExtra("id",""));
                }else{
                    startActivity(new Intent(Mobile_DestinationNewActivity.this,LoginActivityNew.class));
                }


            }
        });
        txtCartMobDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            txtCartMobDest.setVisibility(View.VISIBLE);
            txtCartMobDest.setText(sessonManager.getQty());
        }else {
            txtCartMobDest.setVisibility(View.GONE);
        }

    }

    private void hitApi() {

        Call<MobDestStatus> call = api.getAllMobileDetails(Api.key);
        call.enqueue(new Callback<MobDestStatus>() {
            @Override
            public void onResponse(Call<MobDestStatus> call, Response<MobDestStatus> response) {

                if (response.isSuccessful()) {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    nestedMobDest.setVisibility(View.VISIBLE);

                    MobDestStatus mobDestStatus = response.body();
                    ArrayList<MobDestData> data = mobDestStatus.getData();
                    listAllProd = data.get(0).getAllproduct();
                    Glide.with(Mobile_DestinationNewActivity.this).load(listAllProd.get(0).getImage()).into(mainBanner);

                    listShopCat = data.get(0).getShopcategory();
                    setCategory();

                    listGen = data.get(0).getGenuines();
                    setGenuine();

                    listBestSeller = data.get(0).getBestsellers();
                    setRvBestSeller();

                    listShopPrice = data.get(0).getShopprices();
                    setRvPrice();

                    listKnowMore = data.get(0).getKnowmores();
                    setrRvKnowMore();

                    listOffers = data.get(0).getOffers();
                    Glide.with(Mobile_DestinationNewActivity.this).load(listOffers.get(0).getImage()).into(imgOffer);

                    listBrands = data.get(0).getBrands();
                    setRvBrands();
                }
            }

            @Override
            public void onFailure(Call<MobDestStatus> call, Throwable t) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                Toast.makeText(Mobile_DestinationNewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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


    private void init() {
        sessonManager = new SessonManager(Mobile_DestinationNewActivity.this);
        rv_brands = findViewById(R.id.rv_brands);
        rvBestSellers = findViewById(R.id.rvBestSellers);
        rvGen = findViewById(R.id.rvGen);
        rvPrice = findViewById(R.id.rvPrice);
        rvKnowMore = findViewById(R.id.rvKnowMore);
        rvCategory = findViewById(R.id.rvCategory);
        mainBanner = findViewById(R.id.mainBanner);
        imgBackMobDest = findViewById(R.id.imgBackMobDest);
        mShimmerViewContainer = findViewById(R.id.shimmer_MobDest);
        nestedMobDest = findViewById(R.id.nestedMobDest);
        imgOffer = findViewById(R.id.imgOffer);
        txtCartMobDest = findViewById(R.id.txtCartMobDest);

        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            txtCartMobDest.setVisibility(View.VISIBLE);
            txtCartMobDest.setText(sessonManager.getQty());
        }

    }

    private void setCategory() {
        rvCategory.setLayoutManager(new GridLayoutManager(Mobile_DestinationNewActivity.this, 3));
        rvCategory.setAdapter(new CategoryAdapter(Mobile_DestinationNewActivity.this, listShopCat));
    }

    private void setrRvKnowMore() {
        rvKnowMore.setLayoutManager(new GridLayoutManager(Mobile_DestinationNewActivity.this, 3));
        rvKnowMore.setAdapter(new KnowMoreAdapter(Mobile_DestinationNewActivity.this, listKnowMore));
    }

    private void setRvPrice() {
        rvPrice.setLayoutManager(new GridLayoutManager(Mobile_DestinationNewActivity.this, 5));
        rvPrice.setAdapter(new ShopPriceAdapter(Mobile_DestinationNewActivity.this, listShopPrice));
    }

    private void setGenuine() {
        rvGen.setLayoutManager(new GridLayoutManager(Mobile_DestinationNewActivity.this, 2));
        rvGen.setAdapter(new GenuineAdapter(Mobile_DestinationNewActivity.this, listGen));
    }

    private void setRvBestSeller() {

        rvBestSellers.setLayoutManager(new GridLayoutManager(Mobile_DestinationNewActivity.this, 1, GridLayoutManager.HORIZONTAL, false));
        rvBestSellers.setAdapter(new BestSellerAdapter(Mobile_DestinationNewActivity.this, listBestSeller));
    }

    private void setRvBrands() {
        rv_brands.setLayoutManager(new LinearLayoutManager(Mobile_DestinationNewActivity.this, RecyclerView.HORIZONTAL, false));
        rv_brands.setAdapter(new Mobile_Adapter(Mobile_DestinationNewActivity.this, listBrands));
    }

    public class Mobile_Adapter extends RecyclerView.Adapter<Mobile_Adapter.MobileViewHolder> {

        Context context;
        ArrayList<MobDestBrand> mdest;

        public Mobile_Adapter(Context context, ArrayList<MobDestBrand> mdest) {
            this.context = context;
            this.mdest = mdest;
        }


        @NonNull
        @Override
        public Mobile_Adapter.MobileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_mobile, viewGroup, false);
            return new Mobile_Adapter.MobileViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Mobile_Adapter.MobileViewHolder mobileViewHolder, int position) {
            MobDestBrand model = mdest.get(position);
            Glide.with(context).load(model.getImage()).into(mobileViewHolder.grid_image);
        }

        @Override
        public int getItemCount() {
            return mdest.size();
        }

        public class MobileViewHolder extends RecyclerView.ViewHolder {

            private ImageView grid_image;
            LinearLayout line;

            public MobileViewHolder(@NonNull View itemView) {
                super(itemView);


                grid_image = itemView.findViewById(R.id.grid_image);
                line = itemView.findViewById(R.id.line);
                line.getLayoutParams().width = 300;
                line.requestLayout();

            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Mobile_DestinationNewActivity.this, Home.class));
        finishAffinity();
    }
}
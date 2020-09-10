package com.cellway.Cellway.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.ProductCateogoryAdapter;
import com.cellway.Cellway.retrofitModel.ProductCategoryModel.ProductCategoryDatumModel;
import com.cellway.Cellway.retrofitModel.ProductCategoryModel.ProductCategorySeriesModel;
import com.cellway.Cellway.retrofitModel.ProductCategoryModel.ProductCategoryStatusModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ProductActivityBrand extends BaseActivity implements RecyclerView.OnScrollChangeListener{
    RecyclerView rvYourMobileDest,rvSeries;
    ImageView imgBackProduct;
    String type="",id,nameHeader,orderBy="lth";
    int currentPage = 1;
    int totalPage;
    ArrayList<ProductCategoryDatumModel> listDataBanner = new ArrayList<>();
    ArrayList<ProductCategoryDatumModel> listDataBanner2 = new ArrayList<>();
    ArrayList<String> listSeriesId = new ArrayList<>();
    ArrayList<ProductCategorySeriesModel> listSeries = new ArrayList<>();
    Call<ProductCategoryStatusModel> call;
    ProductCateogoryAdapter adapter;
    TextView txtProductHeader;
    RelativeLayout relSort,relWarranty,relSeries,relCategory;
    LinearLayout linearSeries,linearWarranty,layoutPrice,layoutCategory;
    RadioGroup radioPrice;
    TextView txtWarrantyIN,txtWarrantyOut,txtSeriesApply;
    ImageView imgSeriesUp,imgWarrantyUp,imgPriceUp,imgCatUp;
    String warranty = "",seriesId="";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_brand);
        init();
        id = getIntent().getStringExtra("id");

        hitApiProduct();
        onClick();

        rvYourMobileDest.setOnScrollChangeListener(this);
        rvYourMobileDest.setLayoutManager(new GridLayoutManager(ProductActivityBrand.this, 1));
        adapter = new ProductCateogoryAdapter(ProductActivityBrand.this, listDataBanner2,type,id);
        rvYourMobileDest.setAdapter(adapter);

        imgBackProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init() {
        rvYourMobileDest = findViewById(R.id.rvYourMobileDestPB);
        rvSeries = findViewById(R.id.rvSeriesPB);
        imgBackProduct = findViewById(R.id.imgBackProductPB);
        txtProductHeader = findViewById(R.id.txtProductHeaderPB);
        relSeries = findViewById(R.id.relSeriesPB);
        relCategory = findViewById(R.id.relCategoryPB);
        relWarranty = findViewById(R.id.relWarrantyPB);
        relSort = findViewById(R.id.relSortPB);
        linearSeries = findViewById(R.id.linearSeriesPB);
        linearWarranty = findViewById(R.id.linearWarrantyPB);
        layoutPrice = findViewById(R.id.layoutPricePB);
        layoutCategory = findViewById(R.id.layoutCategoryPB);
        radioPrice = findViewById(R.id.radioPricePB);
        txtWarrantyIN = findViewById(R.id.txtWarrantyINPB);
        txtWarrantyOut = findViewById(R.id.txtWarrantyOutPB);
        txtSeriesApply = findViewById(R.id.txtSeriesApplyPB);
        imgPriceUp = findViewById(R.id.imgPriceUpPB);
        imgCatUp = findViewById(R.id.imgCatUpPB);
        imgWarrantyUp = findViewById(R.id.imgWarrantyUpPB);
        imgSeriesUp = findViewById(R.id.imgSeriesUpPB);

    }
    private void onClick() {
        relSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(linearSeries.getVisibility()==View.VISIBLE){
                    linearSeries.setVisibility(View.GONE);
                    imgSeriesUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.VISIBLE);

                }else {
                    linearSeries.setVisibility(View.VISIBLE);
                    imgSeriesUp.setImageResource(R.drawable.arrow_drop_up);
                    imgWarrantyUp.setImageResource(R.drawable.arrow_drop_down);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.GONE);
                }
                linearWarranty.setVisibility(View.GONE);
                layoutPrice.setVisibility(View.GONE);
                layoutCategory.setVisibility(View.GONE);
            }
        });
        relWarranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linearWarranty.getVisibility()==View.VISIBLE){
                    linearWarranty.setVisibility(View.GONE);
                    imgWarrantyUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.VISIBLE);
                }else {
                    linearWarranty.setVisibility(View.VISIBLE);
                    imgWarrantyUp.setImageResource(R.drawable.arrow_drop_up);
                    imgSeriesUp.setImageResource(R.drawable.arrow_drop_down);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_down);

                }

                linearSeries.setVisibility(View.GONE);
                layoutPrice.setVisibility(View.GONE);
                layoutCategory.setVisibility(View.GONE);
            }
        });

        relSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layoutPrice.getVisibility()==View.VISIBLE){
                    layoutPrice.setVisibility(View.GONE);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.VISIBLE);

                }else {
                    layoutPrice.setVisibility(View.VISIBLE);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_up);
                    imgWarrantyUp.setImageResource(R.drawable.arrow_drop_down);
                    imgSeriesUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.GONE);
                }

                linearSeries.setVisibility(View.GONE);
                linearWarranty.setVisibility(View.GONE);
                layoutCategory.setVisibility(View.GONE);
            }
        });

        relCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(layoutCategory.getVisibility()==View.VISIBLE){
                    layoutCategory.setVisibility(View.GONE);
                    imgCatUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.VISIBLE);

                }else {
                    layoutCategory.setVisibility(View.VISIBLE);
                    imgCatUp.setImageResource(R.drawable.arrow_drop_up);
                    imgSeriesUp.setImageResource(R.drawable.arrow_drop_down);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.GONE);
                }
                linearSeries.setVisibility(View.GONE);
                layoutPrice.setVisibility(View.GONE);
                linearWarranty.setVisibility(View.GONE);
            }
        });


        txtSeriesApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seriesId="";
                for (int i = 0; i < listSeriesId.size(); i++) {
                    if (i == 0) {
                        seriesId = listSeriesId.get(i);
                    } else {
                        seriesId = seriesId + "," + listSeriesId.get(i);
                    }

                }

                currentPage=1;
                listDataBanner2.clear();
                hitApiProduct();
                adapter.notifyDataSetChanged();
                linearSeries.setVisibility(View.GONE);
                imgSeriesUp.setImageResource(R.drawable.arrow_drop_down);
                rvYourMobileDest.setVisibility(View.VISIBLE);
                Log.d("sdfsdfsf",""+listSeriesId);
            }
        });

        txtWarrantyIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warranty = "In";
                currentPage=1;
                txtWarrantyIN.setBackgroundResource(R.drawable.search_round);
                txtWarrantyOut.setBackgroundResource(R.drawable.search_round_white);
                rvYourMobileDest.setVisibility(View.VISIBLE);
                linearWarranty.setVisibility(View.GONE);
                imgWarrantyUp.setImageResource(R.drawable.arrow_drop_down);
                listDataBanner2.clear();
                hitApiProduct();
                adapter.notifyDataSetChanged();
            }
        });
        txtWarrantyOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warranty = "Out";
                currentPage=1;
                txtWarrantyOut.setBackgroundResource(R.drawable.search_round);
                txtWarrantyIN.setBackgroundResource(R.drawable.search_round_white);
                rvYourMobileDest.setVisibility(View.VISIBLE);
                linearWarranty.setVisibility(View.GONE);
                imgWarrantyUp.setImageResource(R.drawable.arrow_drop_down);
                listDataBanner2.clear();
                hitApiProduct();
                adapter.notifyDataSetChanged();
            }
        });

        radioPrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    String check = checkedRadioButton.getText().toString();
                    if(check.equals("High to Low")){
                        orderBy = "htl";
                        listDataBanner2.clear();
                        currentPage=1;
                        rvYourMobileDest.setVisibility(View.VISIBLE);
                        layoutPrice.setVisibility(View.GONE);
                        imgPriceUp.setBackgroundResource(R.drawable.arrow_drop_down);
                        hitApiProduct();
                    }
                    if(check.equals("Low to High")){
                        listDataBanner2.clear();
                        currentPage=1;
                        orderBy = "lth";
                        rvYourMobileDest.setVisibility(View.VISIBLE);
                        imgPriceUp.setBackgroundResource(R.drawable.arrow_drop_down);
                        layoutPrice.setVisibility(View.GONE);
                        hitApiProduct();
                    }

//                    Toast.makeText(ProductActivity.this, ""+ orderBy, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        if (isLastItemDisplaying(rvYourMobileDest)) {
            if (currentPage <= totalPage && listDataBanner2.size() > 1) {
                hitApiProduct();

            }
        }
    }
    private void hitApiProduct() {
        showProgress(ProductActivityBrand.this);
        listDataBanner.clear();

        Log.d("adlskjop",orderBy);
        call = api.getProductByBrand(Api.key, String.valueOf(currentPage), type,id,seriesId,warranty,orderBy);

        call.enqueue(new Callback<ProductCategoryStatusModel>() {
            @Override
            public void onResponse(Call<ProductCategoryStatusModel> call, Response<ProductCategoryStatusModel> response) {
                hideProgress();

                if (response.isSuccessful()) {
                    ProductCategoryStatusModel model = response.body();
                    totalPage = model.getTotalPages();
                    txtProductHeader.setText(model.getBrandName());

                    if(listSeries.size()==0){
                        listSeries = model.getSeries();
                        rvSeries.setLayoutManager(new GridLayoutManager(ProductActivityBrand.this,3));
                        rvSeries.setAdapter(new SeriesAdapter(ProductActivityBrand.this,listSeries));
                    }

                    listDataBanner = model.getData();
                    listDataBanner2.addAll(listDataBanner);
                    adapter.notifyDataSetChanged();

                    currentPage = currentPage + 1;

                } else {
                    showToast(String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ProductCategoryStatusModel> call, Throwable t) {
                hideProgress();
                showToast(t.getMessage());
            }
        });
    }


    public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ProductViewHolder> {

        Context context;
        ArrayList<ProductCategorySeriesModel> arrProduct;
        public SeriesAdapter(Context context, ArrayList<ProductCategorySeriesModel> arrProduct) {
            this.context = context;
            this.arrProduct = arrProduct;
        }

        @NonNull
        @Override
        public SeriesAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_brand_product, parent, false);
            return new SeriesAdapter.ProductViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final SeriesAdapter.ProductViewHolder holder, int position) {
            final ProductCategorySeriesModel model = arrProduct.get(position);
            holder.txtBrand.setText(model.getSeriesName());

            holder.txtBrand.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);

            holder.txtBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.setSelected(!model.isSelected());
                    holder.txtBrand.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);
                    if (model.isSelected() == true) {
                        if(listSeriesId.contains(""+model.getSeriesName())){
                        }else {
                            listSeriesId.add(""+model.getSeriesName());
                        }

                    } else {
                        if (listSeriesId.contains(""+model.getSeriesName())) {
                            listSeriesId.remove(""+model.getSeriesName());
                        }

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrProduct.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView txtBrand;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                txtBrand = itemView.findViewById(R.id.txtBrand);

            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProductActivityBrand.this,Mobile_DestinationNewActivity.class));
        finishAffinity();
    }

}
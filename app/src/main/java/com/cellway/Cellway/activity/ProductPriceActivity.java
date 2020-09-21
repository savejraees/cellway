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
import com.cellway.Cellway.adapter.ProductBrandAdapter;
import com.cellway.Cellway.adapter.ProductPriceAdapter;
import com.cellway.Cellway.retrofitModel.ProductBrandModel.ProductBrandCategoryModel;
import com.cellway.Cellway.retrofitModel.ProductBrandModel.ProductBrandDatumModel;
import com.cellway.Cellway.retrofitModel.ProductBrandModel.ProductBrandSeriesModel;
import com.cellway.Cellway.retrofitModel.ProductPriceModel.ProductPriceBrandModel;
import com.cellway.Cellway.retrofitModel.ProductPriceModel.ProductPriceCategoryModel;
import com.cellway.Cellway.retrofitModel.ProductPriceModel.ProductPriceDatumModel;
import com.cellway.Cellway.retrofitModel.ProductPriceModel.ProductPriceStatusModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ProductPriceActivity extends BaseActivity implements RecyclerView.OnScrollChangeListener{
    RecyclerView rvYourMobileDest,rvSeries,rvCatogry;
    ImageView imgBackProduct;
    String type="",orderBy="lth";
    int currentPage = 1;
    int totalPage;
    ArrayList<ProductPriceDatumModel> listDataBanner = new ArrayList<>();
    ArrayList<ProductPriceDatumModel> listDataBanner2 = new ArrayList<>();
    ArrayList<ProductPriceCategoryModel> listCategory = new ArrayList<>();
    ArrayList<String> listCat = new ArrayList<>();
    ArrayList<String> listSeriesId = new ArrayList<>();
    ArrayList<ProductPriceBrandModel> listSeries = new ArrayList<>();
    Call<ProductPriceStatusModel> call;
    ProductPriceAdapter adapter;
    TextView txtProductHeader;
    RelativeLayout relSort,relWarranty,relSeries,relCategory;
    LinearLayout linearSeries,linearWarranty,layoutPrice,layoutCategory;
    RadioGroup radioPrice;
    TextView txtWarrantyIN,txtWarrantyOut,txtSeriesApply,txtCatApply;
    ImageView imgSeriesUp,imgWarrantyUp,imgPriceUp,imgCatUp;
    String warranty = "",seriesId="";

    String p1="1",p2="1100000";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_price);
        init();
        p1 = getIntent().getStringExtra("p1");
        p2 = getIntent().getStringExtra("p2");

        hitApiProduct();
        onClick();

        rvYourMobileDest.setOnScrollChangeListener(this);
        rvYourMobileDest.setLayoutManager(new GridLayoutManager(ProductPriceActivity.this, 1));
        adapter = new ProductPriceAdapter(ProductPriceActivity.this, listDataBanner2,"Price",p1,p2);
        rvYourMobileDest.setAdapter(adapter);

        imgBackProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init() {
        rvYourMobileDest = findViewById(R.id.rvYourMobileDestPR);
        rvSeries = findViewById(R.id.rvSeriesPR);
        rvCatogry = findViewById(R.id.rvCatogryPR);
        imgBackProduct = findViewById(R.id.imgBackProductPR);
        txtProductHeader = findViewById(R.id.txtProductHeaderPR);
        relSeries = findViewById(R.id.relSeriesPR);
        relCategory = findViewById(R.id.relCategoryPR);
        relWarranty = findViewById(R.id.relWarrantyPR);
        relSort = findViewById(R.id.relSortPR);
        linearSeries = findViewById(R.id.linearSeriesPR);
        linearWarranty = findViewById(R.id.linearWarrantyPR);
        layoutPrice = findViewById(R.id.layoutPricePR);
        layoutCategory = findViewById(R.id.layoutCategoryPR);
        radioPrice = findViewById(R.id.radioPricePR);
        txtWarrantyIN = findViewById(R.id.txtWarrantyINPR);
        txtWarrantyOut = findViewById(R.id.txtWarrantyOutPR);
        txtSeriesApply = findViewById(R.id.txtSeriesApplyPR);
        txtCatApply = findViewById(R.id.txtCatApplyPR);
        imgPriceUp = findViewById(R.id.imgPriceUpPR);
        imgCatUp = findViewById(R.id.imgCatUpPR);
        imgWarrantyUp = findViewById(R.id.imgWarrantyUpPR);
        imgSeriesUp = findViewById(R.id.imgSeriesUpPR);

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
        txtCatApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="";
                for (int i = 0; i < listCat.size(); i++) {
                    if (i == 0) {
                        type = listCat.get(i);
                    } else {
                        type = type + "," + listCat.get(i);
                    }

                }

                currentPage=1;
                listDataBanner2.clear();
                hitApiProduct();
                adapter.notifyDataSetChanged();
                layoutCategory.setVisibility(View.GONE);
                imgCatUp.setImageResource(R.drawable.arrow_drop_down);
                rvYourMobileDest.setVisibility(View.VISIBLE);
                Log.d("sdfsdfsf",""+listCat);
            }
        });

    }

    private void hitApiProduct() {
        showProgress(ProductPriceActivity.this);
        listDataBanner.clear();

        Log.d("adlskjop",orderBy);
        if(p1.equals("Below")){
            call = api.getProductByPrice(Api.key, String.valueOf(currentPage), type,seriesId,warranty,orderBy,"0",p2);
        }else {
            call = api.getProductByPrice(Api.key, String.valueOf(currentPage), type,seriesId,warranty,orderBy,p1,p2);
        }


        call.enqueue(new Callback<ProductPriceStatusModel>() {
            @Override
            public void onResponse(Call<ProductPriceStatusModel> call, Response<ProductPriceStatusModel> response) {
                hideProgress();

                if (response.isSuccessful()) {
                    ProductPriceStatusModel model = response.body();
                    totalPage = model.getTotalPages();

                    if(p1.equals("Below")){
                        txtProductHeader.setText(p1+" "+ p2);
                    }
                    else if(p2.equals("1500000")){
                        txtProductHeader.setText("Above "+p1);
                    }
                    else {
                        txtProductHeader.setText(p1+" - "+p2);
                    }


                    if(listCategory.size()==0){
                        listCategory = model.getCategory();
                        rvCatogry.setLayoutManager(new LinearLayoutManager(ProductPriceActivity.this,RecyclerView.HORIZONTAL,false));
                        rvCatogry.setAdapter(new CategoryAdapter(ProductPriceActivity.this,listCategory));
                    }

                    if(listSeries.size()==0){
                        listSeries = model.getBrands();
                        rvSeries.setLayoutManager(new GridLayoutManager(ProductPriceActivity.this,3));
                        rvSeries.setAdapter(new SeriesAdapter(ProductPriceActivity.this,listSeries));
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
            public void onFailure(Call<ProductPriceStatusModel> call, Throwable t) {
                hideProgress();
                showToast(t.getMessage());
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

    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder> {

        Context context;
        ArrayList<ProductPriceCategoryModel> arrProduct;
        public CategoryAdapter(Context context, ArrayList<ProductPriceCategoryModel> arrProduct) {
            this.context = context;
            this.arrProduct = arrProduct;
        }

        @NonNull
        @Override
        public CategoryAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_category_product, parent, false);
            return new CategoryAdapter.ProductViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final CategoryAdapter.ProductViewHolder holder, int position) {
            final ProductPriceCategoryModel model = arrProduct.get(position);
            holder.txtCat.setText(model.getTitle());

            holder.txtCat.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);

            holder.txtCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.setSelected(!model.isSelected());
                    holder.txtCat.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);

                    if (model.isSelected() == true) {
                        if(listCat.contains(""+model.getTitle())){
                        }else{
                            listCat.add(""+model.getTitle());
                        }

                    } else {
                        if (listCat.contains(""+model.getTitle())) {
                            listCat.remove(""+model.getTitle());
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
            TextView txtCat;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                txtCat = itemView.findViewById(R.id.txtCat);

            }
        }
    }

    public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ProductViewHolder> {

        Context context;
        ArrayList<ProductPriceBrandModel> arrProduct;
        public SeriesAdapter(Context context, ArrayList<ProductPriceBrandModel> arrProduct) {
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
            final ProductPriceBrandModel model = arrProduct.get(position);
            holder.txtBrand.setText(model.getBrandName());

            holder.txtBrand.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);

            holder.txtBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.setSelected(!model.isSelected());
                    holder.txtBrand.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);
                    if (model.isSelected() == true) {
                        if(listSeriesId.contains(""+model.getBrandName())){
                        }else {
                            listSeriesId.add(""+model.getBrandName());
                        }

                    } else {
                        if (listSeriesId.contains(""+model.getBrandName())) {
                            listSeriesId.remove(""+model.getBrandName());
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
        startActivity(new Intent(ProductPriceActivity.this,Mobile_DestinationNewActivity.class));
        finishAffinity();
    }
}
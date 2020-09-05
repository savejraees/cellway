package com.cellway.Cellway.activity;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.Product_NewAdapter;
import com.cellway.Cellway.retrofitModel.ProductModel.ProductBrandModel;
import com.cellway.Cellway.retrofitModel.ProductModel.ProductCategoryModel;
import com.cellway.Cellway.retrofitModel.ProductModel.ProductDatumModel;
import com.cellway.Cellway.retrofitModel.ProductModel.ProductStatusModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ProductActivityBanner extends BaseActivity  implements RecyclerView.OnScrollChangeListener{

    RecyclerView rvYourMobileDest;
    ImageView imgBackProduct;
    String type="OpenBox",id="",nameHeader,orderBy="lth";
    int currentPage = 1;
    int totalPage;
    ArrayList<ProductDatumModel> listDataBanner = new ArrayList<>();
    ArrayList<ProductDatumModel> listDataBanner2 = new ArrayList<>();
    ArrayList<ProductCategoryModel> listCategory = new ArrayList<>();
    ArrayList<ProductBrandModel> listBrand = new ArrayList<>();
    ArrayList<String> listBrandId = new ArrayList<>();
    ArrayList<String> listCat = new ArrayList<>();
    Call<ProductStatusModel> call;
    Product_NewAdapter adapter;
    TextView txtProductHeader,txtCatApply,txtBrandsApply;
    LinearLayout layoutCategory,layoutBrands,layoutPrice;
    ImageView imgPriceUp,imgBrandUp,imgCatUp;
    RadioGroup radioPrice;
    RecyclerView rvBrands,rvCatogry;
    RelativeLayout relCategory,relBrands,relPrice;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_banner);
        init();

        id = getIntent().getStringExtra("id");
        Log.d("askjsdas",type);

        hitApiProductBanner();

        rvYourMobileDest.setOnScrollChangeListener(this);
        rvYourMobileDest.setLayoutManager(new GridLayoutManager(ProductActivityBanner.this, 1));
        adapter = new Product_NewAdapter(ProductActivityBanner.this, listDataBanner2,id);
        rvYourMobileDest.setAdapter(adapter);

        imgBackProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        onClick();

    }

    private void init() {
        rvYourMobileDest = findViewById(R.id.rvYourMobileDest);
        imgBackProduct = findViewById(R.id.imgBackProduct);
        txtProductHeader = findViewById(R.id.txtProductHeader);
        txtCatApply = findViewById(R.id.txtCatApply);
        txtBrandsApply = findViewById(R.id.txtBrandsApply);
        layoutCategory = findViewById(R.id.layoutCategory);
        layoutBrands = findViewById(R.id.layoutBrands);
        layoutPrice = findViewById(R.id.layoutPrice);
        relCategory = findViewById(R.id.relCategory);
        relBrands = findViewById(R.id.relBrands);
        relPrice = findViewById(R.id.relPrice);
        radioPrice = findViewById(R.id.radioPrice);
        rvBrands = findViewById(R.id.rvBrands);
        rvCatogry = findViewById(R.id.rvCatogry);
        imgPriceUp = findViewById(R.id.imgPriceUp);
        imgBrandUp = findViewById(R.id.imgBrandUp);
        imgCatUp = findViewById(R.id.imgCatUp);

        txtProductHeader.setText("Your Mobile Destination");
    }

    private void onClick() {
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
                    imgBrandUp.setImageResource(R.drawable.arrow_drop_down);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.GONE);
                }
                layoutBrands.setVisibility(View.GONE);
                layoutPrice.setVisibility(View.GONE);
            }
        });
        relBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layoutBrands.getVisibility()==View.VISIBLE){
                    layoutBrands.setVisibility(View.GONE);
                    imgBrandUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.VISIBLE);
                }else {
                    layoutBrands.setVisibility(View.VISIBLE);
                    imgBrandUp.setImageResource(R.drawable.arrow_drop_up);
                    imgCatUp.setImageResource(R.drawable.arrow_drop_down);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.GONE);
                }

                layoutCategory.setVisibility(View.GONE);
                layoutPrice.setVisibility(View.GONE);
            }
        });
        relPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layoutPrice.getVisibility()==View.VISIBLE){
                    layoutPrice.setVisibility(View.GONE);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.VISIBLE);

                }else {
                    layoutPrice.setVisibility(View.VISIBLE);
                    imgPriceUp.setImageResource(R.drawable.arrow_drop_up);
                    imgBrandUp.setImageResource(R.drawable.arrow_drop_down);
                    imgCatUp.setImageResource(R.drawable.arrow_drop_down);
                    rvYourMobileDest.setVisibility(View.GONE);
                }

                layoutBrands.setVisibility(View.GONE);
                layoutCategory.setVisibility(View.GONE);
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
                        hitApiProductBanner();
                    }
                    if(check.equals("Low to High")){
                        listDataBanner2.clear();
                        currentPage=1;
                        orderBy = "lth";
                        rvYourMobileDest.setVisibility(View.VISIBLE);
                        layoutPrice.setVisibility(View.GONE);
                        hitApiProductBanner();
                    }

//                    Toast.makeText(ProductActivity.this, ""+ orderBy, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtBrandsApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id="";

                for (int i = 0; i < listBrandId.size(); i++) {
                    if (i == 0) {
                        id = listBrandId.get(i);
                    } else {
                        id = id + "," + listBrandId.get(i);
                    }

                }
                currentPage=1;
                listDataBanner2.clear();
                 hitApiProductBanner();
                adapter.notifyDataSetChanged();
                layoutBrands.setVisibility(View.GONE);
                imgBrandUp.setImageResource(R.drawable.arrow_drop_down);
                rvYourMobileDest.setVisibility(View.VISIBLE);
                Log.d("sdfsadfsf",""+listBrandId);
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
                hitApiProductBanner();
                adapter.notifyDataSetChanged();
                layoutCategory.setVisibility(View.GONE);
                imgCatUp.setImageResource(R.drawable.arrow_drop_down);
                rvYourMobileDest.setVisibility(View.VISIBLE);
                Log.d("sdfsdfsf",""+listCat);
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
                    hitApiProductBanner();

            }
        }
}

    private void hitApiProductBanner() {
        showProgress(ProductActivityBanner.this);
        listDataBanner.clear();


        call = api.getProductBanner(Api.key, String.valueOf(currentPage), type,id,orderBy);
//        if(((MainActivity)getActivity()).barcode.equals("")||((MainActivity)getActivity()).barcode.isEmpty()){
//            call = api.hitOpenBoxApi(Url.key, String.valueOf(currentPage), "openbox");
//        }
//        else
//        {
//            currentPage =1;
//            call = api.hitOpenBoxApiSearch(Url.key, String.valueOf(currentPage), "openbox",((MainActivity) getActivity()).barcode);
//        }

        call.enqueue(new Callback<ProductStatusModel>() {
            @Override
            public void onResponse(Call<ProductStatusModel> call, Response<ProductStatusModel> response) {
                hideProgress();

                if (response.isSuccessful()) {
                    ProductStatusModel model = response.body();
                    totalPage = model.getTotalPages();
                  //  txtProductHeader.setText(model.getBrandName());

                    if(listCategory.size()==0){
                        listCategory = model.getCategory();
                        rvCatogry.setLayoutManager(new LinearLayoutManager(ProductActivityBanner.this,RecyclerView.HORIZONTAL,false));
                        rvCatogry.setAdapter(new CategoryAdapter(ProductActivityBanner.this,listCategory));
                    }

                    if(listBrand.size()==0){
                        listBrand = model.getBrands();
                        rvBrands.setLayoutManager(new GridLayoutManager(ProductActivityBanner.this,3));
                        rvBrands.setAdapter(new BrandAdapter(ProductActivityBanner.this,listBrand));
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
            public void onFailure(Call<ProductStatusModel> call, Throwable t) {
                hideProgress();
                showToast(t.getMessage());
            }
        });
    }

    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder> {

        Context context;
        ArrayList<ProductCategoryModel> arrProduct;
        public CategoryAdapter(Context context, ArrayList<ProductCategoryModel> arrProduct) {
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
            final ProductCategoryModel model = arrProduct.get(position);
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


 public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ProductViewHolder> {

        Context context;
        ArrayList<ProductBrandModel> arrProduct;
        public BrandAdapter(Context context, ArrayList<ProductBrandModel> arrProduct) {
            this.context = context;
            this.arrProduct = arrProduct;
        }

        @NonNull
        @Override
        public BrandAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_brand_product, parent, false);
            return new BrandAdapter.ProductViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final BrandAdapter.ProductViewHolder holder, int position) {
            final ProductBrandModel model = arrProduct.get(position);
            holder.txtBrand.setText(model.getBrandName());

            holder.txtBrand.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);

            holder.txtBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.setSelected(!model.isSelected());
                    holder.txtBrand.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);
                    if (model.isSelected() == true) {
                        if(listBrandId.contains(""+model.getBrandId())){
                        }else {
                            listBrandId.add(""+model.getBrandId());
                        }

                    } else {
                        if (listBrandId.contains(""+model.getBrandId())) {
                            listBrandId.remove(""+model.getBrandId());
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
        startActivity(new Intent(ProductActivityBanner.this,Mobile_DestinationNewActivity.class));
        finishAffinity();
    }
}
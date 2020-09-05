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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.AccessoriesProductAdapter;
import com.cellway.Cellway.adapter.Product_NewAdapter;
import com.cellway.Cellway.retrofitModel.AccessoriesProductModel.AccessoriesProductBrandModel;
import com.cellway.Cellway.retrofitModel.AccessoriesProductModel.AccessoriesProductDatumModel;
import com.cellway.Cellway.retrofitModel.AccessoriesProductModel.AccessoriesProductStatusModel;
import com.cellway.Cellway.retrofitModel.ProductModel.ProductBrandModel;
import com.cellway.Cellway.util.SessonManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AccesoiesProductActivity extends BaseActivity implements RecyclerView.OnScrollChangeListener {

    SessonManager sessonManager;
    RecyclerView rvAccessProduct,rvBrandAccessProduct;
    LinearLayout layoutPrice;
    RadioGroup radioPrice;
    RelativeLayout relPrice;
    ImageView imgPriceUp,imgBackProductAccess;

    Call<AccessoriesProductStatusModel> call;
    ArrayList<AccessoriesProductDatumModel> listProduct = new ArrayList<>();
    ArrayList<AccessoriesProductDatumModel> listProduct2 = new ArrayList<>();
    ArrayList<AccessoriesProductBrandModel> listBrand = new ArrayList<>();
    ArrayList<String> listBrandId = new ArrayList<>();
    int catId,subCatId;
    int currentPage = 1;
    int totalPage;
    String brandId ="";
    String orderBy="htl";
    AccessoriesProductAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesoies_product);
        sessonManager = new SessonManager(AccesoiesProductActivity.this);
        rvAccessProduct = findViewById(R.id.rvAccessProduct);
        layoutPrice = findViewById(R.id.layoutPriceAccess);
        radioPrice = findViewById(R.id.radioPriceAccess);
        relPrice = findViewById(R.id.relPriceAccess);
        imgPriceUp = findViewById(R.id.imgPriceUpAccess);
        imgBackProductAccess = findViewById(R.id.imgBackProductAccess);
        rvBrandAccessProduct = findViewById(R.id.rvBrandAccessProduct);
        catId = getIntent().getIntExtra("catId",0);
        subCatId = getIntent().getIntExtra("subCatId",0);
        Log.d("askldqasd",catId+" "+subCatId);

        hitApi();

        rvAccessProduct.setOnScrollChangeListener(this);
        rvAccessProduct.setLayoutManager(new GridLayoutManager(AccesoiesProductActivity.this, 1));
        adapter = new AccessoriesProductAdapter(AccesoiesProductActivity.this, listProduct2,catId,subCatId);
        rvAccessProduct.setAdapter(adapter);

       // setRvBrands();
        onClick();
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
        if (isLastItemDisplaying(rvAccessProduct)) {
            if (currentPage <= totalPage && listProduct2.size() > 1) {
                hitApi();

            }
        }
    }

    private void hitApi() {
        showProgress(AccesoiesProductActivity.this);
        listProduct.clear();


        call = api.getAccessoriesProduct(Api.key,1,1,brandId,currentPage,orderBy);


        call.enqueue(new Callback<AccessoriesProductStatusModel>() {
            @Override
            public void onResponse(Call<AccessoriesProductStatusModel> call, Response<AccessoriesProductStatusModel> response) {
                hideProgress();

                if (response.isSuccessful()) {
                    AccessoriesProductStatusModel model = response.body();
                    totalPage = model.getTotalPages();
                    //  txtProductHeader.setText(model.getBrandName());


                    if(listBrand.size()==0){
                        listBrand = model.getBrands();
                        rvBrandAccessProduct.setLayoutManager(new LinearLayoutManager(AccesoiesProductActivity.this,RecyclerView.HORIZONTAL,false));
                        rvBrandAccessProduct.setAdapter(new BrandAdapter(AccesoiesProductActivity.this,listBrand));
                    }


                    listProduct = model.getData();
                    listProduct2.addAll(listProduct);
                    adapter.notifyDataSetChanged();

                    currentPage = currentPage + 1;


                } else {
                    showToast(String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<AccessoriesProductStatusModel> call, Throwable t) {
                hideProgress();
                showToast(t.getMessage());
            }
        });
    }


    private void onClick() {


        imgBackProductAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        relPrice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(layoutPrice.getVisibility()==View.VISIBLE){
//                    layoutPrice.setVisibility(View.GONE);
//                    imgPriceUp.setImageResource(R.drawable.arrow_drop_down);
//                    rvAccessProduct.setVisibility(View.VISIBLE);
//
//                }else {
//                    layoutPrice.setVisibility(View.VISIBLE);
//                    imgPriceUp.setImageResource(R.drawable.arrow_drop_up);
//                    rvAccessProduct.setVisibility(View.GONE);
//                }
//
//            }
//        });


        radioPrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    String check = checkedRadioButton.getText().toString();
                    if(check.equals("High to Low")){
                        orderBy = "htl";
                        listProduct2.clear();
                        currentPage=1;

                        hitApi();
                    }
                    if(check.equals("Low to High")){
                        listProduct2.clear();
                        currentPage=1;
                        orderBy = "lth";
                        hitApi();
                    }

//                    Toast.makeText(ProductActivity.this, ""+ orderBy, Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ProductViewHolder> {

        Context context;
        ArrayList<AccessoriesProductBrandModel> arrProduct;
        public BrandAdapter(Context context, ArrayList<AccessoriesProductBrandModel> arrProduct) {
            this.context = context;
            this.arrProduct = arrProduct;
        }

        @NonNull
        @Override
        public BrandAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_mobile, parent, false);
            return new BrandAdapter.ProductViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final BrandAdapter.ProductViewHolder holder, int position) {
            final AccessoriesProductBrandModel model = arrProduct.get(position);
            Glide.with(context).load(arrProduct.get(position).getBrandImg()).into(holder.grid_image);

            holder.line.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);

            holder.line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.setSelected(!model.isSelected());
                    holder.line.setBackgroundResource(model.isSelected() ? R.drawable.search_round : R.drawable.search_round_white);
                    if (model.isSelected() == true) {
                        if(listBrandId.contains(""+model.getId())){
                        }else {
                            listBrandId.add(""+model.getId());
                        }

                    } else {
                        if (listBrandId.contains(""+model.getId())) {
                            listBrandId.remove(""+model.getId());
                        }

                    }

                    for(int i=0;i<listBrandId.size();i++){
                        if(i==0){
                            brandId = listBrandId.get(i);
                        }else{
                            brandId =brandId+"," +listBrandId.get(i);
                        }

                    }
                    Log.d("asjspol",brandId);
                    currentPage=1;
                    listProduct2.clear();
                    hitApi();
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrProduct.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder {
            ImageView grid_image;
            LinearLayout line;
            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                grid_image = itemView.findViewById(R.id.grid_image);
                line = itemView.findViewById(R.id.line);
//                line.getLayoutParams().width = 300;
//                line.requestLayout();

            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AccesoiesProductActivity.this, AccesoriesSubCategoryActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("id",sessonManager.getCatIdAccess()));
        finishAffinity();
    }
}
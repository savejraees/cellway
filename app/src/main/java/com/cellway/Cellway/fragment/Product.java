package com.cellway.Cellway.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;

import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.Mobile_slide_Adapter;
import com.cellway.Cellway.model.Mobile_destination;
import com.cellway.Cellway.model.Product_Description;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Product extends Fragment {

    View view;
    RecyclerView rvProduct;
    ArrayList<Product_Description> listProduct;
    Product_Description product_description;
    ProductAdapter adapter;
    Button filter, sort;
    Toolbar productToolbar;
   // int countVisibility;
    String pbrand_id;
    CardView cardFront, cardRear, cardDisplay, cardBattery;
    String sortValue = "";
    String sortingType = "";
    String type = " ";
    ProgressBar progressbarProduct;
    String ProductUrl = "";

    public Product() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


//        ((Home) getActivity()).getSupportActionBar().setTitle(value);
        view = inflater.inflate(R.layout.fragment_product, container, false);
        listProduct = new ArrayList<>();

        rvProduct = view.findViewById(R.id.rvProduct);
        progressbarProduct = view.findViewById(R.id.progressbarProduct);
        rvProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvProduct.setLayoutManager(layoutManager);


        product_description = new Product_Description();
        productToolbar = view.findViewById(R.id.productToolbar);
        productToolbar.setNavigationIcon(R.drawable.arrow_back);

        final String value = getArguments().getString("pName");
        pbrand_id = getArguments().getString("pBrandId");
        sortValue = getArguments().getString("sortValue");

        Log.d("addadsad", pbrand_id);

//        countVisibility = getArguments().getInt("visibility");
//
//        Log.d("zaqwsx", String.valueOf(countVisibility));
        Log.d("zaqwsasx", value);


        productToolbar.setTitle(value);
        productToolbar.setTitleTextColor(Color.WHITE);
        productToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(Home.counterBack==2){
//
//                }
                getActivity().onBackPressed();

            }
        });

        // listProduct = getArguments().getParcelableArrayList("productDetails");

//        if(listProduct.size()==0){
//            Toast.makeText(getContext(), "No Product Availabe", Toast.LENGTH_SHORT).show();
//        }
        if (sortValue.equals("tablet")) {
            ProductUrl = Api.productBrandTab;
            hitProductApi();
        }
        if (sortValue.equals("mobile")) {
            ProductUrl = Api.productBrandMobile;
            hitProductApi();
        }
        if (sortValue.equals("accessories")) {
            ProductUrl = Api.home_url;
            hitProductApi_Accsees();
        }
        if (sortValue.equals("more")) {
            ProductUrl = Api.home_url;
            hitProductApi_More();
        }

        if (sortValue.equals("range")) {

            if (value.equals("Under 7000")) {
                type = "1";
            }
            if (value.equals("7000-15000")) {
                type = "2";
            }
            if (value.equals("15000-30000")) {
                type = "3";
            }
            if (value.equals("above 30000")) {
                type = "4";
            }

            ProductUrl = Api.filter;
            hitProductRangeApi();
        }

        if (sortValue.equals("Filter")) {
            ProductUrl = Api.getProductDetails;
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Filter", Context.MODE_PRIVATE);
            String warrenty = sharedPreferences.getString("warranty", "");
            String series = sharedPreferences.getString("series", "");

            hitFilterApi(warrenty, series);
        }


        Log.d("poiewe", String.valueOf(listProduct));

        filter = view.findViewById(R.id.filter);
        sort = view.findViewById(R.id.sort);

        //  setRvProduct();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pbrand_id.equals(" ")) {
                    Toast.makeText(getActivity(), "Filter is available only for Mobile and Tablet Section.", Toast.LENGTH_SHORT).show();
                } else {


                    Filter frag = new Filter();
                    Bundle bundle = new Bundle();
                    bundle.putString("pName", value);
                    bundle.putString("pBrandId", pbrand_id);
                    bundle.putString("sortValue", sortValue);
                    frag.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.frame, frag);
                    fragmentTransaction.commitAllowingStateLoss();

                }

            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), sort);
                popupMenu.getMenuInflater().inflate(R.menu.location_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ascending_item:
                                sortDataAscending();

                                break;
                            case R.id.descending_item:
                                sortDataDescending();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();

                //  sortData();
            }
        });


        return view;
    }

    private void hitFilterApi(final String Warrenty, final String Series) {
        progressbarProduct.setVisibility(View.VISIBLE);
        listProduct.clear();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.getProductDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("lksdajhs", response);
                try {
                    progressbarProduct.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");

                    Log.d("ksaa", String.valueOf(jsonArray));
                    if(jsonArray.length()==0){
                        Toast.makeText(getContext(), "No Product Available", Toast.LENGTH_SHORT).show();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {

                        Product_Description model = new Product_Description();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        model.setId(jsonObject1.getString("id"));
                        model.setBrand(jsonObject1.getString("brand_name"));
                        model.setSeries_name(jsonObject1.getString("series_name"));
                        model.setModel(jsonObject1.getString("modelname"));
                        model.setGb(jsonObject1.getString("gb"));
                        model.setTv_wrnty(jsonObject1.getString("warrenty"));
                        model.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                        model.setDeduct_price(jsonObject1.getString("sale_amount"));
                        model.setImg_mobile(jsonObject1.getString("product_image"));
                        model.setTv_front_amera(jsonObject1.getString("front_camera"));
                        model.setTv_rear_amera(jsonObject1.getString("rear_camera"));
                        model.setTv_battery(jsonObject1.getString("battery"));
                        model.setTv_size(jsonObject1.getString("mobile_display"));
                        model.setRemark(jsonObject1.getString("remark"));
                        model.setCondition(jsonObject1.getString("product_condotion"));

                        listProduct.add(model);

                    }
                    adapter = new ProductAdapter(getActivity(), listProduct);
                    rvProduct.setAdapter(adapter);

                    if (code.equals("200")) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Filter", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarProduct.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("brandid", pbrand_id);

                if(Warrenty.equals("")){
                }else {
                    hashMap.put("warrenty", Warrenty);
                }
                if(Series.equals("")){
                }else {
                    hashMap.put("series", Series);
                }
             Log.d("asdqw", String.valueOf(hashMap));
                return hashMap;
            }

        };
        queue.getCache().clear();
        queue.add(request);

    }

    private void hitProductApi_More() {

        progressbarProduct.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.home_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdkddfd", response);
                //  progressDialog.dismiss();
                progressbarProduct.setVisibility(View.GONE);

                listProduct.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);


                    JSONArray otherArray = jsonObject1.getJSONArray("otherproduct");
                    if(otherArray.length()==0){
                        Toast.makeText(getContext(), "No Product Available", Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < otherArray.length(); i++) {
                        JSONObject jsonObjectAccessories = otherArray.getJSONObject(i);
                        Product_Description productModel = new Product_Description();
                        productModel.setImg_mobile(jsonObjectAccessories.getString("image"));
                        productModel.setId(jsonObjectAccessories.getString("id"));
                        productModel.setBrand(jsonObjectAccessories.getString("brand"));
                        productModel.setModel(jsonObjectAccessories.getString("model"));
                        productModel.setDeduct_price(jsonObjectAccessories.getString("sale_amount"));
                        productModel.setTv_size(jsonObjectAccessories.getString("mobile_display"));
                        productModel.setGb(jsonObjectAccessories.getString("gb"));
                        productModel.setTv_battery(jsonObjectAccessories.getString("battery"));
                        productModel.setTv_rear_amera(jsonObjectAccessories.getString("rear_camera"));
                        productModel.setTv_front_amera(jsonObjectAccessories.getString("front_camera"));
                        productModel.setTv_wrnty(jsonObjectAccessories.getString("warrenty"));
                        productModel.setSeries_name(jsonObjectAccessories.getString("series_name"));
                        productModel.setRemark(jsonObjectAccessories.optString("remark"));
                        productModel.setCondition(jsonObjectAccessories.optString("product_condotion"));

                        listProduct.add(productModel);

                    }
                    adapter = new ProductAdapter(getActivity(), listProduct);
                    rvProduct.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarProduct.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                Log.d("sad", hashMap.toString());
                return hashMap;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void hitProductApi_Accsees() {

        progressbarProduct.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ProductUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdkdddfd", response);
                progressbarProduct.setVisibility(View.GONE);

                listProduct.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                    JSONArray accessoriesArray = jsonObject1.getJSONArray("accessories");
                    for (int i = 0; i < accessoriesArray.length(); i++) {
                        JSONObject jsonObjectAccessories = accessoriesArray.getJSONObject(i);
                        Product_Description productModel = new Product_Description();
                        productModel.setImg_mobile(jsonObjectAccessories.getString("image"));
                        productModel.setId(jsonObjectAccessories.getString("id"));
                        productModel.setBrand(jsonObjectAccessories.getString("brand"));
                        productModel.setModel(jsonObjectAccessories.getString("model"));
                        productModel.setDeduct_price(jsonObjectAccessories.getString("sale_amount"));
                        productModel.setGb(jsonObjectAccessories.getString("gb"));
                        productModel.setTv_wrnty(jsonObjectAccessories.getString("warrenty"));
                        //   productModel.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                        productModel.setSeries_name(jsonObjectAccessories.getString("series_name"));
                        productModel.setTv_battery(jsonObjectAccessories.getString("battery"));
                        productModel.setTv_rear_amera(jsonObjectAccessories.getString("rear_camera"));
                        productModel.setTv_front_amera(jsonObjectAccessories.getString("front_camera"));
                        productModel.setTv_size(jsonObjectAccessories.getString("mobile_display"));
                        productModel.setRemark(jsonObjectAccessories.optString("remark"));
                        productModel.setCondition(jsonObjectAccessories.optString("product_condotion"));
                        Log.d("wsdfkj", jsonObjectAccessories.getString("brand"));
                        listProduct.add(productModel);

                    }
                    adapter = new ProductAdapter(getActivity(), listProduct);
                    rvProduct.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                progressbarProduct.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                Log.d("sad", hashMap.toString());
                return hashMap;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void hitProductApi() {

        progressbarProduct.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ProductUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbarProduct.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Product_Description model = new Product_Description();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        model.setId(jsonObject1.getString("id"));
                        model.setBrand(jsonObject1.getString("brand"));
                        model.setModel(jsonObject1.getString("model"));
                        model.setGb(jsonObject1.getString("gb"));
                        model.setTv_wrnty(jsonObject1.getString("warrenty"));
                        model.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                        model.setDeduct_price(jsonObject1.getString("sale_amount"));
                        model.setImg_mobile(jsonObject1.getString("image"));
                        model.setSeries_name(jsonObject1.getString("series_name"));
                        model.setTv_front_amera(jsonObject1.getString("front_camera"));
                        model.setTv_rear_amera(jsonObject1.getString("rear_camera"));
                        model.setTv_battery(jsonObject1.getString("battery"));
                        model.setTv_size(jsonObject1.getString("mobile_display"));
                        model.setCondition(jsonObject1.getString("product_condotion"));
                        model.setRemark(jsonObject1.getString("remark"));
                        listProduct.add(model);

                    }

                    adapter = new ProductAdapter(getActivity(), listProduct);
                    rvProduct.setAdapter(adapter);

                    if (code.equals("200")) {
//
                    } else if (code.equals("401")) {
                        Toast.makeText(getActivity(), "No Product Available", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarProduct.setVisibility(View.GONE);
                Toast.makeText(getActivity(), (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("brand", pbrand_id);
                return hashMap;

            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void hitProductRangeApi() {
        progressbarProduct.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Api.filter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("qwerasd", response);
                progressbarProduct.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Product_Description model = new Product_Description();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        model.setId(jsonObject1.getString("id"));
                        model.setBrand(jsonObject1.getString("brand"));
                        model.setModel(jsonObject1.getString("model"));
                        model.setGb(jsonObject1.getString("gb"));
                        model.setTv_wrnty(jsonObject1.getString("warrenty"));
                        model.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                        model.setDeduct_price(jsonObject1.getString("sale_amount"));
                        model.setImg_mobile(jsonObject1.getString("image"));
                        model.setSeries_name(jsonObject1.getString("series_name"));
                        model.setTv_front_amera(jsonObject1.getString("front_camera"));
                        model.setTv_rear_amera(jsonObject1.getString("rear_camera"));
                        model.setTv_battery(jsonObject1.getString("battery"));
                        model.setTv_size(jsonObject1.getString("mobile_display"));
                        model.setCondition(jsonObject1.getString("product_condotion"));
                        model.setRemark(jsonObject1.getString("remark"));
                        listProduct.add(model);
                    }
                    adapter = new ProductAdapter(getActivity(), listProduct);
                    rvProduct.setAdapter(adapter);

                    if (code.equals("200")) {

                    } else if (code.equals("401")) {
                        Toast.makeText(getActivity(), "No Product Available", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("type", type);
                hashMap.put("key", Api.key);
                return hashMap;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(request);

    }


    //////////////////////////////////////////////////// sort data///////////////////////////////////////////////////////////


    private void sortDataDescending() {

        if (sortValue.equals("mobile")) {
            sortingType = "desc";
            hitMobileAPI();
        }
        if (sortValue.equals("tablet")) {
            sortingType = "desc";
            hitTabletAPI();
        }
        if (sortValue.equals("accessories")) {
            sortingType = "desc";
            hitAccessoriesAPI();
        }
        if (sortValue.equals("more")) {
            sortingType = "desc";
            hitMoreAPI();
        }
        if (sortValue.equals("range")) {
            sortingType = "desc";
            hitRangeAPI();
        }


    }


    private void sortDataAscending() {

        if (sortValue.equals("mobile")) {
            sortingType = "asc";
            hitMobileAPI();
        }
        if (sortValue.equals("tablet")) {
            sortingType = "asc";
            hitTabletAPI();
        }
        if (sortValue.equals("accessories")) {
            sortingType = "asc";
            hitAccessoriesAPI();
        }
        if (sortValue.equals("more")) {
            sortingType = "asc";
            hitMoreAPI();
        }
        if (sortValue.equals("range")) {
            sortingType = "asc";
            hitRangeAPI();
        }

    }

    private void hitAccessoriesAPI() {
        {

            listProduct.clear();


            progressbarProduct.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest request = new StringRequest(Request.Method.POST, Api.sortMore, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressbarProduct.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("msg");
                        String code = jsonObject.getString("code");
                        Log.d("sadasdsda", response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Product_Description model = new Product_Description();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            model.setId(jsonObject1.getString("id"));
                            model.setBrand(jsonObject1.getString("brand"));
                            model.setModel(jsonObject1.getString("model"));
                            model.setGb(jsonObject1.getString("gb"));
                            model.setTv_wrnty(jsonObject1.getString("warrenty"));
                            model.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                            model.setDeduct_price(jsonObject1.getString("sale_amount"));
                            model.setImg_mobile(jsonObject1.getString("image"));
                            model.setSeries_name(jsonObject1.getString("series_name"));
                            model.setTv_front_amera(jsonObject1.getString("front_camera"));
                            model.setTv_rear_amera(jsonObject1.getString("rear_camera"));
                            model.setTv_battery(jsonObject1.getString("battery"));
                            model.setTv_size(jsonObject1.getString("mobile_display"));
                            model.setCondition(jsonObject1.getString("product_condotion"));
                            model.setRemark(jsonObject1.getString("remark"));
                            listProduct.add(model);

                            adapter = new ProductAdapter(getActivity(), listProduct);
                            rvProduct.setAdapter(adapter);

                            Log.d("sdfops", jsonObject1.getString("id"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressbarProduct.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), (CharSequence) error, Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("key", Api.key);
                    hashMap.put("category", "accessories");

                    if (sortingType.equals("desc")) {
                        hashMap.put("order", "desc");
                    }
                    if (sortingType.equals("asc")) {
                        hashMap.put("order", "asc");
                    }
                    Log.d("adsklj", String.valueOf(hashMap));
                    return hashMap;

                }
            };
            requestQueue.getCache().clear();
            requestQueue.add(request);


        }
    }

    private void hitTabletAPI() {
        {
            listProduct.clear();

            progressbarProduct.setVisibility(View.VISIBLE);

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest request = new StringRequest(Request.Method.POST, Api.productBrandTab, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressbarProduct.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("msg");
                        String code = jsonObject.getString("code");
                        Log.d("sadasdsda", response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Product_Description model = new Product_Description();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            model.setId(jsonObject1.getString("id"));
                            model.setBrand(jsonObject1.getString("brand"));
                            model.setModel(jsonObject1.getString("model"));
                            model.setGb(jsonObject1.getString("gb"));
                            model.setTv_wrnty(jsonObject1.getString("warrenty"));
                            model.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                            model.setDeduct_price(jsonObject1.getString("sale_amount"));
                            model.setImg_mobile(jsonObject1.getString("image"));
                            model.setSeries_name(jsonObject1.getString("series_name"));
                            model.setTv_front_amera(jsonObject1.getString("front_camera"));
                            model.setTv_rear_amera(jsonObject1.getString("rear_camera"));
                            model.setTv_battery(jsonObject1.getString("battery"));
                            model.setTv_size(jsonObject1.getString("mobile_display"));
                            model.setCondition(jsonObject1.getString("product_condotion"));
                            model.setRemark(jsonObject1.getString("remark"));
                            listProduct.add(model);

                            adapter = new ProductAdapter(getActivity(), listProduct);
                            rvProduct.setAdapter(adapter);

                            Log.d("sdfops", jsonObject1.getString("id"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressbarProduct.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), (CharSequence) error, Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("key", Api.key);
                    hashMap.put("brand", pbrand_id);

                    if (sortingType.equals("desc")) {
                        hashMap.put("order", "desc");
                    }
                    if (sortingType.equals("asc")) {
                        hashMap.put("order", "asc");
                    }
                    Log.d("adsklj", String.valueOf(hashMap));
                    return hashMap;

                }
            };
            requestQueue.getCache().clear();
            requestQueue.add(request);


        }
    }

    private void hitMobileAPI() {
        listProduct.clear();

        progressbarProduct.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Api.productBrandMobile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbarProduct.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");
                    Log.d("sadasdsda", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Product_Description model = new Product_Description();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        model.setId(jsonObject1.getString("id"));
                        model.setBrand(jsonObject1.getString("brand"));
                        model.setModel(jsonObject1.getString("model"));
                        model.setGb(jsonObject1.getString("gb"));
                        model.setTv_wrnty(jsonObject1.getString("warrenty"));
                        model.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                        model.setDeduct_price(jsonObject1.getString("sale_amount"));
                        model.setImg_mobile(jsonObject1.getString("image"));
                        model.setSeries_name(jsonObject1.getString("series_name"));
                        model.setTv_front_amera(jsonObject1.getString("front_camera"));
                        model.setTv_rear_amera(jsonObject1.getString("rear_camera"));
                        model.setTv_battery(jsonObject1.getString("battery"));
                        model.setTv_size(jsonObject1.getString("mobile_display"));
                        model.setCondition(jsonObject1.getString("product_condotion"));
                        model.setRemark(jsonObject1.getString("remark"));
                        listProduct.add(model);

                        adapter = new ProductAdapter(getActivity(), listProduct);
                        rvProduct.setAdapter(adapter);

                        Log.d("sdfops", jsonObject1.getString("id"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarProduct.setVisibility(View.GONE);
                Toast.makeText(getActivity(), (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("brand", pbrand_id);

                if (sortingType.equals("desc")) {
                    hashMap.put("order", "desc");
                }
                if (sortingType.equals("asc")) {
                    hashMap.put("order", "asc");
                }
                Log.d("adsklj", String.valueOf(hashMap));
                return hashMap;

            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(request);


    }


    private void hitMoreAPI() {

        listProduct.clear();


        progressbarProduct.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Api.sortMore, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbarProduct.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");
                    Log.d("sadasdsda", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Product_Description model = new Product_Description();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        model.setId(jsonObject1.getString("id"));
                        model.setBrand(jsonObject1.getString("brand"));
                        model.setModel(jsonObject1.getString("model"));
                        model.setGb(jsonObject1.getString("gb"));
                        model.setTv_wrnty(jsonObject1.getString("warrenty"));
                        model.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                        model.setDeduct_price(jsonObject1.getString("sale_amount"));
                        model.setImg_mobile(jsonObject1.getString("image"));
                        model.setSeries_name(jsonObject1.getString("series_name"));
                        model.setTv_front_amera(jsonObject1.getString("front_camera"));
                        model.setTv_rear_amera(jsonObject1.getString("rear_camera"));
                        model.setTv_battery(jsonObject1.getString("battery"));
                        model.setTv_size(jsonObject1.getString("mobile_display"));
                        model.setCondition(jsonObject1.getString("product_condotion"));
                        model.setRemark(jsonObject1.getString("remark"));
                        listProduct.add(model);

                        adapter = new ProductAdapter(getActivity(), listProduct);
                        rvProduct.setAdapter(adapter);

                        Log.d("sdfops", jsonObject1.getString("id"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarProduct.setVisibility(View.GONE);
                Toast.makeText(getActivity(), (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("category", "other");

                if (sortingType.equals("desc")) {
                    hashMap.put("order", "desc");
                }
                if (sortingType.equals("asc")) {
                    hashMap.put("order", "asc");
                }
                Log.d("adsklj", String.valueOf(hashMap));
                return hashMap;

            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(request);


    }

    private void hitRangeAPI() {

        listProduct.clear();

        progressbarProduct.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Api.filter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbarProduct.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");
                    Log.d("sadasdsda", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Product_Description model = new Product_Description();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        model.setId(jsonObject1.getString("id"));
                        model.setBrand(jsonObject1.getString("brand"));
                        model.setModel(jsonObject1.getString("model"));
                        model.setGb(jsonObject1.getString("gb"));
                        model.setTv_wrnty(jsonObject1.getString("warrenty"));
                        model.setTv_wrntyMonth(jsonObject1.getString("warrenty_month"));
                        model.setDeduct_price(jsonObject1.getString("sale_amount"));
                        model.setImg_mobile(jsonObject1.getString("image"));
                        model.setSeries_name(jsonObject1.getString("series_name"));
                        model.setTv_front_amera(jsonObject1.getString("front_camera"));
                        model.setTv_rear_amera(jsonObject1.getString("rear_camera"));
                        model.setTv_battery(jsonObject1.getString("battery"));
                        model.setTv_size(jsonObject1.getString("mobile_display"));
                        model.setCondition(jsonObject1.getString("product_condotion"));
                        model.setRemark(jsonObject1.getString("remark"));
                        listProduct.add(model);

                        adapter = new ProductAdapter(getActivity(), listProduct);
                        rvProduct.setAdapter(adapter);

                        Log.d("sdfops", jsonObject1.getString("id"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarProduct.setVisibility(View.GONE);
                Toast.makeText(getActivity(), (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("type", type);

                if (sortingType.equals("desc")) {
                    hashMap.put("order", "desc");
                }
                if (sortingType.equals("asc")) {
                    hashMap.put("order", "asc");
                }
                Log.d("adsklj", String.valueOf(hashMap));
                return hashMap;

            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(request);


    }


    ///////////////////////////////////////////// recyclerView /////////////////////////////////////////////////////////////////////////////

    private void setRvProduct() {

        adapter = new ProductAdapter(getActivity(), listProduct);
        rvProduct.setAdapter(adapter);

    }

    //////////////////////////////////////////////////  Adapter  ////////////////////////////////////////////////////////////

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        Context context;
        ArrayList<Product_Description> arrProduct;

        public ProductAdapter(Context context, ArrayList<Product_Description> arrProduct) {
            this.context = context;
            this.arrProduct = arrProduct;
        }


        @NonNull
        @Override
        public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_product, parent, false);
            return new ProductAdapter.ProductViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
            Product_Description obj = arrProduct.get(position);
            Picasso.get().load(arrProduct.get(position).getImg_mobile()).into(holder.img_mobile);
            holder.deduct_price.setText(obj.getDeduct_price());
            holder.tv_brand.setText(obj.getBrand());
            holder.tv_model.setText(obj.getModel());
            holder.wrntyInOut.setText(obj.getTv_wrnty());
            holder.tv_gb.setText(obj.getGb());

            if (obj.getTv_battery().equalsIgnoreCase("null")) {
                cardBattery.setVisibility(View.GONE);
            } else {
                holder.tv_battery.setText(obj.getTv_battery());
            }
            if (obj.getTv_size().equalsIgnoreCase("null")) {
                cardDisplay.setVisibility(View.GONE);
            } else {
                holder.tv_size.setText(obj.getTv_size());
            }
            if (obj.getTv_front_amera().equalsIgnoreCase("null")) {
                cardFront.setVisibility(View.GONE);
            } else {
                holder.tv_front_amera.setText(obj.getTv_front_amera());
            }
            if (obj.getTv_rear_amera().equalsIgnoreCase("null")) {
                cardRear.setVisibility(View.GONE);
            } else {
                holder.tv_rear_amera.setText(obj.getTv_rear_amera());
            }
        }

        @Override
        public int getItemCount() {
            return arrProduct.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder {
            ImageView img_mobile;
            LinearLayout layoutVisibility;
            TextView tv_brand, tv_model, tv_gb, deduct_price,
                    tv_size, tv_battery, tv_rear_amera, tv_front_amera, wrntyInOut;


            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                img_mobile = itemView.findViewById(R.id.img_mobile);
                deduct_price = itemView.findViewById(R.id.deduct_price);
                tv_brand = itemView.findViewById(R.id.tv_brand);
                tv_model = itemView.findViewById(R.id.tv_model);
                tv_gb = itemView.findViewById(R.id.tv_gb);
                tv_size = itemView.findViewById(R.id.tv_size);
                tv_battery = itemView.findViewById(R.id.tv_battery);
                tv_rear_amera = itemView.findViewById(R.id.tv_rear_amera);
                tv_front_amera = itemView.findViewById(R.id.tv_front_amera);
                wrntyInOut = itemView.findViewById(R.id.wrntyInOut);
                layoutVisibility = itemView.findViewById(R.id.layoutVisibility);

                cardBattery = itemView.findViewById(R.id.cardBattery);
                cardDisplay = itemView.findViewById(R.id.cardDisplay);
                cardRear = itemView.findViewById(R.id.cardRear);
                cardFront = itemView.findViewById(R.id.cardFront);


                if(sortValue.equals("more")||sortValue.equals("accessories")) {
                    layoutVisibility.setVisibility(View.GONE);
                } else {
                    layoutVisibility.setVisibility(View.VISIBLE);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Product_Description product_description = listProduct.get(getAdapterPosition());
                        String id = product_description.getId();
                        String brand = product_description.getBrand();
                        String model = product_description.getModel();
                        String gb = product_description.getGb();
                        String price = product_description.getDeduct_price();
                        String warantyMonth = product_description.getTv_wrntyMonth();
                        String waranty = product_description.getTv_wrnty();
                        String imgMobile = product_description.getImg_mobile();
                        String remark = product_description.getRemark();
                        String condition = product_description.getCondition();

                        Log.d("sasdwera", id + " " + brand + " " + price + " " + warantyMonth + " " + model + " " + gb);

                        Book frag = new Book();
                        Bundle bundle = new Bundle();
                        bundle.putString("ImageId", id);
                        bundle.putString("brand", brand);
                        bundle.putString("model", model);
                        bundle.putString("gb", gb);
                        bundle.putString("price", price);
                        bundle.putString("warrantyMonth", warantyMonth);
                        bundle.putString("warranty", waranty);
                        bundle.putString("image", imgMobile);
                        bundle.putString("remark", remark);
                        bundle.putString("condition", condition);
                        bundle.putString("pBrandId", pbrand_id);
                        frag.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, frag);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commitAllowingStateLoss();
                        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


                    }
                });

            }
        }
    }

}



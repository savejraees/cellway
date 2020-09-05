package com.cellway.Cellway.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.FilterAdapter;
import com.cellway.Cellway.model.Product_Description;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Filter extends Fragment {


    RecyclerView rv_mobile_series;

    LinearLayout layoutCheckbox;
    ArrayList<Product_Description> listProduct;
    ArrayList<Product_Description> filterList;
    ArrayList<Product_Description> seriesList;


    RelativeLayout tv_series;
    RelativeLayout tv_Warrnty;
    CheckBox chkIn, chkOut;
    Button btnApply;
    FilterAdapter adapter;

    String data = "", pbrand_id;
    String Warrenty = "";
    String value;
    String sortValue = "";
    int countForFilter = 1;

    public Filter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        filterList = new ArrayList<>();
        seriesList = new ArrayList<>();
        listProduct = new ArrayList<>();
        value = getArguments().getString("pName");
        pbrand_id = getArguments().getString("pBrandId");
        sortValue = getArguments().getString("sortValue");

        Log.d("aswqes", pbrand_id + " " + sortValue + " " + value);
        rv_mobile_series = (RecyclerView) view.findViewById(R.id.rv_mobile_series);
        rv_mobile_series.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rv_mobile_series.setLayoutManager(layoutManager);


        hitSeriesApi();


        tv_series = view.findViewById(R.id.series);
        tv_Warrnty = view.findViewById(R.id.tv_Warrnty);

        layoutCheckbox = view.findViewById(R.id.layoutCheckbox);

        chkIn = view.findViewById(R.id.chkIn);
        chkOut = view.findViewById(R.id.chkOut);
        btnApply = view.findViewById(R.id.btnApply);


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String series="";
                filterList.clear();
                ArrayList<Product_Description> lstSeries = ((FilterAdapter) adapter).getSeriesList();
                getViewModelStore().clear();
                Product_Description singleSeries;
                for (int i = 0; i < lstSeries.size(); i++) {
                    singleSeries = lstSeries.get(i);
                    if (singleSeries.isSelected() == true) {

                        if (data.equals("")) {
                            data = singleSeries.getSeries_name();
                        } else {
                            data = data + "," + singleSeries.getSeries_name();
                        }
                    }
                }

                //   Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                if (countForFilter == 1) {
                 //   hitDetailsApi();

                    Log.d("dqeaxxc",Warrenty);

                    if ((chkIn.isChecked() && chkOut.isChecked()) && (data.equals(""))) {
                        Warrenty = "all";
                        series = "";
                    }
                   else if ((chkIn.isChecked() && chkOut.isChecked()) && (!data.equals(""))) {
                        Warrenty = "all";
                        series = data;

                    }
                    else if (chkIn.isChecked() && (!data.equals(""))) {
                        series = data;
                    }
                   else if ((chkIn.isChecked()) && (data.equals(""))) {
                        series = "";
                    }
                   else if (chkOut.isChecked() && (!data.equals(""))) {
                        series = data;
                    }
                   else if (chkOut.isChecked() && data.equals("")) {
                        series = "";
                    }
                   else if (((!chkOut.isChecked()) && (!chkIn.isChecked()) && (data.equals("")))) {
                        Warrenty = "";
                        series = data;
                    }
                    else if (!data.equals("")) {
                        Warrenty = "";
                        series = data;
                    }


                    Log.d("dqesaxc",Warrenty+"  "+series);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Filter", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("warranty", Warrenty).apply();
                    sharedPreferences.edit().putString("series", series).apply();

                    Product frag = new Product();
                    Bundle bundle = new Bundle();
                    bundle.putString("pName", value);
                    bundle.putString("pBrandId", pbrand_id);
                    bundle.putString("sortValue", "Filter");
                    frag.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.frame, frag);
                    fragmentTransaction.commit();

                }

            }
        });

        click();
        //    rv_Series();
        return view;
    }


    private void hitSeriesApi() {


        seriesList.clear();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.getSeries, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("lkjhs", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Product_Description model = new Product_Description();
                        model.setId(jsonObject1.getString("id"));
                        model.setSeries_name(jsonObject1.getString("series_name"));

                        Log.d("dsffsadfs", jsonObject1.getString("series_name"));
                        seriesList.add(model);
                    }
                    Log.d("sernme", String.valueOf(seriesList));

                    adapter = new FilterAdapter(getActivity(), seriesList);
                    rv_mobile_series.setAdapter(adapter);
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
                hashMap.put("key", Api.key);
                hashMap.put("brand_id", pbrand_id);
                return hashMap;
            }


        };
        queue.getCache().clear();
        queue.add(request);

    }

    private void hitDetailsApi() {

        listProduct.clear();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.getProductDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("lksdajhs", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");

                    Log.d("ksaa", String.valueOf(jsonArray));

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
                    Log.d("sername", String.valueOf(listProduct));

                    if (code.equals("200")) {

                        Product frag = new Product();
                        Bundle bundle = new Bundle();
                        //bundle.putParcelableArrayList("productDetails", listProduct);
                        bundle.putString("pName", value);
                        bundle.putString("pBrandId", pbrand_id);
                        bundle.putString("sortValue", sortValue);
                        frag.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame, frag);
                        fragmentTransaction.commit();


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
                hashMap.put("key", Api.key);

                if ((chkIn.isChecked() && chkOut.isChecked()) && (data.equals(""))) {
                    hashMap.put("warrenty", "all");
                    hashMap.put("brandid", pbrand_id);
                } else if ((chkIn.isChecked() && chkOut.isChecked()) && (!data.equals(""))) {
                    hashMap.put("warrenty", "all");
                    hashMap.put("series", data);
                    hashMap.put("brandid", pbrand_id);
                } else if (chkIn.isChecked() && (!data.equals(""))) {
                    hashMap.put("warrenty", Warrenty);
                    hashMap.put("series", data);
                    hashMap.put("brandid", pbrand_id);
                } else if ((chkIn.isChecked()) && (data.equals(""))) {
                    hashMap.put("warrenty", Warrenty);
                    hashMap.put("brandid", pbrand_id);
                } else if (chkOut.isChecked() && (!data.equals(""))) {
                    hashMap.put("warrenty", Warrenty);
                    hashMap.put("series", data);
                    hashMap.put("brandid", pbrand_id);
                } else if (chkOut.isChecked() && data.equals("")) {
                    hashMap.put("warrenty", Warrenty);
                    hashMap.put("brandid", pbrand_id);
                } else if (((!chkOut.isChecked()) && (!chkIn.isChecked()) && (data.equals("")))) {
                    hashMap.put("series", data);
                    hashMap.put("brandid", pbrand_id);
                } else if (!data.equals("")) {
                    hashMap.put("series", data);
                    hashMap.put("brandid", pbrand_id);
                }

                return hashMap;
            }

        };
        queue.getCache().clear();
        queue.add(request);

    }

    private void click() {
        tv_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_series.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_Warrnty.setBackgroundColor(Color.parseColor("#ECE9E9"));

                rv_mobile_series.setVisibility(View.VISIBLE);
                layoutCheckbox.setVisibility(View.GONE);
            }
        });

        tv_Warrnty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_series.setBackgroundColor(Color.parseColor("#ECE9E9"));
                tv_Warrnty.setBackgroundColor(Color.parseColor("#FFFFFF"));
                layoutCheckbox.setVisibility(View.VISIBLE);
                rv_mobile_series.setVisibility(View.GONE);
            }
        });

        chkIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                
                if (chkIn.isChecked()) {
                    Warrenty = "In";
                }
            }
        });
        chkOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (chkOut.isChecked()) {
                    Warrenty = "out";
                }
            }
        });


    }


//    private void rv_Series() {
//
//        adapter = new FilterAdapter(getActivity(), seriesList);
//        rv_mobile_series.setAdapter(adapter);
//    }
}


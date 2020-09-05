package com.cellway.Cellway.fragment;



import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.R;

import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.Mobile_slide_Adapter;
import com.cellway.Cellway.model.Mobile_destination;
import com.cellway.Cellway.model.Product_Description;
import com.cellway.Cellway.model.TabletModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TabletDestination extends Fragment {

View view;
RecyclerView rvTablet;

    Tablet_Adapter adapter;
    ArrayList<TabletModel> brandIconArrlist=new ArrayList<>();

    Toolbar tabletToolbar;
    ArrayList<Product_Description> listProdt;
    ProgressBar progressbarTDest;
    public TabletDestination() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        ((Home) getActivity()).getSupportActionBar().setTitle("Tablet Destination");
        view = inflater.inflate(R.layout.fragment_tablet_destination, container, false);

        listProdt = new ArrayList<>();

      //  brandIconArrlist = getArguments().getParcelableArrayList("tablet_Brand");
        rvTablet = view.findViewById(R.id.rvTablet);
        progressbarTDest = view.findViewById(R.id.progressbarTDest);

      //  setTablet();
          hitTabletIconApi();
        tabletToolbar = view.findViewById(R.id.tabletToolbar);
        tabletToolbar.setTitle("Tablet Destination");
        tabletToolbar.setTitleTextColor(Color.WHITE);
        tabletToolbar.setNavigationIcon(R.drawable.arrow_back);
        tabletToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

  return view;
    }

    private void hitTabletIconApi() {
        {

            progressbarTDest.setVisibility(View.VISIBLE);

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.home_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("sdkddfd", response);
                    //  progressDialog.dismiss();
                    progressbarTDest.setVisibility(View.GONE);
                    brandIconArrlist.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                    JSONArray tabletArray = jsonObject1.getJSONArray("tabletbrand");
                    for (int i = 0; i < tabletArray.length(); i++) {
                        JSONObject jsonObjectTablet = tabletArray.getJSONObject(i);
                        TabletModel tabletModel = new TabletModel();
                        tabletModel.setTabimage(jsonObjectTablet.getString("brand_icon"));
                        tabletModel.setId(jsonObjectTablet.getString("id"));
                        tabletModel.setBrandname(jsonObjectTablet.getString("brand_name"));
                        brandIconArrlist.add(tabletModel);

                    }
                        rvTablet.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
                        rvTablet.setLayoutManager(layoutManager);
                        adapter=new Tablet_Adapter(getActivity(),brandIconArrlist);
                        rvTablet.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  progressDialog.dismiss();
                    progressbarTDest.setVisibility(View.GONE);
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
    }

    ////////////////////////////////////////////// Api for Tablet Details //////////////////////////////////////////////////////

         public void hitTabletBrandDetails(final String id, final String brandName){

             progressbarTDest.setVisibility(View.VISIBLE);
             RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
             StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.productBrandTab, new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                     progressbarTDest.setVisibility(View.GONE);

                     try {
                         JSONObject  jsonObject = new JSONObject(response);
                         String msg = jsonObject.getString("msg");
                         String code = jsonObject.getString("code");
                         JSONArray jsonArray = jsonObject.getJSONArray("data");
                         for(int i=0;i<jsonArray.length();i++){
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
                             listProdt.add(model);

                         }

                         if (code.equals("200")){
                             Product frag = new Product();
                             FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                             Bundle bundle = new Bundle();
                             bundle.putString("pName",brandName);
                             bundle.putString("sortValue", "tablet");
                             bundle.putParcelableArrayList("productDetails",listProdt);
                             bundle.putString("pBrandId",id);
                             frag.setArguments(bundle);
                             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                             fragmentTransaction.addToBackStack(null);
                             fragmentTransaction.replace(R.id.frame,frag);
                             fragmentTransaction.commitAllowingStateLoss();
                             ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
                         }
                         else if(code.equals("401")){
                             Toast.makeText(getActivity(), "No Product Available", Toast.LENGTH_SHORT).show();
                         }




                     } catch (JSONException e) {
                         e.printStackTrace();
                     }

                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     progressbarTDest.setVisibility(View.GONE);
                     Toast.makeText(getActivity(), (CharSequence) error, Toast.LENGTH_SHORT).show();
                 }
             }){
                 protected Map<String,String> getParams(){
                     HashMap<String,String> hashMap = new HashMap<>();
                     hashMap.put("key",Api.key);
                     hashMap.put("brand",id);
                     return hashMap;

                 }


             };
             requestQueue.getCache().clear();
             requestQueue.add(stringRequest);

         }


    ////////////////////////////////////////////////////  Adapter  /////////////////////////////////////////////////////////////////////


    public class Tablet_Adapter extends RecyclerView.Adapter<Tablet_Adapter.TabletViewHolder> {

        Context context;
        ArrayList<TabletModel> mdest;

        public Tablet_Adapter(Context context, ArrayList<TabletModel> mdest) {
            this.context = context;
            this.mdest = mdest;
        }


        @NonNull
        @Override
        public Tablet_Adapter.TabletViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_tablet, viewGroup, false);


            return new Tablet_Adapter.TabletViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Tablet_Adapter.TabletViewHolder mobileViewHolder, int position) {

            TabletModel all = mdest.get(position);
            Picasso.get().load(all.getTabimage()).into(mobileViewHolder.tablet_image);

        }

        @Override
        public int getItemCount() {
            return mdest.size();
        }

        public class TabletViewHolder extends RecyclerView.ViewHolder {

//            FragmentManager fragmentManager = ((Home) context).getSupportFragmentManager();
            private ImageView tablet_image;


            public TabletViewHolder(@NonNull View itemView) {
                super(itemView);

                tablet_image = itemView.findViewById(R.id.tablet_image);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TabletModel tabletModel = brandIconArrlist.get(getAdapterPosition());
                        String id = tabletModel.getId();
                        String brandName = tabletModel.getBrandname();

                        Log.d("retsd",id+" "+brandName);

//                        hitTabletBrandDetails(id, brandName);

                        Product frag = new Product();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("pName",brandName);
                        bundle.putString("sortValue", "tablet");
                        bundle.putString("pBrandId",id);
                        frag.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame,frag);
                        fragmentTransaction.commitAllowingStateLoss();
                        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();



//                        if(getAdapterPosition()==0){
//                            Product frag = new Product();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("pName", "Apple");
//                            frag.setArguments(bundle);
//                            fragmentManager.beginTransaction().replace(R.id.frame,
//                                    frag, frag.getTag())
//                                    .addToBackStack(null)
//                                    .commit();
//
//                        }
//                        if(getAdapterPosition()==1){
//                            Product frag = new Product();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("pName", "Samsung");
//                            frag.setArguments(bundle);
//                            fragmentManager.beginTransaction().replace(R.id.frame,
//                                    frag, frag.getTag())
//                                    .addToBackStack(null)
//                                    .commit();
//
//                        }

                    }
                });
            }
        }
    }

}

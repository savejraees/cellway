package com.cellway.Cellway.fragment;

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


public class Mobile_Destination extends Fragment {

    View view;
    RecyclerView rvMobile;
    ArrayList<Mobile_destination> brandIconArrlist = new ArrayList<>();
    Mobile_Adapter adapter;
    ImageView img7000, Img7_15, img15_30, img_above30;
    ProgressBar progressbarMDest;

    Toolbar destinationToobar;
    ArrayList<Product_Description> listProdt;
    ArrayList<Product_Description> listRange;

    public Mobile_Destination(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_mobile__destination, container, false);

//        ((Home) getActivity()).getSupportActionBar().setTitle("Mobile Destination");
        listProdt = new ArrayList<>();
        listRange = new ArrayList<>();
      //  brandIconArrlist =  getArguments().getParcelableArrayList("mobile_brand");
        destinationToobar = view.findViewById(R.id.destinationToobar);
        destinationToobar.setTitle("Mobile Destination");
        destinationToobar.setTitleTextColor(Color.WHITE);
        destinationToobar.setNavigationIcon(R.drawable.arrow_back);
        destinationToobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

       // Log.d("fguy", String.valueOf(brandIconArrlist));
        img7000 = view.findViewById(R.id.img7000);
        Img7_15 = view.findViewById(R.id.Img7_15);
        img15_30 = view.findViewById(R.id.img15_30);
        img_above30 = view.findViewById(R.id.img_above30);

        rvMobile = view.findViewById(R.id.rvMobile);
        progressbarMDest = view.findViewById(R.id.progressbarMDest);

          hitMobileIconApi();

        imageClick();

        return view;

    }

    private void hitMobileIconApi() {

        progressbarMDest.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.home_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdkddfd", response);
                //  progressDialog.dismiss();
                progressbarMDest.setVisibility(View.GONE);
                brandIconArrlist.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                    JSONArray jsonArray1 = jsonObject1.getJSONArray("mobilebrand");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                        Mobile_destination mobile_destination = new Mobile_destination();
                        mobile_destination.setMobDestimg(jsonObject2.getString("brand_icon"));
                        mobile_destination.setId(jsonObject2.getString("id"));
                        mobile_destination.setBrand_name(jsonObject2.getString("brand_name"));
                        brandIconArrlist.add(mobile_destination);

                    }

                    rvMobile.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                    rvMobile.setLayoutManager(layoutManager);
                    adapter = new Mobile_Adapter(getActivity(), brandIconArrlist);
                    rvMobile.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarMDest.setVisibility(View.GONE);
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

    private void imageClick() {

        img7000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String brandRange = "Under 7000";
                loadFragment(brandRange);

            }
        });
        Img7_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String brandRange = "7000-15000";
                loadFragment(brandRange);

            }
        });
        img15_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String brandRange = "15000-30000";
                loadFragment(brandRange);

            }
        });
        img_above30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String brandRange = "above 30000";
                loadFragment(brandRange);

            }
        });
    }

    public class Mobile_Adapter extends RecyclerView.Adapter<Mobile_Adapter.MobileViewHolder> {

        Context context;
        ArrayList<Mobile_destination> mdest;

        public Mobile_Adapter(Context context, ArrayList<Mobile_destination> mdest) {
            this.context = context;
            this.mdest = mdest;
        }


        @NonNull
        @Override
        public MobileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_mobile, viewGroup, false);
            return new MobileViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MobileViewHolder mobileViewHolder, int position) {
            //Mobile_destination all = mdest.get(position);
            Picasso.get().load(mdest.get(position).getMobDestimg()).into(mobileViewHolder.grid_image);
            Log.d("ghi",mdest.get(position).getMobDestimg());
        }

        @Override
        public int getItemCount() {
            return mdest.size();
        }

        public class MobileViewHolder extends RecyclerView.ViewHolder {

            private ImageView grid_image;

            public MobileViewHolder(@NonNull View itemView) {
                super(itemView);


                grid_image = itemView.findViewById(R.id.grid_image);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    Mobile_destination id = brandIconArrlist.get(getAdapterPosition());
                    String brandId = id.getId();
                    String brandName = id.getBrand_name();


                    Log.d("qewoip",brandId + " "+brandName);

                        //hitmobileBrandDetails(brandId,brandName);

                        Product frag = new Product();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("pName",brandName);
                        bundle.putString("pBrandId",brandId);
                        bundle.putString("sortValue", "mobile");
                        frag.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame,frag);
                        fragmentTransaction.commitAllowingStateLoss();
                        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

                    }
                });

            }
        }

    }

    public void loadFragment(String BrandRange){
        Product frag = new Product();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("pName",BrandRange);
        bundle.putString("pBrandId"," ");
        bundle.putString("sortValue", "range");
        frag.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame,frag);
        fragmentTransaction.commitAllowingStateLoss();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}

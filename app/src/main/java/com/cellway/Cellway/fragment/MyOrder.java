package com.cellway.Cellway.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.model.OrderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyOrder extends Fragment {

    View view;
    //LinearLayout layoutOrderdetails;
    TextView tv_View;
    ProgressBar progressbarOrder;
    String mobileNo;
    ArrayList<OrderModel> listOrder;
    OrderAdapter adapter;
    RecyclerView rv_order;

    String inputPattern = "yyyy-MM-dd HH:mm:ss";
    String outputPattern = "dd-MMM-yyyy";
    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
    Date date = null;
    String str = null;
    public MyOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_order, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("My Order");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Cellways", Context.MODE_PRIVATE);
        mobileNo = sharedPreferences.getString("mbl", "");


        listOrder = new ArrayList<>();
//        layoutOrderdetails =  view.findViewById(R.id.layoutOrderdetails);
        //tv_View =  view.findViewById(R.id.tv_View);
        progressbarOrder = view.findViewById(R.id.progressbarOrder);
        rv_order = view.findViewById(R.id.rv_order);


//                layoutOrderdetails.setVisibility(View.VISIBLE);
        hitMyOrderApi();


        return view;

    }

    private void hitMyOrderApi() {
        listOrder.clear();
        progressbarOrder.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.MyOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbarOrder.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        OrderModel model = new OrderModel();
                        model.setBrand(jsonObject1.getString("brand"));
                        model.setModel(jsonObject1.getString("model"));
                        model.setSaleAmount(jsonObject1.getString("sale_amount"));
                        model.setGb(jsonObject1.getString("gb"));
                       // model.setDate(jsonObject1.getString("cdate"));
                        date = inputFormat.parse(jsonObject1.getString("cdate"));
                        str = outputFormat.format(date);
                        model.setDate(str);

                        listOrder.add(model);
                        Log.d("dsad", String.valueOf(listOrder));

                        rv_order.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                        rv_order.setLayoutManager(layoutManager);
                        adapter = new OrderAdapter(listOrder, getActivity());
                        rv_order.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarOrder.setVisibility(View.GONE);

            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("mobile", mobileNo);
                Log.d("oweipq", String.valueOf(hashMap));
                return hashMap;
            }


        };
        requestQueue.add(stringRequest);

    }

//    public String parseDateToddMMyyyy(String time) {
//        String inputPattern = "yyyy-MM-dd HH:mm:ss";
//        String outputPattern = "dd-MMM-yyyy";
//        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
//        Date date = null;
//        String str = null;
//        try {
//            date = inputFormat.parse(time);
//            str = outputFormat.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }



    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {



        ArrayList<OrderModel> lstOder;
        Context context;

        public OrderAdapter(ArrayList<OrderModel> lstOder, Context context) {
            this.lstOder = lstOder;
            this.context = context;
        }


        @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.row_order, parent, false);

            return new OrderHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
            OrderModel model = lstOder.get(position);
            holder.brandOrder.setText(model.getBrand());
            holder.modelOrder.setText(model.getModel());
            holder.orderGb.setText(model.getGb());
            holder.orderAmount.setText(model.getSaleAmount());
            holder.orderdate.setText(model.getDate());

        }


        @Override
        public int getItemCount() {
            return lstOder.size();
        }

        public class OrderHolder extends RecyclerView.ViewHolder {

            TextView brandOrder, modelOrder, orderGb, orderAmount, orderdate;

            public OrderHolder(@NonNull View itemView) {
                super(itemView);
                orderAmount = itemView.findViewById(R.id.orderAmount);
                brandOrder = itemView.findViewById(R.id.brandOrder);
                modelOrder = itemView.findViewById(R.id.modelOrder);
                orderGb = itemView.findViewById(R.id.orderGb);
                orderdate = itemView.findViewById(R.id.orderdate);
            }
        }


    }


}

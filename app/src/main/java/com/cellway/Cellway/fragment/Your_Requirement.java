package com.cellway.Cellway.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Your_Requirement extends Fragment {

  View view;
  EditText edt_req_brand,edt_req_model;
  Button btn_reqSubmit;
  ProgressBar progressbarRequire;
  String userdId;
    public Your_Requirement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_your__requirement, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Your Requirement");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Cellways", Context.MODE_PRIVATE);
        userdId = sharedPreferences.getString("userid", "");

        Log.d("oeqww",userdId);
        edt_req_brand = view.findViewById(R.id.edt_req_brand);
        edt_req_model = view.findViewById(R.id.edt_req_model);
        btn_reqSubmit = view.findViewById(R.id.btn_reqSubmit);
        progressbarRequire = view.findViewById(R.id.progressbarRequire);

        btn_reqSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_req_brand.getText().toString().equals("")){
                    edt_req_brand.setError("Please enter Brand");
                }
                else if(edt_req_model.getText().toString().equals("")){
                    edt_req_model.setError("Please enter Model");
                }
                else{
                    hitReqApi();
                }

            }
        });

   return view;
    }


    private void hitReqApi() {

        progressbarRequire.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest request = new StringRequest(Request.Method.POST, Api.repairGadget, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressbarRequire.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("code").equals("200")){
                            startActivity(new Intent(getActivity(),Home.class));
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressbarRequire.setVisibility(View.GONE);
                }
            }){
                protected Map<String,String> getParams(){
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("key",Api.key);
                    hashMap.put("userid",userdId);
                    hashMap.put("brand",edt_req_brand.getText().toString());
                    hashMap.put("model",edt_req_model.getText().toString());
                    hashMap.put("type","Requirement");
                    return hashMap;
                }

            };
            requestQueue.add(request);


    }

}

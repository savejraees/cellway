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
public class RepairGadget extends Fragment {

    View view;
    ProgressBar progressbarRepair;
EditText RepairBrand,RepairModel,RepairColor,RepairDescription;
Button repairMobile;
String userdId;
    public RepairGadget() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_repair_gadget, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Repair Your Gadget");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Cellways", Context.MODE_PRIVATE);
        userdId = sharedPreferences.getString("userid", "");

        Log.d("oegdqww",userdId);

        RepairBrand = view.findViewById(R.id.RepairBrand);
        RepairModel = view.findViewById(R.id.RepairModel);
        RepairColor = view.findViewById(R.id.RepairColor);
        RepairDescription = view.findViewById(R.id.RepairDescription);
        progressbarRepair = view.findViewById(R.id.progressbarRepair);
        repairMobile = view.findViewById(R.id.repairMobile);

        repairMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepairBrand.getText().toString().equals("")){
                    RepairBrand.setError("Please enter Brand");
                }
                else if(RepairModel.getText().toString().equals("")){
                    RepairModel.setError("Please enter Model");
                }else if(RepairColor.getText().toString().equals("")){
                    RepairColor.setError("Please enter color");
                }
                else if(RepairDescription.getText().toString().equals("")){
                    RepairDescription.setError("Please enter Description");
                }
                else{
                    hitRepairApi();
                }

            }
        });

        return view;
    }

    private void hitRepairApi() {
        progressbarRepair.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.repairGadget, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbarRepair.setVisibility(View.GONE);

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
                progressbarRepair.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }){
              protected Map<String,String> getParams(){
                  HashMap<String,String> hashMap = new HashMap<>();
                  hashMap.put("key",Api.key);
                  hashMap.put("userid",userdId);
                  hashMap.put("brand",RepairBrand.getText().toString());
                  hashMap.put("model",RepairModel.getText().toString());
                  hashMap.put("color",RepairColor.getText().toString());
                  hashMap.put("description",RepairDescription.getText().toString());
                  hashMap.put("type","Repair");
                  return hashMap;
              }

        };
        requestQueue.add(request);
    }

}

package com.cellway.Cellway.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Contact_us extends Fragment {

      View view;
      TextView email,contact,address;
      ProgressBar progressbarContact;
    public Contact_us() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((Home) getActivity()).getSupportActionBar().setTitle("Contact Us");
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        email = view.findViewById(R.id.email);
        contact = view.findViewById(R.id.contact);
        address = view.findViewById(R.id.address);
        progressbarContact = view.findViewById(R.id.progressbarContact);

        hitContactApi();
    return view;
    }

    private void hitContactApi() {
        progressbarContact.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.contactUs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbarContact.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    email.setText(jsonObject.getString("email"));
                    contact.setText(jsonObject.getString("mobile"));
                    address.setText(jsonObject.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarContact.setVisibility(View.GONE);
            }
        }){
            protected Map<String,String> getParams(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("key",Api.key);
                return hashMap;
            }

        };
        requestQueue.add(request);
    }

}

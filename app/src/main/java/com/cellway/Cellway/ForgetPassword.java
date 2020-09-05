package com.cellway.Cellway;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.Services.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {

    EditText edt_mbl;
    Button contiue;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        edt_mbl = findViewById(R.id.edt_mbl);
        contiue = findViewById(R.id.contiue);


        contiue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile = edt_mbl.getText().toString();
                forgotOtp();

              //  startActivity(new Intent(ForgetPassword.this,NewPassword.class));
            }
        });

    }

    private void forgotOtp() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(ForgetPassword.this);
        StringRequest request = new StringRequest(Request.Method.POST, Api.ForgotPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    if(code.equals("403")){

                        Intent intent = new Intent(getApplicationContext(), NewPassword.class);
                        intent.putExtra("mobile", mobile);
                        startActivity(intent);
                        finish();
                       // hitUrlForOtp();
                    }else{

                        Toast.makeText(ForgetPassword.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            protected Map<String, String> getParams(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("key",Api.key);
                hashMap.put("mobile",mobile);
                hashMap.put("status","0");
                Log.d("check", String.valueOf(hashMap));
                return hashMap;
            }


        };

        requestQueue.add(request);

    }





}

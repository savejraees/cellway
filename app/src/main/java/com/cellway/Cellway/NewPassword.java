package com.cellway.Cellway;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import java.util.Random;

public class NewPassword extends AppCompatActivity {

    EditText edt_frgtpwd_otp,edt_newPassword,edt_Cnfm_Pwd;
    Button btnSbmt;

    String msgfrgt;
    String otpfrgt;
    String mobile;
    String password;
    String Confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        edt_frgtpwd_otp = findViewById(R.id.edt_frgtpwd_otp);
        edt_newPassword = findViewById(R.id.edt_newPassword);
        edt_Cnfm_Pwd = findViewById(R.id.edt_Cnfm_Pwd);
        btnSbmt = findViewById(R.id.btnSbmt);

        mobile = getIntent().getStringExtra("mobile");
        Log.d("dsare",mobile);
        hitUrlForOtp();

        btnSbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         password = edt_newPassword.getText().toString();
         Confirmpassword = edt_Cnfm_Pwd.getText().toString();

   if(password.equals("")){
       edt_newPassword.setError("Please Enter Password");

   }
   else if(!password.equals(Confirmpassword)){
       edt_newPassword.setError("Confirm Password should match as Password");
   }
   else if(edt_frgtpwd_otp.getText().toString().equals(otpfrgt)){
       forgotOtp();

   }
   else {
       edt_frgtpwd_otp.setError("Please enter valid otp");
   }

              // startActivity(new Intent(NewPassword.this,Login.class));
            }
        });
    }

    private void forgotOtp() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Api.ForgotPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    if(code.equals("200")){
                        Toast.makeText(NewPassword.this, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                        // hitUrlForOtp();
                    }else{

                        Toast.makeText(NewPassword.this, ""+msg, Toast.LENGTH_SHORT).show();
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
                hashMap.put("status","1");
                hashMap.put("password",password);
                Log.d("check", String.valueOf(hashMap));
                return hashMap;
            }


        };

        requestQueue.add(request);

    }
    private void hitUrlForOtp(){

        otpfrgt = String.valueOf(randomOtp());
        msgfrgt = "Your verification code is "+otpfrgt;
        Log.d("msg",msgfrgt);
        Log.d("ekmulakataho",otpfrgt);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.SENDOTP, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("chekcldsohinfg", response);


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("type").equals("success")){


                        Toast.makeText(NewPassword.this, "OTP sent your mobile number", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NewPassword.this, "Something Wrong With Otp.", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("authkey","290873ACnsgu9J5d5fd88f");
                hashMap.put("mobiles",mobile);
                hashMap.put("message",msgfrgt);
                hashMap.put("sender","MSGOTP");
                hashMap.put("route","106");
                hashMap.put("country","91");

                Log.d("checkpsdarams",hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(NewPassword.this);
        requestQueue.add(stringRequest);

    }

    private int randomOtp(){
        Random rnd = new Random();
        int n = 1000 + rnd.nextInt(9000);

        return n;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

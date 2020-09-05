package com.cellway.Cellway;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.util.NetworkChecker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button buttLogin;
    TextView goToRegister, gotToForgt;
    EditText edt_mobile, edt_password;
    String userId, name, email, mobile,image;
    NetworkChecker networkChecker = new NetworkChecker(this);
    ProgressBar progressbar;

    public static final String CHANNEL_ID = "MyNotifications";
    public static final String CHANNEL_NAME = "MyNotifications";


    String token;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("Cellways",MODE_PRIVATE);

        if(!isNetworkAvailable(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }


        buttLogin = findViewById(R.id.buttLogin);
        goToRegister = findViewById(R.id.goToRegister);
        gotToForgt = findViewById(R.id.gotToForgt);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_password = findViewById(R.id.edt_password);
        progressbar =  findViewById(R.id.progressbar);



        buttLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isNetworkAvailable(getApplicationContext())){
                    Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }

               else if (edt_mobile.getText().toString().isEmpty()) {
                    edt_mobile.setError("Please enter the Mobile No.");
                }
                else if (edt_mobile.getText().toString().length()!=10) {
                    edt_mobile.setError("Please enter 10 digit Mobile No.");
                } else if (edt_password.getText().toString().isEmpty()) {
                    edt_password.setError("Please enter the Password");
                } else if (!networkChecker.isOnLine()) {
                    networkChecker.show();
                } else {
                    loginApi();
                }


            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        gotToForgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgetPassword.class));
            }
        });


        //////////////////////////////////////////// notification ////////////////////////////////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Tag", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        Log.d("tokeasn", token);
                        sharedPreferences.edit().putString("token",token).apply();

                        // Log and toast
                        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("tag", msg);
//                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });



    }

    private void loginApi() {

      //  startActivity(new Intent(Login.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));


       progressbar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Api.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("serverresponse",response);
               progressbar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");

                    if (code.equals("200")) {
                        userId = jsonObject.getString("userid");
                        name = jsonObject.getString("name");
                        email = jsonObject.getString("email");
                        mobile = jsonObject.getString("mobile");
                        image = jsonObject.getString("image");
                        Log.d("ajklaasd",userId +"  "+ code);

                        SharedPreferences sharedPreferences = getSharedPreferences("Cellways", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("userid", userId).apply();
                        sharedPreferences.edit().putString("name",name).apply();
                        sharedPreferences.edit().putString("emal",email).apply();
                        sharedPreferences.edit().putString("mbl",mobile).apply();
                        sharedPreferences.edit().putString("image",image).apply();

                        Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                   else {
                        Log.d("ajklaasd", code);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        edt_mobile.setText("");
                        edt_password.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
        progressbar.setVisibility(View.GONE);

            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("mobile", edt_mobile.getText().toString());
                hashMap.put("password", edt_password.getText().toString());
                hashMap.put("token", token);

                return hashMap;
            }


        };

        requestQueue.add(request);
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}

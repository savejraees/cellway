package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.retrofitModel.PasswordModel.PasswordNewModel;
import com.cellway.Cellway.retrofitModel.PasswordModel.PasswordNewModel;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordNewActivity extends BaseActivity {

    String mobile;
    TextInputEditText edtOtpForgetPass,edtPasswordForget,edt_cnfmPasswordForget;
    Button btnSubmitForget;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_new);
        edtOtpForgetPass = findViewById(R.id.edtOtpForgetPass);
        edtPasswordForget = findViewById(R.id.edtPasswordForget);
        edt_cnfmPasswordForget = findViewById(R.id.edt_cnfmPasswordForget);
        btnSubmitForget = findViewById(R.id.btnSubmitForget);
        mobile = getIntent().getStringExtra("mobile");

        btnSubmitForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtOtpForgetPass.getText().toString().isEmpty()){
                    edtOtpForgetPass.setError("Can't be blank");
                    edtOtpForgetPass.requestFocus();
                } 
                else if(edtPasswordForget.getText().toString().isEmpty()){
                    edtPasswordForget.setError("Can't be blank");
                    edtPasswordForget.requestFocus();
                }
                else if(edt_cnfmPasswordForget.getText().toString().isEmpty()){
                    edt_cnfmPasswordForget.setError("Can't be blank");
                    edt_cnfmPasswordForget.requestFocus();
                }else if (!(edt_cnfmPasswordForget.getText().toString().equals(edtPasswordForget.getText().toString()))) {
                    edt_cnfmPasswordForget.setError("Confirm Password should same as Password");
                    edt_cnfmPasswordForget.requestFocus();
                }else{
                    hitApi();
                }
            }
        });
    }

    private void hitApi() {
        showProgress(ForgetPasswordNewActivity.this);
        Call<PasswordNewModel> call = api.newPassword(Api.key,mobile,edtOtpForgetPass.getText().toString(),
                edtPasswordForget.getText().toString(), edt_cnfmPasswordForget.getText().toString());
        call.enqueue(new Callback<PasswordNewModel>() {
            @Override
            public void onResponse(Call<PasswordNewModel> call, Response<PasswordNewModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    PasswordNewModel model = response.body();
                    if(model.getCode().equals("200")){
                        Toast.makeText(ForgetPasswordNewActivity.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPasswordNewActivity.this, LoginActivityNew.class));

                    }

                    else{
                        Toast.makeText(ForgetPasswordNewActivity.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<PasswordNewModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(ForgetPasswordNewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
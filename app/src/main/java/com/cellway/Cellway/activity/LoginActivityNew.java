package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.LoginStatusModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.OtpGetModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.VerifyOtpModel;
import com.cellway.Cellway.retrofitModel.PasswordModel.ForgetPasswordModel;
import com.cellway.Cellway.util.SessonManager;
import com.google.android.material.textfield.TextInputEditText;
import com.poovam.pinedittextfield.LinePinField;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityNew extends BaseActivity {

    Button btnRegLog,btnOtpVerify,btnSubmitForgetPass;
    ImageView imgLogin;
    EditText edtMobileLogin,edt_password_login;
    Dialog otpDialog,forgetDialog;
    LinePinField edtOtp;
    TextView txtResendOtp,txtForgetPass;
    TextInputEditText edtMobForget;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        sessonManager = new SessonManager(LoginActivityNew.this);
        btnRegLog = findViewById(R.id.btnRegLog);
        imgLogin = findViewById(R.id.imgLogin);
        edtMobileLogin = findViewById(R.id.edtMobileLogin);
        edt_password_login = findViewById(R.id.edt_password_login);
        txtForgetPass = findViewById(R.id.txtForgetPass);
        
        otpDialog = new Dialog(LoginActivityNew.this);
        otpDialog.setContentView(R.layout.dialog_otp);
        txtResendOtp = otpDialog.findViewById(R.id.txtResendOtp);
        SpannableString content1 = new SpannableString("Resend OTP.");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        btnOtpVerify = otpDialog.findViewById(R.id.btnOtpVerify);
        edtOtp = otpDialog.findViewById(R.id.edtOtp);

        forgetDialog = new Dialog(LoginActivityNew.this);
        forgetDialog.setContentView(R.layout.dialog_forget_password);
        edtMobForget = forgetDialog.findViewById(R.id.edtMobForget);
        btnSubmitForgetPass = forgetDialog.findViewById(R.id.btnSubmitForgetPass);
        
       

        btnRegLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivityNew.this,RegisterActivityNew.class));
                finish();
            }
        });
        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               forgetDialog.show();
            }
        });
        btnSubmitForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(edtMobForget.getText().toString().isEmpty()){
                   edtMobForget.setError("Please Enter Mobile no.");
                   edtMobForget.requestFocus();
               }else {
                   hitApiForgetPassword();
               }
            }
        });
        imgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtMobileLogin.getText().toString().isEmpty()){
                    edtMobileLogin.setError("Can't be blank");
                    edtMobileLogin.requestFocus();
                }
                else if(edtMobileLogin.getText().toString().length()!=10){
                    edtMobileLogin.setError("Mobile no. should be 10 digit");
                    edtMobileLogin.requestFocus();
                }
               else if(edt_password_login.getText().toString().isEmpty()){
                    edt_password_login.setError("Can't be blank");
                    edt_password_login.requestFocus();
                }else{
                   hitApi();
                }

            }
        });

        txtResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitgetOtp();
            }
        });
        btnOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtOtp.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivityNew.this, "Please Enter Otp", Toast.LENGTH_SHORT).show();
                }else {
                    hitOtpVerify();
                }

            }
        });
    }

    private void hitApiForgetPassword() {
        showProgress(LoginActivityNew.this);
        Call<ForgetPasswordModel> call = api.forgetPassword(Api.key,edtMobForget.getText().toString());
        call.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(Call<ForgetPasswordModel> call, Response<ForgetPasswordModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    ForgetPasswordModel model = response.body();
                    if(model.getCode().equals("403")){
                        Toast.makeText(LoginActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivityNew.this, ForgetPasswordNewActivity.class)
                        .putExtra("mobile",model.getMobile()));

                    }

                    else{
                        Toast.makeText(LoginActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(LoginActivityNew.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitApi() {
        showProgress(LoginActivityNew.this);
        Call<LoginStatusModel> call = api.postLogin(Api.key,edtMobileLogin.getText().toString(),edt_password_login.getText().toString(),"grdhtetrtrtrtrr6565656565cxcx");
        call.enqueue(new Callback<LoginStatusModel>() {
            @Override
            public void onResponse(Call<LoginStatusModel> call, Response<LoginStatusModel> response) {

                if (response.isSuccessful()) {
                   hideProgress();
                   LoginStatusModel model = response.body();
                   if(model.getCode().equals("200")){
                       Toast.makeText(LoginActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                       sessonManager.setToken(""+model.getUserid());
                       startActivity(new Intent(LoginActivityNew.this, Home.class)
                       .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                   }
                  else if(model.getCode().equals("403")){
                       otpDialog.show();
                   }
                   else{
                       Toast.makeText(LoginActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                   }

                }
            }

            @Override
            public void onFailure(Call<LoginStatusModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(LoginActivityNew.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void hitOtpVerify() {
        showProgress(LoginActivityNew.this);
        Call<VerifyOtpModel> call = api.postVerifyOtp(Api.key,edtMobileLogin.getText().toString(),edtOtp.getText().toString());
        call.enqueue(new Callback<VerifyOtpModel>() {
            @Override
            public void onResponse(Call<VerifyOtpModel> call, Response<VerifyOtpModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    VerifyOtpModel model = response.body();
                    if(model.getCode().equals("200")){
                        Toast.makeText(LoginActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                        sessonManager.setToken(""+model.getUserid());
                        startActivity(new Intent(LoginActivityNew.this, Home.class));
                        finishAffinity();
                    }else{
                        Toast.makeText(LoginActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<VerifyOtpModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(LoginActivityNew.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitgetOtp() {
        showProgress(LoginActivityNew.this);
        Call<OtpGetModel> call = api.postGetOtp(Api.key,edtMobileLogin.getText().toString());
        call.enqueue(new Callback<OtpGetModel>() {
            @Override
            public void onResponse(Call<OtpGetModel> call, Response<OtpGetModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    OtpGetModel model = response.body();
                    if(model.getCode().equals("203")){
                        Toast.makeText(LoginActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(LoginActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OtpGetModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(LoginActivityNew.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Register;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.VerifyOtpModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.OtpGetModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.OtpGetModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.RegisterStatusModel;
import com.cellway.Cellway.retrofitModel.LoginRegisterModel.VerifyOtpModel;
import com.cellway.Cellway.util.SessonManager;
import com.google.android.material.textfield.TextInputEditText;
import com.poovam.pinedittextfield.LinePinField;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivityNew extends BaseActivity {
    TextView loginRegi;
    TextInputEditText edtNameReg, edtMobReg, edtEmailReg, edtPasswordReg, edt_cnfmPasswordReg;
    Button btnSubmitReg,btnOtpVerify;

    Dialog otpDialog;
    LinePinField edtOtp;
    TextView txtResendOtp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        loginRegi = findViewById(R.id.loginRegi);
        edtNameReg = findViewById(R.id.edtNameReg);
        edtMobReg = findViewById(R.id.edtMobReg);
        edtEmailReg = findViewById(R.id.edtEmailReg);
        edtPasswordReg = findViewById(R.id.edtPasswordReg);
        edt_cnfmPasswordReg = findViewById(R.id.edt_cnfmPasswordReg);
        btnSubmitReg = findViewById(R.id.btnSubmitReg);
        sessonManager = new SessonManager(RegisterActivityNew.this);
        SpannableString content = new SpannableString("Login");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        loginRegi.setText(content);
        otpDialog = new Dialog(RegisterActivityNew.this);
        otpDialog.setContentView(R.layout.dialog_otp);
        txtResendOtp = otpDialog.findViewById(R.id.txtResendOtp);
        SpannableString content1 = new SpannableString("Resend OTP.");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        btnOtpVerify = otpDialog.findViewById(R.id.btnOtpVerify);
        edtOtp = otpDialog.findViewById(R.id.edtOtp);

        loginRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivityNew.this, LoginActivityNew.class));
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
                    Toast.makeText(RegisterActivityNew.this, "Please Enter Otp", Toast.LENGTH_SHORT).show();
                }else {
                    hitOtpVerify();
                }

            }
        });

        btnSubmitReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNameReg.getText().toString().isEmpty()) {
                    edtNameReg.setError("Can't be blank");
                    edtNameReg.requestFocus();
                } else if (edtMobReg.getText().toString().isEmpty()) {
                    edtMobReg.setError("Can't be blank");
                    edtMobReg.requestFocus();
                } else if (edtMobReg.getText().toString().length() != 10) {
                    edtMobReg.setError("Should be 10 digit");
                    edtMobReg.requestFocus();
                } else if (edtEmailReg.getText().toString().isEmpty()) {
                    edtEmailReg.setError("Can't be blank");
                    edtEmailReg.requestFocus();
                } else if (edtPasswordReg.getText().toString().isEmpty()) {
                    edtPasswordReg.setError("Can't be blank");
                    edtPasswordReg.requestFocus();
                } else if (edt_cnfmPasswordReg.getText().toString().isEmpty()) {
                    edt_cnfmPasswordReg.setError("Can't be blank");
                    edt_cnfmPasswordReg.requestFocus();
                } else if (!(edt_cnfmPasswordReg.getText().toString().equals(edtPasswordReg.getText().toString()))) {
                    edt_cnfmPasswordReg.setError("Confirm Password should same as Password");
                    edt_cnfmPasswordReg.requestFocus();
                } else {
                    hitRegisterApi();
                }
            }
        });

    }

    private void hitOtpVerify() {
        showProgress(RegisterActivityNew.this);
        Call<VerifyOtpModel> call = api.postVerifyOtp(Api.key,edtMobReg.getText().toString(),edtOtp.getText().toString());
        call.enqueue(new Callback<VerifyOtpModel>() {
            @Override
            public void onResponse(Call<VerifyOtpModel> call, Response<VerifyOtpModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    VerifyOtpModel model = response.body();
                    if(model.getCode().equals("200")){
                        Toast.makeText(RegisterActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                        sessonManager.setToken(""+model.getUserid());
                        startActivity(new Intent(RegisterActivityNew.this, Home.class));
                        finishAffinity();
                    }else{
                        Toast.makeText(RegisterActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<VerifyOtpModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(RegisterActivityNew.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitRegisterApi() {
        showProgress(RegisterActivityNew.this);
        Call<RegisterStatusModel> call = api.postRegister(Api.key, edtNameReg.getText().toString(), edtMobReg.getText().toString(),
                edtEmailReg.getText().toString(), edtPasswordReg.getText().toString(), "grdhtetrtrtrtrr6565656565cxcx");
        call.enqueue(new Callback<RegisterStatusModel>() {
            @Override
            public void onResponse(Call<RegisterStatusModel> call, Response<RegisterStatusModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    RegisterStatusModel model = response.body();
                    if (model.getCode().equals("200")) {
                        otpDialog.show();
//                        Toast.makeText(RegisterActivityNew.this, "" + model.getMsg(), Toast.LENGTH_SHORT).show();
//                        sessonManager.setToken("" + model.getUserid());
//                        startActivity(new Intent(RegisterActivityNew.this, AddressActivity.class));
                    } else {
                        Toast.makeText(RegisterActivityNew.this, "" + model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<RegisterStatusModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(RegisterActivityNew.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitgetOtp() {
        showProgress(RegisterActivityNew.this);
        Call<OtpGetModel> call = api.postGetOtp(Api.key,edtMobReg.getText().toString());
        call.enqueue(new Callback<OtpGetModel>() {
            @Override
            public void onResponse(Call<OtpGetModel> call, Response<OtpGetModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    OtpGetModel model = response.body();
                    if(model.getCode().equals("203")){
                        Toast.makeText(RegisterActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(RegisterActivityNew.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OtpGetModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(RegisterActivityNew.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.cellway.Cellway.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.util.SessonManager;
import com.cellway.Cellway.util.Url;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseActivity extends AppCompatActivity {

    ////////////////////////////////////// Created By Savej saifi ///////////////////////////////////////////////

    private ProgressDialog mProgressDialog;
    public Dialog mDialog;
    SessonManager sessonManager;
   // String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sessonManager = new SessonManager(BaseActivity.this);
//        token = sessonManager.getToken();

    }



    public void hideProgress() {
        while (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();

        }
        /*if (mDialog != null && mDialog.isShowing()) {
          //  mDialog = null;
        }*/
    }

    public void showProgress(Context mContext) {
        if(mContext!=null){
            mDialog= new Dialog(mContext);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.custom_progress_layout);
            mDialog.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    protected void validation(EditText editText, String string){
        editText.setError(string);
        editText.requestFocus();
    }

    protected void Intent(Context context,Class abcd){
        Intent intent = new Intent(context,abcd);
        startActivity(intent);
    }

    protected void IntentWithPutExtra(Context context,Class abcd,String key,String value){
        Intent intent = new Intent(context,abcd);
        intent.putExtra(key,value);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Url.BASE_URL)
            .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
            ))
                    .build())
            .build();
    IApiServices api = retrofit.create(IApiServices.class);


}


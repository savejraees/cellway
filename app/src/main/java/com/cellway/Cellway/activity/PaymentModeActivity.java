package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.retrofitModel.PaymentFormModel.CheckPaymentModel;
import com.cellway.Cellway.util.SessonManager;
import com.cellway.Cellway.util.Url;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentModeActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    private static final String TAG = PaymentModeActivity.class.getSimpleName();
    String orderId;
    int amount;
    SessonManager sessonManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);

        sessonManager=new SessonManager(PaymentModeActivity.this);
        if(getIntent().hasExtra("id")){
            orderId = getIntent().getStringExtra("id");
            amount = getIntent().getIntExtra("amount",0);
        }

        Log.d("dsaQWSAA", amount + " " + orderId );
        Checkout.preload(getApplicationContext());

        startPayment();
    }

    public void startPayment() {
        /**   * Instantiate Checkout   */
        Checkout checkout = new Checkout();
        /**   * Set your logo here   */
        checkout.setImage(R.drawable.app_logo);
        /**   * Reference to current activity   */
        final Activity activity = this;
        /**   * Pass your payment options to the Razorpay Checkout as a JSONObject   */
        try {
            JSONObject options = new JSONObject();
            /**      * Merchant Name      * eg: ACME Corp || HasGeek etc.      */

            options.put("name", "Cellway");
            /**      * Description can be anything      * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.      *     Invoice Payment      *     etc.      */
            options.put("description", "");
          //  options.put("order_id",""+orderId);
            options.put("currency", "INR");      /**      * Amount is always passed in currency subunits      * Eg: "500" = INR 5.00      */
            //   options.put("amount", TotalPrice);
            options.put("amount", ""+amount*100);
            JSONObject preFill = new JSONObject();

            preFill.put("email", "");
            preFill.put("contact", "");
            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch (Exception e) {
            //   Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            String paymentId = paymentData.getPaymentId();
            String signature = paymentData.getSignature();
            String orderId = paymentData.getOrderId();

            PaymentSuccessAPI();
            Log.d("dasdwqpo", s + "  " + paymentId + " " + signature + " " + orderId);

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    private void PaymentSuccessAPI() {
        sessonManager.showProgress(PaymentModeActivity.this);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                        .build())
                .build();
        IApiServices api = retrofit.create(IApiServices.class);
       
            Call<CheckPaymentModel> call = api.postCheckPayment(Api.key, sessonManager.getToken(), orderId);
        

        call.enqueue(new Callback<CheckPaymentModel>() {
            @Override
            public void onResponse(Call<CheckPaymentModel> call, Response<CheckPaymentModel> response) {

                if (response.isSuccessful()) {
                    sessonManager.hideProgress();
                    CheckPaymentModel AddressStatusModel = response.body();
                    if (AddressStatusModel.getCode().equals("200")) {
                        sessonManager.setQty("");
                        startActivity(new Intent(PaymentModeActivity.this,PaymentSuccessActivity.class));
                        finishAffinity();

                    } else {
                        Toast.makeText(PaymentModeActivity.this, "" + AddressStatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckPaymentModel> call, Throwable t) {
                sessonManager.hideProgress();
                Toast.makeText(PaymentModeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(), PaymentFailActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            Log.d("lkts", String.valueOf(s));
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);

            Log.d("fwqfwfa",e.getMessage());
        }
    }



}
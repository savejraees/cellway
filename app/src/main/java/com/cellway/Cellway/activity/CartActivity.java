package com.cellway.Cellway.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.CartAdapter;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailDatumModel;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailStatusModel;
import com.cellway.Cellway.util.SessonManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;


public class CartActivity extends BaseActivity {

    RecyclerView rvCartItem;
    TextView txtPlaceOrder;
    static TextView finalAmountCart;
    ArrayList<CartDetailDatumModel> cartList = new ArrayList<>();

    static public CardView cardMissinig;

    String intent="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        sessonManager = new SessonManager(CartActivity.this);
        rvCartItem = findViewById(R.id.rvCartItem);
        txtPlaceOrder = findViewById(R.id.txtPlaceOrder);
        cardMissinig = findViewById(R.id.cardMissinig);
        finalAmountCart = findViewById(R.id.finalAmountCart);
        if(getIntent().hasExtra("Intent")){
            intent = getIntent().getStringExtra("Intent");
        }

        hitCartDetailApi();
        txtPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardMissinig.getVisibility()==VISIBLE){
                    Toast.makeText(CartActivity.this, "Please Add Some Items", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(CartActivity.this, OrderSummaryActivity.class));
                }

            }
        });

    }

    public static  void finalAmount(String value){
        try{
            finalAmountCart.setText("₹ "+value);
        }
        catch (Exception ex){
            Log.d("Exception","Exception of type"+ex.getMessage());
        }
    }

    private void hitCartDetailApi() {
        showProgress(CartActivity.this);
        Call<CartDetailStatusModel> call = api.getCartDetail(Api.key, sessonManager.getToken());
        call.enqueue(new Callback<CartDetailStatusModel>() {
            @Override
            public void onResponse(Call<CartDetailStatusModel> call, Response<CartDetailStatusModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    CartDetailStatusModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                        cartList = StatusModel.getData();
                        Log.d("sajjjkdasjk",""+cartList.size());
                        setRVCart();
                    } else {
                        Toast.makeText(CartActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartDetailStatusModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(CartActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("slkklsaksax","" + t.getMessage());
            }
        });
    }

    private void setRVCart() {

        rvCartItem.setLayoutManager(new GridLayoutManager(CartActivity.this, 1));
        rvCartItem.setAdapter(new CartAdapter(CartActivity.this, cartList));
    }

    @Override
    public void onBackPressed() {

        if(intent.equals("BookMob")){
            startActivity(new Intent(CartActivity.this,BookNewActivity.class)
            .putExtra("id",sessonManager.getProductIdBook()));
        }else {
            super.onBackPressed();
        }

    }
}
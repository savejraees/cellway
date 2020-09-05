package com.cellway.Cellway.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.activity.PaymentActivity;
import com.cellway.Cellway.adapter.OrderSummaryAdapter;
import com.cellway.Cellway.retrofitModel.AddOrderModel;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailDatumModel;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailStatusModel;
import com.cellway.Cellway.util.NetworkUtils;
import com.cellway.Cellway.util.SessonManager;
import com.cellway.Cellway.util.Url;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PickupFragment extends Fragment {

    ArrayList<CartDetailDatumModel> cartListDetail = new ArrayList<>();
    public static PickupFragment newInstance() {
        return new PickupFragment();
    }

    RecyclerView rv_orderSummary;
//    int[] img = {R.drawable.samsug_demo, R.drawable.samsug_demo, R.drawable.samsug_demo};
//    ArrayList<Integer> cartList = new ArrayList<>();
    ImageView loc_picup;
    View view;
    SessonManager sessonManager;
    TextView txtPickupAddress,txtContinueOrderPickup;
    static TextView txtPricePickup,txtFinalAmountPickup,txtBookingAmountPickup;
    static int pricePickup,bookAmount;
    static int finalAmont=0;

    int page;
    public void setQuery(int page) {
           this.page=page;
        if(page==1){
            if(cartListDetail.size()==0){
                hitCartDetailApi();
            }

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pickup, container, false);
        rv_orderSummary = view.findViewById(R.id.rv_orderSummaryPickup);
        loc_picup = view.findViewById(R.id.loc_picup);
        txtPickupAddress = view.findViewById(R.id.txtPickupAddress);
        txtPricePickup = view.findViewById(R.id.txtPricePickup);
        txtBookingAmountPickup = view.findViewById(R.id.txtBookingAmountPickup);
        txtContinueOrderPickup = view.findViewById(R.id.txtContinueOrderPickup);
        txtFinalAmountPickup = view.findViewById(R.id.txtFinalAmountPickup);
        sessonManager = new SessonManager(getActivity());
      //  setRVCart();
      //  Toast.makeText(getActivity(), "Pickup", Toast.LENGTH_SHORT).show();

        onClick();



        return view;
    }

    public static  void PriceBookPickup(int value){
        try{
            pricePickup = value;
            txtPricePickup.setText("₹ "+value);

            if(pricePickup>bookAmount){
                finalAmont = pricePickup -bookAmount;
                txtBookingAmountPickup.setText("- \u20B9 "+bookAmount);
            }else {
                finalAmont = value;
                txtBookingAmountPickup.setText("- \u20B9 "+0);
            }
            txtFinalAmountPickup.setText("₹ "+finalAmont);
//            txtAmountToPaidPayNow.setText("₹ "+value);
        }
        catch (Exception ex){
            Log.d("Exception","Exception of type"+ex.getMessage());
        }
    }
    private void onClick() {
        loc_picup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkUtils.isNetworkConnected(getActivity())){
                    String name = "Cellway";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("geo:0,0?q=28.632772,77.281131 (" + name + ")"));
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        txtContinueOrderPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAddOrderApi();

            }
        });
    }

    private void hitAddOrderApi() {
        finalAmont =finalAmont+bookAmount;
        sessonManager.showProgress(getActivity());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                        .build())
                .build();
        IApiServices api = retrofit.create(IApiServices.class);
        Call<AddOrderModel> call = api.postAddOrder(Api.key, sessonManager.getToken(), sessonManager.getQty(),
                    ""+finalAmont,txtPickupAddress.getText().toString(),"pickup",
                    "pickup",""+bookAmount,"0","0" );


        call.enqueue(new Callback<AddOrderModel>() {
            @Override
            public void onResponse(Call<AddOrderModel> call, Response<AddOrderModel> response) {

                if (response.isSuccessful()) {
                    sessonManager.hideProgress();
                    AddOrderModel AddressStatusModel = response.body();
                    if (AddressStatusModel.getCode().equals("200")) {
                        sessonManager.setQty("");
                        startActivity(new Intent(getActivity(), PaymentActivity.class));

                    } else {
                        Toast.makeText(getActivity(), "" + AddressStatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddOrderModel> call, Throwable t) {
                sessonManager.hideProgress();
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitCartDetailApi() {
        sessonManager.showProgress(getActivity());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                        .build())
                .build();
        IApiServices api = retrofit.create(IApiServices.class);
        Call<CartDetailStatusModel> call = api.getCartDetail(Api.key, sessonManager.getToken());
        Log.d("kjaskhjastt","pickup");
        call.enqueue(new Callback<CartDetailStatusModel>() {
            @Override
            public void onResponse(Call<CartDetailStatusModel> call, Response<CartDetailStatusModel> response) {

                if (response.isSuccessful()) {
                    sessonManager. hideProgress();
                    CartDetailStatusModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                        cartListDetail = StatusModel.getData();
                        txtPickupAddress.setText(StatusModel.getPickupAddress());
                        txtBookingAmountPickup.setText("- ₹ "+StatusModel.getCodBookingAmount());
                        bookAmount = StatusModel.getCodBookingAmount();
                        setRVCart();

                    } else {
                        Toast.makeText(getActivity(), "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartDetailStatusModel> call, Throwable t) {
                sessonManager.hideProgress();
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("slkklsaksax","" + t.getMessage());
            }
        });
    }


    private void setRVCart() {

        rv_orderSummary.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rv_orderSummary.setAdapter(new OrderSummaryAdapter(getActivity(), cartListDetail));
    }

}
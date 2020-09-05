package com.cellway.Cellway.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.activity.AddressActivity;
import com.cellway.Cellway.activity.MyOrderActivity;
import com.cellway.Cellway.activity.OrderSummaryActivity;
import com.cellway.Cellway.activity.PaymentActivity;
import com.cellway.Cellway.adapter.OrderSummaryAdapter;
import com.cellway.Cellway.retrofitModel.AddOrderModel;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailDatumModel;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailStatusModel;
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

public class DeliveryFragment extends Fragment {

    //    int[] img = {R.drawable.samsug_demo, R.drawable.samsug_demo, R.drawable.samsug_demo};
//    ArrayList<Integer> cartList = new ArrayList<>();
    RecyclerView rv_orderSummary;
    TextView txtNamedelivery, txtAddressDelivery, txtBookCODCharges,
            txtbookAmountCOD, txtContactOreder, txtDeliveryPay, txtDeliveryCOD, txtContinueOrderDelivery;
    Button btnCOD, btnPayNow, btnAddressChange;
    CardView cardCOD, cardPayNow;
    SessonManager sessonManager;
    static TextView txtPriceBookCOD, txtPricePayNow, txtAmountToPaidPayNow, txtAmountToPaidCOD;
    static int price, codCharge, bookAmount, deliveryAmount, finalAmountCOD = 0;
    static int paidAmount;
    LinearLayout linearCOD;

    ArrayList<CartDetailDatumModel> cartListDetail = new ArrayList<>();
    ArrayList<String> cartList = new ArrayList<>();

    View view;

    String paymentMode = "paynow";

    int page;

    public void setQuery(int page) {
        this.page = page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery, container, false);
        rv_orderSummary = view.findViewById(R.id.rv_orderSummary);
        txtContinueOrderDelivery = view.findViewById(R.id.txtContinueOrderDelivery);
        btnCOD = view.findViewById(R.id.btnCOD);
        btnPayNow = view.findViewById(R.id.btnPayNow);
        btnAddressChange = view.findViewById(R.id.btnAddressChange);
        cardPayNow = view.findViewById(R.id.cardPayNow);
        txtNamedelivery = view.findViewById(R.id.txtNamedelivery);
        txtAddressDelivery = view.findViewById(R.id.txtAddressDelivery);
        cardCOD = view.findViewById(R.id.cardCOD);
        txtPriceBookCOD = view.findViewById(R.id.txtPriceBookCOD);
        txtBookCODCharges = view.findViewById(R.id.txtBookCODCharges);
        txtbookAmountCOD = view.findViewById(R.id.txtbookAmountCOD);
        txtPricePayNow = view.findViewById(R.id.txtPricePayNow);
        txtAmountToPaidPayNow = view.findViewById(R.id.txtAmountToPaidPayNow);
        txtAmountToPaidCOD = view.findViewById(R.id.txtAmountToPaidCOD);
        txtContactOreder = view.findViewById(R.id.txtContactOreder);
        txtDeliveryPay = view.findViewById(R.id.txtDeliveryPay);
        txtDeliveryCOD = view.findViewById(R.id.txtDeliveryCOD);
        txtContinueOrderDelivery = view.findViewById(R.id.txtContinueOrderDelivery);
        linearCOD = view.findViewById(R.id.linearCOD);
        sessonManager = new SessonManager(getActivity());

        onClick();
        hitCartDetailApi();


        return view;
    }

    public static void PriceBookWithCOD(int value) {
        try {

            price = value;
            txtPricePayNow.setText("₹ " + value);
            paidAmount = deliveryAmount + value;
            txtAmountToPaidPayNow.setText("₹ " + paidAmount);

            txtPriceBookCOD.setText("₹ " + value);
            if (bookAmount < (codCharge + value)) {
                finalAmountCOD = deliveryAmount + codCharge + value - bookAmount;
            }
            txtAmountToPaidCOD.setText("₹ " + finalAmountCOD);
        } catch (Exception ex) {
            Log.d("Exception", "Exception of type" + ex.getMessage());
        }
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
        call.enqueue(new Callback<CartDetailStatusModel>() {
            @Override
            public void onResponse(Call<CartDetailStatusModel> call, Response<CartDetailStatusModel> response) {

                if (response.isSuccessful()) {
                    sessonManager.hideProgress();
                    CartDetailStatusModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                        cartListDetail = StatusModel.getData();
                        txtNamedelivery.setText(StatusModel.getName());
                        txtAddressDelivery.setText(StatusModel.getOfficeFloor() + ", "
                                + StatusModel.getLandmark() + ", " + StatusModel.getLocality() + ", "
                                + StatusModel.getCity() + ", " + StatusModel.getState() + " - " + StatusModel.getPincode());
                        // txtBookCODCharges.setText(StatusModel.);
                        txtbookAmountCOD.setText("- ₹ " + StatusModel.getCodBookingAmount());
                        txtBookCODCharges.setText("₹ " + StatusModel.getCodCharge());
                        codCharge = StatusModel.getCodCharge();
                        bookAmount = StatusModel.getCodBookingAmount();
                        txtContactOreder.setText(StatusModel.getMobile());
                        if (StatusModel.getDeliveryCharge() == 0) {
                            txtDeliveryPay.setText("Free");
                            txtDeliveryCOD.setText("Free");
                        } else {
                            txtDeliveryPay.setText("₹ " + StatusModel.getDeliveryCharge());
                            txtDeliveryCOD.setText("₹ " + StatusModel.getDeliveryCharge());
                            txtDeliveryCOD.setTextColor(Color.BLACK);
                            txtDeliveryPay.setTextColor(Color.BLACK);
                        }
                        deliveryAmount = StatusModel.getDeliveryCharge();

                        for (int i = 0; i < cartListDetail.size(); i++) {
                            cartList.add(cartListDetail.get(i).getCategory());
                        }

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
                Log.d("slkklsaksax", "" + t.getMessage());
            }
        });
    }

    private void onClick() {

        btnCOD.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                paymentMode = "cod";

                btnPayNow.setBackgroundResource(R.drawable.round_shape_left_white);
                btnCOD.setBackgroundResource(R.drawable.round_shape_right);
                cardPayNow.setVisibility(View.GONE);
                cardCOD.setVisibility(View.VISIBLE);
                btnCOD.setTextColor(Color.parseColor("#ffffff"));
                btnPayNow.setTextColor(Color.parseColor("#000000"));
            }
        });
        btnPayNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                paymentMode = "paynow";

                btnPayNow.setBackgroundResource(R.drawable.round_shape_left);
                btnCOD.setBackgroundResource(R.drawable.round_shape_right_white);
                cardCOD.setVisibility(View.GONE);
                cardPayNow.setVisibility(View.VISIBLE);
                btnPayNow.setTextColor(Color.parseColor("#ffffff"));
                btnCOD.setTextColor(Color.parseColor("#000000"));

            }
        });

        txtContinueOrderDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtAddressDelivery.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Add Your Address", Toast.LENGTH_SHORT).show();
                } else {

                    hitAddOrderApi();
                }

            }
        });

        btnAddressChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddressActivity.class));
                getActivity().finishAffinity();
            }
        });
    }

    private void hitAddOrderApi() {
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
        Call<AddOrderModel> call = null;
        if(paymentMode.equals("cod")){
             int final_amount = finalAmountCOD+bookAmount;
            call = api.postAddOrder(Api.key, sessonManager.getToken(), sessonManager.getQty(),
                    ""+final_amount,txtAddressDelivery.getText().toString(),paymentMode,
                    "deliery",""+bookAmount,""+codCharge,""+deliveryAmount );
        }else {
            call = api.postAddOrder(Api.key, sessonManager.getToken(), sessonManager.getQty(),
                    ""+paidAmount,txtAddressDelivery.getText().toString(),paymentMode,
                    "deliery","0","0",""+deliveryAmount );
        }

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

    private void setRVCart() {
        if (!cartList.contains("mobile")) {
            linearCOD.setVisibility(View.GONE);
        }

        rv_orderSummary.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rv_orderSummary.setAdapter(new OrderSummaryAdapter(getActivity(), cartListDetail));
    }

}
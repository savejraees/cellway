package com.cellway.Cellway.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.activity.AddressActivity;
import com.cellway.Cellway.activity.BookNewActivity;
import com.cellway.Cellway.activity.LoginActivityNew;
import com.cellway.Cellway.retrofitModel.CartModel.AddCartModel;
import com.cellway.Cellway.util.SessonManager;
import com.cellway.Cellway.util.Url;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BotomSheetDialogFragmnt extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    int backCoverPrice = 0, temperedPrice = 0, protectionPrice;
    String brand, model, prod_ID;
    SessonManager sessonManager;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.bottom_sheet_place_order, container, false);
        sessonManager = new SessonManager(getActivity());
        ImageView close = v.findViewById(R.id.closeBottom);
        TextView txtContinue = v.findViewById(R.id.txtContinue);
        TextView tv_brand_Back = v.findViewById(R.id.tv_brand_Back);
        TextView txtPriceBack = v.findViewById(R.id.txtPriceBack);
        TextView txtPriceTemper = v.findViewById(R.id.txtPriceTemper);
        final LinearLayout linearAdd = v.findViewById(R.id.linearAddPlace);
        final LinearLayout linearAddGlass = v.findViewById(R.id.linearAddGlass);
        final ImageView imgCheckCover = v.findViewById(R.id.imgCheckCover);
        final ImageView imgCheckGlass = v.findViewById(R.id.imgCheckGlass);
        final LinearLayout linearBack = v.findViewById(R.id.linearBack);
        final LinearLayout linearTempered = v.findViewById(R.id.linearTempered);

        tv_brand_Back.setText("Designed Back Cover for " + brand + " " + model);
        txtPriceBack.setText("₹ " + BookNewActivity.backCoverPrice);
        txtPriceTemper.setText("₹ " + BookNewActivity.temperedPrice);

        if (BookNewActivity.backCoverPrice == 0) {
            linearBack.setVisibility(View.GONE);
        }
        if (BookNewActivity.temperedPrice == 0) {
            linearTempered.setVisibility(View.GONE);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  mListener.onButtonClicked("Button 1 clicked");
                dismiss();
            }
        });
        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                hitAddCartBuyApi(view);

            }
        });
        linearAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCheckCover.setVisibility(View.VISIBLE);
                linearAdd.setVisibility(View.GONE);
                backCoverPrice = 1;
            }
        });

        imgCheckCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearAdd.setVisibility(View.VISIBLE);
                imgCheckCover.setVisibility(View.GONE);
                backCoverPrice = 0;
            }
        });
        linearAddGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCheckGlass.setVisibility(View.VISIBLE);
                linearAddGlass.setVisibility(View.GONE);
                temperedPrice = 1;
            }
        });

        imgCheckGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearAddGlass.setVisibility(View.VISIBLE);
                imgCheckGlass.setVisibility(View.GONE);
                temperedPrice = 0;
            }
        });

        return v;
    }

    private void hitAddCartBuyApi(final View view) {
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
        Call<AddCartModel> call = api.addCart(Api.key, prod_ID, "mobile", 1, sessonManager.getToken(), protectionPrice, backCoverPrice, temperedPrice);
        call.enqueue(new Callback<AddCartModel>() {
            @Override
            public void onResponse(Call<AddCartModel> call, Response<AddCartModel> response) {

                if (response.isSuccessful()) {
                    sessonManager.hideProgress();
                    AddCartModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                        Toast.makeText(view.getContext(), "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                        BookNewActivity.txtCartBook.setVisibility(View.VISIBLE);
                        sessonManager.setQty(StatusModel.getTotalqty());
                        BookNewActivity.txtCartBook.setText(sessonManager.getQty());
                        BookNewActivity.btnAddCart.setText("GO TO CART");
                        view.getContext().startActivity(new Intent(view.getContext(), AddressActivity.class));


                    } else {
                        Toast.makeText(getActivity(), "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddCartModel> call, Throwable t) {
                sessonManager.hideProgress();
                Toast.makeText(view.getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            backCoverPrice = getArguments().getInt("back",0);
//            temperedPrice = getArguments().getInt("temperd",0);
            protectionPrice = getArguments().getInt("protectionPrice", 0);
            brand = getArguments().getString("brand", "");
            model = getArguments().getString("model", "");
            prod_ID = getArguments().getString("prod_ID", "");
        }
    }
}
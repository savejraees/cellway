package com.cellway.Cellway.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cellway.Cellway.R;
import com.cellway.Cellway.activity.LoginActivityNew;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BotomSheetDialogFragmnt extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    int backCoverPrice,temperedPrice;
    String brand,model;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_place_order, container, false);
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

        tv_brand_Back.setText("Designed Back Cover for "+brand+" "+model);
        txtPriceBack.setText("₹ "+backCoverPrice);
        txtPriceTemper.setText("₹ "+temperedPrice);
        if(backCoverPrice==0){
            linearBack.setVisibility(View.GONE);
        }
        if(temperedPrice==0){
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
                startActivity(new Intent(getActivity(), LoginActivityNew.class));
              //  startActivity(new Intent(getActivity(), AddressActivity.class));

            }
        });
        linearAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCheckCover.setVisibility(View.VISIBLE);
                linearAdd.setVisibility(View.GONE);
            }
        });

        imgCheckCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearAdd.setVisibility(View.VISIBLE);
                imgCheckCover.setVisibility(View.GONE);
            }
        });
        linearAddGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCheckGlass.setVisibility(View.VISIBLE);
                linearAddGlass.setVisibility(View.GONE);
            }
        });

        imgCheckGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearAddGlass.setVisibility(View.VISIBLE);
                imgCheckGlass.setVisibility(View.GONE);
            }
        });

        return v;
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
            backCoverPrice = getArguments().getInt("back",0);
            temperedPrice = getArguments().getInt("temperd",0);
            brand = getArguments().getString("brand","");
            model = getArguments().getString("model","");
        }
    }
}
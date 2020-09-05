package com.cellway.Cellway.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQs extends Fragment {

    LinearLayout txtTermandCondition;
    CheckBox chkterm;
    View view;
    public FAQs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_faqs, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("FAQs");
        txtTermandCondition = view.findViewById(R.id.txtTermandCondition);
        chkterm= view.findViewById(R.id.chkterm);
        chkterm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkterm.isChecked()){
                    txtTermandCondition.setVisibility(View.VISIBLE);
                }
                else {
                    txtTermandCondition.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }

}

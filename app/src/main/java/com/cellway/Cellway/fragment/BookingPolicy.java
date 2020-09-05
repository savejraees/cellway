package com.cellway.Cellway.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;


public class BookingPolicy extends Fragment {


    public BookingPolicy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((Home) getActivity()).getSupportActionBar().setTitle("Booking Policy");
        return inflater.inflate(R.layout.fragment_booking_policy, container, false);
    }

}

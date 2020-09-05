package com.cellway.Cellway.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class More extends Fragment {

    Button faq,legal,bookingPolicy,shippingPolicy,bankDetails;
    View view;
    public More() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("CELLWAY");

         faq = view.findViewById(R.id.faq);
        legal = view.findViewById(R.id.legal);
        bookingPolicy = view.findViewById(R.id.bookingPolicy);
    //    shippingPolicy = view.findViewById(R.id.shippingPolicy);
        bankDetails = view.findViewById(R.id.bankDetails);

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FAQs frag = new FAQs();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.commit();
            }
        });

        bookingPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookingPolicy frag = new BookingPolicy();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.commit();
            }
        });

        bankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForBuisnessFragment frag = new ForBuisnessFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.commit();
            }
        });
        legal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LegalFragment frag = new LegalFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.commit();
            }
        });
    return view;
    }

}

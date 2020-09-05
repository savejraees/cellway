package com.cellway.Cellway.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LegalFragment extends Fragment {

    View view;

    public LegalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_legal, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Legal");

   return view;
    }

}

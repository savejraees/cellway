package com.cellway.Cellway.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cellway.Cellway.R;
import com.cellway.Cellway.adapter.SlideBookNow;
import com.cellway.Cellway.model.BookModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullScreenBook extends Fragment {

    View view;

    ArrayList<BookModel> listFullScreen;
    int positionImage;
    public FullScreenBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_full_screen_book, container, false);
        listFullScreen =  getArguments().getParcelableArrayList("fullImage");
        positionImage = getArguments().getInt("positionImage");
        Log.d("asdkjl", String.valueOf(listFullScreen));
        Log.d("asdkadjl", String.valueOf(positionImage));
        ViewPager mViewPager = view.findViewById(R.id.viewPage);


      //  listFullScreen.set(0,listFullScreen.get(positionImage));

        Log.d("asdkjl", String.valueOf(listFullScreen));

        SlideBookNow adapterView = new SlideBookNow(getActivity(),listFullScreen);
        mViewPager.setAdapter(adapterView);

        mViewPager.setCurrentItem(positionImage);


   return view;
    }

}

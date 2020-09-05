package com.cellway.Cellway.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cellway.Cellway.activity.AccesorriesActivity;
import com.cellway.Cellway.activity.AddressActivity;
import com.cellway.Cellway.activity.Mobile_DestinationNewActivity;
import com.cellway.Cellway.R;
import com.cellway.Cellway.activity.OrderPlacedActivity;
import com.cellway.Cellway.adapter.DemandingAdapter;
import com.cellway.Cellway.adapter.LatestArrival_AdapterNew;
import com.cellway.Cellway.adapter.Mobile_slide_Adapter;
import com.cellway.Cellway.adapter.SwipeBannerAdapter;
import com.cellway.Cellway.adapter.ThreeBannerAdapter;
import com.cellway.Cellway.adapter.VerticalBannerAdapter;
import com.cellway.Cellway.model.Mobile_destination;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomePageNew extends Fragment {
    View view;

    PageListener pageListener;
  //  TabLayout tabLayout;
    ViewPager mViewPager;
    TextView MarqueeText;
    RecyclerView rvThreeBanner,rvVerticalBanner,rvSwipeBanner,rvLatest,rvMostDemanding;
    ArrayList<Integer> threeList = new ArrayList<>();
    ArrayList<Integer> verticalList = new ArrayList<>();
    ArrayList<Integer> swipeList = new ArrayList<>();
    ArrayList<Integer> demandList = new ArrayList<>();
    ArrayList<String> latestList = new ArrayList<>();
    int [] three = {R.drawable.sample,R.drawable.blue13};
    int [] vertical = {R.drawable.blue3,R.drawable.three_banner2,R.drawable.blue1};
    int [] swipe = {R.drawable.blue4,R.drawable.blue7,R.drawable.sample};
    int [] demand = {R.drawable.blue6,R.drawable.blue5,R.drawable.sample};
    String [] latest = {"Lenovo","Apple","Oppo","Samsung"};
    ArrayList<Mobile_destination> arrBannerImage = new ArrayList<>();
    int[] listBanner = {R.drawable.sample,R.drawable.blue3,R.drawable.sample,R.drawable.blue13};
    ImageView banner1,timerBanner,banner2,imgSamsung,imgLenovo;
    Timer swipeTimer;
    private static int currentPage = 0;
    TextView txtMobile,txtTablet,txtAccess;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page_new, container, false);
        MarqueeText = view.findViewById(R.id.MarqueeText);
        txtMobile = view.findViewById(R.id.txtMobile);
        txtTablet = view.findViewById(R.id.txtTablet);
        txtAccess = view.findViewById(R.id.txtAccess);
        rvThreeBanner = view.findViewById(R.id.rvThreeBanner);
        rvVerticalBanner = view.findViewById(R.id.rvVerticalBanner);
        rvSwipeBanner = view.findViewById(R.id.rvSwipeBanner);
        rvLatest = view.findViewById(R.id.rvLatest);
        rvMostDemanding = view.findViewById(R.id.rvMostDemanding);
        banner1 = view.findViewById(R.id.banner1);
        banner1.setClipToOutline(true);
        timerBanner = view.findViewById(R.id.timerBanner);
        timerBanner.setClipToOutline(true);
        banner2 = view.findViewById(R.id.banner2);
        banner2.setClipToOutline(true);
        imgSamsung = view.findViewById(R.id.imgSamsung);
        imgSamsung.setClipToOutline(true);
        imgLenovo = view.findViewById(R.id.imgLenovo);
        imgLenovo.setClipToOutline(true);
        rvVerticalBanner.setNestedScrollingEnabled(false);
        MarqueeText.setSelected(true);
        mViewPager = view.findViewById(R.id.pic_viewpager);
        mViewPager.setOnPageChangeListener(pageListener);
        //tabLayout = view.findViewById(R.id.tab_dots);
       // tabLayout.setupWithViewPager(mViewPager, true);

        viewPager();
        setRvThree();
        setRvVertical();
        setRvSwipe();
        setRvDemand();
        setRvLatest();

        txtMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Mobile_DestinationNewActivity.class));
            }
        });
        txtTablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderPlacedActivity.class));
            }
        });

        txtAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getActivity(), AccesorriesActivity.class));
            }
        });
        return view;
    }

    private void setRvDemand() {
        for(int i = 0;i<demand.length;i++){
            demandList.add((demand[i]));
        }
        rvMostDemanding.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rvMostDemanding.setAdapter(new DemandingAdapter(getActivity(),demandList));
    }

    private void setRvLatest() {
        for(int i = 0;i<latest.length;i++){
            latestList.add((latest[i]));
        }
        rvLatest.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rvLatest.setAdapter(new LatestArrival_AdapterNew(getActivity(),latestList));
    }

    private void setRvSwipe() {
        for(int i = 0;i<swipe.length;i++){
            swipeList.add((swipe[i]));
        }
        rvSwipeBanner.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rvSwipeBanner.setAdapter(new SwipeBannerAdapter(getActivity(),swipeList));
    }

    private void setRvVertical() {
        for(int i = 0;i<vertical.length;i++){
            verticalList.add((vertical[i]));
        }
        rvVerticalBanner.setLayoutManager(new GridLayoutManager(getActivity(),1));
        rvVerticalBanner.setAdapter(new VerticalBannerAdapter(getActivity(),verticalList));
    }

    private void setRvThree() {
        for(int i = 0;i<three.length;i++){

            threeList.add((three[i]));
        }
        Log.d("addasdsadsa", String.valueOf(threeList.size()));
        if(threeList.size()==3){
            rvThreeBanner.setLayoutManager(new GridLayoutManager(getActivity(),3));
        }
        if(threeList.size()==2){
            rvThreeBanner.setLayoutManager(new GridLayoutManager(getActivity(),2));
        }
        if(threeList.size()==1){
            rvThreeBanner.setLayoutManager(new GridLayoutManager(getActivity(),1));
        }

        rvThreeBanner.setAdapter(new ThreeBannerAdapter(getActivity(),threeList));
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPage=0;
    }

    public void viewPager(){

        for(int i = 0;i<listBanner.length;i++){
            Mobile_destination model = new Mobile_destination();
            model.setBannerImage(String.valueOf(listBanner[i]));
            arrBannerImage.add(model);
        }
        Mobile_slide_Adapter adapterView = new Mobile_slide_Adapter(getActivity(), arrBannerImage);
        mViewPager.setAdapter(adapterView);


         final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == listBanner.length) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);}

    private class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {

            currentPage = position;
            Log.d("asldkjasd",""+currentPage);

//            if(currentPage==2){
//                btnToLogin.setVisibility(View.VISIBLE);
//            }else {
//                btnToLogin.setVisibility(View.GONE);
//            }

        }
    }
}
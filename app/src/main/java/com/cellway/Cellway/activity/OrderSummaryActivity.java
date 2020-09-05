package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.OrderSummaryAdapter;
import com.cellway.Cellway.adapter.OrderSummryPagerAdpater;
import com.cellway.Cellway.fragment.DeliveryFragment;
import com.cellway.Cellway.fragment.PickupFragment;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailDatumModel;
import com.cellway.Cellway.retrofitModel.CartModel.CartDetailStatusModel;
import com.cellway.Cellway.util.SessonManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends BaseActivity {

    TabLayout mTabs;
    View mIndicator;
    ViewPager mViewPager;
    ArrayList<CartDetailDatumModel> cartListDetail = new ArrayList<>();

    private int indicatorWidth;
    OrderSummryPagerAdpater orderSummryPagerAdpater;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        mTabs = findViewById(R.id.tabOrder);
        mIndicator = findViewById(R.id.indicatorOrder);
        mViewPager = findViewById(R.id.viewPagerOrder);
        sessonManager = new SessonManager(OrderSummaryActivity.this);

//        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager());
//        adapter.addFragment(DeliveryFragment.newInstance(), "Delivery");
//        adapter.addFragment(PickupFragment.newInstance(), "Pickup");
//        mViewPager.setAdapter(adapter);
//        mTabs.setupWithViewPager(mViewPager);
        orderSummryPagerAdpater = new OrderSummryPagerAdpater(getSupportFragmentManager(),2 );
        mViewPager.setAdapter(orderSummryPagerAdpater);
        mTabs.setupWithViewPager(mViewPager);

        //Determine indicator width at runtime
        mTabs.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidth = mTabs.getWidth() / mTabs.getTabCount();

                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                mIndicator.setLayoutParams(indicatorParams);
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (positionOffset+i) * indicatorWidth ;
                params.leftMargin = (int) translationOffset;
                mIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {
                Fragment fragment = orderSummryPagerAdpater.getRegisteredFragment(i);
                if (fragment instanceof DeliveryFragment) {
                    DeliveryFragment latestJobFragment = (DeliveryFragment) orderSummryPagerAdpater.getRegisteredFragment(i);
                    latestJobFragment.setQuery(0);
                } else if (fragment instanceof PickupFragment) {
                    PickupFragment admitCardFragment = (PickupFragment) orderSummryPagerAdpater.getRegisteredFragment(i);
                    admitCardFragment.setQuery(1);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }




    public class TabFragmentAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public TabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }
    }

}
package com.cellway.Cellway.adapter;


import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cellway.Cellway.fragment.DeliveryFragment;
import com.cellway.Cellway.fragment.PickupFragment;

import org.jetbrains.annotations.NotNull;

public class OrderSummryPagerAdpater extends FragmentPagerAdapter {

        int totalTabs;
        String[] tableArray=new String[]{"Delivery","Pickup"};
        SparseArray<Fragment> registeredFragments = new SparseArray<>();

        public OrderSummryPagerAdpater(FragmentManager fm, int totalTabs) {
            super(fm);
            this.totalTabs = totalTabs;
        }

    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tableArray[position];
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new  DeliveryFragment();

                case 1:
                    return new PickupFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }

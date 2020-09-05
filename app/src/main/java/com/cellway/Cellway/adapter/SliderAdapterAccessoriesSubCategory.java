package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.AccessoriesCategoryModel.CatgoryBannerModel;
import com.cellway.Cellway.retrofitModel.AccessoriesSubCateegoryModel.SubCategoryBannerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapterAccessoriesSubCategory extends PagerAdapter {
    private ArrayList<SubCategoryBannerModel> images;
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapterAccessoriesSubCategory(Context context, ArrayList<SubCategoryBannerModel> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide_image, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.custom_image);
        //  Picasso.with(context).load(images.get(position).getBannerImage()).into(myImage);
        Picasso.get().load(images.get(position).getBannerImg()).placeholder(R.drawable.blue1).into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}



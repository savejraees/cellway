package com.cellway.Cellway.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cellway.Cellway.R;
import com.cellway.Cellway.model.Mobile_destination;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Mobile_slide_Adapter extends PagerAdapter {
    private ArrayList<Mobile_destination> images;
    private LayoutInflater inflater;
    private Context context;

    public Mobile_slide_Adapter(Context context, ArrayList<Mobile_destination> images) {
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
        myImage.setScaleType(ImageView.ScaleType.FIT_XY);
      //  Picasso.with(context).load(images.get(position).getBannerImage()).into(myImage);
        Picasso.get().load(images.get(position).getBannerImage()).placeholder(R.drawable.blue1).into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
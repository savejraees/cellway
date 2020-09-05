package com.cellway.Cellway.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cellway.Cellway.model.BookModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

public class SlideBookNow extends PagerAdapter {
    Context mContext;
    ArrayList<BookModel> listBook;
    int positionImage;

    public SlideBookNow(Context context, ArrayList<BookModel> listBook) {
        this.mContext = context;
        this.listBook = listBook;

//        this.positionImage = positionImage;;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imageView = new PhotoView(mContext);

        try{

            Picasso.get().load(listBook.get(position).getBookMobileImage()).into(imageView);
            ((ViewPager) container).addView(imageView, 0);
        }catch (IllegalArgumentException e){

        }


       // imageView.setScaleType(PhotoView.ScaleType.FIT_XY);

        //  imageView.setImageResource(Book.all_image[position]);


        return imageView;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return listBook.size();
    }


}

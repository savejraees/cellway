package com.cellway.Cellway.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.ScrollerSpeed;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.Mobile_slide_Adapter;
import com.cellway.Cellway.model.Mobile_destination;
import com.cellway.Cellway.model.Product_Description;
import com.cellway.Cellway.model.TabletModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



public class HomePage extends Fragment  {

    LinearLayout layoutMobile, layoutTablet, layout_Accessories, layoutMore;
    View view;
    LatestArrival_Adapter adapter;
    RecyclerView rv_latest;
    ProgressBar progressbarHome;
    int currentPage;
    EditText editSearch;

    ProgressDialog progressDialog;
   // ArrayList<Mobile_destination> arrbrandMobileIcon;
  //  ArrayList<TabletModel> arrbrandTabletIcon = new ArrayList<>();
    ArrayList<Mobile_destination> arrBannerImage = new ArrayList<>();
    ArrayList<Product_Description> arrLatest = new ArrayList<>();
    ArrayList<Product_Description> arrAccessories = new ArrayList<>();
    ArrayList<Product_Description> arrOther = new ArrayList<>();


    TabLayout tabLayout;
    ViewPager mViewPager;
    TextView MarqueeText;

   int currentCountPage =1;

    public HomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
       // arrbrandMobileIcon = new ArrayList<>();
        ((Home) getActivity()).getSupportActionBar().setTitle("CELLWAY");

        editSearch = view.findViewById(R.id.editSearch);
        progressbarHome = view.findViewById(R.id.progressbarHome);

//        editSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SearchFragment fragment = new SearchFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.frame,fragment);
//                fragmentTransaction.commit();
//                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//            }
//        });


        layoutMobile = view.findViewById(R.id.layoutMobile);
        layoutTablet = view.findViewById(R.id.layoutTablet);
        layout_Accessories = view.findViewById(R.id.layout_Accessories);
        layoutMore = view.findViewById(R.id.layoutMore);

        rv_latest = view.findViewById(R.id.rv_latest);
        MarqueeText = view.findViewById(R.id.MarqueeText);
        MarqueeText.setSelected(true);
        mViewPager = view.findViewById(R.id.pic_viewpager);
        tabLayout = view.findViewById(R.id.tab_dots);
        tabLayout.setupWithViewPager(mViewPager, true);

        ViewCompat.setNestedScrollingEnabled(rv_latest, false);


        homeApi();
        layoutClick();
        //setlatestMobile();

        init();

        return view;
    }

  public void init(){
      tabLayout.setupWithViewPager(mViewPager, true);
      try {

          Interpolator sInterpolator = new Interpolator() {
              @Override
              public float getInterpolation(float v) {
                  return v;
              }
          };
          Field mScroller;
          mScroller = ViewPager.class.getDeclaredField("mScroller");
          mScroller.setAccessible(true);
          ScrollerSpeed scroller = new ScrollerSpeed(mViewPager.getContext(), sInterpolator);
          mScroller.set(mViewPager, scroller);
      } catch (NoSuchFieldException e) {
      } catch (IllegalArgumentException e) {
      } catch (IllegalAccessException e) {
      }
      final Handler handler = new Handler();
      final Runnable Update = new Runnable() {
          public void run() {
              if (currentPage == arrBannerImage.size()) {
                  currentPage = 0;

                  mViewPager.setCurrentItem(currentPage++, false);
              }else{
                  if (currentPage!=mViewPager.getCurrentItem()+1){
                      currentPage = mViewPager.getCurrentItem()+1;
                  }

                  mViewPager.setCurrentItem(currentPage++, true);
              }
          }
      };

      if(currentCountPage==1){
          Timer timer = new Timer(); // This will create a new Thread
          timer .schedule(new TimerTask() { // task to be scheduled

              @Override
              public void run() {
                  handler.post(Update);
              }
          }, 1000, 3000);
          currentCountPage++;
      }

  }


    private void homeApi() {

        progressbarHome.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.home_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdkddfd", response);
                progressbarHome.setVisibility(View.GONE);

                arrBannerImage.clear();
                arrLatest.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);


                    JSONArray bannerArray = jsonObject1.getJSONArray("banner");
                    for (int i = 0; i < bannerArray.length(); i++) {
                        JSONObject jsonObjectTablet = bannerArray.getJSONObject(i);
                        Mobile_destination mobile_destination = new Mobile_destination();
                        mobile_destination.setBannerImage(jsonObjectTablet.getString("banner_image"));
                        mobile_destination.setId(jsonObjectTablet.getString("id"));
                        arrBannerImage.add(mobile_destination);
                        Mobile_slide_Adapter adapterView = new Mobile_slide_Adapter(getActivity(), arrBannerImage);
                        mViewPager.setAdapter(adapterView);

                    }

                    JSONArray latestarray = jsonObject1.getJSONArray("allmobile");
                    for (int i = 0; i < latestarray.length(); i++) {
                        JSONObject latestarrayJSONObject = latestarray.getJSONObject(i);
                        Product_Description mobile_destination = new Product_Description();
                        mobile_destination.setImg_mobile(latestarrayJSONObject.getString("image"));
                        mobile_destination.setId(latestarrayJSONObject.getString("id"));
                        mobile_destination.setBrand(latestarrayJSONObject.getString("brand"));
                        mobile_destination.setModel(latestarrayJSONObject.getString("model"));
                        mobile_destination.setGb(latestarrayJSONObject.getString("gb"));
                        mobile_destination.setDeduct_price(latestarrayJSONObject.getString("sale_amount"));
                        mobile_destination.setTv_wrntyMonth(latestarrayJSONObject.getString("warrenty_month"));
                        mobile_destination.setTv_wrnty(latestarrayJSONObject.getString("warrenty"));
                        mobile_destination.setCondition(latestarrayJSONObject.getString("product_condotion"));
                        mobile_destination.setRemark(latestarrayJSONObject.getString("remark"));
                        arrLatest.add(mobile_destination);

                        Log.d("wiqepsa",latestarrayJSONObject.getString("image"));
                        rv_latest.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                        rv_latest.setLayoutManager(layoutManager);
                        adapter = new LatestArrival_Adapter(getActivity(), arrLatest);
                        rv_latest.setAdapter(adapter);
                    }


                    Log.d("ardsfa", String.valueOf(arrLatest));

                    JSONArray marqueArray = jsonObject1.getJSONArray("Text_msg");
                    JSONObject jsonMarque = marqueArray.getJSONObject(0);
                    String textMessage = jsonMarque.getString("text_msg");
                    String marqueId = jsonMarque.getString("id");
                    MarqueeText.setText(textMessage);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarHome.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                Log.d("sad", hashMap.toString());
                return hashMap;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }


    private void layoutClick() {
        layoutMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mobile_Destination frag = new Mobile_Destination();
                loadFragment(frag);

            }
        });

        layoutTablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabletDestination frag = new TabletDestination();
                loadFragment(frag);
            }
        });

        layout_Accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product frag = new Product();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("pName", "Accessories");
                bundle.putString("sortValue", "accessories");
              //  bundle.putInt("visibility",1);
                bundle.putString("pBrandId"," ");
                frag.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame, frag);
                fragmentTransaction.commitAllowingStateLoss();
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

            }
        });

        layoutMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product frag = new Product();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                Bundle bundle = new Bundle();
                bundle.putString("pName", "More");
                bundle.putString("sortValue", "more");
              //  bundle.putInt("visibility",1);
                bundle.putString("pBrandId"," ");
                frag.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame, frag);
                fragmentTransaction.commitAllowingStateLoss();
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

            }
        });

    }




    /////////////////////////////////// Latest Adapter /////////////////////////////////////////////////////////////////////
   public class LatestArrival_Adapter extends RecyclerView.Adapter<LatestArrival_Adapter.LatestViewHolder> {

        Context context;
        ArrayList<Product_Description> lstArvl;


        public LatestArrival_Adapter(Context context, ArrayList<Product_Description> lstArvl) {
            this.context = context;
            this.lstArvl = lstArvl;
        }


        @NonNull
        @Override
        public LatestArrival_Adapter.LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_latestarrival,parent,false);
            return new LatestArrival_Adapter.LatestViewHolder(view);
        }


        @Override
        public void onBindViewHolder(LatestArrival_Adapter.LatestViewHolder holder, int position) {
            Product_Description all = lstArvl.get(position);
            Picasso.get().load(lstArvl.get(position).getImg_mobile()).into(holder.latst_mobile);
            holder.mobileName.setText(all.getBrand());
            holder.mobileGB.setText(all.getGb());
            holder.mobile_rate.setText(all.getDeduct_price());
            holder.mobileModel.setText(all.getModel());

        }


        @Override
        public int getItemCount() {

                return lstArvl.size();


        }

        public class LatestViewHolder extends RecyclerView.ViewHolder{

            ImageView latst_mobile;
            TextView mobileName,mobileGB,mobile_rate,mobileModel;
            public LatestViewHolder(@NonNull View itemView) {
                super(itemView);
                latst_mobile = itemView.findViewById(R.id.latst_mobile);
                mobileName = itemView.findViewById(R.id.mobileName);
                mobileGB = itemView.findViewById(R.id.mobileGB);
                mobile_rate = itemView.findViewById(R.id.mobile_rate);
                mobileModel = itemView.findViewById(R.id.mobileModel);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Product_Description product_description = lstArvl.get(getAdapterPosition());
                        String id = product_description.getId();
                        String brand = product_description.getBrand();
                        String model = product_description.getModel();
                        String gb = product_description.getGb();
                        String price = product_description.getDeduct_price();
                        String warantyMonth = product_description.getTv_wrntyMonth();
                        String waranty = product_description.getTv_wrnty();
                        String imageMobile = product_description.getImg_mobile();
                        String remark = product_description.getRemark();
                        String condition = product_description.getCondition();

                        Log.d("sasda",id+" "+brand+" "+price+" "+warantyMonth);


                        Book frag = new Book();
                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("productDetails",lstArvl);
                        bundle.putString("ImageId",id);
                        bundle.putString("brand",brand);
                        bundle.putString("model",model);
                        bundle.putString("gb",gb);
                        bundle.putString("price",price);
                        bundle.putString("warrantyMonth",warantyMonth);
                        bundle.putString("warranty",waranty);
                        bundle.putString("image",imageMobile);
                        bundle.putString("remark",remark);
                        bundle.putString("condition",condition);
                        frag.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame,frag);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commitAllowingStateLoss();
                        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

                    }
                });

            }
        }

    }  ///////// close Latest Adapter //////////

    public void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commitAllowingStateLoss();
        ((Home) getActivity()).getSupportActionBar().hide();
    }


}








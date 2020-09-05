package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.AccessoriesColorAdapter;
import com.cellway.Cellway.adapter.AvailableOfferAccessAdapter;
import com.cellway.Cellway.adapter.AvailableOfferAdapter;
import com.cellway.Cellway.adapter.PaymentAccessoriesAdapter;
import com.cellway.Cellway.adapter.PaymentAdapter;
import com.cellway.Cellway.fragment.BotomSheetDialogFragmnt;
import com.cellway.Cellway.retrofitModel.BookingAccessoriesModel.BookingAccessoriesAvailableOffer;
import com.cellway.Cellway.retrofitModel.BookingAccessoriesModel.BookingAccessoriesBanner;
import com.cellway.Cellway.retrofitModel.BookingAccessoriesModel.BookingAccessoriesPaymentPolicy;
import com.cellway.Cellway.retrofitModel.BookingAccessoriesModel.BookingAccessoriesStatusModel;
import com.cellway.Cellway.retrofitModel.CartModel.AddCartModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingAvaiableModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingBannerModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingPaymentPolicy;
import com.cellway.Cellway.retrofitModel.YourBookingModel.PincodeModel;
import com.cellway.Cellway.util.SessonManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookNewAccessoriesActivity extends BaseActivity {

    TextView txtBrandModel, txtPrice, txtPinCode, txtAvailOffer, txtOffAccessBook, original_price_AccessBook, txtDescription, txtFreeDelivry;
    EditText edtPin;
    Button btnPinCode;
    private ShimmerFrameLayout mShimmerViewContainer;
    NestedScrollView nestedBookNew;
    ArrayList<BookingAccessoriesBanner> listBanner = new ArrayList<>();
    ArrayList<BookingAccessoriesAvailableOffer> listOffer = new ArrayList<>();
    ArrayList<BookingAccessoriesPaymentPolicy> listPayment = new ArrayList<>();
    ViewPager mViewPager;
    DotsIndicator dots_indicator;
    Button btnBuyNow, btnAddCart;
    RecyclerView rvAvailble, rvPayment;
    ImageView imgFreeDelivery,imgBackBookingAccess;
    Dialog dialogCashOnDelivry;
    String prod_ID;
    TextView txtCartAccessories, txtQuantity;
    int price=0,mrp=0,quantity = 0;
    RelativeLayout decrese, increse,quantity_layout;
    LinearLayout add_layout;
    int constantCart=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_new_accessories);
        sessonManager = new SessonManager(BookNewAccessoriesActivity.this);
        txtBrandModel = findViewById(R.id.txtBrandAccess);
        txtPrice = findViewById(R.id.txtPriceAccess);
        txtOffAccessBook = findViewById(R.id.txtOffAccessBook);
        txtPinCode = findViewById(R.id.txtPinCodeAccess);
        edtPin = findViewById(R.id.edtPinAccess);
        txtAvailOffer = findViewById(R.id.txtAvailOfferAccess);
        btnPinCode = findViewById(R.id.btnPinCodeAccess);
        txtDescription = findViewById(R.id.txtDescription);
        mShimmerViewContainer = findViewById(R.id.shimmer_BookNewAccess);
        nestedBookNew = findViewById(R.id.nestedBookNewAccess);
        mViewPager = findViewById(R.id.book_viewpagersAccess);
        dots_indicator = findViewById(R.id.dots_indicatorAccess);
        btnBuyNow = findViewById(R.id.btnBuyNowAccess);
        btnAddCart = findViewById(R.id.btnAddCartAccess);
        rvAvailble = findViewById(R.id.rvAvailbleAccess);
        rvPayment = findViewById(R.id.rvPaymentAccess);
        imgFreeDelivery = findViewById(R.id.imgFreeDeliveryAccess);
        txtCartAccessories = findViewById(R.id.txtCartAccessories);
        txtQuantity = findViewById(R.id.txtQuantity);
        decrese = findViewById(R.id.decrese);
        increse = findViewById(R.id.increse);
        add_layout = findViewById(R.id.add_layout);
        quantity_layout = findViewById(R.id.quantity_layout);
        imgBackBookingAccess = findViewById(R.id.imgBackBookingAccess);
        original_price_AccessBook = findViewById(R.id.original_price_AccessBook);
        original_price_AccessBook.setPaintFlags(original_price_AccessBook.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        dialogCashOnDelivry = new Dialog(BookNewAccessoriesActivity.this);
        dialogCashOnDelivry.setContentView(R.layout.dialog_cash_on_delivery);
        txtFreeDelivry = dialogCashOnDelivry.findViewById(R.id.txtFreeDelivry);

        if (!sessonManager.getPinCode().isEmpty()) {
            btnPinCode.setText("CHANGE");
            edtPin.setText(sessonManager.getPinCode());
            txtPinCode.setText("Delivery Available");
            txtPinCode.setTextColor(Color.parseColor("#65EB2F"));
        }
        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            txtCartAccessories.setVisibility(View.VISIBLE);
            txtCartAccessories.setText(sessonManager.getQty());
        }

        if (getIntent().hasExtra("id")) {
            String id = getIntent().getStringExtra("id");
            Log.d("sadjkads", id + "  " + sessonManager.getToken());
            hitApi(id);
        }

        btnPinCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPin.getText().toString().isEmpty()) {
                    edtPin.setError("Pincode can't be blank");
                } else {
                    hitPinCodeApi();
                }
            }
        });

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtPinCode.getText().toString().equals("Delivery Available")) {
                    if(quantity>0){
                            if (btnAddCart.getText().toString().equalsIgnoreCase("GO TO CART")) {
                                startActivity(new Intent(BookNewAccessoriesActivity.this, AddressActivity.class));
                            }else {
                                hitAddCartApiBuy();
                            }

                        }else {
                            Toast.makeText(BookNewAccessoriesActivity.this, "Please Add Some Item", Toast.LENGTH_SHORT).show();
                        }

                } else {
                    Toast.makeText(BookNewAccessoriesActivity.this, "Please Verify the Pincode", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(quantity>0){
                    if (txtPinCode.getText().toString().equals("Delivery Available")) {
                        if (btnAddCart.getText().toString().equalsIgnoreCase("GO TO CART")) {
                            startActivity(new Intent(BookNewAccessoriesActivity.this, CartActivity.class));
                        } else {
                            Log.d("ajkjlassd",""+quantity);
                            hitAddCartApi();
                        }

                    } else {
                        Toast.makeText(BookNewAccessoriesActivity.this, "Please Verify the Pincode", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BookNewAccessoriesActivity.this, "Please Add Some No of Item", Toast.LENGTH_SHORT).show();
                }

            }
        });
        txtCartAccessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessonManager.setAddressIntent("buyAccessories");
                sessonManager.setProductIdBook(prod_ID);
                startActivity(new Intent(BookNewAccessoriesActivity.this, CartActivity.class));
            }
        });

        imgFreeDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCashOnDelivry.show();
            }
        });
        txtFreeDelivry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCashOnDelivry.dismiss();
            }
        });

        increse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity = quantity + 1;
                txtQuantity.setText(""+quantity);
                txtPrice.setText(""+(quantity*price));
                original_price_AccessBook.setText(""+((mrp*quantity)));
                if(constantCart==quantity){
                    btnAddCart.setText("GO TO CART");
                }else{
                    btnAddCart.setText("ADD TO CART");
                }
            }
        });

        decrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(quantity>1){
                    quantity = quantity - 1;
                    txtQuantity.setText(""+quantity);
                    txtPrice.setText(""+(quantity*price));
                    original_price_AccessBook.setText(""+((mrp*quantity)));

                    if(constantCart==quantity){
                        btnAddCart.setText("GO TO CART");
                    }else{
                        btnAddCart.setText("ADD TO CART");
                    }
                }else {
                    txtPrice.setText(""+(price));
                    original_price_AccessBook.setText(""+(mrp));
                    add_layout.setVisibility(View.VISIBLE);
                    quantity_layout.setVisibility(View.GONE);
                    quantity=0;
                    txtQuantity.setText("");
                    btnAddCart.setText("ADD TO CART");

                }

            }
        });
        add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = quantity + 1;
                txtQuantity.setText(""+quantity);
                quantity_layout.setVisibility(View.VISIBLE);
                add_layout.setVisibility(View.GONE);
            }
        });

        imgBackBookingAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookNewAccessoriesActivity.this, AccesoiesProductActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("catId",sessonManager.getCatId())
                        .putExtra("subCatId",sessonManager.getSubCatId()));
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            txtCartAccessories.setVisibility(View.VISIBLE);
            txtCartAccessories.setText(sessonManager.getQty());
        }else {
            txtCartAccessories.setVisibility(View.GONE);
        }

    }

    private void hitAddCartApi() {
        showProgress(BookNewAccessoriesActivity.this);
        Call<AddCartModel> call = api.addCart(Api.key, prod_ID, "accessories", quantity, sessonManager.getToken(), 0, 0, 0);
        call.enqueue(new Callback<AddCartModel>() {
            @Override
            public void onResponse(Call<AddCartModel> call, Response<AddCartModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    AddCartModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                      //  Toast.makeText(BookNewAccessoriesActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                        btnAddCart.setText("GO TO CART");
                        txtCartAccessories.setVisibility(View.VISIBLE);
                        txtCartAccessories.setText(""+StatusModel.getTotalqty());
                        sessonManager.setQty(""+StatusModel.getTotalqty());

//                        sessonManager.setAddressIntent("phoneDetail");
//                        sessonManager.setProductIdBook(prod_ID);

                    } else {
                        Toast.makeText(BookNewAccessoriesActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddCartModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(BookNewAccessoriesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitAddCartApiBuy() {
        showProgress(BookNewAccessoriesActivity.this);
        Call<AddCartModel> call = api.addCart(Api.key, prod_ID, "accessories", quantity, sessonManager.getToken(), 0, 0, 0);
        call.enqueue(new Callback<AddCartModel>() {
            @Override
            public void onResponse(Call<AddCartModel> call, Response<AddCartModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    AddCartModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                       // Toast.makeText(BookNewAccessoriesActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();

                        txtCartAccessories.setVisibility(View.VISIBLE);
                        txtCartAccessories.setText(""+StatusModel.getTotalqty());
                        sessonManager.setQty(""+StatusModel.getTotalqty());

                        btnAddCart.setText("GO TO CART");

//                        sessonManager.setAddressIntent("accessories");
//                        sessonManager.setProductIdBook(prod_ID);
                        startActivity(new Intent(BookNewAccessoriesActivity.this, AddressActivity.class));

                    } else {
                        Toast.makeText(BookNewAccessoriesActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddCartModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(BookNewAccessoriesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void hitPinCodeApi() {
        showProgress(BookNewAccessoriesActivity.this);
        Call<PincodeModel> call = api.getPinCodeDetail(Api.key, edtPin.getText().toString());
        call.enqueue(new Callback<PincodeModel>() {
            @Override
            public void onResponse(Call<PincodeModel> call, Response<PincodeModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    PincodeModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                        txtPinCode.setText("Delivery Available");
                        txtPinCode.setTextColor(Color.parseColor("#65EB2F"));
                        sessonManager.setPinCode(edtPin.getText().toString());

                    } else {
                        txtPinCode.setText("Delivery is Not Available for this Pincode");
                        txtPinCode.setTextColor(Color.parseColor("#EB2F51"));
                    }
                }
            }

            @Override
            public void onFailure(Call<PincodeModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(BookNewAccessoriesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void hitApi(final String id) {
        Call<BookingAccessoriesStatusModel> call = api.getBookingAccessoriesDetail(Api.key, id, sessonManager.getToken());
        call.enqueue(new Callback<BookingAccessoriesStatusModel>() {
            @Override
            public void onResponse(Call<BookingAccessoriesStatusModel> call, Response<BookingAccessoriesStatusModel> response) {

                if (response.isSuccessful()) {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    nestedBookNew.setVisibility(View.VISIBLE);
                    BookingAccessoriesStatusModel BookingAccessoriesStatusModel = response.body();

                    prod_ID = id;

                    if (BookingAccessoriesStatusModel.getQty() > 0) {
                        btnAddCart.setText("GO TO CART");
                        txtQuantity.setText("" + BookingAccessoriesStatusModel.getQty());
                        quantity = BookingAccessoriesStatusModel.getQty();
                        add_layout.setVisibility(View.GONE);
                        quantity_layout.setVisibility(View.VISIBLE);
                        txtPrice.setText("" + BookingAccessoriesStatusModel.getPrice()*quantity);
                        original_price_AccessBook.setText("" + BookingAccessoriesStatusModel.getMrp()*quantity);
                        constantCart = quantity;
                    }else {
                        txtPrice.setText("" + BookingAccessoriesStatusModel.getPrice());
                        original_price_AccessBook.setText("" + BookingAccessoriesStatusModel.getMrp());
                    }


                    txtBrandModel.setText("(Openbox) " + BookingAccessoriesStatusModel.getName());
                    txtDescription.setText("" + BookingAccessoriesStatusModel.getDescription());
                    int off = 100 - (BookingAccessoriesStatusModel.getPrice() * 100) / BookingAccessoriesStatusModel.getMrp();
                    txtOffAccessBook.setText(off + "%off");
                    price = BookingAccessoriesStatusModel.getPrice();
                    mrp = BookingAccessoriesStatusModel.getMrp();

                    listBanner = BookingAccessoriesStatusModel.getProductImages();
                    viewPager();

                    listOffer = BookingAccessoriesStatusModel.getAvailableoffer();
                    if (listOffer.size() == 0) {
                        rvAvailble.setVisibility(View.GONE);
                        txtAvailOffer.setVisibility(View.GONE);
                    } else {
                        setRVOffer();
                    }

                    listPayment = BookingAccessoriesStatusModel.getPaymentPolicy();
                    setRvPayment();

                }
            }

            @Override
            public void onFailure(Call<BookingAccessoriesStatusModel> call, Throwable t) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                Toast.makeText(BookNewAccessoriesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRVOffer() {

        rvAvailble.setLayoutManager(new GridLayoutManager(BookNewAccessoriesActivity.this, 1));
        rvAvailble.setAdapter(new AvailableOfferAccessAdapter(BookNewAccessoriesActivity.this, listOffer));
    }

    private void setRvPayment() {
        rvPayment.setLayoutManager(new GridLayoutManager(BookNewAccessoriesActivity.this, 1));
        rvPayment.setAdapter(new PaymentAccessoriesAdapter(BookNewAccessoriesActivity.this, listPayment));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    public void viewPager() {

        SlideAdapter adapterView = new SlideAdapter(BookNewAccessoriesActivity.this, listBanner);
        mViewPager.setAdapter(adapterView);
        dots_indicator.setViewPager(mViewPager);
    }


    public class SlideAdapter extends PagerAdapter {
        private ArrayList<BookingAccessoriesBanner> images;
        private LayoutInflater inflater;
        private Context context;

        public SlideAdapter(Context context, ArrayList<BookingAccessoriesBanner> images) {
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
            Picasso.get().load(images.get(position).getProductImg()).placeholder(R.drawable.blue1).into(myImage);
            view.addView(myImageLayout, 0);
            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BookNewAccessoriesActivity.this, AccesoiesProductActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("catId",sessonManager.getCatId())
                .putExtra("subCatId",sessonManager.getSubCatId()));
        //super.onBackPressed();
    }
}
package com.cellway.Cellway.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.AvailableOfferAdapter;
import com.cellway.Cellway.adapter.PaymentAdapter;
import com.cellway.Cellway.fragment.BotomSheetDialogFragmnt;
import com.cellway.Cellway.retrofitModel.CartModel.AddCartModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingAvaiableModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingBannerModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingPaymentPolicy;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingStatusModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.PincodeModel;
import com.cellway.Cellway.retrofitModel.YourMobDestination.MobDestData;
import com.cellway.Cellway.util.SessonManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookNewActivity extends BaseActivity implements BotomSheetDialogFragmnt.BottomSheetListener {

    ViewPager mViewPager;
    DotsIndicator dots_indicator;


    ArrayList<BookingBannerModel> listBanner = new ArrayList<>();
    ArrayList<BookingAvaiableModel> listOffer = new ArrayList<>();
    ArrayList<BookingPaymentPolicy> listPayment = new ArrayList<>();

    RecyclerView rvAvailble, rvPayment;
    LinearLayout linearAdd;
    ImageView imgCheck, imgBackBooking, img7DaysReplace, imgFreeDelivery;
    Button btnBuyNow, btnAddCart, btnPinCode;
    TextView txtKnowMore, txtBrandModel, txtPrice, txtRamRoM, txtDisplayBook, txtRear, txtFront, txtBattery, txtWarranty, txtPinCode, txtAvailOffer;
    Dialog dialog7Days, dialogCashOnDelivry;
    TextView txt7DaysOk, txtFreeDelivry, txtProcessorBook, txtWarrantyMOnthBok, txtCartBook;
    EditText edtPin;
    ImageView img1;
    CardView cardProtection;
    int backCoverPrice, temperedPrice, protectionPrice;
    String BrandName, ModelName;
    private ShimmerFrameLayout mShimmerViewContainer;
    NestedScrollView nestedBookNew;
    String prod_ID;

    int quantity = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_new);
        mShimmerViewContainer = findViewById(R.id.shimmer_BookNew);
        nestedBookNew = findViewById(R.id.nestedBookNew);
        mViewPager = findViewById(R.id.book_viewpagers);
        dots_indicator = findViewById(R.id.dots_indicator);
        txtKnowMore = findViewById(R.id.txtKnowMore);
        txtBrandModel = findViewById(R.id.txtBrandModel);
        txtPrice = findViewById(R.id.txtPrice);
        txtRamRoM = findViewById(R.id.txtRamRoM);
        txtDisplayBook = findViewById(R.id.txtDisplayBook);
        txtRear = findViewById(R.id.txtRear);
        txtFront = findViewById(R.id.txtFront);
        txtBattery = findViewById(R.id.txtBattery);
        txtWarranty = findViewById(R.id.txtWarranty);
        txtPinCode = findViewById(R.id.txtPinCode);
        txtAvailOffer = findViewById(R.id.txtAvailOffer);
        cardProtection = findViewById(R.id.cardProtection);
        img1 = findViewById(R.id.img1);
        rvAvailble = findViewById(R.id.rvAvailble);
        rvPayment = findViewById(R.id.rvPayment);
        linearAdd = findViewById(R.id.linearAddBook);
        imgCheck = findViewById(R.id.imgCheckBook);
        imgBackBooking = findViewById(R.id.imgBackBooking);
        img7DaysReplace = findViewById(R.id.img7DaysReplace);
        imgFreeDelivery = findViewById(R.id.imgFreeDelivery);
        edtPin = findViewById(R.id.edtPin);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnAddCart = findViewById(R.id.btnAddCart);
        btnPinCode = findViewById(R.id.btnPinCode);
        txtProcessorBook = findViewById(R.id.txtProcessorBook);
        txtWarrantyMOnthBok = findViewById(R.id.txtWarrantyMOnthBok);
        txtCartBook = findViewById(R.id.txtCartBook);
        dialog7Days = new Dialog(BookNewActivity.this);
        dialog7Days.setContentView(R.layout.dialog7days);
        txt7DaysOk = dialog7Days.findViewById(R.id.txt7DaysOk);
        dialogCashOnDelivry = new Dialog(BookNewActivity.this);
        dialogCashOnDelivry.setContentView(R.layout.dialog_cash_on_delivery);
        txtFreeDelivry = dialogCashOnDelivry.findViewById(R.id.txtFreeDelivry);
        sessonManager = new SessonManager(BookNewActivity.this);
        if (!sessonManager.getPinCode().isEmpty()) {
            btnPinCode.setText("CHANGE");
            edtPin.setText(sessonManager.getPinCode());
            txtPinCode.setText("Delivery Available");
            txtPinCode.setTextColor(Color.parseColor("#65EB2F"));
        }

        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            txtCartBook.setVisibility(View.VISIBLE);
            txtCartBook.setText(sessonManager.getQty());
        }

        if (getIntent().hasExtra("id")) {
            String id = getIntent().getStringExtra("id");
            prod_ID = id;
            Log.d("sakdpoiu", id);
            hitApi(id);
        }

        onClick();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
//            txtCartBook.setVisibility(View.VISIBLE);
//            txtCartBook.setText(sessonManager.getQty());
//        }else {
//            txtCartBook.setVisibility(View.GONE);
//        }
        hitApi(sessonManager.getProductIdBook());

    }
    private void hitApi(String id) {
        Call<BookingStatusModel> call = api.getBookingDetail(Api.key, id,sessonManager.getToken());
        call.enqueue(new Callback<BookingStatusModel>() {
            @Override
            public void onResponse(Call<BookingStatusModel> call, Response<BookingStatusModel> response) {

                if (response.isSuccessful()) {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    nestedBookNew.setVisibility(View.VISIBLE);
                    BookingStatusModel bookingStatusModel = response.body();
                    if (bookingStatusModel.getQty() > 0) {
                        btnAddCart.setText("GO TO CART");
                        quantity = bookingStatusModel.getQty();
                    }
                    if (bookingStatusModel.getProtectionPrice() == 0) {
                        cardProtection.setVisibility(View.GONE);
                    }
                    backCoverPrice = bookingStatusModel.getBackcoverPrice();
                    temperedPrice = bookingStatusModel.getTemperedPrice();
                    protectionPrice = bookingStatusModel.getProtectionPrice();
                    BrandName = bookingStatusModel.getBrandName();
                    ModelName = bookingStatusModel.getModelName();


                    txtBrandModel.setText(bookingStatusModel.getBrandName() + " " + bookingStatusModel.getModelName() + " (" + bookingStatusModel.getGb() + "GB)");
                    txtWarranty.setText("Warranty : " + bookingStatusModel.getWarrenty());
                    txtPrice.setText("" + bookingStatusModel.getSaleAmount());
                    txtRamRoM.setText("* ROM : " + bookingStatusModel.getGb() + "GB");
                    txtDisplayBook.setText("* Screen Size : " + bookingStatusModel.getMobileDisplay());
                    txtRear.setText("* Rear Camera : " + bookingStatusModel.getRearCamera());
                    txtFront.setText("* Front Camera : " + bookingStatusModel.getFrontCamera());
                    txtBattery.setText("* Battery : " + bookingStatusModel.getBattery());
                    if (bookingStatusModel.getProcessor() != null) {
                        txtProcessorBook.setText("* Processor :  " + bookingStatusModel.getProcessor());
                    } else {
                        txtProcessorBook.setVisibility(View.GONE);
                    }
                    if (bookingStatusModel.getWarrentyMonth() != null) {
                        txtWarrantyMOnthBok.setText("* Warranty : " + bookingStatusModel.getWarrentyMonth());
                    } else {
                        txtWarrantyMOnthBok.setVisibility(View.GONE);
                    }

                    if (bookingStatusModel.getBannerImage() != null) {
                        Picasso.get().load(bookingStatusModel.getBannerImage()).into(img1);
                    } else {
                        img1.setVisibility(View.GONE);
                    }

                    listBanner = bookingStatusModel.getImages();
                    viewPager();

                    listOffer = bookingStatusModel.getAvailableoffer();
                    if (listOffer.size() == 0) {
                        rvAvailble.setVisibility(View.GONE);
                        txtAvailOffer.setVisibility(View.GONE);
                    } else {
                        setRVOffer();
                    }

                    listPayment = bookingStatusModel.getPaymentpolicy();
                    setRvPayment();
                }
            }

            @Override
            public void onFailure(Call<BookingStatusModel> call, Throwable t) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                Toast.makeText(BookNewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRvPayment() {
        rvPayment.setLayoutManager(new GridLayoutManager(BookNewActivity.this, 1));
        rvPayment.setAdapter(new PaymentAdapter(BookNewActivity.this, listPayment));
    }

    private void onClick() {
        txtCartBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessonManager.setAddressIntent("buyPhone");
                sessonManager.setProductIdBook(prod_ID);
                startActivity(new Intent(BookNewActivity.this, CartActivity.class)
                .putExtra("Intent","BookMob"));
            }
        });
        img7DaysReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog7Days.show();
            }
        });
        txt7DaysOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog7Days.dismiss();
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
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtPinCode.getText().toString().equals("Delivery Available")) {
                        if (backCoverPrice == 0 && temperedPrice == 0) {
                            if (btnAddCart.getText().toString().equalsIgnoreCase("GO TO CART")) {
                                startActivity(new Intent(BookNewActivity.this, AddressActivity.class));
                            }else {
                                hitAddCartBuyApi();
                            }

                        } else {
                            BotomSheetDialogFragmnt bottomSheet = new BotomSheetDialogFragmnt();
                            Bundle bundle = new Bundle();
                            bundle.putInt("back", backCoverPrice);
                            bundle.putInt("temperd", temperedPrice);
                            bundle.putString("brand", BrandName);
                            bundle.putString("model", ModelName);
                            bottomSheet.setArguments(bundle);
                            bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                        }

                } else {
                    Toast.makeText(BookNewActivity.this, "Please Verify the Pincode", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity == 0) {
                        if (txtPinCode.getText().toString().equals("Delivery Available")) {
                            if (btnAddCart.getText().toString().equalsIgnoreCase("GO TO CART")) {
                                startActivity(new Intent(BookNewActivity.this, CartActivity.class));
                            } else {
                                hitAddCartApi();
                            }

                        } else {
                            Toast.makeText(BookNewActivity.this, "Please Verify the Pincode", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                             sessonManager.setProductIdBook(prod_ID);
                              startActivity(new Intent(BookNewActivity.this,CartActivity.class)
                                .putExtra("Intent","BookMob"));
                    }

            }
        });


        linearAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearAdd.setVisibility(View.GONE);
                imgCheck.setVisibility(View.VISIBLE);
            }
        });
        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCheck.setVisibility(View.GONE);
                linearAdd.setVisibility(View.VISIBLE);
            }
        });
        imgBackBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        txtKnowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookNewActivity.this, KnowMoreActivity.class));
            }
        });

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

    }

    private void hitAddCartApi() {
        showProgress(BookNewActivity.this);
        Call<AddCartModel> call = api.addCart(Api.key, prod_ID, "mobile", 1, sessonManager.getToken(), protectionPrice, backCoverPrice, temperedPrice);
        call.enqueue(new Callback<AddCartModel>() {
            @Override
            public void onResponse(Call<AddCartModel> call, Response<AddCartModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    AddCartModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                        Toast.makeText(BookNewActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                        btnAddCart.setText("GO TO CART");

                        txtCartBook.setVisibility(View.VISIBLE);
                        sessonManager.setQty(StatusModel.getTotalqty());
                        txtCartBook.setText(sessonManager.getQty());

                        sessonManager.setAddressIntent("phoneDetail");
                        sessonManager.setProductIdBook(prod_ID);

                    } else {
                        Toast.makeText(BookNewActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddCartModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(BookNewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void hitAddCartBuyApi() {
        showProgress(BookNewActivity.this);
        Call<AddCartModel> call = api.addCart(Api.key, prod_ID, "mobile", 1, sessonManager.getToken(), protectionPrice, backCoverPrice, temperedPrice);
        call.enqueue(new Callback<AddCartModel>() {
            @Override
            public void onResponse(Call<AddCartModel> call, Response<AddCartModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    AddCartModel StatusModel = response.body();
                    if (StatusModel.getCode().equals("200")) {
                        Toast.makeText(BookNewActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();

                        txtCartBook.setVisibility(View.VISIBLE);
                        sessonManager.setQty(StatusModel.getTotalqty());
                        txtCartBook.setText(sessonManager.getQty());

                        btnAddCart.setText("GO TO CART");
                        startActivity(new Intent(BookNewActivity.this, AddressActivity.class));


                    } else {
                        Toast.makeText(BookNewActivity.this, "" + StatusModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddCartModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(BookNewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    private void hitPinCodeApi() {
        showProgress(BookNewActivity.this);
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
                Toast.makeText(BookNewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRVOffer() {

        rvAvailble.setLayoutManager(new GridLayoutManager(BookNewActivity.this, 1));
        rvAvailble.setAdapter(new AvailableOfferAdapter(BookNewActivity.this, listOffer));
    }

    public void viewPager() {

        SlideAdapter adapterView = new SlideAdapter(BookNewActivity.this, listBanner);
        mViewPager.setAdapter(adapterView);
        dots_indicator.setViewPager(mViewPager);
    }


    public class SlideAdapter extends PagerAdapter {
        private ArrayList<BookingBannerModel> images;
        private LayoutInflater inflater;
        private Context context;

        public SlideAdapter(Context context, ArrayList<BookingBannerModel> images) {
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
            Picasso.get().load(images.get(position).getImage()).placeholder(R.drawable.app_logo).into(myImage);
            view.addView(myImageLayout, 0);
            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

    @Override
    public void onButtonClicked(String text) {
        //  mTextView.setText(text);
    }

    @Override
    public void onBackPressed() {
        if(sessonManager.getType().equals("banner")){
            startActivity(new Intent(BookNewActivity.this,ProductActivityBanner.class)
                    .putExtra("id",""+sessonManager.getProductId()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }else {
            startActivity(new Intent(BookNewActivity.this,ProductActivity.class)
                    .putExtra("id",""+sessonManager.getProductId())
                    .putExtra("type",""+sessonManager.getType()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
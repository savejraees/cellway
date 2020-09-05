package com.cellway.Cellway.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.model.BookModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Book extends Fragment {

    View view;
    Button bookMobile;
    ImageView mainImage;
    String imageId, Brand, Model, Gb, Price, warrantymonth,warranty,remark,condition;
    ArrayList<BookModel> mobileImagelist;
    //    ArrayList<Product_Description> listProduct;
    RecyclerView rvBookImage;

    AlertDialog.Builder builder;
    BookRecycleAdapter bookRecycleAdapter;
    TextView BrandName, ModelName, ModelGB, original_price, warrantyMonth,remarkProduct,tvcondition;
    ProgressDialog progressDialog;

    Toolbar toolbarbook;
    EditText bookName, bookPincode, bookHouseNo, bookRoad, bookCity, bookState,bookContact,
            bookEmail;
    CheckBox previous_address;
    ProgressBar progressbarbook;
    String pbrand_id,imgMobile;
    int positionOfImage;


    public Book() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        ((Home) getActivity()).getSupportActionBar().setTitle("Your Booking");
        view = inflater.inflate(R.layout.fragment_book, container, false);
        mobileImagelist = new ArrayList<>();
        pbrand_id = getArguments().getString("pBrandId");


//        listProduct = (ArrayList<Product_Description>) getArguments().getSerializable("productDetails");
        rvBookImage = view.findViewById(R.id.rvBookImage);
        toolbarbook = view.findViewById(R.id.booktoolbar);
        progressbarbook = view.findViewById(R.id.progressbarbook);
        remarkProduct = view.findViewById(R.id.remarkProduct);
        tvcondition = view.findViewById(R.id.condition);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Your Booking");
        toolbarbook.setNavigationIcon(R.drawable.arrow_back);
        toolbarbook.setTitle("Your Booking");
        toolbarbook.setTitleTextColor(Color.WHITE);
        toolbarbook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
               // Bundle bundle = new Bundle();
              //  bundle.putString("pBrandId",pbrand_id);

            }
        });

        imageId = getArguments().getString("ImageId");
        Brand = getArguments().getString("brand");
        Model = getArguments().getString("model");
        Gb = getArguments().getString("gb");
        Price = getArguments().getString("price");
        warrantymonth = getArguments().getString("warrantyMonth");
        warranty = getArguments().getString("warranty");
        imgMobile = getArguments().getString("image");
        remark = getArguments().getString("remark");
        condition = getArguments().getString("condition");

        Log.d("abcasd", imageId + " " + Brand + " " + Model + " " + Gb + " " + Price + " " + warrantymonth);

        bookMobile = view.findViewById(R.id.bookMobile);

        builder = new AlertDialog.Builder(getActivity());
        //imageview
        mainImage = view.findViewById(R.id.mainImage);

        BrandName = view.findViewById(R.id.BrandName);
        ModelName = view.findViewById(R.id.ModelName);
        ModelGB = view.findViewById(R.id.ModelGB);
        original_price = view.findViewById(R.id.original_price);
        warrantyMonth = view.findViewById(R.id.warrantyMonth);
        BrandName.setText(Brand);
        ModelName.setText(Model);
        ModelGB.setText(Gb);
        original_price.setText(Price);
        if(warranty.equals("out")){
            warrantyMonth.setText(warranty);
        }else{
            warrantyMonth.setText(warrantymonth);
        }
        remarkProduct.setText(remark);
        tvcondition.setText(condition);


        hitImageApi();


        bookMobile.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(final View view) {


                                              builder.setMessage("Your Gadget will be booked after paying booking amount of Rs. 2000/-.\n" +
                                                      "For Bank Details please visit 'More tab' in side menu.\n" +
                                                      "After paying Booking amount send the Screenshoot of payment.\n" +
                                                      "Your booking will be confirmed in 20 min of payment.\n" +
                                                      "For Home Delivery and COD visit FAQs in 'MORE tab' of side menu.")
                                                      .setCancelable(true)
                                                      .setPositiveButton("store Pickup", new DialogInterface.OnClickListener() {
                                                          public void onClick(DialogInterface dialog, int id) {
                                                              // View view1 = view;
                                                              openWhatsAppStorePickup(view);


                                                          }
                                                      }).setNegativeButton("Home Delivery", new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialogInterface, int i) {
                                                      // openWhatsApp(view);
                                                      dialogAddress();
                                                  }
                                              });
                                              //Creating dialog box
                                              AlertDialog alert = builder.create();
                                              //Setting the title manually
                                              alert.setTitle("Your Booking");
                                              alert.show();

                                          }
                                      }
        );


        return view;
    }

    private void dialogAddress() {
        final Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Light_NoTitleBar_Fullscreen);
      //  final Dialog dialog = new Dialog(getActivity());
      //  dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.row_dialog_address);

        bookPincode = dialog.findViewById(R.id.bookPincode);
        bookHouseNo = dialog.findViewById(R.id.bookHouseNo);
        bookRoad = dialog.findViewById(R.id.bookRoad);
        bookCity = dialog.findViewById(R.id.bookCity);
        bookState = dialog.findViewById(R.id.bookState);
        bookContact = dialog.findViewById(R.id.bookContact);
        bookEmail = dialog.findViewById(R.id.bookEmail);
        bookName = dialog.findViewById(R.id.bookName);
        previous_address = dialog.findViewById(R.id.previous_address);
        Button bookSubmitButton = dialog.findViewById(R.id.bookSubmitButton);
        dialog.show();
        previous_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(previous_address.isChecked()){
                    SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("Address", Context.MODE_PRIVATE);
                    String pincode = sharedPreferences1.getString("pincode", "");
                    String houseNo = sharedPreferences1.getString("houseNo", "");
                    String landmark = sharedPreferences1.getString("landmark", "");
                    String city = sharedPreferences1.getString("city", "");
                    String state = sharedPreferences1.getString("state", "");
                    String email = sharedPreferences1.getString("email", "");
                    bookPincode.setText(pincode);
                    bookHouseNo.setText(houseNo);
                    bookRoad.setText(landmark);
                    bookCity.setText(city);
                    bookState.setText(state);
                    bookEmail.setText(email);
                }
                if(!previous_address.isChecked()){
                    bookPincode.setText("");
                    bookHouseNo.setText("");
                    bookRoad.setText("");
                    bookCity.setText("");
                    bookState.setText("");
                    bookEmail.setText("");
                    bookPincode.setHintTextColor(Color.parseColor("#808080"));
                    bookHouseNo.setHintTextColor(Color.parseColor("#808080"));
                    bookRoad.setHintTextColor(Color.parseColor("#808080"));
                    bookCity.setHintTextColor(Color.parseColor("#808080"));
                    bookState.setHintTextColor(Color.parseColor("#808080"));
                    bookEmail.setHintTextColor(Color.parseColor("#808080"));
                    bookPincode.setHint("Pincode*");
                    bookHouseNo.setHint("House No., Building Name*");
                    bookRoad.setHint("Road Name, Area, Colony*");
                    bookCity.setHint("City*");
                    bookState.setHint("State*");
                    bookEmail.setHint("Email*");
                }

            }
        });

        bookSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookPincode.getText().toString().isEmpty()) {
                   // bookPincode.setError("Enter Your Pincode");
                    Toast.makeText( getContext(), "Enter Your Pincode", Toast.LENGTH_SHORT ).show();
                    bookPincode.requestFocus();
                }
                else if (bookName.getText().toString().isEmpty()) {
                   // bookName.setError("Enter Your Name");
                    Toast.makeText( getContext(), "Enter Your Name", Toast.LENGTH_SHORT ).show();
                    bookName.requestFocus();
                }
                else if (bookHouseNo.getText().toString().isEmpty()) {
                   // bookHouseNo.setError("Enter Your House No.");
                    Toast.makeText( getContext(), "Enter Your House No.", Toast.LENGTH_SHORT ).show();
                    bookHouseNo.requestFocus();
                } else if (bookRoad.getText().toString().isEmpty()) {
                  //  bookRoad.setError("Enter Your Area");
                    Toast.makeText( getContext(), "Enter Your Area", Toast.LENGTH_SHORT ).show();
                    bookRoad.requestFocus();
                } else if (bookCity.getText().toString().isEmpty()) {
                  //  bookCity.setError("Enter Your City");
                    Toast.makeText( getContext(), "Enter Your City", Toast.LENGTH_SHORT ).show();
                    bookCity.requestFocus();
                } else if (bookState.getText().toString().isEmpty()) {
                   // bookState.setError("Enter Your State");
                    Toast.makeText( getContext(), "Enter Your State", Toast.LENGTH_SHORT ).show();
                    bookState.requestFocus();
                }
                else if (bookContact.getText().toString().isEmpty()) {
                   // bookContact.setError("Enter Your Contact");
                    Toast.makeText( getContext(), "Enter Your Contact", Toast.LENGTH_SHORT ).show();
                    bookContact.requestFocus();
                }
                else if (bookEmail.getText().toString().isEmpty()) {
                   // bookEmail.setError("Enter Your Email");
                    Toast.makeText( getContext(), "Enter Your Email", Toast.LENGTH_SHORT ).show();
                    bookEmail.requestFocus();
                }

                else {
                    dialog.dismiss();
                    dialogTermCondition();
                }

            }
        });
    }

    private void dialogTermCondition() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.row_dialog_term);
        Button okTermButton = dialog.findViewById(R.id.okTermButton);
        dialog.show();
        okTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openWhatsAppHomeDelivery(view);
            }
        });
    }

    public void openWhatsAppHomeDelivery(View view) {

        String toNumber = "+91 8278543443";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" +"Name: " +bookName.getText().toString()+"\n" + "Address: " + "\n" + bookHouseNo.getText().toString()
                + "\n" + bookRoad.getText().toString() + "\n" + bookCity.getText().toString() + "\n" + bookState.getText().toString()
                + "(" + bookPincode.getText().toString() + ")." +"\n"+bookContact.getText().toString()+"\n" +"Email : "+bookEmail.getText().toString()+"\n"+ "\n\n" + "Your phone Id is: " + imageId +"\n"+"Model : "+ Model));

        startActivity(intent);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Address", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("pincode", bookPincode.getText().toString()).apply();
        sharedPreferences.edit().putString("houseNo", bookHouseNo.getText().toString()).apply();
        sharedPreferences.edit().putString("landmark", bookRoad.getText().toString()).apply();
        sharedPreferences.edit().putString("city", bookCity.getText().toString()).apply();
        sharedPreferences.edit().putString("state", bookState.getText().toString()).apply();


    }

    public void openWhatsAppStorePickup(View view) {

        String toNumber = "+91 8278543443";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + "Your phone Id is: " + imageId +"\n"+"Model : "+Model));
        startActivity(intent);

    }


    private void hitImageApi() {
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();

      //  ProgressDialog progressDialog = new ProgressDialog(getContext(),R.style.AppTheme_PopupOverlay);

        progressbarbook.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Api.BookImage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbarbook.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BookModel object = new BookModel();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        object.setBookMobileImage(jsonObject1.getString("image"));
                        object.setBookimageId(jsonObject1.getString("id"));
                        mobileImagelist.add(object);
                        allImage();

                    }

                    if (!(mobileImagelist.size() == 0)) {
                        Picasso.get().load(imgMobile).into(mainImage);
                    }


                    Log.d("weuir", String.valueOf(mobileImagelist));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarbook.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("product_id", imageId);
                return hashMap;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(request);


    }

    private void allImage() {

        rvBookImage.setHasFixedSize(true);
        rvBookImage.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        bookRecycleAdapter = new BookRecycleAdapter(mobileImagelist, getActivity());
        rvBookImage.setAdapter(bookRecycleAdapter);


        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreenBook frag = new FullScreenBook();
                Bundle bundle = new Bundle();
                bundle.putInt("positionImage",positionOfImage);
                bundle.putParcelableArrayList("fullImage", mobileImagelist);
                frag.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, frag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }


    //////////////image adapter////////////

    public class BookRecycleAdapter extends RecyclerView.Adapter<BookRecycleAdapter.BookHolder> {

        ArrayList<BookModel> listModel;
        Context context;

        public BookRecycleAdapter(ArrayList<BookModel> listModel, Context context) {
            this.listModel = listModel;
            this.context = context;
        }


        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.row_book_image, parent, false);
            return new BookHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            Picasso.get().load(listModel.get(position).getBookMobileImage()).into(holder.row_ImgBook);

            Log.d("aserewd", listModel.get(position).getBookMobileImage());
        }

        @Override
        public int getItemCount() {
            return listModel.size();
        }

        public class BookHolder extends RecyclerView.ViewHolder {
            ImageView row_ImgBook;

            public BookHolder(@NonNull View itemView) {
                super(itemView);
                row_ImgBook = itemView.findViewById(R.id.row_ImgBook);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = getAdapterPosition();
                        positionOfImage = Integer.parseInt(String.valueOf(i));
                        BookModel object = mobileImagelist.get(getAdapterPosition());
                        String img = object.getBookMobileImage();
                        Picasso.get().load(img).into(mainImage);

                        //positionOfImage = mobileImagelist.indexOf(object);
                        Log.d("dsdsa", String.valueOf(positionOfImage));

                    }
                });
            }
        }
    }


}

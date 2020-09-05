package com.cellway.Cellway;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.util.VolleyMultipartRequest1;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Otp extends AppCompatActivity {

    Button otpSubmit;
    Button resend;
    Bundle bundle;
    String name, mobile, email,password,image,otp,mesage;
    Bitmap bitmap;
    EditText edt_OTP;
    TextView texttime;
    ProgressBar progressbarOtp;
    private long alog;
    //MyCountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpSubmit = findViewById(R.id.otpSubmit);
        edt_OTP = findViewById(R.id.edt_OTP);
        resend = findViewById(R.id.resend);
        progressbarOtp = findViewById(R.id.progressbarOtp);

        texttime=findViewById(R.id.texttime);

        bundle = getIntent().getExtras();
        name = bundle.getString("name");
        mobile = bundle.getString("mobile");
        email = bundle.getString("email");
        password = bundle.getString("password");
     //   image = bundle.getString("image");

    //  Log.d("poiuwq",image);
      hitUrlForOtp();
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                texttime.setText("Wait for otp: " + millisUntilFinished / 1000);

            }

            public void onFinish() {
                texttime.setText(" ");

                resend.setVisibility(View.VISIBLE);
            }
        }.start();



//        try {
//            if(!image.equals("empty")){
//              //  bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(image));
//
//                if (Build.VERSION.SDK_INT > 23) {
//                    Log.d("inelswe", "inelse");
//                    bitmap = handleSamplingAndRotationBitmap(getApplicationContext(), Uri.parse(image));
//
//                } else {
//
//                    Log.d("inelse", "inelse");
//
//                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(image));
//                    bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);
//
//                }
//
//            }else if(image.equals("empty")){
//
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
//                        R.drawable.app_logo);
//                bitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
//                Log.d("abcd", "inelse");
//            }
//
//           // Log.d("abcd", String.valueOf(bitmap));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_OTP.getText().toString().isEmpty()) {
                    edt_OTP.setError("Please enter Otp");
                } else if (!(edt_OTP.getText().toString().equals(otp))) {
                  //  startActivity(new Intent(Otp.this, Login.class));
                    Toast.makeText(Otp.this, "Please enter Valid Otp", Toast.LENGTH_SHORT).show();

                } else {
                  //  myCountDownTimer.cancel();
                    registrationSuccefull();
                    //myCountDownTimer.cancel();
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitUrlForOtp();

                resend.setVisibility(View.GONE);

                new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        texttime.setText("Wait for otp: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        texttime.setText(" ");

                        resend.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });


/*        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                texttime.setText("Wait for otp: " + millisUntilFinished / 1000);

            }

            public void onFinish() {
                texttime.setText(" ");

                resend.setVisibility(View.VISIBLE);
            }
        }.start();*/


    }

 /*   public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


            texttime.setText("Wait for otp: " + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            texttime.setText(" ");

            resend.setVisibility(View.VISIBLE);
        }
    }*/

    void registrationSuccefull() {

//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        progressbarOtp.setVisibility(View.VISIBLE);

        VolleyMultipartRequest1 request1 = new VolleyMultipartRequest1(Request.Method.POST, Api.register_Url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        Log.d("resposdesdfs",response.toString());
                        progressbarOtp.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray= new JSONArray(new String(response.data));
                            JSONObject jsonObject= jsonArray.getJSONObject(0);

                            Log.d("codehsdajs",jsonObject.getString("code"));

                            // Toast.makeText(Register.this, ""+msg, Toast.LENGTH_SHORT).show();
                            if (jsonObject.getString("code").equals("200")) {
                                Log.d("skldj","successdfjk");


                                startActivity(new Intent(Otp.this,Login.class));

                               //   startActivity(new Intent(Otp.this,Login.class));
                                Toast.makeText(Otp.this, "Registration Successfull", Toast.LENGTH_SHORT).show();

                              finish();
                            }

                            else{
                                Toast.makeText(Otp.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarOtp.setVisibility(View.GONE);
                Toast.makeText(Otp.this, "Application Work in Progress", Toast.LENGTH_SHORT).show();
                Log.d("error2sa",error.getMessage()+"error ");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("username", name);
                hashMap.put("mobile", mobile);
                if(email.equals("")){
                    hashMap.put("email", "");
                }else {
                    hashMap.put("email", email);
                }
                hashMap.put("password", password);
                hashMap.put("status", "1");
                Log.d("fdgdfg", String.valueOf(hashMap));
                return hashMap;
            }

//            @Override
//            protected Map<String, DataPart> getByteData() throws AuthFailureError {
//                HashMap<String,DataPart> imagepram= new HashMap<>();
//                VolleyMultipartRequest1.DataPart dataPart= new DataPart(System.currentTimeMillis() + ".png", convertBitmapToByteArray(bitmap));
//                imagepram.put("image", dataPart);
//                Log.d("image_SDsd",imagepram.toString());
//                return imagepram;
//            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        Volley.newRequestQueue(this).add(request1);


    }



    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
        return buffer.toByteArray();
    }



//        private void hitUrlForOtp(){
//            final ProgressDialog progressDialog = new ProgressDialog(Otp.this);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            otp = String.valueOf(randomOtp());
//            mesage = "Your verification code is "+otp;
//            Log.d("checkmdsotop",mesage);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.SENDOTP, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response)
//                {
//                    Log.d("chekclohin", response);
//
//                    progressDialog.dismiss();
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//
//                        if (jsonObject.getString("type").equals("success")){
//
//                            Toast.makeText(Otp.this, "OTP sent your mobile number", Toast.LENGTH_SHORT).show();
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
//                    Toast.makeText(Otp.this, "Something Wrong With Otp.", Toast.LENGTH_SHORT).show();
//
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    HashMap<String,String> hashMap = new HashMap<>();
//                    hashMap.put("authkey","290873ACnsgu9J5d5fd88f");
//                    hashMap.put("mobiles",mobile);
//                    hashMap.put("message",mesage);
//                    hashMap.put("sender","MSGOTP");
//                    hashMap.put("route","4");
//                    hashMap.put("country","91");
//                    Log.d("checkparams",hashMap.toString());
//                    return hashMap;
//                }
//            };
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    10000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//            RequestQueue requestQueue = Volley.newRequestQueue(Otp.this);
//            requestQueue.add(stringRequest);
//
//    }

    private void hitUrlForOtp(){
        otp = String.valueOf(randomOtp());
        mesage = "Your verification code is :"+otp;
        Log.d("msg",mesage);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.SENDOTP, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("chekclohin", response);

                Toast.makeText(getApplicationContext(), "OTP sent your mobile number", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("authkey","290873ACnsgu9J5d5fd88f");
                hashMap.put("message",mesage);
                hashMap.put("country", "91");
                hashMap.put("route", "106");
                hashMap.put("sender","MSGOTP");
                hashMap.put("mobiles",mobile);
                Log.d("checkparams",hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private int randomOtp(){
        Random rnd = new Random();
        int n = 1000 + rnd.nextInt(9000);
        return n;
    }




    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context, img, selectedImage);
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei = null;
        if (Build.VERSION.SDK_INT > 23) {
            ei = new ExifInterface(input);
        }


        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }


    }





    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }




}

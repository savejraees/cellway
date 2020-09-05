package com.cellway.Cellway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class Register extends AppCompatActivity {
    TextView tv_createAccount;
    Button buttRegister;
    CircleImageView img_register;
    Uri photo_path=null;
    EditText edt_Name, edt_mobile, edt_email, edt_password, edt_cnfmPassword;
    String name, mobile, email, password, confirmPassword;
    ProgressBar progressbarRegister;
    public static Bitmap bitmap;
    private static final int STORAGE_PERMISSION_CODE = 123;
    protected static final int GALLERY_PICTURE = 1;
    int PICK_IMAGE_MULTIPLE = 1;
    String msg;
  //  String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestStoragePermission();
        askForPermissioncamera(Manifest.permission.CAMERA,CAMERA);

        tv_createAccount = findViewById(R.id.tv_createAccount);
        buttRegister = findViewById(R.id.buttRegister);
        edt_Name = findViewById(R.id.edt_Name);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_cnfmPassword = findViewById(R.id.edt_cnfmPassword);
        img_register = findViewById(R.id.img_register);
        progressbarRegister = findViewById(R.id.progressbarRegister);

//        progressBar = new ProgressDialog(this);
//        progressBar.setCancelable(true);//you can cancel it by pressing back button
//        progressBar.setMessage("Please wait ...");
//      //  progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressBar.setIndeterminate(true);
//


        if (!isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }


        clickAll();
    }

    private void validationWithApi() {
        name = edt_Name.getText().toString();
        mobile = edt_mobile.getText().toString();
        email = edt_email.getText().toString();
        password = edt_password.getText().toString();
        confirmPassword = edt_cnfmPassword.getText().toString();

        if (!isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty()) {
            edt_Name.setError("Please enter Your Name");
        } /*else if (mobile.isEmpty()) {
            edt_mobile.setError("Please enter the Mobile No.");*/
        else if (edt_mobile.getText().toString().length()!=10) {
            edt_mobile.setError("Please enter 10 digit Mobile No.");
        } else if (password.isEmpty()) {
            edt_password.setError("Please enter the Password");
        } else if (confirmPassword.isEmpty()) {
            edt_cnfmPassword.setError("Please enter the  Confirm Password");
        } else if (!(confirmPassword.equals(password))) {
            edt_cnfmPassword.setError("Confirm Password should match with Password");
        }

            else {
            register();
        }
    }

    private void clickAll() {
        tv_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
        buttRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                validationWithApi();


            }
          /*  public byte[] convertBitmapToByteArray(Context context, Bitmap bitmap) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
              //  Log.d("sddd", String.valueOf(bitmap));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
                return buffer.toByteArray();
            }*/
        });

//        img_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_MULTIPLE);
//
//                }
//                else {
//                    Intent pictureActionIntent = null;
//                    pictureActionIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
//
//                }
//
//
//
//            }
//        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
////            if (requestCode == 0 && resultCode==RESULT_OK) {
////                photo_path = data.getData().toString();
////
////                    bitmap = MediaStore.Images.Media.getBitmap(Register.this.getContentResolver(), data.getData());
////                    img_register.setImageBitmap(bitmap);
////
////                Log.d("kRC", "" + img_register);
////
////
////            }
//            if ((resultCode == RESULT_OK && requestCode == GALLERY_PICTURE)) {
//                photo_path = data.getData();
//
//                if (Build.VERSION.SDK_INT > 23) {
//                    Log.d("inelswe", "inelse");
//                    bitmap = handleSamplingAndRotationBitmap(getApplicationContext(), photo_path);
//                    img_register.setImageBitmap(handleSamplingAndRotationBitmap(getApplicationContext(), photo_path));
//
//                } else {
//
//                    Log.d("inelse", "inelse");
//
//                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(String.valueOf(photo_path)));
//                    bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);
//                    Picasso.get().load(data.getDataString()).into(img_register);
//
//
//                }
//
//
//
//            }
//        }
//
//        catch(Exception e){
//        }
//    }


    void register() {
        progressbarRegister.setVisibility(View.VISIBLE);

//        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        VolleyMultipartRequest1 request1 = new VolleyMultipartRequest1(Request.Method.POST, Api.register_Url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        Log.d("fgfg", response.toString());
                        //  progressDialog.dismiss();
                        progressbarRegister.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(new String(response.data));
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            Log.d("codehajs", jsonObject.getString("code"));

                            // Toast.makeText(Register.this, ""+msg, Toast.LENGTH_SHORT).show();
                            if (jsonObject.getString("code").equals("401")) {
                                Log.d("already", "otp received");
                                Toast.makeText(Register.this, "Number already registred !", Toast.LENGTH_SHORT).show();

                            } else if (jsonObject.getString("code").equals("403")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("name", name);
                                bundle.putString("mobile", mobile);
                                if(email.equals("")){
                                    bundle.putString("email", "");
                                }else {
                                    bundle.putString("email", email);
                                }

                                bundle.putString("password", password);
//                                if(bitmap!=null){
//                                    bundle.putString("image", String.valueOf(photo_path));
//                                }
//                                if(bitmap==null){
//                                    bundle.putString("image", "empty");
//                                }
//
//
//                              Log.d("photopath==",String.valueOf(photo_path));

                            //    bundle.putString("otp", otp);
                                Intent intent = new Intent(getApplicationContext(), Otp.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                                Toast.makeText(Register.this, "OTP sent your mobile number", Toast.LENGTH_SHORT).show();
                                Log.d("new", "otp received");
                                //   hitUrlForOtp();

                            } else {
                                Toast.makeText(Register.this, "Something Wrong" + jsonObject.getString("code"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
                progressbarRegister.setVisibility(View.GONE);
                Toast.makeText(Register.this, "Appliaction Work in Progress", Toast.LENGTH_SHORT).show();
                Log.d("error2", error.getMessage() + "error ");

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
                hashMap.put("status", "0");
                Log.d("fdgdfg", String.valueOf(hashMap));
                return hashMap;
            }


//            @Override
//            protected Map<String, DataPart> getByteData() throws AuthFailureError {
//                HashMap<String, DataPart> imagepram = new HashMap<>();
//
//                if(bitmap==null){
//                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
//                            R.drawable.app_logo);
//                    Log.d("checkBitmap", String.valueOf(bitmap));
//                   // String nullValue = String.valueOf(R.drawable.app_icon_main);
//                    imagepram.put("image", new DataPart(System.currentTimeMillis() + ".png", convertBitmapToByteArray(bitmap)));
//                }
//                 else if(!(bitmap==null)){
//                    imagepram.put("image", new DataPart(System.currentTimeMillis() + ".png", convertBitmapToByteArray(bitmap)));
//
//                }
//                Log.d("image_SDsd", imagepram.toString());
//
//                return imagepram;
//            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.getCache().clear();
        Volley.newRequestQueue(Register.this).add(request1);

    }


    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
        return buffer.toByteArray();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
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
    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }



    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void askForPermissioncamera(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Register.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(Register.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(Register.this, new String[]{permission}, requestCode);
            }
        } else {
//            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }


    }

}

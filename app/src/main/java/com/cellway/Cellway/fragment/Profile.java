package com.cellway.Cellway.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.ApiFactory;
import com.cellway.Cellway.Home;
import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.util.VolleyMultipartRequest1;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    View view;
    CircleImageView imgProfile;

    String name, mbl, eml, img, userdId;
    EditText profileName, profileNumber, profileEmail, profilePincode, profileHouseNo, profileRoad, profileCity, profileState;
    Bitmap bitmap;
    Button updateButton;
    String imageUrl;
    static String checkbelow23path;

    ProgressBar progressbarProfile;
    public Profile() {
        // Required empty public constructor
    }

    /*
    Example comment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("My Profile");
        imgProfile = (CircleImageView) view.findViewById(R.id.imgProfile);
        updateButton = view.findViewById(R.id.updateButton);

        profileName = view.findViewById(R.id.profileNme);
        profileNumber = view.findViewById(R.id.profileNumber);
        profileEmail = view.findViewById(R.id.profileEmail);
        progressbarProfile = view.findViewById(R.id.progressbarProfile);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Cellways", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        mbl = sharedPreferences.getString("mbl", "");
        eml = sharedPreferences.getString("emal", "");
        img = sharedPreferences.getString("image", "");
        userdId = sharedPreferences.getString("userid", "");

//        decodeBase64(img);

//        Log.d("imgl",img);
        Log.d("ewf", name + " " + mbl + " " + eml + " " + userdId);



        profileName.setText(name);
        profileNumber.setText(mbl);

        profileEmail.setHintTextColor(Color.parseColor("#808080"));
        if(eml.equals(" ")){
            profileEmail.setHint("Email");
        }else{
            profileEmail.setText(eml);
        }

        if(!img.equals("")){
            Picasso.get().load(img).placeholder(R.drawable.app_logo).fit().centerInside()
                    .into(imgProfile);
        }
        else{
            Picasso.get().load(R.drawable.app_logo).fit().centerInside()
                    .into(imgProfile);
        }


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);

                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);

            }
        });

        updateButton();

        ////////////////////////////////////Address from booking ///////////////////////////////////////////////////////////////

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("Address", Context.MODE_PRIVATE);
        String pincode = sharedPreferences1.getString("pincode", "");
        String houseNo = sharedPreferences1.getString("houseNo", "");
        String landmark = sharedPreferences1.getString("landmark", "");
        String city = sharedPreferences1.getString("city", "");
        String state = sharedPreferences1.getString("state", "");

        profilePincode = view.findViewById(R.id.profilePincode);
        profileHouseNo = view.findViewById(R.id.profileHouseNo);
        profileRoad = view.findViewById(R.id.profileRoad);
        profileCity = view.findViewById(R.id.profileCity);
        profileState = view.findViewById(R.id.profileState);

        profilePincode.setText(pincode);
        profileHouseNo.setText(houseNo);
        profileRoad.setText(landmark);
        profileCity.setText(city);
        profileState.setText(state);
        return view;
    }

    private void updateButton() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileName.getText().toString().isEmpty()) {
                    profileName.setError("!Enter the name");
                } else if (profileNumber.getText().toString().isEmpty()) {
                    profileNumber.setError("!Enter the Mobile No.");
                } else if (bitmap == null) {
                    Toast.makeText(getActivity(), "please upload the image", Toast.LENGTH_SHORT).show();
                } else {
                    hitUpdateApi();
                }
            }
        });
    }

    private void hitUpdateApi() {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        progressbarProfile.setVisibility(View.VISIBLE);

        final VolleyMultipartRequest1 request1 = new VolleyMultipartRequest1(Request.Method.POST, Api.updateProfile, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressbarProfile.setVisibility(View.GONE);
                Log.d("welcewwe", String.valueOf(response));
                try {
                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.d("welcewwe", jsonObject.getString("code"));
                    if (jsonObject.getString("code").equals("200")) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Cellways", Context.MODE_PRIVATE);
//                        sharedPreferences.edit().putString("userid", userdId).apply();
                        sharedPreferences.edit().putString("name", profileName.getText().toString()).apply();
                        sharedPreferences.edit().putString("emal", profileEmail.getText().toString()).apply();
//                        sharedPreferences.edit().putString("mbl", profileNumber.getText().toString()).apply();
                        sharedPreferences.edit().putString("image", jsonObject.getString("image")).apply();
                      //  sharedPreferences.edit().putString("image", encodeTobase64(bitmap)).apply();
                        startActivity(new Intent(getActivity(), Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("asdawqsa", String.valueOf(error));
                progressbarProfile.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Api.key);
                hashMap.put("name", profileName.getText().toString());
                hashMap.put("userid", userdId);
                if(profileEmail.getText().toString().equals("")){
                    hashMap.put("email", " ");
                }else{
                    hashMap.put("email", profileEmail.getText().toString());
                }

                return hashMap;

            }

            protected Map<String, DataPart> getByteData() {
                HashMap<String, DataPart> imageParam = new HashMap<>();
                VolleyMultipartRequest1.DataPart dataPart = new DataPart(System.currentTimeMillis() + "png", convertBitmapToByteArray(bitmap));
                imageParam.put("image", dataPart);
                Log.d("wesduio", String.valueOf(imageParam));
                return imageParam;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        Volley.newRequestQueue(getActivity()).add(request1);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(data.getDataString().isEmpty()){
//           Profile frag = new Profile();
//           FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//           fragmentTransaction.addToBackStack(null);
//           fragmentTransaction.replace(R.id.frame,frag);
//           fragmentTransaction.commit();
//          //  getActivity().onBackPressed();
//        }

          //  imageUrl=data.getDataString().toString();
        if(requestCode==0){
            try {
                if (Build.VERSION.SDK_INT > 23) {

                    bitmap = handleSamplingAndRotationBitmap(getActivity(), data.getData());
                    bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);
                    imgProfile.setImageBitmap(handleSamplingAndRotationBitmap(getActivity(), data.getData()));
                } else {

                    Log.d("inelse", "inelse");

                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(data.getDataString().toString()));
                    bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);
                    Picasso.get().load(data.getDataString()).into(imgProfile);

                   /* BitmapFactory.Options options = new BitmapFactory.Options();

                    checkbelow23path = getRealPathFromURI(data.getData());
                    options.inSampleSize = 4;
                    options.inPurgeable = true;
                    Bitmap bm = BitmapFactory.decodeFile(checkbelow23path, options);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                    imgProfile.setImageBitmap(bm);*/
                    //  String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


                }


            } catch (Exception e) {
        }

            }
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
        
        
        
       /* else {
           // ei = new ExifInterface(selectedImage.getPath());

           // String str=selectedImage.getPath();

            Log.d("elsecondition","elsecondition");
           // Log.d("elsecondition12",selectedImage);


             ei = new ExifInterface(checkbelow23path);

        }*/

      
    }
    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }




    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
        return buffer.toByteArray();
    }

}
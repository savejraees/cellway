package com.cellway.Cellway;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.activity.BaseActivity;
import com.cellway.Cellway.activity.CartActivity;
import com.cellway.Cellway.fragment.About_us;
import com.cellway.Cellway.fragment.Contact_us;
import com.cellway.Cellway.fragment.HomePage;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.renderscript.Element;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.cellway.Cellway.fragment.HomePageNew;
import com.cellway.Cellway.fragment.More;
import com.cellway.Cellway.fragment.MyOrder;
import com.cellway.Cellway.fragment.Product;
import com.cellway.Cellway.fragment.Profile;
import com.cellway.Cellway.fragment.RepairGadget;
import com.cellway.Cellway.fragment.Your_Requirement;
import com.cellway.Cellway.updateChecker.GooglePlayStoreAppVersionNameLoader;
import com.cellway.Cellway.updateChecker.WSCallerVersionListener;
import com.cellway.Cellway.util.SessonManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, WSCallerVersionListener {

    Context context;
    Toolbar toolbar;

    CircleImageView profile_image;
    DrawerLayout drawer;
//    ListView lv;
//    ArrayAdapter itemsAdapter;
//    AlertDialog.Builder alertDialog;
    TextView headerName;
    String img,sharedName,userid;
    SharedPreferences sharedPreferences;
    public static int counterBack=1;
    FragmentManager fragmentManager;
    boolean isForceUpdate = true;
    RelativeLayout relative_cart;
    TextView TvCartQty;
    ImageView location,notification_icon;
    SessonManager sessonManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessonManager = new SessonManager(Home.this);

        new GooglePlayStoreAppVersionNameLoader(getApplicationContext(), this).execute();

        Log.d("asda",img+" "+sharedName);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HomePageNew frag = new HomePageNew();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame,frag);
        fragmentTransaction.commit();

        sharedPreferences = getSharedPreferences("Cellways", Context.MODE_PRIVATE);
        img = sharedPreferences.getString("image", "");
        sharedName = sharedPreferences.getString("name","");
        userid = sharedPreferences.getString("userid","");




//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frame, new HomePage());
//        ft.commit();



//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {


    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {


        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
});


        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        profile_image = navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        headerName = navigationView.getHeaderView(0).findViewById(R.id.headerName);

        if(!img.equals("")){
            Picasso.get().load(img).fit().centerInside()
                    .placeholder(R.drawable.app_logo)
                    .into(profile_image);
        }else if(img.equals("")){
            Picasso.get().load(R.drawable.app_logo)
                    .into(profile_image);
        }


//        Picasso.get().load(R.drawable.profile).into(profile_image);
        headerName.setText(sharedName);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile fragment = new Profile();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                hitProfileApi();
               drawer.closeDrawer(Gravity.LEFT);


            }
        });



    }

    private void hitProfileApi() {
//        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.getProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//               progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");
                    String name = jsonObject.getString("name");
                    String mobile = jsonObject.getString("mobile");
                    String email = jsonObject.getString("email");
                    String image = jsonObject.getString("image");


                    if(code.equals("200")){
                        sharedPreferences.edit().putString("userid", userid).apply();
                        sharedPreferences.edit().putString("name",name).apply();
                        sharedPreferences.edit().putString("emal",email).apply();
                        sharedPreferences.edit().putString("mbl",mobile).apply();
                        sharedPreferences.edit().putString("image",image).apply();
                        Profile fragment = new Profile();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//             progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String,String> getParams() {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("key",Api.key);
                hashMap.put("userid",userid);
                return hashMap;
        }
        };
        requestQueue.add(request);

    }

    @Override
    public void onBackPressed() {

        Product frag = new Product();


            ////for drawer///////
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//        else if(fragmentManager.getBackStackEntryCount()>0){
//            fragmentManager.popBackStackImmediate();
//        }
        else {
            super.onBackPressed();
//            backPresed = new AlertDialog.Builder(Home.this);
//            backPresed.setMessage("do you want To close this application")
//                    .setCancelable(false)
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
//                        }
//                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
//                }
//            });
//
//            AlertDialog alertDialog = backPresed.create();
//            alertDialog.show();

        }

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            TvCartQty.setVisibility(View.VISIBLE);
            TvCartQty.setText(sessonManager.getQty());
        }else {
            TvCartQty.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        MenuItem item = menu.findItem(R.id.action_viewcart);

        MenuItemCompat.setActionView(item,R.layout.badge_menu);



        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        relative_cart = (RelativeLayout) notifCount.findViewById(R.id.relative_cart);
        location = (ImageView) notifCount.findViewById(R.id.location_icon);
        notification_icon = (ImageView) notifCount.findViewById(R.id.notification_icon);
        TvCartQty = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);

        if ((!sessonManager.getQty().isEmpty()&&!sessonManager.getToken().isEmpty())) {
            TvCartQty.setVisibility(View.VISIBLE);
            TvCartQty.setText(sessonManager.getQty());
        }

        TvCartQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "Cellway";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=28.632772,77.281131 (" + name + ")"));
                startActivity(intent);
            }
        });
        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.action_viewcart) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
         startActivity(new Intent(Home.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

        }

        else if (id == R.id.nav_myOrder) {

            MyOrder fragment = new MyOrder();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        else if (id == R.id.nav_about) {
            About_us fragment = new About_us();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_share) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = "Your body here";
            String sub = "Your Subject";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent, "Share Using"));
            return true;


        } else if (id == R.id.nav_contact) {
            Contact_us fragment = new Contact_us();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        else if (id == R.id.nav_more) {

            More fragment = new More();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_Logout) {

            sessonManager.setToken("");
            Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onGetResponse(boolean isUpdateAvailable) {
        if (isUpdateAvailable) {
            showUpdateDialog();
        }
    }
    // popup for location

    public void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);

        alertDialogBuilder.setTitle(Home.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage(Home.this.getString(R.string.update_message));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Home.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForceUpdate) {
                    finish();
                }
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }



}

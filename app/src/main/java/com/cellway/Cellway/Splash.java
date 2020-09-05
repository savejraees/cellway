package com.cellway.Cellway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SharedPreferences sharedPreferences = getSharedPreferences("Cellways", Context.MODE_PRIVATE);
         id = sharedPreferences.getString("userid","");

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(3*1000);

                    if(id.isEmpty()){
                        Intent i=new Intent(getBaseContext(),Login.class);
                        startActivity(i);

                    }
                    else{
                        startActivity(new Intent(getApplicationContext(),Home.class));
                    }
                    // After 5 seconds redirect to another intent

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}

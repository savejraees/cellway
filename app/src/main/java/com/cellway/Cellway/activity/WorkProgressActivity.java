package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cellway.Cellway.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class WorkProgressActivity extends AppCompatActivity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_progress);



        MobileAds.initialize(this, "ca-app-pub-2957063050547520~9759537079");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
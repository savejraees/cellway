package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.adapter.AvailableOfferAdapter;
import com.cellway.Cellway.adapter.KeyFeatureAdapter;

import java.util.ArrayList;

public class KnowMoreActivity extends AppCompatActivity {

    ImageView imgBackKnow;
    RecyclerView rvKeyFeature;
    String[] listKeyFeature = {"Repair/Replace Broken Screen","Repair/Replace Water-Damaged Phone",
            "Repair for Hardware/Software Phone","One Number For All Your Queries",
            "Free Doorstep pick-up","2 Hassle Free Service"};
    ArrayList<String> listKey = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_more);
        imgBackKnow = findViewById(R.id.imgBackKnow);
        rvKeyFeature = findViewById(R.id.rvKeyFeature);
        setRVKeyFeature();
    }

    private void setRVKeyFeature() {
        for (int i = 0; i < listKeyFeature.length; i++) {
            listKey.add(listKeyFeature[i]);
        }
        rvKeyFeature.setLayoutManager(new GridLayoutManager(KnowMoreActivity.this, 1));
        rvKeyFeature.setAdapter(new KeyFeatureAdapter(KnowMoreActivity.this, listKey));
    }
}
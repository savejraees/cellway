package com.cellway.Cellway.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.activity.ProductActivity;
import com.cellway.Cellway.activity.ProductActivityBanner;
import com.cellway.Cellway.retrofitModel.BrandListModel.BrandDatum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MobileViewHolder> {

    Context context;
    ArrayList<BrandDatum> mdest;
    String type;

    public BrandAdapter(Context context, ArrayList<BrandDatum> mdest, String type) {
        this.context = context;
        this.mdest = mdest;
        this.type = type;
    }


    @NonNull
    @Override
    public BrandAdapter.MobileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_mobile, viewGroup, false);
        return new BrandAdapter.MobileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BrandAdapter.MobileViewHolder mobileViewHolder, final int position) {
        //Mobile_destination all = mdest.get(position);
        Picasso.get().load(mdest.get(position).getImage()).into(mobileViewHolder.grid_image);
        Log.d("ghi", mdest.get(position).getImage());
        mobileViewHolder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProductActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("type",type)
                        .putExtra("id",""+mdest.get(position).getId())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdest.size();
    }

    public class MobileViewHolder extends RecyclerView.ViewHolder {

        private ImageView grid_image;
        LinearLayout line;

        public MobileViewHolder(@NonNull View itemView) {
            super(itemView);

            grid_image = itemView.findViewById(R.id.grid_image);
            line = itemView.findViewById(R.id.line);


        }
    }

}

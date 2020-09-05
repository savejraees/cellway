package com.cellway.Cellway.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.activity.BookNewActivity;
import com.cellway.Cellway.retrofitModel.ProductModel.ProductDatumModel;
import com.cellway.Cellway.util.SessonManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Product_NewAdapter extends RecyclerView.Adapter<Product_NewAdapter.ProductViewHolder> {

    Context context;
    ArrayList<ProductDatumModel> arrProduct;
    String type = "banner";
    String ProductId;
    SessonManager sessonManager;
    public Product_NewAdapter(Context context, ArrayList<ProductDatumModel> arrProduct,String ProductId) {
        this.context = context;
        this.arrProduct = arrProduct;
        this.ProductId = ProductId;
        sessonManager = new SessonManager(context);
    }

    @NonNull
    @Override
    public Product_NewAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_your_mobile_dest, parent, false);
        return new Product_NewAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Product_NewAdapter.ProductViewHolder holder, int position) {
        final ProductDatumModel model = arrProduct.get(position);
        Picasso.get().load(model.getImage()).into(holder.img_mobile);
        holder.tv_brand.setText(model.getBrandName()+" "+model.getModelName());
        holder.tv_gb.setText("("+model.getGb()+"GB)");
        holder.deduct_price.setText(""+model.getSaleAmount()+"/-");
      //  holder.tv_ram_rom.setText(model.getGb()+"GB");
        holder.txtDeisplay.setText("Display Size : "+model.getMobileDisplay());
        holder.tv_battery.setText("Battery : "+model.getBattery());
        holder.tv_Warrenty.setText("Warranty : "+model.getWarrenty());
        holder.tv_rear_camera.setText("Rear Camera : "+model.getRearCamera());
        holder.tv_front_camera.setText("Front Camera : "+model.getFrontCamera());
        holder.tv_CategoryType.setText(""+model.getCategoryType());
        holder.linearMobileDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessonManager.setProductId(ProductId);
                sessonManager.setType(type);
                context.startActivity(new Intent(context,BookNewActivity.class)
                       .putExtra("id",""+model.getProductId()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img_mobile;
        TextView tv_Warrenty,tv_brand,tv_battery,tv_gb,deduct_price,tv_ram_rom,txtDeisplay,tv_rear_camera,tv_front_camera,tv_CategoryType;
        CardView linearMobileDest;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img_mobile = itemView.findViewById(R.id.img_mobile);
            tv_Warrenty = itemView.findViewById(R.id.tv_Warrenty);
            linearMobileDest = itemView.findViewById(R.id.linearMobileDest);
          //  tv_ram_rom = itemView.findViewById(R.id.tv_ram_rom);
            txtDeisplay = itemView.findViewById(R.id.txtDeisplay);
            tv_rear_camera = itemView.findViewById(R.id.tv_rear_camera);
            tv_front_camera = itemView.findViewById(R.id.tv_front_camera);
            tv_brand = itemView.findViewById(R.id.tv_brand);
            tv_battery = itemView.findViewById(R.id.tv_battery);
            tv_gb = itemView.findViewById(R.id.tv_gb);
            deduct_price = itemView.findViewById(R.id.deduct_price);
            tv_CategoryType = itemView.findViewById(R.id.tv_CategoryType);
            tv_CategoryType.setPaintFlags(tv_CategoryType.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}

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
import com.cellway.Cellway.activity.BookNewAccessoriesActivity;
import com.cellway.Cellway.activity.BookNewActivity;
import com.cellway.Cellway.retrofitModel.AccessoriesProductModel.AccessoriesProductDatumModel;
import com.cellway.Cellway.retrofitModel.ProductModel.ProductDatumModel;
import com.cellway.Cellway.util.SessonManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccessoriesProductAdapter extends RecyclerView.Adapter<AccessoriesProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<AccessoriesProductDatumModel> arrProduct;
    int catId, subCatId;
    SessonManager sessonManager;

    public AccessoriesProductAdapter(Context context, ArrayList<AccessoriesProductDatumModel> arrProduct, int catId, int subCatId) {
        this.context = context;
        this.arrProduct = arrProduct;
        this.catId = catId;
        this.subCatId = subCatId;

        sessonManager = new SessonManager(context);

    }

    @NonNull
    @Override
    public AccessoriesProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_product_access, parent, false);
        return new AccessoriesProductAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AccessoriesProductAdapter.ProductViewHolder holder, final int position) {
        //  final ProductDatumModel model = arrProduct.get(position);
        Picasso.get().load(arrProduct.get(position).getProductImage()).into(holder.img_mobile);
        holder.tv_brand.setText(arrProduct.get(position).getName());
        holder.deduct_price.setText("" + arrProduct.get(position).getPrice());
        holder.original_price_Access.setText("" + arrProduct.get(position).getMrp());
        int off = 100 - (arrProduct.get(position).getPrice() * 100) / arrProduct.get(position).getMrp();
        holder.tv_ColorAccess.setText(arrProduct.get(position).getColor());
        holder.txtOffAccess.setText(off + "%off");
        // holder.tv_brand.setText(model.getBrandName()+" "+model.getModelName());
        //  holder.deduct_price.setText(""+model.getSaleAmount()+"/-");

        holder.CardProductAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessonManager.setCatId(catId);
                sessonManager.setSubCatId(subCatId);
                context.startActivity(new Intent(context, BookNewAccessoriesActivity.class)
                        .putExtra("id", "" + arrProduct.get(position).getId()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img_mobile;
        TextView tv_brand, deduct_price, txtOffAccess, original_price_Access, tv_ColorAccess;
        CardView CardProductAccess;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img_mobile = itemView.findViewById(R.id.img_Product_acces);
            tv_brand = itemView.findViewById(R.id.tv_Name_Access);
            deduct_price = itemView.findViewById(R.id.deduct_price_Access);
            CardProductAccess = itemView.findViewById(R.id.CardProductAccess);
            txtOffAccess = itemView.findViewById(R.id.txtOffAccess);
            original_price_Access = itemView.findViewById(R.id.original_price_Access);
            tv_ColorAccess = itemView.findViewById(R.id.tv_ColorAccess);
            original_price_Access.setPaintFlags(original_price_Access.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}


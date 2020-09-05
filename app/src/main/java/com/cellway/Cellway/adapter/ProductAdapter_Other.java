package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.R;
import com.cellway.Cellway.fragment.Book;
import com.cellway.Cellway.model.Product_Description;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter_Other  extends RecyclerView.Adapter<ProductAdapter_Other.ProductViewHolder>{

    Context context;
    ArrayList<Product_Description> arrProduct;

    public ProductAdapter_Other(Context context, ArrayList<Product_Description> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }


    @NonNull
    @Override
    public ProductAdapter_Other.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_product,parent,false);
        return new ProductAdapter_Other.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter_Other.ProductViewHolder holder, int position) {
        Product_Description obj = arrProduct.get(position);
        Picasso.get().load(arrProduct.get(position).getOther_img_mobile()).into(holder.img_mobile);
        holder.deduct_price.setText(obj.getOther_deduct_price());
        holder.tv_brand.setText(obj.getOther_brand());
        holder.tv_model.setText(obj.getOther_model());
        holder.wrntyInOut.setText(obj.getOther_tv_wrnty());
        holder.tv_gb.setText(obj.getOther_gb());
//        holder.tv_size.setText(obj.getOther_tv_size());
//        holder.tv_battery.setText(obj.getOther_tv_battery());
//        holder.tv_rear_amera.setText(obj.getOther_tv_rear_amera());
//        holder.tv_front_amera.setText(obj.getTv_front_amera());


        holder.tv_size.setText("4 GB RAM I 64 GB Storage");
        holder.tv_battery.setText("14.73 cm (5.8 inch) Quad HD+ Display");
        holder.tv_rear_amera.setText("12 MP Rear Camera");
        holder.tv_front_amera.setText("8 MP Front Camera");

    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView img_mobile;
        TextView tv_brand,tv_model,tv_gb, deduct_price,
                tv_size,tv_battery,tv_rear_amera,tv_front_amera,wrntyInOut;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img_mobile = itemView.findViewById(R.id.img_mobile);
            deduct_price = itemView.findViewById(R.id.deduct_price);
            tv_brand = itemView.findViewById(R.id.tv_brand);
            tv_model = itemView.findViewById(R.id.tv_model);
            tv_gb = itemView.findViewById(R.id.tv_gb);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_battery = itemView.findViewById(R.id.tv_battery);
            tv_rear_amera = itemView.findViewById(R.id.tv_rear_amera);
            tv_front_amera = itemView.findViewById(R.id.tv_front_amera);
            wrntyInOut = itemView.findViewById(R.id.wrntyInOut);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = ((Home) context).getSupportFragmentManager();
                    Book frag = new Book();
                    fragmentManager.beginTransaction().replace(R.id.frame,frag,frag.getTag())
                            .addToBackStack(null).commit();

                }
            });

        }
    }
}

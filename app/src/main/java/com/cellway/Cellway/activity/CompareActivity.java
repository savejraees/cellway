package com.cellway.Cellway.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingAvaiableModel;

import java.util.ArrayList;

public class CompareActivity extends BaseActivity {

    RecyclerView rvCompare,rvHighlits;
    ArrayList<String> listComp = new ArrayList<>();
    ArrayList<String> listHighlight = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        rvHighlits = findViewById(R.id.rvHighlits);
        rvCompare = findViewById(R.id.rvCompare);
        listComp.add("Realme X2 Pro(128GB)");
        listComp.add("Iphone X2 Pro(128GB)");
        listComp.add("Oppo X2 Pro(128GB)");

        listHighlight.add("8 GB RAM");
        listHighlight.add("16 GB RAM");
        listHighlight.add("32 GB RAM");

    }

    public class CompareAdapter extends RecyclerView.Adapter<CompareAdapter.ProductViewHolder>{

        Context context;
        ArrayList<BookingAvaiableModel> arrProduct;

        public CompareAdapter(Context context, ArrayList<BookingAvaiableModel> arrProduct) {
            this.context = context;
            this.arrProduct = arrProduct;
        }


        @NonNull
        @Override
        public CompareAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_compare_adapter,parent,false);
            return new CompareAdapter.ProductViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull CompareAdapter.ProductViewHolder holder, int position) {
           // holder.txtofferKey.setText(arrProduct.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return arrProduct.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder{
            ImageView imgPhone;
            TextView txtBrand,txtPriceComp,txtDelivery,txtEmi;
            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                imgPhone = itemView.findViewById(R.id.imgPhone);
                txtBrand = itemView.findViewById(R.id.txtBrand);
                txtPriceComp = itemView.findViewById(R.id.txtPriceComp);
                txtDelivery = itemView.findViewById(R.id.txtDelivery);
                txtEmi = itemView.findViewById(R.id.txtEmi);

            }
        }
    }

    public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ProductViewHolder>{

        Context context;
        ArrayList<BookingAvaiableModel> arrProduct;

        public HighlightAdapter(Context context, ArrayList<BookingAvaiableModel> arrProduct) {
            this.context = context;
            this.arrProduct = arrProduct;
        }


        @NonNull
        @Override
        public HighlightAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_highlight,parent,false);
            return new HighlightAdapter.ProductViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull HighlightAdapter.ProductViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return arrProduct.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder{
            TextView txtRAm,txtROM,txtDisplayHigh,txtRearCamera,txtProcessor,txtBatteryHigh,txtVersion,txtWaarnty,txtPolicy;
            CardView cardCart;
            Button btnBuyNow;
            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                txtRAm = itemView.findViewById(R.id.txtRAm);
                txtROM = itemView.findViewById(R.id.txtROM);

            }
        }
    }

}
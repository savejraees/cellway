package com.cellway.Cellway.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.IApiServices;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.activity.FillAddressActivity;
import com.cellway.Cellway.retrofitModel.AddressModel.AddressDatumModel;
import com.cellway.Cellway.retrofitModel.AddressModel.DeleteAddressModel;
import com.cellway.Cellway.retrofitModel.YourBookingModel.BookingAvaiableModel;
import com.cellway.Cellway.util.Url;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ProductViewHolder>{

    Context context;
    ArrayList<AddressDatumModel> arrProduct;

    private int index = -1;

    private OnNumberClik mOnNumberClick;

    public AddressAdapter(Context context, ArrayList<AddressDatumModel> arrProduct, OnNumberClik onNumberClik) {
        this.context = context;
        this.arrProduct = arrProduct;
        mOnNumberClick = onNumberClik;
    }


    @NonNull
    @Override
    public AddressAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_address,parent,false);
        return new AddressAdapter.ProductViewHolder(view,mOnNumberClick);
    }


    @Override
    public void onBindViewHolder(@NonNull final AddressAdapter.ProductViewHolder holder, final int position) {
        final AddressDatumModel model = arrProduct.get(position);

        holder.txtCategory.setText(arrProduct.get(position).getCategory());
        holder.txtFloor.setText(arrProduct.get(position).getOfficeFloor()+", ");
        holder.txtLandmark.setText(arrProduct.get(position).getLandmark()+", ");
        holder.txtLocality.setText(arrProduct.get(position).getLocality()+", ");
        holder.txtCity.setText(arrProduct.get(position).getCity()+", ");
        holder.txtState.setText(arrProduct.get(position).getState()+", ");
        holder.txtPincode.setText("("+arrProduct.get(position).getPincode()+")");
        holder.txtNameAddres.setText(arrProduct.get(position).getName());

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = model.getId();
                hitApi(id,holder.getAdapterPosition());
            }
        });
        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = model.getId();
               context.startActivity(new Intent(context, FillAddressActivity.class)
               .putExtra("id",id));
            }
        });

        if (index == position) {
            holder.checkAddress.setChecked(true);
        } else {
            holder.checkAddress.setChecked(false);

        }
    }

    private void hitApi(int id, final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder() .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        IApiServices api = retrofit.create(IApiServices.class);
        Call<DeleteAddressModel> call = api.deleteAddress(Api.key, String.valueOf(id));
        call.enqueue(new Callback<DeleteAddressModel>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<DeleteAddressModel> call, Response<DeleteAddressModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    DeleteAddressModel model = response.body();

                    if (model.getCode().equals("200")) {
                        Toast.makeText(context, ""+ model.getMsg(), Toast.LENGTH_SHORT).show();

                        removeItem(position);
                    } else {
                        Toast.makeText(context, ""+ model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<DeleteAddressModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeItem(int position) {
        arrProduct.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtCategory,txtFloor,txtLandmark,txtLocality,txtCity,txtState,txtPincode,txtDelete,txtEdit,txtNameAddres;
        CheckBox checkAddress;
        private OnNumberClik onNumberClik;
        public ProductViewHolder(@NonNull View itemView,OnNumberClik onNumberClik) {
            super(itemView);
            this.onNumberClik = onNumberClik;
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtFloor = itemView.findViewById(R.id.txtFloor);
            txtLandmark = itemView.findViewById(R.id.txtLandmark);
            txtLocality = itemView.findViewById(R.id.txtLocality);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtState = itemView.findViewById(R.id.txtState);
            txtPincode = itemView.findViewById(R.id.txtPincode);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            txtNameAddres = itemView.findViewById(R.id.txtNameAddres);
            checkAddress = itemView.findViewById(R.id.checkAddress);
            checkAddress.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            index=getAdapterPosition();
            onNumberClik.onNumberClicked(getAdapterPosition());
            notifyDataSetChanged();

            Log.d("klsadjkl", "" + index);
        }
    }
    public interface OnNumberClik {
        void onNumberClicked(int position);

    }
}

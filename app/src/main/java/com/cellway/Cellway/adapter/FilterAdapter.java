package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.model.Product_Description;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder> {
    Context context;
    ArrayList<Product_Description> lstfilter;

    public FilterAdapter(Context context, ArrayList<Product_Description> lstfilter) {
        this.context = context;
        this.lstfilter = lstfilter;

    }

    @NonNull
    @Override
    public FilterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view  = layoutInflater.inflate(R.layout.row_series,parent,false);
        return new FilterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterHolder holder, final int position) {
        Product_Description filterModel = lstfilter.get(position);
         holder.tv_series.setText(filterModel.getSeries_name());
         holder.chkSelected.setChecked(lstfilter.get(position).isSelected());

         holder.chkSelected.setTag(lstfilter.get(position));
         holder.chkSelected.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 CheckBox cb = (CheckBox)view;
                 Product_Description object = (Product_Description) cb.getTag();
                 object.setSelected(cb.isChecked());
                 lstfilter.get(position).setSelected(cb.isChecked());
             }
         });
    }

    @Override
    public int getItemCount() {
        return lstfilter.size();
    }

    public class FilterHolder extends RecyclerView.ViewHolder {
        TextView tv_series;
        CheckBox chkSelected;
        public FilterHolder(@NonNull View itemView) {
            super(itemView);
            tv_series = itemView.findViewById(R.id.tv_series);
            chkSelected = itemView.findViewById(R.id.chkSelected);

        }
    }

    // method to access in activity after updating selection
    public ArrayList<Product_Description> getSeriesList() {
        return lstfilter;
    }

}

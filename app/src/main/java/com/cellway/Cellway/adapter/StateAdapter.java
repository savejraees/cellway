package com.cellway.Cellway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.cellway.Cellway.R;
import com.cellway.Cellway.retrofitModel.AddressModel.StateDatumModel;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends ArrayAdapter<StateDatumModel> {

    Context context;
    int resource, textViewResourceId;
    List<StateDatumModel> items, tempItems, suggestions;

    public StateAdapter(Context context, int resource, int textViewResourceId, List<StateDatumModel> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<StateDatumModel>(items); // this makes the difference.
        suggestions = new ArrayList<StateDatumModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_autocomplete, parent, false);
        }
        StateDatumModel people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getStateName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((StateDatumModel) resultValue).getStateName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (StateDatumModel people : tempItems) {
                    if (people.getStateName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<StateDatumModel> filterList = (ArrayList<StateDatumModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (StateDatumModel people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}



package com.cellway.Cellway.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.model.SearchModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
//public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
//
//    View view;
//
//    SearchView mobsearchView;
//    ArrayList<SearchModel> listSearch;
//    RecyclerView searchRecyclerview;
//    SearchAdapter searchAdapter;
//    Toolbar searchToolbar;
//
//    public SearchFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_search, container, false);
//
//        searchToolbar = view.findViewById(R.id.searchToolbar);
//        searchToolbar.setNavigationIcon(R.drawable.arrow_back);
//        searchToolbar.setTitle("Search");
//        searchToolbar.setTitleTextColor(Color.WHITE);
//        searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });
//        mobsearchView = view.findViewById(R.id.searchView);
//        searchRecyclerview = view.findViewById(R.id.searchRecyclerview);
//        mobsearchView.setOnQueryTextListener(this);
//        listSearch = new ArrayList<SearchModel>();
//        hitSearchApi();
//
//        return view;
//    }
//
//
//    //////////////////////////////////////////////////// search Api ///////////////////////////////////////////////////////
//
//    private void hitSearchApi() {
//
//        {
//
//            RequestQueue requestQueueMobile = Volley.newRequestQueue(getActivity());
//            StringRequest request = new StringRequest(Request.Method.POST, Api.search, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.d("Loginresponse", response);
//                    listSearch.clear();
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//
//                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                            SearchModel searchModel = new SearchModel();
//                            searchModel.setSearchbrand(jsonObject1.getString("brand"));
//                            searchModel.setSearchmodel(jsonObject1.getString("model"));
//                            searchModel.setSearchamount(jsonObject1.getString("sale_amount"));
//                            searchModel.setSearchgb(jsonObject1.getString("gb"));
//                            searchModel.setSearchwarrantyMonth(jsonObject1.getString("warrenty_month"));
//                            searchModel.setSearchId(jsonObject1.getString("id"));
//                            searchModel.setWarranty(jsonObject1.getString("warrenty"));
//
//                            Log.d("ijkad", jsonObject1.getString("imei_no"));
//                            listSearch.add(searchModel);
//                            Log.d("gfdfsdsd", String.valueOf(listSearch.size()));
//
//                        }
//
//                        searchRecyclerview.setHasFixedSize(true);
//                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
//                        searchRecyclerview.setLayoutManager(layoutManager);
//                        searchAdapter = new SearchAdapter(getActivity(), listSearch);
//                        searchRecyclerview.setAdapter(searchAdapter);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
////                    progressDialog.dismiss();
//                    Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
//                    Log.d("errodfr", error.getMessage() + "errorr");
//
//                }
//            }) {
//                protected Map<String, String> getParams() {
//                    HashMap<String, String> hashMap = new HashMap<>();
//                    hashMap.put("key", Api.key);
////                    hashMap.put("search"," ");
//                    return hashMap;
//
//                }
//
//            };
//            requestQueueMobile.getCache().clear();
//            requestQueueMobile.add(request);
//        }
//
//    }
//
//
//    ////////////////////////////////////////////////// method for search view //////////////////////////////////////////////
//    @Override
//    public boolean onQueryTextSubmit(String s) {
//        if (listSearch.contains(s)) {
//            searchAdapter.getFilter().filter(s);
//        } else {
//            Toast.makeText(getActivity(), "No Brand Available", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String s) {
//        searchAdapter.getFilter().filter(s);
//
//        return false;
//    }
//
//
//    ///////////////////////////////////////////// SearchView Adapter //////////////////////////////////////////////////////
//
//
//    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
//
//
//        Context context;
//
//        private List<SearchModel> nameList;
//        private List<SearchModel> filteredNameList;
//
//
//        public SearchAdapter(Context context, ArrayList<SearchModel> nameList) {
//            super();
//            this.context = context;
//            this.nameList = nameList;
//            this.filteredNameList = nameList;
//        }
//
//
//        @NonNull
//        @Override
//        public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view;
//
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            view = layoutInflater.inflate(R.layout.row_search, parent, false);
//
//            return new SearchAdapter.SearchViewHolder(view);
//        }
//
//
//        @Override
//        public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
////        ImeiModel all = mData.get(position);
////        holder.tv_imei.setText(((ImeiModel) all).getImei_no());
//            holder.tv_imei.setText(filteredNameList.get(position).getSearchmodel());
//
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//
//            return filteredNameList.size();
//
//        }
//
//        public android.widget.Filter getFilter() {
//            return new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    String charSequenceString = constraint.toString();
//                    if (charSequenceString.isEmpty()) {
//                        filteredNameList = nameList;
//                    } else {
//                        List<SearchModel> filteredList = new ArrayList<>();
//                        for (SearchModel name : nameList) {
//                            if (name.getSearchmodel().toLowerCase().contains(charSequenceString.toLowerCase())) {
//                                filteredList.add(name);
//                            }
//                            filteredNameList = filteredList;
//                        }
//
//                    }
//                    FilterResults results = new FilterResults();
//                    results.values = filteredNameList;
//                    return results;
//                }
//
//                @Override
//                protected void publishResults(CharSequence constraint, FilterResults results) {
//                    filteredNameList = (List<SearchModel>) results.values;
//                    notifyDataSetChanged();
//                }
//            };
//        }
//
//        public class SearchViewHolder extends RecyclerView.ViewHolder {
//
//            //            FragmentManager fragmentManager = getSupportFragmentManager();
//            TextView tv_imei;
//
//            public SearchViewHolder(@NonNull final View itemView) {
//                super(itemView);
//                tv_imei = itemView.findViewById(R.id.tv_search);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        int pos = getAdapterPosition();
//
//                        // check if item still exists
//                        if (pos != RecyclerView.NO_POSITION) {
//                            SearchModel clickedDataItem = filteredNameList.get(pos);
//                            String id = clickedDataItem.getSearchId();
//                            String amount = clickedDataItem.getSearchamount();
//                            String brand = clickedDataItem.getSearchbrand();
//                            String model = clickedDataItem.getSearchmodel();
//                            String gb = clickedDataItem.getSearchgb();
//                            String warrentyMonth = clickedDataItem.getSearchwarrantyMonth();
//                            String warranty = clickedDataItem.getWarranty();
//
//                            Log.d("poiuyt", id + " " + amount + " " + brand + " " + model + " " + gb + " " + warranty);
//
//
//                            Book frag = new Book();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("ImageId", id);
//                            bundle.putString("price", amount);
//                            bundle.putString("brand", brand);
//                            bundle.putString("model", model);
//                            bundle.putString("gb", gb);
//                            bundle.putString("warrantyMonth", warrentyMonth);
//                            bundle.putString("warranty", warranty);
//                            frag.setArguments(bundle);
//                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                            fragmentTransaction.addToBackStack(null);
//                            fragmentTransaction.replace(R.id.frame, frag);
//                            fragmentTransaction.commit();
//                            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//
//
////                            frag.setArguments(bundle);
////                            fragmentManager.beginTransaction().replace(R.id.frame,
////                                    frag, frag.getTag())
////                                    .addToBackStack(null)
////                                    .commit();
//
//
////                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getImei_no()+" "+clickedDataItem.getId()+" "+clickedDataItem.getBrand()+" "+clickedDataItem.getModel()+" "+clickedDataItem.getGb(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        }
//
//    }
//
//
//
//}

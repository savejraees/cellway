package com.cellway.Cellway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.model.NotificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    NotificationAdapter adapter;
    RecyclerView rvNotification;
    String token;
    ArrayList<NotificationModel> listnotific;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        rvNotification = findViewById(R.id.rvNotification);
        listnotific = new ArrayList<>();
        sharedPreferences = getSharedPreferences("Cellways",MODE_PRIVATE);
         token = sharedPreferences.getString("token","");
         Log.d("saddsawe",token);
        hitApi();

    }

    private void hitApi() {
        listnotific.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.getNotification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        NotificationModel model = new NotificationModel();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        model.setMessage(jsonObject1.getString("message"));
                        model.setTitle(jsonObject1.getString("title"));
                        listnotific.add(model);

                        Log.d("sadswds",jsonObject1.getString("message"));
                    }


                    rvNotification.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
                    rvNotification.setLayoutManager(layoutManager);
                    adapter = new NotificationAdapter(listnotific,getApplicationContext());
                    rvNotification.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("key",Api.key);
                hashMap.put("token",token);

                Log.d("dasdsawq", String.valueOf(hashMap));
                return hashMap;
            }

        };
        requestQueue.add(request);
    }


    ///////////////////////////////////////////// notificationAdapter /////////////////////////////////////////////

    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{

        ArrayList<NotificationModel> listNotificaton;
        Context context;

        public NotificationAdapter(ArrayList<NotificationModel> listNotificaton, Context context) {
            this.listNotificaton = listNotificaton;
            this.context = context;
        }

        @NonNull
        @Override
        public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.row_notification,parent,false);
            return new NotificationHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
            NotificationModel model = listNotificaton.get(position);
            holder.titleNotification.setText(model.getTitle());
            holder.messageNotification.setText(model.getMessage());
        }


        @Override
        public int getItemCount() {
            return listNotificaton.size();
        }

        public class NotificationHolder extends RecyclerView.ViewHolder{
            TextView titleNotification,messageNotification;
            public NotificationHolder(@NonNull View itemView) {
                super(itemView);
                titleNotification = itemView.findViewById(R.id.titleNotification);
                messageNotification = itemView.findViewById(R.id.messageNotification);

            }
        }

    }

}

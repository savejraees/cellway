package com.cellway.Cellway.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChecker {
    static Context context;

    public NetworkChecker(Context context) {
        this.context = context;
    }

    public  boolean isOnLine(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    public  void show() {


            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            builder.setTitle("Internet Problem");
            builder.setMessage("Please check your internet Connection.");
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ((Activity)context).finish();
                }
            });

            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                   /* Intent intent= new Intent(context,);
                    ((Activity)context).finish();
                    context.startActivity(intent);*/
                }
            });
            builder.create().show();
        }



}

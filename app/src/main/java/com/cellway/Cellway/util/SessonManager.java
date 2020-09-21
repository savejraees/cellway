package com.cellway.Cellway.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.cellway.Cellway.R;

public class SessonManager {

    private static SessonManager pref;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    public static final String NAME = "MY_PREFERENCES";
    public static final String Token = "token";
    public static final String pinCode = "pincode";
    public static final String Qty = "Quantity";
    public static final String AddressIntent = "Address";
    public static final String ProductIdBook = "Id";
    public static final String catId = "CatId";
    public static final String subCatId = "SubCatId";
    public static final String ProductId = "ProductId";
    public static final String Price1 = "Price1";
    public static final String Price2 = "Price2";
    public static final String type = "Type";
    public static final String CatIdAccess = "CatIdAccess";
    public Dialog mDialog;

    public SessonManager(Context ctx) {
        sharedPreference = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }


    public void setToken(String tkn) {
        editor.putString(Token, tkn);
        editor.commit();
    }

    public String getToken() {
        return sharedPreference.getString(Token, "");
    }

    public void setCatId(int cati) {
        editor.putInt(catId, cati);
        editor.commit();
    }

    public int getCatId() {
        return sharedPreference.getInt(catId, 0);
    }

    public void setSubCatId(int subcati) {
        editor.putInt(subCatId, subcati);
        editor.commit();
    }

    public int getSubCatId() {
        return sharedPreference.getInt(subCatId, 0);
    }

    public void setCatIdAccess(int subcati) {
        editor.putInt(CatIdAccess, subcati);
        editor.commit();
    }

    public int getCatIdAccess() {
        return sharedPreference.getInt(CatIdAccess, 0);
    }

    public void setAddressIntent(String addressIntent) {
        editor.putString(AddressIntent, addressIntent);
        editor.commit();
    }

    public String getAddressIntent() {
        return sharedPreference.getString(AddressIntent, "");
    }

   public void setProductId(String addressIntent) {
        editor.putString(ProductId, addressIntent);
        editor.commit();
    }

    public String getProductId() {
        return sharedPreference.getString(ProductId, "");
    }

    public void setPrice1(String p1) {
        editor.putString(Price1, p1);
        editor.commit();
    }

    public String getPrice1() {
        return sharedPreference.getString(Price1, "");
    }

    public void setPrice2(String p2) {
        editor.putString(Price2, p2);
        editor.commit();
    }

    public String getPrice2() {
        return sharedPreference.getString(Price2, "");
    }
    public void setType(String addressIntent) {
        editor.putString(type, addressIntent);
        editor.commit();
    }

    public String getType() {
        return sharedPreference.getString(type, "");
    }

    public void setProductIdBook(String productId) {
        editor.putString(ProductIdBook, productId);
        editor.commit();
    }

    public String getProductIdBook() {
        return sharedPreference.getString(ProductIdBook, "");
    }

    public void setPinCode(String pin) {
        editor.putString(pinCode, pin);
        editor.commit();
    }

    public String getPinCode() {
        return sharedPreference.getString(pinCode, "");
    }


    public void setQty(String qty) {
        editor.putString(Qty, qty);
        editor.commit();
    }


    public String getQty() {
        return sharedPreference.getString(Qty, "");
    }


    public void hideProgress() {
        while (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();

        }
        /*if (mDialog != null && mDialog.isShowing()) {
          //  mDialog = null;
        }*/
    }

    public void showProgress(Context mContext) {
        if (mContext != null) {
            mDialog = new Dialog(mContext);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.custom_progress_layout);
            mDialog.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

    }


}

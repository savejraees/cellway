package com.cellway.Cellway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cellway.Cellway.R;
import com.cellway.Cellway.Services.Api;
import com.cellway.Cellway.adapter.StateAdapter;
import com.cellway.Cellway.retrofitModel.AddressModel.FillAddressModel;
import com.cellway.Cellway.retrofitModel.AddressModel.FillAddressModel;
import com.cellway.Cellway.retrofitModel.AddressModel.GetFillAddressModel;
import com.cellway.Cellway.retrofitModel.AddressModel.StateDatumModel;
import com.cellway.Cellway.retrofitModel.AddressModel.StateStatusModel;
import com.cellway.Cellway.retrofitModel.AddressModel.UpdateAddressModel;
import com.cellway.Cellway.util.SessonManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FillAddressActivity extends BaseActivity{

    CardView cardSave;
    TextInputEditText edtPinCode,edt_office_floor,edtLanMark,edtCity,edtLocality,edtName;
    String category="";
    RadioGroup radiCategory;
    ImageView imgBackFillAddress;
    RadioButton radioBtnHome,radioBtnOffice,radioBtnOthers;
    boolean updateAddress = false;
    int ID;
    AutoCompleteTextView state_autocompleteTv;
    ArrayList<StateDatumModel> listState = new ArrayList<>();
    StateAdapter stateAdapter;
    StateDatumModel stateModelObject;
    String stateName="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_fill);
        cardSave = findViewById(R.id.cardSave);
        edtName = findViewById(R.id.edtName);
        edtPinCode = findViewById(R.id.edtPinCode);
        edt_office_floor = findViewById(R.id.edt_office_floor);
        edtLocality = findViewById(R.id.edtLocality);
        edtCity = findViewById(R.id.edtCity);
        edtLanMark = findViewById(R.id.edtLanMark);
        radiCategory = findViewById(R.id.radiCategory);
        radioBtnOthers = findViewById(R.id.radioBtnOthers);
        radioBtnOffice = findViewById(R.id.radioBtnOffice);
        radioBtnHome = findViewById(R.id.radioBtnHome);
        imgBackFillAddress = findViewById(R.id.imgBackFillAddress);
        state_autocompleteTv = findViewById(R.id.state_autocompleteTv);
        state_autocompleteTv.setThreshold(1);
        sessonManager =new SessonManager(FillAddressActivity.this);

        if(getIntent().hasExtra("id")){
            updateAddress = true;
            int id = getIntent().getIntExtra("id",0);
            ID = id;
            hitApiGetAddress(id);
        }else{
            hitStateApi();
        }

        radiCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    category = checkedRadioButton.getText().toString();
                    
//                    Toast.makeText(ProductActivity.this, ""+ orderBy, Toast.LENGTH_SHORT).show();
                }
            }
        });

        state_autocompleteTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //   modelMobTab = (String) parent.getItemAtPosition(position);

                stateModelObject = (StateDatumModel) parent.getItemAtPosition(position);
                stateName = stateModelObject.getStateName();

                Log.d("sfaklsapol",stateName);


            }
        });
        imgBackFillAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cardSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startActivity(new Intent(FillAddressActivity.this, PaymentActivity.class));
                if(edtName.getText().toString().isEmpty()){
                    edtName.setError("Can't be blank");
                    edtName.requestFocus();
                }
               else if(edtPinCode.getText().toString().isEmpty()){
                    edtPinCode.setError("Can't be blank");
                    edtPinCode.requestFocus();
                }
                else if(edt_office_floor.getText().toString().isEmpty()){
                    edt_office_floor.setError("Can't be blank");
                    edt_office_floor.requestFocus();
                }
                else if(edtLanMark.getText().toString().isEmpty()){
                    edtLanMark.setError("Can't be blank");
                    edtLanMark.requestFocus();
                }
                else if(edtLocality.getText().toString().isEmpty()){
                    edtLocality.setError("Can't be blank");
                    edtLocality.requestFocus();
                }
                else if(edtCity.getText().toString().isEmpty()){
                    edtCity.setError("Can't be blank");
                    edtCity.requestFocus();
                }
                else if(stateName.equals("")){
                    Toast.makeText(FillAddressActivity.this, "Select Your State", Toast.LENGTH_SHORT).show();
                } 
                else if(category.equals("")){
                    Toast.makeText(FillAddressActivity.this, "Please Select Address Type", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(updateAddress==true){
                        hitUpdateAddressApi();
                    }else{
                        hitApi();
                    }
                 
                }
            }
        });
    }

    private void hitUpdateAddressApi() {
        showProgress(FillAddressActivity.this);
        Call<UpdateAddressModel> call = api.updateaddress(Api.key,ID,edt_office_floor.getText().toString(),
                edtLanMark.getText().toString(),edtLocality.getText().toString(),edtPinCode.getText().toString(),edtCity.getText().toString(),
                stateName,category,edtName.getText().toString());
        call.enqueue(new Callback<UpdateAddressModel>() {
            @Override
            public void onResponse(Call<UpdateAddressModel> call, Response<UpdateAddressModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    UpdateAddressModel FillAddressModel = response.body();
                    Toast.makeText(FillAddressActivity.this, ""+FillAddressModel.getMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FillAddressActivity.this,AddressActivity.class));
                    finishAffinity();
                }
            }

            @Override
            public void onFailure(Call<UpdateAddressModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(FillAddressActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitApiGetAddress(int id) {
        showProgress(FillAddressActivity.this);
        Call<GetFillAddressModel> call = api.getFillAddress(Api.key, String.valueOf(id));
        call.enqueue(new Callback<GetFillAddressModel>() {
            @Override
            public void onResponse(Call<GetFillAddressModel> call, Response<GetFillAddressModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    GetFillAddressModel FillAddressModel = response.body();

                    edtName.setText(FillAddressModel.getName());
                    edtPinCode.setText(""+FillAddressModel.getPincode());
                    edt_office_floor.setText(FillAddressModel.getOfficeFloor());
                    edtLanMark.setText(FillAddressModel.getLandmark());
                    edtCity.setText(FillAddressModel.getCity());
                    state_autocompleteTv.setText(FillAddressModel.getState());
                    edtLocality.setText(FillAddressModel.getLocality());
                    stateName = FillAddressModel.getState();
                    if(FillAddressModel.getCategory().equalsIgnoreCase("Home")){
                        radioBtnHome.setChecked(true);
                        category = FillAddressModel.getCategory();
                    }
                    if(FillAddressModel.getCategory().equalsIgnoreCase("Others")){
                        radioBtnOthers.setChecked(true);
                        category = FillAddressModel.getCategory();
                    }
                    if(FillAddressModel.getCategory().equalsIgnoreCase("Office")){
                        radioBtnOffice.setChecked(true);
                        category = FillAddressModel.getCategory();
                    }

                    hitStateApi();

                }
            }

            @Override
            public void onFailure(Call<GetFillAddressModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(FillAddressActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitStateApi() {
        showProgress(FillAddressActivity.this);
        Call<StateStatusModel> call = api.getStateApi(Api.key);
        call.enqueue(new Callback<StateStatusModel>() {
            @Override
            public void onResponse(Call<StateStatusModel> call, Response<StateStatusModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    StateStatusModel model = response.body();
                    listState = model.getData();

                    stateAdapter = new StateAdapter(getApplicationContext(), R.layout.activity_address_fill,
                            R.id.lbl_name, listState);
                    state_autocompleteTv.setAdapter(stateAdapter);
                }
            }

            @Override
            public void onFailure(Call<StateStatusModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(FillAddressActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitApi() {
        showProgress(FillAddressActivity.this);
        Call<FillAddressModel> call = api.addAddress(Api.key,sessonManager.getToken(),edt_office_floor.getText().toString(),
                edtLanMark.getText().toString(),edtLocality.getText().toString(),edtPinCode.getText().toString(),edtCity.getText().toString(),
               stateName,category,edtName.getText().toString());
        call.enqueue(new Callback<FillAddressModel>() {
            @Override
            public void onResponse(Call<FillAddressModel> call, Response<FillAddressModel> response) {

                if (response.isSuccessful()) {
                    hideProgress();
                    FillAddressModel FillAddressModel = response.body();
                    Toast.makeText(FillAddressActivity.this, ""+FillAddressModel.getMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FillAddressActivity.this,AddressActivity.class));
                    finishAffinity();
                }
            }

            @Override
            public void onFailure(Call<FillAddressModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(FillAddressActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
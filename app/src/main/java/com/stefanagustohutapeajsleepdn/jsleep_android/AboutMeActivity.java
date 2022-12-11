package com.stefanagustohutapeajsleepdn.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stefanagustohutapeajsleepdn.jsleep_android.model.Account;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Renter;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.BaseApiService;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
        BaseApiService mApiService;
        TextView name, email, balance, nameRent, addRent, phoneRent, nameOfRent, addOfRent, phoneOfRent;
        EditText nameReg, addReg, phoneReg, amountTopUp;
        Button regBtn, cancelBtn, regRenBtn, topupBtn, logoutBtn;
        Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        topupBtn = findViewById(R.id.buttonTopup);
        topupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topUpRequest();
            }
        });
        logoutBtn = findViewById(R.id.logoutButton);
        name = findViewById(R.id.nameOfUser);
        email = findViewById(R.id.emailOfUser);
        balance = findViewById(R.id.balanceOfUser);
        name.setText(MainActivity.loginAccount.name);
        email.setText(MainActivity.loginAccount.email);
        balance.setText(String.valueOf(MainActivity.loginAccount.balance));
        nameReg = findViewById(R.id.name_register);
        addReg = findViewById(R.id.address_register);
        phoneReg = findViewById(R.id.phoneNum_register);
        nameRent = findViewById(R.id.name_renter);
        addRent = findViewById(R.id.address_renter);
        phoneRent = findViewById(R.id.phoneNum_renter);
        nameOfRent = findViewById(R.id.nameOfRenter);
        addOfRent = findViewById(R.id.addressOfRenter);
        phoneOfRent = findViewById(R.id.phoneNumOfRenter);
        regRenBtn = findViewById(R.id.buttonRegisterRenter);
        regBtn = findViewById(R.id.buttonRegister);
        cancelBtn = findViewById(R.id.buttonCancel);
        amountTopUp = findViewById(R.id.amountUser);

        if (MainActivity.loginAccount.renter != null) {
            nameOfRent.setText(MainActivity.loginAccount.renter.username);
            addOfRent.setText(MainActivity.loginAccount.renter.address);
            phoneOfRent.setText(MainActivity.loginAccount.renter.phoneNumber);
            nameRent.setVisibility(View.VISIBLE);
            addRent.setVisibility(View.VISIBLE);
            phoneRent.setVisibility(View.VISIBLE);
            nameOfRent.setVisibility(View.VISIBLE);
            addOfRent.setVisibility(View.VISIBLE);
            phoneOfRent.setVisibility(View.VISIBLE);
        }
        if (MainActivity.loginAccount.renter == null) {
            regRenBtn.setVisibility(View.VISIBLE);
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                Intent move = new Intent(AboutMeActivity.this, LoginActivity.class);
                startActivity(move);
            }
        });
    }

    public void registerRenClick(View view){
        nameReg.setVisibility(View.VISIBLE);
        addReg.setVisibility(View.VISIBLE);
        phoneReg.setVisibility(View.VISIBLE);
        regBtn.setVisibility(Button.VISIBLE);
        cancelBtn.setVisibility(Button.VISIBLE);
        regRenBtn.setVisibility(Button.INVISIBLE);
    }

    public void cancelClick(View view){
        nameReg.setVisibility(View.INVISIBLE);
        addReg.setVisibility(View.INVISIBLE);
        phoneReg.setVisibility(View.INVISIBLE);
        regBtn.setVisibility(Button.INVISIBLE);
        cancelBtn.setVisibility(Button.INVISIBLE);
        regRenBtn.setVisibility(Button.VISIBLE);
    }

    public void registerClick(View view){
        Account account = registerRenter();
        nameReg.setVisibility(View.INVISIBLE);
        addReg.setVisibility(View.INVISIBLE);
        phoneReg.setVisibility(View.INVISIBLE);
        regBtn.setVisibility(Button.INVISIBLE);
        cancelBtn.setVisibility(Button.INVISIBLE);
    }

    protected Boolean topUpRequest(){
        Double topUpAmount = Double.parseDouble(amountTopUp.getText().toString());
        mApiService.topUp(MainActivity.loginAccount.id, Double.parseDouble(amountTopUp.getText().toString())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Top Up Success", Toast.LENGTH_SHORT).show();
                    MainActivity.loginAccount.balance += topUpAmount;
                    balance.setText(String.valueOf(MainActivity.loginAccount.balance));
                } else {
                    Toast.makeText(mContext, "Top Up Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Top Up Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }


    protected Account registerRenter(){
        mApiService.registerRenter(
                MainActivity.loginAccount.id,
                nameReg.getText().toString(),
                addReg.getText().toString(),
                phoneReg.getText().toString()
        ).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if (response.isSuccessful()){
                    MainActivity.loginAccount.renter = response.body();
                    Toast.makeText(mContext, "Register Renter Success", Toast.LENGTH_SHORT).show();
                    System.out.println(response.body());
                    System.out.println(MainActivity.loginAccount.id);
                    System.out.println(nameReg.getText().toString());
                    System.out.println(MainActivity.loginAccount.renter.username);
                    System.out.println(MainActivity.loginAccount.renter.address);
                    System.out.println(MainActivity.loginAccount.renter.phoneNumber);
                    nameOfRent.setText(MainActivity.loginAccount.renter.username);
                    addOfRent.setText(MainActivity.loginAccount.renter.address);
                    phoneOfRent.setText(MainActivity.loginAccount.renter.phoneNumber);
                    nameRent.setVisibility(View.VISIBLE);
                    addRent.setVisibility(View.VISIBLE);
                    phoneRent.setVisibility(View.VISIBLE);
                    nameOfRent.setVisibility(View.VISIBLE);
                    addOfRent.setVisibility(View.VISIBLE);
                    phoneOfRent.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                System.out.println("id="+MainActivity.loginAccount.id);
                Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}
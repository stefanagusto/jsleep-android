package com.stefanagustohutapeajsleepdn.jsleep_android;

import android.content.Context;
import android.os.Bundle;
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
        EditText nameReg, addReg, phoneReg;
        Button regBtn, cancelBtn, regRenBtn;
        Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getApiService();
        mContext = this;

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
        if (MainActivity.loginAccount.renter != null) {
            nameOfRent.setText(MainActivity.loginAccount.renter.username);
            addOfRent.setText(MainActivity.loginAccount.renter.address);
            phoneOfRent.setText(MainActivity.loginAccount.renter.phoneNumber);
            nameRent.setVisibility(View.VISIBLE);
            addRent.setVisibility(View.VISIBLE);
            phoneRent.setVisibility(View.VISIBLE);
        }
        if (MainActivity.loginAccount.renter == null) {
            regRenBtn.setVisibility(View.VISIBLE);
        }
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

    protected Account registerRenter(){
        mApiService.registerRenter(
                MainActivity.loginAccount.id,
                nameRent.getText().toString(),
                phoneRent.getText().toString(),
                addRent.getText().toString()
        ).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if (response.isSuccessful()){
                    MainActivity.loginAccount.renter = response.body();
                    Toast.makeText(mContext, "Register Renter Success", Toast.LENGTH_SHORT).show();
                    System.out.println(response.body());
                    nameOfRent.setText(MainActivity.loginAccount.renter.username);
                    addOfRent.setText(MainActivity.loginAccount.renter.address);
                    phoneOfRent.setText(MainActivity.loginAccount.renter.phoneNumber);
                    nameRent.setVisibility(View.VISIBLE);
                    addRent.setVisibility(View.VISIBLE);
                    phoneRent.setVisibility(View.VISIBLE);
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
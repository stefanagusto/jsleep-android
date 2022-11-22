package com.stefanagustohutapeajsleepdn.jsleep_android;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutMeActivity extends AppCompatActivity {

        TextView name, email, balance;
        Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        name = findViewById(R.id.nameOfUser);
        email = findViewById(R.id.emailOfUser);
        balance = findViewById(R.id.balanceOfUser);

    }
}
package com.stefanagustohutapeajsleepdn.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.stefanagustohutapeajsleepdn.jsleep_android.model.BedType;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.City;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Spinner spinner = findViewById(R.id.planets_spinner);
        Spinner spinner2 = findViewById(R.id.planets_spinner2);

        spinner.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));
        spinner2.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));

    }
}
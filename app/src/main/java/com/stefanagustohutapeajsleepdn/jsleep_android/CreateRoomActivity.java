package com.stefanagustohutapeajsleepdn.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stefanagustohutapeajsleepdn.jsleep_android.model.BedType;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.City;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Facility;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Room;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.BaseApiService;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Javadoc
 * @author Stefan Agusto Hutapea
 */

/**
 * An activity that allows users to create a new room.
 */
public class CreateRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    Spinner bedTypeSpinner, citySpinner;
    ArrayAdapter bedTypeAdapter, cityAdapter;
    EditText nameRoom, addressRoom, priceRoom, sizeRoom;
    CheckBox AC, Refrigerator, Wifi, Bathub, Balcony, Restaurant, SwimmingPool, Fitness;
    Button createRoom, cancelRoom;
    ArrayList<Facility> facility = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        AC = findViewById(R.id.acCheckBox);
        Refrigerator = findViewById(R.id.refriCheckBox);
        Wifi = findViewById(R.id.wifiCheckBox);
        Bathub = findViewById(R.id.bathCheckBox);
        Balcony = findViewById(R.id.balconyCheckBox);
        Restaurant = findViewById(R.id.restaurantCheckBox);
        SwimmingPool = findViewById(R.id.poolCheckBox);
        Fitness = findViewById(R.id.fitnessCheckBox);

        nameRoom = findViewById(R.id.nameCreate);
        addressRoom = findViewById(R.id.addressCreate);
        priceRoom = findViewById(R.id.priceCreate);
        sizeRoom = findViewById(R.id.sizeCreate);

        bedTypeSpinner = findViewById(R.id.planets_spinner2);
        citySpinner = findViewById(R.id.planets_spinner);
        bedTypeAdapter = new ArrayAdapter<BedType>(getApplicationContext(), android.R.layout.simple_spinner_item, BedType.values());
        bedTypeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        bedTypeSpinner.setAdapter(bedTypeAdapter);
        cityAdapter = new ArrayAdapter<City>(getApplicationContext(), android.R.layout.simple_spinner_item, City.values());
        cityAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        createRoom = findViewById(R.id.createButton);
        cancelRoom = findViewById(R.id.cancelButton);
        /**
         * Sets a click listener on the `createRoom` button to initiate the `createRequest` method
         * and navigate to the `MainActivity` page when the button is clicked.
         */
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRequest();
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });
    }

    /**
     * Creates a new room.
     */
    protected Room createRequest () {
        ArrayList<Facility> checkedFacility = checkFacility();
        System.out.println(checkedFacility);
        mApiService.create(MainActivity.loginAccount.id,
                nameRoom.getText().toString(),
                Integer.parseInt(sizeRoom.getText().toString()),
                Integer.parseInt(priceRoom.getText().toString()),
                checkedFacility,
                City.valueOf(citySpinner.getSelectedItem().toString()),
                addressRoom.getText().toString(),
                BedType.valueOf(bedTypeSpinner.getSelectedItem().toString())
        ).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    MainActivity.listRoom = response.body();
                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
    /**
     * To Check Facility to the facility.java
     */
    public ArrayList<Facility> checkFacility () {
        if (AC.isChecked()) {
            facility.add(Facility.AC);
        }
        if (Wifi.isChecked()) {
            facility.add(Facility.WiFi);
        }
        if (Refrigerator.isChecked()) {
            facility.add(Facility.Refrigerator);
        }
        if (Bathub.isChecked()) {
            facility.add(Facility.Bathub);
        }
        if (Balcony.isChecked()) {
            facility.add(Facility.Balcony);
        }
        if (Restaurant.isChecked()) {
            facility.add(Facility.Restaurant);
        }
        if (SwimmingPool.isChecked()) {
            facility.add(Facility.SwimmingPool);
        }
        if (Fitness.isChecked()) {
            facility.add(Facility.FitnessCenter);
        }
        return facility;
    }
}
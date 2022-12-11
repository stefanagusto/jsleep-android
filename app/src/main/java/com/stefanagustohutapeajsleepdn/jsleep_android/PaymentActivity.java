package com.stefanagustohutapeajsleepdn.jsleep_android;

import static com.stefanagustohutapeajsleepdn.jsleep_android.DetailRoomActivity.tempRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stefanagustohutapeajsleepdn.jsleep_android.model.Facility;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.BaseApiService;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.UtilsApi;

import java.util.Timer;
import java.util.TimerTask;

public class PaymentActivity extends AppCompatActivity {
    BaseApiService mApiService;
    TextView roomName, roomAddress, roomPrice, roomSize, roomBedType;
    CheckBox ac, refr, wifi, bath, balcon, rest, pool, fitn;
    Button cancelBtn, payBtn;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mApiService = UtilsApi.getApiService();
        roomName = findViewById(R.id.nameOfRoom);
        roomBedType = findViewById(R.id.bedTypeRoom);
        roomSize = findViewById(R.id.sizeOfRoom);
        roomPrice = findViewById(R.id.priceOfRoom);
        roomAddress = findViewById(R.id.addressOfRoom);
        cancelBtn = findViewById(R.id.cancelPayButton);
        payBtn = findViewById(R.id.payButton);
        timer = new Timer();

        ac = findViewById(R.id.acBox);
        refr = findViewById(R.id.refrigeratorBox);
        wifi = findViewById(R.id.wifiBox);
        bath = findViewById(R.id.bathubBox);
        balcon = findViewById(R.id.balconyBox);
        rest = findViewById(R.id.restaurantBox);
        pool = findViewById(R.id.poolBox);
        fitn = findViewById(R.id.fitnessBox);

        roomName.setText(tempRoom.name);
        roomPrice.setText(String.valueOf(tempRoom.price.price));
        roomAddress.setText(tempRoom.address);
        roomBedType.setText(tempRoom.bedType.toString());
        roomSize.setText(String.valueOf(tempRoom.size));

        for (int i = 0; i < tempRoom.facility.size(); i++) {
            if (tempRoom.facility.get(i).equals(Facility.AC)) {
                ac.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Refrigerator)) {
                refr.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Bathub)) {
                bath.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Balcony)) {
                balcon.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Restaurant)) {
                rest.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                pool.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                fitn.setChecked(true);
            }
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.loginAccount.balance = MainActivity.loginAccount.balance + tempRoom.price.price;
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(PaymentActivity.this, "Room Booking Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
            }
        });

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.loginAccount.balance = MainActivity.loginAccount.balance + tempRoom.price.price;
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },  10000);
    }
}
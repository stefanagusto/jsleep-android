package com.stefanagustohutapeajsleepdn.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Account;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Room;
import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static Account loginAccount;
    public static Account requestRegister;
    Button addBoxBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = null;
        try {
            InputStream inStream = getAssets().open("randomRoomList.json");
            int size = inStream.available();
            byte[] buffer = new byte[size];
            inStream.read(buffer);
            inStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Room[] roomList = gson.fromJson(json, Room[].class);
        ArrayList<String> roomNameList = new ArrayList<>();
        for (Room room : roomList) {
            Log.d("Room", room.name);
            roomNameList.add(room.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomNameList);
        ListView listView = findViewById(R.id.view_list);
        listView.setAdapter(adapter);

        if (MainActivity.loginAccount.renter == null) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_resource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.PersonButton:
                Intent move = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(move);
        }
        return true;
    }
}
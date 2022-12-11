package com.stefanagustohutapeajsleepdn.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Account;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Payment;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Room;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.BaseApiService;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.UtilsApi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static Account loginAccount;
    public static Account requestRegister;
    public static Payment paymentAccount;
    BaseApiService mApiService;
    String name;
    static ArrayList<Room> roomList = new ArrayList<Room>();
    public static Room listRoom;

    Context mContext;
    List<String> strName;
    public static List<Room> getRoom ;
    List<Room> account ;
    ListView listView;
    Button prev, next, go;
    MenuItem add, person, search;
    int currPage = 1, pageSize = 10;
    EditText pageNumber;
    public static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        listView = findViewById(R.id.view_list);
        prev = findViewById(R.id.prev_button);
        next = findViewById(R.id.next_button);
        go = findViewById(R.id.go_button);
        pageNumber = findViewById(R.id.pageText);
        listView.setOnItemClickListener(this::OnItemClick);
        getAllRoom(currPage);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllRoom(currPage + 1);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currPage - 1 < 1) {
                    getAllRoom(currPage);
                } else {
                    getAllRoom(currPage - 1);
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inpPage = Integer.parseInt(pageNumber.getText().toString());
                if(inpPage < 1)
                    getAllRoom(currPage);
                getAllRoom(inpPage);
            }
        });
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

        switch (item.getItemId()) {
            case R.id.AddBoxButton:
                Intent move = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(move);
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem register = menu.findItem(R.id.AddBoxButton);
        if (loginAccount == null) {
            register.setVisible(false);
        } else {
            register.setVisible(true);
        }
        return true;
    }

    protected Room getAllRoom(int page) {
        mApiService.getAllRoom(page-1, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    getRoom = (ArrayList<Room>)response.body();
                    System.out.println(getRoom.toString());
                    ArrayList<String> names = new ArrayList<>();

                    for (Room r : getRoom) {
                        names.add(r.name);
                    }
                    if(names.isEmpty()) {
                        Toast.makeText(mContext, "No More Room to List", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, names);
                    ListView listView = (ListView) findViewById(R.id.view_list);
                    listView.setAdapter(adapter);
                    pageNumber.setText(String.valueOf(page));
                    currPage = page;
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                System.out.println(currPage);
                System.out.println(t);
            }
        });
        return null;
    }

    public static ArrayList<String> getName(List<Room> roomList){
        ArrayList<String> name = new ArrayList<String>();
        int i = 0;
        for (i = 0; i < roomList.size(); i++){
            name.add(roomList.get(i).name);
        }
        return name;
    }

    public void OnItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + pos);
        Intent move = new Intent(MainActivity.this, DetailRoomActivity.class);
        DetailRoomActivity.tempRoom = getRoom.get(pos);
        move.putExtra("Position", pos);
        move.putExtra("ID", id);
        startActivity(move);
    }

}
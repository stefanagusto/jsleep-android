package com.stefanagustohutapeajsleepdn.jsleep_android;

import static com.stefanagustohutapeajsleepdn.jsleep_android.DetailRoomActivity.tempRoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Javadoc
 * @author Stefan Agusto Hutapea
 */

/**
 * Launcher Activity to show the logo of the app and set timer to 3 seconds
 */

public class LauncherActivity extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        },  3000);
    }
}
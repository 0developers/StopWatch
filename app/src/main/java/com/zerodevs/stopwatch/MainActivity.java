package com.zerodevs.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        // wait 3 seconds and open activity

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask testing = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        Intent intt = new Intent();
                        intt.setClass(getApplicationContext() , StopWatch.class);
                        startActivity(intt);
                        finish();

                    }

                });


            }
        };
        timer.schedule(testing,1000);
    }
}
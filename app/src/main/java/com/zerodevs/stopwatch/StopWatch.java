package com.zerodevs.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import android.widget.EditText;


public class StopWatch extends AppCompatActivity {

    private boolean stopped;
    private int secs;
    private int sec;
    private int minutes;
    private int hours;
    private Boolean stopTimer = false;
    private Boolean stopCountdown = false;
    private Boolean stopWatchMode = false;
    private Boolean timerMode = false;
    int beeps;
    Button resetbtn;
    Button startbtn;
    Button starttimer;
    BottomNavigationView bottomnav;
    TickerView timertxt;
    private SoundPool sp;
    private ObjectAnimator effect = new ObjectAnimator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        BottomNavigationView bottomnav = findViewById(R.id.bottomnav);
        bottomnav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);


       stopwatch();



    }

    // bottom nav bar
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                if (!stopWatchMode)
                    stopwatch();

                return true;

            case R.id.item2:
                if(!timerMode)
                    timermode();



                return true;
        }

        return false;
    }


    public void stopwatch() {
        stopWatchMode = true;
        timerMode = false;
        Button reset = findViewById(R.id.resetbtn);
        Button start = findViewById(R.id.startbtn);
        Button starttimer = findViewById(R.id.starttimer);
        BottomNavigationView bottomnav = findViewById(R.id.bottomnav);
        TickerView timertxt = findViewById(R.id.timer);
        EditText inputHour = findViewById(R.id.inputHour);
        EditText inputMinute = findViewById(R.id.inputMinute);
        EditText inputSecond = findViewById(R.id.inputSecond);
        timertxt.setCharacterLists(TickerUtils.provideNumberList());
        timertxt.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);
        timertxt.setGravity(Gravity.CENTER);
        starttimer.setVisibility(View.GONE);
        timertxt.setVisibility(View.VISIBLE);
        start.setVisibility(View.VISIBLE);
        reset.setVisibility(View.VISIBLE);
        inputHour.setVisibility(View.GONE);
        inputMinute.setVisibility(View.GONE);
        inputSecond.setVisibility(View.GONE);

        stopped = true;
        secs = 0;
        hours = 0;
        minutes = 0;
        sec = 0;
        stopTimer = false;
        stopCountdown = true;


        start.setText("Start");

        timertxt.setText("00:00:00");
        start.setBackgroundColor(Color.parseColor("#00C853"));


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!stopped) {
                    // if the timer is started , stop the timer
                    start.setText("Start");
                    start.setBackgroundColor(Color.parseColor("#00C853"));
                    stopped = true;


                } else {
                    // if the timer is not started , start the timer

                    start.setText("Stop");
                    start.setBackgroundColor(Color.parseColor("#D50000"));
                    stopped = false;


                }



            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //stop and reset the timer


                stopped = true;
                secs = 0;
                sec = 0;
                minutes = 0;
                hours = 0;
                start.setText("Start");
                timertxt.setText("00:00:00");
                start.setBackgroundColor(Color.parseColor("#00C853"));

            }
        });



        // this is the timer for stop watch

        Timer timer = new Timer();
        final Handler handler = new Handler();
        TimerTask TK = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {

                    public void run() {

                        try {
                            if (!stopped) {

                                secs++;
                                hours = (secs / 3600);
                                minutes = (secs % 3600) / 60;
                                sec = secs % 60;


                                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, sec);

                                timertxt.setText(time);


                            }
                        } catch (Exception e) {

                        }
                        if (stopTimer) {
                            timer.cancel();
                        }
                    }

                });


            }
        };

        timer.schedule(TK, 1000, 1000);





    }

    public void timermode() {
        stopWatchMode = false;
        timerMode = true;
        Button reset = findViewById(R.id.resetbtn);
        Button start = findViewById(R.id.startbtn);
        Button starttimer = findViewById(R.id.starttimer);
        BottomNavigationView bottomnav = findViewById(R.id.bottomnav);
        TickerView timertxt = findViewById(R.id.timer);
        EditText inputHour = findViewById(R.id.inputHour);
        EditText inputMinute = findViewById(R.id.inputMinute);
        EditText inputSecond = findViewById(R.id.inputSecond);
        timertxt.setCharacterLists(TickerUtils.provideNumberList());
        timertxt.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);
        timertxt.setGravity(Gravity.CENTER);
        //set visibility of the views
        starttimer.setVisibility(View.VISIBLE);
        timertxt.setVisibility(View.VISIBLE);
        start.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);

        inputHour.setVisibility(View. VISIBLE);
        inputMinute.setVisibility(View.VISIBLE);
        inputSecond.setVisibility(View.VISIBLE);
        //stop the stop watch first
        stopped = true;
        stopTimer = true;

        timertxt.setText("set the timer first:");

        starttimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            long H = 0;
            long M = 0;
            long S = 0;

            String sH = String.valueOf(inputHour.getText());
            String sM = String.valueOf(inputMinute.getText());
            String sS = String.valueOf(inputSecond.getText());

                if (!sH.isEmpty()) {
                    try {
                        H = Long.parseLong(sH);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                if (!sM.isEmpty()) {
                    try {

                        M = Long.parseLong(sM);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                if (!sS.isEmpty()) {
                    try {
                        S = Long.parseLong(sS);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }



                CountDownTimer count = new CountDownTimer(convertToMilliseconds(H,M,S) , 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (timerMode) {
                            NumberFormat NF = new DecimalFormat("00");
                            long hours = (millisUntilFinished / 360000000) % 24;
                            long minutes = (millisUntilFinished / 60000) % 60;
                            long seconds = (millisUntilFinished / 1000) % 60;

                            timertxt.setText(NF.format(hours) + ":" + NF.format(minutes) + ":" + NF.format(seconds));
                            starttimer.setVisibility(View.INVISIBLE);
                            inputHour.setVisibility(View.INVISIBLE);
                            inputMinute.setVisibility(View.INVISIBLE);
                            inputSecond.setVisibility(View.INVISIBLE);

                        }


                    }

                    @Override
                    public void onFinish() {
                        if (timerMode) {
                            timertxt.setText("00:00:00");
                            starttimer.setVisibility(View.VISIBLE);
                            inputHour.setVisibility(View.VISIBLE);
                            inputMinute.setVisibility(View.VISIBLE);
                            inputSecond.setVisibility(View.VISIBLE);
                            Alarmbeep();
                            // the text view effect
                            effect.setTarget(timertxt);
                            effect.setPropertyName("alpha");
                            effect.setFloatValues((float) (0) , (float) (1));
                            effect.setDuration((int)(1000));
                            effect.setRepeatMode(ValueAnimator.REVERSE);
                            effect.setRepeatCount((int)(10));
                            effect.start();





                        }
                    }
                }.start();



            }
        });

    }

    public long convertToMilliseconds(long Hours , long Minutes , long Seconds) {

            long H = 0;
            long M = 0;
            long S = 0;

        if (!(Hours == 0)) {
            H =  Hours * 360000000;
        }


        if (!(Minutes == 0)) {
             M =  Minutes * 60000;
        }


        if (!(Seconds == 0)) {
             S = Seconds * 1000;
        }

        return H + M + S;

    }
    public void Alarmbeep() {
      beeps = 0;
        AudioAttributes audioAttributes= new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType( AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        sp = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(audioAttributes).build();
        int alarm = sp.load(getApplicationContext(),R.raw.beep,0);
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {



                Timer alarmtimer = new Timer();
                final Handler handler = new Handler();
                TimerTask TK = new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {

                            public void run() {
                                if (!stopWatchMode) {
                                    if (!(beeps >= 10)) {
                                        beeps++;
                                        sp.play(alarm, 1, 1, 0, 0, 1);

                                    } else {
                                        alarmtimer.cancel();
                                    }
                                } else {
                                    alarmtimer.cancel();
                                }


                            }

                        });


                    }
                };

                alarmtimer.schedule(TK, 1100, 1100);



            }
        });











    }

}
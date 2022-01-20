package com.example.scoreboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SetDefaults extends AppCompatActivity {
    EditText mainMin, mainSec, shortC, timeout, noOfTimeouts, betweenQ, fouls;
    int mainMinValue, mainSecValue, shortCValue, timeoutValue, noOfTimeoutsValue, betweenQValue, foulsValue;
    public static final String MAIN_TIMER_MIN = "MAIN_TIMER_MIN";
    public static final String MAIN_TIMER_SEC = "MAIN_TIMER_SEC";
    public static final String SHORT_CLOCK = "SHORT_CLOCK";
    public static final String TIMEOUT = "TIMEOUT";
    public static final String QUARTER = "QUARTER";
    public static final String FOULS = "FOULS";
    public static final String NUMBER_TIMEOUTS = "NUMBER_TIMEOUTS";
    public static final String VALUES = "VALUES";
    ImageView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_defaults);
        mainMin = findViewById(R.id.mainTimerMIN);
        mainSec = findViewById(R.id.mainTimerSEC);
        shortC = findViewById(R.id.shortTime_sec);
        timeout = findViewById(R.id.timeout_sec);
        betweenQ = findViewById(R.id.quarter_sec);
        fouls = findViewById(R.id.foulsPerson);
        noOfTimeouts = findViewById(R.id.no_of_timeouts);

        save = findViewById(R.id.save);


        SharedPreferences sharedPreferences = getSharedPreferences(VALUES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        mainMinValue = sharedPreferences.getInt(MAIN_TIMER_MIN,0);
        mainSecValue = sharedPreferences.getInt(MAIN_TIMER_SEC,0);
        shortCValue = sharedPreferences.getInt(SHORT_CLOCK,0);
        timeoutValue = sharedPreferences.getInt(TIMEOUT,0);
        betweenQValue = sharedPreferences.getInt(QUARTER,0);
        noOfTimeoutsValue = sharedPreferences.getInt(NUMBER_TIMEOUTS,0);
        foulsValue = sharedPreferences.getInt(FOULS,0);

        mainMin.setText(String.valueOf(mainMinValue));
        mainSec.setText(String.valueOf(mainSecValue));
        shortC.setText(String.valueOf(shortCValue));
        timeout.setText(String.valueOf(timeoutValue));
        betweenQ.setText(String.valueOf(betweenQValue));
        noOfTimeouts.setText(String.valueOf(noOfTimeoutsValue));
        fouls.setText(String.valueOf(foulsValue));

        save.setOnClickListener(view -> {
            mainMinValue = getValue(mainMin);
            mainSecValue = getValue(mainSec);
            shortCValue = getValue(shortC);
            timeoutValue = getValue(timeout);
            betweenQValue = getValue(betweenQ);
            noOfTimeoutsValue = getValue(noOfTimeouts);
            foulsValue = getValue(fouls);
        });

    }

    public int getValue(EditText editText){
        int val = 0;
        if(!editText.getText().toString().equals(""))
            val = Integer.parseInt(editText.getText().toString());
        return val;
    }
}
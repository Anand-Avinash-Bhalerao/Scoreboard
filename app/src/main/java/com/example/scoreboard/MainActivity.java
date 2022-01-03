package com.example.scoreboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NameDialog.ExampleDialogListener {
    private static long MAIN_START_TIME_IN_MILLIS = 600000;
    //    private static long MAIN_START_TIME_IN_MILLIS = 10000;
    private static long SHORT_START_TIME_IN_MILLIS = 24000;
    private long mMainTimeLeftInMillis = MAIN_START_TIME_IN_MILLIS;
    private long mShortTimeLeftInMillis = SHORT_START_TIME_IN_MILLIS;
    private boolean mMainRunning = true;
    int team1_score = 0;
    int team2_score = 0;
    int currentQuarter = 1;
    Button t1_1, t1_2, t1_3, t1_min1, t2_1, t2_2, t2_3, t2_min1, team1_name_button, team2_name_button;
    TextView t1_score, t2_score, main_timer, short_clock, quarter;
    ImageView t1PossesionGiver, t2PossesionGiver, t1PossesionBall, t2PossesionBall, refresh, play_pause;

    ArrayList<String> team1Players;
    ArrayList<String> team2Players;
    HashMap<String, Integer> values;


    ArrayList<PersonInfo> team1MemberInfo;
    ArrayList<PersonInfo> team2MemberInfo;

    CountDownTimer mainTimerCountdown;
    CountDownTimer shortClockCountdown;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = MainActivity.this;
        //Buttons to increase or decrease scores
        t1_1 = findViewById(R.id.team1_1);
        t1_2 = findViewById(R.id.team1_2);
        t1_3 = findViewById(R.id.team1_3);
        t1_min1 = findViewById(R.id.team1_min1);
        t2_1 = findViewById(R.id.team2_1);
        t2_2 = findViewById(R.id.team2_2);
        t2_3 = findViewById(R.id.team2_3);
        t2_min1 = findViewById(R.id.team2_min1);

        //TextViews scores and timers
        t1_score = findViewById(R.id.team1_score);
        t2_score = findViewById(R.id.team2_score);
        main_timer = findViewById(R.id.main_timer);
        short_clock = findViewById(R.id.short_clock);

        t1PossesionGiver = findViewById(R.id.team1_pos_give);
        t2PossesionGiver = findViewById(R.id.team2_pos_give);
        t1PossesionBall = findViewById(R.id.team1_pos);
        t2PossesionBall = findViewById(R.id.team2_pos);

        refresh = findViewById(R.id.reset_short_clock);
        play_pause = findViewById(R.id.reset_pause_play);

        quarter = findViewById(R.id.quarter);

        team1_name_button = findViewById(R.id.team1_players);
        team2_name_button = findViewById(R.id.team2_players);


        Intent intent = getIntent();
        String team1Name = intent.getStringExtra(MatchSettings.TEAM1);
        String team2Name = intent.getStringExtra(MatchSettings.TEAM2);

        if(team1Name.equals("")){
            team1Name = "Team 1";
        }
        if(team2Name.equals("")){
            team2Name = "Team 2";
        }

        try {
            team1Players = new ArrayList<>((ArrayList<String>) getIntent().getSerializableExtra(MatchSettings.TEAM1_ARRAY));
            team2Players = new ArrayList<>((ArrayList<String>) getIntent().getSerializableExtra(MatchSettings.TEAM2_ARRAY));
            values = (HashMap<String, Integer>) intent.getSerializableExtra(MatchSettings.VAL_MAP);
            MAIN_START_TIME_IN_MILLIS = values.get(MatchSettings.MAIN_TIMER_MIN) * 60 * 1000 + values.get(MatchSettings.MAIN_TIMER_SEC) * 1000;
            SHORT_START_TIME_IN_MILLIS = values.get(MatchSettings.SHORT_CLOCK) * 1000;
            mMainTimeLeftInMillis = MAIN_START_TIME_IN_MILLIS;
            mShortTimeLeftInMillis = SHORT_START_TIME_IN_MILLIS;
        } catch (Exception e) {
            Toast.makeText(this, "Abe values hi nahi dali mahan", Toast.LENGTH_LONG).show();
        }
        team1_name_button.setText(team1Name);
        team2_name_button.setText(team2Name);

        team1MemberInfo = new ArrayList<>();
        team2MemberInfo = new ArrayList<>();

        for (int i = 0; i < team1Players.size(); i++) {
            PersonInfo temp = new PersonInfo(team1Players.get(i));
            team1MemberInfo.add(temp);
        }

        for (int i = 0; i < team2Players.size(); i++) {
            PersonInfo temp = new PersonInfo(team2Players.get(i));
            team2MemberInfo.add(temp);
        }


        t1_1.setOnClickListener(view -> {
            team1_score += 1;
            team1_set();
        });
        t1_2.setOnClickListener(view -> {
            team1_score += 2;
            team1_set();
        });
        t1_3.setOnClickListener(view -> {
            team1_score += 3;
            team1_set();
        });

        t1_min1.setOnClickListener(view -> {
            if (team1_score > 0) {
                team1_score -= 1;
                team1_set();
            } else {
                Toast.makeText(this, "Score cannot be negative", Toast.LENGTH_SHORT).show();
            }
        });

        t2_1.setOnClickListener(view -> {
            team2_score += 1;
            team2_set();
        });
        t2_2.setOnClickListener(view -> {
            team2_score += 2;
            team2_set();
        });
        t2_3.setOnClickListener(view -> {
            team2_score += 3;
            team2_set();
        });
        t2_min1.setOnClickListener(view -> {
            if (team2_score > 0) {
                team2_score -= 1;
                team2_set();
            } else {
                Toast.makeText(this, "Score cannot be negative", Toast.LENGTH_SHORT).show();
            }
        });

        t1PossesionGiver.setOnClickListener(view -> {
            t1PossesionBall.setVisibility(View.VISIBLE);
            t1PossesionGiver.setImageResource(R.drawable.left_red);
            t2PossesionGiver.setImageResource(R.drawable.right);
            t2PossesionBall.setVisibility(View.INVISIBLE);
        });

        t2PossesionGiver.setOnClickListener(view -> {
            t2PossesionBall.setVisibility(View.VISIBLE);
            t1PossesionGiver.setImageResource(R.drawable.left);
            t2PossesionGiver.setImageResource(R.drawable.right_red);
            t1PossesionBall.setVisibility(View.INVISIBLE);
        });

        play_pause.setOnClickListener(view -> {
            setPlay_pause();
        });

        refresh.setOnClickListener(view -> {
            if (mShortTimeLeftInMillis != SHORT_START_TIME_IN_MILLIS)
                resetShortTimer();
            else
                Toast.makeText(this, "PRESS THE PLAY BUTTON TO CONTINUE", Toast.LENGTH_SHORT).show();
        });

        startShortTimer();
        startMainTimer();

        team1_name_button.setOnClickListener(view -> {
            openDialog(1);
        });
        team2_name_button.setOnClickListener(view -> {
            openDialog(2);
        });
    }

    public void openDialog(int teamNumber) {
        if (teamNumber == 1) {
            NameDialog nameDialog = new NameDialog(team1MemberInfo, 1, this);
            nameDialog.show(getSupportFragmentManager(), "Example dialog");
        } else if (teamNumber == 2) {
            NameDialog nameDialog = new NameDialog(team2MemberInfo, 2, this);
            nameDialog.show(getSupportFragmentManager(), "Example dialog");
        }
    }

    public void team1_set() {
        String toSet = "";
        if (team1_score < 10) {
            toSet = "0" + team1_score;
        } else {
            toSet = String.valueOf(team1_score);
        }
        t1_score.setText(toSet);
    }

    public void team2_set() {
        String toSet = "";
        if (team2_score < 10) {
            toSet = "0" + team2_score;
        } else {
            toSet = String.valueOf(team2_score);
        }
        t2_score.setText(toSet);
    }

    private void updateCountDownText() {
        int min = (int) (mMainTimeLeftInMillis / 1000) / 60;
        int sec = (int) (mMainTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        main_timer.setText(timeLeftFormatted);
    }

    public void startMainTimer() {
        mainTimerCountdown = new CountDownTimer(mMainTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mMainTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                currentQuarter++;
                if (currentQuarter <= 4) {
                    quarter.setText(String.valueOf("Quarter: " + currentQuarter));
                    pauseMainTimer();
                    mMainTimeLeftInMillis = MAIN_START_TIME_IN_MILLIS;
                    mShortTimeLeftInMillis = SHORT_START_TIME_IN_MILLIS;
                    startMainTimer();
                    resetShortTimer();
                } else {
                    pauseShortTimer();
                    if (team1_score > team2_score)
                        Toast.makeText(context, "Team1 wins!", Toast.LENGTH_SHORT).show();
                    else if (team1_score < team2_score)
                        Toast.makeText(context, "Team2 wins!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show();


                }
            }
        }.start();

        mMainRunning = true;
    }

    public void startShortTimer() {
        shortClockCountdown = new CountDownTimer(mShortTimeLeftInMillis, 10) {
            @Override
            public void onTick(long l) {
                mShortTimeLeftInMillis = l;
                float val = (float) l / 1000;
                if (val < 5) {
                    short_clock.setTextColor(ContextCompat.getColor(context, R.color.red));
                    String formatted = String.format(Locale.getDefault(), "%.1f", val);
                    short_clock.setText(formatted);
                } else {
                    short_clock.setText(String.valueOf(l / 1000));

                }

            }

            @Override
            public void onFinish() {
                mShortTimeLeftInMillis = 0;
                setPlay_pause();
            }
        }.start();

        mMainRunning = true;
    }

    public void pauseMainTimer() {
        mainTimerCountdown.cancel();
        mMainRunning = false;
    }

    public void pauseShortTimer() {
        shortClockCountdown.cancel();
        mMainRunning = false;
    }

    public void resetShortTimer() {
        short_clock.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        pauseShortTimer();
        mMainRunning = true;
        mShortTimeLeftInMillis = SHORT_START_TIME_IN_MILLIS;
        startShortTimer();
    }

    public void setPlay_pause() {
        if (mShortTimeLeftInMillis == 0) {
            mShortTimeLeftInMillis = SHORT_START_TIME_IN_MILLIS;
        }
        if (mMainRunning) {
            pauseMainTimer();
            pauseShortTimer();
            play_pause.setImageResource(R.drawable.play);
        } else {
            if (mShortTimeLeftInMillis > 5000)
                short_clock.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            startMainTimer();
            startShortTimer();
            play_pause.setImageResource(R.drawable.pause);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setMessage("Are you sure you want to stop the timer?")
                .setPositiveButton("YES", (dialogInterface, i) -> finish())
                .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }

    @Override
    public void applyChanges(ArrayList<PersonInfo> list, int teamNumber) {
        String val = "\n";
        if (teamNumber == 1) {
            team1MemberInfo = list;
            for (int i = 0; i < team1MemberInfo.size(); i++) {
                val += team1MemberInfo.get(i).getName() + ": " + team1MemberInfo.get(i).getScore() + "\n";
            }
        } else {
            team2MemberInfo = list;
            for (int i = 0; i < team2MemberInfo.size(); i++) {
                val += team2MemberInfo.get(i).getName() + ": " + team2MemberInfo.get(i).getScore() + "\n";
            }
        }


        Log.d("VALUES_AFTER_CHANGE", val);
    }
}
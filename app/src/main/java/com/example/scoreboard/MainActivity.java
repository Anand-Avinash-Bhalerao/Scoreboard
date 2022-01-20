package com.example.scoreboard;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NameDialog.ExampleDialogListener, PlayerScoreRecyclerAdapter.OnNoteClickListener, FoulDialog.FoulDialogInterface {
    private static long MAIN_START_TIME_IN_MILLIS = 600000;
    //    private static long MAIN_START_TIME_IN_MILLIS = 10000;
    private static long SHORT_START_TIME_IN_MILLIS = 24000;
    private static final long SHORT_14_START_TIME_IN_MILLIS = 14000;
    private long mMainTimeLeftInMillis = MAIN_START_TIME_IN_MILLIS;
    private long mShortTimeLeftInMillis = SHORT_START_TIME_IN_MILLIS;
    private boolean mMainRunning = true;
    private MediaPlayer referee, buzzer;
    int team1_score = 0;
    int team2_score = 0;
    int currentQuarter = 1;

    int team1_timeoutsCounter = 0;
    int team2_timeoutsCounter = 0;
    int timeout_time = 20;
    int noOfTimeouts = 5;


    TextView team1_name_button, team2_name_button, t1_timeoutLeft, t2_timeoutLeft;
    TextView t1_score, t2_score, main_timer, short_clock, quarter;
    ImageView t1PossesionGiver, t2PossesionGiver, refresh, play_pause,refresh14;
    ConstraintLayout team1_foul, team2_foul, team1_timeout, team2_timeout;
    RecyclerView team1, team2;

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

        //TextViews scores and timers
        t1_score = findViewById(R.id.team1_score);
        t2_score = findViewById(R.id.team2_score);
        main_timer = findViewById(R.id.main_timer);
        short_clock = findViewById(R.id.short_clock);

        t1PossesionGiver = findViewById(R.id.team1_pos_give);
        t2PossesionGiver = findViewById(R.id.team2_pos_give);

        refresh = findViewById(R.id.reset_short_clock);
        refresh14 = findViewById(R.id.reset_short_clock_14);
        play_pause = findViewById(R.id.reset_pause_play);

        quarter = findViewById(R.id.quarter);

        team1_name_button = findViewById(R.id.team1_team_name);
        team2_name_button = findViewById(R.id.team2_team_name);

        team1 = findViewById(R.id.team1_recycler);
        team2 = findViewById(R.id.team2_recycler);

        team1_foul = findViewById(R.id.team1_foul);
        team2_foul = findViewById(R.id.team2_foul);

        team1_timeout = findViewById(R.id.team1_timeouts);
        team2_timeout = findViewById(R.id.team2_timeouts);

        t1_timeoutLeft = findViewById(R.id.team1_timeout_left);
        t2_timeoutLeft = findViewById(R.id.team2_timeout_left);


        referee = MediaPlayer.create(this, R.raw.referee);
        buzzer = MediaPlayer.create(this, R.raw.buzzer);

        Intent intent = getIntent();
        String team1Name = intent.getStringExtra(MatchSettings.TEAM1);
        String team2Name = intent.getStringExtra(MatchSettings.TEAM2);

        if (team1Name.equals("")) {
            team1Name = "Team 1";
        }
        if (team2Name.equals("")) {
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
//        noOfTimeouts = intent.getIntExtra(MatchSettings.NUMBER_TIMEOUTS, 5);
//        timeout_time = intent.getIntExtra(MatchSettings.TIMEOUT, 10);
        if (values.containsKey(MatchSettings.NUMBER_TIMEOUTS))
            noOfTimeouts = values.get(MatchSettings.NUMBER_TIMEOUTS);
        if (values.containsKey(MatchSettings.TIMEOUT))
            timeout_time = values.get(MatchSettings.TIMEOUT);
        t1_timeoutLeft.setText(String.valueOf(noOfTimeouts - team1_timeoutsCounter));
        t2_timeoutLeft.setText(String.valueOf(noOfTimeouts - team2_timeoutsCounter));

//        team1Players = new ArrayList<>();
//        team1Players.add("ANAND");
//        team1Players.add("AKSHAY");
//        team1Players.add("MUMMY");
//        team1Players.add("PAPA");
//
//        team2Players = new ArrayList<>();
//        team2Players.add("LAXMAN");
//        team2Players.add("RAJAN");
//        team2Players.add("MADHWAN");
//        team2Players.add("ROHIT");


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

        t1PossesionGiver.setOnClickListener(view -> {
            t1PossesionGiver.setImageResource(R.drawable.left_red);
            t2PossesionGiver.setImageResource(R.drawable.right);

        });

        t2PossesionGiver.setOnClickListener(view -> {
            t1PossesionGiver.setImageResource(R.drawable.left);
            t2PossesionGiver.setImageResource(R.drawable.right_red);
        });

        play_pause.setOnClickListener(view -> setPlay_pause());

        refresh.setOnClickListener(view -> {
            if (mShortTimeLeftInMillis != SHORT_START_TIME_IN_MILLIS && mMainRunning)
                resetShortTimer();
            else
                Toast.makeText(this, "PRESS THE PLAY BUTTON TO CONTINUE", Toast.LENGTH_SHORT).show();
        });

        startShortTimer();
        startMainTimer();

        team1_name_button.setOnClickListener(view -> openDialog(1));
        team2_name_button.setOnClickListener(view -> openDialog(2));

        PlayerScoreRecyclerAdapter forTeam1 = new PlayerScoreRecyclerAdapter(this, team1MemberInfo, this, 1);
        team1.setAdapter(forTeam1);
        team1.setLayoutManager(new LinearLayoutManager(this));

        PlayerScoreRecyclerAdapter forTeam2 = new PlayerScoreRecyclerAdapter(this, team2MemberInfo, this, 2);
        team2.setAdapter(forTeam2);
        team2.setLayoutManager(new LinearLayoutManager(this));

        team1_foul.setOnClickListener(view -> {
            FoulDialog foulDialog = new FoulDialog(team1MemberInfo, 1, this);
            foulDialog.show(getSupportFragmentManager(), "Example dialog");
        });

        team2_foul.setOnClickListener(view -> {
            FoulDialog foulDialog = new FoulDialog(team2MemberInfo, 2, this);
            foulDialog.show(getSupportFragmentManager(), "Example dialog");
        });

        team1_timeout.setOnClickListener(view -> {
            if (team1_timeoutsCounter < noOfTimeouts) {
                TimeoutDialog timeoutDialog = new TimeoutDialog(1, this, timeout_time, true);
                timeoutDialog.show(getSupportFragmentManager(), "TEMP");
                team1_timeoutsCounter++;
                setPause();
            } else {
                Toast.makeText(this, "No timeouts left! :(", Toast.LENGTH_SHORT).show();
            }
            t1_timeoutLeft.setText(String.valueOf(noOfTimeouts - team1_timeoutsCounter));
        });
        team2_timeout.setOnClickListener(view -> {
            if (team2_timeoutsCounter < noOfTimeouts) {
                TimeoutDialog timeoutDialog = new TimeoutDialog(2, this, timeout_time, true);
                timeoutDialog.show(getSupportFragmentManager(), "TEMP");
                team2_timeoutsCounter++;
                setPause();
            } else {
                Toast.makeText(this, "No timeouts left! :(", Toast.LENGTH_SHORT).show();
            }
            t2_timeoutLeft.setText(String.valueOf(noOfTimeouts - team2_timeoutsCounter));

        });

        refresh14.setOnClickListener(view -> {
            resetShortTimer14();
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
                buzzer.start();
                currentQuarter++;
                if (currentQuarter <= 4) {
                    quarter.setText("Quarter: " + currentQuarter);
//                    pauseMainTimer();
                    mMainTimeLeftInMillis = MAIN_START_TIME_IN_MILLIS;
                    mShortTimeLeftInMillis = SHORT_START_TIME_IN_MILLIS;
                    TimeoutDialog timeoutDialog = new TimeoutDialog(0, MainActivity.this, timeout_time, false);
                    timeoutDialog.show(getSupportFragmentManager(), "TEMP");
                    setPause();
//                    startMainTimer();
//                    resetShortTimer();
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
                buzzer.start();
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

    public void resetShortTimer14(){
        short_clock.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        pauseShortTimer();
        mMainRunning = true;
        mShortTimeLeftInMillis = SHORT_14_START_TIME_IN_MILLIS;
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

    public void setPause(){
        if (mShortTimeLeftInMillis == 0) {
            mShortTimeLeftInMillis = SHORT_START_TIME_IN_MILLIS;
        }
        if (mMainRunning) {
            pauseMainTimer();
            pauseShortTimer();
            play_pause.setImageResource(R.drawable.play);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setMessage("Are you sure you want to stop the timer?")
                .setPositiveButton("YES", (dialogInterface, i) -> {
                    referee.stop();
                    buzzer.stop();
                    finish();
                })
                .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }

    @Override
    public void applyChanges(ArrayList<PersonInfo> list, int teamNumber) {
        if (teamNumber == 1) {
            team1MemberInfo = list;
        } else {
            team2MemberInfo = list;
        }

    }

    @Override
    public void plus1Click(int pos, int teamNo) {
        if (teamNo == 1) {
            team1_score += 1;
            t1_score.setText(String.valueOf(team1_score));
        } else if (teamNo == 2) {
            team2_score += 1;
            t2_score.setText(String.valueOf(team2_score));
        }
    }

    @Override
    public void plus2Click(int pos, int teamNo) {
        if (teamNo == 1) {
            team1_score += 2;
            t1_score.setText(String.valueOf(team1_score));
        } else if (teamNo == 2) {
            team2_score += 2;
            t2_score.setText(String.valueOf(team2_score));
        }
    }

    @Override
    public void plus3Click(int pos, int teamNo) {
        if (teamNo == 1) {
            team1_score += 3;
            t1_score.setText(String.valueOf(team1_score));
        } else if (teamNo == 2) {
            team2_score += 3;
            t2_score.setText(String.valueOf(team2_score));
        }
    }

    @Override
    public void saveChanges(ArrayList<PersonInfo> list, int teamNumber, boolean call, boolean changesDone) {
        if (call) {
            if (teamNumber == 1) {
                team1MemberInfo = list;
            } else {
                team2MemberInfo = list;
            }
            if (changesDone) {
                //make sound
                referee.start();
                Toast.makeText(this, "MAKE SOUND", Toast.LENGTH_SHORT).show();
            }
            setPlay_pause();
        } else {
            Toast.makeText(this, "No Whistle", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.scoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchSettings extends AppCompatActivity {

    EditText team1Name, team2Name, team1_addName, team2_addName;
    EditText mainMin, mainSec, shortC, timeout, noOfTimeouts, betweenQ, fouls;
    ImageView team1Add, team2Add;
    ImageView play;
    String team1 = "";
    String team2 = "";
    LinearLayout team1Members, team2Members;

    HashMap<String, Integer> values;

    public static final String TEAM1 = "TEAM1";
    public static final String TEAM1_ARRAY = "TEAM1_ARRAY";
    public static final String TEAM2 = "TEAM2";
    public static final String TEAM2_ARRAY = "TEAM2_ARRAY";
    public static final String VAL_MAP = "VAL_MAP";
    public static final String MAIN_TIMER_MIN = "MAIN_TIMER_MIN";
    public static final String MAIN_TIMER_SEC = "MAIN_TIMER_SEC";
    public static final String SHORT_CLOCK = "SHORT_CLOCK";
    public static final String TIMEOUT = "TIMEOUT";
    public static final String QUARTER = "QUARTER";
    public static final String FOULS = "FOULS";
    public static final String NUMBER_TIMEOUTS = "NUMBER_TIMEOUTS";

    private ArrayList<String> team1Players;
    private ArrayList<String> team2Players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        team1Add = findViewById(R.id.team1_add);
        team2Add = findViewById(R.id.team2_add);
        team1Name = findViewById(R.id.team1_name);
        team2Name = findViewById(R.id.team2_name);
        team1_addName = findViewById(R.id.team1_name_add_EditText);
        team2_addName = findViewById(R.id.team2_name_add_EditText);

        play = findViewById(R.id.start);

        team1Members = findViewById(R.id.team1_members_layout);
        team2Members = findViewById(R.id.team2_members_layout);

        team1Players = new ArrayList<>();
        team2Players = new ArrayList<>();

        mainMin = findViewById(R.id.mainTimerMIN);
        mainSec = findViewById(R.id.mainTimerSEC);
        shortC = findViewById(R.id.shortTime_sec);
        timeout = findViewById(R.id.timeout_sec);
        betweenQ = findViewById(R.id.quarter_sec);
        fouls = findViewById(R.id.foulsPerson);
        noOfTimeouts = findViewById(R.id.no_of_timeouts);

        values = new HashMap<>();


        team1Name.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                team1 = team1Name.getText().toString();
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        team2Name.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                team2 = team2Name.getText().toString();
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        mainMin.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String valString = mainMin.getText().toString();
                int val = 0;
                if (!valString.equals(""))
                    val = Integer.parseInt(mainMin.getText().toString());
                values.put(MAIN_TIMER_MIN, val);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });
        mainSec.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String valString = mainSec.getText().toString();
                int val = 0;
                if (!valString.equals(""))
                    val = Integer.parseInt(mainSec.getText().toString());
                values.put(MAIN_TIMER_SEC, val);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });
        shortC.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String value = shortC.getText().toString();
                int val = 0;
                if (!value.equals(""))
                    val = Integer.parseInt(shortC.getText().toString());
                values.put(SHORT_CLOCK, val);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });
        timeout.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String value = timeout.getText().toString();
                int val = 0;
                if (!value.equals(""))
                    val = Integer.parseInt(timeout.getText().toString());
                values.put(TIMEOUT, val);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        noOfTimeouts.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String value = noOfTimeouts.getText().toString();
                int val = 0;
                if (!value.equals(""))
                    val = Integer.parseInt(noOfTimeouts.getText().toString());
                values.put(NUMBER_TIMEOUTS, val);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });
        betweenQ.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String value = betweenQ.getText().toString();
                int val = 0;
                if (!value.equals(""))
                    val = Integer.parseInt(betweenQ.getText().toString());
                values.put(QUARTER, val);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        fouls.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                String value = fouls.getText().toString();
                int val = 0;
                if (!value.equals(""))
                    val = Integer.parseInt(fouls.getText().toString());
                values.put(FOULS, val);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        team1Add.setOnClickListener(view -> {
            String name = team1_addName.getText().toString().toUpperCase();
            team1_addName.setText("");
            addViewTeam1(name);
        });
        team2Add.setOnClickListener(view -> {
            String name = team2_addName.getText().toString().toUpperCase();
            team2_addName.setText("");

            addViewTeam2(name);
        });

        play.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(TEAM1, team1);
            intent.putExtra(TEAM2, team2);
            intent.putExtra(TEAM1_ARRAY, team1Players);
            intent.putExtra(TEAM2_ARRAY, team2Players);
            intent.putExtra(VAL_MAP, values);
            startActivity(intent);
        });
    }

    public void addViewTeam1(String name) {
        View itemView = getLayoutInflater().inflate(R.layout.list_item, null, false);
        TextView textViewName = itemView.findViewById(R.id.name_text);
        textViewName.setText(name);
        ImageView delete = itemView.findViewById(R.id.delete_item);

        delete.setOnClickListener(view -> {
            team1Members.removeView(itemView);
            team1Players.remove(name);

        });
        team1Members.addView(itemView);
        team1Players.add(name);
    }

    public void addViewTeam2(String name) {
        View itemView = getLayoutInflater().inflate(R.layout.list_item, null, false);
        TextView textViewName = itemView.findViewById(R.id.name_text);
        textViewName.setText(name);
        ImageView delete = itemView.findViewById(R.id.delete_item);

        delete.setOnClickListener(view -> {
            team2Members.removeView(itemView);
            team2Players.remove(name);
        });
        team2Members.addView(itemView);
        team2Players.add(name);

    }
}
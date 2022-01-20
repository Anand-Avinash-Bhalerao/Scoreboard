package com.example.scoreboard;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.concurrent.TimeUnit;

public class TimeoutDialog extends AppCompatDialogFragment {
    private int teamNumber;
//    private ExampleDialogListener listener;
    Context context;
    private int time;
    private TextView timeTextView;
    CountDownTimer countDownTimer;
    int currentTime;
    String title;
    boolean isATimeout;
    private MediaPlayer whistle;
    public TimeoutDialog(int teamNumber, Context context,int time,boolean isATimeout){
        this.context = context;
        this.teamNumber = teamNumber;
        this.time = time;
        this.isATimeout = isATimeout;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        whistle = MediaPlayer.create(context,R.raw.referee);
        whistle.start();
        if(isATimeout)
            title = "TIMEOUT!";
        else
            title = "QUARTER OVER!";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.timeout_dialog,null);
        builder.setView(view)
                .setTitle(title);

        timeTextView = view.findViewById(R.id.time);
        timeTextView.setText(String.valueOf(time));
        currentTime = time;
//        progressBar.setProgress(50);
        countDownTimer = new CountDownTimer((long)time*1000,1000) {
            @Override
            public void onTick(long l) {
                String val = "";
                int min = currentTime/60;
                int sec = currentTime%60;
                String secS="";
                String minS="";
                if(sec<10){
                    secS = "0" + sec;
                }
                else
                    secS = sec+"";
                if(min<10){
                    minS = "0"+min;
                }
                else minS = min+"";
                val = minS+":"+secS;


                timeTextView.setText(val);
                currentTime--;
            }

            @Override
            public void onFinish() {
//                Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                whistle.start();
                dialog.dismiss();
                dismiss();
            }
        }.start();


//        return dialog;
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
//            listener =(ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement ExampleDialogListener");
        }
    }

//    public interface ExampleDialogListener{
//        void applyChanges(ArrayList<PersonInfo> list,int teamNumber);
//    }
}

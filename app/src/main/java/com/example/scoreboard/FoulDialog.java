package com.example.scoreboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoulDialog extends AppCompatDialogFragment implements FoulAdapter.MadeChanges{
    private RecyclerView recyclerView;
    private ArrayList<PersonInfo> info;
    private ArrayList<PersonInfo> ORIGNAL;
    private int teamNumber;
    private FoulDialogInterface listener;
    private boolean changesDone;
    Context context;
    public FoulDialog(ArrayList<PersonInfo> containsInfo, int teamNumber, Context context){
        info = containsInfo;
        ORIGNAL = new ArrayList<>();
        for(int i = 0;i<containsInfo.size();i++){
            PersonInfo toAdd = new PersonInfo();
            PersonInfo temp = containsInfo.get(i);
            toAdd.setName(temp.getName());
            toAdd.setScore(temp.getScore());
            toAdd.setFouls(temp.getFouls());
            toAdd.setPlus1(temp.getPlus1());
            toAdd.setPlus2(temp.getPlus2());
            toAdd.setPlus3(temp.getPlus3());
            ORIGNAL.add(toAdd);
        }
        this.context = context;
        this.changesDone = false;
        this.teamNumber = teamNumber;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Toast.makeText(context, "Created dialog", Toast.LENGTH_SHORT).show();
        this.changesDone = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add,null);
        builder.setView(view)
                .setTitle("Mark the foul for the player: ")
                .setPositiveButton("Whistle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            listener.saveChanges(info, teamNumber, true,changesDone);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.saveChanges(ORIGNAL, teamNumber, false, false);
                    }
                });

        recyclerView = view.findViewById(R.id.name_recyclerView);
        FoulAdapter recyclerAdapter = new FoulAdapter(context, info,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerAdapter);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener =(FoulDialogInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement ExampleDialogListener");
        }
    }

    @Override
    public void madeSomeChanges(boolean state) {
        this.changesDone = true;
    }

    public interface FoulDialogInterface {
        void saveChanges(ArrayList<PersonInfo> list,int teamNumber,boolean call,boolean changesDone);
    }
}


package com.example.scoreboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class NameDialog extends AppCompatDialogFragment {
    private RecyclerView recyclerView;
    private ArrayList<PersonInfo> info;
    private ArrayList<PersonInfo> ORIGNAL;
    private int teamNumber;
    private ExampleDialogListener listener;
    Context context;
    public NameDialog(ArrayList<PersonInfo> containsInfo,int teamNumber, Context context){
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
        this.teamNumber = teamNumber;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add,null);
        builder.setView(view)
                .setTitle("Enter the points for the players: ")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyChanges(info,teamNumber);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyChanges(ORIGNAL,teamNumber);
                    }
                });

        recyclerView = view.findViewById(R.id.name_recyclerView);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(context, info);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerAdapter);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener =(ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyChanges(ArrayList<PersonInfo> list,int teamNumber);
    }
}

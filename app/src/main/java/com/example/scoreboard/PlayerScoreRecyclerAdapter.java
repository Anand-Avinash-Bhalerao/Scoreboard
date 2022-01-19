package com.example.scoreboard;

import android.app.Person;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlayerScoreRecyclerAdapter extends RecyclerView.Adapter<PlayerScoreRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PersonInfo> list;
    private OnNoteClickListener onNoteClickListener;
    private int teamNo;

    public PlayerScoreRecyclerAdapter(Context context, ArrayList<PersonInfo> list, OnNoteClickListener onNoteClickListener, int no) {
        this.context = context;
        this.onNoteClickListener = onNoteClickListener;
        this.teamNo = no;
        this.list = list;
    }

    @NonNull
    @Override
    public PlayerScoreRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_recycler_score, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerScoreRecyclerAdapter.ViewHolder holder, int position) {
        PersonInfo personInfo = list.get(position);
        TextView name = holder.name;
        TextView plus1 = holder.plus1;
        TextView plus2 = holder.plus2;
        TextView plus3 = holder.plus3;
        TextView score = holder.score;

        name.setText(personInfo.getName());
        score.setText(String.valueOf(personInfo.getScore()));
        plus1.setOnClickListener(view -> {
            PersonInfo current = list.get(position);
            Log.d("SCORE_INC","Name - "+current.getName()+" and before - "+current.getScore());
            list.get(position).setScore(list.get(position).getScore()+1);
            Log.d("SCORE_INC","Name - "+personInfo.getName()+" and after - "+list.get(position).getScore());
            score.setText(String.valueOf(list.get(position).getScore()));
            onNoteClickListener.plus1Click(position,teamNo);
        });
        plus2.setOnClickListener(view -> {
            list.get(position).setScore(list.get(position).getScore()+2);
            score.setText(String.valueOf(list.get(position).getScore()));
            onNoteClickListener.plus2Click(position,teamNo);
        });
        plus3.setOnClickListener(view -> {
            list.get(position).setScore(list.get(position).getScore()+3);
            score.setText(String.valueOf(list.get(position).getScore()));
            onNoteClickListener.plus3Click(position,teamNo);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, plus1, plus2, plus3, score;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recycler_name);
            plus1 = itemView.findViewById(R.id.recycler_plus1);
            plus2 = itemView.findViewById(R.id.recycler_plus2);
            plus3 = itemView.findViewById(R.id.recycler_plus3);
            score = itemView.findViewById(R.id.recycler_score);
        }

    }

    public interface OnNoteClickListener {
        void plus1Click(int pos,int no);
        void plus2Click(int pos, int no);
        void plus3Click(int pos,int no);
    }
}

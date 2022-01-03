package com.example.scoreboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<PersonInfo> list;

    public RecyclerAdapter(Context context, List<PersonInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_player_scores_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        PersonInfo personInfo = list.get(position);
        TextView name = holder.name;
        TextView plus1 = holder.plus1;
        TextView plus2 = holder.plus2;
        TextView plus3 = holder.plus3;
        TextView score = holder.score;
        name.setText(personInfo.getName());
        score.setText(String.valueOf(personInfo.getScore()));
        plus1.setOnClickListener(view -> {
            int current = personInfo.getScore();
            current += 1;
            score.setText(String.valueOf(current));
//            Toast.makeText(context, "Name: " + personInfo.getName() + " increase by 1 and current" + current, Toast.LENGTH_SHORT).show();
            personInfo.setScore(current);
        });
        plus2.setOnClickListener(view -> {
            int current = personInfo.getScore();
            current += 2;
            score.setText(String.valueOf(current));
//            Toast.makeText(context, "Name: " + personInfo.getName() + " increase by 2 and current" + current, Toast.LENGTH_SHORT).show();
            personInfo.setScore(current);
        });
        plus3.setOnClickListener(view -> {
            int current = personInfo.getScore();
            current += 3;
            score.setText(String.valueOf(current));
//            Toast.makeText(context, "Name: " + personInfo.getName() + " increase by 3 and current" + current, Toast.LENGTH_SHORT).show();
            personInfo.setScore(current);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView plus1;
        private TextView plus2;
        private TextView plus3;
        private TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.player_name);
            plus1 = itemView.findViewById(R.id.plus1);
            plus2 = itemView.findViewById(R.id.plus2);
            plus3 = itemView.findViewById(R.id.plus3);
            score = itemView.findViewById(R.id.player_score);
        }
    }
}
package com.example.scoreboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class FoulAdapter extends RecyclerView.Adapter<FoulAdapter.ViewHolder> {
    private Context context;
    private List<PersonInfo> list;
    private MadeChanges madeChanges;

    public FoulAdapter(Context context, List<PersonInfo> list,MadeChanges madeChanges) {
        this.context = context;
        this.list = list;
        this.madeChanges = madeChanges;
    }
    @NonNull
    @Override
    public FoulAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foul_dialog_item, parent, false);
        FoulAdapter.ViewHolder holder = new FoulAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoulAdapter.ViewHolder holder, int position) {
        PersonInfo personInfo = list.get(position);
        TextView name = holder.name;
        TextView plus1 = holder.plus1;
        TextView fouls = holder.fouls;
        name.setText(personInfo.getName());
        fouls.setText(String.valueOf(personInfo.getFouls()));
        plus1.setOnClickListener(view -> {
            int current = personInfo.getFouls();
            current += 1;
            fouls.setText(String.valueOf(current));
            list.get(position).setFouls(list.get(position).getFouls()+1);
            madeChanges.madeSomeChanges(true);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView fouls;
        private TextView plus1;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.player_name);
            plus1 = itemView.findViewById(R.id.plus1);
            fouls = itemView.findViewById(R.id.player_fouls);
        }
    }


    public interface MadeChanges{
        public void madeSomeChanges(boolean state);
    }
}

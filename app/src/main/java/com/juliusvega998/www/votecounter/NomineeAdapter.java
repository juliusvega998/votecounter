package com.juliusvega998.www.votecounter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VegaCentre on 4/21/2017.
 */

public class NomineeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Nominee> nominees;
    private ArrayList<Integer> stack;

    public NomineeAdapter() {
        super();
        this.nominees = new ArrayList<>();
        this.stack = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView votes;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            votes = (TextView) itemView.findViewById(R.id.votes);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_nominee, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TextView votes = ((ViewHolder) holder).votes;
        votes.setText(Integer.toString(nominees.get(position).getVotes()));

        ((ViewHolder) holder).name.setText(nominees.get(position).getName());
        ((ViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votes.setText(Integer.toString(Integer.parseInt(votes.getText().toString())+1));
                stack.add(position);
                nominees.get(position).vote();
            }
        });
    }

    public void addNominee(Nominee n) {
        nominees.add(n);
    }

    public void undo() {
        if(stack.size() > 0) {
            int position = stack.remove(stack.size() - 1);
            nominees.get(position).undo();
        }
    }

    public void reset() {
        for(Nominee n : nominees) {
            n.reset();
        }

        stack.clear();
    }

    @Override
    public int getItemCount() {
        return nominees.size();
    }
}

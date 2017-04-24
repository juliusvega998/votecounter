package com.juliusvega998.www.votecounter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by VegaCentre on 4/21/2017.
 */

public class NomineeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Nominee> nominees;
    private ArrayList<Nominee> stack;

    public NomineeAdapter() {
        super();
        this.nominees = new ArrayList<>();
        this.stack = new ArrayList<>();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        private TextView votes;

        private ViewHolder(View itemView) {
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final TextView votes = ((ViewHolder) holder).votes;
        votes.setText(Integer.toString(nominees.get(position).getVotes()));

        ((ViewHolder) holder).name.setText(nominees.get(position).getName());
        ((ViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votes.setText(Integer.toString(Integer.parseInt(votes.getText().toString())+1));
                stack.add(nominees.get(holder.getAdapterPosition()));
                nominees.get(holder.getAdapterPosition()).vote();
            }
        });

        ((ViewHolder) holder).name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.delete_dialog_title);
                if(nominees.get(holder.getAdapterPosition()).getVotes() > 0) {
                    builder.setMessage(R.string.delete_dialog_desc_with_votes);
                } else {
                    builder.setMessage(R.string.delete_dialog_desc_without_votes);
                }

                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Nominee n = nominees.remove(holder.getAdapterPosition());
                        stack.removeAll(Collections.singleton(n));
                        NomineeAdapter.this.notifyDataSetChanged();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });
    }

    public void addNominee(Nominee n) {
        nominees.add(n);
    }

    public void undo() {
        if(stack.size() > 0) {
            Nominee n = stack.remove(stack.size() - 1);
            n.undo();
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

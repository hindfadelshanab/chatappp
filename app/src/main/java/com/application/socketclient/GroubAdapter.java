package com.application.socketclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroubAdapter extends RecyclerView.Adapter<GroubAdapter.GroubViewHolder> {

    private List<Groub> mGroubs;

    public GroubAdapter(List<Groub> Groubs) {
        this.mGroubs = Groubs;
    }

    @NonNull
    @Override
    public GroubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groub_item, parent, false);
        return new GroubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroubViewHolder holder, int position) {
        holder.onBind(mGroubs.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroubs.size();
    }


    public class GroubViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public GroubViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_groub_ame);


        }

        public void onBind(Groub item) {
          textView.setText(item.getGroubName());


        }
    }
}

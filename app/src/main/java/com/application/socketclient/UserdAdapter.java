package com.application.socketclient;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.socketio.client.Socket;

import java.util.List;

public class UserdAdapter extends RecyclerView.Adapter<UserdAdapter.userViewHolder> {

    private List<User> mUsers;
    OnItemClickListener onItemClickListener;
    public UserdAdapter(List<User> Users) {
        this.mUsers = Users;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        holder.onBind(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }



    public class userViewHolder extends RecyclerView.ViewHolder {

        TextView userame;
        TextView email;
        ImageView imageView;

        public userViewHolder(@NonNull final View itemView) {
            super(itemView);
               userame=itemView.findViewById(R.id.username);
               email=itemView.findViewById(R.id.email);
               imageView=itemView.findViewById(R.id.image_online);

               if (onItemClickListener !=null){
                   itemView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                          onItemClickListener.onItemClick(v,getAdapterPosition());
                       }
                   });
               }



        }

        public void onBind(User item) {

            userame.setText(item.getUsername());
            email.setText(item.getEmail());

        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}

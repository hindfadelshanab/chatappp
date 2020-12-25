package com.application.socketclient;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserdGroubAdapter extends RecyclerView.Adapter<UserdGroubAdapter.userViewHolder> {

    private final List<User> mUsers;
    private final List<User> usergroub;
    private Socket mSocket;
    ChatApplication app = new ChatApplication();

    OnItemClickListener onItemClickListener;
    OnItemSelectUser onItemSelectUser;
    Context context;

    public UserdGroubAdapter(Context context, List<User> Users, OnItemSelectUser onItemSelectUser) {
        this.mUsers = Users;
        this.onItemSelectUser = onItemSelectUser;
        this.context = context;
        usergroub = new ArrayList<>();
        mSocket = app.getSocket();

        mSocket.connect();
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_groub_item, parent, false);
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
        CheckBox checkBox;

        public userViewHolder(@NonNull final View itemView) {
            super(itemView);
            userame = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
            imageView = itemView.findViewById(R.id.image_online);
            checkBox = itemView.findViewById(R.id.checkbox);


            if (onItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                });
            }


        }

        public void onBind(final User item) {

            userame.setText(item.getUsername());
            email.setText(item.getEmail());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBox.isChecked()) {
                        //  usergroub.add(item);
                        //  User ug=item;
//                        Log.e("check", "checkeddddddd");
//                        Log.e("ched", item.getUsername() + "chhhhhecked");
//                        mSocket.emit("userOnline", item.getId());

                        onItemSelectUser.onSelect(isChecked,item);
/*
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("username", item.getUsername());
                            jsonObject.put("email",item.getEmail() );
                            jsonObject.put("password", item.getPassword());
                            jsonObject.put("id", item.getId());
                            mSocket.emit("userOnline", jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

 */

                        //  mSocket.emit("userOnline",usergroub);
                    } else {
                        Log.e("check", " No___checkeddddddd");
                    }
                }
            });

            //   checkBox.setChecked(item.isChecked());
            // checkBox.isChecked();
            //      checkBox.setChecked(item.isChecked());
            /// checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //     @Override
            //      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //      if (checkBox.isChecked()) {
            //           usergroub.add(item);
            //           Log.e("check","checkeddddddd");

            //       }else {
            // Log.e("check"," No___checkeddddddd");
///
            //     }
            //         }
            //     });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    interface OnItemSelectUser{
        void onSelect(boolean isSelect,User user);
    }

}

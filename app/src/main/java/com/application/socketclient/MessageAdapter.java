package com.application.socketclient;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> mMessages;

    public MessageAdapter(List<Message> Messages) {
        this.mMessages = Messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
      if (viewType==0){
         view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
          return new messageViewHolder(view);
      }else {
          view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_sender, parent, false);
          return new ReceverMessageViewHolder(view);
      }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==0){
        messageViewHolder messageViewHolder= (MessageAdapter.messageViewHolder) holder;
            messageViewHolder.onBind(mMessages.get(position));

        }else {
            ReceverMessageViewHolder receverMessageViewHolder= (MessageAdapter.ReceverMessageViewHolder) holder;
            receverMessageViewHolder.onBind(mMessages.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        Message message=mMessages.get(position);
        if (message.userid.equals(LogInActivity.user.getId())){
            Log.e("madapter",LogInActivity.user.getId());
         return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }


    public class messageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        public messageViewHolder(@NonNull View itemView) {
            super(itemView);
         messageText=itemView.findViewById(R.id.text_view_message);

        }

        public void onBind(Message item) {
            messageText.setText(item.getMessage());
        }
    }

    public  class ReceverMessageViewHolder extends RecyclerView.ViewHolder{
        TextView messageText;

        public ReceverMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText=itemView.findViewById(R.id.text_view_message);

        }

        public void onBind(Message item) {
            messageText.setText(item.getMessage());
        }
    }
}

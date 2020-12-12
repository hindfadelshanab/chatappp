package com.application.socketclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private String TAG = "useractivity";
    RecyclerView recyclerViewUser;
    private Gson gson;
    private List<User> userArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        userArray = new ArrayList<>();
        recyclerViewUser = findViewById(R.id.recycleviewUser);
        final UserdAdapter userdAdapter = new UserdAdapter(userArray);
        recyclerViewUser.setAdapter(userdAdapter);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(this));
        userdAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                User u = userArray.get(position);
                String usernamee = u.getUsername();
                String userId = u.getId();
                Intent i = new Intent(UsersActivity.this, MainActivity.class);
                i.putExtra("username", usernamee);
                i.putExtra("userid", u.getId());
                Log.e("iiid", userId);
                startActivity(i);
            }
        });


        gson = new Gson();

        ChatApplication app = new ChatApplication();

        Socket mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.connect();

        mSocket.emit("allusers", true);

        mSocket.on("allusers", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("hin", String.valueOf(args[0]));
                        System.out.println(args[0]);
                        Type userListType = new TypeToken<List<User>>() {
                        }.getType();
                        List<User> userlisst = gson.fromJson(args[0].toString(), userListType);
                        Log.e("mm", userlisst.toString());
                        userArray.addAll(userlisst);
                        userdAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


    }

    public Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "Socket Connected!");
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                }
            });
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                }
            });
        }
    };

}
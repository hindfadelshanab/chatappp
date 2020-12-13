package com.application.socketclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static android.util.Log.e;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText ed_messege;
    ImageView img_send;
    private Socket mSocket;
    String data;
    RecyclerView messageRecycle;
    MessageAdapter messageAdapter;
    List<Message> mesageArray;
    TextView nameOfUser;
    String desId;
    String userId;
    private Gson gson;
    static String user_username;
    static String user_userid;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mesageArray = new ArrayList<>();
        messageRecycle = findViewById(R.id.messageRecycleView);
        messageAdapter = new MessageAdapter(mesageArray);
        gson = new Gson();

        ed_messege = findViewById(R.id.ed_messege);
        img_send = findViewById(R.id.img_send);
        nameOfUser = findViewById(R.id.text_user_name);

        String sendername = getIntent().getStringExtra("username");
        desId = getIntent().getStringExtra("userid");
        user = getIntent().getParcelableExtra("user");
        nameOfUser.setText(sendername);

        messageRecycle.setLayoutManager(new LinearLayoutManager(this));
        messageRecycle.setAdapter(messageAdapter);

        SharedPreferences sharedPreferencess = getSharedPreferences("shared2", MODE_PRIVATE);
        user_username = sharedPreferencess.getString("user_username", "");
        user_userid = sharedPreferencess.getString("user_userid", "");


        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        userId = sharedPreferences.getString("userid", "");

        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.connect();

        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSend();
            }
        });

        mSocket.on("allmessage", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //       JSONObject data = (JSONObject) args[0];
                        if (args[0].toString().equals(LogInActivity.user.getId() + desId)
                                || args[0].toString().equals(desId + LogInActivity.user.getId())) {
                            Message m = gson.fromJson(args[1].toString(), Message.class);
                            mesageArray.add(m);
                            messageAdapter.notifyDataSetChanged();
/*
                            Log.e("tttttt", data.getString("desid"));
                       //     if (desId.equals(data.getString("desid")) ) {
                                Log.e("ttttttasdf", data.getString("desid"));
                                String messagetext = data.getString("message");
                                String ui = data.getString("userid");
                                Log.e("mss", messagetext);
                             //  = new Message(messagetext, ui,
                                        data.getString("desid"));
                         //   }

 */


                            System.out.println("ggg");
/*

                                Log.e("mess", String.valueOf(args[0]));
                                System.out.println(args[0] + "mss");
                                Type userListType = new TypeToken<List<Message>>() {
                                }.getType();
                                List<Message> messagelisst = gson.fromJson(args[0].toString(), userListType);
                                */
                            //   mesageArray.addAll(messagelisst);

                        }
                    }
                });
            }
        });
                /*563
        mSocket.on("message1", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                data = (String) args[0];
                Log.e("a", data);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("hin", data);
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        */


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


    private void attemptSend() {
        String message = ed_messege.getText().toString().trim();

        if (TextUtils.isEmpty(message)) {
            return;
        }
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("message", ed_messege.getText().toString().trim());
            jsonObject.put("userid", LogInActivity.user.getId());
            jsonObject.put("userName", user_userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ed_messege.setText("");
        mSocket.emit("allmessage", LogInActivity.user.getId() + desId, jsonObject);
    }

}

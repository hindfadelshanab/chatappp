package com.application.socketclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageGroubActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    private Socket mSocket;
    String Igroubid;
    Gson gson;
    ImageView imageView;
    List<Message> mesageArrayG;
    RecyclerView messageRecycleG;
    MessageAdapter messageAdapterG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect to Socket
        gson = new Gson();
        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();
        mSocket.connect();



        setContentView(R.layout.activity_message_groub);
        editText = findViewById(R.id.ed_messege_togroub);
        textView = findViewById(R.id.text_groub_name);
        imageView=findViewById(R.id.img_send_g);

        Igroubid = getIntent().getStringExtra("groubid");
        String IgroubName = getIntent().getStringExtra("GroubNamee");
        String[] IgroubUserId = getIntent().getStringArrayExtra("users1");
        textView.setText(IgroubName);

        Log.e("MessageGroub", Igroubid);
        Log.e("MessageGroub", IgroubName);


        mesageArrayG = new ArrayList<>();
        messageRecycleG = findViewById(R.id.messageGRecycleView);
        messageAdapterG = new MessageAdapter(mesageArrayG);
        messageRecycleG.setLayoutManager(new LinearLayoutManager(this));
        messageRecycleG.setAdapter(messageAdapterG);

        imageView.setOnClickListener(new View.OnClickListener() {
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
                        if (args[0].toString().equals(Igroubid)) {
                            Message m = gson.fromJson(args[1].toString(), Message.class);
                            mesageArrayG.add(m);
                            messageAdapterG.notifyDataSetChanged();


                        }
                    }
                });
            }
        });








    }

    private void attemptSend() {

        if (TextUtils.isEmpty(editText.toString().trim())) {
            return;
        }
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("message", editText.getText().toString().trim());
            jsonObject.put("userid", LogInActivity.user.getId());
            jsonObject.put("userName", LogInActivity.user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editText.setText("");
        mSocket.emit("allmessage", Igroubid, jsonObject);
    }
}
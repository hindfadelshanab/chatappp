package com.application.socketclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class LogInActivity extends AppCompatActivity {

    private String EMAIL = "emailLogin";
    private String PASSWORD = "passwordLogin";
    private EditText email;
    private EditText password;
    Button btnLogin;
    Button btnCreatAccount;
    private Socket mSocket;
    private String TAG = "tag";
    String Appid;
    public static User user;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.email_sign_in);
        password = findViewById(R.id.password_sign_in);
        btnLogin = findViewById(R.id.btn_sign_in);
        btnCreatAccount = findViewById(R.id.btn_have_account);
        gson = new Gson();

        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.connect();


        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Appid = sharedPreferences.getString("Appid", "");

        final UUID userId = UUID.randomUUID();
        mSocket.on("SignInData", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((Boolean) args[1] && userId.toString().equals(args[0])) {
                            user = gson.fromJson(args[2].toString(), User.class);
                            System.out.println(user.getEmail() + user.getId());
                            Intent i=new Intent(LogInActivity.this, UsersActivity.class);
                            startActivity(i);
                        }
                        Log.e("op", String.valueOf(args[0]));

                    }
                });

            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIsNotEmptys()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("emaill", email.getText());
                        jsonObject.put("pass", password.getText());
                        mSocket.emit("SignInData", userId.toString(), jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


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

    public Boolean checkIsNotEmptys() {
        if (TextUtils.isEmpty(email.getText())) {
            email.setError(" field is empty");
            return false;
        } else if (TextUtils.isEmpty(password.getText())) {
            password.setError("field is empty");
            return false;
        }
        return true;
    }


}

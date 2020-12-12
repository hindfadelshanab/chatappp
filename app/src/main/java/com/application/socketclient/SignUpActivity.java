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
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {

    private String USER_NAME = "username";
    private String EMAIL = "email";
    private String PASSWORD = "password";
    private String TAG = "hin";

    EditText name;
    EditText email;
    EditText password;
    Button btnSignUp;
    TextView btnhaveAccount;
    private Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.email_sign_in);
        password = findViewById(R.id.password_sign_up);
        email = findViewById(R.id.password_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_in);
        btnhaveAccount = findViewById(R.id.btn_have_account);

        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.connect();

        final UUID userId = UUID.randomUUID();
        Log.e("ttttttt", userId.toString());
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userid", userId.toString());
        editor.apply();

        mSocket.on("SignUp", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (args[0].toString().equals(userId.toString())) {
                            Log.e("i", String.valueOf(args[0]));
                            startActivity(new Intent(SignUpActivity.this, LogInActivity.class));

                        }
                        Log.e("oo", String.valueOf(args[0]));

                    }
                });
            }
        });


        btnhaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkIsNotEmpty()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(USER_NAME, name.getText());
                        jsonObject.put(EMAIL, email.getText());
                        jsonObject.put(PASSWORD, password.getText());
                        jsonObject.put("id", UUID.randomUUID().toString());
                        mSocket.emit("SignUpData", userId.toString(), jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
               startActivity(new Intent(SignUpActivity.this, LogInActivity.class));

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


    public Boolean checkIsNotEmpty() {
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Name field cannot be empty");
            return false;
        } else if (TextUtils.isEmpty(password.getText())) {
            password.setError("Password field cannot be empty");
            return false;


        } else if (TextUtils.isEmpty(email.getText())) {
            email.setError("Email field cannot be empty");
            return false;

        }
        return true;
    }
}
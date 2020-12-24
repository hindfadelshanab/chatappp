package com.application.socketclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroubActivity extends AppCompatActivity {

    ArrayList<Groub> groubs;
    private Socket mSocket;
    private Gson gson;
    ArrayList<Groub> groubArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groub);
        groubs = new ArrayList<>();
        gson = new Gson();
        groubArrayList=new ArrayList<>();

        ChatApplication app = new ChatApplication();

        Socket mSocket = app.getSocket();
        mSocket.connect();


        ArrayList<User> users = getIntent().getParcelableArrayListExtra("userInGroub");
     //   Log.e("gg", groubs.get(0).getGroubName());
        String groubName = getIntent().getStringExtra("groubName");
        //Log.e("gg", groubName.toString());
        System.out.println(groubName);
        groubs.add(new Groub("",groubName, users));


        RecyclerView recyclerView = findViewById(R.id.groub_recycleview);
        final GroubAdapter groubAdapter = new GroubAdapter(groubs);
        recyclerView.setAdapter(groubAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));






        mSocket.on("allGroub", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("hin", String.valueOf(args[0]));
                        System.out.println("lllllll"+args[0].toString());
                        Type userListType = new TypeToken<List<Groub>>() {
                        }.getType();
                  //      groubArrayList = gson.fromJson(args[0].toString(), userListType);
                     //    Log.e("groub2",groubArrayList.get(0).getGroubName() +"\n " +
                      //           groubArrayList.get(0).getUser().get(0).getUsername());
           //     Groub groub=(Groub) args[0];
                    //    Log.e("groub2",groub.getGroubName() +"\n " + groub.getUser().get(0).getUsername());
                  //      groubs.clear();
                   //     groubs.addAll(groubArrayList);
                    //    groubAdapter.notifyDataSetChanged();
                    }
                });


            }
        });

    }
}
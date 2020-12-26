package com.application.socketclient;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AllGroubFragment extends Fragment {

    ArrayList<Groub> groubs;
    private Socket mSocket;
    private Gson gson;
    private ArrayList<String> iduserliststring;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_groub, container, false);


        groubs = new ArrayList<>();
        iduserliststring = new ArrayList<>();
        gson = new Gson();
        //   groubArrayList=new ArrayList<>();

        ChatApplication app = new ChatApplication();
        Socket mSocket = app.getSocket();
        mSocket.connect();


        ArrayList<User> users = getActivity().getIntent().getParcelableArrayListExtra("userInGroub");
        String groubName = getActivity().getIntent().getStringExtra("groubName");


        RecyclerView recyclerView = root.findViewById(R.id.groub_recycleview);
        final GroubAdapter groubAdapter = new GroubAdapter(groubs);
        recyclerView.setAdapter(groubAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        groubAdapter.setOnitemGroubClickListener(new OnitemGroubClickListener() {
            @Override
            public void onItemClickGroub(View v, Groub groub) {
                Intent intent = new Intent(getContext(), MessageGroubActivity.class);
                String groubid = groub.getId();
                String GroubNamee = groub.getGroubName();
                ArrayList<String> users1 = (ArrayList<String>) groub.getUser();
                intent.putExtra("groubid", groubid);
                intent.putExtra("GroubNamee", GroubNamee);
                intent.putStringArrayListExtra("users1", users1);
                Log.e("intent", groubid);
                startActivity(intent);

            }
        });


        mSocket.emit("allGroubs", true);
        mSocket.on("allGroubs", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("oppp99999", String.valueOf(args[0]));
                        Type userListType = new TypeToken<List<Groub>>() {
                        }.getType();
                        List<Groub> userlisst = gson.fromJson(args[0].toString(), userListType);
                        Log.e("oppp99999", userlisst.toString());

                        groubs.clear();
                        groubs.addAll(userlisst);
                        groubAdapter.notifyDataSetChanged();


                    }
                });
                 }
              });





        return  root ;
    }


}
package com.application.socketclient;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class AllUserFragment extends Fragment {

    private String TAG = "useractivity";
    RecyclerView recyclerViewUser;
    private Gson gson;
    private List<User> userArray;
    ArrayList<User> userlisst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_all_user, container, false);

        userlisst=new ArrayList<>();
        userArray = new ArrayList<>();
        recyclerViewUser = root.findViewById(R.id.recycleviewUser);
        final UserdAdapter userdAdapter = new UserdAdapter(userArray);
        recyclerViewUser.setAdapter(userdAdapter);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));

        userdAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                User u = userArray.get(position);
                String usernamee = u.getUsername();
                String userId = u.getId();
                Intent i = new Intent(getContext(), MainActivity.class);
                i.putExtra("username", usernamee);
                i.putExtra("userid", u.getId());
                i.putExtra("user", u);
                Log.e("iiid", userId);
                startActivity(i);
            }
        });


        gson = new Gson();

        ChatApplication app = new ChatApplication();

        Socket mSocket = app.getSocket();

        mSocket.connect();


        mSocket.emit("allusers", true);

        mSocket.on("allusers", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("hin", String.valueOf(args[0]));
                        System.out.println(args[0]);
                        Type userListType = new TypeToken<List<User>>() {
                        }.getType();
                        userlisst = gson.fromJson(args[0].toString(), userListType);
                        userlisst.get(0).getId();
                        Log.e("mm", userlisst.toString());
                        userArray.clear();
                        userArray.addAll(userlisst);
                        userdAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


        mSocket.on("joinu", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data= (String) args[0];
                        Log.e("oooooooooopsppsps","inline id :"+data);

                    }
                });
            }
        });




        return root;
    }




    public void moveToMackeGroub(View view) {

        Intent i=new Intent(getContext(),MakeGroubActivity.class);
        i.putParcelableArrayListExtra("userlist",userlisst);
        startActivity(i);
    }
}
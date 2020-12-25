package com.application.socketclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroubActivity extends AppCompatActivity {

    ArrayList<Groub> groubs;
    private Socket mSocket;
    private Gson gson;
    private ArrayList<String> iduserliststring;
   // ArrayList<Groub> groubArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groub);
        groubs = new ArrayList<>();
        iduserliststring = new ArrayList<>();
        gson = new Gson();
     //   groubArrayList=new ArrayList<>();

        ChatApplication app = new ChatApplication();
        Socket mSocket = app.getSocket();
        mSocket.connect();


        ArrayList<User> users = getIntent().getParcelableArrayListExtra("userInGroub");
        String groubName = getIntent().getStringExtra("groubName");
        //Log.e("gg", groubName.toString());
        //System.out.println(groubName);
      //  groubs.add(new Groub("",groubName, users));

        RecyclerView recyclerView = findViewById(R.id.groub_recycleview);
        final GroubAdapter groubAdapter = new GroubAdapter(groubs);
        recyclerView.setAdapter(groubAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));






   mSocket.emit("allGroubs", true);
        mSocket.on("allGroubs", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("oppp99999", String.valueOf(args[0]));
                        Groub m = gson.fromJson(args[0].toString(), Groub.class);

                        //   Type userListType = new TypeToken<List<Groub>>() {}.getType();
             //    ArrayList<Groub>  userlisst = gson.fromJson(String.valueOf(args[0]), userListType);
                        groubs.add(m);
                        groubAdapter.notifyDataSetChanged();
             //     
                    //    System.out.println("this is groub"+args[0]);
               //       try {
                 //         Groub gg = null;
                    //     JSONArray jsonArray1= new JSONArray(args[0]);
                   //     Log.e("o99", String.valueOf(jsonArray1));

                       //   for (int i=0;i<jsonArray1.length();i++){
                       //    JSONObject jsonObject =jsonArray1.getJSONObject(i);
                       ///   String nameGroub = jsonObject.getString("groubName");
                       //    String id=jsonObject.getString("id");
                         //  JSONArray jsonArray=jsonObject.getJSONArray("user");
                           //   for (int ii=0;ii<jsonArray.length();ii++){
                                // String jj=jsonArray.get(ii).toString();
                             //   iduserliststring.add(jj);
                           //   }
                      //  gg=new Groub(id,nameGroub,iduserliststring);
                    //  }

                      //    groubs.add(gg);
                   //       groubAdapter.notifyDataSetChanged();
                  //  } catch (JSONException e) {
                 //    e.printStackTrace();
               //  }

                    }
                });


            }
        });

    }
}
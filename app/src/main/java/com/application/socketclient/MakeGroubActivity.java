package com.application.socketclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.chip.ChipDrawable;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MakeGroubActivity extends AppCompatActivity {
    private Socket mSocket;
    private Gson gson;
    private EditText editTextGroubName;
    ArrayList<String> userlisstGroub;
    ArrayList<Groub> groubs;
    private String groubId;
     ArrayList<User> arr;
     ArrayList<String > UserIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_groub);
        ChatApplication app = new ChatApplication();
        mSocket = app.getSocket();
        gson = new Gson();
        mSocket.connect();
        editTextGroubName = findViewById(R.id.groubname);
        userlisstGroub = new ArrayList<>();
        groubs = new ArrayList<>();
        groubId = UUID.randomUUID().toString();

        arr = getIntent().getParcelableArrayListExtra("userlist");


        //List<User> list=new ArrayList<>();

        mSocket.on("userOnline", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
               Log.e("yarab", String.valueOf(args[0]));
                userlisstGroub.add(String.valueOf(args[0]));
                //Type userListType = new TypeToken<List<User>>() {
                ///}.getType();
           //    ArrayList<User> userlisst = gson.fromJson(args[0].toString(), userListType);
             //  for (int i=0; i<userlisst.size();i++) {
               //    userlisstGroub.add(userlisst.get(i).getId());
                 //  Log.e("tag",userlisst.get(i).getId());
             //  }
                groubs.add(new Groub(groubId, editTextGroubName.getText().toString(), userlisstGroub));

            }
        });



        RecyclerView recyclerView = findViewById(R.id.recycle_g);
      UserdGroubAdapter userdAdapter = new UserdGroubAdapter(MakeGroubActivity.this, arr);

       recyclerView.setAdapter(userdAdapter);
     recyclerView.setLayoutManager(new LinearLayoutManager(this));
       if (arr != null) {
          for (int i = 0; i < arr.size(); i++) {
             User user = new User(arr.get(i).getId(), arr.get(i).getUsername(), arr.get(i).getEmail());
             Log.e("us", user.toString());
         }


    }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.make_groub_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_check) {
            Intent i = new Intent(MakeGroubActivity.this, GroubActivity.class);
            //i.putParcelableArrayListExtra("userInGroub", userlisstGroub);
          //  i.putExtra("groubName", editTextGroubName.getText().toString());


            JSONObject jsonObject = new JSONObject();
            try {
                JsonArray jsonArray = new JsonArray();

                for (int ii=0;ii<userlisstGroub.size();ii++){
                    jsonArray.add(userlisstGroub.get(ii));

                }
                jsonObject.put("id", groubId);
                jsonObject.put("groubName", editTextGroubName.getText().toString());
                jsonObject.put("user", jsonArray);

                mSocket.emit("AddGroub", jsonObject);
                Log.e("TAG",jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //  mSocket.emit("allGroub", groubs);

            //  System.out.println(editTextGroubName);

            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}




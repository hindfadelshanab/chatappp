package com.application.socketclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {


    private Gson gson;
    ArrayList<User> userlisst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, new AllUserFragment()).commit();
        final ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdpter(getSupportFragmentManager()));
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.user);
        tabLayout.getTabAt(1).setText(R.string.groub);


        gson = new Gson();

        ChatApplication app = new ChatApplication();

        Socket mSocket = app.getSocket();

        mSocket.connect();

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
                        userlisst = gson.fromJson(args[0].toString(), userListType);

                    }
                });


            }
        });
    }


    class MyPagerAdpter extends FragmentPagerAdapter {

        public MyPagerAdpter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return new AllUserFragment();
                case 1:
                    return new AllGroubFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }


    public void moveToMackeGroub(View view) {

        Intent i = new Intent(UsersActivity.this, MakeGroubActivity.class);
        i.putParcelableArrayListExtra("userlist", userlisst);
        startActivity(i);
    }


}
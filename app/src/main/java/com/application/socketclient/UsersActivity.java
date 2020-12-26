package com.example.navigtionpatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllUserFragment()).commit();
        final ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdpter(getSupportFragmentManager()));
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);














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
                    return   new AllUserFragment();
                case 1:
                    return  new AllGroubFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }










}
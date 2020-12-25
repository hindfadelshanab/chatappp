package com.application.socketclient;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Groub implements Parcelable {
    private String groubName;
    private List<String> user;
    private String id;

    protected Groub(Parcel in) {
        groubName = in.readString();
        user = in.createStringArrayList();
        id = in.readString();
    }

    public static final Creator<Groub> CREATOR = new Creator<Groub>() {
        @Override
        public Groub createFromParcel(Parcel in) {
            return new Groub(in);
        }

        @Override
        public Groub[] newArray(int size) {
            return new Groub[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Groub(String id,String groubName, List<String> user) {
        this.groubName = groubName;
        this.user = user;
        this.id=id;
    }



    public List<String> getUser() {
        return user;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }


    public void setGroubName(String groubName) {
        this.groubName = groubName;
    }

    public String getGroubName() {
        return groubName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groubName);
        dest.writeStringList(user);
        dest.writeString(id);
    }
}

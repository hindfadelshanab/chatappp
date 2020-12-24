package com.application.socketclient;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Groub implements Parcelable{
    private String groubName;
    private List<User> user;
    private String id;

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

    public Groub(String id,String groubName, List<User> user) {
        this.groubName = groubName;
        this.user = user;
        this.id=id;
    }

    protected Groub(Parcel in) {
        groubName = in.readString();
        user = in.createTypedArrayList(User.CREATOR);
    }


    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
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
        dest.writeTypedList(user);
        dest.writeString(id);
    }
}

package com.app.pawapp.DataAccess.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class District implements Parcelable{

    private int id;
    private String name;

    public District() {
    }

    public District(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private District(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<District> CREATOR = new Creator<District>() {
        @Override
        public District createFromParcel(Parcel in) {
            return new District(in);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}

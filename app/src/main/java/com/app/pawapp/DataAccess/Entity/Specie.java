package com.app.pawapp.DataAccess.Entity;

import com.google.gson.annotations.SerializedName;

public class Specie {

    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;

    public Specie() {
    }

    public Specie(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

}

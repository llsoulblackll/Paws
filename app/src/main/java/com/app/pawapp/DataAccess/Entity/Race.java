package com.app.pawapp.DataAccess.Entity;

import com.google.gson.annotations.SerializedName;

public class Race {

    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;
    @SerializedName("SpecieId") private int specieId;

    public Race() {
    }

    public Race(int id, String name, int specieId) {
        this.id = id;
        this.name = name;
        this.specieId = specieId;
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

    public int getSpecieId() {
        return specieId;
    }

    public void setSpecieId(int specieId) {
        this.specieId = specieId;
    }

}

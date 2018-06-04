package com.app.pawapp.DataAccess.Entity;

import com.google.gson.annotations.SerializedName;

public class Pet {

    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;
    @SerializedName("Age") private String age;
    @SerializedName("Description") private String description;
    @SerializedName("Picture") private String picture;
    @SerializedName("SpecieId") private int specieId;
    @SerializedName("RaceId") private int raceId;
    @SerializedName("OwnerId") private int ownerId;

    @SerializedName("ImageBase64") private String imageBase64;
    @SerializedName("ImageExtension") private String imageExtension;

    public Pet() {
    }

    public Pet(int id, String name, String age, String description, String picture, int specieId, int raceId, int ownerId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.description = description;
        this.picture = picture;
        this.specieId = specieId;
        this.raceId = raceId;
        this.ownerId = ownerId;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getSpecieId() {
        return specieId;
    }

    public void setSpecieId(int specieId) {
        this.specieId = specieId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }
}

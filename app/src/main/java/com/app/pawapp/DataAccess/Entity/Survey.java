package com.app.pawapp.DataAccess.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Survey implements Parcelable{

    @SerializedName("Id") private int id;
    @SerializedName("AmountOfPeople") private String amountOfPeople;
    @SerializedName("HomeDescription") private String homeDescription;
    @SerializedName("OtherPets") private boolean otherPets;
    @SerializedName("OtherPetsDescription") private String otherPetsDescription;
    @SerializedName("WorkType") private String workType;
    @SerializedName("Availability") private String availability;
    @SerializedName("OwnerId") private int ownerId;

    public Survey() {
    }

    public Survey(int id, String amountOfPeople, String homeDescription, boolean otherPets, String otherPetsDescription, String workType, String availability, int ownerId) {
        this.id = id;
        this.amountOfPeople = amountOfPeople;
        this.homeDescription = homeDescription;
        this.otherPets = otherPets;
        this.otherPetsDescription = otherPetsDescription;
        this.workType = workType;
        this.availability = availability;
        this.ownerId = ownerId;
    }

    private Survey(Parcel in) {
        id = in.readInt();
        amountOfPeople = in.readString();
        homeDescription = in.readString();
        otherPets = in.readByte() != 0;
        otherPetsDescription = in.readString();
        workType = in.readString();
        availability = in.readString();
        ownerId = in.readInt();
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmountOfPeople() {
        return amountOfPeople;
    }

    public void setAmountOfPeople(String amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public String getHomeDescription() {
        return homeDescription;
    }

    public void setHomeDescription(String homeDescription) {
        this.homeDescription = homeDescription;
    }

    public boolean isOtherPets() {
        return otherPets;
    }

    public void setOtherPets(boolean otherPets) {
        this.otherPets = otherPets;
    }

    public String getOtherPetsDescription() {
        return otherPetsDescription;
    }

    public void setOtherPetsDescription(String otherPetsDescription) {
        this.otherPetsDescription = otherPetsDescription;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(amountOfPeople);
        parcel.writeString(homeDescription);
        parcel.writeByte((byte) (otherPets ? 1 : 0));
        parcel.writeString(otherPetsDescription);
        parcel.writeString(workType);
        parcel.writeString(availability);
        parcel.writeInt(ownerId);
    }
}

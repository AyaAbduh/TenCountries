package com.example.tencountries;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class Country implements Serializable , Parcelable {
    private String Rank;
    private String Name;
    private String Population;
    private String Image;

    public Country(){}

    protected Country(Parcel in) {
        Rank = in.readString();
        Name = in.readString();
        Population = in.readString();
        Image = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPopulation() {
        return Population;
    }

    public void setPopulation(String population) {
        Population = population;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
    @Override
    public String toString(){
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Rank);
        dest.writeString(Name);
        dest.writeString(Population);
        dest.writeString(Image);
    }
}

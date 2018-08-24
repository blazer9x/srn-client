package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Location implements Parcelable {

    @SerializedName("longitude")
    long mLongitude;

    @SerializedName("latitude")
    long mLatitude;

    public long getLongitude() {
        return mLongitude;
    }

    public void setLongitude(long longitude) {
        this.mLongitude = longitude;
    }

    public long getLatitude() {
        return mLatitude;
    }

    public void setLatitude(long latitude) {
        this.mLatitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mLongitude);
        dest.writeLong(mLatitude);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    protected Location(Parcel in) {
        this.mLongitude = in.readLong();
        this.mLatitude = in.readLong();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}

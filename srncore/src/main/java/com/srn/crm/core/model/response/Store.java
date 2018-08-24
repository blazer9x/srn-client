package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Store implements Parcelable  {

    @SerializedName("id")
    private long mId;

    @SerializedName("storeName")
    private String mStoreName;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("city")
    private String mCity;

    @SerializedName("location")
    private Location mLocation;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mStoreName);
        dest.writeString(mAddress);
        dest.writeString(mCity);
        dest.writeParcelable(mLocation, flags);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    protected Store(Parcel in) {
        this.mId = in.readLong();
        this.mStoreName = in.readString();
        this.mAddress = in.readString();
        this.mCity = in.readString();
        this.mLocation = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel source) {
            return new Store(source);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };
}

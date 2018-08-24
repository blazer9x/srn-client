package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Offer implements Parcelable  {

    @SerializedName("brand")
    private Brand mBrand;

    @SerializedName("id")
    private long mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("validity")
    private long mValidityTimestamp;

    @SerializedName("tnc")
    private String mTnc;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mBrand,flags);
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeLong(mValidityTimestamp);
        dest.writeString(mTnc);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    protected Offer(Parcel in) {
        this.mBrand = in.readParcelable(getClass().getClassLoader());
        this.mId = in.readParcelable(getClass().getClassLoader());
        this.mTitle = in.readString();
        this.mValidityTimestamp = in.readLong();
        this.mTnc = in.readString();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };


}

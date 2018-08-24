package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Redeem implements Parcelable {

    @SerializedName("redeemStatus")
    private int mRedeemStatus;

    @SerializedName("redeemTime")
    private long mRedeemTime;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mRedeemStatus);
        dest.writeLong(mRedeemTime);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    protected Redeem(Parcel in) {
        this.mRedeemStatus = in.readInt();
        this.mRedeemTime = in.readLong();
    }

    public static final Creator<Redeem> CREATOR = new Creator<Redeem>() {
        @Override
        public Redeem createFromParcel(Parcel source) {
            return new Redeem(source);
        }

        @Override
        public Redeem[] newArray(int size) {
            return new Redeem[size];
        }
    };
}

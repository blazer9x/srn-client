package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PointRewards implements Parcelable {

    @SerializedName("pointRewards")
    int mRewards;

    public int getRewards() {
        return mRewards;
    }

    public void setRewards(int rewards) {
        this.mRewards = rewards;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PointRewards(Parcel in) {
        this.mRewards = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRewards);
    }

    public static final Creator<PointRewards> CREATOR = new Creator<PointRewards>() {
        @Override
        public PointRewards createFromParcel(Parcel source) {
            return new PointRewards(source);
        }

        @Override
        public PointRewards[] newArray(int size) {
            return new PointRewards[size];
        }
    };
}

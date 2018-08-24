package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Brand implements Parcelable  {

    @SerializedName("id")
    private long mId;

    @SerializedName("imageUrl")
    private String mImageUrl;

    @SerializedName("name")
    private String mName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mImageUrl);
        dest.writeString(mName);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    protected Brand(Parcel in) {
        this.mId = in.readLong();
        this.mImageUrl = in.readString();
        this.mName = in.readString();
    }

    public static final Creator<Brand> CREATOR = new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel source) {
            return new Brand(source);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };
}

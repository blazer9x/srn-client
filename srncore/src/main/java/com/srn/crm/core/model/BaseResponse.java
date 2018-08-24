package com.srn.crm.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class BaseResponse<T extends Parcelable> implements Parcelable {

    @SerializedName("resultCode")
    private int mResultCode;

    @SerializedName("timestamp")
    private long mTimestamp;

    @SerializedName("data")
    private T mData;

    public int getResultCode() {
        return mResultCode;
    }

    public void setResultCode(int mResultCode) {
        this.mResultCode = mResultCode;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(int mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public T getData() {
        return mData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    protected BaseResponse(Parcel in) {
        this.mResultCode = in.readInt();
        this.mTimestamp = in.readLong();
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel source) {
            return new BaseResponse(source);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };
}

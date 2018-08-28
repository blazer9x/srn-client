package com.srn.crm.core.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public abstract class BaseRequest implements Parcelable {

    @SerializedName("sessionId")
    private String mSessionId;

    @SerializedName("timestamp")
    private long mTimestamp;

    public BaseRequest() {

    }

    public BaseRequest(Parcel in) {
        this.mSessionId = in.readString();
        this.mTimestamp = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSessionId);
        dest.writeLong(this.mTimestamp);
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }

    public String getSessionId() {
        return mSessionId;
    }

    public void setSessionId(String sessionId) {
        this.mSessionId = sessionId;
    }
}

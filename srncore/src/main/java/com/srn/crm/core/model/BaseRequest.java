package com.srn.crm.core.model;


import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public abstract class BaseRequest implements Parcelable {

    @SerializedName("timestamp")
    private long mTimestamp;

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }
}

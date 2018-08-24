package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Provisioning implements Parcelable {

    @SerializedName("sessionId")
    private String mSessionId;

    public String getSessionId() {
        return mSessionId;
    }

    public void setSessionId(String sessionId) {
        this.mSessionId = sessionId;
    }

    public Provisioning(Parcel in) {
        this.mSessionId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSessionId);
    }

    public static final Creator<Provisioning> CREATOR = new Creator<Provisioning>() {
        @Override
        public Provisioning createFromParcel(Parcel source) {
            return new Provisioning(source);
        }

        @Override
        public Provisioning[] newArray(int size) {
            return new Provisioning[size];
        }
    };
}

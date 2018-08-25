package com.srn.crm.core.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.srn.crm.core.model.BaseRequest;

public class FcmTokenUpdatesRequest extends BaseRequest {

    @SerializedName("fcmToken")
    private String mFcmToken;

    public String getFcmToken() {
        return mFcmToken;
    }

    public void setFcmToken(String mFcmToken) {
        this.mFcmToken = mFcmToken;
    }

    public FcmTokenUpdatesRequest(Parcel in) {
        super(in);
        this.mFcmToken = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mFcmToken);
    }

    public static final Parcelable.Creator<FcmTokenUpdatesRequest> CREATOR = new Parcelable.Creator<FcmTokenUpdatesRequest>() {
        @Override
        public FcmTokenUpdatesRequest createFromParcel(Parcel source) {
            return new FcmTokenUpdatesRequest(source);
        }

        @Override
        public FcmTokenUpdatesRequest[] newArray(int size) {
            return new FcmTokenUpdatesRequest[size];
        }
    };
}

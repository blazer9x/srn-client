package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class FcmToken implements Parcelable {


    public FcmToken(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<FcmToken> CREATOR = new Creator<FcmToken>() {
        @Override
        public FcmToken createFromParcel(Parcel source) {
            return new FcmToken(source);
        }

        @Override
        public FcmToken[] newArray(int size) {
            return new FcmToken[size];
        }
    };
}

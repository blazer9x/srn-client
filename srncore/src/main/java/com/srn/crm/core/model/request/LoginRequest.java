package com.srn.crm.core.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.srn.crm.core.model.BaseRequest;

public class LoginRequest extends BaseRequest {

    @SerializedName("token")
    private String mToken;

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    public LoginRequest() {

    }

    protected LoginRequest(Parcel in) {
        super(in);
        this.mToken = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mToken);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static final Parcelable.Creator<LoginRequest> CREATOR = new Parcelable.Creator<LoginRequest>() {
        @Override
        public LoginRequest createFromParcel(Parcel source) {
            return new LoginRequest(source);
        }

        @Override
        public LoginRequest[] newArray(int size) {
            return new LoginRequest[size];
        }
    };

}

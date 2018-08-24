package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Login implements Parcelable {

    @SerializedName("email")
    private String mEmail;

    @SerializedName("fullName")
    private String mFullName;

    @SerializedName("nickName")
    private String mNickName;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("city")
    private String mCity;

    @SerializedName("province")
    private String mProvince;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("alternateEmail")
    private String mAlternateEmail;

    public Login() {

    }

    protected Login(Parcel in) {
        this.mEmail = in.readString();
        this.mFullName = in.readString();
        this.mNickName = in.readString();
        this.mAddress = in.readString();
        this.mCity = in.readString();
        this.mProvince = in.readString();
        this.mPhone = in.readString();
        this.mAlternateEmail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEmail);
        dest.writeString(mFullName);
        dest.writeString(mNickName);
        dest.writeString(mAddress);
        dest.writeString(mCity);
        dest.writeString(mProvince);
        dest.writeString(mPhone);
        dest.writeString(mAlternateEmail);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel source) {
            return new Login(source);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };

}

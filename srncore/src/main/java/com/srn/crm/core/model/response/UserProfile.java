package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class UserProfile implements Parcelable {

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

    public UserProfile() {

    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getAlternateEmail() {
        return mAlternateEmail;
    }

    public void setAlternateEmail(String mAlternateEmail) {
        this.mAlternateEmail = mAlternateEmail;
    }

    protected UserProfile(Parcel in) {
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

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel source) {
            return new UserProfile(source);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

}

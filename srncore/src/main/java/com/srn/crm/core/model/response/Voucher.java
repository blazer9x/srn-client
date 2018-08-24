package com.srn.crm.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Voucher implements Parcelable {

    @SerializedName("voucherName")
    private String mVoucherName;

    @SerializedName("voucherCode")
    private String mVoucherCode;

    @SerializedName("expiredDate")
    private long mExpiredDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mVoucherName);
        dest.writeString(this.mVoucherCode);
        dest.writeLong(this.mExpiredDate);
    }

    public Voucher(Parcel in) {
        this.mVoucherName = in.readString();
        this.mVoucherCode = in.readString();
        this.mExpiredDate = in.readLong();
    }

    public static final Creator<Voucher> CREATOR = new Creator<Voucher>() {
        @Override
        public Voucher createFromParcel(Parcel source) {
            return new Voucher(source);
        }

        @Override
        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };
}

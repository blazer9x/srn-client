package com.srn.crm.core.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.srn.crm.core.model.BaseRequest;

public class ClaimRequest extends BaseRequest {

    @SerializedName("offerId")
    private String mOfferId;

    @SerializedName("voucherCode")
    private String mVoucherCode;

    @SerializedName("claimedDatetime")
    private long mClaimedDatetime;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mOfferId);
        dest.writeString(this.mVoucherCode);
        dest.writeLong(this.mClaimedDatetime);
    }

    public ClaimRequest(Parcel in) {
        super(in);
        this.mOfferId = in.readString();
        this.mVoucherCode = in.readString();
        this.mClaimedDatetime = in.readLong();
    }

    public static final Parcelable.Creator<ClaimRequest> CREATOR = new Parcelable.Creator<ClaimRequest>() {
        @Override
        public ClaimRequest createFromParcel(Parcel source) {
            return new ClaimRequest(source);
        }

        @Override
        public ClaimRequest[] newArray(int size) {
            return new ClaimRequest[size];
        }
    };
}

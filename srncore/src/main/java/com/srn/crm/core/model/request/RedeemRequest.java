package com.srn.crm.core.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.srn.crm.core.model.BaseRequest;

public class RedeemRequest extends BaseRequest {

    @SerializedName("offerId")
    private String mOfferId;

    @SerializedName("requiredPoints")
    private int mRequiredPoints;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mOfferId);
        dest.writeInt(this.mRequiredPoints);
    }

    public RedeemRequest(Parcel in) {
        super(in);
        this.mOfferId = in.readString();
        this.mRequiredPoints = in.readInt();
    }

    public static final Parcelable.Creator<RedeemRequest> CREATOR = new Parcelable.Creator<RedeemRequest>() {
        @Override
        public RedeemRequest createFromParcel(Parcel source) {
            return new RedeemRequest(source);
        }

        @Override
        public RedeemRequest[] newArray(int size) {
            return new RedeemRequest[size];
        }
    };
}

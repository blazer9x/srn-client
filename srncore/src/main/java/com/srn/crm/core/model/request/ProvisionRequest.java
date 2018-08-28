package com.srn.crm.core.model.request;

import android.os.Parcel;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.srn.crm.core.model.BaseRequest;

public class ProvisionRequest extends BaseRequest {
    
    @SerializedName("imei")
    private String mImei;
    
    @SerializedName("model")
    private String mModel;
    
    @SerializedName("manufacture")
    private String mManufacture;
    
    @SerializedName("osversion")
    private String mOsVersion;

    public String getImei() {
        return mImei;
    }

    public void setImei(String imei) {
        this.mImei = imei;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        this.mModel = mModel;
    }

    public String getManufacture() {
        return mManufacture;
    }

    public void setManufacture(String manufacture) {
        this.mManufacture = manufacture;
    }

    public String getOsVersion() {
        return mOsVersion;
    }

    public void setOsVersion(String osVersion) {
        this.mOsVersion = osVersion;
    }

    public ProvisionRequest() {

    }

    protected ProvisionRequest(Parcel in) {
        super(in);
        this.mImei = in.readString();
        this.mModel = in.readString();
        this.mManufacture = in.readString();
        this.mOsVersion = in.readString();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mImei);
        dest.writeString(mModel);
        dest.writeString(mManufacture);
        dest.writeString(mOsVersion);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static final Creator<ProvisionRequest> CREATOR = new Creator<ProvisionRequest>() {
        @Override
        public ProvisionRequest createFromParcel(Parcel source) {
            return new ProvisionRequest(source);
        }

        @Override
        public ProvisionRequest[] newArray(int size) {
            return new ProvisionRequest[size];
        }
    };
}

package com.srn.crm.core.api;

import android.content.Context;

import com.srn.crm.core.callback.Callback;
import com.srn.crm.core.model.BaseResponse;
import com.srn.crm.core.model.request.LoginRequest;
import com.srn.crm.core.model.request.ProvisionRequest;
import com.srn.crm.core.model.response.Provisioning;
import com.srn.crm.core.model.response.UserProfile;
import com.srn.crm.core.preference.PreferenceManager;
import com.srn.crm.core.utils.SecurityUtils;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserApi extends BaseApi {

    public UserApi(Context context) {
        super(context);
    }

    public boolean isProvisioned() {
        return PreferenceManager.getInstance(mContext).getSessionId() != null ? true : false;
    }

    public boolean isAppTncConfirmed() {
        return PreferenceManager.getInstance(mContext).isTermsConditionChecked();
    }

    public void registerDevice(String fcmId, final Callback<Void> callback) {
        ProvisionRequest request = new ProvisionRequest();

        //TODO: before set request parameter need to check permissions first

        buildRestService().srnProvisioning(SecurityUtils.encryptTypedInput(request.toString()), new retrofit.Callback<BaseResponse<Provisioning>>() {
            @Override
            public void success(BaseResponse<Provisioning> provisioningBaseResponse, Response response) {
                Provisioning provisioningData = provisioningBaseResponse.getData();
                PreferenceManager.getInstance(mContext).setSessionId(provisioningData.getSessionId());
                callback.onSuccess(null);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onFailed(error.getCause());
            }
        });
    }

    public void userLogin(final Callback<UserProfile> callback) {
        LoginRequest request = new LoginRequest();

        buildRestService().srnLogin(SecurityUtils.encryptTypedInput(request.toString()), new retrofit.Callback<BaseResponse<UserProfile>>() {
            @Override
            public void success(BaseResponse<UserProfile> userProfileBaseResponse, Response response) {
                UserProfile profile = userProfileBaseResponse.getData();
                PreferenceManager.getInstance(mContext).setUserProfile(profile);
                callback.onSuccess(profile);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onFailed(error.getCause());
            }
        });
    }


}

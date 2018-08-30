package com.srn.crm.view.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.srn.crm.core.api.UserApi;
import com.srn.crm.core.callback.Callback;
import com.srn.crm.core.model.response.Provisioning;
import com.srn.crm.view.MainActivity;
import com.srn.crm.view.base.BaseActivity;
import com.srn.crm.view.utils.Redirector;

public class OpeningActivity extends BaseActivity {

    private UserApi mUserApi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserApi = new UserApi(this);
        if (!mUserApi.isProvisioned()) {
            setupProvisioning();
        }
    }


    private void setupProvisioning() {
        if (!mUserApi.isAppTncConfirmed()) {
            Redirector.redirectToTermConditionScreen(this);
            return;
        }
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                setupProvisioning(instanceIdResult.getToken());
            }
        });
    }

    private void setupProvisioning(String fcmId) {
        mUserApi.registerDevice(fcmId, new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                //test commit
                Redirector.redirectToMainScreen(OpeningActivity.this);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d("provisionig-failed --> ", throwable.getMessage());
                Redirector.redirectToMainScreen(OpeningActivity.this);
            }
        });
    }









}

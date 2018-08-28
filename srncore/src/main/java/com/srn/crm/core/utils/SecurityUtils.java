package com.srn.crm.core.utils;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;
import com.srn.crm.core.model.request.ProvisionRequest;

import retrofit.mime.TypedInput;
import retrofit.mime.TypedString;

public class SecurityUtils {


    public static String getSecureId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static TypedInput encryptTypedInput(Object request) {
        String json = new Gson().toJson(request);
        return new TypedString(json); //TODO: need to create encryption
    }

    public static TypedInput encryptTypedInput(String json) {
        return new TypedString(json); //TODO: need to create encryption
    }

    public static class AES {

    }

}

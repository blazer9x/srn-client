package com.srn.crm.core.utils;

import android.content.Context;
import android.provider.Settings;

public class SecurityUtils {


    public static String getSecureId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}

package com.srn.crm.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.srn.crm.core.utils.SecurityUtils;

public class PreferenceManager {

    private static final Object LOCK = new Object();

    private Context mContext;
    private SrnSharedPreferences mSharedPreferences;
    private static PreferenceManager sInstance;

    public static PreferenceManager getInstance(Context context) {
        synchronized (LOCK) {
            if (sInstance == null) {
                sInstance = new PreferenceManager(context);
            }
        }
        return sInstance;
    }

    private PreferenceManager(Context context) {
        mContext = context.getApplicationContext();
        SharedPreferences sharedPreference = mContext.getSharedPreferences(PreferenceConstant.FILE_NAME, Context.MODE_PRIVATE);
        mSharedPreferences = new SrnSharedPreferences(mContext, sharedPreference, SecurityUtils.getSecureId(context));
    }

    private static interface PreferenceConstant {
        String FILE_NAME = "pref_srn_core";
    }
}

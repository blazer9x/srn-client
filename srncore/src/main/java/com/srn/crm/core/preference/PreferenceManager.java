package com.srn.crm.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.srn.crm.core.model.response.UserProfile;
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


    public void setFcmRegistrationId(String fcmId) {
        mSharedPreferences.edit().putString(PreferenceConstant.FCM_REGISTRATION_ID, fcmId).commit();
    }

    public String getFcmRegistrationId() {
        return mSharedPreferences.getString(PreferenceConstant.FCM_REGISTRATION_ID, null);
    }

    public void setSessionId(String sessionId) {
        mSharedPreferences.edit().putString(PreferenceConstant.SESSION_ID, sessionId).commit();
    }

    public String getSessionId() {
        return mSharedPreferences.getString(PreferenceConstant.SESSION_ID, null);
    }

    public void setUserProfile(UserProfile userProfile) {
        SharedPreferences.Editor editor = mSharedPreferences.edit()
                .putString(PreferenceConstant.PROFILE_EMAIL, userProfile.getEmail())
                .putString(PreferenceConstant.PROFILE_USER_NAME, userProfile.getFullName())
                .putString(PreferenceConstant.PROFILE_NICKNAME, userProfile.getNickName())
                .putString(PreferenceConstant.PROFILE_ADDRESS, userProfile.getAddress())
                .putString(PreferenceConstant.PROFILE_CITY, userProfile.getCity())
                .putString(PreferenceConstant.PROFILE_PROVINCE, userProfile.getProvince())
                .putString(PreferenceConstant.PROFILE_PHONE, userProfile.getPhone())
                .putString(PreferenceConstant.PROFILE_ALTERNATE_EMAIL, userProfile.getAlternateEmail());
        editor.commit();
    }

    public void setTermsCondition(boolean agreement) {
        mSharedPreferences.edit().putBoolean(PreferenceConstant.APP_TERM_CONDITION_AGREEMENT, agreement).commit();
    }

    public boolean isTermsConditionChecked() {
        return mSharedPreferences.getBoolean(PreferenceConstant.APP_TERM_CONDITION_AGREEMENT, false);
    }

    private static interface PreferenceConstant {
        String FILE_NAME = "pref_srn_core";

        /* user device*/
        String FCM_REGISTRATION_ID = "fcm_registration_id";
        String SESSION_ID = "sessionid";

        /* app term and condition */
        String APP_TERM_CONDITION_AGREEMENT = "tnc_agreement";

        /* user profile */
        String PROFILE_EMAIL = "profile_email";
        String PROFILE_USER_NAME = "profile_user_name";
        String PROFILE_NICKNAME = "profile_nickname";
        String PROFILE_ADDRESS = "profile_address";
        String PROFILE_CITY = "profile_city";
        String PROFILE_PROVINCE = "profile_province";
        String PROFILE_PHONE = "profile_phone";
        String PROFILE_ALTERNATE_EMAIL = "profile_alternate_email";



    }
}

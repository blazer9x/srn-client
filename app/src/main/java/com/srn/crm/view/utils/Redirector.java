package com.srn.crm.view.utils;

import android.app.Activity;
import android.content.Intent;

import com.srn.crm.view.MainActivity;
import com.srn.crm.view.common.OpeningActivity;

public class Redirector {

    public static void redirectToOpeningScreen(Activity activity) {
        Intent intent = new Intent(activity, OpeningActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public static void redirectToMainScreen(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public static void redirectToTermConditionScreen(Activity activity) {

    }
}

package com.srn.crm.core;

import android.app.Application;
import android.content.Context;

import com.srn.crm.core.utils.logger.DebugLogTree;
import com.srn.crm.core.utils.logger.FileLogger;

import retrofit.RestAdapter;
import timber.log.Timber;

public abstract class SRNApplication extends Application {

    static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initialLog();
        Timber.d("app#oncreate application");
    }

    public static Context getContext() {
        return mContext;
    }


    private void initialLog() {
        Timber.plant(new DebugLogTree(mContext, shouldLogApplicationToFile()));
        logUncaughtException();
        FileLogger fileLogger = new FileLogger(mContext);
        fileLogger.cleanLog();
        if (!((SRNApplication) getContext()).isProduction()) {
            fileLogger.copyLogToExternalCacheFolder();
        }
    }

    private void logUncaughtException() {
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Timber.d(ex, "srn::UNCAUGHT EXCEPTION");
                if (uncaughtExceptionHandler != null) {
                    uncaughtExceptionHandler.uncaughtException(thread, ex);
                }
            }
        });
    }

    public RestAdapter.LogLevel getRetrofitLogLevel() {
        return RestAdapter.LogLevel.BASIC;
    }


    public boolean shouldLogApplicationToFile() {
        return true;
    }

    public abstract boolean isProduction();

    public abstract String getServerEndPoint();


}

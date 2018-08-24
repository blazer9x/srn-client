package com.srn.crm.core.utils;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import android.text.format.DateUtils;

import java.util.HashMap;

import timber.log.Timber;

public class DebugLogTree extends Timber.DebugTree {

    public static final String SPLITTER = "::";

    private HashMap<String, FileLogger> mFileLoggerMap;

    private Context mContext;
    private boolean logToMonitor;

    public DebugLogTree(Context context, boolean shouldLogApplicationToFile) {
        mContext = context;
        mFileLoggerMap = new HashMap<>();
        logToMonitor = shouldLogApplicationToFile;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (logToMonitor) {
            super.log(priority, tag, message, t);
        }

        String[] messages = message.split(SPLITTER);

        // If file name exist
        if (messages.length > 1) {
            FileLogger fileLogger = getFileLogger(messages[0]);
            if (t != null) {
                fileLogger.writeLog(messages[1], t);
            } else {
                fileLogger.writeLog(messages[1]);
            }
        }
    }

    private FileLogger getFileLogger(String fileName) {
        FileLogger fileLogger = mFileLoggerMap.get(fileName);
        if (fileLogger == null) {
            fileLogger = getNewFileLog(fileName);
            mFileLoggerMap.put(fileName, fileLogger);
        }
        return fileLogger;
    }

    private FileLogger getNewFileLog(String fileName) {
        FileLogger fileLogger = new FileLogger(mContext, fileName);
        fileLogger.setDoNotLog(false);
        fileLogger.writeLog("[application]" + "\n"
                + "onCreate called with" + "\n"
                + "[pid] = " + Process.myPid() + "\n"
                + "[tid] = " + Process.myTid() + "\n"
                + "[uid] = " + Process.myUid() + "\n"
                + "[Application Elapsed CPU Time] = " + DateUtils.formatElapsedTime(Process.getElapsedCpuTime()) + "\n"
                + "[Device Elapsed Real Time] = " + DateUtils.formatElapsedTime(SystemClock.elapsedRealtime()) + "\n"
                + "[Device Up Time] = " + DateUtils.formatElapsedTime(SystemClock.uptimeMillis()));
        return fileLogger;
    }
}

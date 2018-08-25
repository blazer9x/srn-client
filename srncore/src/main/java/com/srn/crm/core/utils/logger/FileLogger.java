package com.srn.crm.core.utils.logger;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import org.apache.commons.codec.CharEncoding;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileLogger {

    private static final String DEBUG_LOG_DIR = "srn_debug_log";
    private static final int MAX_OLD_LOG_DAY = 3;
    private static final String LOG_EXTENSION = "log";
    public static final String META_LOG_FILE = "meta.log";

    private final SimpleDateFormat LINE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss:SSS Z", Locale.US);
    private final SimpleDateFormat FILE_NAME_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.US);

    private Context mContext;
    private String mLogName;
    private File mDebugLogDir;
    private boolean mDoNotLog;

    public FileLogger(Context context) {
        mContext = context.getApplicationContext();
        mDebugLogDir = getDebugLogDir();
    }

    public FileLogger(Context context, String logName) {
        mContext = context.getApplicationContext();
        mLogName = logName;
        mDebugLogDir = getDebugLogDir();
    }

    private File getDebugLogDir() {
        File file = new File(mContext.getCacheDir(), DEBUG_LOG_DIR);
        if (file.mkdirs() || file.isDirectory()) {
            return file;
        }
        return null;
    }

    private File createDebugLogFile() {
        File file = new File(mDebugLogDir, FILE_NAME_DATE_FORMAT.format(Calendar.getInstance().getTime()) + "_" + mLogName + "." + LOG_EXTENSION);
        try {
            if (file.createNewFile() || file.isFile()) {
                return file;
            }
        } catch (IOException ex) {
            return null;
        }
        return null;
    }

    public void setDoNotLog(boolean doNotLog) {
        mDoNotLog = doNotLog;
    }

    public void writeLog(String message) {
        if (mDoNotLog) {
            return;
        }
        Writer writer = null;
        try {
            File file = createDebugLogFile();
            StringBuilder stringBuilder = new StringBuilder();
            //stringBuilder.append(SecurityUtils.AESEncrypt(LINE_DATE_FORMAT.format(new Date()) + ": " + message, true));
            stringBuilder.append(LINE_DATE_FORMAT.format(new Date()) + ": " + message);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), CharEncoding.UTF_8));
            writer.append(stringBuilder.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeLog(String message, Throwable throwable) {
        if (mDoNotLog) {
            return;
        }
        Writer writer = null;
        try {
            File file = createDebugLogFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), CharEncoding.UTF_8));
            writer.append(LINE_DATE_FORMAT.format(new Date()) + ": " + message + "\n");
            if (throwable != null) {
                throwable.printStackTrace(new PrintWriter(writer));
            }
            writer.append("\n\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeLog(String message, Bundle bundle) {
        if (mDoNotLog) {
            return;
        }
        Writer writer = null;
        try {
            File file = createDebugLogFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), CharEncoding.UTF_8));
            writer.append(LINE_DATE_FORMAT.format(new Date()) + ": " + message + "\nBundle:\n");
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    writer.append("[" + key + "] = " + bundle.get(key).toString() + "\n");
                }
            }
            writer.append("\n\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Copy db to local folder
     */
    /*public void copyDbToLogFolder() {
        try {
            File dbFile = mContext.getDatabasePath(DatabaseHelper.NAME);
            File backupDbFile = new File(getDebugLogDir(), dbFile.getName() + ".db");
            if (dbFile.exists()) {
                FileChannel src = new FileInputStream(dbFile).getChannel();
                FileChannel dst = new FileOutputStream(backupDbFile).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Put bitmap file to log folder
     *
     * @param bitmap
     */
    public void putBitmapToLogFolder(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        FileOutputStream out = null;
        File file = new File(mDebugLogDir, FILE_NAME_DATE_FORMAT.format(Calendar.getInstance().getTime()) + "_image.png");
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clean log to limit the size
     */
    public void cleanLog() {
        File[] fileList = mDebugLogDir.listFiles();
        if(fileList == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -MAX_OLD_LOG_DAY);
        for (File file : fileList) {
            // Delete if file ext is not 'log'
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
            if (TextUtils.isEmpty(ext) || !ext.equalsIgnoreCase(LOG_EXTENSION)) {
                file.delete();
                continue;
            }
            // Keep log based on date
            if (file.lastModified() < calendar.getTimeInMillis()) {
                file.delete();
            }

            if (file.getName().equals(META_LOG_FILE)) {
                file.delete();
            }
        }
    }

    /**
     * Get zipped file
     *
     * @return
     */
    public File getZipFile() {
        File[] fileList = mDebugLogDir.listFiles();
        if(fileList == null) {
            return null;
        }
        return getZipFile(fileList);
    }

    public File getZipFileWithOption(List<String> fileNameList) {
        if(fileNameList == null || fileNameList.size() < 1) {
            return null;
        }
        List<File> fileList = new ArrayList<>();

        File metaLog = new File(mDebugLogDir, META_LOG_FILE);
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(metaLog, true), CharEncoding.UTF_8));

            for (String fileName : fileNameList) {
                File file = new File(mDebugLogDir, fileName);
                StringBuilder stringBuilder = new StringBuilder();
                if (file.exists() && file.isFile()) {
                    fileList.add(file);
                    stringBuilder.append(fileName + ": Found");
                }else{
                    stringBuilder.append(fileName + ": Not Found");
                }
                stringBuilder.append("\n\n");
                writer.write(stringBuilder.toString());
            }
            writer.close();
            fileList.add(metaLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getZipFile(fileList.toArray(new File[fileList.size()]));
    }

    private File getZipFile(File[] fileList) {
        byte[] buffer = new byte[1024];
        File zippedFile = new File(mDebugLogDir, FILE_NAME_DATE_FORMAT.format(Calendar.getInstance().getTime()) + "log.zip");
        FileOutputStream dest = null;
        ZipOutputStream out = null;
        try{
            dest = new FileOutputStream(zippedFile);
            out = new ZipOutputStream(dest);
            for (File file : fileList) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                out.putNextEntry(zipEntry);

                FileInputStream in = new FileInputStream(file);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }

                in.close();
            }

            out.closeEntry();
            //remember close it
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (dest != null) {
                    dest.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return zippedFile;
    }

    /**
     * Copy all log files to external storage
     */
    public void copyLogToExternalCacheFolder() {
        File[] fileList = mDebugLogDir.listFiles();
        if(fileList == null) {
            return;
        }
        for (File file : fileList) {
            copyToExternalCacheFolder(file);
        }
    }

    /**
     * Copy file to external cache storage
     * @param file
     */
    private void copyToExternalCacheFolder(File file) {
        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
        File toFile = new File(mContext.getExternalCacheDir(), file.getName());
        if (ext.equals("zip") && toFile.exists()) {
            toFile.delete();
            toFile = new File(mContext.getExternalCacheDir(), file.getName());
        }

        try {
            FileChannel src = new FileInputStream(file).getChannel();
            FileChannel dst = new FileOutputStream(toFile).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unzip log file into a folder, then decrypt it
     *
     * @param context
     * @param file
     * @param filenameWithoutExtension
     * @param directory
     */
    public static void unzipLogFile(Context context, File file, String filenameWithoutExtension, File directory) {
        File decryptedDirectory = new File(directory, filenameWithoutExtension);
        decryptedDirectory.mkdirs();
        File encryptedDirectory = new File(decryptedDirectory, "encrypted");
        encryptedDirectory.mkdirs();
        try {
            InputStream is = new FileInputStream(file);
            BufferedInputStream bin = new BufferedInputStream(is);
            ZipInputStream zin = new ZipInputStream(bin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    File f = new File(ze.getName() + encryptedDirectory);
                    if (!f.isDirectory()) {
                        f.mkdirs();
                    }
                } else {
                    OutputStream fout = new FileOutputStream(encryptedDirectory + "/" + ze.getName());
                    BufferedOutputStream out = new BufferedOutputStream(fout);
                    byte[] buffer = new byte[1024];
                    int read = 0;
                    while ((read = zin.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    zin.closeEntry();
                    out.close();
                    fout.close();
                }
            }
            zin.close();
            decryptFile(context, encryptedDirectory, decryptedDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read encrypted-log in a folder, then create decrypted-log file
     *
     * @param context
     * @param files
     * @param decryptedDirectory
     */
    private static void decryptFile(Context context, File files, File decryptedDirectory) {
        File[] fileList = files.listFiles();
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            try {
                InputStream inputStream = new FileInputStream(file);
                readLogString(context, inputStream, file.getName(), decryptedDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read log strings from log file, line by line
     *
     * @param inputStream
     * @param name
     * @param decryptedDirectory
     */
    private static void readLogString(Context context, InputStream inputStream, String name, File decryptedDirectory) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder log = new StringBuilder();
            String line;
            ArrayList<String> logFile = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("\n\n")) {
                    logFile.add(log.toString());
                    log = new StringBuilder();
                } else {
                    log.append(line);
                }
            }
            File outputFile = new File(decryptedDirectory, name);
            decryptLog(context, logFile, outputFile, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decrypt log based on split-log strings, save into a file
     *
     * @param messageInLogFile
     * @param file
     * @param t
     */
    private static void decryptLog(Context context, ArrayList<String> messageInLogFile, File file, Throwable t) {
        String[] messages = messageInLogFile.toArray(new String[messageInLogFile.size()]);
        FileLogger fileLogger = new FileLogger(context);
        for (String message : messages) {
            if (message != null) {
                fileLogger.decryptLogString(message, file, t);
            }
        }
    }


    public void decryptLogString(String encryptedValue, File file, Throwable throwable) {
        Writer writer = null;
        try {
            StringBuilder builder = new StringBuilder();
            //builder.append(SecurityUtils.AESDecrypt(encryptedValue, false));
            builder.append(encryptedValue);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), CharEncoding.UTF_8));
            writer.append(builder.toString());
            if (throwable != null) {
                throwable.printStackTrace(new PrintWriter(writer));
            }
            writer.append("\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
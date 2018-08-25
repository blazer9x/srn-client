package com.srn.crm.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.srn.crm.core.db.tables.BaseTable;
import com.srn.crm.core.db.tables.BrandTable;
import com.srn.crm.core.db.tables.OfferTable;

public class DatabaseHelper  extends SQLiteOpenHelper {

    public static String DB_NAME = "srn";
    private static int DB_VERSION = 1;
    private static DatabaseHelper sInstance;
    private static final Object LOCK  = new Object();

    private static BaseTable[] SRN_TABLES = {
            new BrandTable(),
            new OfferTable()
    };


    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        synchronized (LOCK) {
            if (sInstance == null) {
                sInstance = new DatabaseHelper(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (BaseTable b : SRN_TABLES) {
            b.onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

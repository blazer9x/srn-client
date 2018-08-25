package com.srn.crm.core.db.tables;

import android.database.sqlite.SQLiteDatabase;

public interface BaseTable {
    void onCreate(SQLiteDatabase db);
    void onUpdate(SQLiteDatabase db);
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}

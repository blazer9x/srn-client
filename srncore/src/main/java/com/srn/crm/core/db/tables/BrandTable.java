package com.srn.crm.core.db.tables;

import android.database.sqlite.SQLiteDatabase;

public class BrandTable implements BaseTable {

    public static String TABLE_NAME = "brand";
    public static String FIELD_BRANDID = "brand_id";
    public static String FIELD_BRANDNAME = "brand_name";
    public static String FIELD_BRANDIMAGEURL = "brand_url";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ? " +
                "(? integer not null," +
                "? text," +
                "? text )", new String[] {
                TABLE_NAME,
                FIELD_BRANDID,
                FIELD_BRANDNAME,
                FIELD_BRANDIMAGEURL});
    }

    @Override
    public void onUpdate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

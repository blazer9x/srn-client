package com.srn.crm.core.db.tables;

import android.database.sqlite.SQLiteDatabase;

public class OfferTable implements BaseTable {

    public static String TABLE_NAME = "offer";

    private static String FIELD_OFFERID = "offer_id";
    private static String FIELD_BRANDID = "brand_id";
    private static String FIELD_OFFERTITLE = "offer_title";
    private static String FIELD_OFFERTNC = "offer_tnc";
    private static String FIELD_VALIDITY = "offer_validity";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ? " +
                "( ? integer not null," +
                "? integer not null," +
                "? text not null," +
                "? text not null," +
                "? bigint not null)", new String[] {
                TABLE_NAME,
                FIELD_OFFERID,
                FIELD_BRANDID,
                FIELD_OFFERTITLE,
                FIELD_OFFERTNC,
                FIELD_VALIDITY });
    }

    @Override
    public void onUpdate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

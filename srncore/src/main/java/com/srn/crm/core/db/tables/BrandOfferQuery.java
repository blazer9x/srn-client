package com.srn.crm.core.db.tables;

public class BrandOfferQuery {
    public static final String OFFER_PER_BRAND = "SELECT " + OfferTable.TABLE_NAME + ".*" +
            " FROM  " + BrandTable.TABLE_NAME + " b JOIN " + OfferTable.TABLE_NAME + " o ON o." +
            OfferTable.FIELD_BRANDID + " = b." + BrandTable.FIELD_BRANDID + " where " + BrandTable.FIELD_BRANDID + " = ?";
}

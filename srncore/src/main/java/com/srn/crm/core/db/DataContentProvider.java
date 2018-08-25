package com.srn.crm.core.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.srn.crm.core.db.tables.BrandTable;
import com.srn.crm.core.db.tables.OfferTable;

public class DataContentProvider extends ContentProvider {

    private DatabaseHelper mDbHelper;

    private static final int BRAND = 10001;
    private static final int OFFER = 10002;
    private static final int BRAND_OFFER = 10003;

    public static final String AUTHORITY = "com.srn.crm";
    private static final String BASE_PATH = DatabaseHelper.DB_NAME + "/";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //join table
    public static final String BRAND_OFFER_PATH = "brand_offer";

    //base uri
    public static final Uri BRAND_URI = Uri.withAppendedPath(CONTENT_URI, BASE_PATH + BrandTable.TABLE_NAME);
    public static final Uri OFFER_URI = Uri.withAppendedPath(CONTENT_URI, BASE_PATH + OfferTable.TABLE_NAME);
    public static final Uri BRAND_OFFER_URI = Uri.withAppendedPath(CONTENT_URI, BASE_PATH + BRAND_OFFER_PATH);

    //matcher
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH + BrandTable.TABLE_NAME, BRAND);
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH + OfferTable.TABLE_NAME, OFFER);
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH + BRAND_OFFER_PATH, BRAND_OFFER);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = DatabaseHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri result = insertNoNotify(db, uri, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        if (values.length < 1) {
            return 0;
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ContentValues cv : values) {
                insertNoNotify(db, uri, cv);
            }
            db.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
        } finally {
            db.endTransaction();
        }
        return values.length;
    }

    private Uri insertNoNotify(SQLiteDatabase db, Uri uri, ContentValues values) {
        long id;
        int type = URI_MATCHER.match(uri);
        switch (type) {
            case BRAND :
                id = db.insert(BrandTable.TABLE_NAME, null, values);
                break;
            case OFFER:
                id = db.insert(OfferTable.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

package com.srn.crm.core.db;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.srn.crm.core.db.tables.BrandOfferQuery;
import com.srn.crm.core.db.tables.BrandTable;
import com.srn.crm.core.db.tables.OfferTable;

import java.util.ArrayList;

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
        Cursor cursor = null;
        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case OFFER:
                cursor = getBasicQuery(OfferTable.TABLE_NAME, projection, selection, selectionArgs, sortOrder);
                break;
            case BRAND:
                cursor = getBasicQuery(BrandTable.TABLE_NAME, projection, selection, selectionArgs, sortOrder);
                break;
            case BRAND_OFFER:
                cursor = getCustomQuery(BrandOfferQuery.OFFER_PER_BRAND, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    private Cursor getBasicQuery(String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tableName);
        return queryBuilder.query(mDbHelper.getWritableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor getCustomQuery(String sql, String[] args) {
        return mDbHelper.getWritableDatabase().rawQuery(sql, args);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return (uri != null) ? uri.toString() : null;
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
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int deletedRows = 0;
        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case BRAND:
                deletedRows = db.delete(BrandTable.TABLE_NAME, selection, selectionArgs);
                break;
            case OFFER:
                deletedRows = db.delete(OfferTable.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int updatedRows = 0;
        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case BRAND:
                updatedRows = db.update(BrandTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case OFFER:
                updatedRows = db.update(OfferTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updatedRows;
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        final int numOperations = operations.size();
        final ContentProviderResult[] results = new ContentProviderResult[numOperations];
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        db.beginTransaction();
        try {
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful(); // commit if successful
        } finally {
            db.endTransaction();             // end transaction
        }
        return results;
    }
}

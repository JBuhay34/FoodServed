package com.example.justinbuhay.foodserved.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by justinbuhay on 7/13/17.
 */

public class FoodServedProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int FOODS = 1;
    private static final int FOODS_ID = 2;

    static {

        sUriMatcher.addURI(FoodContract.AUTHORITIES, FoodContract.FoodEntry.TABLE_NAME, FOODS);

        sUriMatcher.addURI(FoodContract.AUTHORITIES, FoodContract.FoodEntry.TABLE_NAME + "/#", FOODS_ID);

    }

    private final String LOG_TAG = this.getClass().getSimpleName();
    private FoodDBHelper mDBHelper;

    @Override
    public boolean onCreate() {

        mDBHelper = new FoodDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selections, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        columns = new String[]{FoodContract.FoodEntry._ID, FoodContract.FoodEntry.COLUMNS_FOOD_TITLE, FoodContract.FoodEntry.COLUMNS_TABLE_NUMBER, FoodContract.FoodEntry.COLUMNS_SERVED_VERIFICATION};
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        int result = sUriMatcher.match(uri);
        switch (result) {

            case FOODS:

                return database.query(FoodContract.FoodEntry.TABLE_NAME, columns, selections, selectionArgs, null, null, null);

            case FOODS_ID:

                selections = FoodContract.FoodEntry._ID + "=?";

                selectionArgs = new String[]{uri.getPath()};

                return database.query(FoodContract.FoodEntry.TABLE_NAME, columns, selections, selectionArgs, null, null, null);

            default:
                throw new NullPointerException(LOG_TAG + ": Invalid uri -" + uri);

        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase database = mDBHelper.getWritableDatabase();


        long rowId = database.insert(FoodContract.FoodEntry.TABLE_NAME, null, contentValues);

        if (rowId == -1) {
            Log.e(LOG_TAG, "Cannot insert with uri: " + uri);
            return null;
        }

        return Uri.parse(FoodContract.FoodEntry.CONTENT_URI + "/" + rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

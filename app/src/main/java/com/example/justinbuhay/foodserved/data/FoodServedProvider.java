package com.example.justinbuhay.foodserved.data;

import android.content.ContentProvider;
import android.content.ContentUris;
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

        Cursor c;
        columns = new String[]{FoodContract.FoodEntry._ID, FoodContract.FoodEntry.COLUMNS_FOOD_TITLE, FoodContract.FoodEntry.COLUMNS_TABLE_NUMBER, FoodContract.FoodEntry.COLUMNS_SERVED_VERIFICATION};
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        int result = sUriMatcher.match(uri);
        switch (result) {

            case FOODS:

                c = database.query(FoodContract.FoodEntry.TABLE_NAME, columns, selections, selectionArgs, null, null, null);
                break;

            case FOODS_ID:

                selections = FoodContract.FoodEntry._ID + "=?";

                selectionArgs = new String[]{uri.getPath()};

                return database.query(FoodContract.FoodEntry.TABLE_NAME, columns, selections, selectionArgs, null, null, null);

            default:
                throw new NullPointerException(LOG_TAG + ": Invalid uri -" + uri);

        }

        c.setNotificationUri(getContext().getContentResolver(), FoodContract.FoodEntry.CONTENT_URI);
        return c;
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


        Uri newUri = Uri.parse(FoodContract.FoodEntry.CONTENT_URI + "/" + rowId);
        // Delete if doesnt work.
        getContext().getContentResolver().notifyChange(FoodContract.FoodEntry.CONTENT_URI, null);
        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        int result = sUriMatcher.match(uri);
        int returnInt;
        SQLiteDatabase database = mDBHelper.getWritableDatabase();

        switch (result) {

            case FOODS:
                returnInt = database.delete(FoodContract.FoodEntry.TABLE_NAME, null, null);
                break;
            case FOODS_ID:
                String where = FoodContract.FoodEntry._ID + "=?";
                String[] theId = new String[]{uri.getPath()};
                returnInt = database.delete(FoodContract.FoodEntry.TABLE_NAME, where, theId);
                break;
            default:
                throw new IllegalArgumentException("Delete failure on Uri:" + uri);

        }

        getContext().getContentResolver().notifyChange(FoodContract.FoodEntry.CONTENT_URI, null);
        return returnInt;


    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        int rowsUpdated;
        int result = sUriMatcher.match(uri);
        switch (result) {

            case FOODS_ID:
                Log.i("FoodServedProvider: ", "correct update called" + String.valueOf(ContentUris.parseId(uri)));
                String where = FoodContract.FoodEntry._ID + "=?";

                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = database.update(FoodContract.FoodEntry.TABLE_NAME, contentValues, where, strings);
                break;
            default:
                return 0;

        }
        getContext().getContentResolver().notifyChange(FoodContract.FoodEntry.CONTENT_URI, null);
        return rowsUpdated;

    }
}

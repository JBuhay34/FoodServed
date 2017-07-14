package com.example.justinbuhay.foodserved.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by justinbuhay on 7/13/17.
 */

public class FoodDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "foodserved.db";
    public static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE "
            + FoodContract.FoodEntry.TABLE_NAME + " ("
            + FoodContract.FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
            + FoodContract.FoodEntry.COLUMNS_FOOD_TITLE + " TEXT NOT NULL, "
            + FoodContract.FoodEntry.COLUMNS_TABLE_NUMBER + " INTEGER NOT NULL PRIMARY KEY, "
            + FoodContract.FoodEntry.COLUMNS_SERVED_VERIFICATION + " INTEGER NOT NULL);";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + FoodContract.FoodEntry.TABLE_NAME;

    public FoodDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);

    }

}

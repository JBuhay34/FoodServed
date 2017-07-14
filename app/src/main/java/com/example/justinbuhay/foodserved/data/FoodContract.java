package com.example.justinbuhay.foodserved.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by justinbuhay on 7/13/17.
 */

public class FoodContract {

    public static final String AUTHORITIES = "com.example.justinbuhay.foodserved";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITIES);

    private FoodContract() {
    }

    public static class FoodEntry implements BaseColumns {

        public static final String TABLE_NAME = "foods";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMNS_FOOD_TITLE = "food_title";

        public static final String COLUMNS_TABLE_NUMBER = "table_number";

        public static final String COLUMNS_SERVED_VERIFICATION = "served_verification";

    }


}

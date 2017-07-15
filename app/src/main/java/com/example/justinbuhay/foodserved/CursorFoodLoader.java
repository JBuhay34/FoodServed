package com.example.justinbuhay.foodserved;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.justinbuhay.foodserved.data.FoodContract;


/**
 * Created by jbuha on 7/15/2017.
 */

public class CursorFoodLoader extends android.support.v4.content.CursorLoader {

    public CursorFoodLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return  getContext().getContentResolver().query(FoodContract.FoodEntry.CONTENT_URI, null, null, null, null);




    }
}

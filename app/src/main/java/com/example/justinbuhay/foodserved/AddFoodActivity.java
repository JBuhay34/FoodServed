package com.example.justinbuhay.foodserved;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.justinbuhay.foodserved.data.FoodContract;

public class AddFoodActivity extends AppCompatActivity {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private EditText mFoodTitleEditText;
    private EditText mTableNumberEditText;
    private int positionCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        this.setTitle(getIntent().getStringExtra("ADD_FOOD"));

        mFoodTitleEditText = (EditText) findViewById(R.id.foodNameEditText);
        mTableNumberEditText = (EditText) findViewById(R.id.TableNumberEditText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_food_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_food_menu:
                addFood();
                getIntent().putExtra("POSITION", positionCreated);
                setResult(Activity.RESULT_OK, getIntent());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    private void addFood() {

        String foodTitle = mFoodTitleEditText.getText().toString();
        Log.i(LOG_TAG, "foodTitle: " + foodTitle);

        int tableNumber = Integer.parseInt(mTableNumberEditText.getText().toString());
        Log.i(LOG_TAG, "tableNumber: " + tableNumber);

        if ((!foodTitle.equals("")) || (foodTitle != null) || (!(tableNumber < 0))) {

            Log.i(LOG_TAG, "FoodEntry CONTENT_URI = " + FoodContract.FoodEntry.CONTENT_URI);

            ContentValues values = new ContentValues();
            values.put(FoodContract.FoodEntry.COLUMNS_FOOD_TITLE, foodTitle);
            values.put(FoodContract.FoodEntry.COLUMNS_TABLE_NUMBER, tableNumber);

            Uri getnewUri = getContentResolver().insert(FoodContract.FoodEntry.CONTENT_URI, values);
            positionCreated = (int) ContentUris.parseId(getnewUri);


        } else {
            throw new NullPointerException("Empty parameters within the editTexts");
        }

    }

}

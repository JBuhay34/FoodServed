package com.example.justinbuhay.foodserved;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.justinbuhay.foodserved.data.FoodContract;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_FOOD_REQUEST_CODE = 4;
    private RecyclerView mRecyclerView;
    private FoodServedAdapter mAdapter;
    private FloatingActionButton mFAB;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCursor = getContentResolver().query(FoodContract.FoodEntry.CONTENT_URI, null, null, null, null);

        if (mCursor.moveToFirst())
            Log.i(MainActivity.class.getSimpleName(), mCursor.getString(mCursor.getColumnIndex(FoodContract.FoodEntry.COLUMNS_FOOD_TITLE)));

        // Initializes the  adapter with the current query cursor;
        mAdapter = new FoodServedAdapter(this, mCursor);

        // Finds the RecyclerView's ID.
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Set the adapter to the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        // Set LayoutManager for the RecyclerView.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // findView of the Floating ActionButton
        mFAB = (FloatingActionButton) findViewById(R.id.FoodFAB);

        // Sets the onClickListener for the FAB, and sends an intent to start the AddFood activity.
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
                intent.putExtra("ADD_FOOD", "Add Food");

                // Sends an Activity for a result to listen for a change in the data.
                startActivityForResult(intent, ADD_FOOD_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdapter.notifyDataSetChanged();
    }
}

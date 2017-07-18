package com.example.justinbuhay.foodserved;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.justinbuhay.foodserved.data.FoodContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ADD_FOOD_REQUEST_CODE = 4;
    private RecyclerView mRecyclerView;
    private FoodServedAdapter mAdapter;
    private FloatingActionButton mFAB;
    private Cursor mCursor;
    // private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "OnCreate called", Toast.LENGTH_SHORT).show();

        mCursor = getContentResolver().query(FoodContract.FoodEntry.CONTENT_URI, null, null, null, null);

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

        getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ADD_FOOD_REQUEST_CODE:
                    updateRecyclerView();
                    break;
            }
        }

    }

    // Updates RecyclerView for now...
    public void updateRecyclerView() {
        mCursor = getContentResolver().query(FoodContract.FoodEntry.CONTENT_URI, null, null, null, null);
        mAdapter = new FoodServedAdapter(this, mCursor);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, FoodContract.FoodEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mCursor = data;
        mAdapter = new FoodServedAdapter(this, mCursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mCursor.close();

    }
}

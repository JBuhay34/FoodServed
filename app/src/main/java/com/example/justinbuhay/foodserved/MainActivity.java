package com.example.justinbuhay.foodserved;


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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.justinbuhay.foodserved.data.FoodContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ADD_FOOD_REQUEST_CODE = 4;
    private RecyclerView mRecyclerView;
    private FoodServedAdapter mAdapter;
    private FloatingActionButton mFAB;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes the  adapter with the current query cursor;
        mAdapter = new FoodServedAdapter(this, null, getContentResolver());

        // Finds the RecyclerView's ID.
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

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
                startActivity(intent);
            }
        });

        // Initialize LoaderManager
        getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_item:
                deleteAllItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllItems() {
        getContentResolver().delete(FoodContract.FoodEntry.CONTENT_URI, null, null);
    }

    /*
    // Gets Result from Activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ADD_FOOD_REQUEST_CODE:
                    break;
            }
        }

    }
    */


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, FoodContract.FoodEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mCursor = data;
        mAdapter.swapCursor(mCursor);

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mCursor.close();

    }
}

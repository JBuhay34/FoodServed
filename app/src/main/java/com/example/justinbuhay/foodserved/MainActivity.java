package com.example.justinbuhay.foodserved;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.justinbuhay.foodserved.data.FoodContract;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerView;
    private FoodServedAdapter mAdapter;
    private FloatingActionButton mFAB;
    private Handler handlerToWait = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAdapter = new FoodServedAdapter(this, null);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mFAB = (FloatingActionButton) findViewById(R.id.FoodFAB);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
                intent.putExtra("ADD_FOOD", "Add Food");
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 0:
                return new CursorFoodLoader(this);
            default:
                throw new IllegalArgumentException("Unable to create correct Loader");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case 0:

                Log.e(getClass().getSimpleName(), "Cursor has " + data.getCount());
                mAdapter.swapCursor(data);

                /*
                Cursor cursor = ((FoodServedAdapter) mRecyclerView.getAdapter()).getCursor();

                MatrixCursor mx = new MatrixCursor(new String[]{FoodContract.FoodEntry._ID, FoodContract.FoodEntry.COLUMNS_FOOD_TITLE, FoodContract.FoodEntry.COLUMNS_TABLE_NUMBER, FoodContract.FoodEntry.COLUMNS_SERVED_VERIFICATION});
                fillMx(cursor, mx);

                fillMx(data, mx);

                ((FoodServedAdapter) mRecyclerView.getAdapter()).swapCursor(mx);

                */


                break;
            default:
                throw new IllegalArgumentException("Unidentified loader Id");
        }


    }

    /*
    private void fillMx(Cursor data, MatrixCursor mx) {
        if (data == null) {
            return;
        }



        data.moveToPosition(-1);
        while (data.moveToNext()) {
            mx.addRow(new Object[]{
                    data.getString(data.getColumnIndex(FoodContract.FoodEntry._ID)),
                    data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMNS_FOOD_TITLE)),
                    data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMNS_TABLE_NUMBER)),
                    data.getString(data.getColumnIndex(FoodContract.FoodEntry.COLUMNS_SERVED_VERIFICATION))
            });
        }
    }
           */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        loader.reset();

    }
}

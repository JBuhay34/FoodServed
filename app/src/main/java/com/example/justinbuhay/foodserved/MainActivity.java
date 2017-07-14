package com.example.justinbuhay.foodserved;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FoodServedAdapter mAdapter;
    private FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<FoodDescription> foodDescriptions= new ArrayList<FoodDescription>();
        foodDescriptions.add(new FoodDescription("Pepperoni Pizza", 25, 0));
        foodDescriptions.add(new FoodDescription("Chicken for Christine", 1, 1));
        foodDescriptions.add(new FoodDescription("Chicken for Christine", 1, 1));
        foodDescriptions.add(new FoodDescription("Chicken for Christine", 1, 1));
        foodDescriptions.add(new FoodDescription("Chicken for Christine", 1, 1));


        mAdapter = new FoodServedAdapter(foodDescriptions);

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


    }
}

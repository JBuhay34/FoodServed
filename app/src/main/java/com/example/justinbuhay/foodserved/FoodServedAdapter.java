package com.example.justinbuhay.foodserved;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.justinbuhay.foodserved.data.FoodContract;


/**
 * Created by justinbuhay on 7/13/17.
 */

public class FoodServedAdapter extends RecyclerView.Adapter<FoodServedAdapter.FoodViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    // Constructor for this Adapter class.
    public FoodServedAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;

    }


    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.food_card_item_view, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.setData(mCursor);
        }

    }


    // Returns the amount of items in the cursor.
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    // FoodViewHolder class that has a setData method.
    public class FoodViewHolder extends RecyclerView.ViewHolder {

        public TextView foodTitleTextView;
        public TextView tableNumberTextView;
        public TextView isServedTextView;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodTitleTextView = (TextView) itemView.findViewById(R.id.food_title_text_view);
            tableNumberTextView = (TextView) itemView.findViewById(R.id.table_exact_number_text_view);
            isServedTextView = (TextView) itemView.findViewById(R.id.on_completion_text_view);

        }

        // Sets the data
        public void setData(Cursor c) {
            FoodDescription currentFoodDescription = new FoodDescription(c.getString(c.getColumnIndex(FoodContract.FoodEntry.COLUMNS_FOOD_TITLE)), c.getInt(c.getColumnIndex(FoodContract.FoodEntry.COLUMNS_TABLE_NUMBER)), c.getInt(c.getColumnIndex(FoodContract.FoodEntry.COLUMNS_SERVED_VERIFICATION)));

            // Log.i("FoodServedAdapter", "Set current food title: " + currentFoodDescription.getFoodTitle());

            foodTitleTextView.setText(currentFoodDescription.getFoodTitle());
            tableNumberTextView.setText(Integer.toString(currentFoodDescription.getTableNumber()));
            determineOrderStatus(currentFoodDescription.getOnCompletion());
        }

        // Determines the orderStatus
        private void determineOrderStatus(int orderStatus) {
            switch (orderStatus) {
                case 0:
                    isServedTextView.setText(mContext.getString(R.string.not_delivered));
                    break;
                case 1:
                    isServedTextView.setText(mContext.getString(R.string.delivered));
                    break;
                default:
                    throw new NullPointerException("Order Status is unavailable right now.");
            }

        }
    }
}
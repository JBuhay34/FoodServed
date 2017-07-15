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

public class FoodServedAdapter extends CursorRecyclerViewAdapter {

    public FoodServedAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.food_card_item_view, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        FoodViewHolder holder = (FoodViewHolder) viewHolder;
        cursor.moveToPosition(cursor.getPosition());
        holder.setData(cursor);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

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

        public void setData(Cursor c) {
            foodTitleTextView.setText(c.getString(c.getColumnIndex(FoodContract.FoodEntry.COLUMNS_FOOD_TITLE)));
            tableNumberTextView.setText(c.getInt(c.getColumnIndex(FoodContract.FoodEntry.COLUMNS_TABLE_NUMBER)) + "");
            determineOrderStatus(c.getInt(c.getColumnIndex(FoodContract.FoodEntry.COLUMNS_SERVED_VERIFICATION)));
        }

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
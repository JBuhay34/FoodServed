package com.example.justinbuhay.foodserved;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by justinbuhay on 7/13/17.
 */

public class FoodServedAdapter extends RecyclerView.Adapter<FoodServedAdapter.FoodViewHolder> {

    private List<FoodDescription> foodDescriptions;

    public FoodServedAdapter(ArrayList<FoodDescription> foodDescriptions){

        this.foodDescriptions = foodDescriptions;

    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card_item_view, parent, false);
        FoodViewHolder tempViewHolder = new FoodViewHolder(v);
        return tempViewHolder;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {



        FoodDescription tempFoodDescription = foodDescriptions.get(position);

        int tableNumber = tempFoodDescription.getTableNumber();

        holder.foodTitleTextView.setText(tempFoodDescription.getFoodTitle());

        holder.tableNumberTextView.setText(Integer.toString(tableNumber));

        determineOrderStatus(tempFoodDescription.getOnCompletion(), holder.completionStatusTextView);


    }

    private void determineOrderStatus(int orderStatus, TextView completionStatusTextView){

        String orderString = null;

        switch (orderStatus){
            case 0:
                orderString = completionStatusTextView.getResources().getString(R.string.not_delivered);
                completionStatusTextView.setTextColor(completionStatusTextView.getResources().getColor(R.color.red));
                break;
            case 1:
                orderString = completionStatusTextView.getResources().getString(R.string.delivered);
                completionStatusTextView.setTextColor(completionStatusTextView.getResources().getColor(R.color.green));
                break;

        }
        if(orderString == null){
            throw new NullPointerException("The order string of completion textView is null.");
        }
        completionStatusTextView.setText(orderString);

    }

    @Override
    public int getItemCount() {
        return foodDescriptions.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        TextView foodTitleTextView;
        TextView tableNumberTextView;
        TextView completionStatusTextView;

        public FoodViewHolder(View itemView) {
            super(itemView);

            foodTitleTextView = (TextView) itemView.findViewById(R.id.food_title_text_view);
            tableNumberTextView = (TextView) itemView.findViewById(R.id.table_exact_number_text_view);
            completionStatusTextView  = (TextView) itemView.findViewById(R.id.completion_status_text_view);
            
        }






    }


}

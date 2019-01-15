package com.hxtruonglhsang.cooky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxtruonglhsang.cooky.R;
import com.hxtruonglhsang.cooky.model.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodSavedAdapter extends ArrayAdapter<Food> {
    private Context context;
    private int resource;
    private ArrayList<Food> foodArrayList;

    public FoodSavedAdapter (Context context, int resource, ArrayList<Food> foods) {
        super(context, resource, foods);
        this.context=context;
        this.foodArrayList=foods;
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(resource, parent,false);

        ImageView imageList=(ImageView)convertView.findViewById(R.id.imageFood);
        TextView user = (TextView)convertView.findViewById(R.id.user) ;
        TextView foodName = (TextView)convertView.findViewById(R.id.foodName) ;

        Food food=foodArrayList.get(position);

        user.setText(food.getUserName());
        foodName.setText(food.getName());
        Picasso.with(context).load(food.getImages().get(0)).error(R.mipmap.ic_launcher).into(imageList);

        return convertView;
    }

    @Override
    public Food getItem(int position) {
        return super.getItem(position);
    }

}

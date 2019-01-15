package com.hxtruonglhsang.cooky.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.hxtruonglhsang.cooky.R;
import com.hxtruonglhsang.cooky.model.Comment;
import com.hxtruonglhsang.cooky.model.Food;
import com.hxtruonglhsang.cooky.service.FoodService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodInNewsfeedAdapter extends ArrayAdapter<Food> {
    private Context context;
    private int resource;
    private ArrayList<Food> foodArrayList;

    public FoodInNewsfeedAdapter(Context context, int resource, ArrayList<Food> foods) {
        super(context, resource, foods);
        this.context = context;
        this.foodArrayList = foods;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource, parent, false);

        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.like_sound);
        ImageView imageList = (ImageView) convertView.findViewById(R.id.imageFood);
        TextView user = (TextView) convertView.findViewById(R.id.user);
        TextView foodName = (TextView) convertView.findViewById(R.id.foodName);
        TextView foodDescription = (TextView) convertView.findViewById(R.id.foodDescription);
        TextView likeNumber = (TextView) convertView.findViewById(R.id.likeNumber);
        CheckBox like = (CheckBox) convertView.findViewById(R.id.ck_like);

        Food food = foodArrayList.get(position);
        try {
            user.setText(food.getUserId());
            foodName.setText(food.getName());
            foodDescription.setText(food.getDescription().substring(0, food.getDescription().length() > 80 ? 80 : food.getDescription().length()-1) + "...");
            Picasso.with(context).load(food.getImages().get(0)).error(R.mipmap.ic_launcher).into(imageList);

        }catch (Exception e){

        }

        if (food.getLikes() != null) {
//            likeNumber.setText(food.getLikes().size());
        }
        // TODO: comments

        // TODO: action like
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mp.start();
            }
        });

        // For update comment
        //displayCommentsReadltime(food.getId());
        return convertView;
    }

    void updateComments(String foodId) {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // A new comment has been added, add it to the displayed list
                Comment comment = dataSnapshot.getValue(Comment.class);
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        FoodService.displayCommentsReadltime(foodId, childEventListener);

    }

    @Override
    public Food getItem(int position) {
        return super.getItem(position);
    }

}

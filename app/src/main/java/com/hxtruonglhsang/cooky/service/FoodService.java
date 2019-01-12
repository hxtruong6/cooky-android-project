package com.hxtruonglhsang.cooky.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hxtruonglhsang.cooky.fragment.HomeFragment;
import com.hxtruonglhsang.cooky.model.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodService extends Firebase {

    public Food getFoodById(int id) {
        return null;
    }

    public boolean saveFood(Food food) {
        return true;
    }

    public boolean delete(Food food) {
        return true;
    }

    public static void getAllFood(ValueEventListener valueEventListenerCallback) {
        DatabaseReference foodRef = database.getReference("foods");
        foodRef.addValueEventListener(valueEventListenerCallback);
    }

    public static void getFoodIngredientsById(String foodId) {
        DatabaseReference ingredientRef = database.getReference("foodIngredients").child(foodId);
        ingredientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("xxx", "ingredients: " + dataSnapshot.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getFoodLikesById(String foodId, final HomeFragment.IFoodLikesCallback foodLikesCallback) {
        DatabaseReference likeRef = database.getReference("foodLikes").child(foodId);
        likeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Log.d("xxx", "onDataChange Likes: " + dataSnapshot.toString());
                    List<String> likes = new ArrayList<>();
                    for (DataSnapshot userLikes : dataSnapshot.getChildren()) {
                        if (userLikes.getKey().compareTo("true") == 0) {
                            likes.add(userLikes.getKey());
                        }
                    }
                    //Log.d("xxx", "Likes: " + likes.toString());
                    foodLikesCallback.onCallback(likes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

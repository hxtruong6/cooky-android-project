package com.hxtruonglhsang.cooky.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hxtruonglhsang.cooky.model.Comment;
import com.hxtruonglhsang.cooky.model.Food;
import com.hxtruonglhsang.cooky.model.Ingredient;
import com.hxtruonglhsang.cooky.model.Step;
import com.hxtruonglhsang.cooky.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodService extends Firebase {
    public void uploadImage(String imageName) {

    }

    public static void saveFood(Food food) {
        DatabaseReference rootRef = Firebase.database.getReference();
        DatabaseReference foodRef = rootRef.child("foods").push();
        String key = foodRef.getKey();
        food.setId(key);
        Map<String, Object> foodValue = food.toFoodGeneralInfoMap();
        String userId = "hxtruong";
//        foodValue.put("userId", Firebase.getUid());
        foodValue.put("userId", userId);
//        Log.d("xxx save food", foodValue.toString());
        foodRef.setValue(foodValue);

        rootRef.child("foodIngredients").child(key).updateChildren(food.toFoodIngredientsMap());
        rootRef.child("foodSteps").child(key).updateChildren(food.toFoodStepsMap());
        rootRef.child("foodComments").child(key).updateChildren(food.toFoodCommentsMap());
        rootRef.child("foodLikes").child(key).updateChildren(food.toFoodLikesMap());

        HashMap keyMap = new HashMap();
        keyMap.put(key, true);
        rootRef.child("userFoods").child(userId).updateChildren(keyMap);

        // add food to list usrFoods of usr
        UserService.updateUserFoods(userId, food.getId());
    }

    public static void displayFoodCommentsReadltime(String foodId, ChildEventListener childEventListener) {
        DatabaseReference commentRef = Firebase.database.getReference().child("foodComments").child(foodId);
        commentRef.addChildEventListener(childEventListener);
    }

    public static void updateFoodSteps(String foodId, List<Step> lists) {
        DatabaseReference dataRef = Firebase.database.getReference();
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < lists.size(); i++) {
            map.put(String.valueOf(i), lists.get(i).toMap());
        }
        dataRef.child("foodSteps").child(foodId).setValue(map);
    }

    public static void updateFoodComments(String foodId, List<Comment> lists) {
        DatabaseReference dataRef = Firebase.database.getReference();
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < lists.size(); i++) {
            map.put(String.valueOf(i), lists.get(i).toMap());
        }
        dataRef.child("foodComments").child(foodId).setValue(map);
    }

    public static void updateFoodIngredients(String foodId, List<Ingredient> lists) {
        DatabaseReference dataRef = Firebase.database.getReference();
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < lists.size(); i++) {
            map.put(String.valueOf(i), lists.get(i).toMap());
        }
        dataRef.child("foodIngredients").child(foodId).setValue(map);
    }

    public static void updateFoodLikes(String foodId, List<String> lists) {
        DatabaseReference dataRef = Firebase.database.getReference();
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < lists.size(); i++) {
            map.put(lists.get(i), true);
        }
        dataRef.child("foodLikes").child(foodId).setValue(map);
    }


    public static void delete(Food food) {
//        food.setId("-LW4bW8K8S_b28Wg45M2"); // remove this
        Firebase.deleteFirebaseNode("/foods/" + food.getId());
        Firebase.deleteFirebaseNode("/foodLikes/" + food.getId());
        Firebase.deleteFirebaseNode("/foodComments/" + food.getId());
        Firebase.deleteFirebaseNode("/foodSteps/" + food.getId());
        Firebase.deleteFirebaseNode("/foodIngredients/" + food.getId());
        Firebase.deleteFirebaseNode("/userFoods/" + food.getUserId() + "/" + food.getId());

        // Delete userFoods of user
        UserService.removerUserFood(food.getUserId(), food.getId());
    }

    public static void getAllFood(final IAllFoodsCallback iAllFoodsCallback) {
        DatabaseReference foodRef = database.getReference("foods");
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<Food> foods = new ArrayList<>();
                    for (DataSnapshot foodDataSnapshot : dataSnapshot.getChildren()) {
                        final Food food = foodDataSnapshot.getValue(Food.class);
                        food.setId(foodDataSnapshot.getKey());
                        //Log.d("xxx food", foodData.getId());
                        // TODO: find other way to sync get
                        getFoodIngredientsById(food.getId(), new IFoodIngredientsCallback() {
                            @Override
                            public void onCallback(ArrayList<Ingredient> ingredients) {
                                food.setIngredients(ingredients);
                            }
                        });
                        getFoodLikesById(food.getId(), new IFoodLikesCallback() {
                            @Override
                            public void onCallback(List<String> likes) {
                                food.setLikes(likes);
                            }
                        });
                        getFoodCommentsById(food.getId(), new IFoodCommentsCallback() {
                            @Override
                            public void onCallback(ArrayList<Comment> comments) {
                                food.setComments(comments);
                            }
                        });
                        foods.add(food);
                    }
                    iAllFoodsCallback.onCallback(foods);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("xxx Error", "Failed to read value.", error.toException());
            }
        });
    }

    public static void getFoodIngredientsById(String foodId, final IFoodIngredientsCallback iFoodIngredientsCallback) {
        DatabaseReference ingredientRef = database.getReference("foodIngredients").child(foodId);
        ingredientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    Log.d("xxx", "ingredients: " + dataSnapshot.toString());
                    List<Object> ingredientData = (List<Object>) dataSnapshot.getValue();
                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                    for (int i = 0; i < ingredientData.size(); i++) {
                        Ingredient ing = dataSnapshot.child(String.valueOf(i)).getValue(Ingredient.class);
//                        Log.d("xxx ing", ing.toString());
                        ingredients.add(ing);
                    }

                    iFoodIngredientsCallback.onCallback(ingredients);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getFoodCommentsById(String foodId, final IFoodCommentsCallback iFoodCommentsCallback) {
        DatabaseReference ingredientRef = database.getReference("foodComments").child(foodId);
        ingredientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Object> commentData = (List<Object>) dataSnapshot.getValue();
                    ArrayList<Comment> comments = new ArrayList<>();
                    for (int i = 0; i < commentData.size(); i++) {
                        Comment cmt = dataSnapshot.child(String.valueOf(i)).getValue(Comment.class);
//                        Log.d("xxx cmt:", cmt.getContent());
                        comments.add(cmt);
                    }
                    iFoodCommentsCallback.onCallback(comments);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getFoodStepsById(String foodId, final IFoodStepsCallback iFoodStepsCallback) {
        DatabaseReference ingredientRef = database.getReference("foodSteps").child(foodId);
        ingredientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Object> stepsData = (List<Object>) dataSnapshot.getValue();
                    ArrayList<Step> steps = new ArrayList<>();
                    for (int i = 0; i < stepsData.size(); i++) {
                        Step step = dataSnapshot.child(String.valueOf(i)).getValue(Step.class);
                        steps.add(step);
                    }
                    iFoodStepsCallback.onCallback(steps);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getFoodLikesById(String foodId, final IFoodLikesCallback iFoodLikesCallback) {
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
                    iFoodLikesCallback.onCallback(likes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface IAllFoodsCallback {
        void onCallback(ArrayList<Food> foods);
    }

    public interface IFoodsCallback {
        void onCallback(Food food);
    }

    public interface IFoodLikesCallback {
        void onCallback(List<String> likes);
    }

    public interface IFoodCommentsCallback {
        void onCallback(ArrayList<Comment> comments);
    }

    public interface IFoodIngredientsCallback {
        void onCallback(ArrayList<Ingredient> ingredients);
    }

    public interface IFoodStepsCallback {
        void onCallback(ArrayList<Step> steps);
    }
}

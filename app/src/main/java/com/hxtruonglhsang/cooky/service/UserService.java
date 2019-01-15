package com.hxtruonglhsang.cooky.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hxtruonglhsang.cooky.model.Food;
import com.hxtruonglhsang.cooky.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    public static void updateUser(User user) {
        DatabaseReference userRef = Firebase.database.getReference("users");
        Map<String, Object> map = user.toUserInfoMap();
        userRef.child(user.getId()).updateChildren(map);
    }

    public static void getUserByUserName(final String userName, final IUserByUserNameCallback iUserByUserNameCallback) {
        DatabaseReference userRef = Firebase.database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                        User user = userDataSnapshot.getValue(User.class);
                        if (user.getUserName() == userName) {
                            iUserByUserNameCallback.onCallback(user);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getUserById(final String userId, final IUserByIdCallback iUserByIdCallback) {
        DatabaseReference userRef = Firebase.database.getReference("users").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    iUserByIdCallback.onCallback(dataSnapshot.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getFoodsByUserId(final String userId, final IFoodsByUserIdCallback iFoodsByUserIdCallback) {
        DatabaseReference userRef = Firebase.database.getReference("userFoods").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> foodIds = new ArrayList<>();
                    for (DataSnapshot foodId : dataSnapshot.getChildren()) {
                        if (foodId.getValue(Boolean.class)) {
                            foodIds.add(foodId.getKey());
                        }
                    }
                    iFoodsByUserIdCallback.onCallback(foodIds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getSavedFoodsByUserId(final ISavedFoodsByUserIdCallback iSavedFoodsByUserIdCallback) {
        String userId = Firebase.getUserId();
        DatabaseReference userRef = Firebase.database.getReference("savedUserFoods").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> foodIds = new ArrayList<>();
                    for (DataSnapshot foodId : dataSnapshot.getChildren()) {
                        if (foodId.getValue(Boolean.class)) {
                            foodIds.add(foodId.getKey());
                        }
                    }
                    iSavedFoodsByUserIdCallback.onCallback(foodIds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void updateUserFoods(final String userId, String foodId) {
        DatabaseReference dataRef = Firebase.database.getReference();
        HashMap<String, Object> map = new HashMap<>();
        map.put(foodId, true);
        dataRef.child("userFoods").child(userId).updateChildren(map);
    }

    public static void updateSavedUserFoods(final String userId, String foodId) {
        DatabaseReference dataRef = Firebase.database.getReference();
        HashMap<String, Object> map = new HashMap<>();
        map.put(foodId, true);
        dataRef.child("savedUserFoods").child(userId).updateChildren(map);
    }

    public static void removerUserFood(final String userId, final String foodId) {
        Firebase.deleteFirebaseNode("/userFoods/" + userId + "/" + foodId);
    }

    public static void removerSavedUserFood(final String userId, final String foodId) {
        Firebase.deleteFirebaseNode("/savedUserFoods/" + userId + "/" + foodId);
    }

    public interface IUserByUserNameCallback {
        void onCallback(User user);
    }

    public interface IUserByIdCallback {
        void onCallback(User user);
    }

    public interface IFoodsByUserIdCallback {
        void onCallback(List<String> foodIds);
    }

    public interface ISavedFoodsByUserIdCallback {
        void onCallback(List<String> foodIds);
    }

}

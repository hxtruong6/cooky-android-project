package com.hxtruonglhsang.cooky.service;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hxtruonglhsang.cooky.model.Food;
import com.hxtruonglhsang.cooky.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    public User getUserByUserName(String userName) {
        return null;
    }

    public boolean signIn() {
        return true;
    }

    public boolean signUp(User user) {
        return true;
    }

    public static void getUserByUserName(final String userName, final IUserByUserNameCallback iUserByUserNameCallback) {
        DatabaseReference likeRef = Firebase.database.getReference("users");
        likeRef.addValueEventListener(new ValueEventListener() {
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
        DatabaseReference likeRef = Firebase.database.getReference("users").child(userId);
        likeRef.addValueEventListener(new ValueEventListener() {
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
        DatabaseReference likeRef = Firebase.database.getReference("userFoods").child(userId);
        likeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> foodIds = new ArrayList<>();
                    for (DataSnapshot foodId : dataSnapshot.getChildren()) {
                        if (foodId.getKey().compareTo("true") == 0) {
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


    public interface IUserByUserNameCallback {
        void onCallback(User user);
    }

    public interface IUserByIdCallback {
        void onCallback(User user);
    }

    public interface IFoodsByUserIdCallback {
        void onCallback(List<String> foodIds);
    }

}

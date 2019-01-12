package com.hxtruonglhsang.cooky.service;

import com.google.firebase.database.FirebaseDatabase;
import com.hxtruonglhsang.cooky.model.Food;

import java.util.List;

public class Firebase {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public interface IFirebaseCallback{
        void onCallback(List<Food> foods);
    }
}

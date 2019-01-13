package com.hxtruonglhsang.cooky.service;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.hxtruonglhsang.cooky.activity.SplashScreen;
import com.hxtruonglhsang.cooky.model.Food;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class Firebase {
    //private static FirebaseAuth mAuth;
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public interface IFirebaseCallback {
        void onCallback(List<Food> foods);
    }
}

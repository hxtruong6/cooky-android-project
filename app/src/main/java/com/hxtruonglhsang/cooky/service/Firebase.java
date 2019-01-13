package com.hxtruonglhsang.cooky.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    public static void deleteFirebaseNode(final String nodePath) {
        DatabaseReference dataRef = database.getReference().child(nodePath);
        dataRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Log.d("Delete", nodePath + " Successfully!");
            }
        });
    }
}

package com.hxtruonglhsang.cooky.service;

import android.app.Activity;
import android.content.Context;
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
    private static FirebaseAuth mAuth;
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

    public static boolean isSignedIn() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    public static void signUpWithEmail(final Context context, String email, String password, final ISignUpCallback iSignUpCallback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            iSignUpCallback.onCallback(user);
                        } else {
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            iSignUpCallback.onCallback(null);
                        }
                    }
                });

    }

    public static void signInWithEmail(final Context context, String email, String password, final ISignInCallback iSignInCallback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            iSignInCallback.onCallback(user);
                        } else {
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            iSignInCallback.onCallback(null);
                        }
                    }
                });
    }

    public static void signOut() {
        if (FirebaseAuth.getInstance() != null) {
            FirebaseAuth.getInstance().signOut();
        }
    }

    public interface ISignUpCallback {
        void onCallback(FirebaseUser currentUser);
    }

    public interface ISignInCallback {
        void onCallback(FirebaseUser currentUser);
    }
}

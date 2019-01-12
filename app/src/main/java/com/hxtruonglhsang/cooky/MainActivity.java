package com.hxtruonglhsang.cooky;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hxtruonglhsang.cooky.fragment.AddFragment;
import com.hxtruonglhsang.cooky.fragment.HomeFragment;
import com.hxtruonglhsang.cooky.fragment.ProfileFragment;
import com.hxtruonglhsang.cooky.fragment.SavedFragment;
import com.hxtruonglhsang.cooky.model.Food;
import com.hxtruonglhsang.cooky.service.FoodService;
import com.hxtruonglhsang.cooky.utils.BottomNavigationBehavior;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        AddFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        SavedFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            fragment = new HomeFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.navigation_add:
                            fragment = new AddFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.navigation_saved:
                            fragment = new SavedFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.navigation_profile:
                            fragment = new ProfileFragment();
                            loadFragment(fragment);
                            return true;
                    }
                    return false;
                }
            };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        return true;
    }

    public void onTestBtnClick(View view) {
        String message = "on";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}

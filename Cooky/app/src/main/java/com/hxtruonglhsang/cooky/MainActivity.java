package com.hxtruonglhsang.cooky;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.hxtruonglhsang.cooky.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            //fragment = new HomeFragment();
                            //loadFragment(fragment);
                            Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navigation_add:
                            //fragment = new AddFragment();
                            //loadFragment(fragment);
                            Toast.makeText(getApplicationContext(),"Add",Toast.LENGTH_SHORT).show();

                            return true;
                        case R.id.navigation_saved:
                            //fragment = new SavedFragment();
                            //loadFragment(fragment);
                            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navigation_profile:
                            //fragment = new ProfileFragment();
                            //loadFragment(fragment);
                            Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
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

}

package com.hxtruonglhsang.cooky.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.hxtruonglhsang.cooky.MainActivity;
import com.hxtruonglhsang.cooky.R;

public class SplashScreen extends AppCompatActivity {
    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
            getSupportActionBar().hide(); // hide the title bar
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        }catch (Exception e){

        }


        setContentView(R.layout.activity_splash_screen);


        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    Intent intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(intent);

                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 1500);  // Give a 2 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}

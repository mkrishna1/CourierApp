package com.example.courierapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courierapp.R;
import com.example.courierapp.utils.PreferenceUtil;


public class SplashScreen extends AppCompatActivity {

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        user_id = PreferenceUtil.getValueString(this, PreferenceUtil.USER_ID);

        new SplashDownCountDown(3000, 1000).start();


    }

    private class SplashDownCountDown extends CountDownTimer {

        SplashDownCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

            PreferenceUtil.remove(SplashScreen.this, PreferenceUtil.POINT_A_LAT);
            PreferenceUtil.remove(SplashScreen.this, PreferenceUtil.POINT_A_LONG);

            PreferenceUtil.remove(SplashScreen.this, PreferenceUtil.POINT_B_LAT);
            PreferenceUtil.remove(SplashScreen.this, PreferenceUtil.POINT_B_LONG);

        }

        @Override
        public void onTick(long milliSecond) {

        }

        @Override
        public void onFinish() {

            Intent intent;

            if (user_id.equals(PreferenceUtil.NO_VALUE)) {
                intent = new Intent(SplashScreen.this, LoginActivity.class);

            } else {
                intent = new Intent(SplashScreen.this, DrawerActivity.class);

            }

            startActivity(intent);
            finish();

        }
    }
}
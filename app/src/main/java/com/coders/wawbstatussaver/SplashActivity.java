package com.coders.wawbstatussaver;

import static com.coders.wawbstatussaver.util.Utils.permissions;
import static com.coders.wawbstatussaver.util.Utils.permissions13;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.coders.wawbstatussaver.intro.IntroActivity;
import com.coders.wawbstatussaver.util.SharedPrefs;
import com.coders.wawbstatussaver.util.Utils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AppCompatDelegate.setDefaultNightMode(SharedPrefs.getAppNightDayMode(this));

        Utils.setLanguage(SplashActivity.this, SharedPrefs.getLang(SplashActivity.this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!Utils.hasPermissions(this, permissions13) && Utils.isNotificationServiceRunning(this)) {
                gotoNext();
            } else {
                gotoIntro();
            }
        } else {
            if (!Utils.hasPermissions(this, permissions) && Utils.isNotificationServiceRunning(this)) {
                gotoNext();
            } else {
                gotoIntro();
            }
        }


    }

    void gotoNext() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }, 600);

    }

    void gotoIntro() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }, 600);

    }

}
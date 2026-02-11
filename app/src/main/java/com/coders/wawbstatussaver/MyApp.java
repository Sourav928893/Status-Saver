package com.coders.wawbstatussaver;

import android.app.Application;
import android.content.Context;

import com.coders.wawbstatussaver.util.AdController;

public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        
        // Initialize AdMob
        AdController.initAd(this);
    }

    public static Context getAppContext() {
        return context;
    }
}

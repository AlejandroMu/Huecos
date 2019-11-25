package com.example.huecoscolombia.app;

import android.app.Application;
import android.content.Context;

public class HuecosColombiaApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        HuecosColombiaApp.context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}

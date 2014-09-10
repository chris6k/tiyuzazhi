package com.tiyuzazhi.app;

import android.app.Application;
import android.content.Context;

/**
 * @author chris.xue
 */
public class TiyuApp extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getContext() {
        return applicationContext;
    }
}

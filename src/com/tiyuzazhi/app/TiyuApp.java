package com.tiyuzazhi.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.tiyuzazhi.service.CheckNotifyService;

/**
 * @author chris.xue
 */
public class TiyuApp extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        startService(new Intent(applicationContext, CheckNotifyService.class));
    }

    public static Context getContext() {
        return applicationContext;
    }
}

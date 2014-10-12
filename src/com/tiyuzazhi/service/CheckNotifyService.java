package com.tiyuzazhi.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.tiyuzazhi.api.UserApi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author kun
 */
public class CheckNotifyService extends Service {
    private ScheduledExecutorService checkThread = Executors.newScheduledThreadPool(1);
    private volatile boolean hasNotify = false;
    private SimpleBinder binder;

    public class SimpleBinder extends Binder {

        /**
         * @return
         */
        public CheckNotifyService getService() {
            return CheckNotifyService.this;
        }

        public boolean hasNotiy() {
            return hasNotify;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        binder = new SimpleBinder();
        checkThread.schedule(new Runnable() {
            @Override
            public void run() {
                hasNotify = UserApi.checkNotify();
            }
        }, 30, TimeUnit.SECONDS);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

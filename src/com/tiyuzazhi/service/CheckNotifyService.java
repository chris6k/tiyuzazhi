package com.tiyuzazhi.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.app.HomeActivity;
import com.tiyuzazhi.app.R;

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
        final NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        binder = new SimpleBinder();
        checkThread.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                hasNotify = UserApi.checkNotify();
                if (hasNotify) {
                    //获得通知管理器
                    NotificationManager manager = (NotificationManager) getSystemService(CheckNotifyService.NOTIFICATION_SERVICE);
                    //构建一个通知对象(需要传递的参数有三个,分别是图标,标题和 时间)
                    Notification notification = new Notification();
//                    R.drawable.ic_launcher,"通知",System.currentTimeMillis()
                    notification.icon = R.drawable.ic_launcher;
                    notification.tickerText = "你有新的通知";
                    Intent intent = new Intent(CheckNotifyService.this, HomeActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(CheckNotifyService.this, 0, intent, 0);
                    notification.setLatestEventInfo(getApplicationContext(), "你有新的通知", "你有新的通知", pendingIntent);
                    notification.flags = Notification.FLAG_AUTO_CANCEL;//点击后自动消失
                    notification.defaults = Notification.DEFAULT_SOUND;//声音默认
                    manager.notify(1, notification);//发动通知,id由自己指定，每一个Notification对应的唯一标志
                    //其实这里的id没有必要设置,只是为了下面要用到它才进行了设置
                }
            }
        }, 0, 30, TimeUnit.SECONDS);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

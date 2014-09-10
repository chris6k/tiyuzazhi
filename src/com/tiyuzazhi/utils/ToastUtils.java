package com.tiyuzazhi.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.tiyuzazhi.app.TiyuApp;

/**
 * @author chris.xue
 */
public class ToastUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void show(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TiyuApp.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

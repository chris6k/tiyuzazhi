package com.tiyuzazhi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.tiyuzazhi.app.TiyuApp;

/**
 * @author chris.xue
 */
public class LocalUtils {
    private static SharedPreferences sp;

    static {
        sp = TiyuApp.getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
    }


    public static synchronized void put(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public static synchronized String get(String key, String defaultVal) {
        return sp.getString(key, defaultVal);
    }

    public static synchronized void put(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public static synchronized int get(String key, int defaultVal) {
        return sp.getInt(key, defaultVal);
    }


}

package com.tiyuzazhi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * @author chris.xue
 */
public final class NetUtils {

    private NetUtils() {
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("NetUtil.isOnline", "Exception", e);
        }
        return false;
    }

    public static boolean isWifi(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("NetUtils.isWifi", "Exception", e);
        }
        return false;
    }

    public static boolean isConnect(Context context) {
        return isConnect(context, false);
    }

    private static boolean isConnect(Context context, boolean needWIFI) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                return !(info != null && info.isConnected()
                        && info.getState() == NetworkInfo.State.CONNECTED
                        && needWIFI && ConnectivityManager.TYPE_WIFI != info.getType());
            }
        } catch (Exception e) {
            Log.e("NetUtils.isConnect", "Exception", e);
            return false;
        }
        return false;
    }
}

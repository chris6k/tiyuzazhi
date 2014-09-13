package com.tiyuzazhi.utils;

import android.graphics.Bitmap;

/**
 * @author chris.xue
 *         图片下载
 */
public class ImageLoader {

    public static Bitmap loadPic(String url, ImageLoaderCallback callback) {
        //TODO
        return null;
    }

    public interface ImageLoaderCallback {
        void finish(Bitmap image);

        void error(Exception e);
    }
}

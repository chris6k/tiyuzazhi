package com.tiyuzazhi.utils;

import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Utilities for Image.
 * 
 * @author out-sourcing, Chris.xue
 * 
 */
public final class ImageUtils {

    private static final int[] BACKGROUND_COLOR = new int[]{0xff333333, 0xffff69b4, 0xffff6347, 0xffff1493, 0xff2ca7ea,
            0xfffffaf0, 0xff82b1ed};

    private ImageUtils() {

    }

    /**
     * get Resized Bitmap with given width and height but keeps the original
     * aspect ratio.
     * 
     * @param map
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getResizedBitmap(final Bitmap map, int width, int height) {
        if (map == null) {
            return map;
        }
        // 源文件的大小
        int w = map.getWidth();
        int h = map.getHeight();
        // 宽度缩小比例
        float scaleWidth = ((float) width) / w;
        // 高度缩小比例
        float scaleHeight = ((float) height) / h;
        // calculate the scale - in this case = 0.4f
        // 矩阵
        Matrix m = new Matrix();
        // 设置矩阵比例
        m.postScale(scaleWidth, scaleHeight);
        Bitmap thumbnail = Bitmap.createBitmap(map, 0, 0, w, h, m, true);
        Log.d("thumbnail dimensions", "[" + thumbnail.getWidth() + "," + thumbnail.getHeight() + "]");
        return thumbnail;
    }

    /**
     * get Resized Bitmap with given width and height.
     * 
     * @param map
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getResizedBitmap2(final Bitmap map, int width, int height) {
        if (map == null) {
            return map;
        }
        int sourceWidth = map.getWidth();
        int sourceHeight = map.getHeight();
        int h = height;
        int w = width;
        if (sourceHeight < sourceWidth) {
            h = (int) (((float) w / sourceWidth) * sourceHeight);
        } else {
            w = (int) (((float) h / sourceHeight) * sourceWidth);
        }
        return getResizedBitmap(map, w, h);
    }

    /**
     * get Resized Bitmap with given width and height and will crop original
     * bitmap.
     * 
     * @param map
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getResizedBitmap3(final Bitmap map, int width, int height) {
        if (map == null) {
            return map;
        }
        int sourceWidth = map.getWidth();
        int sourceHeight = map.getHeight();
        int w = sourceWidth;
        int h = sourceHeight;
        w = (int) (((float) width / height) * sourceHeight);
        if (sourceWidth < w) {
            w = sourceWidth;
            h = (int) (((float) height / width) * sourceWidth);
        }
        if (sourceHeight < h) {
            h = sourceHeight;
        }

        int startX = (Math.max(sourceWidth, w) - w) / 2;
        int startY = (Math.max(sourceHeight, h) - h) / 2;
        if (w != sourceWidth || h != sourceWidth) {
            Bitmap croppedBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
            Canvas c = new Canvas(croppedBitmap);
            c.drawBitmap(map, -startX, -startY, null);
            return croppedBitmap;
        } else {
            return map;
        }
    }

    /**
     * convert bitmap to byte array compress format is PNG.
     * 
     * @param map
     * @return
     */
    public static byte[] getBitmapByte(Bitmap map) {
        if (map == null) {
            return new byte[0];
        }
        CompressFormat format = CompressFormat.PNG;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        map.compress(format, 100, os);
        return os.toByteArray();
    }

    /**
     * draw shadow to original bitmap.
     * 
     * @param map
     * @param radius
     * @return
     */
    public static Bitmap drawShadow(Bitmap map, int radius) {
        if (map == null) {
            return null;
        }

        BlurMaskFilter blurFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
        Paint shadowPaint = new Paint();
        shadowPaint.setMaskFilter(blurFilter);

        int[] offsetXY = new int[2];
        Bitmap shadowImage = map.extractAlpha(shadowPaint, offsetXY);
        shadowImage = shadowImage.copy(Config.ARGB_8888, true);
        Canvas c = new Canvas(shadowImage);
        c.drawBitmap(map, -offsetXY[0], 0, null);
        // c.drawBitmap(map, -offsetXY[0], -offsetXY[1], null);
        return shadowImage;
    }

    /**
     * decodes image and scales it to reduce memory consumption.
     * 
     * @param imageByteArray
     * @param requiredSize
     *            The new size we want to scale to
     * @return
     */
    public static Bitmap decodeFile(byte[] imageByteArray, int requiredSize) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length, o);

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            if (requiredSize != -1 && requiredSize > 0) {
                while (o.outWidth / scale / 2 >= requiredSize && o.outHeight / scale / 2 >= requiredSize) {
                    scale *= 2;
                }
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = Math.max(scale, 3);
            return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length, o2);
        } catch (Exception e) {
            Log.e("ImageUtils", "Exception", e);
        }
        return null;
    }

    /**
     * 
     * @param loadDrawable
     * @return null if loadDrawable is not the instance of BitmapDrawable
     */
    public static byte[] getBitmapByte(Drawable loadDrawable) {
        if (loadDrawable instanceof BitmapDrawable) {
            return getBitmapByte(((BitmapDrawable) loadDrawable).getBitmap());
        }
        return null;
    }

    public static Bitmap drawText(String text, int size, int sourceWidth, int sourceHeight) {
        try {
            int width = sourceWidth;
            int height = sourceHeight;
            // BlurMaskFilter blurFilter = new BlurMaskFilter(10,
            // BlurMaskFilter.Blur.NORMAL);
            TextPaint paint = new TextPaint();
            paint.setShadowLayer(5f, 2, 2, Color.BLACK);
            paint.setStyle(Style.FILL);
            // paint.setMaskFilter(blurFilter);
            paint.setTextSize(size);
            // paint.setColor(Color.BLACK);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas c = new Canvas(bitmap);
            c.drawColor(Color.WHITE);
            // c.drawPaint(paint);
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            StaticLayout sl = new StaticLayout(text, paint, c.getWidth() - 2, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

            Paint backPaint = new Paint();
            backPaint.setColor(BACKGROUND_COLOR[(int) (Math.random() * (BACKGROUND_COLOR.length - 1))]);
            int h2 = Math.max(height / 3, sl.getHeight() + 20);
            if (h2 > height) {
                h2 = sl.getHeight();
            }
            int y = (height - h2) / 2;
            int x = 2;
            c.translate(0, y);
            c.drawRect(new Rect(0, 0, width, h2), backPaint);
            c.translate(0, -y);
            c.translate(x, (height - sl.getHeight()) / 2);
            sl.draw(c);
            return bitmap;
        } catch (Exception e) {
            Log.e("ImageUtils", "Exception", e);
        }
        return null;
    }
}

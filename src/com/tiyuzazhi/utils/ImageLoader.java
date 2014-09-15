package com.tiyuzazhi.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.tiyuzazhi.app.R;
import com.tiyuzazhi.app.TiyuApp;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author chris.xue
 *         图片下载
 */
public class ImageLoader {

    private static FileUtils fileUtils;

    static {
        fileUtils = new FileUtils();
    }

    public static Bitmap loadPic(String url, ImageLoaderCallback callback) {
        Drawable drawable = TiyuApp.getContext().getResources().getDrawable(R.drawable.header);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (callback != null) {
            callback.finish(bitmap);
        }
        return bitmap;
//        InputStream i = null;
//        BitmapDrawable d;
//        String saveToLocalFileName = url.replace("/", "").replace(":", "");
//        String fullFilePath = FileUtils.getSDPATH() + saveToLocalFileName;
//        Bitmap source = null;
//        try {
//            Log.d("loadImageFromUrl", url);
//
//            byte[] imageByteArray;
//            if (fileUtils.fileIsExists(fullFilePath)) {
//                i = new FileInputStream(new File(fullFilePath));
//            } else {
//                if (!NetUtils.isWifi(TiyuApp.getContext())) {
//                    return null;
//                }
//                if (!NetUtils.isConnect(TiyuApp.getContext())) {
//                    return null;
//                }
//                HttpGet get = new HttpGet(url);
//                Future<HttpResponse> responseFuture = TiHttp.getInstance().send(get);
//                HttpResponse response = responseFuture.get(1, TimeUnit.MINUTES);
//
//                if (response != null && response.getStatusLine().getStatusCode() == 200) {
//                    i = response.getEntity().getContent();
//                }
//
//                if (i == null) {
//                    return null;
//                }
//                ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
//                int readSize;
//                byte[] buf = new byte[1024];
//                while ((readSize = i.read(buf)) > -1) {
//                    bos.write(buf, 0, readSize);
//                }
//                i.close();
//                imageByteArray = bos.toByteArray();
//
//                if (imageByteArray != null && imageByteArray.length > 0) {
//                    if (callback != null) {
//                        source = ImageUtils.decodeFile(imageByteArray, 120);
//                        callback.finish(source);
//                    }
//                    if (imageByteArray.length > 0) {
//                        TPool.post(new SaveToLocalFile(imageByteArray, saveToLocalFileName));
//                        i = new ByteArrayInputStream(imageByteArray);
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return null;
//                }
//            }
//            d = (BitmapDrawable) BitmapDrawable.createFromStream(i, saveToLocalFileName);
//            try {
//                i.close();
//            } catch (Exception e) {
//            }
//            return d.getBitmap();
//
//        } catch (MalformedURLException e) {
//            Log.e("ImageLoader", "MalformedURLException", e);
//        } catch (IOException e) {
//            Log.e("online", "ImageLoader", e);
//        } catch (Exception e) {
//            Log.e("ImageLoader", "Exception", e);
//        } finally {
//            if (source != null && !source.isRecycled()) {
//                source.recycle();
//            }
//        }
//        return null;
    }


    public interface ImageLoaderCallback {
        void finish(Bitmap image);

        void error(Exception e);
    }

    /**
     * @author chris.xue
     */
    private static class SaveToLocalFile implements Runnable {
        private byte[] file;
        private String fileName;

        public SaveToLocalFile(byte[] data, String name) {
            this.file = data;
            this.fileName = name;
        }

        public void run() {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            try {
                fileUtils.saveFile(new ByteArrayInputStream(file), "", fileName);
            } catch (IOException e) {
                Log.e("ImageLoader", "IOException", e);
            }
        }
    }
}

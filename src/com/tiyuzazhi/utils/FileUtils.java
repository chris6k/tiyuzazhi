/**
 *
 */
package com.tiyuzazhi.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.*;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

/**
 * @author chris.xue
 */
public class FileUtils {

    private static final String SDROOT;
    private static final String SDPATH;
    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            md5 = null;
        }
    }

    public FileUtils() {
    }

    public static String getSDPATH() {
        return SDPATH;
    }

    public static String getSDROOT() {
        return SDROOT;
    }

    /**
     * 在SD卡上创建文件.
     *
     * @throws java.io.IOException
     */
    public File creatSDFile(String fileName) throws IOException {
        Log.d("FileHelper", "createSDFile" + fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 判断文件是否存在.
     */
    public boolean fileIsExists(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    /**
     * 在SD卡上写入文件内容.
     *
     * @throws java.io.IOException
     */
    public boolean writeToFile(String directory, String fileName, String strContent) {
        if (TextUtils.isEmpty(strContent) || TextUtils.isEmpty(directory) || TextUtils.isEmpty(fileName)) {
            return false;
        }
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            return false;
        }
        try {
            // 判断文件是否存在
            if (!fileIsExists(directory + fileName)) {
                // 判断目录是否存在
                if (!fileIsExists(directory)) {
                    File f = new File(directory);
                    // 创建目录
                    f.mkdirs();
                }
                // 创建文件
                creatSDFile(directory + fileName);
            } else {
                delSDFile(directory + fileName);
            }

            FileWriter fw = new FileWriter(directory + fileName);
            fw.write(strContent);
            fw.close();
            return true;
        } catch (IOException e) {
            Log.e("FileHelper", e.toString());
            return false;
        }
    }

    public void saveFile(InputStream is, String root, String direPrefix, String fileName) throws IOException {
        if (is == null) {
            return;
            // throw new RuntimeException("stream is null");
        }
        // 把文件存到path
        String direcroty = root;
        String path = direcroty + direPrefix + fileName;

        if (!fileIsExists(path)) {
            // 判断目录是否存在
            if (!fileIsExists(direcroty + direPrefix)) {
                File f = new File(direcroty + direPrefix);
                // 创建目录
                f.mkdirs();
            }
            Log.v("create", path);
            // 创建文件
            creatSDFile(path);
        }

        FileOutputStream fos = new FileOutputStream(path);

        byte[] bt = new byte[1024];

        int i = 0;

        while ((i = is.read(bt)) > 0) {

            fos.write(bt, 0, i);

        }

        fos.flush();

        fos.close();

        is.close();
    }

    // 将文件流保存至本地
    public void saveFile(InputStream is, String direPrefix, String fileName) throws IOException {
        saveFile(is, SDPATH + "image/", direPrefix, fileName);
        // if (is == null) {
        // return;
        // // throw new RuntimeException("stream is null");
        // }
        // // 把文件存到path
        // String direcroty = SDPATH + "image/";
        // String path = direcroty + direPrefix + fileName;
        //
        // if (!fileIsExists(path)) {
        // // 判断目录是否存在
        // if (!fileIsExists(direcroty + direPrefix)) {
        // File f = new File(direcroty + direPrefix);
        // // 创建目录
        // f.mkdirs();
        // }
        // Log.v("create", path);
        // // 创建文件
        // creatSDFile(path);
        // }
        //
        // FileOutputStream fos = new FileOutputStream(path);
        //
        // byte[] bt = new byte[1024];
        //
        // int i = 0;
        //
        // while ((i = is.read(bt)) > 0) {
        //
        // fos.write(bt, 0, i);
        //
        // }
        //
        // fos.flush();
        //
        // fos.close();
        //
        // is.close();

    }

    /**
     * 读取sd卡上的内容.
     *
     * @throws Exception
     */
    /*
     * public String readFromFile(String fileName) throws Exception { //
     * BufferedReader reader; // StringBuffer sb; // try { // reader = new
     * BufferedReader(new FileReader(fileName)); // String s =
     * reader.readLine(); // sb= new StringBuffer(); // while (s != null) { //
     * sb.append(s); // sb.append("\r\n"); // s = reader.readLine(); // } //
     * System.out.println(sb.toString()); // reader.close(); try { return
     * HttpHandler.convertCodeAndGetText(fileName); // return sb.toString(); }
     * catch (FileNotFoundException e) { e.printStackTrace(); return ""; } catch
     * (IOException e) { Auto-generated catch block e.printStackTrace(); return
     * ""; }
     * 
     * // return sb.toString(); }
     */
    public String readFromFile(String filepath) {
        String path = filepath;
        if (null == path) {
            Log.d("online", "Error: Invalid file name!");
            return null;
        }

        String filecontent = null;
        File f = new File(path);
        if (f != null && f.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
            } catch (FileNotFoundException e1) {
                Log.e("online", "Error: Input File not find!");
                return null;
            }

            CharBuffer cb;
            try {
                cb = CharBuffer.allocate(fis.available());
            } catch (Exception e1) {
                Log.e("online", "Error: CharBuffer initial failed!");
                try {
                    fis.close();
                } catch (Exception e) {
                    // do nothing.
                }
                return null;
            }
            InputStreamReader isr;
            try {
                isr = new InputStreamReader(fis, "utf-8");
                StringBuffer sb = new StringBuffer();

                while (isr.read(cb) > 0) {
                    sb.append(cb.array());
                }

                filecontent = sb.toString();
                isr.close();
            } catch (Exception e) {
                Log.e("FileHelper", e.toString());
                return null;
            }
        }
        // Log.d("online", "readFile filecontent = " + filecontent);
        if (!TextUtils.isEmpty(filecontent)) {
            filecontent = filecontent.trim();
        }
        return filecontent;
    }

    /**
     * 删除SD卡上的文件.
     *
     * @param fileName
     */
    public boolean delSDFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        file.delete();
        return true;
    }

    /**
     * 在SD卡上创建目录.
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 递归计算文件夹大小.
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File[] flist = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 转换文件大小.
     *
     * @param fileS
     * @return
     */
    public String FormatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static void deletDir(String dirName) {
        // 获取SDCard目录,2.2的时候为:/mnt/sdcard
        File sdCardDir = Environment.getExternalStorageDirectory();
        // 2.1的时候为：/sdcard，所以使用静态方法得到路径会好一点。
        File dir = new File(sdCardDir, dirName);
        DeleteRecursive(dir);
    }

    public static void cleanCache() {
        deletDir(Constants.CACHE_PATH);
    }

    public static void DeleteRecursive(File dir) {
        Log.d("DeleteRecursive", "DELETEPREVIOUS TOP" + dir.getPath());
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                File temp = new File(dir, children[i]);
                if (temp.isDirectory()) {
                    Log.d("DeleteRecursive", "Recursive Call" + temp.getPath());
                    DeleteRecursive(temp);
                } else {
                    Log.d("DeleteRecursive", "Delete File" + temp.getPath());
                    boolean b = temp.delete();
                    if (!b) {
                        Log.d("DeleteRecursive", "DELETE FAIL");
                    }
                }
            }
            dir.delete();
        }
    }

    public static String getSavedFileName(String filename) {
        String fileName = filename;
        fileName = fileName.replace("http://", "");
        fileName = fileName.replace("/", "_");
        fileName = fileName.replace(" ", "");
        fileName = fileName.replace(".", "_");
        fileName += "_tmp";

        if (md5 != null) {
            byte[] hash = md5.digest(fileName.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            return hexString.toString();
        } else {
            return fileName;
        }

    }

    static {
        SDROOT = Environment.getExternalStorageDirectory().getPath() + Constants.APP_ROOT;
        SDPATH = Environment.getExternalStorageDirectory().getPath() + Constants.CACHE_PATH;
    }
}

package com.tiyuzazhi.api;

import android.util.Log;
import com.tiyuzazhi.beans.Examiner;
import com.tiyuzazhi.beans.StatsDashboard;
import com.tiyuzazhi.beans.User;
import com.tiyuzazhi.utils.LocalUtils;
import com.tiyuzazhi.utils.TiHttp;
import com.tiyuzazhi.utils.ToastUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author chris.xue
 */
public class UserApi {
    public static final String USER_ENDPOINT = TiHttp.HOST + "/user";
    public static final String USER_DASHBOARD_ENDPOINT = USER_ENDPOINT + "/todo";
    public static final String USER_LOGIN_ENDPOINT = USER_ENDPOINT + "/login";
    public static final String USER_REGISTER_ENDPOINT = USER_ENDPOINT + "/register";
    public static final String USER_EXAMLIST_ENDPOINT = USER_ENDPOINT + "/examiners";
    public static final String USER_MAIL_COUNT_ENDPOINT = USER_ENDPOINT + "/mailCount";

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER = "user";
    public static final String KEY_USER_ROLE = "userRole";
    public static final String KEY_LAST_MAIL_ID = "lastMailId";

    /**
     * 读取当前用户信息
     *
     * @return
     */
    public static User getUserInfo() {
        User user = null;
        if (LocalUtils.get(KEY_USER_ID, 0) != 0) {
            try {
                user = new User(new JSONObject(LocalUtils.get(KEY_USER, "{}")));
            } catch (JSONException e) {
                return null;
            }
        }
        return user;


//        user = new User();
//        user.setEmail("test@test.com");
//        user.setId(1);
//        user.setCompany("东华大学出版社");
//        user.setName("鲁连海");
//        user.setRole(1);
//        user.setAddress("上海市二大街");
//        user.setMobile("1380000000");
//        user.setFavCount(1);
//        user.setMsgCount(5);
//        user.setIconPath("");
//        return user;
    }

    /**
     * 读取当前用户身份
     *
     * @return
     */
    public static int loginRole() {
        return LocalUtils.get(KEY_USER_ROLE, 0);
//        return 1;
    }

    /**
     * 获取用户dashboard信息
     *
     * @return
     */
    public static StatsDashboard getUserDashboard() {
        int userId = LocalUtils.get(KEY_USER_ID, 0);
        HttpGet get = new HttpGet(USER_DASHBOARD_ENDPOINT + "?uid=" + userId);
        try {
            HttpResponse response = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getBoolean("result")) {
                    return new StatsDashboard(jsonObject.getJSONObject("data"));
                }
            }
        } catch (TimeoutException e) {
            ToastUtils.show("请求超时");
        } catch (Exception e) {
            Log.e("UserApi", "Exception", e);
            ToastUtils.show("发生异常，请稍候再试");
        }
//        StatsDashboard dashboard = new StatsDashboard();
//        dashboard.setChiefEditorTaskNo(0);
//        dashboard.setEditorTaskNo(0);
//        dashboard.setUserCenterTaskNo(1);
//        dashboard.setMasterCenterTaskNo(4);
        return new StatsDashboard();
    }

    /**
     * 根据条件返回评审员信息
     *
     * @param type
     * @param offset
     * @param count
     * @return
     */
    public static List<Examiner> getExaminer(int type, int offset, int count) {
//        HttpGet get = new HttpGet(USER_EXAMLIST_ENDPOINT + "?type=" + type + "&offset=" + offset + "&count=" + count);
//        try {
//            HttpResponse response = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                JSONArray array = new JSONArray(EntityUtils.toString(response.getEntity()));
//                List<Examiner> examiners = new ArrayList<Examiner>(array.length());
//                for (int i = 0; i < array.length(); i++) {
//                    examiners.add(new Examiner(array.getJSONObject(i)));
//                }
//                return examiners;
//            } else {
//                return new ArrayList<Examiner>(0);
//            }
//        } catch (TimeoutException e) {
//            ToastUtils.show("请求超时");
//        } catch (Exception e) {
//            Log.e("UserApi", "Exception", e);
//            ToastUtils.show("发生异常，请稍候再试");
//        }
//        return new ArrayList<Examiner>(0);
        Examiner examiner = new Examiner();
        examiner.setEmail("test@test.com");
        examiner.setId(1);
        examiner.setCompany("东华大学出版社");
        examiner.setName("鲁连海");
        examiner.setRole(1);
        examiner.setAddress("上海市二大街");
        examiner.setMobile("1380000000");
        examiner.setFavCount(1);
        examiner.setMsgCount(5);
        examiner.setIconPath("");
        examiner.setStatus(1);
        examiner.setType(1);
        List<Examiner> examiners = new ArrayList<Examiner>(5);
        examiners.add(examiner);
        examiners.add(examiner);
        examiners.add(examiner);
        examiners.add(examiner);
        examiners.add(examiner);
        return examiners;
    }


    /**
     * 登出
     *
     * @return
     */
    public static boolean logout() {
//        HttpGet get = new HttpGet(USER_LOGOUT_ENDPOINT + "?userId=");
//        try {
//            HttpResponse response = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
//            if (response.getStatusLine().getStatusCode() == 200) {
        LocalUtils.put(KEY_USER_ROLE, 0);
        LocalUtils.put(KEY_USER_ID, 0);
        LocalUtils.put(KEY_USER, "");
        LocalUtils.put(KEY_LAST_MAIL_ID, 0);
        return true;
//            } else {
//                return false;
//            }
//        } catch (TimeoutException e) {
//            ToastUtils.show("请求超时");
//        } catch (Exception e) {
//            Log.e("UserApi", "Exception", e);
//            ToastUtils.show("发生异常，请稍候再试");
//        }
//        return false;
    }

    public static User login(String userName, String password) {
        User user = null;
        HttpPost post = new HttpPost(USER_LOGIN_ENDPOINT);
        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("username", userName));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
            post.setEntity(encodedFormEntity);
            HttpResponse res = TiHttp.getInstance().send(post).get(1, TimeUnit.MINUTES);
            if (res.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(res.getEntity());
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getBoolean("result")) {
                    user = new User(jsonObject.getJSONObject("data"));
                    LocalUtils.put(KEY_USER, jsonObject.getString("data"));
                    LocalUtils.put(KEY_USER_ID, user.getId());
                    LocalUtils.put(KEY_USER_ROLE, user.getRole());
                    ToastUtils.show("登录成功");
                } else {
                    ToastUtils.show("登录失败, 用户名或密码错误");
                }
            }
        } catch (TimeoutException e) {
            ToastUtils.show("请求超时");
        } catch (Exception e) {
            Log.e("UserApi", "Exception", e);
            ToastUtils.show("发生异常，请稍候再试");
        }
        return user;

    }

    public static boolean register(String userName, String password) {
        HttpPost post = new HttpPost(USER_REGISTER_ENDPOINT);
        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("username", userName));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
            post.setEntity(encodedFormEntity);
            HttpResponse res = TiHttp.getInstance().send(post).get(1, TimeUnit.MINUTES);
            if (res.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(res.getEntity());
                JSONObject jsonObject = new JSONObject(content);
                return (jsonObject.getBoolean("result"));
            }
        } catch (TimeoutException e) {
            ToastUtils.show("请求超时");
        } catch (Exception e) {
            Log.e("UserApi", "Exception", e);
            ToastUtils.show("发生异常，请稍候再试");
        }
        ToastUtils.show("注册失败");
        return false;

    }

    public static boolean checkNotify() {
        int userId = LocalUtils.get(KEY_USER_ID, 0);
        if (userId == 0) return false;
        HttpPost post = new HttpPost(USER_MAIL_COUNT_ENDPOINT);
        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("uid", String.valueOf(userId)));
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
            post.setEntity(encodedFormEntity);
            HttpResponse res = TiHttp.getInstance().send(post).get(1, TimeUnit.MINUTES);
            if (res.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(res.getEntity());
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getBoolean("result")) {
                    JSONObject obj = jsonObject.getJSONObject("data");
                    if (obj.has("mailId") && obj.getInt("mailId") > LocalUtils.get(KEY_LAST_MAIL_ID, 0)) {
                        LocalUtils.put(KEY_LAST_MAIL_ID, obj.getInt("mailId"));
                        return true;
                    }
                }
            }
        } catch (TimeoutException e) {
            ToastUtils.show("请求超时");
        } catch (Exception e) {
            Log.e("UserApi", "Exception", e);
            ToastUtils.show("发生异常，请稍候再试");
        }
        return false;
    }
}

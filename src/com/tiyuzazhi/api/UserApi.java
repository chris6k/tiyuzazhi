package com.tiyuzazhi.api;

import com.tiyuzazhi.beans.Examiner;
import com.tiyuzazhi.beans.StatsDashboard;
import com.tiyuzazhi.beans.User;
import com.tiyuzazhi.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chris.xue
 */
public class UserApi {
    public static final String USER_ENDPOINT = Constants.API_ROOT + "/api/user";
    public static final String USER_DASHBOARD_ENDPOINT = Constants.API_ROOT + "/api/user/dashboard";
    public static final String USER_LOGIN_ENDPOINT = Constants.API_ROOT + "/api/login";
    public static final String USER_LOGOUT_ENDPOINT = Constants.API_ROOT + "/api/logout";
    public static final String USER_EXAMLIST_ENDPOINT = Constants.API_ROOT + "/api/examiners";

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER = "user";

    /**
     * 读取当前用户信息
     *
     * @return
     */
    public static User getUserInfo() {
        User user = null;
//        if (LocalUtils.get(KEY_USER_ID, 0) != 0) {
//            try {
//                user = new User(new JSONObject(LocalUtils.get(KEY_USER, "{}")));
//            } catch (JSONException e) {
//                return null;
//            }
//        }
//        return user;


        user = new User();
        user.setEmail("test@test.com");
        user.setId(1);
        user.setCompany("东华大学出版社");
        user.setName("鲁连海");
        user.setRole(1);
        user.setAddress("上海市二大街");
        user.setMobile("1380000000");
        user.setFavCount(1);
        user.setMsgCount(5);
        user.setIconPath("");
        return user;
    }

    /**
     * 读取当前用户身份
     *
     * @return
     */
    public static int loginRole() {
//        return LocalUtils.get("role", 0);
        return 1;
    }

    /**
     * 获取用户dashboard信息
     *
     * @return
     */
    public static StatsDashboard getUserDashboard() {
//        int userId = LocalUtils.get(KEY_USER_ID, 0);
//        HttpGet get = new HttpGet(USER_DASHBOARD_ENDPOINT + "?id=" + userId);
//        try {
//            HttpResponse response = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
//                return new StatsDashboard(jsonObject);
//            } else {
//                return new StatsDashboard();
//            }
//        } catch (TimeoutException e) {
//            ToastUtils.show("请求超时");
//        } catch (Exception e) {
//            Log.e("UserApi", "Exception", e);
//            ToastUtils.show("发生异常，请稍候再试");
//        }
        StatsDashboard dashboard = new StatsDashboard();
        dashboard.setChiefEditorTaskNo(1);
        dashboard.setEditorTaskNo(2);
        return dashboard;
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
//        int userId = LocalUtils.get(KEY_USER_ID, 0);
//        HttpGet get = new HttpGet(USER_LOGOUT_ENDPOINT);
//        try {
//            HttpResponse response = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                LocalUtils.put(KEY_USER_ID, 0);
//                LocalUtils.put(KEY_USER, "");
//                return true;
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
        return true;
    }

    public static User login(String userName, String password) {
//        User user = null;
//        int userId = LocalUtils.get(KEY_USER_ID, 0);
//        if (userId == 0) return null;
//        else {
//            HttpPost post = new HttpPost(USER_ENDPOINT);
//            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
//            nameValuePairs.add(new BasicNameValuePair("username", userName));
//            nameValuePairs.add(new BasicNameValuePair("password", password));
//            try {
//                UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
//                post.setEntity(encodedFormEntity);
//                HttpResponse res = TiHttp.getInstance().send(post).get(1, TimeUnit.MINUTES);
//                if (res.getStatusLine().getStatusCode() == 200) {
//                    String content = EntityUtils.toString(res.getEntity());
//                    JSONObject jsonObject = new JSONObject(content);
//                    user = new User(jsonObject);
//                    LocalUtils.put(KEY_USER, content);
//                    LocalUtils.put(KEY_USER_ID, user.getId());
//                    LocalUtils.put("userRole", user.getRole());
//                }
//            } catch (TimeoutException e) {
//                ToastUtils.show("请求超时");
//            } catch (Exception e) {
//                Log.e("UserApi", "Exception", e);
//                ToastUtils.show("发生异常，请稍候再试");
//            }
//        }
//        return user;

        User user = new User();
        user.setEmail("test@test.com");
        user.setId(1);
        user.setCompany("东华大学出版社");
        user.setName("鲁连海");
        user.setRole(1);
        user.setAddress("上海市二大街");
        user.setMobile("1380000000");
        user.setFavCount(1);
        user.setMsgCount(5);
        return user;
    }
}

package com.tiyuzazhi.api;

import android.util.Log;
import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.beans.Magazine;
import com.tiyuzazhi.beans.Notice;
import com.tiyuzazhi.utils.LocalUtils;
import com.tiyuzazhi.utils.TiHttp;
import com.tiyuzazhi.utils.ToastUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author chris.xue
 */
public class ArticleApi {

    /**
     * 通过审核
     *
     * @param article 待审核的文章
     * @return
     */
    public static boolean passExamine(ExaminingArticle article) {
        //TODO
        return true;
    }

    /**
     * 通过审核
     *
     * @param articleId 待审核的文章id
     * @return
     */
    public static boolean passExamine(int articleId) {
        //TODO
        return true;
    }


    /**
     * 审核失败
     *
     * @param article 待审核的文章
     * @return
     */
    public static boolean rejectExamine(ExaminingArticle article) {
        //TODO
        return true;
    }

    /**
     * 审核失败
     *
     * @param articleId 待审核的文章id
     * @return
     */
    public static boolean rejectExamine(int articleId) {
        //TODO
        return true;
    }

    /**
     * load新的待审核的文章
     *
     * @param offset 起始位置
     * @param count  文章总数
     * @param asc
     * @return
     */
    public static List<ExaminingArticle> loadExamineArticle(int offset, int count, int step, boolean asc) {
        try {
            List<ExaminingArticle> articles;
            Integer uid = LocalUtils.get(UserApi.KEY_USER_ID, 0);
            HttpGet get = new HttpGet(TiHttp.HOST + "/exam/examArts?uid=" + uid
                    + "&step=" + (step == 0 ? "" : step) + "&asc=" + (asc ? 1 : 0) + "&of=" + offset);
            HttpResponse res = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
            if (res.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(res.getEntity());
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getBoolean("result")) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    articles = new ArrayList<ExaminingArticle>(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        articles.add(new ExaminingArticle(array.getJSONObject(i)));
                    }
                    return articles;
                }
            } else {
                ToastUtils.show("读取待审文章失败");
            }
        } catch (Exception e) {
            ToastUtils.show("读取待审文章失败");
            Log.e("TAG", "ArticleApi Error", e);
        }
        return new ArrayList<ExaminingArticle>(0);
    }

    /**
     * 获取最新的杂志信息
     *
     * @return
     */
    public static List<Magazine> loadNewestMagazine() {
        HttpGet get = new HttpGet(TiHttp.HOST + "/mag/list");
        Future<HttpResponse> responseFuture = TiHttp.getInstance().send(get);
        try {
            HttpResponse response = responseFuture.get(1, TimeUnit.MINUTES);
            if (response.getStatusLine().getStatusCode() == 200) {
                String baseString = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(baseString);
                if (object.has("result") && object.getBoolean("result")) {
                    JSONArray array = object.getJSONArray("data");
                    int len = array.length();
                    ArrayList<Magazine> magazines = new ArrayList<Magazine>(len);
                    for (int i = 0; i < len; i++) {
                        magazines.add(new Magazine(array.getJSONObject(i)));
                    }
                    return magazines;
                }
            }
        } catch (Exception e) {
            Log.e("ArticleApi", "load newest magazine failed.", e);
        }
        ToastUtils.show("读取杂志信息失败");
        return new ArrayList<Magazine>(0);
    }

    /**
     * load杂志的文章目录内容
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static List<ArticleMenu> loadArticleMenu(int magazineId) {
        HttpGet get = new HttpGet(TiHttp.HOST + "/mag/articles?magId=" + magazineId);
        Future<HttpResponse> responseFuture = TiHttp.getInstance().send(get);
        try {
            HttpResponse response = responseFuture.get(1, TimeUnit.MINUTES);
            if (response.getStatusLine().getStatusCode() == 200) {
                String baseString = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(baseString);
                if (object.has("result") && object.getBoolean("result")) {
                    JSONArray array = object.getJSONArray("data");
                    int len = array.length();
                    ArrayList<ArticleMenu> articleMenus = new ArrayList<ArticleMenu>(len);
                    for (int i = 0; i < len; i++) {
                        articleMenus.add(new ArticleMenu(array.getJSONObject(i)));
                    }
                    return articleMenus;
                }
            }
        } catch (Exception e) {
            Log.e("ArticleApi", "load newest magazine failed.", e);
        }
        ToastUtils.show("读取杂志信息失败");
        return new ArrayList<ArticleMenu>(0);
    }

    /**
     * 搜索杂志的文章
     *
     * @param keywords 搜索关键词
     * @return
     */
    public static List<ArticleMenu> search(String keywords, int index) {
        String encKeywords;
        try {
            encKeywords = URLEncoder.encode(keywords, "utf-8");
        } catch (UnsupportedEncodingException e) {
            encKeywords = keywords;
        }
        HttpGet get = new HttpGet(TiHttp.HOST + "/mag/search?keywords=" + encKeywords + "&index=" + index);
        Future<HttpResponse> responseFuture = TiHttp.getInstance().send(get);
        try {
            HttpResponse response = responseFuture.get(1, TimeUnit.MINUTES);
            if (response.getStatusLine().getStatusCode() == 200) {
                String baseString = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(baseString);
                if (object.has("result") && object.getBoolean("result")) {
                    JSONArray array = object.getJSONArray("data");
                    int len = array.length();
                    ArrayList<ArticleMenu> articleMenus = new ArrayList<ArticleMenu>(len);
                    for (int i = 0; i < len; i++) {
                        articleMenus.add(new ArticleMenu(array.getJSONObject(i)));
                    }
                    return articleMenus;
                }
            }
        } catch (Exception e) {
            Log.e("ArticleApi", "search articles failed.", e);
        }
        ToastUtils.show("读取搜索结果失败");
        return new ArrayList<ArticleMenu>(0);
    }

    /**
     * 获取通告
     *
     * @return
     */
    public static List<Notice> getNotice() {
        HttpGet get = new HttpGet(TiHttp.HOST + "/mag/news");
        Future<HttpResponse> responseFuture = TiHttp.getInstance().send(get);
        try {
            HttpResponse response = responseFuture.get(1, TimeUnit.MINUTES);
            if (response.getStatusLine().getStatusCode() == 200) {
                String baseString = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(baseString);
                if (object.has("result") && object.getBoolean("result")) {
                    JSONArray array = object.getJSONArray("data");
                    int len = array.length();
                    ArrayList<Notice> notices = new ArrayList<Notice>(len);
                    for (int i = 0; i < len; i++) {
                        notices.add(new Notice(array.getJSONObject(i)));
                    }
                    return notices;
                }
            }
        } catch (Exception e) {
            Log.e("ArticleApi", "load notice failed.", e);
        }
        ToastUtils.show("读取通告失败");
        return new ArrayList<Notice>(0);
    }

    /**
     * load next magazine
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static Magazine loadNextMagazine(int magazineId) {
        HttpGet get = new HttpGet(TiHttp.HOST + "/mag/nextMag?magId=" + magazineId);
        Future<HttpResponse> responseFuture = TiHttp.getInstance().send(get);
        try {
            HttpResponse response = responseFuture.get(1, TimeUnit.MINUTES);
            if (response.getStatusLine().getStatusCode() == 200) {
                String baseString = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(baseString);
                if (object.has("result") && object.getBoolean("result")) {
                    JSONObject data = object.getJSONObject("data");
                    return new Magazine(data);
                } else {
                    ToastUtils.show("没有更多杂志");
                    return null;
                }
            }
        } catch (Exception e) {
            Log.e("ArticleApi", "load newest magazine failed.", e);
        }
        ToastUtils.show("读取杂志信息失败");
        return null;
    }

    /**
     * load previous magazine
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static Magazine loadPrevMagazine(int magazineId) {
        HttpGet get = new HttpGet(TiHttp.HOST + "/mag/prevMag?magId=" + magazineId);
        Future<HttpResponse> responseFuture = TiHttp.getInstance().send(get);
        try {
            HttpResponse response = responseFuture.get(1, TimeUnit.MINUTES);
            if (response.getStatusLine().getStatusCode() == 200) {
                String baseString = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject(baseString);
                if (object.has("result") && object.getBoolean("result")) {
                    JSONObject data = object.getJSONObject("data");
                    return new Magazine(data);
                } else {
                    ToastUtils.show("没有更多杂志");
                    return null;
                }
            }
        } catch (Exception e) {
            Log.e("ArticleApi", "load newest magazine failed.", e);
        }
        ToastUtils.show("读取杂志信息失败");
        return null;
    }


    /**
     * 转交给新的评审评审
     *
     * @param examiningArticle
     * @param examinerIds
     * @return
     */
    public static boolean forward(ExaminingArticle examiningArticle, ArrayList<Integer> examinerIds) {
        //TODO
        return true;
    }

    /**
     * 获取评审流程
     *
     * @param articleId
     * @return
     */
    public static List<ExaminingArticle> loadExamineFlow(int articleId) {
        try {
            List<ExaminingArticle> articles;
            HttpGet get = new HttpGet(TiHttp.HOST + "/exam/examHist?aid=" + articleId);
            HttpResponse res = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
            if (res.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(res.getEntity());
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getBoolean("result")) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    articles = new ArrayList<ExaminingArticle>(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        articles.add(new ExaminingArticle(array.getJSONObject(i)));
                    }
                    if (articles.isEmpty()) {
                        ToastUtils.show("此稿件暂无摘要");
                    }
                    return articles;
                }
            } else {
                ToastUtils.show("获取稿件摘要失败");
            }
        } catch (Exception e) {
            ToastUtils.show("获取稿件摘要失败");
        }
        return new ArrayList<ExaminingArticle>(0);
    }

    /**
     * 获取某篇文章
     *
     * @param articleId
     * @return
     */
    public static ExaminingArticle loadArticle(int articleId) {
        try {
            ExaminingArticle article;
            HttpGet get = new HttpGet(TiHttp.HOST + "/exam/get?aid=" + articleId);
            HttpResponse res = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
            if (res.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(res.getEntity());
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getBoolean("result")) {
                    JSONObject json = jsonObject.getJSONObject("data");
                    article = new ExaminingArticle(json);
                    return article;
                }
            } else {
                ToastUtils.show("读取文章失败");
            }
        } catch (Exception e) {
            Log.e("TAG", "load article error", e);
        }
        return null;
    }

    /**
     * 获取最近审核的文章
     *
     * @return
     */
    public static ExaminingArticle loadArticle() {
        try {
            ExaminingArticle article;
            Integer uid = LocalUtils.get(UserApi.KEY_USER_ID, 0);
            HttpGet get = new HttpGet(TiHttp.HOST + "/exam/get?uid=" + uid);
            HttpResponse res = TiHttp.getInstance().send(get).get(1, TimeUnit.MINUTES);
            if (res.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(res.getEntity());
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getBoolean("result")) {
                    JSONObject json = jsonObject.getJSONObject("data");
                    if (json.has("id")) {
                        return new ExaminingArticle(json);
                    } else {
                        ToastUtils.show("还没有投稿");
                    }
                }
            } else {
                ToastUtils.show("读取文章失败");
            }
        } catch (Exception e) {
            Log.e("TAG", "load article error", e);
        }
        return null;
    }
}

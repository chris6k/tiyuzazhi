package com.tiyuzazhi.api;

import android.util.Log;
import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.beans.Magazine;
import com.tiyuzazhi.enums.EXAM_STEP;
import com.tiyuzazhi.utils.TiHttp;
import com.tiyuzazhi.utils.ToastUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
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
     * @return
     */
    public static List<ExaminingArticle> loadExamineArticle(int offset, int count) {
        //TODO
        ExaminingArticle examiningArticle = new ExaminingArticle();
        examiningArticle.setCategory(EXAM_STEP.SHOUGAO.getCode());
        examiningArticle.setComment("稿子很好");
        examiningArticle.setConclusion(1);
        examiningArticle.setDraftNo("10010");
        examiningArticle.setExamineStart(new Date());
        examiningArticle.setExamineFinish(new Date());
        examiningArticle.setAuthor("王小二");
        examiningArticle.setId(1);
        examiningArticle.setOpId(1);
        examiningArticle.setOpName("鲁连海");
        examiningArticle.setScore(5);
        examiningArticle.setState(1);
        examiningArticle.setStep(1);
        examiningArticle.setTitle("文章审核");
        examiningArticle.setAuthor("鲁连海");
        examiningArticle.setSummary("这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要");
        List<ExaminingArticle> examiningArticles = new ArrayList<ExaminingArticle>(5);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        return examiningArticles;
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
        //TODO
        ExaminingArticle examiningArticle2 = new ExaminingArticle();
        examiningArticle2.setComment("稿子很好");
        examiningArticle2.setAttachment("http://localhost/hehe.jpg");
        examiningArticle2.setAttachmentText("附件-2013-01");
        examiningArticle2.setConclusion(1);
        examiningArticle2.setDraftNo("10010");
        examiningArticle2.setExamineStart(new Date());
        examiningArticle2.setExamineFinish(new Date());
        examiningArticle2.setAuthor("王小二");
        examiningArticle2.setId(1);
        examiningArticle2.setOpId(1);
        examiningArticle2.setOpName("鲁连海");
        examiningArticle2.setScore(5);
        examiningArticle2.setState(0);
        examiningArticle2.setStep(EXAM_STEP.WAISHEN.getCode());
        ExaminingArticle examiningArticle = new ExaminingArticle();
        examiningArticle.setComment("稿子很好");
        examiningArticle.setAttachmentText("附件-2013-01");
        examiningArticle.setConclusion(1);
        examiningArticle.setDraftNo("10010");
        examiningArticle.setExamineStart(new Date());
        examiningArticle.setExamineFinish(new Date());
        examiningArticle.setAuthor("王小二");
        examiningArticle.setId(1);
        examiningArticle.setOpId(1);
        examiningArticle.setOpName("鲁连海");
        examiningArticle.setScore(5);
        examiningArticle.setState(1);
        examiningArticle.setStep(EXAM_STEP.TUIXIU.getCode());
        List<ExaminingArticle> examiningArticles = new ArrayList<ExaminingArticle>(5);
        examiningArticles.add(examiningArticle2);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        return examiningArticles;
    }

    /**
     * 获取某篇文章
     *
     * @param articleId
     * @return
     */
    public static ExaminingArticle loadArticle(int articleId) {
        //TODO
        ExaminingArticle examiningArticle = new ExaminingArticle();
        examiningArticle.setCategory(EXAM_STEP.SHOUGAO.getCode());
        examiningArticle.setComment("稿子很好");
        examiningArticle.setConclusion(1);
        examiningArticle.setSummary("这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要<p/>这是摘要这是摘要这这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要");
        examiningArticle.setDraftNo("10010");
        examiningArticle.setExamineFinish(new Date());
        examiningArticle.setExamineFinish(new Date());
        examiningArticle.setAuthor("王小二");
        examiningArticle.setId(1);
        examiningArticle.setOpId(1);
        examiningArticle.setOpName("鲁连海");
        examiningArticle.setScore(5);
        examiningArticle.setState(1);
        examiningArticle.setStep(1);
        examiningArticle.setTitle("文章标题");
        return examiningArticle;
    }
}

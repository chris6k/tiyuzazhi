package com.tiyuzazhi.api;

import android.util.Log;
import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.beans.Magazine;
import com.tiyuzazhi.beans.Notice;
import com.tiyuzazhi.enums.EXAM_STEP;
import com.tiyuzazhi.enums.Status;
import com.tiyuzazhi.enums.Step;
import com.tiyuzazhi.utils.TiHttp;
import com.tiyuzazhi.utils.ToastUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
        try {
            //TODO
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ExaminingArticle examiningArticle = new ExaminingArticle();
            examiningArticle.setCategory(EXAM_STEP.SHOUGAO.getCode());
            examiningArticle.setComment("");
            examiningArticle.setConclusion(1);
            examiningArticle.setDraftNo("2014-10-0014");
            examiningArticle.setExamineStart(simpleDateFormat.parse("2014-10-11"));
            examiningArticle.setExamineEnd(simpleDateFormat.parse("2014-10-21"));
            examiningArticle.setAuthor("鲁连海");
            examiningArticle.setId(1);
            examiningArticle.setOpId(1);
            examiningArticle.setOpName("鲁连海");
            examiningArticle.setScore(5);
            examiningArticle.setState(1);
            examiningArticle.setStep(1);
            examiningArticle.setTitle("基于文化空间理论的民族传统体育保护研究——来自土家摆手舞的田野释义与演证");
            examiningArticle.setSummary("保护民族传统体育文化事象要求整体性保护其相关依存物，而通过“文化空间”的建构与保护是实现这一本质要求的根本方法。研究表明：民族传统体育文化事象“文化空间”包括理念变量、组织变量和物态变量，保护民族传统体育文化事象必须同时保护这三个变量，保护住了“文化空间”就整体性地保护好了文化事象及相关依存物。在保护中：（1）理念变量是核心，在保持传统核心理念不变的情况下进行适应性丰富和发展。（2）组织变量处于中间层面，一是要建构和回归族民的主流地位和主流群体；二是要保持族内传承的民族性、传统性，保护传承人；三是要保护传统体育活动方式和活动场所的传统性、民族性和原生性；四是科学保留优秀民族文化、传统文化；五是提高当地自然农耕经济水平，保持农耕生产方式。（3）物态变量处于外显层面，要求保护动作内容及表现形式的原始性、原生性；保护自然生态环境，以及民族性、传统性建筑风貌及物质媒介。");


            ExaminingArticle examiningArticle2 = new ExaminingArticle();
            examiningArticle2.setCategory(EXAM_STEP.SHOUGAO.getCode());
            examiningArticle2.setComment("稿子很好");
            examiningArticle2.setConclusion(1);
            examiningArticle2.setDraftNo("2014-09-0168");
            examiningArticle2.setExamineStart(simpleDateFormat.parse("2014-09-29"));
            examiningArticle2.setExamineEnd(simpleDateFormat.parse("2014-10-09"));
            examiningArticle2.setId(1);
            examiningArticle2.setOpId(1);
            examiningArticle2.setOpName("鲁连海");
            examiningArticle2.setScore(5);
            examiningArticle2.setState(1);
            examiningArticle2.setStep(1);
            examiningArticle2.setTitle("福建村庙祭祀仪式中的民俗体育研究——以武夷山枫坡村奶娘庙“拔烛桥”为个案");
            examiningArticle2.setAuthor("鲁连海");
            examiningArticle2.setSummary("采用文献资料、田野调查和深度访谈等研究方法，对武夷山市枫坡村奶娘庙祭祀仪式中的“拔烛桥”活动的起源、发展现状、文化功能和传承载体进行分析。研究结果表明：民俗体育是福建村庙祭祀的重要内容，是维系、强化和巩固福建村庙信仰的重要手段；福建村庙祭祀仪式中的民俗体育在现代社会的复兴，主要依靠其所承载的文化功能能够满足村民的文化需求，以及福建发达的村庙文化为其传承提供了载体。");

            ExaminingArticle examiningArticle3 = new ExaminingArticle();
            examiningArticle3.setCategory(EXAM_STEP.SHOUGAO.getCode());
            examiningArticle3.setComment("稿子很好");
            examiningArticle3.setConclusion(1);
            examiningArticle3.setDraftNo("2014-08-0098");
            examiningArticle3.setExamineStart(simpleDateFormat.parse("2014-09-24"));
            examiningArticle3.setExamineEnd(simpleDateFormat.parse("2014-10-04"));
            examiningArticle3.setExamineFinish(simpleDateFormat.parse("2014-10-08"));
            examiningArticle3.setId(1);
            examiningArticle3.setOpId(1);
            examiningArticle3.setOpName("鲁连海");
            examiningArticle3.setScore(5);
            examiningArticle3.setState(1);
            examiningArticle3.setStep(1);
            examiningArticle3.setTitle("体育人类学的身体动作分析法");
            examiningArticle3.setAuthor("鲁连海");
            examiningArticle3.setSummary("身体动作是体育的基本构成单元，也是体育人类学的首要研究对象。身体动作是一种文化符号，体育本身是一个承载和表述社会文化意义的象征系统，一项民族传统体育，就是一本以身体动作书写的民族精神文化史。中国体育人类学在对中华民族传统体育的长期田野调查和多次研究实践中，逐渐摸索和提炼出了以民族传统体育的身体动作为主要分析对象的身体动作分析法，它致力于研究体育身体动作的文化意义，实施步骤包括：（1）拍摄真实完整的身体动作影像；（2）典型动作的参与式分析；（3）对资料的比较分析和理论构建。身体动作分析法可以支持书写深入到身体动作的完整的民族体育志，也可通过研究东巴跳和东巴文形成的关系来探索身体动作对原始文字形成的影响。推而广之，还可用于研究身体动作与其他文化要素（原始宗教、原始艺术等）的互动关系，从而深化对民族传统体育在民族文化发生和发展过程中的特殊地位和独特作用的认识。体育人类学身体动作分析法对构建中国体育人类学的主体性具有重要意义。");


            ExaminingArticle examiningArticle4 = new ExaminingArticle();
            examiningArticle4.setCategory(EXAM_STEP.SHOUGAO.getCode());
            examiningArticle4.setComment("稿子很好");
            examiningArticle4.setConclusion(1);
            examiningArticle4.setDraftNo("2014-09-0097");
            examiningArticle4.setExamineStart(simpleDateFormat.parse("2014-09-23"));
            examiningArticle4.setExamineEnd(simpleDateFormat.parse("2014-10-03"));
            examiningArticle4.setExamineFinish(simpleDateFormat.parse("2014-10-08"));
            examiningArticle4.setId(1);
            examiningArticle4.setOpId(1);
            examiningArticle4.setOpName("鲁连海");
            examiningArticle4.setScore(5);
            examiningArticle4.setState(1);
            examiningArticle4.setStep(1);
            examiningArticle4.setTitle("民族传统体育的走向");
            examiningArticle4.setAuthor("鲁连海");
            examiningArticle4.setSummary("源于西方文化的体育运动风靡全球，已是一个基本事实。不过，对于体育的研究，东方学者应该扬长避短，运用科学的方法深入挖掘与改进养生保健文化，利用西方体育成熟之前在东方已存续千年的独特资源，借助东南亚各民族携手崛起的后发优势，构建新的体育发展之路。");
            List<ExaminingArticle> examiningArticles = new ArrayList<ExaminingArticle>(4);
            examiningArticles.add(examiningArticle);
            examiningArticles.add(examiningArticle2);
            examiningArticles.add(examiningArticle3);
            examiningArticles.add(examiningArticle4);

            return examiningArticles;
        } catch (Exception e) {
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
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ExaminingArticle examiningArticle = new ExaminingArticle();
            examiningArticle.setCategory(EXAM_STEP.SHOUGAO.getCode());
            examiningArticle.setComment("该文对女性主义范式从认识论、方法论和理论等几个层面进行了清晰的阐释，对于我国女性体育的研究有很好的参考价值和引导价值。如果作者能在几个不同时期、不同流派的分析中，列出一些代表性的作品就更有说服力和科学性。\n");
            examiningArticle.setConclusion(1);
            examiningArticle.setSummary("这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要<p/>这是摘要这是摘要这这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要");
            examiningArticle.setDraftNo("2014-01-0024");
            examiningArticle.setExamineFinish(simpleDateFormat.parse("2014-01-03"));
            examiningArticle.setAuthor("王小二");
            examiningArticle.setId(1);
            examiningArticle.setOpId(1);
            examiningArticle.setOpName("评审专家");
            examiningArticle.setScore(5);
            examiningArticle.setState(Status.DONE.getCode());
            examiningArticle.setStep(Step.RETRY_OK.getCode());
            examiningArticle.setTitle("体育背景下性别的理论化——论女性主义范式对女性体育研究的建构");
            return examiningArticle;
        } catch (Exception e) {
            Log.e("TAG", "load article error", e);
            return null;
        }
    }
}

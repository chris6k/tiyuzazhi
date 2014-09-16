package com.tiyuzazhi.api;

import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.beans.Magazine;
import com.tiyuzazhi.enums.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        examiningArticle.setCategory(Category.SHOUGAO.getCode());
        examiningArticle.setComment("稿子很好");
        examiningArticle.setConclusion(1);
        examiningArticle.setDraftNo("No:10010");
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
        //TODO
        Magazine magazine = new Magazine();
        magazine.setId(1);
        magazine.setPublishNo(1);
        magazine.setPublishTime(new Date());
        magazine.setSubTitle("体育杂志副标题");
        magazine.setTitle("体育杂志主标题");
        Magazine magazine2 = new Magazine();
        magazine2.setId(2);
        magazine2.setPublishNo(2);
        magazine2.setPublishTime(new Date());
        magazine2.setSubTitle("体育杂志副标题2");
        magazine2.setTitle("体育杂志主标题2");
        return Arrays.asList(magazine, magazine2);
    }

    /**
     * load杂志的文章目录内容
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static List<ArticleMenu> loadArticleMenu(int magazineId) {
        //TODO
        ArticleMenu magazine = new ArticleMenu();
        magazine.setId(1);
        magazine.setAuthor("鲁连海");
        magazine.setTitle("体育杂志主标题" + magazineId);
        ArticleMenu magazine2 = new ArticleMenu();
        magazine2.setId(2);
        magazine2.setAuthor("鲁连海");
        magazine2.setTitle("体育杂志主标题2" + magazineId);
        return Arrays.asList(magazine, magazine2);
    }

    /**
     * load next magazine
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static Magazine loadNextMagazine(int magazineId) {
        //TODO
        Magazine magazine = new Magazine();
        magazine.setId(magazineId + 1);
        magazine.setPublishNo(magazineId + 1);
        magazine.setPublishTime(new Date());
        magazine.setSubTitle("体育杂志副标题" + (magazineId + 1));
        magazine.setTitle("体育杂志主标题" + (magazineId + 1));
        return magazine;
    }

    /**
     * load previous magazine
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static Magazine loadPrevMagazine(int magazineId) {
        //TODO
        Magazine magazine = new Magazine();
        magazine.setId(magazineId - 1);
        magazine.setPublishNo(magazineId - 1);
        magazine.setPublishTime(new Date());
        magazine.setSubTitle("体育杂志副标题" + (magazineId - 1));
        magazine.setTitle("体育杂志主标题" + (magazineId - 1));
        return magazine;
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
        ExaminingArticle examiningArticle = new ExaminingArticle();
        examiningArticle.setCategory(Category.SHOUGAO.getCode());
        examiningArticle.setComment("稿子很好");
        examiningArticle.setAttachmentText("附件-2013-01");
        examiningArticle.setConclusion(1);
        examiningArticle.setDraftNo("No:10010");
        examiningArticle.setExamineStart(new Date());
        examiningArticle.setExamineFinish(new Date());
        examiningArticle.setAuthor("王小二");
        examiningArticle.setId(1);
        examiningArticle.setOpId(1);
        examiningArticle.setOpName("鲁连海");
        examiningArticle.setScore(5);
        examiningArticle.setState(0);
        examiningArticle.setStep(5);

        ExaminingArticle examiningArticle2 = new ExaminingArticle();
        examiningArticle2.setCategory(Category.SHOUGAO.getCode());
        examiningArticle2.setComment("稿子很好");
        examiningArticle2.setAttachment("http://localhost/hehe.jpg");
        examiningArticle2.setAttachmentText("附件-2013-01");
        examiningArticle2.setConclusion(1);
        examiningArticle2.setDraftNo("No:10010");
        examiningArticle2.setExamineStart(new Date());
        examiningArticle2.setExamineFinish(new Date());
        examiningArticle2.setAuthor("王小二");
        examiningArticle2.setId(1);
        examiningArticle2.setOpId(1);
        examiningArticle2.setOpName("鲁连海");
        examiningArticle2.setScore(5);
        examiningArticle2.setState(0);
        examiningArticle2.setStep(5);
        List<ExaminingArticle> examiningArticles = new ArrayList<ExaminingArticle>(5);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle);
        examiningArticles.add(examiningArticle2);
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
        examiningArticle.setCategory(Category.SHOUGAO.getCode());
        examiningArticle.setComment("稿子很好");
        examiningArticle.setConclusion(1);
        examiningArticle.setSummary("这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要这是摘要");
        examiningArticle.setDraftNo("No:10010");
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

package com.tiyuzazhi.api;

import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.beans.Magazine;

import java.util.ArrayList;
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
        return new ArrayList<ExaminingArticle>(0);
    }

    /**
     * 获取最新的杂志信息
     *
     * @return
     */
    public static List<Magazine> loadNewestMagazine() {
        //TODO
        return new ArrayList<Magazine>(0);
    }

    /**
     * load杂志的文章目录内容
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static List<ArticleMenu> loadArticleMenu(int magazineId) {
        //TODO
        return new ArrayList<ArticleMenu>(0);
    }

    /**
     * load next magazine
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static Magazine loadNextMagazine(int magazineId) {
        //TODO
        return null;
    }

    /**
     * load previous magazine
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static Magazine loadPrevMagazine(int magazineId) {
        //TODO
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
        return false;
    }

    /**
     * 获取评审流程
     *
     * @param articleId
     * @return
     */
    public static List<ExaminingArticle> loadExamineFlow(int articleId) {
        //TODO
        return new ArrayList<ExaminingArticle>(0);
    }

    /**
     * 获取谋篇文章
     *
     * @param articleId
     * @return
     */
    public static ExaminingArticle loadArticle(int articleId) {
        //TODO
        return null;
    }
}

package com.tiyuzazhi.api;

import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.ExaminingArticle;

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
     * load杂志的文章目录内容
     *
     * @param magazineId 杂志ID
     * @return
     */
    public static List<ArticleMenu> loadArticleMenu(int magazineId) {
        //TODO
        return new ArrayList<ArticleMenu>(0);
    }
}

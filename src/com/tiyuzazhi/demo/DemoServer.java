package com.tiyuzazhi.demo;

import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.Examiner;
import com.tiyuzazhi.beans.Magazine;
import com.tiyuzazhi.beans.User;
import com.tiyuzazhi.enums.ExaminerType;
import com.tiyuzazhi.enums.Role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kun
 */
public class DemoServer {
    private static final User author;
    private static final User master;
    private static final User editor;
    private static final User chiefEditor;

    private static final Examiner examiner1;
    private static final Examiner examiner2;
    private static final Examiner examiner3;
    private static final Examiner examiner4;
    private static final Examiner examiner5;

    private static final Magazine magazine1;
    private static final Magazine magazine2;
    private static final Magazine magazine3;
    private static final Magazine magazine4;

    private static final List<ArticleMenu> mag1MenuList;
    private static final List<ArticleMenu> mag2MenuList;
    private static final List<ArticleMenu> mag3MenuList;
    private static final List<ArticleMenu> mag4MenuList;

    static {
        author = new User();
        author.setEmail("author@tiyuzazhi.com");
        author.setId(1);
        author.setCompany("东华大学出版社");
        author.setName("鲁连海");
        author.setRole(Role.AUTHOR.getCode());
        author.setAddress("上海市二大街");
        author.setMobile("1380000000");
        author.setFavCount(0);
        author.setMsgCount(0);
        author.setIconPath("");

        master = new User();
        master.setEmail("master@tiyuzazhi.com");
        master.setId(2);
        master.setCompany("南师大出版社");
        master.setName("王晓芬");
        master.setRole(Role.MASTER.getCode());
        master.setAddress("南京市中山路");
        master.setMobile("1380000000");
        master.setFavCount(0);
        master.setMsgCount(0);
        master.setIconPath("");

        editor = new User();
        editor.setEmail("author@tiyuzazhi.com");
        editor.setId(3);
        editor.setCompany("南师大出版社");
        editor.setName("陈晓");
        editor.setRole(Role.EDITOR.getCode());
        editor.setAddress("南京市中山路");
        editor.setMobile("1380000000");
        editor.setFavCount(0);
        editor.setMsgCount(0);
        editor.setIconPath("");

        chiefEditor = new User();
        chiefEditor.setEmail("chief@tiyuzazhi.com");
        chiefEditor.setId(4);
        chiefEditor.setCompany("南师大出版社");
        chiefEditor.setName("谷毅丰");
        chiefEditor.setRole(Role.CHIEF_EDITOR.getCode());
        chiefEditor.setAddress("南京市中山路");
        chiefEditor.setMobile("1380000000");
        chiefEditor.setFavCount(0);
        chiefEditor.setMsgCount(0);
        chiefEditor.setIconPath("");

        examiner1 = new Examiner();
        examiner1.setEmail("master@tiyuzazhi.com");
        examiner1.setId(2);
        examiner1.setCompany("南师大出版社");
        examiner1.setName("王晓芬");
        examiner1.setRole(Role.MASTER.getCode());
        examiner1.setAddress("南京市中山路");
        examiner1.setMobile("1380000000");
        examiner1.setFavCount(0);
        examiner1.setMsgCount(0);
        examiner1.setIconPath("");
        examiner1.setStatus(1);
        examiner1.setType(ExaminerType.RECOMM.getCode());

        examiner2 = new Examiner();
        examiner2.setEmail("master@tiyuzazhi.com");
        examiner2.setId(5);
        examiner2.setCompany("华师大出版社");
        examiner2.setName("李大海");
        examiner2.setRole(Role.MASTER.getCode());
        examiner2.setAddress("上海市中山北路100号");
        examiner2.setMobile("1380000000");
        examiner2.setFavCount(0);
        examiner2.setMsgCount(0);
        examiner2.setIconPath("");
        examiner2.setStatus(1);
        examiner2.setType(ExaminerType.RECOMM.getCode());

        examiner3 = new Examiner();
        examiner3.setEmail("master@tiyuzazhi.com");
        examiner3.setId(6);
        examiner3.setCompany("清华大学出版社");
        examiner3.setName("汪斌");
        examiner3.setRole(Role.MASTER.getCode());
        examiner3.setAddress("北京市西长安街120号");
        examiner3.setMobile("1380000000");
        examiner3.setFavCount(0);
        examiner3.setMsgCount(0);
        examiner3.setIconPath("");
        examiner3.setStatus(1);
        examiner3.setType(ExaminerType.EDU.getCode());

        examiner4 = new Examiner();
        examiner4.setEmail("master@tiyuzazhi.com");
        examiner4.setId(7);
        examiner4.setCompany("华师大出版社");
        examiner4.setName("向真");
        examiner4.setRole(Role.MASTER.getCode());
        examiner4.setAddress("上海市中山北路100号");
        examiner4.setMobile("1380000000");
        examiner4.setFavCount(0);
        examiner4.setMsgCount(0);
        examiner4.setIconPath("");
        examiner4.setStatus(1);
        examiner4.setType(ExaminerType.EDU.getCode());

        examiner5 = new Examiner();
        examiner5.setEmail("master@tiyuzazhi.com");
        examiner5.setId(8);
        examiner5.setCompany("电子工业出版社");
        examiner5.setName("包亦樊");
        examiner5.setRole(Role.MASTER.getCode());
        examiner5.setAddress("合肥市人民路123号");
        examiner5.setMobile("1380000000");
        examiner5.setFavCount(0);
        examiner5.setMsgCount(0);
        examiner5.setIconPath("");
        examiner5.setStatus(1);
        examiner5.setType(ExaminerType.PSY.getCode());

        magazine1 = new Magazine();
        magazine1.setId(1);
        magazine1.setPublishNo(1);
        magazine1.setPublishTime(new Date());
        magazine1.setSubTitle("CHINA SPORT SCIENCE AND TECHNOLOGY");
        magazine1.setTitle("中国体育科技");

        magazine2 = new Magazine();
        magazine2.setId(2);
        magazine2.setPublishNo(2);
        magazine2.setPublishTime(new Date());
        magazine2.setSubTitle("CHINA SPORT SCIENCE AND TECHNOLOGY");
        magazine2.setTitle("中国体育科技");

        magazine3 = new Magazine();
        magazine3.setId(3);
        magazine3.setPublishNo(10);
        magazine3.setPublishTime(new Date());
        magazine3.setSubTitle("CHINA SPORT SCIENCE");
        magazine3.setTitle("体育科学");

        magazine4 = new Magazine();
        magazine4.setId(4);
        magazine4.setPublishNo(11);
        magazine4.setPublishTime(new Date());
        magazine4.setSubTitle("CHINA SPORT SCIENCE");
        magazine4.setTitle("体育科学");

        mag1MenuList = new ArrayList<ArticleMenu>(5);
        mag2MenuList = new ArrayList<ArticleMenu>(5);
        mag3MenuList = new ArrayList<ArticleMenu>(5);
        mag4MenuList = new ArrayList<ArticleMenu>(5);

        ArticleMenu menu1 = new ArticleMenu();
        menu1.setId(1);
        menu1.setAuthor("鲁连海");
        menu1.setTitle("消化系统与运动健康(1)");
        mag1MenuList.add(menu1);

        ArticleMenu menu2 = new ArticleMenu();
        menu2.setId(2);
        menu2.setAuthor("王政");
        menu2.setTitle("运动护理与体育保健对学生运动的指导");

        ArticleMenu menu3 = new ArticleMenu();
        menu3.setId(3);
        menu3.setAuthor("小柯");
        menu3.setTitle("体育护具的科学使用与利用");

        ArticleMenu menu4 = new ArticleMenu();
        menu4.setId(4);
        menu4.setAuthor("鲁连海");
        menu4.setTitle("消化系统与运动健康(2)");

        ArticleMenu menu5 = new ArticleMenu();
        menu5.setId(5);
        menu5.setAuthor("鲁连海");
        menu5.setTitle("消化系统与运动健康(3)");

    }

}

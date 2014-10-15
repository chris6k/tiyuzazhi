package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

/**
 * @author kun
 */
public class TGYQActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.tgxz);
        webView = (WebView) findViewById(R.id.summary);
        String data = "<html><head><style type='text/css'>*{font-size:16px;color:#4a5153;line-height:150%;text-indent:2em;}</style></head><body>" + "<div id=\"content\" class=\"cn summary\"><!-- 简介 -->\n" +
                "<span style=\"font-size:x-small;\"><strong><span style=\"font-size:medium;\">《体育科学》、《中国体育科技》杂志</span>&nbsp;</strong></span><span style=\"font-size:small;\">热诚欢迎海内外作者投寄稿件。为保证学术研究成果的原创性和严谨性，倡导良好的学术风气，推进学术规范建设，请作者投稿时遵照如下规定： </span>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;&nbsp;&nbsp;&nbsp;1. 所投稿件须系作者（或课题组）独立研究完成，对他人知识产权有充分尊重，无任何违法、违纪和违反学术道德的内容。按学术研究规范认真核对引文、注释和文中使用的其他资料，确保引文、注释和相关资料准确无误。如使用转引资料，应实事求是注明转引出处。文稿不得包含任何与中华人民共和国现行法律相抵触的内容；稿件的内容是真实的。</span></p>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;<strong>&nbsp;&nbsp; 2. 注册投稿人需为本文第一作者，其他作者注册投稿无效。文章有关事宜编辑部只与第一作者联系。本刊第一作者即为通讯作者。所有作者按系统要求添加，一经入库，作者及排序按投稿时添加的为准。</strong></span></p>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;&nbsp;&nbsp; 3. 凡向本部投稿，承诺该文未一稿两投或多投，包括未局部改动后投寄其他报刊，并保证不会将该文主要观点或基本内容先于《体育科学》和《中国体育科技》在其他出版物上发表。</span></p>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;&nbsp;&nbsp; 4. 所投稿件应遵守相关的国家标准和法规，如关于标点符号和数字使用的规范等。论文如果获得有关研究基金或课题资助，需提供基金名称及编号，并上传相关文件。学位论文请在论文整体框架下进行适当精简，所刊论文原则上与学位论文文题保持一致，本刊原则上不刊发学位论文节选内容。</span></p>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;&nbsp;&nbsp; 5. 文稿题目应简洁、准确，不宜使用缩略词；摘要内容对论文研究背景、研究内容、研究成果及相关数据等应有所介绍，不要过于简单；注意容易混淆的外文字母的文种、大小写、正斜体及上下角标的正确书写；图、表和公式应通篇分别编号，表格应采用三线表形式。</span></p>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;&nbsp;&nbsp;&nbsp; 6. 《体育科学》和《中国体育科技》杂志整体版权属国家体育总局体育科学研究所科技书刊部所有，未经许可，不得以任何方式复制、选编。经我部许可需在其他出版物上发表或转载的，须特别注明“本文首发于《体育科学》（或《中国体育科技》）”。</span></p>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;&nbsp;&nbsp;&nbsp; 7. &nbsp;本编辑部有权对来稿做文字性修改，如作者因违背上述规定而给《体育科学》和《中国体育科技》造成不良影响，作者将承担全部责任，接受编辑部采取相应措施予以警示，包括5年内禁止作者本人的任何成果在《体育科学》、《中国体育科技》上发表等。</span></p>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;&nbsp;<strong> &nbsp;&nbsp;8.&nbsp;文稿一经采用，编辑部将告之作者（多作者文稿则告之第一作者），经作者（第一作者）同意等同于同意在《体育科学》和《中国体育科技》的网站上或由其授权的文献数据库全文刊载其成果，并付稿酬及寄样刊。</strong></span></p>\n" +
                "<p><span style=\"font-size:small;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9. &nbsp;本刊实施专职编辑与相关学科专家匿名审稿相结合的审稿制度，请在注册、提交稿件时按要求填写作者信息，<strong>提供的文稿中不要出现作者姓名及其他有关作者的信息</strong>。&nbsp;&nbsp; </span></p>\n" +
                "</div>" + "</div><div style='clear:both;'></div></body></html>";
        webView.loadDataWithBaseURL("", data, "text/html", "utf-8", "");
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

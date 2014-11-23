package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.Notice;

/**
 * @author chris.xue
 *         文章摘要
 */
public class NoticeSummaryActivity extends Activity {

    private TextView title;
    private TextView author;
    private WebView summary;
    private View buttonOk;
    private View buttonReject;
    private Notice notice;
    private View footerWhite;
    private View pdfdownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.article_summary);
        super.onCreate(savedInstanceState);
        notice = (Notice) getIntent().getSerializableExtra("notice");
        TextView headerTitle = (TextView) findViewById(R.id.headTitle);
        headerTitle.setText("通告");
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.GONE);
        author = (TextView) findViewById(R.id.author);
        author.setVisibility(View.GONE);
        summary = (WebView) findViewById(R.id.summary);
        View draftNo = findViewById(R.id.draftNoText);
        draftNo.setVisibility(View.GONE);
        buttonOk = findViewById(R.id.buttonOkText);
        buttonReject = findViewById(R.id.buttonRejectText);
        buttonOk.setVisibility(View.GONE);
        buttonReject.setVisibility(View.GONE);
        footerWhite = findViewById(R.id.footerBar);
        footerWhite.setVisibility(View.GONE);
        pdfdownload = findViewById(R.id.pdfdownload);
        pdfdownload.setVisibility(View.GONE);
        View line = findViewById(R.id.line);
        line.setVisibility(View.GONE);
        init();
    }

    private void init() {
//        title.setText(notice.getTitle());
//        author.setText(notice.getAuthor());
        summary.loadDataWithBaseURL("", "<html><head><style type='text/css'>*{font-size:16px;color:#4a5153;line-height:150%;}</style></head><body>" +
                "<div style='width:98%;'>"
                + notice.getSummary() + "</div><div style='clear:both;'></div></body></html>", "text/html", "utf-8", null);

    }
}

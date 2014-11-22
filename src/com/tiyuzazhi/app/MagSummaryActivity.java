package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.tiyuzazhi.beans.ArticleMenu;

/**
 * @author chris.xue
 *         文章摘要
 */
public class MagSummaryActivity extends Activity {

    private TextView title;
    private TextView author;
    private WebView summary;
    private View buttonOk;
    private View buttonReject;
    private ArticleMenu menu;
    private View footerWhite;
    private View pdfdownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.article_summary);
        super.onCreate(savedInstanceState);
        menu = (ArticleMenu) getIntent().getSerializableExtra("menu");
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        summary = (WebView) findViewById(R.id.summary);
        buttonOk = findViewById(R.id.buttonOkText);
        buttonReject = findViewById(R.id.buttonRejectText);
        buttonOk.setVisibility(View.GONE);
        buttonReject.setVisibility(View.GONE);
        footerWhite = findViewById(R.id.footerBar);
        footerWhite.setVisibility(View.GONE);
        pdfdownload = findViewById(R.id.pdfdownload);
        pdfdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(menu.getAttachment()));
                startActivity(intent);
            }
        });
        init();
    }

    private void init() {
        title.setText(menu.getTitle());
        author.setText(menu.getAuthor());
        summary.loadDataWithBaseURL("", "<html><head><style type='text/css'>*{font-size:16px;color:#4a5153;line-height:150%;text-indent:2em;}</style></head><body>" +
                "<div style='width:98%;'><span style='color:#00367e;margin-left:-2em;'>[摘要]</span>&nbsp;&nbsp;"
                + menu.getSummary() + "</div><div style='clear:both;'></div></body></html>", "text/html", "utf-8", null);

    }
}

package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chris.xue
 *         文章摘要
 */
public class ArticleSummaryActivity extends Activity {

    private AtomicBoolean opLock;
    private Handler handler;
    private TextView title;
    private TextView author;
    private WebView summary;
    private int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.article_summary);
        super.onCreate(savedInstanceState);
        articleId = getIntent().getIntExtra("articleId", 0);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        opLock = new AtomicBoolean(false);
        handler = new Handler(Looper.getMainLooper());
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        summary = (WebView) findViewById(R.id.summary);
        View buttonOk = findViewById(R.id.buttonOkText);
        View buttonReject = findViewById(R.id.buttonRejectText);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opLock.compareAndSet(false, true)) {
                    TPool.post(new Runnable() {
                                   @Override
                                   public void run() {
                                       try {
                                           boolean success = ArticleApi.passExamine(articleId);
                                           if (success) {
                                               ToastUtils.show("操作成功");
                                           }
                                       } finally {
                                           opLock.set(false);
                                       }
                                   }
                               }

                    );
                } else {
                    ToastUtils.show("前一个操作正在处理中，请稍后再试");
                }
            }
        });
        buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opLock.compareAndSet(false, true)) {
                    TPool.post(new Runnable() {
                                   @Override
                                   public void run() {
                                       try {
                                           boolean success = ArticleApi.rejectExamine(articleId);
                                           if (success) {
                                               ToastUtils.show("操作成功");
                                           }
                                       } finally {
                                           opLock.set(false);
                                       }
                                   }
                               }
                    );
                } else {
                    ToastUtils.show("前一个操作正在处理中，请稍后再试");
                }
            }
        });
        init();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                final ExaminingArticle examiningArticle = ArticleApi.loadArticle(articleId);
                if (examiningArticle != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            title.setText(examiningArticle.getTitle());
                            author.setText(examiningArticle.getAuthor());
                            summary.loadDataWithBaseURL("", "<html><head><style type='text/css'>*{font-size:16px;color:#4a5153;line-height:150%;text-indent:2em;}</style></head><body>" +
                                    "<span style='color:#00367e;float:left;margin-left:-2em;'>[摘要]</span>"
                                    + examiningArticle.getSummary() + "</body></html>", "text/html", "utf-8", null);
                        }
                    });
                } else {
                    ToastUtils.show("未能获取到文章，请稍后再试");
                }
            }
        });
    }
}

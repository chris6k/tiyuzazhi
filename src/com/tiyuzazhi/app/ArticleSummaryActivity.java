package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.component.PassDialog;
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
    private View buttonOk;
    private View buttonReject;

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
        buttonOk = findViewById(R.id.buttonOkText);
        buttonReject = findViewById(R.id.buttonRejectText);

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
                                    "<div style='width:98%;'><span style='color:#00367e;margin-left:-2em;'>[摘要]</span>&nbsp;&nbsp;"
                                    + examiningArticle.getSummary() + "</div><div style='clear:both;'></div></body></html>", "text/html", "utf-8", null);
                        }
                    });

                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final PassDialog passDialog = new PassDialog(ArticleSummaryActivity.this, R.style.my_dialog) {
                                @Override
                                public void onButtonClick(final String comment) {
                                    if (opLock.compareAndSet(false, true)) {
                                        TPool.post(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           try {
                                                               examiningArticle.setComment(comment);
                                                               if (ArticleApi.passExamine(examiningArticle)) {
                                                                   ToastUtils.show("操作成功");
                                                                   dismiss();
                                                                   init();
                                                               } else {
                                                                   ToastUtils.show("操作失败");
                                                               }
                                                           } finally {
                                                               opLock.set(false);
                                                           }
                                                       }
                                                   }
                                        );
                                    } else {
                                        ToastUtils.show("前一个操作正在进行，请稍后再试");
                                    }
                                }
                            };
                            passDialog.setText("审核通过");
                            passDialog.setButtonText("通过");
                            passDialog.show();
                            WindowManager windowManager = getWindowManager();
                            Display display = windowManager.getDefaultDisplay();
                            WindowManager.LayoutParams lp = passDialog.getWindow().getAttributes();
                            lp.width = (int) (display.getWidth()); //设置宽度
                            passDialog.getWindow().setAttributes(lp);
                        }
                    });
                    buttonReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final PassDialog passDialog = new PassDialog(ArticleSummaryActivity.this, R.style.my_dialog) {
                                @Override
                                public void onButtonClick(final String comment) {
                                    if (opLock.compareAndSet(false, true)) {
                                        TPool.post(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           try {
                                                               examiningArticle.setComment(comment);
                                                               if (ArticleApi.rejectExamine(examiningArticle)) {
                                                                   ToastUtils.show("操作成功");
                                                                   dismiss();
                                                                   init();
                                                               } else {
                                                                   ToastUtils.show("操作失败");
                                                               }
                                                           } finally {
                                                               opLock.set(false);
                                                           }
                                                       }
                                                   }
                                        );
                                    } else {
                                        ToastUtils.show("前一个操作正在进行，请稍后再试");
                                    }
                                }
                            };
                            passDialog.setText("审核不通过");
                            passDialog.setButtonText("不通过");
                            passDialog.show();
                            WindowManager windowManager = getWindowManager();
                            Display display = windowManager.getDefaultDisplay();
                            WindowManager.LayoutParams lp = passDialog.getWindow().getAttributes();
                            lp.width = (int) (display.getWidth()); //设置宽度
                            passDialog.getWindow().setAttributes(lp);
                        }
                    });
                } else {
                    ToastUtils.show("未能获取到文章，请稍后再试");
                }
            }
        });
    }
}

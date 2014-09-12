package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 */
public class CommentCenterActivity extends Activity {

    private AtomicBoolean opLock;
    private Handler handler;
    private TextView opName;
    private TextView comment;
    private int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.author_center);
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
        opName = (TextView) findViewById(R.id.opName);
        comment = (TextView) findViewById(R.id.comment);
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
                            opName.setText(examiningArticle.getOpName() + ":");
                            comment.setText(Html.fromHtml(examiningArticle.getComment()));
                        }
                    });
                } else {
                    ToastUtils.show("未能获取到文章，请稍后再试");
                }
            }
        });
    }

}

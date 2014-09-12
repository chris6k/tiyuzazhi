package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.utils.DatetimeUtils;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chris.xue
 *         作者中心
 */
public class AuthorCenterActivity extends Activity {

    private AtomicBoolean opLock;
    private Handler handler;
    private TextView title;
    private TextView draftNo;
    private TextView dateDay;
    private TextView opName;
    private TextView comment;
    private View attached;
    private TextView state;
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
        title = (TextView) findViewById(R.id.title);
        draftNo = (TextView) findViewById(R.id.draftNo);
        dateDay = (TextView) findViewById(R.id.dateDay);
        opName = (TextView) findViewById(R.id.opName);
        comment = (TextView) findViewById(R.id.comment);
        state = (TextView) findViewById(R.id.state);
        attached = findViewById(R.id.attached);
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
                            opName.setText(examiningArticle.getOpName() + ":");
                            draftNo.setText(examiningArticle.getDraftNo());
                            dateDay.setText(DatetimeUtils.format(examiningArticle.getExamineFinish()));
                            state.setText(examiningArticle.getConclusion());
                            if (TextUtils.isEmpty(examiningArticle.getAttachment())) {
                                attached.setVisibility(View.GONE);
                                comment.setText(Html.fromHtml(examiningArticle.getComment()));
                            } else {
                                attached.setVisibility(View.VISIBLE);
                                comment.setVisibility(View.GONE);
                                attached.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //TODO 附件处理
                                    }
                                });
                            }

                        }
                    });
                } else {
                    ToastUtils.show("未能获取到文章，请稍后再试");
                }
            }
        });
    }
}

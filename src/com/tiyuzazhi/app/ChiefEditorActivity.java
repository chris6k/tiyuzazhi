package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.utils.DatetimeUtils;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chris.xue
 *         主编办公
 */
public class ChiefEditorActivity extends Activity {
    private DecimalFormat format = new DecimalFormat("0.0");
    private ListView titleBar;
    private AtomicBoolean opLock;
    private Handler handler;
    private volatile List<ExaminingArticle> examiningArticles;
    private volatile int offset;
    private volatile int step;
    private volatile boolean isAsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.chief_editor_list_layout);
        offset = 0;
        step = 0;
        isAsc = true;
        super.onCreate(savedInstanceState);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleBar = (ListView) findViewById(R.id.article_item_list);
        opLock = new AtomicBoolean(false);
        handler = new Handler(Looper.getMainLooper());
        init();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                try {
                    examiningArticles = ArticleApi.loadExamineArticle(offset, 10, step, isAsc);
                    if (examiningArticles.isEmpty()) {
                        ToastUtils.show("没有更多文章");
                        return;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            titleBar.setAdapter(new ArticleAdaptor(examiningArticles));
                        }
                    });
                } catch (Exception e) {
                    ToastUtils.show("未能获取到数据，请稍后再试");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class ArticleAdaptor extends BaseAdapter {
        private List<ExaminingArticle> articleList;

        public ArticleAdaptor(List<ExaminingArticle> articleList) {
            this.articleList = articleList;
        }

        @Override
        public int getCount() {
            return articleList.size();
        }

        @Override
        public Object getItem(int i) {
            return articleList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return articleList.get(i).getId();
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final AdaptorHelper helper;
            if (view == null || view.getTag() == null) {
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.chief_editor_list_item, null, false);
                helper = new AdaptorHelper();
                helper.title = (TextView) view.findViewById(R.id.title);
                helper.draftNo = (TextView) view.findViewById(R.id.draftNo);
                helper.dateDay = (TextView) view.findViewById(R.id.dateDay);
                helper.leftDay = (TextView) view.findViewById(R.id.leftDay);
                helper.opName = (TextView) view.findViewById(R.id.opName);
                helper.flow = (TextView) view.findViewById(R.id.opName2);
                helper.plan = (TextView) view.findViewById(R.id.plan);
                helper.conclusion = (TextView) view.findViewById(R.id.conclusion);
                helper.score = (TextView) view.findViewById(R.id.score);
                helper.comment = (EditText) view.findViewById(R.id.commentEditText);
                helper.ok = (Button) view.findViewById(R.id.buttonOkText);
                helper.reject = (Button) view.findViewById(R.id.buttonRejectText);
                helper.forward = (Button) view.findViewById(R.id.buttonForward);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final ExaminingArticle article = (ExaminingArticle) getItem(i);
            helper.title.setText(article.getTitle());
            helper.title.setClickable(true);
            helper.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ChiefEditorActivity.this, ExamSummaryActivity.class);
                    intent.putExtra("article", article);
                    startActivity(intent);
                }
            });
            helper.draftNo.setText("编号:" + article.getDraftNo());
            helper.dateDay.setText(DatetimeUtils.format(article.getExamineStart()));
            helper.leftDay.setText(DatetimeUtils.getDuringDay(article.getExamineStart(), new Date()) + "天");
            helper.plan.setText("计划 " + DatetimeUtils.format(article.getExamineEnd()));
            helper.opName.setText("处理人:" + article.getOpName());
            helper.flow.setText(
                    article.getPrevOpName() + "(" +
                            DatetimeUtils.format(article.getPrevExamineFinish()) +
                            ") " + article.getOpName() +
                            (article.getExamineFinish() == null ? "" : ("(" + article.getExamineFinish() + ")")));
            helper.score.setText("审稿得分:" + format.format(Double.valueOf(article.getScore())));
            helper.conclusion.setText("");
            helper.ok.setEnabled(false);
            helper.reject.setEnabled(false);
            helper.forward.setEnabled(false);
//            helper.ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    article.setComment(helper.comment.getText().toString());
//                    if (opLock.compareAndSet(false, true)) {
//                        TPool.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    if (ArticleApi.passExamine(article)) {
//                                        ToastUtils.show("操作成功");
//                                        init();
//                                    } else {
//                                        ToastUtils.show("操作失败");
//                                    }
//                                } finally {
//                                    opLock.set(false);
//                                }
//                            }
//                        });
//                    } else {
//                        ToastUtils.show("前一个操作正在进行，请稍后再试");
//                    }
//                }
//            });
//            helper.reject.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    article.setComment(helper.comment.getText().toString());
//                    if (opLock.compareAndSet(false, true)) {
//                        TPool.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    if (ArticleApi.rejectExamine(article)) {
//                                        ToastUtils.show("操作成功");
//                                        init();
//                                    } else {
//                                        ToastUtils.show("操作失败");
//                                    }
//                                } finally {
//                                    opLock.set(false);
//                                }
//                            }
//                        });
//                    } else {
//                        ToastUtils.show("前一个操作正在进行，请稍后再试");
//                    }
//                }
//            });
//            helper.forward.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(ChiefEditorActivity.this, ExaminerActivity.class);
//                    startActivityForResult(intent, i);
//                }
//            });
            return view;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final ArrayList<Integer> examinerIds = data.getIntegerArrayListExtra("examiners");
            if (examinerIds != null) {
                final ExaminingArticle examiningArticle = examiningArticles.get(requestCode);
                if (opLock.compareAndSet(false, true)) {
                    TPool.post(new Runnable() {
                        @Override
                        public void run() {
                            boolean success = ArticleApi.forward(examiningArticle, examinerIds);
                            if (success) {
                                ToastUtils.show("操作成功");
                                init();
                            } else {
                                ToastUtils.show("操作失败");
                            }
                        }
                    });
                } else {
                    ToastUtils.show("前一个操作正在进行，请稍后再试");
                }
            }
        }
    }

    private class AdaptorHelper {
        private TextView title;
        private TextView draftNo;
        private TextView dateDay;
        private TextView leftDay;
        private TextView plan;
        private TextView opName;
        private TextView flow;
        private TextView score;
        private TextView conclusion;
        private EditText comment;
        private Button ok;
        private Button reject;
        private Button forward;
    }
}

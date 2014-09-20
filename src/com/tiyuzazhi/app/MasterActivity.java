package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.component.PassDialog;
import com.tiyuzazhi.utils.DatetimeUtils;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chris.xue
 *         专家审稿
 */
public class MasterActivity extends Activity {
    private ListView articleListView;
    private AtomicBoolean opLock;
    private Handler handler;
    private volatile List<ExaminingArticle> examiningArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.master_list_layout);
        super.onCreate(savedInstanceState);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        articleListView = (ListView) findViewById(R.id.article_item_list);
        opLock = new AtomicBoolean(false);
        handler = new Handler(Looper.getMainLooper());
        init();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                try {
                    examiningArticles = ArticleApi.loadExamineArticle(0, 10);
                    if (examiningArticles.isEmpty()) {
                        ToastUtils.show("没有更多文章");
                        return;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            articleListView.setAdapter(new ArticleAdaptor(examiningArticles));
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            AdaptorHelper helper;
            if (view == null || view.getTag() == null) {
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.master_list_item, null, false);
                helper = new AdaptorHelper();
                helper.title = (TextView) view.findViewById(R.id.title);
                helper.draftNo = (TextView) view.findViewById(R.id.draftNo);
                helper.dateDay = (TextView) view.findViewById(R.id.dateDay);
                helper.leftDay = (TextView) view.findViewById(R.id.leftDay);
                helper.summary = (TextView) view.findViewById(R.id.summary);
                helper.ok = (Button) view.findViewById(R.id.buttonOkText);
                helper.reject = (Button) view.findViewById(R.id.buttonRejectText);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final ExaminingArticle article = (ExaminingArticle) getItem(i);
            helper.title.setText(article.getTitle());
            helper.title.setClickable(true);
            helper.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MasterActivity.this, ArticleSummaryActivity.class);
                    intent.putExtra("articleId", article.getId());
                    startActivity(intent);
                }
            });
            helper.draftNo.setText(article.getDraftNo());
            helper.dateDay.setText(DatetimeUtils.format(article.getExamineStart()));
            helper.leftDay.setText(String.valueOf(DatetimeUtils.getDuringDay(article.getExamineStart(), new Date())) + "天");
            helper.summary.setText(article.getSummary());

            helper.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PassDialog passDialog = new PassDialog(MasterActivity.this, R.style.my_dialog) {
                        @Override
                        public void onButtonClick(final String comment) {
                            if (opLock.compareAndSet(false, true)) {
                                TPool.post(new Runnable() {
                                               @Override
                                               public void run() {
                                                   try {
                                                       article.setComment(comment);
                                                       if (ArticleApi.passExamine(article)) {
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
            helper.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PassDialog passDialog = new PassDialog(MasterActivity.this, R.style.my_dialog) {
                        @Override
                        public void onButtonClick(final String comment) {
                            if (opLock.compareAndSet(false, true)) {
                                TPool.post(new Runnable() {
                                               @Override
                                               public void run() {
                                                   try {
                                                       article.setComment(comment);
                                                       if (ArticleApi.rejectExamine(article)) {
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
            return view;
        }
    }

    private class AdaptorHelper {
        private TextView title;
        private TextView draftNo;
        private TextView dateDay;
        private TextView leftDay;
        private TextView summary;
        private Button ok;
        private Button reject;
    }

}

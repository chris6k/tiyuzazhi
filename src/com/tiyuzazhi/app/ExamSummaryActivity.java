package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.component.PassDialog;
import com.tiyuzazhi.enums.EXAM_STEP;
import com.tiyuzazhi.utils.DatetimeUtils;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chris.xue
 *         审核摘要
 */
public class ExamSummaryActivity extends Activity {

    private Handler handler;
    private ListView flowListView;
    private ExaminingArticle article;
    private ImageView operate;
    private View buttonOk;
    private View buttonReject;
    private View buttonExaminer;
    private AtomicBoolean opLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.examine_center);
        super.onCreate(savedInstanceState);
        article = (ExaminingArticle) getIntent().getSerializableExtra("article");
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        handler = new Handler(Looper.getMainLooper());
        flowListView = (ListView) findViewById(R.id.summaryList);
        final View opPanel = findViewById(R.id.opPanel);
        opPanel.setVisibility(View.GONE);
        opLock = new AtomicBoolean(false);
        operate = (ImageView) findViewById(R.id.operate);
        operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opPanel.getVisibility() != View.VISIBLE) {
                    operate.setImageResource(R.drawable.edit);
                    opPanel.setVisibility(View.VISIBLE);
                } else {
                    operate.setImageResource(R.drawable.points);
                    opPanel.setVisibility(View.GONE);
                }
            }
        });
        buttonOk = findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PassDialog passDialog = new PassDialog(ExamSummaryActivity.this, R.style.my_dialog) {
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
        buttonReject = findViewById(R.id.buttonReject);
        buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PassDialog passDialog = new PassDialog(ExamSummaryActivity.this, R.style.my_dialog) {
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
        buttonExaminer = findViewById(R.id.examiner);
        buttonExaminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamSummaryActivity.this, ExaminerActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                final List<ExaminingArticle> examiningArticles = ArticleApi.loadExamineFlow(article.getId());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        flowListView.setAdapter(new SummaryAdaptor(examiningArticles));
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class SummaryAdaptor extends BaseAdapter {
        private List<ExaminingArticle> articleList;

        public SummaryAdaptor(List<ExaminingArticle> articleList) {
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
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.examine_center_item, null, false);
                helper = new AdaptorHelper();
                helper.summaryNo = (TextView) view.findViewById(R.id.summaryNo);
                helper.step = (TextView) view.findViewById(R.id.step);
                helper.company = (TextView) view.findViewById(R.id.company);
                helper.examiner = (TextView) view.findViewById(R.id.examinerText);
                helper.dateDayStart = (TextView) view.findViewById(R.id.dateDayStart);
                helper.dateDayEnd = (TextView) view.findViewById(R.id.dateDayEnd);
                helper.comment = (TextView) view.findViewById(R.id.comment);
                helper.upDownArrow = (ImageView) view.findViewById(R.id.upDownArrow);
                helper.result = (ImageView) view.findViewById(R.id.result);
                helper.bottomPanel = view.findViewById(R.id.panel_bottom);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final ExaminingArticle article = (ExaminingArticle) getItem(i);
            helper.summaryNo.setText(String.valueOf(getCount() - i));
            helper.step.setText(EXAM_STEP.findByCode(article.getStep()).getName());
            helper.company.setText(article.getOrgName());
            helper.examiner.setText(article.getOpName());
            helper.dateDayStart.setText(DatetimeUtils.format(article.getExamineStart()));
            helper.dateDayEnd.setText(DatetimeUtils.format(article.getExamineFinish()));
            helper.comment.setText(article.getComment());
            final int openDrawableId;
            final int closeDrawableId;
            if (article.getState() == 1) {
                view.setBackgroundResource(R.drawable.module_bg);
                helper.summaryNo.setTextColor(Color.parseColor("#e6e6e6"));
                helper.result.setImageResource(R.drawable.complete_xhdpi);
                openDrawableId = R.drawable.open_module_gray_xhdpi;
                closeDrawableId = R.drawable.close_module_gray_xhdpi;
            } else {
                helper.summaryNo.setTextColor(Color.parseColor("#fdc68f"));
                openDrawableId = R.drawable.open_module_white_xhdpi;
                view.setBackgroundResource(R.drawable.module_yellow_bg);
                helper.result.setImageResource(R.drawable.uncomplete_xhdpi);
                closeDrawableId = R.drawable.close_module_white_xhdpi;
            }
            helper.upDownArrow.setImageResource(openDrawableId);
            helper.bottomPanel.setVisibility(View.GONE);
            helper.upDownArrow.setClickable(true);
            helper.upDownArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (helper.bottomPanel.getVisibility() != View.VISIBLE) {
                        helper.bottomPanel.setVisibility(View.VISIBLE);
                        helper.upDownArrow.setImageResource(closeDrawableId);
                    } else {
                        helper.bottomPanel.setVisibility(View.GONE);
                        helper.upDownArrow.setImageResource(openDrawableId);
                    }
                }
            });
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ExamSummaryActivity.this, ArticleSummaryActivity.class);
                    intent.putExtra("articleId", article.getId());
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private class AdaptorHelper {
        private TextView summaryNo;
        private TextView step;
        private ImageView result;
        private TextView company;
        private TextView examiner;
        private TextView dateDayStart;
        private TextView dateDayEnd;
        private TextView comment;
        private ImageView upDownArrow;
        private View bottomPanel;
    }

}

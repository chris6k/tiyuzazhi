package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.enums.EXAM_STEP;
import com.tiyuzazhi.enums.Status;
import com.tiyuzazhi.enums.Step;
import com.tiyuzazhi.utils.DatetimeUtils;
import com.tiyuzazhi.utils.TPool;

import java.util.List;

/**
 * @author chris.xue
 */
public class ExamineDetailActivity extends Activity {

    private Handler handler;
    private ListView examineDetailList;
    private volatile int articleId;
    private volatile List<ExaminingArticle> examiningArticles;
    private int indentPixel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.examine_detail);
        super.onCreate(savedInstanceState);
        articleId = getIntent().getIntExtra("articleId", 0);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        handler = new Handler(Looper.getMainLooper());
        examineDetailList = (ListView) findViewById(R.id.examineDetailList);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        indentPixel = Math.round(dm.density * 16 + 1) * 2;
        init();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                examiningArticles = ArticleApi.loadExamineFlow(articleId);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        examineDetailList.setAdapter(new DetailAdaptor(examiningArticles));
                    }
                });
            }
        });
    }

    private class DetailAdaptor extends BaseAdapter {
        private List<ExaminingArticle> articleList;

        public DetailAdaptor(List<ExaminingArticle> articleList) {
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
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.examine_detail_item, null, false);
                helper = new AdaptorHelper();
                helper.dateDay = (TextView) view.findViewById(R.id.dateDay);
                helper.step = (TextView) view.findViewById(R.id.step);
                helper.detailPanel = view.findViewById(R.id.commPanel);
                helper.attachmentPanel = view.findViewById(R.id.attachmentPanel);
                helper.detail = (TextView) view.findViewById(R.id.stepDetail);
                helper.systemNotify = (TextView) view.findViewById(R.id.systemNotify);
                helper.attachmentText = (TextView) view.findViewById(R.id.attachmentText);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final ExaminingArticle article = (ExaminingArticle) getItem(i);
            helper.dateDay.setText(DatetimeUtils.format(article.getExamineStart()));
            helper.step.setText(article.getOpName());
            if (TextUtils.isEmpty(article.getAttachment())) {
                helper.detailPanel.setVisibility(View.VISIBLE);
                helper.detail.setText(Step.findStatusByCode(article.getStep()).getText());
                helper.systemNotify.setText(
                        createIndentedText(Status.findStatusByCode(article.getState()).getSystem(), indentPixel, 0));
                helper.attachmentPanel.setVisibility(View.GONE);
            } else {
                helper.detailPanel.setVisibility(View.GONE);
                helper.attachmentPanel.setVisibility(View.VISIBLE);
                helper.attachmentText.setText(article.getAttachmentText());
            }
            return view;
        }
    }

    private class AdaptorHelper {
        private TextView dateDay;
        private TextView step;
        private View detailPanel;
        private View attachmentPanel;
        private TextView attachmentText;
        private TextView detail;
        private TextView systemNotify;
    }

    static SpannableString createIndentedText(String text, int marginFirstLine, int marginNextLines) {
        SpannableString result = new SpannableString(text);
        result.setSpan(new LeadingMarginSpan.Standard(marginFirstLine, marginNextLines), 0, text.length(), 0);
        return result;
    }
}

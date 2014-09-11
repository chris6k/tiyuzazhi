package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.utils.DatetimeUtils;

import java.util.List;

/**
 * @author chris.xue
 *         审核摘要
 */
public class ExamSummaryActivity extends Activity {

    private Handler handler;
    private ListView flowListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.examine_center);
        super.onCreate(savedInstanceState);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        handler = new Handler(Looper.getMainLooper());
        flowListView = (ListView) findViewById(R.id.summaryList);
        init();
    }

    private void init() {
        //TODO
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            final AdaptorHelper helper;
            if (view == null || view.getTag() == null) {
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.examine_center_item, null, false);
                helper = new AdaptorHelper();
                helper.summaryNo = (TextView) view.findViewById(R.id.summaryNo);
                helper.step = (TextView) view.findViewById(R.id.step);
                helper.company = (TextView) view.findViewById(R.id.company);
                helper.examiner = (TextView) view.findViewById(R.id.examiner);
                helper.dateDayStart = (TextView) view.findViewById(R.id.dateDayStart);
                helper.dateDayEnd = (TextView) view.findViewById(R.id.dateDayEnd);
                helper.summary = (TextView) view.findViewById(R.id.summary);
                helper.upDownArrow = (ImageView) view.findViewById(R.id.upDownArrow);
                helper.result = (ImageView) view.findViewById(R.id.result);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final ExaminingArticle article = (ExaminingArticle) getItem(i);
            helper.summaryNo.setText(String.valueOf(i));
            helper.step.setText(String.valueOf(article.getStep()));
            helper.company.setText(article.getOrgName());
            helper.examiner.setText(article.getOpName());
            helper.dateDayStart.setText(DatetimeUtils.format(article.getExamineStart()));
            helper.dateDayEnd.setText(DatetimeUtils.format(article.getExamineFinish()));
            helper.summary.setText(article.getSummary());
            if (article.getState() == 1) {
                helper.result.setImageResource(R.drawable.complete_xhdpi);
            } else {
                helper.result.setImageResource(R.drawable.uncomplete_xhdpi);
            }
            helper.upDownArrow.setClickable(true);
            helper.upDownArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (helper.summary.getVisibility() != View.VISIBLE) {
                        helper.summary.setVisibility(View.VISIBLE);
                        helper.upDownArrow.setImageResource(R.drawable.close_module_gray_xhdpi);
                    } else {
                        helper.summary.setVisibility(View.GONE);
                        helper.upDownArrow.setImageResource(R.drawable.open_module_gray_xhdpi);
                    }
                }
            });
            return view;
        }
    }

    private class AdaptorHelper {
        private TextView summaryNo;
        private TextView step;
        private ImageView result;
        private TextView company;
        private TextView examiner;
        private TextView dateDayStart;
        private TextView dateDayEnd;
        private TextView summary;
        private ImageView upDownArrow;
    }

}

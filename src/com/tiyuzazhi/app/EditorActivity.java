package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.component.PassDialog;
import com.tiyuzazhi.enums.EXAM_STEP;
import com.tiyuzazhi.service.CheckNotifyService;
import com.tiyuzazhi.utils.DatetimeUtils;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chris.xue
 *         编辑审稿
 */
public class EditorActivity extends Activity {
    private ListView articleListView;
    private AtomicBoolean opLock;
    private Handler handler;
    private View reorderByDateDay;
    private View flowButton;
    private View reorderButton;
    private ImageView dateDayOrder;
    private View filter;
    private ImageView filterOrder;
    private Spinner spinner;
    private boolean orderByDateDesc = true;
    private volatile List<ExaminingArticle> articles;
    private int step;
    private String stepName;
    private String[] stepNames = {"全部流程", EXAM_STEP.SHOUGAO.getName(), EXAM_STEP.TUIXIU.getName(), EXAM_STEP.WAISHEN.getName(), EXAM_STEP.ZHONGSHEN.getName()};
    private int[] stepCodes = {-1, EXAM_STEP.SHOUGAO.getCode(), EXAM_STEP.TUIXIU.getCode(), EXAM_STEP.WAISHEN.getCode(), EXAM_STEP.ZHONGSHEN.getCode()};
    private CheckNotifyService.SimpleBinder sBinder;
    private RelativeLayout articleListPanel;
    private boolean hasShowNotify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.editor_layout);
        super.onCreate(savedInstanceState);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        articleListView = (ListView) findViewById(R.id.articleList);
        flowButton = findViewById(R.id.flowButton);
        reorderButton = findViewById(R.id.reorderButton);
        reorderByDateDay = findViewById(R.id.reorderByDateDay);
        dateDayOrder = (ImageView) findViewById(R.id.dateDayOrder);
        filter = findViewById(R.id.filter);
        spinner = (Spinner) findViewById(R.id.cateSpinner);
        SpinnerAdapter adapter = new ArrayAdapter<String>(EditorActivity.this,
                android.R.layout.simple_spinner_item, stepNames);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (opLock.compareAndSet(false, true)) {
                    try {
                        List<ExaminingArticle> filtered = filterByStatus(stepCodes[position]);
                        articleListView.setAdapter(new ArticleAdaptor(filtered));
                        ((TextView) filter).setText(stepNames[position]);
                    } finally {
                        opLock.set(false);
                    }
                } else {
                    ToastUtils.show("前一个操作正在进行，请稍后");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner.setAdapter(adapter);
        filterOrder = (ImageView) findViewById(R.id.filterOrder);
        opLock = new AtomicBoolean(false);
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (message.what == 1) {
                    if (sBinder.hasNotiy() && !hasShowNotify) {
                        View view = LayoutInflater.from(EditorActivity.this).inflate(R.layout.notify, null, false);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        view.setLayoutParams(params);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hasShowNotify = false;
                                articleListPanel.removeView(view);
                            }
                        });
                        articleListPanel.addView(view);
                        hasShowNotify = true;
                    }
                    handler.sendEmptyMessageDelayed(1, 30 * 1000);
                }
                return true;
            }
        });
        reorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opLock.compareAndSet(false, true)) {
                    try {
                        reorderByDateDay(orderByDateDesc);
                        articleListView.setAdapter(new ArticleAdaptor(articles));
                        orderByDateDesc = !orderByDateDesc;
                        if (!orderByDateDesc) {
                            dateDayOrder.setImageResource(R.drawable.close_xhdpi);
                        } else {
                            dateDayOrder.setImageResource(R.drawable.open_xhdpi);
                        }
                    } finally {
                        opLock.set(false);
                    }
                } else {
                    ToastUtils.show("前一个操作正在进行，请稍后");
                }
            }
        });
        flowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner.performClick();

            }
        });
        articleListPanel = (RelativeLayout) findViewById(R.id.articleListPanel);
        //初始化通知
        ServiceConnection sc = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                sBinder = (CheckNotifyService.SimpleBinder) service;
                handler.sendEmptyMessageDelayed(1, 30 * 1000);
            }
        };
        bindService(new Intent(EditorActivity.this, CheckNotifyService.class), sc, Context.BIND_AUTO_CREATE);
        init();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                try {
                    articles = ArticleApi.loadExamineArticle(0, 10);
                    if (articles.isEmpty()) {
                        ToastUtils.show("没有更多文章");
                        return;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            articleListView.setAdapter(new ArticleAdaptor(articles));
                        }
                    });
                } catch (Exception e) {
                    ToastUtils.show("未能获取到数据，请稍后再试");
                }
            }
        });
    }

    /**
     * 根据日期升序/降序排列
     *
     * @param asc
     */
    private void reorderByDateDay(final boolean asc) {
        ExaminingArticle[] data = articles.toArray(new ExaminingArticle[articles.size()]);
        Arrays.sort(data, new Comparator<ExaminingArticle>() {
            @Override
            public int compare(ExaminingArticle lhs, ExaminingArticle rhs) {
                return lhs.getExamineStart().compareTo(rhs.getExamineStart()) * (asc ? 1 : -1);
            }
        });
        articles = new ArrayList<ExaminingArticle>(Arrays.asList(data));
    }

    /**
     * 根据条件过滤
     *
     * @param step
     * @return
     */
    private List<ExaminingArticle> filterByStatus(int step) {
        if (step == -1) {
            return articles;
        }
        List<ExaminingArticle> filtered = new ArrayList<ExaminingArticle>(articles.size());
        for (ExaminingArticle article : articles) {
            if (article.getStep() == step) {
                filtered.add(article);
            }
        }
        return filtered;
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
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.editor_list_item, null, false);
                helper = new AdaptorHelper();
                helper.title = (TextView) view.findViewById(R.id.title);
                helper.draftNo = (TextView) view.findViewById(R.id.draftNo);
                helper.dateDay = (TextView) view.findViewById(R.id.dateDay);
                helper.opName = (TextView) view.findViewById(R.id.opName);
                helper.ok = (Button) view.findViewById(R.id.buttonOkText);
                helper.reject = (Button) view.findViewById(R.id.buttonRejectText);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final ExaminingArticle article = (ExaminingArticle) getItem(i);
            helper.title.setText(article.getTitle());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EditorActivity.this, ExamSummaryActivity.class);
                    intent.putExtra("article", article);
                    startActivity(intent);
                }
            });
            helper.draftNo.setText(article.getDraftNo());
            helper.dateDay.setText(DatetimeUtils.format(article.getExamineStart()));
            helper.opName.setText(article.getOpName());
            helper.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PassDialog passDialog = new PassDialog(EditorActivity.this, R.style.my_dialog) {
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
                    final PassDialog passDialog = new PassDialog(EditorActivity.this, R.style.my_dialog) {
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
        private TextView opName;
        private Button ok;
        private Button reject;
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(1);
    }
}

package com.tiyuzazhi.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.enums.Step;
import com.tiyuzazhi.service.CheckNotifyService;
import com.tiyuzazhi.utils.DatetimeUtils;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.ArrayList;
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
    private volatile boolean orderByDateAsc = false;
    private volatile List<ExaminingArticle> articles = new ArrayList<ExaminingArticle>();
    private int step;
    private String stepName;
    private String[] stepNames = {"全部流程", Step.NEW.getText(), Step.FIRST_MANU.getText(), Step.REDO.getText(), Step.EXTERNAL_MANU.getText(), Step.FINAL.getText()};
    private int[] stepCodes = {0, Step.NEW.getCode(), Step.FIRST_MANU.getCode(), Step.REDO.getCode(), Step.EXTERNAL_MANU.getCode(), Step.FINAL.getCode()};
    private CheckNotifyService.SimpleBinder sBinder;
    private RelativeLayout articleListPanel;
    private boolean hasShowNotify = false;
    private volatile int offset;
    private ArticleAdaptor adaptor;
//    private volatile boolean isAsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.editor_layout);
        super.onCreate(savedInstanceState);
        offset = 0;
        step = 0;
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final TextView textView = new TextView(EditorActivity.this);
        textView.setLayoutParams(new AbsListView.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(10, 10, 10, 10);
        textView.setText("点击载入更多");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("数据载入中，请稍候...");
                init(articles.size(), step, orderByDateAsc);
            }
        });
        articleListView = (ListView) findViewById(R.id.articleList);
        articleListView.addFooterView(textView);
        adaptor = new ArticleAdaptor();
        articleListView.setAdapter(adaptor);


        flowButton = findViewById(R.id.flowButton);
        reorderButton = findViewById(R.id.reorderButton);
        reorderByDateDay = findViewById(R.id.reorderByDateDay);
        dateDayOrder = (ImageView) findViewById(R.id.dateDayOrder);
        filter = findViewById(R.id.filter);
        spinner = (Spinner) findViewById(R.id.cateSpinner);
        if (orderByDateAsc) {
            dateDayOrder.setImageResource(R.drawable.close_xhdpi);
        } else {
            dateDayOrder.setImageResource(R.drawable.open_xhdpi);
        }
        SpinnerAdapter adapter = new ArrayAdapter<String>(EditorActivity.this,
                android.R.layout.simple_spinner_dropdown_item, stepNames);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (opLock.compareAndSet(false, true)) {
                    try {
                        filterByStatus(stepCodes[position]);
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
                        reorderByDateDay(orderByDateAsc);
                        adaptor.notifyDataSetChanged();
                        orderByDateAsc = !orderByDateAsc;
                        if (orderByDateAsc) {
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
        init(offset, step, orderByDateAsc);
    }

    private void init(final int offset, final int step, final boolean asc) {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                try {

                    final List<ExaminingArticle> articleList = ArticleApi.loadExamineArticle(offset, 10, step, asc);
                    if (articleList.isEmpty()) {
                        ToastUtils.show("没有更多文章");
                    }


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (offset > 0 && articles != null) {
                                articles.addAll(articleList);
                            } else {
                                articles = articleList;
                            }
                            adaptor.notifyDataSetChanged();
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
        init(offset, step, !asc);
    }

    /**
     * 根据条件过滤
     *
     * @param step
     * @return
     */
    private void filterByStatus(int step) {
        this.offset = 0;
        this.step = step;
        init(offset, step, orderByDateAsc);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class ArticleAdaptor extends BaseAdapter {

        public ArticleAdaptor() {
        }

        @Override
        public int getCount() {
            return articles.size();
        }

        @Override
        public Object getItem(int i) {
            return articles.get(i);
        }

        @Override
        public long getItemId(int i) {
            return articles.get(i).getId();
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
            helper.dateDay.setText(DatetimeUtils.format(article.getSubmitDate()));
            helper.opName.setText(article.getOpName());
            helper.ok.setEnabled(false);
            helper.reject.setEnabled(false);
            //TODO
//            helper.ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final PassDialog passDialog = new PassDialog(EditorActivity.this, R.style.my_dialog) {
//                        @Override
//                        public void onButtonClick(final String comment) {
//                            if (opLock.compareAndSet(false, true)) {
//                                TPool.post(new Runnable() {
//                                               @Override
//                                               public void run() {
//                                                   try {
//                                                       article.setComment(comment);
//                                                       if (ArticleApi.passExamine(article)) {
//                                                           ToastUtils.show("操作成功");
//                                                           dismiss();
//                                                           init(offset, step, orderByDateAsc);
//                                                       } else {
//                                                           ToastUtils.show("操作失败");
//                                                       }
//                                                   } finally {
//                                                       opLock.set(false);
//                                                   }
//                                               }
//                                           }
//                                );
//                            } else {
//                                ToastUtils.show("前一个操作正在进行，请稍后再试");
//                            }
//                        }
//                    };
//                    passDialog.setText("审核通过");
//                    passDialog.setButtonText("通过");
//                    passDialog.show();
//                    WindowManager windowManager = getWindowManager();
//                    Display display = windowManager.getDefaultDisplay();
//                    WindowManager.LayoutParams lp = passDialog.getWindow().getAttributes();
//                    lp.width = (int) (display.getWidth()); //设置宽度
//                    passDialog.getWindow().setAttributes(lp);
//                }
//            });
//            helper.reject.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final PassDialog passDialog = new PassDialog(EditorActivity.this, R.style.my_dialog) {
//                        @Override
//                        public void onButtonClick(final String comment) {
//                            if (opLock.compareAndSet(false, true)) {
//                                TPool.post(new Runnable() {
//                                               @Override
//                                               public void run() {
//                                                   try {
//                                                       article.setComment(comment);
//                                                       if (ArticleApi.rejectExamine(article)) {
//                                                           ToastUtils.show("操作成功");
//                                                           dismiss();
//                                                           init(offset, step, orderByDateAsc);
//                                                       } else {
//                                                           ToastUtils.show("操作失败");
//                                                       }
//                                                   } finally {
//                                                       opLock.set(false);
//                                                   }
//                                               }
//                                           }
//                                );
//                            } else {
//                                ToastUtils.show("前一个操作正在进行，请稍后再试");
//                            }
//                        }
//                    };
//                    passDialog.setText("审核不通过");
//                    passDialog.setButtonText("不通过");
//                    passDialog.show();
//                    WindowManager windowManager = getWindowManager();
//                    Display display = windowManager.getDefaultDisplay();
//                    WindowManager.LayoutParams lp = passDialog.getWindow().getAttributes();
//                    lp.width = (int) (display.getWidth()); //设置宽度
//                    passDialog.getWindow().setAttributes(lp);
//                }
//            });
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

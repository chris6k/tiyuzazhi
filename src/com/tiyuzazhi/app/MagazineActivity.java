package com.tiyuzazhi.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.Magazine;
import com.tiyuzazhi.component.MagazineItem;
import com.tiyuzazhi.component.ShareDialog;
import com.tiyuzazhi.component.SharePanelDialog;
import com.tiyuzazhi.utils.DatetimeUtils;
import com.tiyuzazhi.utils.ShareUtils;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chris.xue
 *         杂志
 */
public class MagazineActivity extends Activity {
    private ListView menuListView;
    private Handler handler;
    private View nextButton;
    private View previousButton;
    private TextView magazineNo;
    private TextView magazinePubTime;
    private AtomicBoolean opLock;
    private TextView title;
    //    private Map<Integer, Boolean> selectIds;
    private volatile Magazine magazine;
    private View fav;
    private View share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.magazine_list_layout);
        super.onCreate(savedInstanceState);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        menuListView = (ListView) findViewById(R.id.article_item_list);
        menuListView.setItemsCanFocus(true);
        handler = new Handler(Looper.getMainLooper());
        nextButton = findViewById(R.id.next_mag);
        previousButton = findViewById(R.id.previous_mag);
        magazineNo = (TextView) findViewById(R.id.mag_no);
        magazinePubTime = (TextView) findViewById(R.id.mag_date);
        opLock = new AtomicBoolean(false);
        magazine = (Magazine) this.getIntent().getSerializableExtra("magazine");
        title = (TextView) findViewById(R.id.title);
        title.setText(magazine.getTitle());
//        selectIds = new HashMap<Integer, Boolean>(30, 0.5f);

        fav = findViewById(R.id.collect);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                ToastUtils.show("操作成功");
            }
        });
        share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePanelDialog sharePanelDialog = new SharePanelDialog(MagazineActivity.this, R.style.my_dialog) {
                    @Override
                    public void onButtonClick(final int platformId) {
                        ShareDialog shareDialog = new ShareDialog(MagazineActivity.this, R.style.my_dialog) {
                            @Override
                            public void onButtonClick(final String content) {
                                TPool.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        ShareUtils.share(platformId, content, new PlatformActionListener() {
                                            @Override
                                            public void onComplete(Platform platform, int i, HashMap<String, Object> stringObjectHashMap) {
                                                ToastUtils.show("分享成功");
                                            }

                                            @Override
                                            public void onError(Platform platform, int i, Throwable throwable) {
                                                ToastUtils.show("分享失败");
                                            }

                                            @Override
                                            public void onCancel(Platform platform, int i) {
                                                ToastUtils.show("分享取消");
                                            }
                                        });
                                    }
                                });
                                dismiss();
                            }
                        };
                        SparseBooleanArray array = menuListView.getCheckedItemPositions();
                        if (array.size() > 0) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0, j = 0; i < menuListView.getAdapter().getCount() && j < array.size(); i++) {
                                if (array.get(i, false)) {
                                    ArticleMenu menu = (ArticleMenu) menuListView.getAdapter().getItem(i);
                                    sb.append(menu.getTitle()).append(" ");
                                    j++;
                                }
                            }
                            shareDialog.setContent(sb.toString());
                        }
                        shareDialog.show();
                        WindowManager windowManager = getWindowManager();
                        Display display = windowManager.getDefaultDisplay();
                        WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
                        lp.width = (int) (display.getWidth()); //设置宽度
                        shareDialog.getWindow().setAttributes(lp);
                        dismiss();
                    }
                };
                sharePanelDialog.show();
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = sharePanelDialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth()); //设置宽度
                sharePanelDialog.getWindow().setAttributes(lp);

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opLock.compareAndSet(false, true)) {
                    ToastUtils.show("内容载入中，请稍候");
                    TPool.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Magazine newMag = ArticleApi.loadNextMagazine(magazine.getId());
                                if (newMag != null) {
                                    magazine = newMag;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            previousButton.setEnabled(true);
                                            init();
                                        }
                                    });
                                } else {
                                    ToastUtils.show("没有更新的杂志");
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            nextButton.setEnabled(false);
                                        }
                                    });
                                }
                            } finally {
                                opLock.set(false);
                            }
                        }
                    });
                } else {
                    ToastUtils.show("前一个操作未完成，请稍候再试");
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opLock.compareAndSet(false, true)) {
                    ToastUtils.show("内容载入中，请稍候");
                    TPool.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Magazine newMag = ArticleApi.loadPrevMagazine(magazine.getId());
                                if (newMag != null) {
                                    magazine = newMag;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            nextButton.setEnabled(true);
                                            init();
                                        }
                                    });
                                } else {
                                    ToastUtils.show("没有更早的杂志");
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            previousButton.setEnabled(false);
                                        }
                                    });
                                }
                            } finally {
                                opLock.set(false);
                            }
                        }
                    });
                } else {
                    ToastUtils.show("前一个操作未完成，请稍候再试");
                }
            }
        });
        ShareSDK.initSDK(this);
        init();
    }

    private void init() {
        magazineNo.setText(magazine.getPublishNo());
        magazinePubTime.setText(DatetimeUtils.format2(magazine.getPublishTime()));
        TPool.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<ArticleMenu> articleMenus = ArticleApi.loadArticleMenu(magazine.getId());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            menuListView.setAdapter(new ArticleAdaptor(articleMenus));
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
        private List<ArticleMenu> articleMenuList;

        public ArticleAdaptor(List<ArticleMenu> articleMenuList) {
            this.articleMenuList = articleMenuList;
        }

        @Override
        public int getCount() {
            return articleMenuList.size();
        }

        @Override
        public Object getItem(int i) {
            return articleMenuList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return articleMenuList.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            AdaptorHelper helper;
            if (view == null || view.getTag() == null) {
                view = new MagazineItem(MagazineActivity.this);
                int color = getResources().getColor(R.color.white);
                if ((i + 1) % 2 == 0) {
                    color = Color.parseColor("#f5f5f5");
                }
                ((MagazineItem) view).setBaseColor(color);
                helper = new AdaptorHelper();
                helper.title = (TextView) view.findViewById(R.id.title);
                helper.author = (TextView) view.findViewById(R.id.author);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final ArticleMenu articleMenu = (ArticleMenu) getItem(i);
            helper.title.setText(articleMenu.getTitle());
            helper.author.setText("作者:" + articleMenu.getAuthor());
            return view;
        }
    }

    private class AdaptorHelper {
        private TextView title;
        private TextView author;
    }
}

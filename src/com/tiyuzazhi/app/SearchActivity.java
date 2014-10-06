package com.tiyuzazhi.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.Magazine;
import com.tiyuzazhi.component.MagazineItem;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chris.xue
 *         搜索结果
 */
public class SearchActivity extends Activity {
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
    private volatile int index;
    private String keywords;
    private volatile boolean hasMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.magazine_list_layout);
        super.onCreate(savedInstanceState);
        hasMore = true;
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
        magazineNo.setVisibility(View.INVISIBLE);
        magazinePubTime.setVisibility(View.INVISIBLE);
        opLock = new AtomicBoolean(false);
        keywords = this.getIntent().getStringExtra("keywords");
        title = (TextView) findViewById(R.id.title);
        title.setText("搜索结果");
        TextView nextText = (TextView) findViewById(R.id.next_mag_text);
        nextText.setText("下一页");
        TextView prevText = (TextView) findViewById(R.id.previous_mag_text);
        nextText.setText("上一页");
        View closeArrow = findViewById(R.id.close_arrow);
        closeArrow.setVisibility(View.INVISIBLE);
        fav = findViewById(R.id.collect);
        fav.setVisibility(View.INVISIBLE);
        share = findViewById(R.id.share);
        share.setVisibility(View.INVISIBLE);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opLock.compareAndSet(false, true)) {
                    ToastUtils.show("内容载入中，请稍候");
                    TPool.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (hasMore) {
                                    index += 10;
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        init();
                                    }
                                });
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
                                index = Math.max(index - 10, 0);
                                if (index > 0) {
                                    hasMore = true;
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        init();
                                    }
                                });
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
        TPool.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<ArticleMenu> articleMenus = ArticleApi.search(keywords, index);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (articleMenus.isEmpty() || articleMenus.size() < 10) {
                                ToastUtils.show("没有更多结果");
                                hasMore = false;
                                nextButton.setEnabled(false);
                            } else {

                                nextButton.setEnabled(true);
                            }
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
                view = new MagazineItem(SearchActivity.this);
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

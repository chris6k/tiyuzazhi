package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.Notice;
import com.tiyuzazhi.component.MagazineItem;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.List;

/**
 * @author chris.xue
 *         通告
 */
public class NoticeActivity extends Activity {
    private ListView menuListView;
    private Handler handler;
    private TextView magazineNo;
    private TextView magazinePubTime;
    private TextView title;
    private volatile List<Notice> notices;

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
        magazineNo = (TextView) findViewById(R.id.mag_no);
        magazinePubTime = (TextView) findViewById(R.id.mag_date);
        magazineNo.setVisibility(View.INVISIBLE);
        magazinePubTime.setVisibility(View.INVISIBLE);
        View share = findViewById(R.id.share);
        share.setVisibility(View.INVISIBLE);
        View fav = findViewById(R.id.collect);
        fav.setVisibility(View.INVISIBLE);
        title = (TextView) findViewById(R.id.title);
        title.setText("公告通知");
        View bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setVisibility(View.GONE);
        init();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                try {
                    notices = ArticleApi.getNotice();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            menuListView.setAdapter(new ArticleAdaptor(notices));
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
        private List<Notice> articleMenuList;

        public ArticleAdaptor(List<Notice> articleMenuList) {
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
            int color = getResources().getColor(R.color.white);
            if (view == null || view.getTag() == null) {
                view = new MagazineItem(NoticeActivity.this);
                if ((i + 1) % 2 == 0) {
                    color = Color.parseColor("#f5f5f5");
                }
                ((MagazineItem) view).setBaseColor(color);
                helper = new AdaptorHelper();
                helper.title = (TextView) view.findViewById(R.id.title);
                helper.author = (TextView) view.findViewById(R.id.author);
                helper.keyword = view.findViewById(R.id.keyword);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
                if ((i + 1) % 2 == 0) {
                    color = Color.parseColor("#f5f5f5");
                }
                ((MagazineItem) view).setBaseColor(color);
            }
            final Notice notice = (Notice) getItem(i);
            helper.title.setText(notice.getTitle());
            helper.author.setVisibility(View.GONE);
            helper.keyword.setVisibility(View.GONE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NoticeActivity.this, NoticeSummaryActivity.class);
                    intent.putExtra("notice", notice);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    private class AdaptorHelper {
        private TextView title;
        private TextView author;
        private View keyword;
    }
}

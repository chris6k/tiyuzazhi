package com.tiyuzazhi.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.beans.ArticleMenu;
import com.tiyuzazhi.beans.ExaminingArticle;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.List;

/**
 * @author chris.xue
 *         杂志
 */
public class MagazineActivity extends Activity {
    private ListView titleBar;
    private Handler handler;

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
        titleBar = (ListView) findViewById(R.id.article_item_list);
        handler = new Handler(Looper.getMainLooper());
        init();
    }

    private void init() {
        final int magazineId = this.getIntent().getIntExtra("magazineId", 0);
        TPool.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<ArticleMenu> articleMenus = ArticleApi.loadArticleMenu(magazineId);
                    if (articleMenus.isEmpty()) {
                        ToastUtils.show("该期杂志目录为空");
                        return;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            titleBar.setAdapter(new ArticleAdaptor(articleMenus));
                            titleBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //TODO 跳转到文章正文
                                }
                            });
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
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.magazine_list_item, null, false);
                if ((i + 1) % 2 == 0) {
                    view.setBackgroundColor(Color.parseColor("#f5f5f5"));
                }
                helper = new AdaptorHelper();
                helper.title = (TextView) view.findViewById(R.id.title);
                helper.author = (TextView) view.findViewById(R.id.summary);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final ArticleMenu articleMenu = (ExaminingArticle) getItem(i);
            helper.title.setText(articleMenu.getTitle());
            helper.author.setText(articleMenu.getAuthor());
            return view;
        }
    }

    private class AdaptorHelper {
        private TextView title;
        private TextView author;
    }
}

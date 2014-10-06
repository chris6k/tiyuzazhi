package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.tiyuzazhi.api.ArticleApi;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.beans.Magazine;
import com.tiyuzazhi.beans.StatsDashboard;
import com.tiyuzazhi.beans.User;
import com.tiyuzazhi.component.RoundedImageView;
import com.tiyuzazhi.utils.ImageLoader;
import com.tiyuzazhi.utils.TPool;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author chris.xue
 *         首页
 */
public class HomeActivity extends Activity {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private Handler handler;
    private ListView magazineList;
    private ImageView header;
    private TextView authorCenterNo;
    private TextView refereeingNo;
    private TextView editorCenterNo;
    private TextView chiefEditorNo;
    private TextView userName;
    private int zeroColor;
    private int noZeroColor;
    private int sideColorOdd;
    private int sideColorEven;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        zeroColor = Color.parseColor("#b8b8b8");
        noZeroColor = Color.parseColor("#ff8a00");
        sideColorOdd = Color.parseColor("#df1f68");
        sideColorEven = Color.parseColor("#113d7d");
        handler = new Handler(Looper.getMainLooper());
        View slideMenuButton = findViewById(R.id.slide_menu_button);
        magazineList = (ListView) findViewById(R.id.magazine_list);
        View authorCenterBtn = findViewById(R.id.author_center_btn);
        View refereeingBtn = findViewById(R.id.refereeing_btn);
        View editorBtn = findViewById(R.id.editor_center_btn);
        View chiefEditorBtn = findViewById(R.id.chief_editor_btn);
        header = (RoundedImageView) findViewById(R.id.header);
        authorCenterNo = (TextView) findViewById(R.id.author_center_no);
        refereeingNo = (TextView) findViewById(R.id.refereeing_no);
        editorCenterNo = (TextView) findViewById(R.id.editor_center_no);
        chiefEditorNo = (TextView) findViewById(R.id.chief_editor_no);
        EditText searchBar = (EditText) findViewById(R.id.search_editor);
        searchBar.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                    intent.putExtra("keywords", textView.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        userName = (TextView) findViewById(R.id.user_name);

        slideMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserCenterActivity.class);
                startActivity(intent);
            }
        });

        authorCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AuthorCenterActivity.class);
                startActivity(intent);
            }
        });
        refereeingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MasterActivity.class);
                startActivity(intent);
            }
        });
        editorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                startActivity(intent);
            }
        });
        chiefEditorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChiefEditorActivity.class);
                startActivity(intent);
            }
        });


        authorCenterNo.setText("0");
        refereeingNo.setText("0");
        editorCenterNo.setText("0");
        chiefEditorNo.setText("0");
        header.setImageDrawable(getResources().getDrawable(R.drawable.header));
        userName.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onDetachedFromWindow() {
        handler = null;
        super.onDetachedFromWindow();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                final User user = UserApi.getUserInfo();
                final StatsDashboard statsDashboard = UserApi.getUserDashboard();
                final List<Magazine> magazines = ArticleApi.loadNewestMagazine();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (statsDashboard.getUserCenterTaskNo() > 0) {
                            authorCenterNo.setTextColor(noZeroColor);
                        } else {
                            authorCenterNo.setTextColor(zeroColor);
                        }
                        if (statsDashboard.getMasterCenterTaskNo() > 0) {
                            refereeingNo.setTextColor(noZeroColor);
                        } else {
                            refereeingNo.setTextColor(zeroColor);
                        }
                        if (statsDashboard.getEditorTaskNo() > 0) {
                            editorCenterNo.setTextColor(noZeroColor);
                        } else {
                            editorCenterNo.setTextColor(zeroColor);
                        }
                        if (statsDashboard.getChiefEditorTaskNo() > 0) {
                            chiefEditorNo.setTextColor(noZeroColor);
                        } else {
                            chiefEditorNo.setTextColor(zeroColor);
                        }
                        //初始化代办事项总数
                        authorCenterNo.setText(String.valueOf(statsDashboard.getUserCenterTaskNo()));
                        refereeingNo.setText(String.valueOf(statsDashboard.getMasterCenterTaskNo()));
                        editorCenterNo.setText(String.valueOf(statsDashboard.getEditorTaskNo()));
                        chiefEditorNo.setText(String.valueOf(statsDashboard.getChiefEditorTaskNo()));


                        magazineList.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return magazines.size();
                            }

                            @Override
                            public Object getItem(int i) {
                                return magazines.get(i);
                            }

                            @Override
                            public long getItemId(int i) {
                                return magazines.get(i).getId();
                            }

                            @Override
                            public View getView(int i, View view, ViewGroup viewGroup) {
                                MagItem magItem;
                                if (view == null) {
                                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.main_magazine_item, null);
                                    magItem = new MagItem();
                                    magItem.sideColor = (ImageView) view.findViewById(R.id.mag_item_side_color);
                                    magItem.title = (TextView) view.findViewById(R.id.mag_item_title);
                                    magItem.subTitle = (TextView) view.findViewById(R.id.mag_item_sub_title);
                                    magItem.magNo = (TextView) view.findViewById(R.id.mag_no);
                                    magItem.magNoBk = view.findViewById(R.id.mag_no_bk);
                                    magItem.magData = (TextView) view.findViewById(R.id.mag_date);
                                    view.setTag(magItem);
                                } else {
                                    magItem = (MagItem) view.getTag();
                                }
                                Magazine magazine = (Magazine) getItem(i);
                                if (i % 2 != 0) {
                                    magItem.sideColor.setBackgroundColor(sideColorEven);
                                    magItem.magNoBk.setBackgroundColor(sideColorEven);
                                } else {
                                    magItem.sideColor.setBackgroundColor(sideColorOdd);
                                    magItem.magNoBk.setBackgroundColor(sideColorOdd);
                                }
                                magItem.magData.setText(dateFormat.format(magazine.getPublishTime()));
                                magItem.title.setText(magazine.getTitle());
                                magItem.subTitle.setText(magazine.getSubTitle());
                                magItem.magNo.setText(magazine.getPublishNo());

                                return view;
                            }

                            class MagItem {
                                ImageView sideColor;
                                TextView title;
                                TextView subTitle;
                                TextView magNo;
                                View magNoBk;
                                TextView magData;
                            }
                        });

                        magazineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(HomeActivity.this, MagazineActivity.class);
                                intent.putExtra("magazine", magazines.get(position));
                                startActivity(intent);
                            }
                        });

                        //初始化头像和名字
                        userName.setText(user.getName());
                        TPool.post(new Runnable() {
                            @Override
                            public void run() {
                                ImageLoader.loadPic(user.getIconPath(), new ImageLoader.ImageLoaderCallback() {
                                    @Override
                                    public void finish(Bitmap image) {
                                        header.setImageBitmap(image);
                                    }

                                    @Override
                                    public void error(Exception e) {
                                        Log.e("HomeActivity", "Load header failed", e);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });


    }
}

package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private View slideMenuButton;
    private ListView magazineList;
    private View authorCenterBtn;
    private View refereeingBtn;
    private View editorBtn;
    private View chiefEditorBtn;
    private EditText searchBar;
    private ImageView header;
    private TextView authorCenterNo;
    private TextView refereeingNo;
    private TextView editorCenterNo;
    private TextView chiefEditorNo;
    private TextView userName;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        handler = new Handler(Looper.getMainLooper());
        slideMenuButton = findViewById(R.id.slide_menu_button);
        magazineList = (ListView) findViewById(R.id.magazine_list);
        authorCenterBtn = findViewById(R.id.author_center_btn);
        refereeingBtn = findViewById(R.id.refereeing_btn);
        editorBtn = findViewById(R.id.editor_center_btn);
        chiefEditorBtn = findViewById(R.id.chief_editor_btn);
        header = (RoundedImageView) findViewById(R.id.header);
        authorCenterNo = (TextView) findViewById(R.id.author_center_no);
        refereeingNo = (TextView) findViewById(R.id.refereeing_no);
        editorCenterNo = (TextView) findViewById(R.id.editor_center_no);
        chiefEditorNo = (TextView) findViewById(R.id.chief_editor_no);
        searchBar = (EditText) findViewById(R.id.search_editor);
        userName = (TextView) findViewById(R.id.user_name);

        slideMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "选中了侧边栏菜单", Toast.LENGTH_SHORT).show();
            }
        });

        authorCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
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
                        //初始化代办事项总数
                        authorCenterNo.setText(statsDashboard.getUserCenterTaskNo());
                        refereeingNo.setText(statsDashboard.getMasterCenterTaskNo());
                        editorCenterNo.setText(statsDashboard.getEditorTaskNo());
                        chiefEditorNo.setText(statsDashboard.getChiefEditorTaskNo());


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

                        //初始化头像和名字
                        userName.setText(user.getName());
                        TPool.post(new Runnable() {
                            @Override
                            public void run() {
                                final Bitmap bitmap = ImageLoader.loadPic(user.getIconPath());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        header.setImageBitmap(bitmap);
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

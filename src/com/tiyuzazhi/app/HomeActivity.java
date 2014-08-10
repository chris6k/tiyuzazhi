package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.tiyuzazhi.component.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends Activity {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

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
                Toast.makeText(getBaseContext(), "选中了作者中心", Toast.LENGTH_SHORT).show();
            }
        });
        refereeingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "选中了审稿专家", Toast.LENGTH_SHORT).show();
            }
        });
        editorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "选中了编辑办公", Toast.LENGTH_SHORT).show();
            }
        });
        chiefEditorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "选中了主编办公", Toast.LENGTH_SHORT).show();
            }
        });
        //初始化代办事项总数
        authorCenterNo.setText("1");
        refereeingNo.setText("0");
        editorCenterNo.setText("10");
        chiefEditorNo.setText("21");


        magazineList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                MagItem magItem;
                if (view == null) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.magazine_item_layout, null);
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

                magItem.magData.setText(dateFormat.format(new Date()));
                magItem.title.setText("体育科学");
                magItem.subTitle.setText("CHINA SPORT SCIENCE");
                magItem.magNo.setText("第5期");
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
        header.setImageDrawable(getResources().getDrawable(R.drawable.header));
        userName.setText("Layee Huang");

    }
}

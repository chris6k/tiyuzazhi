package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MasterActivity extends Activity {
    private View back;
    private ListView titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.master_list_layout);
        super.onCreate(savedInstanceState);
        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleBar = (ListView) findViewById(R.id.article_item_list);
    }

    private void init() {
        //TODO
        titleBar.setAdapter( new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
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
                return null;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

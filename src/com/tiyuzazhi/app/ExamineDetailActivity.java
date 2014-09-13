package com.tiyuzazhi.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;

/**
 * @author chris.xue
 */
public class ExamineDetailActivity extends Activity {

    private Handler handler;
    private ListView examineDetailList;
    private volatile int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.examine_center);
        super.onCreate(savedInstanceState);
        articleId = getIntent().getIntExtra("articleId", 0);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        handler = new Handler(Looper.getMainLooper());
        examineDetailList = (ListView) findViewById(R.id.examineDetailList);
        init();
    }

    private void init() {
        //TODO
    }
}

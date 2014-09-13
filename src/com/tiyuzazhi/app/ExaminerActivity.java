package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.beans.Examiner;
import com.tiyuzazhi.component.RoundedImageView;
import com.tiyuzazhi.utils.ImageLoader;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chris.xue
 *         指派选择
 */
public class ExaminerActivity extends Activity {
    private Handler handler;
    private volatile List<Examiner> examiners;
    private ArrayList<Integer> selectedList;
    private ListView examinerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.examiner_layout);
        super.onCreate(savedInstanceState);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        handler = new Handler(Looper.getMainLooper());
        selectedList = new ArrayList<Integer>(10);
        examinerList = (ListView) findViewById(R.id.examSelector);
        View doneButton = findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putIntegerArrayListExtra("examiners", selectedList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        View refreshButton = findViewById(R.id.refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
        init();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                examiners = UserApi.getExaminer(0, 0, 10);
                if (!examiners.isEmpty()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            examinerList.setAdapter(new ExaminerAdaptor(examiners));
                        }
                    });
                } else {
                    ToastUtils.show("没有更多审查员");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class ExaminerAdaptor extends BaseAdapter {
        private List<Examiner> examiners;

        private ExaminerAdaptor(List<Examiner> examiners) {
            this.examiners = examiners;
        }

        @Override
        public int getCount() {
            return examiners.size();
        }

        @Override
        public Object getItem(int position) {
            return examiners.get(position);
        }

        @Override
        public long getItemId(int position) {
            return examiners.get(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final AdaptorHelper helper;
            if (view == null || view.getTag() == null) {
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.examiner_list_item, null, false);
                helper = new AdaptorHelper();
                helper.name = (TextView) view.findViewById(R.id.name);
                helper.header = (RoundedImageView) view.findViewById(R.id.header);
                helper.mask = (ImageView) view.findViewById(R.id.headerSelected);
                helper.company = (TextView) view.findViewById(R.id.company);
                helper.address = (TextView) view.findViewById(R.id.address);
                view.setTag(helper);
            } else {
                helper = (AdaptorHelper) view.getTag();
            }
            final Examiner examiner = (Examiner) getItem(position);
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (helper.mask.getVisibility() != View.VISIBLE) {
                        helper.mask.setVisibility(View.VISIBLE);
                        selectedList.add(examiner.getId());
                    } else {
                        helper.mask.setVisibility(View.GONE);
                        selectedList.remove(examiner.getId());
                    }
                }
            });
            helper.name.setText(examiner.getName());
            TPool.post(new Runnable() {
                @Override
                public void run() {
                    ImageLoader.loadPic(examiner.getIconPath(), new ImageLoader.ImageLoaderCallback() {
                        @Override
                        public void finish(Bitmap image) {
                            helper.header.setImageBitmap(image);
                        }

                        @Override
                        public void error(Exception e) {
                            Log.e("ExaminerActivity", "Load header failed", e);
                        }
                    });
                }
            });
            helper.company.setText(examiner.getCompany());
            helper.address.setText(examiner.getAddress());
            return view;
        }
    }


    private class AdaptorHelper {
        private TextView name;
        private RoundedImageView header;
        private ImageView mask;
        private TextView company;
        private TextView address;
    }

}

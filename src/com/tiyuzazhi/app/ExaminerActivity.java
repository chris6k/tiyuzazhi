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
import android.widget.*;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.beans.Examiner;
import com.tiyuzazhi.component.RoundedImageView;
import com.tiyuzazhi.enums.ExaminerType;
import com.tiyuzazhi.utils.ImageLoader;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chris.xue
 *         指派选择
 */
public class ExaminerActivity extends Activity {
    private Handler handler;
    private volatile List<Examiner> examiners;
    private ArrayList<Integer> selectedList;
    private ListView examinerList;
    private Spinner spinner;
    private String[] typeNames = {"全部", ExaminerType.RECOMM.getName(), ExaminerType.PSY.getName(), ExaminerType.EDU.getName()};
    private int[] typeCodes = {-1, ExaminerType.RECOMM.getCode(), ExaminerType.PSY.getCode(), ExaminerType.EDU.getCode()};
    private View filter;

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
        spinner = (Spinner) findViewById(R.id.examinerTypeSpinner);
        SpinnerAdapter adapter = new ArrayAdapter<String>(ExaminerActivity.this,
                android.R.layout.simple_spinner_item, typeNames);
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
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Examiner> filtered = filterByType(typeCodes[position]);
                examinerList.setAdapter(buildAdaptor(filtered));
                ((TextView) filter).setText(typeNames[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        filter = findViewById(R.id.examiner_category);
        View refreshButton = findViewById(R.id.refresh);
        View cateSelector = findViewById(R.id.cateSelector);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
        cateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
        init();
    }

    private List<Examiner> filterByType(int typeCode) {
        if (typeCode == -1) {
            return examiners;
        }
        List<Examiner> filtered = new ArrayList<Examiner>(examiners.size());
        for (Examiner article : examiners) {
            if (article.getType() == typeCode) {
                filtered.add(article);
            }
        }
        return filtered;
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
                            examinerList.setAdapter(buildAdaptor(examiners));
                        }
                    });
                } else {
                    ToastUtils.show("没有更多审查员");
                }
            }
        });
    }

    private ExaminerAdaptor buildAdaptor(List<Examiner> examinerList) {
        Map<String, List<Examiner>> examinerMap =
                new HashMap<String, List<Examiner>>(examinerList.size());
        List<Examiner> itemList;
        for (Examiner examiner : examinerList) {
            String key = ExaminerType.findByCode(examiner.getType()).getName();
            itemList = examinerMap.get(key);
            if (itemList == null) {
                itemList = new ArrayList<Examiner>(20);
                examinerMap.put(key, itemList);
            }
            itemList.add(examiner);
        }
        return new ExaminerAdaptor(examinerMap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class ExaminerAdaptor extends BaseAdapter {
        private Map<String, List<Examiner>> examinerMap;
        private List<Object> items;

        private ExaminerAdaptor(Map<String, List<Examiner>> examiners) {
            examinerMap = examiners;
            items = new ArrayList<Object>(20);
            for (Map.Entry<String, List<Examiner>> entry : examinerMap.entrySet()) {
                items.add(entry.getKey());
                items.addAll(entry.getValue());
            }
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
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
            final Object item = getItem(position);
            if (item instanceof Examiner) {
                final Examiner examiner = (Examiner) item;
                view.setClickable(true);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (helper.mask.getVisibility() != View.VISIBLE) {
                            helper.mask.setVisibility(View.VISIBLE);
                            selectedList.add(Integer.valueOf(examiner.getId()));
                        } else {
                            helper.mask.setVisibility(View.GONE);
                            selectedList.remove(Integer.valueOf(examiner.getId()));
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
            } else if (item instanceof String) {
                view = LayoutInflater.from(ExaminerActivity.this).inflate(R.layout.examiner_cate_item, null, false);
                TextView typeText = (TextView) view.findViewById(R.id.cateName);
                typeText.setText((String) item);
            }
            return view;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return !(items.get(position) instanceof String);
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

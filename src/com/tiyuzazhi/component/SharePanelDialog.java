package com.tiyuzazhi.component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.tiyuzazhi.app.R;
import com.tiyuzazhi.utils.ShareUtils;

/**
 * @author chris.xue
 *         分享选择框对话框
 */
public abstract class SharePanelDialog extends Dialog {
    View buttonShare;
    GridView grid;

    public SharePanelDialog(Context context) {
        super(context);
    }

    public SharePanelDialog(Context context, int theme) {
        super(context, theme);
    }

    protected SharePanelDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.share_dialog);
        grid = (GridView) findViewById(R.id.shareGrid);
        grid.setNumColumns(3);
        grid.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 6;
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
                view = LayoutInflater.from(getContext()).inflate(R.layout.share_panel_item, null, false);
                TextView name = (TextView) view.findViewById(R.id.sharePlatformName);
                name.setText(ShareUtils.iconName[i]);
                return view;
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onButtonClick(i);
            }
        });
    }

    public abstract void onButtonClick(int platformId);

}

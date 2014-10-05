package com.tiyuzazhi.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import com.tiyuzazhi.app.R;

/**
 * @author chris.xue
 */
public class MagazineItem extends LinearLayout implements Checkable {
    boolean isChecked = false;
    int baseColor;
    View view;

    public MagazineItem(Context context) {
        super(context);
        init(context);
    }

    public MagazineItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MagazineItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setBaseColor(int color) {
        baseColor = color;
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.magazine_list_item, this);
    }

    @Override
    public void setChecked(boolean b) {
        if (b) {
            view.setBackgroundColor(getResources().getColor(R.color.orange));
        } else {
            view.setBackgroundColor(baseColor);
        }
        isChecked = b;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(isChecked);
    }
}

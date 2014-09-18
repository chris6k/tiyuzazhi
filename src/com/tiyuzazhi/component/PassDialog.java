package com.tiyuzazhi.component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.tiyuzazhi.app.R;

/**
 * @author chris.xue
 *         审核通过对话框
 */
public abstract class PassDialog extends Dialog {
    TextView title;
    View buttonOk;
    EditText comment;

    public PassDialog(Context context) {
        super(context);
    }

    public PassDialog(Context context, int theme) {
        super(context, theme);
    }

    protected PassDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.examine_dialog);
        title = (TextView) findViewById(R.id.examinTitleText);
        comment = (EditText) findViewById(R.id.comment);
        buttonOk = findViewById(R.id.buttonOkText);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(comment.toString());
            }
        });
    }

    public void setText(String text) {
        title.setText(text);
    }

    public abstract void onButtonClick(String comment);

}

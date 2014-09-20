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
    private TextView title;
    private View buttonOk;
    private EditText comment;
    private String titleText = "";
    private String buttonText = "";

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
        title.setText(titleText);
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
        titleText = text;
        if (title != null) {
            title.setText(titleText);
        }
    }

    public void setButtonText(String text) {
        buttonText = text;
        if (buttonOk != null && buttonOk instanceof TextView) {
            ((TextView) buttonOk).setText(buttonText);
        }
    }

    public abstract void onButtonClick(String comment);

}

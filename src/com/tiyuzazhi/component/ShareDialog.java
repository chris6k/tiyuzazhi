package com.tiyuzazhi.component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.tiyuzazhi.app.R;

/**
 * @author chris.xue
 *         分享对话框
 */
public abstract class ShareDialog extends Dialog {
    View buttonShare;
    EditText content;

    public ShareDialog(Context context) {
        super(context);
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
    }

    protected ShareDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.share_dialog);
        content = (EditText) findViewById(R.id.shareContent);
        buttonShare = findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(content.toString());
            }
        });
    }

    public abstract void onButtonClick(String content);

}

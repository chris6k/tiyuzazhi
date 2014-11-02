package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

/**
 * @author chris.xue
 */
public class RegisterOkActivity extends Activity {
    private View finishButton;
    private View cancelButton;
    private Handler handler;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_register_finish);
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        finishButton = findViewById(R.id.user_home);
        cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterOkActivity.this, UserCenterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}

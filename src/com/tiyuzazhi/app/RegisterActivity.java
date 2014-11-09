package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

/**
 * @author chris.xue
 */
public class RegisterActivity extends Activity {
    private EditText username;
    private EditText password1;
    private EditText password2;
    private View registerButton;
    private Handler handler;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_quick_register);
        super.onCreate(savedInstanceState);
        username = (EditText) findViewById(R.id.username);
        password1 = (EditText) findViewById(R.id.passwrod1);
        password2 = (EditText) findViewById(R.id.passwrod2);
        registerButton = findViewById(R.id.register);
        handler = new Handler(Looper.getMainLooper());
        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usr = username.getText().toString();
                final String pwd1 = password1.getText().toString();
                String pwd2 = password2.getText().toString();
                if (TextUtils.isEmpty(usr) || TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
                    ToastUtils.show("用户名或密码不能为空");
                    return;
                } else if (!TextUtils.equals(pwd1, pwd2)) {
                    ToastUtils.show("两次输入的密码不同");
                    return;
                } else if (pwd1.length() > 14 || pwd1.length() < 6) {
                    ToastUtils.show("密码需大于6位小于14位");
                    return;
                }
                TPool.post(new Runnable() {
                    @Override
                    public void run() {
                        boolean succ = UserApi.register(usr, pwd1);
                        if (succ) {
                            UserApi.login(usr, pwd1);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(RegisterActivity.this, RegisterOkActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

}

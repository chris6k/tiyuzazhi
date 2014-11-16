package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.beans.User;
import com.tiyuzazhi.component.RoundedImageView;
import com.tiyuzazhi.utils.LocalUtils;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

/**
 * @author kun
 */
public class LoginActivity extends Activity {

    private EditText username;
    private EditText password;
    private View loginButton;
    private View registerButton;
    private Handler handler;
    private RoundedImageView header;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_login);
        super.onCreate(savedInstanceState);
        header = (RoundedImageView) findViewById(R.id.header);
        header.setImageResource(R.drawable.header);
        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (password.hasFocus()) {
                    imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                } else {
                    imm.hideSoftInputFromWindow(username.getWindowToken(), 0);
                }
                finish();
            }
        });
        handler = new Handler(Looper.getMainLooper());
        username = (EditText) findViewById(R.id.username);
        username.setText(LocalUtils.get("cacheLoginName", ""));
        password = (EditText) findViewById(R.id.password);
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (username.hasFocus()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                }
            }
        });
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usr = username.getText().toString();
                final String pwd = password.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (password.hasFocus()) {
                    imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                } else {
                    imm.hideSoftInputFromWindow(username.getWindowToken(), 0);
                }
                if (TextUtils.isEmpty(usr) || TextUtils.isEmpty(pwd)) {
                    ToastUtils.show("用户名或密码不能为空");
                    return;
                }
                LocalUtils.put("cacheLoginName", username.getText().toString());
                ToastUtils.show("正在登录，请稍候……");
                TPool.post(new Runnable() {
                    @Override
                    public void run() {
                        final User user = UserApi.login(usr, pwd);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                if (user != null) {
//                                    Intent i = new Intent();
//                                    i.putExtra("userId", user.getId());
//                                    setResult(Activity.RESULT_OK, i);
                                    finish();
                                }
                            }
                        });
                    }
                });
            }
        });
        registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

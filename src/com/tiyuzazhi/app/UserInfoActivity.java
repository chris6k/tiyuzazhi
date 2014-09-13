package com.tiyuzazhi.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.beans.User;
import com.tiyuzazhi.component.RoundedImageView;
import com.tiyuzazhi.utils.ImageLoader;
import com.tiyuzazhi.utils.TPool;
import com.tiyuzazhi.utils.ToastUtils;

/**
 * @author chris.xue
 */
public class UserInfoActivity extends Activity {
    private Handler handler;
    private int normalColor;
    private int unsetColor;

    private RoundedImageView header;
    private TextView email;
    private TextView company;
    private TextView mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_center);
        super.onCreate(savedInstanceState);
        View back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        handler = new Handler(Looper.getMainLooper());
        header = (RoundedImageView) findViewById(R.id.header);
        unsetColor = Color.parseColor("#abadae");
        normalColor = Color.parseColor("#00367e");
        email = (TextView) findViewById(R.id.email);
        company = (TextView) findViewById(R.id.company);
        mobile = (TextView) findViewById(R.id.mobile);
        Button logoutButton = (Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("正在退出...");
                TPool.post(new Runnable() {
                    @Override
                    public void run() {
                        boolean success = UserApi.logout();
                        if (success) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });
                        } else {
                            ToastUtils.show("退出账户失败，请稍候重试");
                        }
                    }
                });
            }
        });
        init();
    }

    private void init() {
        TPool.post(new Runnable() {
            @Override
            public void run() {
                final User user = UserApi.getUserInfo();
                if (user != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(user.getEmail())) {
                                email.setText("未设置");
                                email.setTextColor(unsetColor);
                            } else {
                                email.setText(user.getEmail());
                                email.setTextColor(normalColor);
                            }
                            if (TextUtils.isEmpty(user.getCompany())) {
                                company.setText("未设置");
                                company.setTextColor(unsetColor);
                            } else {
                                company.setText(user.getCompany());
                                company.setTextColor(normalColor);
                            }
                            if (TextUtils.isEmpty(user.getMobile())) {
                                mobile.setText("未设置");
                                mobile.setTextColor(unsetColor);
                            } else {
                                mobile.setText(user.getMobile());
                                mobile.setTextColor(normalColor);
                            }
                        }
                    });
                    ImageLoader.loadPic(user.getIconPath(), new ImageLoader.ImageLoaderCallback() {
                        @Override
                        public void finish(Bitmap image) {
                            header.setImageBitmap(image);
                        }

                        @Override
                        public void error(Exception e) {
                            Log.e("UserInfoActivity", "Load header failed", e);
                        }
                    });
                } else {
                    ToastUtils.show("未能获取到用户信息，请稍候重试");
                }
            }
        });


    }
}

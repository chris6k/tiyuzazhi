package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.beans.User;
import com.tiyuzazhi.component.RoundedImageView;
import com.tiyuzazhi.utils.ImageLoader;
import com.tiyuzazhi.utils.TPool;

/**
 * @author chris.xue
 *         用户界面
 */
public class UserCenterActivity extends Activity {
    private Handler handler;

    private View userInfoPanel;
    private View unloginPanel;

    private RoundedImageView header;
    private RoundedImageView icon;
    private TextView name;
    private TextView company;
    private TextView address;

    private View loginButton;
    private View regButton;

    private View myFav;
    private View myMsg;

    private TextView myFavText;
    private TextView myMsgText;

    private View notifyBoard;
    private View shouldKnown;
    private View contactUs;
    private View settings;


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

        userInfoPanel = findViewById(R.id.userInfoPanel);
        userInfoPanel.setClickable(true);
        unloginPanel = findViewById(R.id.unlogin_panel);
        header = (RoundedImageView) findViewById(R.id.header);
        icon = (RoundedImageView) findViewById(R.id.icon);
        name = (TextView) findViewById(R.id.name);
        company = (TextView) findViewById(R.id.company);
        address = (TextView) findViewById(R.id.address);

        loginButton = findViewById(R.id.buttonLogin);
        regButton = findViewById(R.id.buttonReg);

        myFav = findViewById(R.id.my_fav);
        myMsg = findViewById(R.id.my_msg);
        myFavText = (TextView) findViewById(R.id.myFavText);
        myMsgText = (TextView) findViewById(R.id.myMsgText);

        notifyBoard = findViewById(R.id.notifyBoard);
        shouldKnown = findViewById(R.id.shoudKnown);
        contactUs = findViewById(R.id.contactUs);
        settings = findViewById(R.id.settings);

        notifyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserCenterActivity.this, NoticeActivity.class);
                startActivity(i);
            }
        });

        shouldKnown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserCenterActivity.this, TGYQActivity.class);
                startActivity(i);
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 010-87182592"));
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        myFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        myMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void init() {
        if (UserApi.loginRole() == 0) {
            icon.setImageDrawable(getResources().getDrawable(R.drawable.header));
            unloginPanel.setVisibility(View.VISIBLE);
            userInfoPanel.setVisibility(View.GONE);
            myFav.setVisibility(View.GONE);
            myMsg.setVisibility(View.GONE);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(i, 1);
                    finish();
                }
            });
            regButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserCenterActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            unloginPanel.setVisibility(View.GONE);
            userInfoPanel.setVisibility(View.VISIBLE);
            myFav.setVisibility(View.VISIBLE);
            myMsg.setVisibility(View.VISIBLE);
            //已登录用户则查询信息
            TPool.post(new Runnable() {
                @Override
                public void run() {
                    final User user = UserApi.getUserInfo();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (user == null) return;
                            name.setText(user.getName());
                            userInfoPanel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(UserCenterActivity.this, UserInfoActivity.class);
                                    intent.putExtra("userId", user.getId());
                                    startActivityForResult(intent, 1);
                                }
                            });
                            if (!TextUtils.isEmpty(user.getCompany()) &&
                                    !TextUtils.equals("null", user.getCompany().toLowerCase())) {
                                company.setText(user.getCompany());
                            }
                            if (!TextUtils.isEmpty(user.getAddress()) &&
                                    !TextUtils.equals("null", user.getAddress().toLowerCase())) {
                                address.setText(user.getAddress());
                            }
                            myFavText.setText("我的收藏（" + user.getFavCount() + "）");
                            myMsgText.setText("我的消息（" + user.getMsgCount() + "）");
                        }
                    });
                    ImageLoader.loadPic(user.getIconPath(), new ImageLoader.ImageLoaderCallback() {
                        @Override
                        public void finish(Bitmap image) {
                            header.setImageBitmap(image);
                        }

                        @Override
                        public void error(Exception e) {
                            Log.e("UserCenterActivity", "Load header failed", e);
                        }
                    });
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            init();
        }
    }
}

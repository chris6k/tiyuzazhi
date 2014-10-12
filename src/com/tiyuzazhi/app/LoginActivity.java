package com.tiyuzazhi.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import com.tiyuzazhi.api.UserApi;
import com.tiyuzazhi.beans.User;
import com.tiyuzazhi.utils.TPool;

/**
 * @author kun
 */
public class LoginActivity extends Activity {

    private EditText username;
    private EditText password;
    private View loginButton;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_login);
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usr = username.getText().toString();
                final String pwd = password.getText().toString();
                TPool.post(new Runnable() {
                    @Override
                    public void run() {
                        final User user = UserApi.login(usr, pwd);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                if (user != null) {
                                    Intent i = new Intent();
                                    i.putExtra("userId", user.getId());
                                    setResult(Activity.RESULT_OK, i);
                                    finish();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}

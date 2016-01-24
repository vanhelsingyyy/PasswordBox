package com.vanhely.passwordbox.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.config.BoxAppliction;
import com.vanhely.passwordbox.config.Config;
import com.vanhely.passwordbox.ui.base.BaseActivity;
import com.vanhely.passwordbox.widget.ProtectbView;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/1/19 0019.
 *
 */
public class PasswordProtectActivity extends BaseActivity {

    private static final String TAG = "PasswordProtectActivity";
    private RelativeLayout rlProtect;
    private ProtectbView protectbView;
    private String setPassword;
    private String confirmPassword;
    private boolean firstProtect;
    private String settingname;


    @Override
    public int initContentView() {
        return R.layout.activity_protect;
    }

    @Override
    public void initViewId() {
        rlProtect = (RelativeLayout) findViewById(R.id.rl_protect);
        protectbView = new ProtectbView(this);
        protectbView.setInfoText("");
        rlProtect.addView(protectbView);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        settingname = intent.getStringExtra("settingname");

        firstProtect = sp.getBoolean(Config.firstProtect, true);
        if (!firstProtect) {
            protectbView.setTitle("输入密码");
        }
    }

    @Override
    public void initListen() {
        protectbView.setDotCountListener(new ProtectbView.onClickNumberListener() {
            @Override
            public void onClick(int length, String password) {
                if (firstProtect) {
                    String title = protectbView.getTitle();
                    if (length == 4 && title.equals("设置密码")) {
                        setPassword = password;
                        Log.i(TAG,"setPassword"+setPassword);
                        protectbView.setTitle("确认密码");
                        protectbView.clearAllPassword();
                    }
                    if (length == 4 && title.equals("确认密码")) {
                        confirmPassword = password;
                        if (setPassword.equals(confirmPassword)) {
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString(Config.protectPassword, confirmPassword).apply();
                            edit.putBoolean(Config.firstProtect, false).apply();
                            Log.i(TAG, "confirmPassword" + confirmPassword);
                            EventBus.getDefault().post(settingname);
                            finish();
                        } else {
                            Toast.makeText(BoxAppliction.getmContext(),"请重新输入", Toast.LENGTH_SHORT).show();
                            protectbView.clearAllPassword();
                        }
                    }
                } else {
                    if (length == 4) {
                        String inputPassword = sp.getString(Config.protectPassword, "");
                        if (password.equals(inputPassword)) {
                            sp.edit().putBoolean(Config.needProtect,false).apply();
                            Intent intent = new Intent(PasswordProtectActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(BoxAppliction.getmContext(),"密码错误请重新输入",Toast.LENGTH_SHORT).show();
                            protectbView.clearAllPassword();
                        }
                    }

                }
            }

            @Override
            public void onInfoClick() {

            }
        });
    }



}




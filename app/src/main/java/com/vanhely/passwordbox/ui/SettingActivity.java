package com.vanhely.passwordbox.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.config.BoxAppliction;
import com.vanhely.passwordbox.config.Config;
import com.vanhely.passwordbox.model.PasswordBean;
import com.vanhely.passwordbox.ui.base.BaseActivity;
import com.vanhely.passwordbox.widget.SwitchImageView;

import org.litepal.crud.DataSupport;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/1/18 0018.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {


    private boolean protect;
    public SwitchImageView ivProtect;
    private RelativeLayout rlResetContent;
    private RelativeLayout rlProtect;
    private RelativeLayout rlResetPassword;

    @Override
    public int initContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViewId() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolName = (TextView) findViewById(R.id.tool_name);
        rlResetContent = (RelativeLayout) findViewById(R.id.rl_resetcontent);
        rlProtect = (RelativeLayout) findViewById(R.id.rl_protect);
        rlResetPassword = (RelativeLayout) findViewById(R.id.rl_resetPassword);
        ivProtect = (SwitchImageView) findViewById(R.id.iv_protect);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);


        toolName.setText("设置");

    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        protect = sp.getBoolean(Config.protectState, false);
        ivProtect.setImageViewStatus(protect);
    }

    @Override
    public void initListen() {
        rlResetContent.setOnClickListener(this);
        rlProtect.setOnClickListener(this);
        rlResetPassword.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(String str) {

        if ("setPassword".equals(str)) {
            ivProtect.changeimageViewStatus();
            sp.edit().putBoolean(Config.protectState, true).apply();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_resetcontent:
                resetContent();
                break;
            case R.id.rl_protect:
                setPasswordProtect();
                break;
            case R.id.rl_resetPassword:
                resetPassword();
                break;
        }
    }


    private void resetPassword() {
        String password = sp.getString(Config.protectPassword, "");
        if (!TextUtils.isEmpty(password)) {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("重置密码");
            ad.setMessage("是否重新设置密码");
            ad.setNegativeButton("重置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean(Config.firstProtect, true).apply();
                    Intent intent = new Intent(SettingActivity.this, PasswordProtectActivity.class);
                    intent.putExtra("settingname", "resetPassword");
                    startActivity(intent);
                }
            });
            ad.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = ad.create();
            dialog.show();
        } else {
            Toast.makeText(BoxAppliction.getmContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
        }
    }


    private void resetContent() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("重置内容");
        ad.setMessage("警告: 重置将清空所有内容");
        ad.setNegativeButton("重置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataSupport.deleteAll(PasswordBean.class);
                SplashActivity.setDataBase();
                EventBus.getDefault().post("resetContent");
            }
        });
        ad.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = ad.create();
        dialog.show();
    }

    private void setPasswordProtect() {
        boolean imageViewStatus = ivProtect.getImageViewStatus();
        if (imageViewStatus) {
            ivProtect.changeimageViewStatus();
            sp.edit().putBoolean(Config.protectState, false).apply();
        } else {

            boolean fistProtect = sp.getBoolean(Config.firstProtect, true);
            if (fistProtect) {
                Intent intent = new Intent(SettingActivity.this, PasswordProtectActivity.class);
                intent.putExtra("settingname", "setPassword");
                startActivity(intent);
                Toast.makeText(BoxAppliction.getmContext(), "密码保护开启", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BoxAppliction.getmContext(), "密码保护开启", Toast.LENGTH_SHORT).show();
                ivProtect.changeimageViewStatus();
                sp.edit().putBoolean(Config.protectState, true).apply();
            }

        }
    }

}

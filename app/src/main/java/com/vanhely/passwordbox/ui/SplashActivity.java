package com.vanhely.passwordbox.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.config.Config;
import com.vanhely.passwordbox.model.PasswordBean;
import com.vanhely.passwordbox.ui.base.BaseActivity;

import org.litepal.tablemanager.Connector;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/8 0008.
 */
public class SplashActivity extends BaseActivity implements View.OnClickListener {


    private String time;

    @Override
    public int initContentView() {

        return R.layout.activity_splash;

    }


    @Override
    public void initViewId() {
        TextView skip = (TextView) findViewById(R.id.skip);
        skip.setOnClickListener(this);

    }

    @Override
    public void initData() {
        boolean splashStaus = sp.getBoolean(Config.splashStaus, true);
        if (splashStaus) {
            Date date = new Date();
            time = String.format("%tF", date) + " " + String.format("%tT", date);
            Connector.getDatabase();
            saveGameData();
            saveToolData();
            saveSocialData();


        } else {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


    }


    @Override
    public void initListen() {

    }


    private void saveGameData() {

        for (int i = 0; i < Config.gameCount; i++) {

            PasswordBean passwordBean = new PasswordBean();
            passwordBean.setTitle(Config.gameNames[i]);
            passwordBean.setDesc(Config.desc);
            passwordBean.setPassword("");
            passwordBean.setUsername("");
            passwordBean.setImage("1");
            passwordBean.setType("game");
            passwordBean.setSaveTime(time);
            passwordBean.save();

        }
    }

    private void saveToolData() {

        for (int i = 0; i < Config.toolCount; i++) {
            PasswordBean passwordBean = new PasswordBean();
            passwordBean.setTitle(Config.toolNames[i]);
            passwordBean.setDesc(Config.desc);
            passwordBean.setPassword("");
            passwordBean.setUsername("");
            passwordBean.setImage("1");
            passwordBean.setType("tool");
            passwordBean.setSaveTime(time);
            passwordBean.save();
        }

    }

    private void saveSocialData() {

        for (int i = 0; i < Config.socialCount; i++) {
            PasswordBean passwordBean = new PasswordBean();
            passwordBean.setTitle(Config.socialNames[i]);
            passwordBean.setDesc(Config.desc);
            passwordBean.setPassword("");
            passwordBean.setUsername("");
            passwordBean.setImage("1");
            passwordBean.setType("social");
            passwordBean.setSaveTime(time);
            passwordBean.save();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip:
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
        }
    }
}

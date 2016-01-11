package com.vanhely.passwordbox.ui;

import android.content.Intent;

import com.vanhely.passwordbox.config.Config;
import com.vanhely.passwordbox.model.GameBean;
import com.vanhely.passwordbox.model.SocialBean;
import com.vanhely.passwordbox.model.ToolBean;
import com.vanhely.passwordbox.ui.base.BaseActivity;
import com.vanhely.passwordbox.R;

import org.litepal.tablemanager.Connector;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/8 0008.
 */
public class SplashActivity extends BaseActivity {



    private String time;

    @Override
    public int initContentView() {

        return R.layout.activity_splash;

    }


    @Override
    public void initViewId() {

    }

    @Override
    public void initData() {

        if (Config.splashStaus) {
            Date date = new Date();
            time = String.format("%tF", date) + " " +String.format("%tT", date);
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

            GameBean game = new GameBean();
            game.setLid(i);
            game.setTitle(Config.gameNames[i]);
            game.setUsername("");
            game.setPassword("");
            game.setSaveTime(time);
            game.setDesc(Config.desc);
            game.save();
        }
    }

    private void saveToolData() {

        for (int i = 0; i < Config.toolCount; i++) {
            ToolBean tool = new ToolBean();
            tool.setLid(i);
            tool.setTitle(Config.toolNames[i]);
            tool.setUsername("");
            tool.setPassword("");
            tool.setSaveTime(time);
            tool.setDesc(Config.desc);
            tool.save();
        }

    }

    private void saveSocialData() {

        for (int i = 0; i < Config.socialCount; i++) {
            SocialBean social = new SocialBean();
            social.setLid(i);
            social.setTitle(Config.socialNames[i]);
            social.setUsername("");
            social.setPassword("");
            social.setSaveTime(time);
            social.setDesc(Config.desc);
            social.save();
        }
    }

}

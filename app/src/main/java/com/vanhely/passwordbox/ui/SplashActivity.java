package com.vanhely.passwordbox.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.config.Config;
import com.vanhely.passwordbox.model.PasswordBean;
import com.vanhely.passwordbox.ui.base.BaseActivity;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/8 0008.
 */
public class SplashActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "SplashActivity";
    public static String time;


    @Override
    public int initContentView() {

        return R.layout.activity_splash;

    }


    @Override
    public void initViewId() {
        AppCompatButton skip = (AppCompatButton) findViewById(R.id.skip);
        skip.setOnClickListener(this);
    }

    @Override
    public void initData() {
        boolean splashStaus = sp.getBoolean(Config.splashStaus, true);
        boolean protectState = sp.getBoolean(Config.protectState, false);
        boolean needProtect = sp.getBoolean(Config.needProtect, true);
        Log.i(TAG, protectState + "");


        if (splashStaus) {
            setDataBase();


        } else {
            boolean updata = sp.getBoolean("updata", true);
            if (updata) {
                Connector.getDatabase();
                for (int i = 0; i < Config.gameCount; i++) {
                    ContentValues values = new ContentValues();
                    values.put("imageId", Config.gameImages[i]);
                    DataSupport.updateAll(PasswordBean.class, values, "image = ? and title = ?", "1", Config.gameNames[i]);
                }

                for (int i = 0; i < Config.toolCount; i++) {
                    ContentValues values = new ContentValues();
                    values.put("imageId", Config.toolImages[i]);
                    DataSupport.updateAll(PasswordBean.class, values, "image = ? and title = ?", "1", Config.toolNames[i]);
                }

                for (int i = 0; i < Config.socialCount; i++) {
                    ContentValues values = new ContentValues();
                    values.put("imageId", Config.socialImages[i]);
                    DataSupport.updateAll(PasswordBean.class, values, "image = ? and title = ?", "1", Config.socialNames[i]);
                }
            }

            if (protectState) {
                if (needProtect) {
                    Intent intent = new Intent(SplashActivity.this, PasswordProtectActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            } else {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

        }


    }

    public static void setDataBase() {
        Date date = new Date();
        time = String.format("%tF", date) + " " + String.format("%tT", date);
        Connector.getDatabase();
        saveGameData();
        saveToolData();
        saveSocialData();
    }


    @Override
    public void initListen() {

    }


    private static void saveGameData() {

        for (int i = 0; i < Config.gameCount; i++) {

            PasswordBean passwordBean = new PasswordBean();
            passwordBean.setTitle(Config.gameNames[i]);
            passwordBean.setDesc(Config.desc);
            passwordBean.setPassword("");
            passwordBean.setUsername("");
            passwordBean.setImage("1");
            passwordBean.setImagePath("");
            passwordBean.setImageId(Config.gameImages[i]);
            passwordBean.setType("game");
            passwordBean.setSaveTime(time);
            passwordBean.save();
        }
    }

    private static void saveToolData() {

        for (int i = 0; i < Config.toolCount; i++) {
            PasswordBean passwordBean = new PasswordBean();
            passwordBean.setTitle(Config.toolNames[i]);
            passwordBean.setDesc(Config.desc);
            passwordBean.setPassword("");
            passwordBean.setUsername("");
            passwordBean.setImage("1");
            passwordBean.setImagePath("");
            passwordBean.setImageId(Config.toolImages[i]);
            passwordBean.setType("tool");
            passwordBean.setSaveTime(time);
            passwordBean.save();


        }

    }

    private static void saveSocialData() {

        for (int i = 0; i < Config.socialCount; i++) {
            PasswordBean passwordBean = new PasswordBean();
            passwordBean.setTitle(Config.socialNames[i]);
            passwordBean.setDesc(Config.desc);
            passwordBean.setPassword("");
            passwordBean.setUsername("");
            passwordBean.setImage("1");
            passwordBean.setImagePath("");
            passwordBean.setImageId(Config.socialImages[i]);
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
                break;
        }
    }
}

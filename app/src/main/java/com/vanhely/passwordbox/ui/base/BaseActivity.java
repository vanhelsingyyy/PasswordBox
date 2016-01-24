package com.vanhely.passwordbox.ui.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.vanhely.passwordbox.config.Config;

/**
 * Created by Administrator on 2016/1/8 0008.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(Config.spFileName,MODE_PRIVATE);
        setContentView(initContentView());
        initViewId();
        initData();
        initListen();

    }

    //初始化布局
    public abstract int initContentView();

    //初始化组件id
    public abstract void initViewId();

    //初始化数据
    public abstract void initData();

    //初始化监听
    public abstract void initListen();



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

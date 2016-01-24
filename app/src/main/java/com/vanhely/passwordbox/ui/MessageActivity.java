package com.vanhely.passwordbox.ui;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/1/18 0018.
 */
public class MessageActivity extends BaseActivity {
    @Override
    public int initContentView() {
        return R.layout.activity_message;
    }

    @Override
    public void initViewId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolName = (TextView) findViewById(R.id.tool_name);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolName.setText("反馈");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListen() {

    }
}

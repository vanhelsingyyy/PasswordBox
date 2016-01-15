package com.vanhely.passwordbox.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.model.PasswordBean;
import com.vanhely.passwordbox.ui.base.BaseActivity;
import com.vanhely.passwordbox.utils.Base64Utils;

import org.litepal.crud.DataSupport;

import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/1/13 0013.
 */
public class PaddingDataActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PaddingDataActivity";
    private MaterialEditText dataTitle;
    private MaterialEditText dataUser;
    private MaterialEditText dataKey;
    private AppCompatEditText dataDesc;
    private AppCompatButton dataSave;
    private String time;
    private String type;
    private String startTitle;
    private String startUser;
    private String startKey;
    private String startDesc;
    private String currentTitle;
    private String currentUser;
    private String currentKey;
    private String currentDesc;
    private int id;


    @Override
    public int initContentView() {

        return R.layout.activity_paddingdata;
    }

    @Override
    public void initViewId() {
        TextView appName = (TextView) findViewById(R.id.app_name);
        TextView bwName = (TextView) findViewById(R.id.bw_name);
        dataTitle = (MaterialEditText) findViewById(R.id.data_title);
        dataUser = (MaterialEditText) findViewById(R.id.data_user);
        dataKey = (MaterialEditText) findViewById(R.id.data_key);
        dataDesc = (AppCompatEditText) findViewById(R.id.data_desc);
        dataSave = (AppCompatButton) findViewById(R.id.data_save);
        appName.setVisibility(View.GONE);
        bwName.setVisibility(View.VISIBLE);

        dataSave.setOnClickListener(this);
    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        PasswordBean passwordBean = intent.getParcelableExtra("password");
        if (passwordBean != null) {
            dataTitle.setText(passwordBean.getTitle());
            dataUser.setText(Base64Utils.deToStr(passwordBean.getUsername()));
            dataKey.setText(Base64Utils.deToStr(passwordBean.getPassword()));
            dataDesc.setText(passwordBean.getDesc());
            id = passwordBean.getId();
        }
        getStartData();

    }


    @Override
    public void initListen() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data_save:
                getCurrentData();
                if (startTitle.equals(currentTitle) && startUser.equals(currentUser) && startKey.equals(currentKey) && startDesc.equals(currentDesc)) {
                    finish();
                } else if ((TextUtils.isEmpty(startTitle) && TextUtils.isEmpty(startUser) && TextUtils.isEmpty(startKey) && TextUtils.isEmpty(startDesc)) && (!currentTitle.equals(startTitle) || !currentUser.equals(startUser) || !currentKey.equals(startKey) || !currentDesc.equals(startDesc))) {
                    Date date = new Date();
                    time = String.format("%tF", date) + " " + String.format("%tT", date);
                    PasswordBean bean = new PasswordBean();
                    bean.setTitle(dataTitle.getText().toString().trim());
                    bean.setUsername(Base64Utils.enToStr(dataUser.getText().toString()).trim());
                    bean.setPassword(Base64Utils.enToStr(dataKey.getText().toString()).trim());
                    bean.setDesc(dataDesc.getText().toString().trim());
                    bean.setSaveTime(time);
                    bean.setImage("0");
                    bean.setType(type);
                    bean.save();
                    EventBus.getDefault().post(type);
                    finish();
                } else if (!currentTitle.equals(startTitle) || !currentUser.equals(startUser) || !currentKey.equals(startKey) || !currentDesc.equals(startDesc)) {
                    Date date = new Date();
                    time = String.format("%tF", date) + " " + String.format("%tT", date);
                    ContentValues values = new ContentValues();
                    values.put("title", currentTitle);
                    values.put("username", Base64Utils.enToStr(currentUser));
                    values.put("password", Base64Utils.enToStr(currentKey));
                    values.put("desc", currentDesc);
                    values.put("saveTime", time);
                    DataSupport.update(PasswordBean.class, values, id);
                    EventBus.getDefault().post(type);
                    Log.i(TAG,type);
                    finish();
                }
        }
    }


    private void getStartData() {
        startTitle = dataTitle.getText().toString().trim();
        startUser = dataUser.getText().toString().trim();
        startKey = dataKey.getText().toString().trim();
        startDesc = dataDesc.getText().toString().trim();
        Log.i(TAG, startTitle + startUser + startKey + startDesc);
    }


    private void getCurrentData() {
        currentTitle = dataTitle.getText().toString().trim();
        currentUser = dataUser.getText().toString().trim();
        currentKey = dataKey.getText().toString().trim();
        currentDesc = dataDesc.getText().toString().trim();
    }


}

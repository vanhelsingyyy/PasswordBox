package com.vanhely.passwordbox.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/1/18 0018.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llVersion;
    private TextView tvVersion;
    private LinearLayout llEventBus;
    private LinearLayout llLitePal;
    private LinearLayout llMaterEdite;

    @Override
    public int initContentView() {
        return R.layout.activity_about;
    }

    @Override
    public void initViewId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolName = (TextView) findViewById(R.id.tool_name);
        llVersion = (LinearLayout) findViewById(R.id.ll_version);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        llEventBus = (LinearLayout) findViewById(R.id.ll_eventBus);
        llLitePal = (LinearLayout) findViewById(R.id.ll_litePal);
        llMaterEdite = (LinearLayout) findViewById(R.id.ll_materEdite);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolName.setText("关于");
    }

    @Override
    public void initData() {
        tvVersion.setText("密码盒子"+getVersion());
    }

    @Override
    public void initListen() {
        llEventBus.setOnClickListener(this);
        llLitePal.setOnClickListener(this);
        llMaterEdite.setOnClickListener(this);
        llVersion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_version:
                Uri appInfoUri = Uri.parse("package:"+getPackageName());
                Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,appInfoUri);
                startActivity(intent);
                break;
            case R.id.ll_litePal:
                startWebview("https://github.com/LitePalFramework/LitePal");
                break;
            case R.id.ll_eventBus:
                startWebview("https://github.com/greenrobot/EventBus");
                break;
            case R.id.ll_materEdite:
                startWebview("https://github.com/rengwuxian/MaterialEditText");
                break;
        }
    }

    private void startWebview(String loadUrl) {
        Intent intent = new Intent(AboutActivity.this, GitHubActivity.class);
        intent.putExtra("loadUrl",loadUrl);
        startActivity(intent);

    }

    public String getVersion() {
        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "0";
    }
}

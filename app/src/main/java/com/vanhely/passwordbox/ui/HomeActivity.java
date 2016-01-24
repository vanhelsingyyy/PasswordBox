package com.vanhely.passwordbox.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.adapter.PagerAdapter;
import com.vanhely.passwordbox.config.BoxAppliction;
import com.vanhely.passwordbox.config.Config;
import com.vanhely.passwordbox.ui.base.BaseActivity;
import com.vanhely.passwordbox.ui.fragment.GameFragment;
import com.vanhely.passwordbox.ui.fragment.SocialFragment;
import com.vanhely.passwordbox.ui.fragment.ToolFragment;
import com.vanhely.passwordbox.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/10 0010.
 *
 */
public class HomeActivity extends BaseActivity {

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabStrip;
    private List<Fragment> fragments;
    private NavigationView mNavigationView;
    private TextView toolName;
    private DrawerLayout mDrawerLayout;


    @Override
    public int initContentView() {
        return R.layout.activity_home;
    }

    @Override
    public void initViewId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        toolName = (TextView) findViewById(R.id.tool_name);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow);
        toolbar.setOverflowIcon(drawable);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

    @Override
    public void initData() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Config.splashStaus, false);
        editor.apply();
        boolean firstProtect = sp.getBoolean(Config.firstProtect, true);
        if (firstProtect) {
            Toast.makeText(BoxAppliction.getmContext(),"请在设置中开启密码保护哒!",Toast.LENGTH_LONG).show();
        }

        GameFragment gameFragment = new GameFragment();
        ToolFragment toolFragment = new ToolFragment();
        SocialFragment socialFragment = new SocialFragment();

        fragments = new ArrayList<>();
        fragments.add(gameFragment);
        fragments.add(toolFragment);
        fragments.add(socialFragment);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mNavigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void initListen() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent home = new Intent(BoxAppliction.getmContext(), HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.menu_setting:
                        Intent setting = new Intent(BoxAppliction.getmContext(), SettingActivity.class);
                        startActivity(setting);
                        break;
                    case R.id.menu_share:
                        getShare();

                        break;
                    case R.id.menu_message:
                        Intent message = new Intent(BoxAppliction.getmContext(), MessageActivity.class);
                        startActivity(message);
                        break;
                    case R.id.menu_information:
                        Intent infor = new Intent(BoxAppliction.getmContext(), AboutActivity.class);
                        startActivity(infor);
                        break;
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }


    private void getShare() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "分享");
        share.putExtra(Intent.EXTRA_TEXT, Config.extra_text);
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(share,getTitle()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                getShare();
                break;
            case R.id.action_setting:
                Intent setting = new Intent(BoxAppliction.getmContext(), SettingActivity.class);
                startActivity(setting);
                break;
        }

        return super.onOptionsItemSelected(item);


    }
}

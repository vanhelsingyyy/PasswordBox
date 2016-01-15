package com.vanhely.passwordbox.ui;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.adapter.PagerAdapter;
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
 */
public class HomeActivity extends BaseActivity {

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabStrip;
    private List<Fragment> fragments;

    @Override
    public int initContentView() {
        return R.layout.activity_home;
    }

    @Override
    public void initViewId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_navigate);
        Drawable drawable = getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow);
        toolbar.setOverflowIcon(drawable);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);


    }

    @Override
    public void initData() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Config.splashStaus,false);
        editor.apply();

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
    public void initListen() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


}

package com.vanhely.passwordbox.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vanhely.passwordbox.config.Config;

import java.util.List;

/**
 * Created by Administrator on 2016/1/12 0012.
 *
 */
public class PagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {


        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Config.tabTitle[position];
    }
}

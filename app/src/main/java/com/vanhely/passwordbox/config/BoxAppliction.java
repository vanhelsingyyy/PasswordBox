package com.vanhely.passwordbox.config;

import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
public class BoxAppliction extends LitePalApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getmContext(){
        return mContext;
    }
}

package com.vanhely.passwordbox.config;

import android.content.Context;
import android.content.Intent;

import com.vanhely.passwordbox.service.PasswordProtectService;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2016/1/9 0009.
 *
 */
public class BoxAppliction extends LitePalApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Intent intent = new Intent(mContext, PasswordProtectService.class);
        startService(intent);

    }

    public static Context getmContext(){
        return mContext;
    }
}

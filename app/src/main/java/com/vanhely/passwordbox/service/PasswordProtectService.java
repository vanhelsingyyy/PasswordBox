package com.vanhely.passwordbox.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.vanhely.passwordbox.config.Config;

/**
 * Created by Administrator on 2016/1/20 0020.
 */
public class PasswordProtectService extends Service {


    private ScreenReceiver screenReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        screenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReceiver,filter);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenReceiver);
    }


    class ScreenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            context.getSharedPreferences(Config.spFileName, MODE_PRIVATE)
                    .edit().putBoolean(Config.needProtect, true).apply();

        }
    }
}

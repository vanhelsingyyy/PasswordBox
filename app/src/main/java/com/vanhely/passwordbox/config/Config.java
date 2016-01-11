package com.vanhely.passwordbox.config;

import android.content.res.Resources;

import com.vanhely.passwordbox.R;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
public class Config {

    private static final Resources resources = BoxAppliction.getmContext().getResources();

    public static String spFileName = resources.getString(R.string.sp_filename);
    public static Boolean splashStaus = true;
    public static String desc = resources.getString(R.string.desc);
    public static String[] gameNames = resources.getStringArray(R.array.game_names);
    public static String[] toolNames = resources.getStringArray(R.array.tool_names);
    public static String[] socialNames   = resources.getStringArray(R.array.social_names);
    public static int gameCount = 14;
    public static int toolCount = 20;
    public static int socialCount = 17;

}

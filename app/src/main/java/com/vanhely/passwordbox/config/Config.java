package com.vanhely.passwordbox.config;

import android.content.res.Resources;

import com.vanhely.passwordbox.R;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
public class Config {

    private static final Resources resources = BoxAppliction.getmContext().getResources();

    public static String spFileName = resources.getString(R.string.sp_filename);
    public static String splashStaus = "splashStaus";
    public static String desc = resources.getString(R.string.desc);
    public static String protectState = "protectState";
    public static String firstProtect = "firstProtect";
    public static String protectPassword = "protectPassword";
    public static String needProtect = "needProtect";
    public static String extra_text = resources.getString(R.string.app_desc);
    public static int[] gameImages = {  R.drawable.game_steam, R.drawable.game_psn, R.drawable.game_xbox,
                                        R.drawable.game_battlenet, R.drawable.game_battlenet, R.drawable.game_tencentgame,
                                        R.drawable.game_neteasegame, R.drawable.game_dota, R.drawable.game_jw3,
                                        R.drawable.game_tlbb, R.drawable.game_coc, R.drawable.game_dotalegend,
                                        R.drawable.game_vainglory, R.drawable.game_taiji, R.drawable.game_liewang};

    public static int[] toolImages = {  R.drawable.tool_google, R.drawable.tool_github, R.drawable.tool_stackoverflow,
                                        R.drawable.tool_pocket, R.drawable.tool_kindle, R.drawable.tool_duokan,
                                        R.drawable.tool_jianshu, R.drawable.tool_evernote, R.drawable.tool_thunde,
                                        R.drawable.tool_baiduclund, R.drawable.tool_360clound, R.drawable.tool_115,
                                        R.drawable.tool_jd, R.drawable.tool_tb, R.drawable.tool_amazon,
                                        R.drawable.tool_meituan, R.drawable.tool_xiecheng, R.drawable.tool_firefox, R.drawable.tool_uc, R.drawable.tool_zhifu};

    public static int[] socialImages = {R.drawable.soc_qq, R.drawable.soc_wechat, R.drawable.soc_momo,
                                        R.drawable.soc_whatsapp, R.drawable.soc_telegram, R.drawable.soc_path,
                                        R.drawable.soc_sinaweibo, R.drawable.soc_twitter, R.drawable.soc_tumblr,
                                        R.drawable.soc_facebook, R.drawable.soc_zhihu, R.drawable.soc_quora,
                                        R.drawable.soc_douban, R.drawable.soc_reddit, R.drawable.soc_coolmarket,
                                        R.drawable.soc_bilibili, R.drawable.soc_acfun};
    public static String[] gameNames = resources.getStringArray(R.array.game_names);
    public static String[] toolNames = resources.getStringArray(R.array.tool_names);
    public static String[] socialNames = resources.getStringArray(R.array.social_names);
    public static String[] tabTitle = resources.getStringArray(R.array.tab_title);
    public static int gameCount = 15;
    public static int toolCount = 20;
    public static int socialCount = 17;

}

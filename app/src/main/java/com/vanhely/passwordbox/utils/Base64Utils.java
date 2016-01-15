package com.vanhely.passwordbox.utils;

import android.util.Base64;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public class Base64Utils {

    public static String enToStr(String str) {
        String enStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        return enStr;
    }

    public static String deToStr(String str) {
        String deStr = new String(Base64.decode(str.getBytes(),Base64.DEFAULT));
        return deStr;
    }
}

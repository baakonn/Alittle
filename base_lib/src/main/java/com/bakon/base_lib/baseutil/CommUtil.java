package com.bakon.base_lib.baseutil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

/**
 * author: bakon(762713299@qq.com)
 * date: 2016-11-7
 */
public class CommUtil {
    /**
     * 在Android应用程序中来判断当前应用是否处于debug状态来做一些操作
     */
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }





    /***************String 相关 start***********************/

    public static String processStr(String str) {
        if (str == null) {
            return "";
        }
        return str.replaceAll("null", "").trim();
    }

    public static String processStr(String str, String defult) {
        if (TextUtils.isEmpty(str) || "null".equals(str) || "null".equals(str.trim())) {
            return defult;
        } else {
            return str.trim();
        }
    }

    /***************String 相关 end***********************/

}

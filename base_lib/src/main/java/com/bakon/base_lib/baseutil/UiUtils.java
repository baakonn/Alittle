package com.bakon.base_lib.baseutil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.bakon.base_lib.base.BaseApplication;


public class UiUtils {

    public static Context getContext() {
        return BaseApplication.getInstance();
    }


    public static Resources getResource() {
        return getContext().getResources();
    }

    /******
     * 获取资源颜色
     * @param color
     * @return
     * liukui 2017/06/23 10:38
     */
    public static int getColor(int color){
        return getResource().getColor(color);
    }

    /*****
     * 获取本地资源文件
     * @param resources
     * @return
     *
     * liukui 2017/06/23 10:38
     *
     */
    public static Drawable getDrawable(int resources){
        return getResource().getDrawable(resources);
    }

    /*****
     * 格式化字符串
     * @param strRes
     * @param str
     * @return
     *
     * liukui 2017/06/23 10:38
     *
     */
    public static String getFormatString(int strRes , Object str){
        return String.format(getResource().getString(strRes), str);
    }


    /**
     * 根据百分比改变颜色透明度
     */
    public static int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

}

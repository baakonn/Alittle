package com.bakon.base_lib.baseutil;

import android.util.Log;

import com.bakon.base_lib.BuildConfig;
import com.bakon.base_lib.base.BaseApplication;


public class LogUtil {
    //BuildConfig.LOG_DEBUG && LEVEL <= VERBOSE
    //根据是否debug模式，和等级显示log
    private static final String TAG = BaseApplication.getInstance().getPackageName();

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int LEVEL = VERBOSE;//打印所有等级的信息

    //public static final int LEVEL = NOTHING;//关闭所有等级的信息

    public static void d(String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= VERBOSE) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }

}

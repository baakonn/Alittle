package com.bakon.base_lib.baseutil;

import android.util.Log;

import com.bakon.base_lib.BuildConfig;
import com.bakon.base_lib.base.BaseApplication;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class LogUtil {
    //BuildConfig.LOG_DEBUG && LEVEL <= VERBOSE
    //根据是否debug模式，和等级显示log
    private static final String TAG = LogUtil.class.getSimpleName();

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int LEVEL = VERBOSE;//打印所有等级的信息
    //public  final int LEVEL = NOTHING;//关闭所有等级的信息

    @Inject
    public LogUtil() {
    }

    public static void d(String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= VERBOSE) {
            Log.d(TAG, msg);
        }
    }

    public void d(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public void v(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public void i(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public void w(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public void e(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG && LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }

}

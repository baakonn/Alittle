package com.bakon.alittle.application;

import com.bakon.base_lib.base.BaseApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //OkHttpUtils 初始化
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //log   tag:okhttputils
                .addInterceptor(new LoggerInterceptor("http"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}

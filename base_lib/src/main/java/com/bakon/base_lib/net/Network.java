package com.bakon.base_lib.net;

import com.bakon.base_lib.BuildConfig;
import com.bakon.base_lib.baseutil.Contant;
import com.bakon.base_lib.baseutil.NetUtil;
import com.bakon.base_lib.baseutil.SystemUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络操作类
 */

//https://github.com/lygttpod/RxHttpUtils

public class Network {
    public static String API_BASE_URL = "http://gank.io/api/";

    private static OkHttpClient okHttpClient = null;
    private static Retrofit retrofit;
    private static Network network;

    //retrofit，创建不同的api
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            network = new Network();
        }
        return retrofit;
    }

    //初始化
    public Network() {
        initOkHttp();
        initRetrofit();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(Contant.NET_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);//50M
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetWorkAviliable()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                Response.Builder newBuilder = response.newBuilder();
                if (NetUtil.isNetWorkAviliable()) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    newBuilder.header("Cache-Control", "public, max-age=" + maxAge);
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
                }

                return newBuilder.build();
            }
        };
        //缓存设置
        builder.cache(cache).addInterceptor(cacheInterceptor);
        //log设置
        if (BuildConfig.LOG_DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        //公共参数
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                //String method = originalRequest.method();
                //Headers headers = originalRequest.headers();
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        .addQueryParameter("platform", "android")
                        .addQueryParameter("version", SystemUtil.getVersionCode() + "")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        builder.addInterceptor(addQueryParameterInterceptor);
        //设置公共header
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("AppType", "TPOS")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        //设置头
        builder.addInterceptor(headerInterceptor);

        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    //初始化retrofit
    private static void initRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

}

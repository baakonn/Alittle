package com.bakon.base_lib.di.component;

import com.bakon.base_lib.base.AppManager;
import com.bakon.base_lib.base.BaseApplication;
import com.bakon.base_lib.baseutil.LogUtil;
import com.bakon.base_lib.di.module.AppModule;
import com.bakon.base_lib.di.module.NetworkModule;
import com.bakon.base_lib.net.HttpAPI;
import com.bakon.base_lib.net.imageloader.ImageLoader;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 统一的管理器
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    BaseApplication getApplication();

//    //用于管理网络请求层,以及数据缓存层
//    IRepositoryManager repositoryManager();
//    //Rxjava错误处理管理类
//    RxErrorHandler rxErrorHandler();

    //OkHttpClient
    OkHttpClient getOkHttpClient();

    //Retrofit
    Retrofit getRetrofit();

    //具体的api
    HttpAPI getHttpApi();

    //图片管理器,用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    ImageLoader imageLoader();

    //用于管理所有activity
    AppManager appManager();

    //用于管理所有activity
    LogUtil logUtil();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Map<String, Object> getSession();

//    void inject(AppDelegate delegate);
}

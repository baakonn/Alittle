package com.bakon.base_lib.di.component;

import com.bakon.base_lib.base.BaseApplication;
import com.bakon.base_lib.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    BaseApplication getApplication();

//    //用于管理网络请求层,以及数据缓存层
//    IRepositoryManager repositoryManager();
//
//    //Rxjava错误处理管理类
//    RxErrorHandler rxErrorHandler();
//

//    OkHttpClient okHttpClient();

//    //图片管理器,用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
//    ImageLoader imageLoader();

    //gson
//    Gson gson();

    //缓存文件根目录(RxCache和Glide的的缓存都已经作为子文件夹在这个目录里),应该将所有缓存放到这个根目录里,便于管理和清理,可在GlobeConfigModule里配置
//    File cacheFile();

//    //用于管理所有activity
//    AppManager appManager();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
//    Map<String, Object> extras();

//    void inject(AppDelegate delegate);
}

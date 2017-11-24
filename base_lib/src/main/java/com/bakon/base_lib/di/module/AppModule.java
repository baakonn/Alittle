package com.bakon.base_lib.di.module;

import com.bakon.base_lib.base.BaseApplication;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class AppModule {
    private BaseApplication mApplication;

    public AppModule(BaseApplication application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public BaseApplication provideApplication() {
        return mApplication;
    }

    //可以存放全局的数据，类似session的感觉
    @Singleton
    @Provides
    public Map<String, Object> provideSession() {
        return new HashMap();
    }


}

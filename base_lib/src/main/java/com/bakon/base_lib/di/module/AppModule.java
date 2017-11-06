package com.bakon.base_lib.di.module;

import com.bakon.base_lib.base.BaseApplication;

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


}

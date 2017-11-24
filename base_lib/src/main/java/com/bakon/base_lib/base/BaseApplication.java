package com.bakon.base_lib.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bakon.base_lib.di.component.AppComponent;
import com.bakon.base_lib.di.component.DaggerAppComponent;
import com.bakon.base_lib.di.module.AppModule;
import com.bakon.base_lib.di.module.NetworkModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class BaseApplication extends Application {

    private static BaseApplication mApplication;
    public static AppComponent appComponent;

    //Application为整个应用保存全局的RefWatcher
    private RefWatcher refWatcher;

    public static BaseApplication getInstance() {
        return mApplication;
    }
    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mApplication))
                    .networkModule(new NetworkModule())
                    .build();
        }
        return appComponent;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //内存泄漏检测
        refWatcher = setupLeakCanary();

        //初始化DB
//        GreenDaoManager.getInstance();
        //腾讯X5
//        QbSdk.initX5Environment(getApplicationContext(),  null);
    }



    //内存泄漏检测
    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

}

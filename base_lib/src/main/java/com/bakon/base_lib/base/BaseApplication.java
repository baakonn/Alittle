package com.bakon.base_lib.base;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDex;

import com.bakon.base_lib.baseutil.NetUtil;
import com.bakon.base_lib.baseutil.ToastUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    //接收网络状态变化的Receiver
    private ConnectionChangeReceiver mNetworkStateReceiver;
    //Application为整个应用保存全局的RefWatcher
    private RefWatcher refWatcher;

    public static BaseApplication getInstance() {
        return mApplication;
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
        ////
        initConnectionChangeReceiver();
        //初始化OKHttp配置
//        HttpUtils.initOkHttpUtils();
        //初始化DB
//        GreenDaoManager.getInstance();

        //在这里初始化 bugtags
//        Bugtags.start("f1ad69f1d895506f7983a985adb28704", this, Bugtags.BTGInvocationEventNone);

        //腾讯X5
//        QbSdk.initX5Environment(getApplicationContext(),  null);
    }

    //获取设备的屏幕宽度
    public int getScreenWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }

    //设备的屏幕高度
    public int getScreenHeight() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }

    //设备的密度值
    public float getScale() {
        return this.getResources().getDisplayMetrics().density;
    }


    //获取应用包名
    String MyPackageName;

    public String getMyPackageName() {
        if (MyPackageName == null) {
            MyPackageName = getPackageName();
        }
        return MyPackageName;
    }

    /**
     * 初始化网络状态广播接收者
     */
    public void initConnectionChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(getNetworkStateReceiver(), filter);
    }

    //获得ConnectionChangeReceiver的实例
    private ConnectionChangeReceiver getNetworkStateReceiver() {
        if (mNetworkStateReceiver == null) {
            mNetworkStateReceiver = new ConnectionChangeReceiver();
        }
        return mNetworkStateReceiver;
    }

    //内部类ConnectionChangeReceiver
    public class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!NetUtil.isNetWorkAviliable()) {
                ToastUtil.showShort("当前网络不可用");
            }
        }
    }

    @Override
    public void onLowMemory() {
        //ToastUtil.showDefaultGravityToast("低内存警告");
//		//如果内存不够用，就清空Activities，释放掉Activity的引用
        //AppManager.getAppManager().finishOtherButCurrentActivity();
        super.onLowMemory();

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

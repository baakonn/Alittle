/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bakon.base_lib.mvp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.bakon.base_lib.base.AppManager;
import com.bakon.base_lib.baseutil.StatusBarUtil;
import com.bakon.base_lib.di.component.ActivityComponent;
import com.bakon.base_lib.di.component.DaggerActivityComponent;
import com.bakon.base_lib.di.module.ActivityModule;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.bakon.base_lib.base.BaseApplication.getAppComponent;

/**
 * ================================================
 * 因为 Java 只能单继承,所以如果要用到需要继承特定 {@link Activity} 的三方库,那你就需要自己自定义 {@link Activity}
 * 继承于这个特定的 {@link Activity},然后再按照 {@link BaseActivity} 的格式,将代码复制过去,记住一定要实现{@link IActivity}
 * <p>
 * Created by JessYan on 22/03/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements IActivity {
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;

    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //网页中的视频，上屏幕的时候，可能出现闪烁的情况
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtil.setWindowFlag(getWindow());

        super.onCreate(savedInstanceState);
        int layoutResID = initView(savedInstanceState);
        if (layoutResID != 0) {//如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            setContentView(layoutResID);
            //绑定到butterknife
            mUnbinder = ButterKnife.bind(this);
            //dagger注入
            setupActivityComponent();
            AppManager.getAppManager().addActivity(this);
            AppManager.getAppManager().setCurrentActivity(this);
            if (useEventBus()) {
                EventBus.getDefault().register(this);
            }
            initData(savedInstanceState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mUnbinder = null;
        this.mPresenter = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    //  建议写在基类Activity里
    protected ActivityComponent getActivityComponent(){
        return  DaggerActivityComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


}

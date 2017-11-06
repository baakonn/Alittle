/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bakon.base_lib.mvp;

/**
 * 添加公用的接口
 */
public class BasePresenter<V extends BaseView> implements IPresenter{

    protected V mRootView;

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
        //如果要使用eventbus请将此方法返回true
//        if (useEventBus()){
//            EventBus.getDefault().register(this);//注册eventbus
//        }
    }

    @Override
    public void onDestroy() {
        //如果要使用eventbus请将此方法返回true
//        if (useEventBus()){
//            EventBus.getDefault().unregister(this);//解除注册eventbus
//        }
        this.mRootView = null;
    }


//    /**
//     * 是否使用eventBus,默认为使用(true)，
//     *
//     * @return
//     */
//    protected boolean useEventBus() {
//        return true;
//    }
}

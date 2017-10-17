package com.bakon.base_lib.net;
import com.bakon.base_lib.model.BaseBean;

import io.reactivex.disposables.Disposable;

/**
 * <p>
 * 定义请求结果处理接口
 */

public interface ISubscriber<T extends BaseBean> {

    void doOnSubscribe(Disposable d);

    void doOnError(String errorMsg);

    void doOnNext(T t);

    void doOnCompleted();
}
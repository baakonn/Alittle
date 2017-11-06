package com.bakon.base_lib.net;


import io.reactivex.disposables.Disposable;

/**
 * <p>
 * 定义请求结果处理接口
 */

public interface ISubscriber<T> {

    void doOnSubscribe(Disposable d);

    void doOnError(String errorMsg);

    void doOnNext(T t);

    void doOnCompleted();
}
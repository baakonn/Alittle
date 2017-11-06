package com.bakon.base_lib.net;

import com.bakon.base_lib.baseutil.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 基类BaseObserver
 */

public abstract class BaseObserver<T> implements Observer<T>, ISubscriber<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        doOnNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof SocketTimeoutException) {
            setError("网络链接超时，请检查您的网络状态，稍后重试！");
        } else if (e instanceof ConnectException) {
            setError("网络链接异常，请检查您的网络状态");
        } else if (e instanceof UnknownHostException) {
            setError("网络异常，请检查您的网络状态");
        } else {
            setError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

    //错误显示
    private void setError(String errorMsg) {
        ToastUtil.showShort(errorMsg);
        doOnError(errorMsg);
    }

}

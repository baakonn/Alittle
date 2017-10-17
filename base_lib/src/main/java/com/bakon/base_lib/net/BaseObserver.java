package com.bakon.base_lib.net;

import android.widget.Toast;

import com.bakon.base_lib.base.BaseApplication;
import com.bakon.base_lib.model.BaseBean;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * 基类BaseObserver
 */

public abstract class BaseObserver<T extends BaseBean> implements Observer<T>, ISubscriber<T> {

    private Toast mToast;

    protected void doOnNetError() {
    }

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

            String error = e.getMessage();
            showToast(error);
            doOnError(error);
        }
    }


    @Override
    public void onComplete() {
        doOnCompleted();
    }


    private void setError(String errorMsg) {
        showToast(errorMsg);
        doOnError(errorMsg);
        doOnNetError();
    }


    /**
     * Toast提示
     *
     * @param msg 提示内容
     */
    protected void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    /**
     * 错误处理
     *
     * @param e
     * @return
     */
    private String handleError(Throwable e) {
        String error = null;
        try {
            ResponseBody errorBody = ((HttpException) e).response().errorBody();
            error = errorBody.string();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return error;
    }
}

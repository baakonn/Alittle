package com.bakon.alittle.mvp.main;

import android.os.Message;

import com.bakon.alittle.bean.ApiStore;
import com.bakon.alittle.bean.NewsBean;
import com.bakon.alittle.util.Constant;
import com.bakon.base_lib.base.BaseApplication;
import com.bakon.base_lib.model.BaseResponse;
import com.bakon.base_lib.mvp.BasePresenter;
import com.bakon.base_lib.net.BaseObserver;
import com.bakon.base_lib.net.Network;
import com.bakon.base_lib.net.Transformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MainPresenter
 * author: bakon(762713299@qq.com)
 * date: 2017年6月20日11:33:02
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainContract.View view) {
        super(view);
    }

//    @Inject
//    public MainPresenter() {
//    }

    @Override
    public void loadNews(int type, int pagerNum) {
//    BaseApplication.getAppComponent().getHttpApi()  //也可以这样使用
        Network.getRetrofit().create(ApiStore.class)
                .getCategoryData(Constant.tabName[type], 20, pagerNum)
                .compose(Transformer.<BaseResponse<List<NewsBean>>>switchSchedulers())
                .subscribe(new BaseObserver<BaseResponse<List<NewsBean>>>() {
                    @Override
                    public void doOnSubscribe(Disposable d) {

                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        //失败
                        Message message = Message.obtain();
                        message.what = MainContract.errorCode;
                        message.obj = errorMsg;
                        mRootView.showData(message);
                    }

                    @Override
                    public void doOnNext(BaseResponse<List<NewsBean>> listBaseResponse) {
                        //成功
                        Message message = Message.obtain();
                        message.what = MainContract.succCode;
                        message.obj = listBaseResponse.getData();
                        mRootView.showData(message);
                    }

                    @Override
                    public void doOnCompleted() {

                    }
                });
    }


}

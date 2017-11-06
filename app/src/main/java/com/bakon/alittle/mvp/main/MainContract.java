package com.bakon.alittle.mvp.main;


import android.os.Message;

import com.bakon.base_lib.mvp.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MainContract {
    int errorCode = 1001;
    int succCode = 1002;

    interface View extends BaseView {
        void showData(Message message);
    }

    interface Presenter {
        void loadNews(int type, int pagerNum);
    }


}

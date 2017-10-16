package com.bakon.alittle.mvp.main;


import com.bakon.alittle.bean.NewsBean;
import com.bakon.base_lib.basemvp.BasePresenter;
import com.bakon.base_lib.basemvp.BaseView;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MainContract {

    interface View extends BaseView {
        void addNews(List<NewsBean> newsList);
    }

    interface Presenter extends BasePresenter {
        void loadNews(int type, int pagerNum);
        void loadNews(String url, int type, OnLoadNewsListener listener);
    }


}

package com.bakon.alittle.presenter;

/**
 * NewsModel
 * author: bakon(762713299@qq.com)
 * date: 2016-11-08
 */
//http://www.cnblogs.com/liuling/archive/2015/12/23/mvp-pattern-android.html
public interface NewsPresenter {

    void loadNews(String url, int type);
    void loadNewsNextPage(String url, int page);

}

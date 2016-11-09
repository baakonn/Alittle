package com.bakon.alittle.model;

/**
 * NewsModel
 * author: bakon(762713299@qq.com)
 * date: 2016-11-08
 */
public interface NewsModel {

    void loadNews(String url, int type, OnLoadNewsListener listener);

}

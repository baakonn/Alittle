package com.bakon.alittle;

import com.bakon.alittle.bean.NewsBean;

import java.util.List;

/**
 * Created by bakon on 16/11/8.
 */

public interface NewsView {

    void showProgress();
    void addNews(List<NewsBean> newsList);
    void hideProgress();
    void showLoadFailMsg(String msg);
}

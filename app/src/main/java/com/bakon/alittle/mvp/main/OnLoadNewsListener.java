package com.bakon.alittle.mvp.main;

import com.bakon.alittle.bean.NewsBean;

import java.util.List;

/**
 * OnLoadNewsListener
 * author: bakon(762713299@qq.com)
 * date: 2016-11-09
 */
public interface OnLoadNewsListener {

    void onSuccess(List<NewsBean> list);

    void onFailure(String msg, Exception e);
}

package com.bakon.alittle.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bakon.alittle.bean.ApiResponse;
import com.bakon.alittle.bean.NewsBean;
import com.bakon.alittle.util.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * NewsModel
 * author: bakon(762713299@qq.com)
 * date: 2016-11-08
 */
public class NewsModelImpl implements NewsModel {

    @Override
    public void loadNews(String url, int type, final OnLoadNewsListener listener) {
        LogUtil.d("okhttputils", "loadNews url=="+url);
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        //id：100 http   id：101 https
                        LogUtil.d("okhttputils", "onResponse-id = " + id);
                        if (!TextUtils.isEmpty(response)) {
                            //解析泛型new TypeReference<ApiResponse<NewsBean>>(){}
                            ApiResponse apiResponse = JSON.parseObject(response,
                                    new TypeReference<ApiResponse<NewsBean>>(){});
                            if (apiResponse != null && !apiResponse.error) {
                                listener.onSuccess(apiResponse.results);
                            } else {
                                LogUtil.d("okhttputils", "检查apiResponse.error");
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.d("okhttputils", "onError-id = " + id);
                        listener.onFailure(e.getMessage(), e);
                    }
                });
    }

}

package com.bakon.alittle.mvp.main;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bakon.alittle.bean.ApiResponse;
import com.bakon.alittle.bean.NewsBean;
import com.bakon.alittle.util.Constant;
import com.bakon.alittle.util.UrlPath;
import com.bakon.base_lib.baseutil.LogUtil;
import com.bakon.base_lib.model.CategoryResult;
import com.bakon.base_lib.net.Network;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;

import io.reactivex.Observable;
import okhttp3.Call;

/**
 * MainPresenter
 * author: bakon(762713299@qq.com)
 * date: 2017年6月20日11:33:02
 */
public class MainPresenter implements MainContract.Presenter {

    @Override
    public void loadNews(int type, int pagerNum) {
        String url = null;
        try {
            url = getUrl(type, pagerNum);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LogUtil.d("url=" + url);
        //只有第一页的或者刷新的时候才显示刷新进度条
        if (pagerNum == 0) {
//            mNewsView.showProgress();
        }

        Observable<CategoryResult> observable = Network.getInstance().getCategoryData(Constant.tabName[type],20,pagerNum);







    }

    @Override
    public void loadNews(String url, int type, final OnLoadNewsListener listener) {
        LogUtil.d("okhttputils", "loadNews url==" + url);








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
                                    new TypeReference<ApiResponse<NewsBean>>() {
                                    });
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

    /**
     * 组合对应tab的url
     *
     * @param type
     * @param pagerNum
     * @return
     */
    private String getUrl(int type, int pagerNum) throws UnsupportedEncodingException {
        StringBuffer url = new StringBuffer();
        switch (type) {
            case Constant.TAB_TYPE.SURPRISE:
//                url.append(URLEncoder.encode(UrlPath.GANK_SURPRISE, "utf-8"));
                url.append(UrlPath.GANK_SURPRISE);
                break;
            case Constant.TAB_TYPE.ANDROID:
                url.append(UrlPath.GANK_ANDROID);
                break;
            case Constant.TAB_TYPE.HTML:
                url.append(UrlPath.GANK_HTML);
                break;
            case Constant.TAB_TYPE.IOS:
                url.append(UrlPath.GANK_IOS);
                break;
            case Constant.TAB_TYPE.MORE:
                url.append(UrlPath.GANK_MORE);
                break;
            default:
                url.append(UrlPath.GANK_ANDROID);
                break;
        }
        return url.append(UrlPath.GANK_PAGESIZE)
                .append("/")
                .append(pagerNum)
                .toString();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}

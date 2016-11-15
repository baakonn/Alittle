package com.bakon.alittle.presenter;

import com.bakon.alittle.NewsView;
import com.bakon.alittle.bean.NewsBean;
import com.bakon.alittle.model.NewsModel;
import com.bakon.alittle.model.NewsModelImpl;
import com.bakon.alittle.model.OnLoadNewsListener;
import com.bakon.alittle.util.Constant;
import com.bakon.alittle.util.LogUtil;
import com.bakon.alittle.util.UrlPath;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * NewsModel
 * author: bakon(762713299@qq.com)
 * date: 2016-11-08
 */
public class NewsPresenterImpl implements NewsPresenter,OnLoadNewsListener {
    private static String TAG = "NewsPresenterImpl";
    private NewsView mNewsView;
    private NewsModel mNewsModel;

    /**
     * 构造方法，持有NewsView和NewsModel
     * @param newsView
     */
    public NewsPresenterImpl(NewsView newsView) {
        this.mNewsView = newsView;
        this.mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNews(int type, int pagerNum) {
        String url = null;
        try {
            url = getUrl(type, pagerNum);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LogUtil.d(TAG, "url=" + url);
        //只有第一页的或者刷新的时候才显示刷新进度条
        if(pagerNum == 0) {
            mNewsView.showProgress();
        }
        mNewsModel.loadNews(url, type, this);
    }

    @Override
    public void onSuccess(List<NewsBean> list) {
        mNewsView.addNews(list);
        mNewsView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsView.hideProgress();
        mNewsView.showLoadFailMsg(msg);
    }

    /**
     * 组合对应tab的url
     * @param type
     * @param pagerNum
     * @return
     */
    private String getUrl(int type, int pagerNum) throws UnsupportedEncodingException {
        StringBuffer url = new StringBuffer(UrlPath.GANK_BASEURL);
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
}

package com.bakon.alittle.bean;

import com.bakon.base_lib.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 代码家的gank.io接口
 */

public interface ApiStore {

    /**
     * 根据category获取Android、iOS等干货数据
     *
     * @param category 类别
     * @param count    条目数目
     * @param page     页数
     */
    @GET("data/{category}/{count}/{page}")
    Observable<BaseResponse<List<NewsBean>>> getCategoryData(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}

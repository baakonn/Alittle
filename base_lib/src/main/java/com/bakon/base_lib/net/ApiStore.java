package com.bakon.base_lib.net;

import com.bakon.base_lib.model.BaseBean;
import com.bakon.base_lib.model.CategoryResult;
import com.bakon.base_lib.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 代码家的gank.io接口
 */

public interface ApiStore {
    String API_BASE_URL = "http://gank.io/api/";

    @POST("101")
    Observable<BaseBean<User>> login(@Field("username") String username, @Field("password") String password);


    /**
     * 根据category获取Android、iOS等干货数据
     *
     * @param category 类别
     * @param count    条目数目
     * @param page     页数
     */
    @GET("data/{category}/{count}/{page}")
    Observable<CategoryResult> getCategoryData(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}

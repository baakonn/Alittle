package com.bakon.base_lib.model;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器通用返回数据格式
 * gankio的数据形式
 * {
 "error": false,
 "results": []
 }
 * Created by Administrator on 2017/10/17 0017.
 */

public class BaseResponse<T> {
    @SerializedName("error")
    private boolean error;
    @SerializedName("msg")
    private String msg;
    @SerializedName("results")
    private T results;

    public boolean isSuccess() {
        return error == false;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return results;
    }
}

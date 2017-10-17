package com.bakon.base_lib.model;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器通用返回数据格式
 * Created by Administrator on 2017/10/17 0017.
 */

public class BaseBean<T> {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}

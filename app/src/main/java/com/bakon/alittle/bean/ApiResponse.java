package com.bakon.alittle.bean;


import java.util.List;

/**
 * news item
 */
public class ApiResponse<T> {
    //error
    public boolean error;
    //具体的实体bean list
    public List<T> results;
    //分类列表-暂时不用
    public List<String> category;

}

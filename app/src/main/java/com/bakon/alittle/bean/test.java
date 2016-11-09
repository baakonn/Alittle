package com.bakon.alittle.bean;


import java.io.Serializable;
import java.util.List;

/**
 * news item
 */
public class test implements Serializable{
    private static final long serialVersionUID = 1L;


    /**
     * _id : 58227dc2421aa90e6f21b4e0
     * createdAt : 2016-11-09T09:37:06.212Z
     * desc : ReView 一个用于辅助程序员、设计师“在线”查看字体大小以及颜色等属性的 View 组件集合
     * images : ["http://img.gank.io/5643e616-c539-4490-a8af-e38d4214a83f"]
     * publishedAt : 2016-11-09T11:40:48.268Z
     * source : chrome
     * type : Android
     * url : https://github.com/maoruibin/ReView
     * used : true
     * who : 咕咚
     */

    public String _id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
    public List<String> images;
}

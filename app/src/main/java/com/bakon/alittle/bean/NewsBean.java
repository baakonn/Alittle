package com.bakon.alittle.bean;


import java.util.List;

/**
 * news item
 */
public class NewsBean{
    //id  hash值
    public String _id;
    //创建时间 2016-10-01T11:50:13.266Z
    public String createdAt;
    //desc 描述，标题
    public String desc;
    //发布时间（显示这个）: 2016-10-26T11:28:10.759Z
    public String publishedAt;
    //来源类型: web
    public String source;
    //news类型（福利 | Android | iOS | 休息视频 | 拓展资源 | 前端）
    public String type;
    //详情地址webview显示
    public String url;
    //图片地址
    public List<String> images;
    //used
    public boolean used;
    //作者name
    public String who;

}

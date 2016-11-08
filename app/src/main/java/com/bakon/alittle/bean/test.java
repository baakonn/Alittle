package com.bakon.alittle.bean;


import java.io.Serializable;
import java.util.List;

/**
 * news item
 */
public class test implements Serializable{
    private static final long serialVersionUID = 1L;


    public boolean error;
    public ResultsBean results;
    public List<String> category;

    public static class ResultsBean {

        public List<AndroidBean> Android;

        public List<IOSBean> iOS;

        public List<休息视频Bean> 休息视频;

        public List<拓展资源Bean> 拓展资源;

        public List<瞎推荐Bean> 瞎推荐;

        public List<福利Bean> 福利;

        public static class AndroidBean {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String type;
            public String url;
            public boolean used;
            public String who;
        }

        public static class IOSBean {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String type;
            public String url;
            public boolean used;
            public String who;
        }

        public static class 休息视频Bean {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String type;
            public String url;
            public boolean used;
            public String who;
        }

        public static class 拓展资源Bean {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String type;
            public String url;
            public boolean used;
            public String who;
        }

        public static class 瞎推荐Bean {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String type;
            public String url;
            public boolean used;
            public String who;
        }

        public static class 福利Bean {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String type;
            public String url;
            public boolean used;
            public String who;
        }
    }
}

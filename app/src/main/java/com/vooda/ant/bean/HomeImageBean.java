package com.vooda.ant.bean;

import java.util.List;

/**
 * Create by  leijiaxq
 * Date       2017/3/13 18:16
 * Describe   首页图片bean
 */

public class HomeImageBean extends BaseBean {


    /**
     * result : ok
     * message : 获取成功.
     * data : [{"HomeID":1,"ImgUrl":"/upload/image/2016/10/12/0750aead-aac8-4d2d-b176-e9b16fb3c058.jpg","PostDate":"2016-10-12 15:57:11"},{"HomeID":2,"ImgUrl":"/upload/image/2016/09/08/2180fc5b-908f-4e40-a204-2d02a039793b.jpg","PostDate":"2016-09-08 11:33:51"},{"HomeID":3,"ImgUrl":"/upload/image/2016/10/12/ad902de7-0bb9-4277-8d6a-0aaf33996ffb.png","PostDate":"2016-10-12 15:55:10"},{"HomeID":4,"ImgUrl":"/upload/image/2016/09/08/12709486-9641-4e4d-8abc-20bf9f7aa0a4.jpg","PostDate":"2016-09-08 11:34:11"}]
     * dataSet : null
     * other : null
     */

    public String           result;
    public String           message;
    public Object           dataSet;
    public Object           other;
    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * HomeID : 1
         * ImgUrl : /upload/image/2016/10/12/0750aead-aac8-4d2d-b176-e9b16fb3c058.jpg
         * PostDate : 2016-10-12 15:57:11
         */

        public int    HomeID;
        public String ImgUrl;
        public String PostDate;
    }
}

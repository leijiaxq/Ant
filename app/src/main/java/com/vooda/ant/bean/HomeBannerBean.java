package com.vooda.ant.bean;

import java.util.List;

/**
 * Create by  leijiaxq
 * Date       2017/3/13 18:18
 * Describe   首页轮播图bean
 */

public class HomeBannerBean extends BaseBean {

    /**
     * data : [{"AdID":29,"ImgUrl":"/upload/image/2016/09/08/3e553b86-4756-4d57-af18-ef0fd7b7602b.jpg","LinkUrl":"","Title":"超市"},{"AdID":30,"ImgUrl":"/upload/image/2017/01/02/2ab98057-a773-4fcf-9e9c-c1ab5fdb28cb.jpg","LinkUrl":"","Title":"新鲜果"},{"AdID":31,"ImgUrl":"/upload/image/2017/01/02/44da9737-dbfc-484e-b5de-c6998b140352.jpg","LinkUrl":"","Title":"初一十五"},{"AdID":32,"ImgUrl":"/upload/image/2016/09/08/4cc4fbd7-65cc-4f3f-8580-a6994cccf6d2.jpg","LinkUrl":"","Title":"跑腿"}]
     * message : 获取成功
     * result : ok
     */

    public String message;
    public String           result;
    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * AdID : 29
         * ImgUrl : /upload/image/2016/09/08/3e553b86-4756-4d57-af18-ef0fd7b7602b.jpg
         * LinkUrl :
         * Title : 超市
         */

        public int AdID;
        public String ImgUrl;
        public String LinkUrl;
        public String Title;
    }
}

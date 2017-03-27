package com.vooda.ant.bean;

import java.util.List;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 9:56
 * Describe
 */

public class FiltrateTotalBean extends BaseBean {

    /**
     * data : [{"AddTime":"2016-11-15 17:52:11","CID":442,"ClassName":"水果","ImageUrl":"","ParentID":"442"},{"AddTime":"2016-08-27 16:09:35","CID":209,"ClassName":"蔬菜","ImageUrl":"","ParentID":"209"},{"AddTime":"2016-08-27 16:10:45","CID":210,"ClassName":"肉类","ImageUrl":"","ParentID":"210"},{"AddTime":"2016-08-27 16:11:17","CID":211,"ClassName":"海鲜水产","ImageUrl":"","ParentID":"211"},{"AddTime":"2016-10-17 16:07:16","CID":332,"ClassName":"蛋豆丸","ImageUrl":"","ParentID":"332"},{"AddTime":"2016-11-15 17:25:22","CID":417,"ClassName":"粿条面粉","ImageUrl":"/upload/image/2016/11/15/1c9270ef-1db6-4fe5-b998-8247e054fc6f.jpg","ParentID":"417"},{"AddTime":"2016-11-07 18:31:50","CID":392,"ClassName":"调味用品","ImageUrl":"","ParentID":"392"}]
     * message : 获取成功.
     * result : ok
     */

    public String           message;
    public String           result;
    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * AddTime : 2016-11-15 17:52:11
         * CID : 442
         * ClassName : 水果
         * ImageUrl :
         * ParentID : 442
         */

        public String AddTime;
        public int    CID;
        public String ClassName;
        public String ImageUrl;
        public String ParentID;

        //用于区别显示背景颜色
        public boolean mFlag = false;
    }
}

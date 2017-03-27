package com.vooda.ant.bean;

import java.util.List;

/**
 * Create by  leijiaxq
 * Date       2017/3/22 15:36
 * Describe
 */
public class CommentCountBean extends BaseBean{


    /**
     * result : ok
     * message : 获取成功.
     * data : [{"BadCount":0,"NormalCount":0,"GoodCount":1}]
     * dataSet : null
     * other : null
     */

    public String result;
    public String           message;
    public String           dataSet;
    public String           other;
    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * BadCount : 0
         * NormalCount : 0
         * GoodCount : 1
         */

        public int BadCount;
        public int NormalCount;
        public int GoodCount;
    }
}

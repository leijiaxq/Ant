package com.vooda.ant.bean;

import java.util.List;

/**
 * Create by  leijiaxq
 * Date       2017/3/22 13:38
 * Describe
 */

public class CommentBean extends BaseBean {


    /**
     * result : ok
     * message : 获取成功.
     * data : [{"row_id":1,"CommentID":1074,"Overview":14.5,"ImageUrl":"","AddTime":"2016-10-28 15:02:33","ProID":1978,"UserID":96,"UserName":null,"HeadUrl":"http://wx.qlogo.cn/mmopen/Of5QNstbUDlh8hsSU8VOlib70wkSRcftbfBonriavnuGyF5lX8MMQxXPF85tS4eWf7TVjjjCjTp5k0piaYQr7GEib0EalUxlZHQz/0","Remark":"66666666","IsIncognito":"N","IsValid":"Y","Contents":"","PostDate":null}]
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
         * row_id : 1
         * CommentID : 1074
         * Overview : 14.5
         * ImageUrl :
         * AddTime : 2016-10-28 15:02:33
         * ProID : 1978
         * UserID : 96
         * UserName : null
         * HeadUrl : http://wx.qlogo.cn/mmopen/Of5QNstbUDlh8hsSU8VOlib70wkSRcftbfBonriavnuGyF5lX8MMQxXPF85tS4eWf7TVjjjCjTp5k0piaYQr7GEib0EalUxlZHQz/0
         * Remark : 66666666
         * IsIncognito : N
         * IsValid : Y
         * Contents :
         * PostDate : null
         */

        public int row_id;
        public int    CommentID;
        public double Overview;
        public String ImageUrl;
        public String AddTime;
        public int    ProID;
        public int    UserID;
        public String UserName;
        public String HeadUrl;
        public String Remark;
        public String IsIncognito;
        public String IsValid;
        public String Contents;
        public String PostDate;
    }
}

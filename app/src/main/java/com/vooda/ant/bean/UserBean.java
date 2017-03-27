package com.vooda.ant.bean;

import java.util.List;

/**
 * Create by  leijiaxq
 * Date       2017/3/21 10:00
 * Describe
 */

public class UserBean extends BaseBean{

    /**
     * result : ok
     * message : 登录成功
     * data : [{"UserID":110,"UserName":null,"NickName":"番茄酱","HeadUrl":"/upload/2016/12/05/539e751b-6eb4-4cce-ac66-cf7626641b5a.png","Password":"e10adc3949ba59abbe56e057f20f883e","Phone":"13888888888","RegisterDate":"2016-09-13 15:17:30","LastLoginDate":"2017-03-21 10:00:06","IsStatus":true,"OpenID":null,"IsID":0,"Sex":"男","Remark":null}]
     * dataSet : null
     * other : null
     */

    public String           result;
    public String           message;
    public String           dataSet;
    public String           other;
    public List<UserInfoBean> data;

    public static class UserInfoBean {
        /**
         * UserID : 110
         * UserName : null
         * NickName : 番茄酱
         * HeadUrl : /upload/2016/12/05/539e751b-6eb4-4cce-ac66-cf7626641b5a.png
         * Password : e10adc3949ba59abbe56e057f20f883e
         * Phone : 13888888888
         * RegisterDate : 2016-09-13 15:17:30
         * LastLoginDate : 2017-03-21 10:00:06
         * IsStatus : true
         * OpenID : null
         * IsID : 0
         * Sex : 男
         * Remark : null
         */

        public int     UserID;
        public String  UserName;
        public String  NickName;
        public String  HeadUrl;
        public String  Password;
        public String  Phone;
        public String  RegisterDate;
        public String  LastLoginDate;
        public boolean IsStatus;
        public String  OpenID;
        public int     IsID;
        public String  Sex;
        public String  Remark;
    }
}

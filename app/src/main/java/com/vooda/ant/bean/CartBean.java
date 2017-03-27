package com.vooda.ant.bean;

import java.util.List;

/**
 * Created by  leijiaxq
 * Date        2017/3/27 14:39
 * Describe
 */
public class CartBean extends BaseBean{


    /**
     * result : ok
     * message : 获取成功.
     * data : [{"SCID":10872,"UserID":66,"ProID":1978,"BuyNum":1,"ProductName":"棱瓜（角瓜）","Price":6,"IsStatus":1,"StockCount":966,"ImageUrl":"/upload/2016/10/18/2e945d47-3820-4cb6-a7ad-b7becd8c029a.jpg","UnitName":"斤（时价）","PCType":1},{"SCID":9098,"UserID":66,"ProID":5579,"BuyNum":1,"ProductName":"美国啤梨","Price":20,"IsStatus":1,"StockCount":9974,"ImageUrl":"/upload/2016/10/29/7f44b56c-e3bc-4d37-bcc6-2b94b16b1b02_thumbail_.jpg","UnitName":"斤","PCType":1},{"SCID":9097,"UserID":66,"ProID":6400,"BuyNum":1,"ProductName":"嘉顿蔬菜梳打饼干230g","Price":6.5,"IsStatus":1,"StockCount":81,"ImageUrl":"/upload/2017/01/19/ca65c051-3b18-483d-b9cf-aadc8560fee8_thumbail_.jpg","UnitName":"盒","PCType":2}]
     * dataSet : null
     * other : null
     */

    public String result;
    public String message;
    public Object dataSet;
    public Object other;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * SCID : 10872
         * UserID : 66
         * ProID : 1978
         * BuyNum : 1
         * ProductName : 棱瓜（角瓜）
         * Price : 6
         * IsStatus : 1
         * StockCount : 966
         * ImageUrl : /upload/2016/10/18/2e945d47-3820-4cb6-a7ad-b7becd8c029a.jpg
         * UnitName : 斤（时价）
         * PCType : 1
         */

        public int SCID;
        public int UserID;
        public int ProID;
        public int BuyNum;
        public String ProductName;
        public double Price;
        public int IsStatus;
        public int StockCount;
        public String ImageUrl;
        public String UnitName;
        public int PCType;
    }
}

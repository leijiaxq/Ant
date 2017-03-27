package com.vooda.ant.api;

import com.vooda.ant.bean.CartBean;
import com.vooda.ant.bean.CartDeleteBean;
import com.vooda.ant.bean.CartUpdateBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by  leijiaxq
 * Date        2017/3/27 14:30
 * Describe
 */

public interface CartService {


    //    获取购物车列表数据
    @FormUrlEncoded
    @POST("mall/GetShopCartDataTable")
    Observable<CartBean> getCartListNet(@Field("UserID") int UserID);

    //    更改购物车数量
    @FormUrlEncoded
    @POST("mall/UpdateShopCartData")
    Observable<CartUpdateBean> getUpdateShopCartNet(@Field("SCID") int SCID, @Field("Num") int Num);

    //    删除购物车商品 type 0全部,1某条
    @FormUrlEncoded
    @POST("mall/DeleteShopCartData")
    Observable<CartDeleteBean> getDeleteShopCartNet(@Field("SCID") int SCID, @Field("UserID") int UserID, @Field("Type") int Type);


}

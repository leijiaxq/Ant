package com.vooda.ant.api;


import com.vooda.ant.bean.ColletAddBean;
import com.vooda.ant.bean.ColletDeleteBean;
import com.vooda.ant.bean.ColletJudeBean;
import com.vooda.ant.bean.CommentBean;
import com.vooda.ant.bean.CommentCountBean;
import com.vooda.ant.bean.FiltrateItemBean;
import com.vooda.ant.bean.FiltrateTotalBean;
import com.vooda.ant.bean.HomeBannerBean;
import com.vooda.ant.bean.HomeImageBean;
import com.vooda.ant.bean.ProductBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Create by  leijiaxq
 * Date       2017/3/2 16:57
 * Describe
 */

public interface BaseService {

    //    获取首页Image
    @GET("mall/GetHomeImg")
    Observable<HomeImageBean> getImageNet();

    //    获取首页轮播图
    @FormUrlEncoded
    @POST("userattention/GetHomeUrl")
    Observable<HomeBannerBean> getImageBannerNet(@Field("Location") String Location);


    //    获取菜市场的分类数据  蔬菜，肉类，鱼类
    @FormUrlEncoded
    @POST("mall/GetAttributesDataTable")
    Observable<FiltrateTotalBean> getTotalType(@Field("Type") int Type, @Field("CID") String CID);

    //    获取菜市场的分类数据  蔬菜，肉类，鱼类----下一级分类
    @FormUrlEncoded
    @POST("mall/GetAttributesDataTable")
    Observable<FiltrateItemBean> getItemType(@Field("Type") int Type, @Field("CID") String CID);


    //    获取菜市场的分类数据  蔬菜，肉类，鱼类----下一级分类
    @FormUrlEncoded
    @POST("mall/GetProductDataTable")
    Observable<ProductBean> getProductListNet(@Field("Type") int Type, @Field("PageIndex") int PageIndex, @Field("OrderType") int OrderType, @Field("Order") int Order, @Field("ParentCID") String ParentCID);

    //    获取商品评论数量
    @FormUrlEncoded
    @POST("mall/GetProductCommentCountDetail")
    Observable<CommentCountBean> getProductCommenNumberNet(@Field("ID") int ID);


    //    获取商品评论详情
    @FormUrlEncoded
    @POST("mall/GetProductCommentDetail")
    Observable<CommentBean> getProductCommenDetailNet(@Field("ID") int ID, @Field("Type") int Type, @Field("PageIndex") int PageIndex);

    //    添加商品收藏
    @FormUrlEncoded
    @POST("mall/AddCollectData")
    Observable<ColletAddBean> getAddCollectDataNet(@Field("ProID") int ProID, @Field("UserID") int UserID);

    //    删除商品收藏
    @FormUrlEncoded
    @POST("/mall/DeleteCollectData")
    Observable<ColletDeleteBean> getDeleteCollectDataNet(@Field("ProID") int ProID, @Field("UserID") int UserID, @Field("Type") int Type, @Field("CollectID") String CollectID);

    //    判断商品是否已收藏
    @FormUrlEncoded
    @POST("/mall/IsCollect")
    Observable<ColletJudeBean> JudeCollectDataNet(@Field("ProID") int ProID, @Field("UserID") int UserID);

}

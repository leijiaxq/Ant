package com.vooda.ant.api;

import com.vooda.ant.bean.UserBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Create by  leijiaxq
 * Date       2017/3/21 9:55
 * Describe   个人中心相关的service
 */

public interface MeService{


    //   用户登录
    @FormUrlEncoded
    @POST("user/Login")
    Observable<UserBean> signIn(@Field("phone") String phone, @Field("Password") String Password);

}

package com.vooda.ant.base;

import android.app.Application;

import com.vooda.ant.bean.UserBean;

/**
 * Create by  leijiaxq
 * Date       2017/3/2 14:31
 * Describe
 */

public class BaseApplication extends Application {

    public static BaseApplication mInstance;

    public static UserBean.UserInfoBean mUserInfoBean;



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}

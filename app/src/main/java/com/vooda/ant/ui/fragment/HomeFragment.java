package com.vooda.ant.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.api.RetrofitHelper;
import com.vooda.ant.base.RxLazyFragment;
import com.vooda.ant.bean.BaseBean;
import com.vooda.ant.bean.HomeBannerBean;
import com.vooda.ant.bean.HomeImageBean;
import com.vooda.ant.ui.activity.MarketActivity;
import com.vooda.ant.utils.ImageUtil;
import com.vooda.ant.utils.ScreenUtil;
import com.vooda.ant.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create by  leijiaxq
 * Date       2017/3/13 15:48
 * Describe
 */
public class HomeFragment extends RxLazyFragment {

    @BindView(R.id.main_banner)
    Banner    mMainBanner;
    @BindView(R.id.main_super_iv)
    ImageView mMainSuperIv;
    @BindView(R.id.main_market_iv)
    ImageView mMainMarketIv;
    @BindView(R.id.main_run_iv)
    ImageView mMainRunIv;
    @BindView(R.id.main_tea_iv)
    ImageView mMainTeaIv;

    private List<String> mBannersList;
    private List<HomeImageBean.DataEntity> mDatas;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle state) {

    }

    @Override
    public void loadData() {
        int screenWidth = ScreenUtil.getScreenWidth(mActivity);
        ViewGroup.LayoutParams params = mMainBanner.getLayoutParams();
        float f = screenWidth*14f/25f;
        params.height = (int) f;
        mMainBanner.setLayoutParams(params);

        getBannerNet();

        getImageNet();
    }

    //获取超市，菜市场，下午茶背景图数据
    private void getImageNet() {
        RetrofitHelper.getBaseApi()
                .getImageNet()
                .compose(this.<HomeImageBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeImageBean>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(HomeImageBean bean) {
                        if (Constants.OK.equals(bean.result)) {
                            finishTask(bean);
                        } else {
                            ToastUtil.showShort(mActivity, TextUtils.isEmpty(bean.message) ? "" : bean.message);
                        }
                    }
                });


    }

    //获取广告轮播图数据
    private void getBannerNet() {
        RetrofitHelper
                .getBaseApi()
                .getImageBannerNet("9")
                .compose(this.<HomeBannerBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeBannerBean>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(HomeBannerBean bean) {
                        if (Constants.OK.equals(bean.result)) {
                            finishTask(bean);
                        } else {
                            ToastUtil.showShort(mActivity, TextUtils.isEmpty(bean.message) ? "" : bean.message);
                        }
                    }
                });
    }


    @Override
    protected void finishTask(BaseBean bean) {
        super.finishTask(bean);
        if (bean instanceof HomeBannerBean) {
            setHomeBanner((HomeBannerBean) bean);
        } else if (bean instanceof HomeImageBean) {
            setHomeImage((HomeImageBean) bean);
        }

    }

    //设置首页超市，菜市场，下午茶背景图
    private void setHomeImage(HomeImageBean bean) {
        mDatas = bean.data;
        if (mDatas != null && mDatas.size() > 3) {
            String imgUrl1 = mDatas.get(0).ImgUrl;
            String imgUrl2 = mDatas.get(1).ImgUrl;
            String imgUrl3 = mDatas.get(2).ImgUrl;
            String imgUrl4 = mDatas.get(3).ImgUrl;

            ImageUtil.getInstance().displayImage(mActivity,Constants.BASE_URL+imgUrl1,mMainMarketIv);
            ImageUtil.getInstance().displayImage(mActivity,Constants.BASE_URL+imgUrl2,mMainSuperIv);
            ImageUtil.getInstance().displayImage(mActivity,Constants.BASE_URL+imgUrl3,mMainRunIv);
            ImageUtil.getInstance().displayImage(mActivity,Constants.BASE_URL+imgUrl4,mMainTeaIv);
        }


    }

    //设置首页轮播图
    private void setHomeBanner(HomeBannerBean bean) {
        mBannersList = new ArrayList<>();
        for (HomeBannerBean.DataEntity bean2 : bean.data) {
            if (!bean2.ImgUrl.startsWith("http://")) {
                mBannersList.add(Constants.BASE_URL + bean2.ImgUrl);
            } else {
                mBannersList.add(bean2.ImgUrl);
            }

        }

        mMainBanner.setImages(mBannersList)
                //                .setBannerTitles(App.titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setDelayTime(5000)
                .setImageLoader(new ImageUtil.GlideImageLoader())
                .start();

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mMainBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mMainBanner.stopAutoPlay();
    }

    //菜市场
    @OnClick(R.id.main_market_iv)
    public void clickMarket(View view) {
        skipMarket(1);
    }



    //超市
    @OnClick(R.id.main_super_iv)
    public void clickSupert(View view) {
        skipMarket(2);

    }

    //跑腿
    @OnClick(R.id.main_run_iv)
    public void clickRun(View view) {

    }

    //下午茶
    @OnClick(R.id.main_tea_iv)
    public void clickTea(View view) {
        skipMarket(3);

    }

    //跳转到市场
    private void skipMarket(int index) {
        Intent intent = new Intent(mActivity, MarketActivity.class);
        intent.putExtra("market_key", index);
        startActivity(intent);
    }



}
